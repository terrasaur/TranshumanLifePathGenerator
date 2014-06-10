package lifepath;

import character.EPCharacter;
import character.Skill;
import dice.Die;

/**
 * This is one of the four different package types. Everything is hard-coded.
 * I know, I know... I should just learn databases.
 * 
 * @author terrasaur
 */
public class BackgroundPackage extends LifePathPackage {
	List packageName;
	
	public BackgroundPackage(List name, Integer cost) {
		super(name.text, cost);
		this.pkgType = Type.Background;
		this.packageName = name;
		if (this.ppCost != 1 && this.ppCost != 3 && this.ppCost != 5){
			System.out.println("Error: You have the wrong cost for a package: " + this.ppCost);
			this.ppCost = 1;
		}
		this.getPackageContents();
	}
	
	public BackgroundPackage(String name, Integer cost) {
		super(name, cost);
		this.packageName = getPackageByLabel(name);
		this.pkgType = Type.Background;			
		if (this.ppCost != 1 && this.ppCost != 3 && this.ppCost != 5){
			System.out.println("Error: You have the wrong cost for a package: " + this.ppCost);
			this.ppCost = 1;
		}
		this.getPackageContents();
	}
	
	/**
	 * List of all factions. Each package has a list enum containing its list objects.
	 * When creating a package, the class will look up the package in this list.
	 * @author terrasaur
	 */
	public enum List {
		ColonistCommandStaff   ("Colonist: Command Staff"),
		ColonistFlightStaff    ("Colonist: Flight Staff"),
		ColonistSecurityStaff  ("Colonist: Security Staff"),
		ColonistScienceStaff   ("Colonist: Science Staff"),
		ColonistTechStaff      ("Colonist: Tech Staff"),
		Drifter                ("Drifter"),
		EarthSurvivor          ("Earth Survivor"),
		FallEvacueeEnclaver    ("Fall Evacuee: Enclaver"),
		FallEvacueeUnderclass  ("Fall Evacuee: Underclass"),
		HypereliteMedia        ("Hyperelite: Media Personality"),
		HypereliteScion        ("Hyperelite: Scion"),
		Indenture              ("Indenture"),
		InfolifeEmergentUplift ("Infolife: Emergent Uplift"),
		InfolifeHumanitiesAGI  ("Infolife: Humanities AGI"),
		InfolifeMachineAGI     ("Infolife: Machine AGI"),
		InfolifeResearchAGI    ("Infolife: Research AGI"),
		IsolateSeparatist      ("Isolate: Separatist"),
		IsolateSurvivalist     ("Isolate: Survivalist"),
		LostDisturbedChild     ("Lost: Disturbed Child"),
		LostMaskedNormalcy     ("Lost: Masked Normalcy"),
		OriginalScum           ("Original Scum"),
		ReinstantiatedCivilian ("Re-instantiated: Civilian Casualty"),
		ReinstantiatedInfomorph("Re-instantiated: Infomorph"),
		ReinstantiatedMilitary ("Re-instantiated: Military Casualty"),
		StreetRat              ("Street Rat"),
		UpliftEscapee          ("Uplift: Escapee"),
		UpliftFeral            ("Uplift: Feral"),
		UpliftStandardSpecimen ("Uplift: Standard Specimen");
	
		
		String text;
		
		private List(String s){
			this.text = s;
		}
		
		public String toString(){
			return text;
		}
		
	
		public Boolean equals(String s){
			if (s == this.text)
				return true;
			return false;
		}

	}

	@Override
	public String prettyPrint() {
		String ret = this.packageName.text + " (" + this.ppCost + " pp)\n" + super.getLists();
		return ret.substring(0, ret.length()-1);
	}


	/**
	 * Loops over the list of packages, and returns the proper list
	 * enum given a string of the pretty-print name.
	 * @param label
	 * @return
	 */
	public static List getPackageByLabel(String label) {
		for (List l : List.values()){
			if (l.equals(label))
				return l;
		}
		return null;
	}
	
	/**
	 * Applies a package to a character. Overridden to add the background package 
	 * to the character's faction. If you have multiple faction packages, this will 
	 * just append your backgrounds onto one another. 
	 */
	protected void applyToCharacter(EPCharacter c) {
		super.applyToCharacter(c);
		c.setBackground(this.packageName.text);
	}
	
