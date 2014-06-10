package lifepath;

import java.util.ArrayList;
import java.util.List;

import character.EPCharacter;
import character.Skill;

/**
 * Parent type for all packages in the life path creator. Each package type
 * extends this class.
 * @author terrasaur
 *
 */
public abstract class LifePathPackage {
	String label;
	Integer ppCost;
	Type pkgType;
	ArrayList<Skill>     skillList;
	ArrayList<String>    traitList;
	ArrayList<StatBonus> bonusList;
	ArrayList<String>    choiceList;
	ArrayList<String>    suggestedMotivations;
	Integer creditsMod;
	// Some packages have a "pick A OR B". If this is true, the package will
	// pick one or the other randomly at package creation
	boolean getRandomSkills = false; 
	
	public enum Type {
		Background,
		Faction,
		Focus,
		Customization
	}
	
	// Helper class that tracks stat boosts. Really just a string and int combo.
	// Can probably be replaced with a hashmap
	protected class StatBonus{
		String label;
		int bonus;
		
		protected StatBonus(String label, int bonus){
			this.label = label;
			this.bonus = bonus;
		}
		
		public String toString(){
			return "+" + this.bonus + " " + this.label;
		}
	}

	/* (non-Javadoc)
	 * @see lifepath.Package#toString()
	 */
	@Override
	public String toString() {
		return label + " (" + ppCost + " PP)";
	}

	/* (non-Javadoc)
	 * @see lifepath.Package#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String){
			return (String)obj == this.label;
		} else if (obj instanceof LifePathPackage){
			return ((LifePathPackage) obj).label == this.label;
		} else {
			return super.equals(obj);
		}
	}
	
	public  LifePathPackage( String label, Integer cost) {
		super();
		this.label = label;
		this.ppCost = cost;
		this.creditsMod = 0;
		this.skillList = new ArrayList<Skill>();
		this.traitList = new ArrayList<String>();
		this.bonusList = new ArrayList<StatBonus>();
		this.choiceList = new ArrayList<String>();
		this.suggestedMotivations = new ArrayList<String>();

	}

	public abstract String prettyPrint();	
	public String getLists(){
		String ret = "";
		for (StatBonus stat : this.bonusList){
			ret += "         - " + stat.toString() + "\n";
		}
		for (Skill skill : this.skillList){
			ret += "         - " + skill.toShortString() + "\n";
		}
		for (String trait : this.traitList){
			ret += "         - " + trait + "\n";
		}
		for (String choice : this.choiceList){
			ret += "         - " + choice + "\n";
		}
		return ret;
	}
	
	/* Regular expressions for the getPackageContents function found in all of the 
	 * packages (but not here, because I wanted to keep it private): 
	 * (\w+):\s+\(Choose One\)\s+(\d\d)[^\"]
	 * this\.choiceList\.add\(\"Add one $1 skill of your choice at $2\"\);
	 * 
	 * (\w+):\s+\(?(\w+)\)?\s+(\d\d)[^\"]
	 * this\.skillList\.add\(new Skill\(\"$1\"\, \"$2\"\, $3\)\);
	 * 
	 * (\w+\s\w+?)\s+(\d\d)[^\"]
	 * (\w+)\s+(\d\d)[^\"]
	 * this\.skillList\.add\(new Skill\(\"$1\"\, $2\)\);
	 * 
	 * (\+\w+\s\w+?),
	 * this\.suggestedMotivations\.add\(\"$1\"\);
	 * 
	 * this\.choiceList\.add\("Add ([A-Za-z :]+?) or ([A-Za-z :]+?) at (\d\d)"\);
	 * if \(this\.getRandomSkills\)\R\t\t\t\t\tif \(d10\.Roll\(\) > 5\)\R\t\t\t\t\t\t
	 * this\.skillList\.add\(new Skill\("$2", $4\)\);\R\t\t\t\t\telse\R\t\t\t\t\t\t
	 * this\.skillList\.add\(new Skill\("$3", $4\)\);\R\t\t\t\telse\R\t\t\t\t\t$1 
	 */	
	
