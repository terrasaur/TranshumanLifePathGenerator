package character;


/**
 * A package containing trait objects. Eventually this will have a lovely 
 * big list of traits and what they do. Right now it just has their names.
 * @author terrasaur
 *
 */
public class Trait {
	enum TraitType{
		Ego,
		Morph,
		Either
	}
	
	enum Cost{
		Positive,
		Negative
	}
	
	TraitList info;
	int cost;
	int level;
	String descriptor;	
	TraitType type;
	
	// Constructors
	public Trait(String string, TraitType t) {
		this(string, null, 1, t);
	}
	
	public Trait(String string, String descriptor, TraitType t){
		this(string, descriptor, 1, t);
	}
	public Trait(String string, int level, TraitType t){
		this(string, null, level, t);
	}
	
	public Trait(String string, String descriptor, int level, TraitType t) {
		this(getTraitByLabel(string), descriptor, level, t);
		if (getTraitByLabel(string) == null){
			System.out.println("Trait not found. " + string);
			return;
		}
	}
	
	public Trait(TraitList trait, TraitType t){
		this(trait, null, 1, t);
	}
	
	public Trait(TraitList trait, String descriptor, int level, TraitType t) {
		this.info = trait;
		this.level = level;
		this.type  = t;
		this.descriptor = descriptor;
		if (this.info.takeMultiple && this.descriptor == null){
			System.out.println("Error: You must specify a descriptor for the trait " + this.info.label);
		}
		this.cost  = trait.cost[level-1];
		initializeTrait();
	}



	/**
	 * Loops over the list of traits, and returns the proper list
	 * enum given a string of the pretty-print name.
	 * @param label
	 * @return
	 */
	public static TraitList getTraitByLabel(String label) {
		for (TraitList l : TraitList.values()){
			if (l.equals(label))
				return l;
		}
		return null;
	}
	

	
	static enum TraitList{
		Adaptability        ("Adaptability",          TraitType.Ego, Cost.Positive, 10, 20, false),
		Allies              ("Allies",                TraitType.Ego, Cost.Positive, 30, false),
		Ambidextrous        ("Ambidextrous",          TraitType.Ego, Cost.Positive, 10, true),
		AnimalEmpathy       ("Animal Empathy",        TraitType.Ego, Cost.Positive, 5,  false),
		Brave               ("Brave",                 TraitType.Ego, Cost.Positive, 10, false),
		CommonSense         ("Common Sense",          TraitType.Ego, Cost.Positive, 10, false),
		DangerSense         ("Danger Sense",          TraitType.Ego, Cost.Positive, 10, false),
		DirectionSense      ("Direction Sense",       TraitType.Ego, Cost.Positive, 5,  false),
		EideticMemory       ("Eidetic Memory",        TraitType.Either, Cost.Positive, 10, false),
		ExceptionalAptitude ("Exceptional Aptitude",  TraitType.Either, Cost.Positive, 20, false),
		Expert              ("Expert",                TraitType.Ego, Cost.Positive, 10, false),
		FastLearner         ("Fast Learner",          TraitType.Ego, Cost.Positive, 10, false),
		FirstImpression     ("First Impression",      TraitType.Ego, Cost.Positive, 10, false),
		HyperLinguist       ("Hyper Linguist",        TraitType.Ego, Cost.Positive, 10, false),
		MathWiz             ("Math Wiz",              TraitType.Ego, Cost.Positive, 10, false),
		PainTolerance       ("Pain Tolerance",        TraitType.Either, Cost.Positive, 10, 20, false),
		Patron              ("Patron",                TraitType.Ego, Cost.Positive, 30, false),
		Psi                 ("Psi",                   TraitType.Ego, Cost.Positive, 20, 25, false),
		PsiChameleon        ("Psi Chameleon",         TraitType.Either, Cost.Positive, 10, false),
		PsiDefense          ("Psi Defense",           TraitType.Either, Cost.Positive, 10, 20, false),
		RightAtHome         ("Right At Home",         TraitType.Ego, Cost.Positive, 10, false),
		SecondSkin          ("Second Skin",           TraitType.Ego, Cost.Positive, 15, false),
		SituationalAwareness("Situational Awareness", TraitType.Ego, Cost.Positive, 10, false),
		Zoosemiotics        ("Zoosemiotics",          TraitType.Ego, Cost.Positive, 5,  false),
		HomeTurf            ("Home Turf",             TraitType.Ego, Cost.Positive, 10, false),
		InformationControl  ("Information Control",   TraitType.Ego, Cost.Positive, 10, false),
		SocialButterfly     ("Social Butterfly",      TraitType.Ego, Cost.Positive, 15, false),
		YoureThatGuy        ("You're That Guy!",      TraitType.Ego, Cost.Positive, 10, false),
		