	/**
	 * This instantiates the package with all of its associated stuff.
	 * This is a huge list. Just... hope it's right.
	 */
	private void getPackageContents() {
		
		// All background packages give +1 moxie
		this.bonusList.add(new StatBonus("MOX", 1)); 
		Die d10 = new Die(10);
		
		//this\.choiceList\.add\("Add one (\w+) skill of your choice at (\d\d)"\);
		//this\.skillList\.add\(new Skill\("$1", "Choice", $2\)\);
		switch (this.packageName){
		case ColonistCommandStaff:
			this.suggestedMotivations.add("+Hard Work");
			this.suggestedMotivations.add("+Leadership");
			this.suggestedMotivations.add("+Survival");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Persuasion", 15));
				this.skillList.add(new Skill("Profession", "Administration", 30));
				this.skillList.add(new Skill("Protocol", 40));
			} else if (this.ppCost == 3){
				this.skillList.add(new Skill("Persuasion", 40));
				this.skillList.add(new Skill("Profession", "Administration", 40));
				this.skillList.add(new Skill("Protocol", 50));
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 35));
					else
						this.skillList.add(new Skill("Free Running", 35));
				else
					this.choiceList.add("Add Free Fall or Free Running at 35");
				this.skillList.add(new Skill("Interest", "Choice", 20)); 
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Pilot", "Choice", 30)); 
			} else {
				this.bonusList.add(new StatBonus("SAV", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Art", "Choice", 40));
				this.skillList.add(new Skill("Beam Weapons", 30));
				this.skillList.add(new Skill("Fray", 25));
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 35));
					else
						this.skillList.add(new Skill("Free Running", 35));
				else
					this.choiceList.add("Add Free Fall or Free Running at 35");
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Intimidation", 30)); 
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Persuasion", 40)); 
				this.skillList.add(new Skill("Pilot", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Administration", 40)); 
				this.skillList.add(new Skill("Protocol", 50));
				}
			break;
		case ColonistFlightStaff:
			this.suggestedMotivations.add("+Exploration"); 
			this.suggestedMotivations.add("+Personal Career"); 
			this.suggestedMotivations.add("+Thrill Seeking");

			if (this.ppCost == 1){
				this.skillList.add(new Skill("Pilot", "Spacecraft", 40));
				this.skillList.add(new Skill("Profession", "Flight Crew", 30));
			} else if (this.ppCost == 3){
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Academics", "Astrophysics", 30));
					else
						this.skillList.add(new Skill("Academics", "Engineering", 30));
				else
					this.choiceList.add("Add Academics: Astrophysics or Academics: Engineering at 30");
				this.skillList.add(new Skill("Free Fall", 40));
				this.skillList.add(new Skill("Hardware", "Aerospace", 35)); 
				this.skillList.add(new Skill("Interest", "Choice", 20)); 
				this.skillList.add(new Skill("Navigation", 40));
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Pilot", "Spacecraft", 50));
				this.skillList.add(new Skill("Profession", "Flight Crew", 40));
			} else {
				this.bonusList.add(new StatBonus("REF", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Academics", "Astrophysics", 40));
					else
						this.skillList.add(new Skill("Academics", "Engineering", 40));
				else
					this.choiceList.add("Add Academics: Astrophysics or Academics: Engineering at 40");
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Free Fall", 40));
				this.skillList.add(new Skill("Gunnery", 30)); 
				this.skillList.add(new Skill("Hardware", "Aerospace", 40));
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Navigation", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 30));
				this.skillList.add(new Skill("Networking", "Choice", 30));
				this.skillList.add(new Skill("Pilot", "Spacecraft", 50));
				this.skillList.add(new Skill("Profession", "Flight Crew", 50));
			}
			break;
		case ColonistScienceStaff:
			this.suggestedMotivations.add("+Hard Work"); 
			this.suggestedMotivations.add("+Personal Career"); 
			this.suggestedMotivations.add("+Science!");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 15));
					else
						this.skillList.add(new Skill("Free Running", 15));
				else
					this.choiceList.add("Add Free Fall or Free Running at 15");
			this.skillList.add(new Skill("Investigation", 30));
				
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 50)); 
				this.choiceList.add("Add Free Fall or Free Running at 30");
				this.skillList.add(new Skill("Interest", "Choice", 15));
				this.skillList.add(new Skill("Interfacing", 40)); 
				this.skillList.add(new Skill("Investigation", 40));
				this.skillList.add(new Skill("Networking", "Scientists", 40));
				this.skillList.add(new Skill("Profession", "Lab Technician", 30));
				this.skillList.add(new Skill("Research", 40));
			} else {
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Fray", 25)); 
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 30));
					else
						this.skillList.add(new Skill("Free Running", 30));
				else
					this.choiceList.add("Add Free Fall or Free Running at 30");
				this.skillList.add(new Skill("Hardware", "Choice", 20));
				
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Interfacing", 40)); 
				this.skillList.add(new Skill("Investigation", 50)); 
				this.skillList.add(new Skill("Networking", "Scientists", 40));
				this.skillList.add(new Skill("Profession", "Lab Technician", 30));
				this.skillList.add(new Skill("Programming", 35)); 
				this.skillList.add(new Skill("Research", 40));
			}
			break;
		case ColonistSecurityStaff:
			this.suggestedMotivations.add("+Law and Order"); 
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("–Criminals");
			this.suggestedMotivations.add("–Autonomists");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Beam Weapons", 40));
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 15));
					else
						this.skillList.add(new Skill("Free Running", 15));
				else
					this.choiceList.add("Add Free Fall or Free Running at 15");
				this.skillList.add(new Skill("Profession", "Security Ops", 30));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Beam Weapons", 50)); 
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 40));
					else
						this.skillList.add(new Skill("Free Running", 40));
				else
					this.choiceList.add("Add Free Fall or Free Running at 40"); 
				this.skillList.add(new Skill("Clubs", 35)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Intimidation", 40));
				this.skillList.add(new Skill("Language", "Choice", 20));
				this.skillList.add(new Skill("Networking", "Choice", 30));
				this.skillList.add(new Skill("Profession", "Security Ops", 40));
			} else {
				this.bonusList.add(new StatBonus("SOM", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));

				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Beam Weapons", 50)); 
				this.skillList.add(new Skill("Fray", 25)); 
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 40));
					else
						this.skillList.add(new Skill("Free Running", 40));
				else
					this.choiceList.add("Add Free Fall or Free Running at 40");
				this.skillList.add(new Skill("Clubs", 35)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Intimidation", 40)); 
				this.skillList.add(new Skill("Investigation", 20)); 
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Security Ops", 50));
				this.skillList.add(new Skill("Unarmed Combat", 40));
			}
			break;
		case ColonistTechStaff:
			this.suggestedMotivations.add("+Hard Work"); 
			this.suggestedMotivations.add("+Problem Solving");
			this.suggestedMotivations.add("+Survival");
			if (this.ppCost == 1){
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 15));
					else
						this.skillList.add(new Skill("Free Running", 15));
				else
					this.choiceList.add("Add Free Fall or Free Running at 15");
				this.skillList.add(new Skill("Hardware", "Choice", 40));
				this.skillList.add(new Skill("Profession", "Choice", 30));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 30));	
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 30));
					else
						this.skillList.add(new Skill("Free Running", 30));
				else
					this.choiceList.add("Add Free Fall or Free Running at 30");
				this.skillList.add(new Skill("Hardware", "Choice", 50));
				this.skillList.add(new Skill("Interest", "Choice", 20));
				this.skillList.add(new Skill("Interfacing", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 40));
				this.skillList.add(new Skill("Programming", 40));
				this.skillList.add(new Skill("Scrounging", 35));
			} else {
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Fray", 20)); 
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 30));
					else
						this.skillList.add(new Skill("Free Running", 30));
				else
					this.choiceList.add("Add Free Fall or Free Running at 30");
				this.skillList.add(new Skill("Hardware", "Choice", 50));
				this.skillList.add(new Skill("Hardware", "Choice", 40));
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Interfacing", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Pilot", "Choice", 25));
				this.skillList.add(new Skill("Profession", "Choice", 50));
				this.skillList.add(new Skill("Programming", 40)); 
				this.skillList.add(new Skill("Scrounging", 35));
			}
			break;
		case Drifter:
			this.suggestedMotivations.add("+Exploration");
			this.suggestedMotivations.add("+Pragmatism"); 
			this.suggestedMotivations.add("+Survival");
			this.suggestedMotivations.add("+Hard Work");
			if (this.ppCost == 1){
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 15));
					else
						this.skillList.add(new Skill("Free Running", 15));
				else
					this.choiceList.add("Add Free Fall or Free Running at 15"); 
				this.skillList.add(new Skill("Profession", "Choice", 30));
				this.skillList.add(new Skill("Scrounging", 40));
			} else if (this.ppCost == 3) {
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 40));
					else
						this.skillList.add(new Skill("Free Running", 40));
				else
					this.choiceList.add("Add Free Fall or Free Running at 40");
				this.skillList.add(new Skill("Hardware", "Choice", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 20)); 
				this.skillList.add(new Skill("Kinetic Weapons", 20)); 
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Navigation", 20)); 
				this.skillList.add(new Skill("Networking", "Choice", 35)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Scrounging", 50));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("INT", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Art", "Choice", 40));
				this.skillList.add(new Skill("Fray", 25)); 
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Free Fall", 40));
					else
						this.skillList.add(new Skill("Free Running", 40));
				else
					this.choiceList.add("Add Free Fall or Free Running at 40");
				this.skillList.add(new Skill("Hardware", "Choice", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Kinetic	Weapons", 20)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Navigation", 20));
				this.skillList.add(new Skill("Networking", "Choice", 30));
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Protocol", 20)); 
				this.skillList.add(new Skill("Scrounging", 50));
			}
			break;
		case EarthSurvivor:
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("-TITANs"); 
			this.suggestedMotivations.add("+Reclaiming Earth");
			this.suggestedMotivations.add("-Reclaiming Earth");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Freerunning", 15)); 
				this.skillList.add(new Skill("Profession", "Post-Apocalyptic Survival", 30));
				this.skillList.add(new Skill("Scrounging", 40));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Freerunning", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 20));
				this.skillList.add(new Skill("Infiltration", 40)); 
				this.skillList.add(new Skill("Kinetic Weapons", 35)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 20));
				this.skillList.add(new Skill("Profession", "Post-Apocalyptic Survival", 40)); 
				this.skillList.add(new Skill("Scrounging", 50));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("WIL", 5));
				this.skillList.add(new Skill("Animal Handling", 20)); 
				this.skillList.add(new Skill("Demolitions", 20)); 
				this.skillList.add(new Skill("Fray", 25)); 
				this.skillList.add(new Skill("Freerunning", 30));
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Infiltration", 40)); 
				this.skillList.add(new Skill("Kinetic Weapons", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 25));
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Profession", "Post-Apocalyptic Survival", 40));
				this.skillList.add(new Skill("Scrounging", 50));
				this.skillList.add(new Skill("Seeker Weapons", 30));
				this.traitList.add("Traits: Neural Damage");
			}
			break;
		case FallEvacueeEnclaver:
			this.suggestedMotivations.add("+Personal Career"); 
			this.suggestedMotivations.add("+Reclaiming Earth"); 
			this.suggestedMotivations.add("+Wealth");
			this.suggestedMotivations.add("+Survival");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.skillList.add(new Skill("Protocol", 25));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 20));
				this.skillList.add(new Skill("Interfacing", 35)); 
				this.skillList.add(new Skill("Networking", "Choice", 50)); 
				this.skillList.add(new Skill("Persuasion", 40)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 30));
				this.skillList.add(new Skill("Profession", "Choice", 40));
				this.skillList.add(new Skill("Protocol", 30));
			} else {
				this.bonusList.add(new StatBonus("SAV", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Art", "Choice", 30));
				this.skillList.add(new Skill("Beam Weapons", 20)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Interfacing", 35)); 
				this.skillList.add(new Skill("Kinesics", 25)); 
				this.skillList.add(new Skill("Networking", "Choice", 50));
				this.skillList.add(new Skill("Networking", "Choice", 20)); 
				this.skillList.add(new Skill("Persuasion", 40));
				this.skillList.add(new Skill("Pilot", "Groundcraft", 30));
				this.skillList.add(new Skill("Profession", "Choice", 40));
				this.skillList.add(new Skill("Protocol", 40));
			}
			break;
		case FallEvacueeUnderclass:
			this.suggestedMotivations.add("+Personal Development"); 
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("-Hypercapitalism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Networking", "Choice", 40));
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 15));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Blades", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 20));
				this.skillList.add(new Skill("Deception", 30));
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Networking", "Choice", 50));
				this.skillList.add(new Skill("Persuasion", 40)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 25)); 
				this.skillList.add(new Skill("Profession", "Choice", 40));
				this.skillList.add(new Skill("Unarmed Combat", 20));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("WIL", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Blades", 30)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Infiltration", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Deception", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 40));
				this.skillList.add(new Skill("Networking", "Choice", 50));
				this.skillList.add(new Skill("Persuasion", 40)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 30));
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Unarmed Combat", 35));
			}
			break;
		case HypereliteMedia:
			this.suggestedMotivations.add("+Artistic Expression"); 
			this.suggestedMotivations.add("+Fame"); 
			this.suggestedMotivations.add("+Personal Career");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Art", "Choice", 40));
				this.skillList.add(new Skill("Networking", "Media", 40));
				this.creditsMod = 5000;
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Art", "Choice", 40));
				this.skillList.add(new Skill("Disguise", 25)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Networking", "Media", 50)); 
				this.skillList.add(new Skill("Persuasion", 30));
				this.skillList.add(new Skill("Profession", "Choice", 20)); 
				this.skillList.add(new Skill("Protocol", 30));
				this.creditsMod = 30000;
			} else {
				this.bonusList.add(new StatBonus("SAV", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Art", "Choice", 40)); 
				this.skillList.add(new Skill("Disguise", 25)); 
				this.skillList.add(new Skill("Fray", 15)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Networking", "Media", 50));
				this.skillList.add(new Skill("Networking", "Choice", 20));
				this.skillList.add(new Skill("Persuasion", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Protocol", 50));
				this.creditsMod = 60000;
			}
			break;
		case HypereliteScion:
			this.suggestedMotivations.add("+Family"); 
			this.suggestedMotivations.add("+Hypercapitalism"); 
			this.suggestedMotivations.add("+Wealth");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Kinesics", 30)); 
				this.skillList.add(new Skill("Protocol", 20));
				this.creditsMod = 5000;
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Art", "Choice", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 20)); 
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Kinesics", 50)); 
				this.skillList.add(new Skill("Networking", "Hypercorps", 35));
				this.skillList.add(new Skill("Persuasion", 30)); 
				this.skillList.add(new Skill("Protocol", 30));
				this.creditsMod = 20000;
			} else {
				this.bonusList.add(new StatBonus("SAV", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Art", "Choice", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Intimidation", 20)); 
				this.skillList.add(new Skill("Kinesics", 50)); 
				this.skillList.add(new Skill("Kinetic Weapons", 20));
				this.skillList.add(new Skill("Networking", "Hypercorps", 40));
				this.skillList.add(new Skill("Persuasion", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.skillList.add(new Skill("Protocol", 40));
				this.creditsMod = 50000;
				this.traitList.add("Patron");
			}
			break;
		case Indenture:
			this.suggestedMotivations.add("+Hard Work"); 
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("-Hypercorps");
			this.suggestedMotivations.add("-Indentured Service"); 
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Hardware", "Choice", 40));
				this.skillList.add(new Skill("Language", "Choice", 15)); 
				this.skillList.add(new Skill("Profession", "Choice", 30));  
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Blades", 30)); 
				this.skillList.add(new Skill("Demolitions", 30)); 
				this.skillList.add(new Skill("Free Fall", 40)); 
				this.skillList.add(new Skill("Hardware", "Choice", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 20));
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Scrounging", 45));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("SOM", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Blades", 30)); 
				this.skillList.add(new Skill("Demolitions", 30)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Free Fall", 40)); 
				this.skillList.add(new Skill("Hardware", "Choice", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Criminal", 20)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Scrounging", 45));
			}
			break;
		case InfolifeEmergentUplift:
			this.suggestedMotivations.add("+AGI Rights"); 
			this.suggestedMotivations.add("+Mercurial Cause"); 
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Infosec", 25)); 
				this.skillList.add(new Skill("Interfacing", 40));
				this.skillList.add(new Skill("Programming", 30));
				this.traitList.add("Anomalous Mind");
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Infosec", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Interfacing", 50)); 
				this.skillList.add(new Skill("Networking", "Choice", 30));
				this.skillList.add(new Skill("Profession", "Choice", 20)); 
				this.skillList.add(new Skill("Programming", 40)); 
				this.skillList.add(new Skill("Research", 30));
				this.traitList.add("Anomalous Mind");
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.choiceList.add("Add any two skills at 30");
				this.choiceList.add("Add any two Academics skills of your choice at 40"); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Infosec", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 50)); 
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40));
				this.skillList.add(new Skill("Programming", 40)); 
				this.skillList.add(new Skill("Research", 35));
				this.traitList.add("Anomalous Mind");
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			}
			break;
		case InfolifeHumanitiesAGI:
			this.suggestedMotivations.add("+AGI Rights"); 
			this.suggestedMotivations.add("+Personal Development"); 
			this.suggestedMotivations.add("+Philanthropy");
			if (this.ppCost == 1){
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Academics", "Psychology", 30));
					else
						this.skillList.add(new Skill("Academics", "Sociology", 30));
				else
					this.choiceList.add("Add Academics: Psychology or Academics: Sociology at 30"); 				
				this.skillList.add(new Skill("Interfacing", 40)); 
				this.skillList.add(new Skill("Kinesics", 35));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			} else if (this.ppCost == 3) {
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Academics", "Psychology", 40));
					else
						this.skillList.add(new Skill("Academics", "Sociology", 40));
				else
					this.choiceList.add("Add Academics: Psychology or Academics: Sociology at 40"); 
				this.skillList.add(new Skill("Art", "Digital Art", 20)); 
				this.skillList.add(new Skill("Impersonation", 25)); 
				this.skillList.add(new Skill("Interfacing", 50)); 
				this.skillList.add(new Skill("Kinesics", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 30));
				this.skillList.add(new Skill("Persuasion", 40)); 
				this.skillList.add(new Skill("Profession", "Psychotherapy", 30));
				this.skillList.add(new Skill("Research", 30));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			} else {
				this.bonusList.add(new StatBonus("SAV", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Academics", "Psychology", 40));
					else
						this.skillList.add(new Skill("Academics", "Sociology", 40));
				else
					this.choiceList.add("Add Academics: Psychology or Academics: Sociology at 40"); 
				this.skillList.add(new Skill("Art", "Digital Art", 40));
				this.skillList.add(new Skill("Impersonation", 30)); 
				this.skillList.add(new Skill("Infosec", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 50)); 
				this.skillList.add(new Skill("Kinesics", 50)); 
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Persuasion", 40));
				this.skillList.add(new Skill("Profession", "Psychotherapy", 30)); 
				this.skillList.add(new Skill("Protocol", 30)); 
				this.skillList.add(new Skill("Research", 30));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			}
			break;
		case InfolifeMachineAGI:
			this.suggestedMotivations.add("+AGI Rights"); 
			this.suggestedMotivations.add("+Thrill Seeking"); 
			this.suggestedMotivations.add("+Sousveillance");
			this.suggestedMotivations.add("-Disorganization"); 
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Interfacing", 40));
				this.skillList.add(new Skill("Programming", 35));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Art", "Choice", 20));
				this.skillList.add(new Skill("Hardware", "Electronics", 40)); 
				this.skillList.add(new Skill("Hardware", "Robotics", 20)); 
				this.skillList.add(new Skill("Infosec", 35)); 
				this.skillList.add(new Skill("Interfacing", 50)); 
				this.skillList.add(new Skill("Pilot", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.skillList.add(new Skill("Programming", 40));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			} else {
				this.bonusList.add(new StatBonus("REF", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.choiceList.add("Add any two Academics skills of your choice at 40"); 
				this.skillList.add(new Skill("Art", "Choice", 30)); 
				this.skillList.add(new Skill("Flight", 30));
				this.skillList.add(new Skill("Hardware", "Electronics", 40)); 
				this.skillList.add(new Skill("Hardware", "Robotics", 30)); 
				this.skillList.add(new Skill("Infosec", 40)); 
				this.skillList.add(new Skill("Interfacing", 50)); 
				this.skillList.add(new Skill("Pilot", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Programming", 50)); 
				this.skillList.add(new Skill("Research", 30));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			}
			break;
		case InfolifeResearchAGI:
			this.suggestedMotivations.add("+AGI Rights"); 
			this.suggestedMotivations.add("+Education"); 
			this.suggestedMotivations.add("+Research");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Interfacing", 35)); 
				this.skillList.add(new Skill("Research", 40));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 40)); 
				this.skillList.add(new Skill("Investigation", 30)); 
				this.skillList.add(new Skill("Networking", "Scientists", 25)); 
				this.skillList.add(new Skill("Profession", "Choice", 20)); 
				this.skillList.add(new Skill("Programming", 30)); 
				this.skillList.add(new Skill("Research", 50));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			} else {
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.choiceList.add("Add any two Academics skills of your choice at 40");  
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Infosec", 30)); 
				this.skillList.add(new Skill("Interfacing", 40));
				this.skillList.add(new Skill("Investigation", 40)); 
				this.skillList.add(new Skill("Networking", "Scientists", 30)); 
				this.skillList.add(new Skill("Pilot", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Programming", 40));
				this.skillList.add(new Skill("Research", 50));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("Social Stigma (AGI)");
			}
			break;
		case IsolateSeparatist:
			this.suggestedMotivations.add("+Artistic Expression"); 
			this.suggestedMotivations.add("+Bioconservatism"); 
			this.suggestedMotivations.add("+Religion");
			this.suggestedMotivations.add("+Research");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Free Fall", 15)); 
				this.skillList.add(new Skill("Profession", "Choice", 30));
				this.skillList.add(new Skill("Scrounging", 40));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Animal Handling", 25)); 
				this.skillList.add(new Skill("Art", "Choice", 40)); 
				this.skillList.add(new Skill("Free Fall", 30));
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Medicine", "Paramedic", 30));
				this.skillList.add(new Skill("Pilot", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 50));
				this.skillList.add(new Skill("Scrounging", 40));
			} else {
				this.choiceList.add("+5 to INT or WIL");
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Animal Handling", 25)); 
				this.skillList.add(new Skill("Art", "Choice", 40)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Free Fall", 40)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Interfacing", 20)); 
				this.skillList.add(new Skill("Medicine", "Paramedic", 30)); 
				this.skillList.add(new Skill("Pilot", "Choice", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 50)); 
				this.skillList.add(new Skill("Programming", 30));
				this.skillList.add(new Skill("Scrounging", 50));
				this.traitList.add("Real World Naiveté");
			}
			break;
		case IsolateSurvivalist:
			this.suggestedMotivations.add("+Bioconservatism"); 
			this.suggestedMotivations.add("+Religion"); 
			this.suggestedMotivations.add("+Survival");
			this.suggestedMotivations.add("-Autonomists");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Free Fall", 15)); 
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.skillList.add(new Skill("Kinetic Weapons", 40));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 20)); 
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Kinetic Weapons", 50)); 
				this.skillList.add(new Skill("Medicine", "Paramedic", 30)); 
				this.skillList.add(new Skill("Pilot", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Seeker Weapons", 15));
			} else {
				this.choiceList.add("+5 to INT or WIL");
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Animal Handling", 20));
				this.skillList.add(new Skill("Demolitions", 20)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Kinetic Weapons", 50));
				this.skillList.add(new Skill("Medicine", "Paramedic", 30)); 
				this.skillList.add(new Skill("Navigation", 20)); 
				this.skillList.add(new Skill("Pilot", "Choice", 30)); 
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Seeker Weapons", 25));
			}
			break;
		case LostDisturbedChild:
			this.suggestedMotivations.add("+Neurodiversity"); 
			this.suggestedMotivations.add("+Sadism"); 
			this.suggestedMotivations.add("+Vengeance");
			this.suggestedMotivations.add("–Research");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Control", 35)); 
				this.skillList.add(new Skill("Free Fall", 15));
				this.traitList.add("Psi (Level 2)");
				this.bonusList.add(new StatBonus("Mental Disorder", 3));				
				this.traitList.add("Real World Naiveté");
				this.traitList.add("On the Run");
				this.bonusList.add(new StatBonus("psi-gamma", 2));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Blades", 15));
				this.skillList.add(new Skill("Control", 50)); 
				this.skillList.add(new Skill("Deception", 40)); 
				this.skillList.add(new Skill("Fray", 15)); 
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Infiltration", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 20)); 
				this.traitList.add("Psi (Level 2)"); 
				this.bonusList.add(new StatBonus("Mental Disorder", 3));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("On the Run");				
				this.bonusList.add(new StatBonus("psi-chi", 1));
				this.bonusList.add(new StatBonus("psi-gamma", 3));
			} else {
				this.bonusList.add(new StatBonus("WIL", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Blades", 20)); 
				this.skillList.add(new Skill("Control", 50)); 
				this.skillList.add(new Skill("Deception", 40));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Infiltration", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Psi Assault", 40)); 
				this.skillList.add(new Skill("Unarmed Combat", 20));
				this.traitList.add("Psi (Level 2)"); 
				this.bonusList.add(new StatBonus("Mental Disorder", 3));
				this.traitList.add("Real World Naiveté");
				this.traitList.add("On the Run");
				this.bonusList.add(new StatBonus("psi-chi", 2));
				this.bonusList.add(new StatBonus("psi-gamma", 5));
			}
			break;
		case LostMaskedNormalcy:
			this.suggestedMotivations.add("+Self Control"); 
			this.suggestedMotivations.add("+Privacy"); 
			this.suggestedMotivations.add("+Acceptance");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Persuasion", 35));
				this.traitList.add("Psi (Level 1)");
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi-chi", 2));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Free Fall", 30));
				this.skillList.add(new Skill("Impersonation", 40)); 
				this.skillList.add(new Skill("Kinesics", 45)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Persuasion", 50));
				this.skillList.add(new Skill("Profession", "Choice", 20)); 
				this.traitList.add("Psi (Level 1)");
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi-chi", 4));
			} else {
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Impersonation", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Kinesics", 45));
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Persuasion", 50)); 
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.skillList.add(new Skill("Protocol", 30)); 
				this.skillList.add(new Skill("Sense", 50)); 
				this.traitList.add("Psi (Level 2)");
				this.bonusList.add(new StatBonus("Mental Disorder", 3));
				this.traitList.add("On the Run");
				this.bonusList.add(new StatBonus("psi-chi", 4));
				this.bonusList.add(new StatBonus("psi-gamma", 2));
			}
			break;
		case OriginalScum:
			this.suggestedMotivations.add("+Morphological Freedom"); 
			this.suggestedMotivations.add("+Hedonism"); 
			this.suggestedMotivations.add("+Anarchism");
			this.suggestedMotivations.add("+Individualism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Art", "Choice", 30)); 
				this.skillList.add(new Skill("Free Fall", 15));
				this.skillList.add(new Skill("Medicine", "Biosculpting", 40)); 
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Art", "Choice", 40)); 
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 20)); 
				this.skillList.add(new Skill("Medicine", "Biosculpting", 50));
				this.skillList.add(new Skill("Networking", "Choice", 35)); 
				this.skillList.add(new Skill("Persuasion", 40));
				this.skillList.add(new Skill("Psychosurgery", 40));
			} else {
				this.bonusList.add(new StatBonus("WIL", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Art", "Choice", 40)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Kinesics", 20));
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Medicine", "Biosculpting", 50)); 
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 20)); 
				this.skillList.add(new Skill("Persuasion", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 30));
				this.skillList.add(new Skill("Psychosurgery", 40));
				this.skillList.add(new Skill("Spray Weapons", 20));
			}
			break;
		case ReinstantiatedCivilian:
			this.suggestedMotivations.add("+Reclaiming Earth"); 
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("–TITAN Tech");
			if (this.ppCost == 1){
				this.choiceList.add("Add any two non-Psi skills of your choice at 40");
				this.skillList.add(new Skill("Interfacing", 15)); 
				this.skillList.add(new Skill("Profession", "Choice", 30));
			} else if (this.ppCost == 3) {
				this.choiceList.add("Add any two non-Psi skills of your choice at 50"); 
				this.skillList.add(new Skill("Academics", "Choice", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Interfacing", 45)); 
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 30));
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Research", 30));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("INT", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.choiceList.add("Add any two non-Psi skills of your choice at 50");
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Freerunning", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 20)); 
				this.skillList.add(new Skill("Interfacing", 45)); 
				this.skillList.add(new Skill("Kinesics", 20)); 
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Research", 30));
				this.traitList.add("Edited Memories");
			}
			break;
		case ReinstantiatedInfomorph:
			this.suggestedMotivations.add("+Reclaiming Earth"); 
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("–Virtual Reality");
			this.suggestedMotivations.add("+Virtual Reality");
			this.suggestedMotivations.add("+TITANs");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Interfacing", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 30));
				this.skillList.add(new Skill("Programming", 15));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Interfacing", 50)); 
				this.skillList.add(new Skill("Networking", "Choice", 40));
				this.skillList.add(new Skill("Pilot", "Groundcraft", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40));
				this.skillList.add(new Skill("Programming", 45)); 
				this.skillList.add(new Skill("Research", 30));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.choiceList.add("Add any one non-Psi skill of your choice at 40");
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Fray", 10)); 
				this.skillList.add(new Skill("Infosec", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 50));
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Programming", 45)); 
				this.skillList.add(new Skill("Research", 30));
				this.traitList.add("Edited Memories");
			}
			break;
		case ReinstantiatedMilitary:
			this.suggestedMotivations.add("+Reclaiming Earth"); 
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("–TITANs");
			if (this.ppCost == 1){
				this.choiceList.add("Add any one Combat skill of your choice at 40"); 
				this.skillList.add(new Skill("Freerunning", 15));
				this.skillList.add(new Skill("Profession", "Choice", 30));
			} else if (this.ppCost == 3) {
				this.choiceList.add("Add any one Combat skill of your choice at 50"); 
				this.skillList.add(new Skill("Academics", "Choice", 20));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Freerunning", 35)); 
				this.skillList.add(new Skill("Gunnery", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 40));
				this.skillList.add(new Skill("Unarmed Combat", 30));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("INT", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.choiceList.add("Add any two Combat skills of your choice at 50");
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Climbing", 30));
				this.skillList.add(new Skill("Fray", 25)); 
				this.skillList.add(new Skill("Freerunning", 35)); 
				this.skillList.add(new Skill("Gunnery", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 50)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 15)); 
				this.skillList.add(new Skill("Profession", "Choice", 40));
				this.skillList.add(new Skill("Unarmed Combat", 40));
				this.traitList.add("Edited Memories");
			}
			break;
		case StreetRat:
			this.suggestedMotivations.add("+Black Markets"); 
			this.suggestedMotivations.add("+Cartel/Gang/Family");
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("+Wealth");
			this.suggestedMotivations.add("-Law and Order");
			this.suggestedMotivations.add("-Police");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Networking", "Criminals", 15));
				this.skillList.add(new Skill("Profession", "Choice", 30));
				this.skillList.add(new Skill("Unarmed Combat", 40));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Clubs", 30));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Infiltration", 15)); 
				this.skillList.add(new Skill("Interest", "Choice", 20));
				this.skillList.add(new Skill("Intimidation", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Criminals", 30)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 40));
			} else {
				this.bonusList.add(new StatBonus("SOM", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Clubs", 30)); 
				this.skillList.add(new Skill("Deception", 25)); 
				this.skillList.add(new Skill("Fray", 30)); 
				this.skillList.add(new Skill("Infiltration", 25)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Intimidation", 40));
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Criminals", 40));
				this.skillList.add(new Skill("Palming", 20)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 30));
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Unarmed Combat", 40));
			}
			break;
		case UpliftEscapee:
			this.suggestedMotivations.add("+Uplift Rights"); 
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("+Privacy");
			this.suggestedMotivations.add("-Hypercorps");
			this.suggestedMotivations.add("-Uplift Slavery");
			if (this.ppCost == 1){
				this.choiceList.add("Add Climbing or other uplift-type-appropriate athletics skill at 40");
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Infiltration", 25));
				this.traitList.add("Social Stigma: (Uplift)");
			} else if (this.ppCost == 3) {
				this.choiceList.add("Add Climbing or other uplift-type-appropriate athletics skill at 45");
				this.skillList.add(new Skill("Deception", 40)); 
				this.skillList.add(new Skill("Infiltration", 50)); 
				this.skillList.add(new Skill("Impersonation", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 20)); 
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 30));
				this.traitList.add("On the Run");
				this.traitList.add("Social Stigma: (Uplift)");
			} else {
				this.bonusList.add(new StatBonus("SAV", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.choiceList.add("Add Climbing or other uplift-type-appropriate athletics skill at 50");
				this.skillList.add(new Skill("Deception", 40));
				this.skillList.add(new Skill("Fray", 25)); 
				this.skillList.add(new Skill("Infiltration", 50)); 
				this.skillList.add(new Skill("Impersonation", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 40));
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Palming", 25));
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 30));
				this.traitList.add("On the Run");
				this.traitList.add("Social Stigma: (Uplift)");
			}
			break;
		case UpliftFeral:
			this.suggestedMotivations.add("+Mercurial Cause"); 
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("+Uplift Rights");
			this.suggestedMotivations.add("-Hypercorps");
			this.suggestedMotivations.add("-Uplift Slavery");
			if (this.ppCost == 1){
				this.choiceList.add("Add Climbing or other uplift-type-appropriate athletics skill at 40");
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 35));
				this.traitList.add("Anomalous Mind");
				this.traitList.add("Heightened Instinct");
				this.traitList.add("Social Stigma: (Uplift)");
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.choiceList.add("Add Climbing or other uplift-type-appropriate athletics skill at 50"); 
				this.skillList.add(new Skill("Fray", 40)); 
				this.skillList.add(new Skill("Infiltration", 20));
				this.skillList.add(new Skill("Interest", "Choice", 45));
				this.skillList.add(new Skill("Interest", "Choice", 45)); 
				this.skillList.add(new Skill("Intimidation", 40)); 
				this.skillList.add(new Skill("Unarmed Combat", 50));
				this.traitList.add("Anomalous Mind");
				this.traitList.add("Heightened Instinct");
				this.traitList.add("Social Stigma: (Uplift)");
			} else {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("REF", 5));
				this.skillList.add(new Skill("Art", "Choice", 30)); 
				this.choiceList.add("Add Climbing or other uplift-type-appropriate athletics skill at 50");
				this.skillList.add(new Skill("Fray", 40)); 
				this.skillList.add(new Skill("Infiltration", 50));
				this.choiceList.add("Add three Interest skills of your choice at 40"); 
				this.skillList.add(new Skill("Intimidation", 50)); 
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Scrounging", 20)); 
				this.skillList.add(new Skill("Unarmed Combat", 50));
				this.traitList.add("Anomalous Mind");
				this.traitList.add("Heightened Instinct");
				this.traitList.add("Social Stigma: (Uplift)");
			}
			break;
		case UpliftStandardSpecimen:
			this.suggestedMotivations.add("+Mercurial Cause"); 
			this.suggestedMotivations.add("+Sapient Cause"); 
			this.suggestedMotivations.add("+Uplift Rights");
			this.suggestedMotivations.add("-Uplift Slavery");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.choiceList.add("Add Climbing or other uplift-type-appropriate athletics skill at 45");
				this.skillList.add(new Skill("Interfacing", 20));
				this.traitList.add("Social Stigma: (Uplift)");
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.choiceList.add("Add Climbing or other uplift-type-appropriate athletics skill at 50"); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 20));
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Intimidation", 30)); 
				this.skillList.add(new Skill("Kinesics", 35)); 
				this.skillList.add(new Skill("Networking", "Choice", 40));
				this.skillList.add(new Skill("Profession", "Choice", 30));
				this.traitList.add("Social Stigma: (Uplift)");				
			} else {
				this.bonusList.add(new StatBonus("COO", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.choiceList.add("Add Climbing or other uplift-type-appropriate athletics skill at 50");
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Intimidation", 30)); 
				this.skillList.add(new Skill("Kinesics", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Persuasion", 30));
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Protocol", 30));
				this.skillList.add(new Skill("Unarmed Combat", 20));
				this.traitList.add("Social Stigma: (Uplift)");
			}
			break;
		default:
			System.out.println("Error: Unknown background package. Wat.");
			break;
		}
		
	}


}
