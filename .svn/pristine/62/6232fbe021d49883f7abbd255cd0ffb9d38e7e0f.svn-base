package character;


/***
 * This is the class to contain a single skill.
 * 
 * Skills should have an associated aptitude. If you update the aptitude,
 * call this.updateAptitude(). The Character class should call this when 
 * it changes aptitudes or morphs.
 * 
 * When creating a skill, assign *base skill points only*. The skill
 * will handle aptitude bonuses and skills over 60.
 * 
 * @author terrasaur
 *
 */
public class Skill {
	public enum Category{
		Active,
		Knowledge,
		Psi
	}
	
	protected SkillList skill;
	protected String    field;
	protected Integer   base;
	protected Integer   aptBonus;
	protected Integer   morphBonus;
	protected Boolean   isNativeLanguage = false;
	protected int       morphImplantBonus = 0;;


	public Skill(String name, Integer base) {
		super();
		this.skill = getSkillByName(name);
		this.field = null;
		this.base = base;
		this.aptBonus = 0;
		this.morphBonus = 0;
	}
	
	public Skill(String name, String field, Integer base) {
		super();
		this.skill = getSkillByName(name);
		this.field = field;
		this.base = base;
		this.aptBonus = 0;
		this.morphBonus = 0;
	}
	
	public Skill(String name, String field, Integer base, Aptitude aptitude) {
		super();
		this.skill = getSkillByName(name);
		this.field = field;
		this.base = base;
		this.aptBonus = aptitude.total;
		this.morphBonus = aptitude.morphBonus;
	}
	
	public Skill(String name, Integer base, Aptitude aptitude) {
		super();
		this.skill = getSkillByName(name);
		this.field = null;
		this.base = base;
		this.aptBonus = aptitude.total;
		this.morphBonus = aptitude.morphBonus;
	}

	public Skill(SkillList skill) {
		this.skill = skill;
		this.field = null;
		this.base  = 0;
		this.aptBonus = 0;
		this.morphBonus = 0;
	}
	
	public Skill(SkillList skill, String field) {
		this.skill = skill;
		this.field = field;
		this.base = 0;
		this.aptBonus = 0;
		this.morphBonus = 0;
	}
	
	public enum SkillList{
		BeamWeapons   ("Beam Weapons",     "COO", Category.Active, false),
		Blades        ("Blades",           "SOM", Category.Active, false),
		Clubs         ("Clubs",            "SOM", Category.Active, false),
		ExoticMelee   ("Exotic Melee",     "SOM", Category.Active, true),
		ExoticRanged  ("Exotic Ranged",    "COO", Category.Active, true),
		Fray          ("Fray",             "REF", Category.Active, false),
		Gunnery       ("Gunnery",          "INT", Category.Active, false),
		KineticWeapons("Kinetic Weapons",  "COO", Category.Active, false),
		SeekerWeapons ("Seeker Weapons",   "COO", Category.Active, false),
		SprayWeapons  ("Spray Weapons",    "COO", Category.Active, false),
		ThrownWeapons ("Throwing Weapons", "COO", Category.Active, false),
		UnarmedCombat ("Unarmed Combat",   "SOM", Category.Active, false),
		Climbing      ("Climbing",         "SOM", Category.Active, false),
		Flight        ("Flight",           "SOM", Category.Active, false),
		FreeFall      ("Free Fall",        "REF", Category.Active, false),
		Freerunning   ("Freerunning",      "SOM", Category.Active, false),
		Infiltration  ("Infiltration",     "COO", Category.Active, false),
		Palming       ("Palming",          "COO", Category.Active, false),
		Swimming      ("Swimming",         "SOM", Category.Active, false),
		Pilot         ("Pilot",            "REF", Category.Active, true),
		AnimalHandling("Animal Handling",  "SAV", Category.Active, false),
		Deception     ("Deception",        "SAV", Category.Active, false),
		Disguise      ("Disguise",         "INT", Category.Active, false),
		Impersonation ("Impersonation",    "SAV", Category.Active, false),
		Intimidation  ("Intimidation",     "SAV", Category.Active, false),
		Kinesics      ("Kinesics",         "SAV", Category.Active, false),
		Networking    ("Networking",       "SAV", Category.Active, true),
		Persuasion    ("Persuasion",       "SAV", Category.Active, false),
		Protocol      ("Protocol",         "SAV", Category.Active, false),
		Demolitions   ("Demolitions",      "COG", Category.Active, false),
		Hardware      ("Hardware",         "COG", Category.Active, true),
		Infosec       ("Infosec",          "COG", Category.Active, false),
		Interfacing   ("Interfacing",      "COG", Category.Active, false),
		Medicine      ("Medicine",         "COG", Category.Active, true),
		Programming   ("Programming",      "COG", Category.Active, false),
		Psychosurgery ("Psychosurgery",    "COG", Category.Active, false),
		Research      ("Research",         "COG", Category.Active, false),
		Investigation ("Investigation",    "INT", Category.Active, false),
		Navigation    ("Navigation",       "INT", Category.Active, false),
		Perception    ("Perception",       "INT", Category.Active, false),
		Scrounging    ("Scrounging",       "INT", Category.Active, false),
		Academics     ("Academics",        "COG", Category.Knowledge, true),
		Art           ("Art",              "INT", Category.Knowledge, true),
		Language      ("Language",         "INT", Category.Knowledge, true),
		Interest      ("Interest",         "COG", Category.Knowledge, true),
		Profession    ("Profession",       "COG", Category.Knowledge, true),
		Control       ("Control",          "WIL", Category.Psi, false),
		PsiAssault    ("Psi Assault",      "WIL", Category.Psi, false),
		Sense         ("Sense",            "INT", Category.Psi, false),
		;
				
