package lifepath;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.TreeSet;

import lifepath.Charts;
import character.EPCharacter;
import dice.Die;


/**
 * LifePath is a creator for the Eclipse Phase Transhuman Life Path way of
 * making characters. 
 * 
 * More information as it develops.
 * This contains the main class. Running it will generate one character.
 * 
 * 
 * This file is part of TranshumanLifePathGenerator.
 *
 * TranshumanLifePathGenerator is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation, either version 2 of the 
 * License, or (at your option) any later version.
 *
 * TranshumanLifePathGenerator is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TranshumanLifePathGenerator.  If not, see 
 * <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2014 
 * @author terrasaur
 *
 */
public class LifePath {	
	
	private EPCharacter character;
	private TreeSet<String> todo;
	private ArrayList<LifePathPackage> packages;
	private String aptTemplate;
	private YouthPath childhood;
	private String nativeLanguage;
	private ArrayList<PathEvent> eventList; 
	private String age;
	private Boolean getPreFallEvent = true;
	private AdultPath adulthood;
	private int totalPP;
	private String motivations;
	private Boolean chooseSkills = false;
	private boolean getRandomMotivations = false;
	private ArrayList<String> allowedBackgrounds;
	private ArrayList<String> allowedFactions;
	private boolean getFirewallEvent = true;
	private boolean getStoryEvent = true;

	
	public LifePath(String name) {
		super();
		this.character   = new EPCharacter(name);
		this.clearFields();
	}
	public LifePath() {
		super();
		this.character   = new EPCharacter();
		this.clearFields();
	}
	
	// Clears fields and whatnot
	private void clearFields(){
		if (packages == null) 
			packages = new ArrayList<LifePathPackage>();
		else
			packages.clear();
		if (eventList == null) 
			eventList = new ArrayList<PathEvent>();
		else
			eventList.clear();
		if (todo == null)
			todo = new TreeSet<String>();
		else
			todo.clear();
		
		this.childhood = new YouthPath();
		this.adulthood = new AdultPath();
		
		this.character = new EPCharacter();
		this.aptTemplate = "";
		this.nativeLanguage = "";
		this.age = "";
		this.getPreFallEvent = true;
		this.totalPP = 0;
		this.motivations = "";
	}
	
	/**
	 * Sets GUI-determined variables
	 * @param randomMotivations - if the sim should pick random motivations
	 * @param chooseSkills  - whether or not the sim should choose random skills
	 * @param randomMotivations 
	 * @param backgrounds - allowed backgrounds
	 * @param factions - allowed factions
	 */
	public void setUserOptions(boolean getFirewallEvent, boolean getStoryEvent, 
			boolean randomMotivations, boolean chooseSkills, 
			ArrayList<String> backgrounds, ArrayList<String> factions) {
		this.clearFields();
		this.getFirewallEvent = getFirewallEvent;
		this.getStoryEvent = getStoryEvent;
		this.chooseSkills = chooseSkills;
		this.getRandomMotivations  = randomMotivations;
		this.allowedBackgrounds = backgrounds;
		this.allowedFactions = factions;		
	}
	