		Addiction         ("Addiction",           TraitType.Either, Cost.Negative, 5, 10, 20, true),
		BadLuck           ("Bad Luck",            TraitType.Ego, Cost.Negative, 30, false),
		Blacklisted       ("Blacklisted",         TraitType.Ego, Cost.Negative, 5, 20, false),
		BlackMark         ("Black Mark",          TraitType.Ego, Cost.Negative, 10, 20, 30, false),
		CombatParalysis   ("Combat Paralysis",    TraitType.Ego, Cost.Negative, 20, false),
		EditedMemories    ("Edited Memories",     TraitType.Ego, Cost.Negative, 10, false),
		Enemy             ("Enemy",               TraitType.Ego, Cost.Negative, 10, false),
		Feeble            ("Feeble",              TraitType.Ego, Cost.Negative, 20, false),
		IdentityCrisis    ("Identity Crisis",     TraitType.Ego, Cost.Negative, 10, false),
		Illiterate        ("Illiterate",          TraitType.Ego, Cost.Negative, 10, false),
		ImmortalityBlues  ("Immortality Blues",   TraitType.Ego, Cost.Negative, 10, false),
		Incompetent       ("Incompetent",         TraitType.Ego, Cost.Negative, 10, false),
		LowPainTolerance  ("Low Pain Tolerance",  TraitType.Either, Cost.Negative, 20, false),
		MentalDisorder    ("Mental Disorder",     TraitType.Ego, Cost.Negative, 10, true),
		ModifiedBehavior  ("Modified Behavior",   TraitType.Ego, Cost.Negative, 5, 10, 20, false),
		MorphingDisorder  ("Morphing Disorder",   TraitType.Ego, Cost.Negative, 10, 20, 30, false),
		NeuralDamage      ("Neural Damage",       TraitType.Ego, Cost.Negative, 10, true),
		Oblivious         ("Oblivious",           TraitType.Ego, Cost.Negative, 10, false),
		OntheRun          ("On the Run",          TraitType.Ego, Cost.Negative, 10, false),
		PsiVulnerability  ("Psi Vulnerability",   TraitType.Either, Cost.Negative, 10, false),
		RealWorldNaivete  ("Real World Naivete",  TraitType.Ego, Cost.Negative, 10, false),
		SlowLearner       ("Slow Learner",        TraitType.Ego, Cost.Negative, 10, false),
		SocialStigma      ("Social Stigma",       TraitType.Either, Cost.Negative, 10, false),
		Timid             ("Timid",               TraitType.Ego, Cost.Negative, 10, false),
		VRVertigo         ("VR Vertigo",          TraitType.Ego, Cost.Negative, 10, false),
		DataFootprint     ("Data Footprint",      TraitType.Ego, Cost.Negative, 10, false),
		ImparedBalance    ("Impared Balance",     TraitType.Ego, Cost.Negative, 10, 20, 30, false),
		ImparedLinguistics("Impared Linguistics", TraitType.Ego, Cost.Negative, 10, 20, false),
		ShutIn            ("Shut In",             TraitType.Ego, Cost.Negative, 15, false),
		Stalker           ("Stalker",             TraitType.Ego, Cost.Negative, 10, false),
		StolenIdentity    ("Stolen Identity",     TraitType.Ego, Cost.Negative, 10, false),
		WaitThatWasYou    ("Wait, That Was You?", TraitType.Ego, Cost.Negative, 10, false),
		
