package character;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.TreeMap;


/***
 * Character object for Eclipse Phase characters.
 * Characters are sort of created piecemeal. When you initialize the object,
 * you can add a name and total CP for creating the character.
 * 
 * CP support (automatically handling CP bookkeeping) is not yet implemented.
 * 
 * Steps to make a character (mostly):
 *   1. SetAllAptitudes takes an array of integers. These should be your
 *      base aptitudes. It will run updateStats() to assign base stats
 *   2. Use setMorph() to give the character a morph object. If you want
 *      the character to update morph bonuses, make sure this has its 
 *      base morph attributes fille out.
 *   3. Use addSkill() to add skills. At the end of character creation,
 *      call finalizeSkills() once to set everything properly.
 *   4. Use addEgoTrait() and addMorphTrait() to add traits
 *   5. Use modifyAptitudes() to update aptitudes once they are assigned
 * 
 * @author terrasaur
 *
 */
public class EPCharacter {
	private String name;
	private int CP;
	private int credits;
	private LinkedHashMap<String, Aptitude> aptitudes;
	private LinkedHashMap<String, Stats> stats;
	private TreeMap<String, Skill> skills;
	private String background;
	private String faction;
	private Morph morph;
	private String motivations;
	private ArrayList<String> traits;
	private ArrayList<String> morphTraits;
	private ArrayList<String> sleights;
	private LinkedHashMap<String, Integer> rep;
	private ArrayList<String> equipment;
	private int psiRating;
	
	public EPCharacter() {
		super();
		this.name = "";
		this.CP = 1000;	
		this.initFields();
	}
	
	public EPCharacter(int CP) {
		super();
		this.name = "";
		this.CP = CP;
		this.initFields();
	}
	
	public EPCharacter(String name, int CP) {
		super();
		this.name = name;
		this.CP = CP;
		this.initFields();
	}
	public EPCharacter(String name) {
		super();
		this.name = name;
		this.CP = 1000;
		this.initFields();
	}
	