	/**
	 * Applies all packages from a list. Use this to apply many packages of 
	 * different types
	 * @param c Character to apply packages to
	 * @param list Package list
	 * @param chooseSkills 
	 * @return String array of todo that came out of the packages
	 */
	public static ArrayList<String> applyAllPackages(EPCharacter c, 
			List<LifePathPackage> list, boolean chooseSkills){
		ArrayList<String> todo = new ArrayList<String>();
		
		for (LifePathPackage p: list){
			p.getRandomSkills = chooseSkills;
			p.applyToCharacter(c);
			todo.addAll(p.choiceList);
		}
		
		int refund = c.finalizeSkills();
		if (refund > 0){
			todo.add("Spend " + refund + " CP from refunding skills past 80");
		}
		return todo;
	}
	

	
	/**
	 * Applies SKILLS AND TRAITS ONLY. Does *NOT* apply attribute bonuses.
	 * 
	 * @param c character
	 * @return refund value, if any
	 */
	protected void applyToCharacter(EPCharacter c) {
		
		for (String trait : this.traitList){
			//System.out.println("Adding trait " + trait);
			if (!c.addTrait(trait)){ // already has trait
				this.choiceList.add("Reassign CP from duplicate " + trait + " trait");
			}
		}
		
		for (StatBonus stat : this.bonusList){
			if (stat.label.contains("psi")){
				if (getRandomSkills){
					this.assignRandomPsiSleights(c, stat.label, stat.bonus);
				} else {
					this.choiceList.add("Add " + stat.bonus + " " + stat.label + 
							" sleights of your choice\n");
				}
				continue;
			}
			if (stat.label == "Mental Disorder"){
				if (getRandomSkills){
					for (int i = 0; i < stat.bonus; i++){
						boolean addedUniqueDisorder = false;
						do {
							addedUniqueDisorder = c.addTrait(
									LifePathCharts.getMentalDisorder(c.getPsiRating() > 0));
						} while (addedUniqueDisorder == false);
					}
				} else {
					String choice = "Add " + stat.bonus + " Mental Disorder trait";
					if (stat.bonus > 1)
						choice += "s";
					choice+= " of your choice\n";
					this.choiceList.add(choice);
				}
				continue;
			}
			if (stat.label == "Choose Rep"){
				stat.label = LifePathCharts.getRandomRepGroup();
			}
			if ( c.modifyStat(stat.label, stat.bonus) == -1){
				System.out.println("Error attempting to add stat " + stat.label);
			}
		}
		for (Skill skill : this.skillList){
			if (skill.getField() == "Choice"){
				boolean gotSkillField = false;
				if (this.getRandomSkills)
					gotSkillField = RandomSkillCharts.setRandomSkillField(skill);
				if (!gotSkillField) {
					if (skill.getText() == null)
						System.out.println(this.prettyPrint());
					else
						this.choiceList.add("Add one " + skill.getText() +
								" skill of your choice at " + skill.getBaseValue());
					continue;
				}				
			}
			c.addSkill(skill);
		}

		if (this.creditsMod > 0){
			c.modifyCredits(creditsMod);
		}

	}
	

	/**
	 * Assigns a number of sleights to the character
	 * @param c character to add sleights to
	 * @param type type of sleights (psi, psi-gamma, or psi-chi)
	 * @param sleights number of sleights to add
	 */
	private void assignRandomPsiSleights(EPCharacter c, String type, int sleights) {
		
		for (int i = 0; i < sleights; i++){
			boolean addedUniqueSleight = false;
			do {
				if (type == "psi-chi")
					addedUniqueSleight = c.addSleight(LifePathCharts.getPsiChiSleight());
				else if (type == "psi-gamma")
					addedUniqueSleight = c.addSleight(LifePathCharts.getPsiGammaSleight());
				else
					addedUniqueSleight = c.addSleight(LifePathCharts.getPsiSleight(c.getPsiRating()));					
			} while (addedUniqueSleight == false);
		}
	}

	/**
	 * Gets all the motivations that each package suggests, in a nice handy list
	 * @param packages A list of the packages
	 * @return An arrayList of strings of motivations
	 */
	public static ArrayList<String> getMotivations(ArrayList<LifePathPackage> packages) {
		ArrayList<String> motivations = new ArrayList<String>();
		for (LifePathPackage p : packages){
			if (p.suggestedMotivations.size() > 0){
				motivations.addAll(p.suggestedMotivations);
			}
		}
		return motivations;
	}
}