		//([A-Za-z :\']+?)\t\t([0-9]+)\t([0-9]*)\t([0-9]*)\R		
		//$1\(\"$1\", Cost.Negative, $2, $3, $4, false\),\R
		CoronalAdaptation       ("Coronal Adaptation",     TraitType.Morph, Cost.Positive, 30, false),
		HighTemperatureOperation("High-Temperature Operation", TraitType.Morph, Cost.Positive, 10, false),
		ImprovedImmuneSystem    ("Improved Immune System", TraitType.Morph, Cost.Positive, 10, 20, false),
		Innocuous               ("Innocuous",              TraitType.Morph, Cost.Positive, 10, false),
		Limber                  ("Limber",                 TraitType.Morph, Cost.Positive, 10, 20, false),
		NaturalImmunity         ("Natural Immunity",       TraitType.Morph, Cost.Positive, 10, false),
		RapidHealer             ("Rapid Healer",           TraitType.Morph, Cost.Positive, 10, false),
		StrikingLooks           ("Striking Looks",         TraitType.Morph, Cost.Positive, 10, 20, false),
		Tough                   ("Tough",                  TraitType.Morph, Cost.Positive, 10, 20, 30, false),
		
		Aged                  ("Aged",                 TraitType.Morph, Cost.Negative, 10, false),
		AlienBiochemistry     ("Alien Biochemistry",   TraitType.Morph, Cost.Negative, 0, false),
		FastMetabolism        ("Fast Metabolism",      TraitType.Morph, Cost.Negative, 5, false),
		Frail                 ("Frail",                TraitType.Morph, Cost.Negative, 10, 20, false),
		GeneticDefect         ("Genetic Defect",       TraitType.Morph, Cost.Negative, 10, 20, false),
		ImplantRejection      ("Implant Rejection",    TraitType.Morph, Cost.Negative, 5, 15, false),
		ImparedHearing        ("Impared Hearing",      TraitType.Morph, Cost.Negative, 5, false),
		Lemon                 ("Lemon",                TraitType.Morph, Cost.Negative, 10, false),
		MildAllergy           ("Mild Allergy",         TraitType.Morph, Cost.Negative, 5, false),
		NoCorticalStack       ("No Cortical Stack",    TraitType.Morph, Cost.Negative, 10, false),
		PlannedObsolescence   ("Planned Obsolescence", TraitType.Morph, Cost.Negative, 5, false),
		SevereAllergy         ("Severe Allergy",       TraitType.Morph, Cost.Negative, 10, 20, false),
		TemperatureIntolerance("Temperature Intolerance (Warm)",TraitType.Morph, Cost.Negative, 10, false),	
		Unattractive          ("Unattractive",         TraitType.Morph, Cost.Negative, 10, 20, 30, false),
		UncannyValley         ("Uncanny Valley",       TraitType.Morph, Cost.Negative, 10, false),
		Unfit                 ("Unfit",                TraitType.Morph, Cost.Negative, 10, 20, false),
		WeakImmuneSystem      ("Weak Immune System",   TraitType.Morph, Cost.Negative, 10, 20, false),
		WeakGrip              ("Weak Grip",            TraitType.Morph, Cost.Negative, 10, false),
		WholeBodyApoptosis    ("Whole Body Apoptosis", TraitType.Morph, Cost.Negative, 0, false),
		ZeroGNausea           ("Zero-G Nausea",        TraitType.Morph, Cost.Negative, 10, false)
		;
		
		String label;
		Cost costType;
		TraitType type;
		int cost[];
		boolean takeMultiple;
		
		TraitList(String s, TraitType type, Cost costType, int costLv1, boolean takeMultiple){
			this(s, type, costType, costLv1, -1, -1, takeMultiple);
		}

		TraitList(String s, TraitType type, Cost costType, int costLv1, int costLv2, boolean takeMultiple){
			this(s, type, costType, costLv1, costLv2, -1, takeMultiple);
		}
		
		TraitList(String s, TraitType type, Cost costType, int costLv1, int costLv2, int costLv3, boolean takeMultiple){
			this.label        = s;
			this.costType     = costType;
			this.cost = new int[3];
			this.cost[0]      = costLv1;
			this.cost[1]      = costLv2;
			this.cost[2]      = costLv3;
			this.takeMultiple = takeMultiple;
		}
	}

	
	private void initializeTrait() {
		
	}
}