	/**
	 * Generates entire life path from scratch. Will overwrite previous information.
	 */
	public void generateLifePath(){
		this.clearFields();
		
		Die d10  = new Die(10);
		Die d100 = new Die(100);
		
		// Set aptitude template
		int roll = d10.Roll();
		while (roll > 8){
			roll = d10.Roll(); // 9-10 is reroll
		}
		this.aptTemplate = Charts.aptitudeTemplates.get(roll-1).name;
		this.character.setAllAptitudes(Charts.aptitudeTemplates.get(roll-1).aptitudes);	

		
		// Get language
		this.nativeLanguage = ChartEntry.findResult(RandomSkillCharts.languageFields, d100.Roll());
		this.character.addNativeLanguage(this.nativeLanguage);
		
		// Get background event (might override youth package, so roll now)
		PathEvent tempBackground = PathEvent.getBackgroundEvent(d100.Roll());
		
		// Get youth path
		this.eventList.add(this.setYouthPath(tempBackground)); 
		

		this.setAdultPath();
		
		// Add together total allocated PP
		for (LifePathPackage p: this.packages){
			this.totalPP += p.ppCost;
		}
		// If we don't have enough, add more 1 pp packages
		if (this.totalPP < 10) {
			this.packages = this.adulthood.getAdditionalPackages(this.packages, 
					10-this.totalPP, this.childhood.isUplift);
		}		
	
		// Resolve post-life path stuff
		this.character.setStartingCredits(Charts.getStartingCredits(d10.Roll(), d10.Roll()));
		if (this.getFirewallEvent)
			this.eventList.add(PathEvent.getFirewallEvent(d100.Roll()));
		if (this.getStoryEvent)
			this.eventList.add(PathEvent.getStoryEvent(true));

		// This should add all the path event crap to the character
		for (PathEvent e: this.eventList){
			String s = e.resolveEvent(this.character);
			if (s != null){
				this.todo.add(s);
			}
		}
		
		// Add some messages to the todo list
		if (this.character.getMorph() != null && this.character.getMorph() != "Flat"){
			if (this.character.getMorphAptitudeChoice() != null)
				this.todo.add("Additional morph aptitudes: " + this.character.getMorphAptitudeChoice());
			else
				this.todo.add("Look up morph and apply morph bonuses");
		}
		if (this.character.getNumEgoTraits() > 0)
			this.todo.add("Look up and apply all traits");

		
		// Resolve packages
		this.todo.addAll(LifePathPackage.applyAllPackages(this.character, this.packages, this.chooseSkills));

		// Gets motivations
		ArrayList<String> motivationList = LifePathPackage.getMotivations(this.packages);
		String s;
		Die motiveDie = new Die (motivationList.size());
		int randomMotivations = 0;
		if (this.getRandomMotivations)
			randomMotivations = 2;
		// Motivations from your suggested motives list
		for (int i = 0; i < 3 - randomMotivations; i++){
			do {
				s = motivationList.get(motiveDie.Roll() - 1);
			} while ( this.motivations.contains(s.substring(1)));
			this.motivations += s + ", ";
		}
		
		// Random ones
		for (int i = 0; i < randomMotivations; i++){
			do {
				s = Charts.getRandomMotivation();
			} while ( this.motivations.contains(s.substring(1)));
			this.motivations += s + ", ";
		}		
		this.motivations = this.motivations.substring(0, this.motivations.length()-2);
		this.character.setMotivations(this.motivations);

	}
	
	/**
	 * Basically pretty toString()
	 * @return a pretty version of toString()
	 */
	public String getText() {
		String output = "";
		output += "Aptitude Template: " + this.aptTemplate + "\n";
		//output += this.character.printAptitudes() + "\n";
		//output += this.motivations + "\n";
		output += "Native Language: " + nativeLanguage + "\n";
		output += "Background: " + this.childhood.toString() + "\n";
		output += "Starting Morph: " + this.childhood.startingMorph.getLabel() + "\n";
		if (this.childhood.startingMorph.getLabel() != this.character.getMorph()){
			output += "Current Morph: " + this.character.getMorph() + "\n";
		}
		output += "Age: " + this.age + "\n";
		
		output += this.adulthood.getPathHistory() + "\n";
		output += "Life Path Events:" + "\n";
		for (PathEvent ev : this.eventList) {
			output += "   • " + wrap(ev.toString(), 75) + "\n";
		}

		//output += "Starting Credits: " + this.character.getCredits() + " credits";
		if (this.character.getCredits() == 0)
			output += "You are flat broke.";
		output += "\n";
		output += "Package list: \n";
		for (LifePathPackage p : this.packages){
			output += "   • " + p.toString() + "\n";
			//output += "   • " + p.prettyPrint() + "\n";
		}

		output += "\n CHARACTER INFORMATION \n";
		// Character information
		output += this.character.prettyPrint();
				
		output += "\n";
		output += "Remaining Actions:\n";
		for (String s : this.todo){
			output += "   • " + wrap( s , 75) + "\n";
		}
		output += "\n";
		return output;
	}
	
	/**
	 *  Code from: http://ramblingsrobert.wordpress.com/2011/04/13/java-word-wrap-algorithm/
	 * @param in input string
	 * @param len line length
	 * @return wrapped text
	 */
	public static String wrap(String in,int len) {
		in=in.trim();
		if(in.length()<len) 
			return in;
		if(in.substring(0, len).contains("\n"))
			return in.substring(0, in.indexOf("\n")).trim() + "\n\n" + wrap(in.substring(in.indexOf("\n") + 1), len);
		int place=Math.max(Math.max(in.lastIndexOf(" ",len),in.lastIndexOf("\t",len)),in.lastIndexOf("-",len));
		
		return in.substring(0,place).trim()+"\n     "+wrap(in.substring(place),len);
	}
	
	/**
	 * Prints a character given an output stream
	 * @param output stream to write to
	 */
	public void printCharacter(PrintStream output){
		output.println(this.getText());
	}