	private class Stats{
		String name;
		int value;
		public Stats(String string, int i) {
			this.name = string;
			this.value = i;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof String){
				return ((String)obj).equals(this.name);
			}
			return super.equals(obj);
		}
	}
	
	/**
	 * Initializes fields. Called by constructors.
	 */
	private void initFields(){
		this.aptitudes   = new LinkedHashMap<String, Aptitude>();
		this.aptitudes.put("COG", new Aptitude("COG"));
		this.aptitudes.put("COO", new Aptitude("COO"));
		this.aptitudes.put("INT", new Aptitude("INT"));
		this.aptitudes.put("REF", new Aptitude("REF"));
		this.aptitudes.put("SAV", new Aptitude("SAV"));
		this.aptitudes.put("SOM", new Aptitude("SOM"));
		this.aptitudes.put("WIL", new Aptitude("WIL"));

		this.stats = new LinkedHashMap<String, Stats>();
		this.stats.put("MOX",  new Stats("MOX", 1));
		this.stats.put("TT",   new Stats("TT", 0));
		this.stats.put("LUC",  new Stats("LUC", 0));
		this.stats.put("IR",   new Stats("IR", 0));
		this.stats.put("WT",   new Stats("WT", 0));
		this.stats.put("DUR",  new Stats("DUR", 0));
		this.stats.put("DR",   new Stats("DR", 0));
		this.stats.put("INIT", new Stats("INIT", 0));
		this.stats.put("SPD",  new Stats("SPD", 1));
		
		this.rep = new LinkedHashMap<String, Integer>();
		this.rep.put("@-rep",  0);
		this.rep.put("c-rep",  0);
		this.rep.put("e-rep",  0);
		this.rep.put("f-rep",  0);
		this.rep.put("g-rep",  0);
		this.rep.put("i-rep",  0);
		this.rep.put("r-rep",  0);
		this.rep.put("x-rep",  0);
		
		this.skills      = new TreeMap<String, Skill>();

		this.motivations = "";
		this.traits      = new ArrayList<String>();
		this.morphTraits = new ArrayList<String>();
		this.equipment   = new ArrayList<String>();
		this.sleights    = new ArrayList<String>();
		this.psiRating   = 0;
	}

	/**
	 * Sets aptitudes. Integer array *MUST* be in proper order
	 *    COG   COO   INT   REF   SAV   SOM   WIL
	 * @param aptitudes array of aptitudes
	 */
	public void setAllAptitudes(Integer[] aptitudes) {		
		this.aptitudes.get("COG").setBaseAptitude(aptitudes[0]);
		this.aptitudes.get("COO").setBaseAptitude(aptitudes[1]);
		this.aptitudes.get("INT").setBaseAptitude(aptitudes[2]);
		this.aptitudes.get("REF").setBaseAptitude(aptitudes[3]);
		this.aptitudes.get("SAV").setBaseAptitude(aptitudes[4]);
		this.aptitudes.get("SOM").setBaseAptitude(aptitudes[5]);
		this.aptitudes.get("WIL").setBaseAptitude(aptitudes[6]);

		this.updateStats();
	}
	
	
	/**
	 * Modifies a particular stat. Always adds (so use negative numbers if
	 * you want to lower a stat).
	 * @param stat stat to modify
	 * @param mod modifier to add
	 */
	public int modifyStat(String stat, int mod){
		if (this.aptitudes.containsKey(stat)){
			int total = this.aptitudes.get(stat).base + mod;
			if (this.morph != null  ){
				this.aptitudes.get(stat).setBaseAptitude(total);	
			} 
		} else if (this.stats.containsKey(stat)){
			this.stats.get(stat).value =  this.stats.get(stat).value + mod;
		} else if (this.rep.containsKey(stat)){
			this.rep.put(stat, this.rep.get(stat) + mod); 
		} else {
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * Modifies a particular stat. Always adds (so use negative numbers if
	 * you want to lower a stat).
	 * @param stat stat to modify
	 * @param mod modifier to add
	 */
	private void setStat(String stat, int value){
		this.stats.get(stat).value = value;
	}
	

	/**
	 * Updates derived statistics based on aptitudes. Run whenever you 
	 * change an aptitude
	 */
	private void updateStats() {
		int LUC =  this.aptitudes.get("WIL").total * 2;
		this.setStat("TT", Math.round(LUC / 5));
		this.setStat("LUC", LUC);
		this.setStat("IR", LUC * 2);
		this.setStat("INIT", (int)Math.round(this.aptitudes.get("INT").total + 
				                             this.aptitudes.get("REF").total) / 5);
		
		// morph based
		if (this.morph != null){
			if (this.morph.type.category == Morph.Type.infomorph) {
				this.setStat("SPD", 3);
			} else {
				if (this.morph.traits.contains("Neurachem"))
					this.setStat("SPD", 2);
				else
					this.setStat("SPD", 1);
				this.setStat("WT", this.morph.type.woundThreshold);
				this.setStat("DUR", this.morph.type.durability); 
				if (this.morph.type.category == Morph.Type.synthmorph){
					this.setStat("DR", this.morph.type.durability * 2); 
				} else {
					this.setStat("DR", (int)Math.ceil(this.morph.type.durability * 1.5)); 
				}
			}
		}
	}
	
	/**
	 * Sets starting credits. nothing fancy here.
	 * @param credits
	 */
	public void setStartingCredits(int credits){
		this.credits = credits;
	}

	@Override
	public String toString() {
		return "Character [name=" + name + ", aptitudes=" + aptitudes + "\n stats=" 
				+ stats + "\n skills=" + skills	+ "\n background=" + background + ", "
				+ "faction=" + faction + ", morph=" + morph + ", motivations=" + motivations
				+ ", traits=" + traits + ", rep=" + rep + ", equipment=" + equipment + "]";
	}
	
	/**
	 * Pretty print! Prints the character in a nice way.
	 * @return String containing pretty-printed character.
	 */
	public String prettyPrint(){
		String ret = "";
		ret += "   Background: " + this.background + "\n";
		ret += "      Faction: " + this.faction + "\n";
		ret += "        Morph: " + this.morph.type.label + " (" + this.morph.type.category + ")\n";
		ret += "  Motivations: " + this.motivations + "\n\n";
		ret += "                        |  COG |  COO |  INT |  REF |  SAV |  SOM |  WIL |\n";
		ret += "           base         ";
		for (Aptitude a : this.aptitudes.values()){
			ret += String.format("|  %2d  ", a.base);
		}
		ret += "|\n          morph         ";
		for (Aptitude a : this.aptitudes.values()){
			if (a.morphBonus > 0)
				if (a.morphImplantBonus > 0)
					ret += String.format("|%2d(%2d)", a.morphBonus, a.morphImplantBonus);
				else
					ret += String.format("|  %2d  ", a.morphBonus);
			else 
				ret += "|      ";
		}		
		ret += "|\n          total         ";
		for (Aptitude a : this.aptitudes.values()){
			if (a.morphImplantBonus > 0)
				ret += String.format("|%2d(%2d)", a.total, a.total + a.morphImplantBonus);
			else
				ret += String.format("|  %2d  ", a.total);
		}
		ret += "|\n          ----------------------------------------------------------------\n";
		ret += "          |  MOX |  TT  |  LUC |  IR  |  WT  |  DUR |  DR  | INIT |  SPD |\n          ";
		for (Stats a : this.stats.values()){
			if (this.morph.hasStructuralEnhancement && a.name == "DUR")
				ret += String.format("|%2d(%2d)", a.value, a.value+10);
			else if (this.morph.hasStructuralEnhancement && a.name == "WT")
				ret += String.format("|%2d(%2d)", a.value, a.value+2);
			else if (this.morph.hasStructuralEnhancement && a.name == "DR")
				ret += String.format("|%2d(%2d)", a.value, a.value + 20);
			else
				ret += String.format("| %3d  ", a.value);
		}
		ret += "|\n\n";
		
		ret += "                                                        SKILLS            \n";
		ret += "                                         |  APT  |  BASE | MORPH | TOTAL |\n";
		ret += "          ----------------------------------------------------------------\n";
		for (Skill s : this.skills.values()){
			if (s.morphBonus > 0){
				if (s.morphImplantBonus > 0){
					ret += String.format("%40s |  %3s  |   %2d  |%2d (%2d)|   %2d  |\n", s.getLabel(),
							s.skill.aptitude, s.base + s.aptBonus, s.morphBonus,
							s.morphBonus + s.morphImplantBonus,	s.morphBonus + s.aptBonus + s.base);
				} else {
					ret += String.format("%40s |  %3s  |   %2d  |   %2d  |   %2d  |\n", s.getLabel(),
							s.skill.aptitude, s.base + s.aptBonus, s.morphBonus, s.morphBonus + s.aptBonus + s.base);
				}
			} else {
				ret += String.format("%40s |  %3s  |   %2d  |       |   %2d  |\n", s.getLabel(),
						s.skill.aptitude, s.base + s.aptBonus, s.morphBonus + s.aptBonus + s.base);
			}
		}
		
		ret += "\n\n        REP \n     ------------ \n";
		for (Entry<String, Integer> e : this.rep.entrySet()){
			if (e.getValue() != 0){
				ret += String.format("     %6s: %2d\n", e.getKey(), e.getValue());
			}
		}
		ret += "\n      TRAITS\n     --------\n  ";
		ret += wrap(this.traits.toString().substring(1,  this.traits.toString().length() - 1), 65);
		if (this.morph.traits.size() > 0){
			ret += "\n  Morph Traits: " + wrap(this.morph.traits.toString().substring(1,  
						this.morph.traits.toString().length() - 1), 65);
		}
		if (this.morph.implants.size() > 0){
			ret += "\n  Implants: " + wrap(this.morph.implants.toString().substring(1,  
						this.morph.implants.toString().length() - 1), 65);
		}
		if (this.psiRating > 0 && this.sleights.size() > 0){
			ret += "\n  Psi Sleights: " + wrap(this.sleights.toString().substring(1,  
					this.sleights.toString().length() - 1), 65);
		}
		
		ret += "\n\n  Starting Credits: " + this.getCredits() + " credits";
		ret += "\n\n";
		return ret;
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
	 * Just prints the aptitudes. They aren't pretty.
	 * @return aptitude string
	 */
	public String printAptitudes() {
		String output = "";
		for (Aptitude a : this.aptitudes.values()){
			output += a.name.shortText + ": " + a.total + " ";
		}
		if (this.morph.type.category != Morph.Type.infomorph){
			output += "\n";
			for (Stats s : this.stats.values()){
				output += s.name + ": " + s.value + " ";
			}
		}
		return output;
	}


	/**
	 * Goes through all the skills and checks to make sure they 
	 * do not exceed the 80 point limit. Only call this once, or you will
	 * have problems.
	 * 
	 * @return any leftover CP that comes of adding a skill that is
	 *  already too high
	 */
	public int finalizeSkills(){
		int totalRefund = 0;
		this.finalizeMorph();
		for (Skill s: this.skills.values()){
			s.updateSkillAptitudeVals(this.aptitudes.get(s.skill.aptitude));
			int base  = s.base;
			int apt   = s.aptBonus;
			int total = base + apt;
			int refund = 0;	
			
			if (s.isNativeLanguage){
				if (total > 60){ // Still cut back above 60	
					base = 60 - apt + ((total - 60) / 2) ;
					total = base + apt;
				}
				// Cap at 90
				if (total > 90){ // base + apt > 80 should not exist 
					base = 90 - apt; 
					refund += (total - 90) * 2;				
				}			
			} else {			
				if (total > 60){ // base + aptitude > 60 gets weird	
					if (total % 2 > 0){ // if is odd
						refund += 1;
						total = total - 1; // so we don't have to worry about rounding
					}				
					base = 60 - apt + ((total - 60) / 2) ;
					total = base + apt;
				}			
				if (total > 80){ // base + apt > 80 should not exist 
					base = 80 - apt; 
					refund += (total - 80) * 2;				
				}
				
				if (refund == 1 && total != 80){ //  you rounded earlier
					// just round up
					refund = 0;
					base++;
				}
			}
			s.base = base;
			totalRefund += refund;
		}
		
		return totalRefund;
	}
	
	/**
	 * Finalizes morph stats and traits
	 */
	private void finalizeMorph() {
		for (Morph.MorphAptitude ma : this.morph.stats.values()){
			if (ma.value > 0) {
				if (ma.label.shortText == "SOM") // sometimes this is different
					this.aptitudes.get(ma.label.shortText).setMorphBonus(ma.value, ma.bonus, this.morph.aptMaxSOM);
				else
					this.aptitudes.get(ma.label.shortText).setMorphBonus(ma.value, ma.bonus, this.morph.aptMax);
				//System.out.println(this.aptitudes.get(ma.label.shortText) + " " + ma);
			}
		}
		for (String t : this.morph.traits){
			if (t.contains("Skill:")){
				// Format: "Skill: dd <name>"
				t = t.substring("Skill: ".length());
				int value = Integer.parseInt(t.substring(0, 2));
				String skillName = t.substring(3); 
				this.addSkill(skillName, value);
				System.out.println("Adding skill " + skillName + " at " + value);
			}
			else
				this.morphTraits.add(t);
		}
	}

	/**
	 * Adds a new a skill, given a skill name and modified value.
	 * 
	 * @param label
	 * @param value
	 * @return any leftover CP that comes of adding a skill that is
	 *  already too high
	 */
	public void addSkill(String skillName, String field, int value) {
		String skillLabel = skillName + ": " + field;
		 if (this.skills.containsKey(skillLabel)){
			 //this.updateSkill(skillLabel, value);
			this.skills.get(skillName).base += value;
		} else {
			Skill s = new Skill(skillName, field, value);			
			this.addSkill(s);
		}
	}
	

	/**
	 * Only adds one skill and that skill is the native language
	 * @param nativeLanguage
	 */
	public void addNativeLanguage(String nativeLanguage) {
		Skill s = new Skill("Language", nativeLanguage, 70);
		//s.updateSkillAptitudeVals(this.aptitudes.get("INT"));
		s.isNativeLanguage = true;
		this.skills.put(s.getLabel(), s);		
	}

	/**
	 * Adds a new a skill, given a skill name and modified value.
	 * @param skillName Name of skill
	 * @param value value at which to add it
	 * @returnany leftover CP that comes of adding a skill that is
	 *  already too high
	 */
	public void addSkill(String skillName, int value) {
		if (this.skills.containsKey(skillName)){
			this.skills.get(skillName).base += value;
		} else {
			Skill s = new Skill(skillName, value);
			//s.updateSkillAptitudeVals(this.aptitudes.get(s.skill.aptitude));
			this.addSkill(s);
		}
	}
	

	/**
	 * The "true" skill adding function. Will actually put a skill in the list.
	 * Everything else just calls this.
	 * @param skill A skill to add
	 * @return value to refund
	 */
	public void addSkill(Skill skill) {
		if (this.skills.containsValue(skill)){
			 //this.updateSkill(skill.getLabel(), skill.base);
			 this.skills.get(skill.getLabel()).base += skill.base;
		} else {
			this.skills.put(skill.getLabel(), skill);			
		}

	}


	/**
	 * This should calculate the CP spent on the character. It doesn't.
	 * @return
	 */
	public int calculateCP(){
		return this.CP;
	}


	/**
	 * Gets string of morph
	 * @return Current morph's label
	 */
	public String getMorph() {
		if (this.morph != null)
			return this.morph.type.label;
		return null;
	}

	/**
	 * Sets the character's morph to a new one
	 * @param morph Morph object
	 */
	public void setMorph(Morph morph) {
		this.morph = morph;
		
		if (morph != null){
			if (this.morph.type.category == Morph.Type.infomorph) {
				this.setStat("SPD", 3);
				this.setStat("WT",  0);
				this.setStat("DUR", 0);
				this.setStat("DR",  0); 
			} else {
				this.setStat("SPD", 1);
				this.setStat("WT",  this.morph.type.woundThreshold);
				this.setStat("DUR", this.morph.type.durability); 
				if (this.morph.type.category == Morph.Type.synthmorph){
					this.setStat("DR", this.morph.type.durability * 2); 
				} else {
					this.setStat("DR", (int)Math.ceil(this.morph.type.durability * 1.5)); 
				}
			}
		}
	}

	public String getMorphType() {		
		return this.morph.type.category.name();
	}
	
	public boolean addSleight(String sleight){
		if (this.sleights.contains(sleight)) {
			return false;
		}

		this.sleights.add(sleight);
		return true;
	}
	
	public boolean addTrait(String trait) {
		if (trait.contains("Psi")){
			int rating = Character.getNumericValue(trait.charAt(trait.length() - 2));
			if (this.psiRating > rating) // so we don't drop to a lower rating
				return false;
			this.psiRating = rating;
			
		}
		if (this.traits.contains(trait)) {
			return false;
		}

		this.traits.add(trait);
		return true;
	}	

	public int getCredits() {
		return this.credits;
	}
	
	public void modifyCredits(int mod) {
		this.credits += mod;
	}

	public int getPsiRating(){
		return this.psiRating;
	}

	public void setMotivations(String motivations) {
		this.motivations = motivations;		
	}

	public void setBackground(String text) {
		if (this.background == null)
			this.background = text;		
	}

	public void setFaction(String text) {
		if (this.faction == null)
			this.faction = text;
		else 
			this.faction += ", " + text;
	}

	public String getMorphAptitudeChoice() {
		return this.morph.aptChoice;
	}



}