		protected String text;
		protected String aptitude;
		protected Category category;
		protected Boolean hasField;
		
		
		private SkillList(String text, String apt, Category c, Boolean field){
			this.text = text;
			this.aptitude = apt;
			this.category = c;
			this.hasField = field;
		}

	}
	
	static SkillList getSkillByName(String label) {
		for (SkillList s : SkillList.values()){
			if (s.text.equals(label))
				return s;
		}
		System.out.println("Error: Could not find skill "+ label);
		return null;
	}
	
	public void setAptitude(Aptitude a){
		this.aptBonus = a.total;
		this.morphBonus = a.morphBonus;
	}

	@Override
	public String toString() {
		if (this.skill != null){
			if (this.skill.hasField){
				return this.skill.text + ": " + this.field + " (" + this.skill.aptitude + "): " + 
			           (this.base + this.aptBonus) + " (morph bonus: " + this.morphBonus + ")";
			} else {
				return this.skill.text + " (" + this.skill.aptitude + "): " + (this.base + this.aptBonus) + 
						" (morph bonus: " + this.morphBonus + ")";
			}
		} 
		return null;
	}
	
	/**
	 * Like toString, but only returns the base value
	 * @return String containing name, field (if any) and base value
	 */
	public String toShortString(){
		if (this.skill != null)
			if (this.skill.hasField){
				return this.skill.text + ": " + this.field + " (" + this.skill.aptitude + "): " + this.base  ;
			} else {
				return this.skill.text + " (" + this.skill.aptitude + "): " + this.base ;
			}
		return null;
	}
	
	/**
	 * Returns only the label of the skill
	 * @return Label
	 */
	public String getLabel(){
		if (this.skill != null){
			if (this.skill.hasField)
				return this.skill.text + ": " + this.field;
			return this.skill.text;
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String){
			return obj.equals(this.getLabel());
		} else if (obj instanceof Skill){
			if (this.skill.hasField){
				return (((Skill) obj).getLabel().equals(this.getLabel())  &&
						((Skill) obj).field.equals(this.field)); // must match both
			}
			return ((Skill) obj).getLabel().equals(this.getLabel());
		}
		return super.equals(obj);
	}

	public void updateSkillAptitudeVals(Aptitude aptitude) {
		this.aptBonus          = aptitude.total;
		this.morphBonus        = aptitude.morphBonus;
		this.morphImplantBonus = aptitude.morphImplantBonus;
	}

	public String getField() {
		return this.field;
	}

	public String getText() {
		if (skill != null)
			return this.skill.text;
		return null;
	}

	public int getBaseValue() {
		return this.base;
	}

	public void setField(String newField) {
		this.field = newField;		
	}

	
}