	/**
	 * Sets a new Youth Path. Will wipe out the old one
	 * @param roll a 1-10 roll of type of youth
	 * @param pathEvent 
	 */
	protected PathEvent setYouthPath(PathEvent pathEvent){
		this.childhood.allowedBackgrounds = this.allowedBackgrounds;
		this.childhood.getYouthPath();
		
		if (pathEvent.action == PathEvent.ActionType.overrideBackground){
			// there's only one override, and it's Street Rat
			if (!this.childhood.overrideLastPackageWithStreetRat()){
				// already have Street Rat, so get a new background
				Die d100 = new Die (100);
				int roll;
				do {
					roll = d100.Roll();
				} while (roll == 29 || roll == 30); // street rat result
				pathEvent = PathEvent.getBackgroundEvent(roll);
			}
		}
		
		this.character.setMorph(this.childhood.startingMorph);
		this.packages.addAll(this.childhood.getPackages());
		return pathEvent;
	}
	
	/**
	 * Sets the adult path (steps 6-9)
	 */
	protected void setAdultPath(){
		Die d100 = new Die(100);

		this.adulthood.allowedFactions = this.allowedFactions;

		ArrayList<PathEvent> fallEvent = null;
		if (this.childhood.isAsync){ // you can only get this during childhood if you are Lost
			this.age = "Lost Generation";
			this.getPreFallEvent = false;
			// You also don't get a Fall event
		} else if (this.childhood.goDirectlyToAdult){
			this.age = "Created after the fall";
			this.getPreFallEvent = false;
		} else{
			this.getAge();
			fallEvent = new ArrayList<PathEvent>();
			// need to do this now because it changes .getPostFallPath()
			fallEvent.addAll(PathEvent.getFallEvent(d100.Roll()));
			this.adulthood.fallEvent = fallEvent.get(0);
		}
		
		// get PreFall stuff, but only if you are old enough		
		if (this.getPreFallEvent){
			this.adulthood.getAdultPrefallPath(this.childhood.pathForward);
			this.eventList.addAll(PathEvent.getPreFallEvent(d100.Roll()));	
			this.adulthood.getAdultPostfallPath(this.childhood.isAGI, 
					this.childhood.isUplift, this.childhood.isAsync);
		} else {
			this.adulthood.getAdultPostfallPath(this.childhood.pathForward, 
					this.childhood.isAGI, this.childhood.isUplift, this.childhood.isAsync);
		}
		
		// This is down here because I want the order to be right, even though this got created earlier
		if (this.adulthood.fallEvent != null){
			this.eventList.addAll(fallEvent);
		}
			
		this.eventList.addAll(PathEvent.getPostFallEvent(d100.Roll()));
		
		// Adds up all the packages rolled so far
		this.packages.addAll(this.adulthood.getPackages());

	}
	
	/**
	 * Sets the age of the character - Table 5
	 */
	protected void getAge(){
		Die d100 = new Die(100);
		int roll = d100.Roll();
		
		if (roll <= 20){
			this.getPreFallEvent = false; // you are too young
			this.age = "20s";
		} else if (roll <= 50){
			this.age = "30s";
		} else if (roll <= 70){
			this.age = "40s";
		} else if (roll <= 80){
			this.age = "50s";
		} else if (roll <= 85){
			this.age = "60s";
		} else if (roll <= 90){
			this.age = "70s";
		} else if (roll <= 94){
			this.age = "80s";
		} else if (roll <= 98){
			this.age = "90s";
			this.getAdvancedAgeEffects(1);
		} else {
			this.age = "Over 100";
			this.getAdvancedAgeEffects(0);
		}
	}
	
	/**
	 * Rolls for the over 100 age effects - Table 5
	 * @param modifier bonus to rolls (for if you are in your 90s)
	 */
	private void getAdvancedAgeEffects(int modifier){
		Die d10 = new Die (10);
		int count1 = 0, count3 = 0, count4 = 0;
		int roll = d10.Roll() + modifier;
		
		while(roll <= 5 && count1 <=3 && count3 < 1 && count4 <= 3){
			if (roll == 1) {
				count1++;
				this.todo.add("Lose -10 SOM, REF, or COO (your choice)");
			} else if (roll == 2) {
				this.todo.add("Lose -5 SOM, REF, or COO (your choice)");
			} else if (roll == 3){
				count3++;
				this.character.addTrait("Immortality Blues");
				//this.todo.add("Gain Immortality Blues trait");
			} else {
				count4++;
				this.todo.add("Gain +5 COG, INT, SAV, or WIL (your choice)");
			}
			roll = d10.Roll() + modifier;
		}
	}

	/**
	 * Prints the character to a file
	 * @param filename name of the file you want to print to
	 */
	public void printToFile(String filename){
		PrintStream writer;
		try {
			writer = new PrintStream(filename, "UTF-8");
			this.printCharacter(writer);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Main for testing the Life Path generator.
	 * This is command line based. Will add a GUI maybe later.
	 * @param args
	 */
	public static void main(String[] args) {
		LifePath path = new LifePath("Test Character");
		
		path.generateLifePath();		
		path.printCharacter(System.out);
	}


}
