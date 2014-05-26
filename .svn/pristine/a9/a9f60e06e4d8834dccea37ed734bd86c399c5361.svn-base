package lifepath;


import character.Skill;

public class FocusPackage extends LifePathPackage {
	List packageName;
	
	public FocusPackage(List name, Integer cost) {
		super(name.text, cost);
		this.packageName = name;
		this.pkgType = Type.Focus;
		if (this.ppCost != 1 && this.ppCost != 3 && this.ppCost != 5){
			System.out.println("Error: You have the wrong cost for a package: " + this.ppCost);
			this.ppCost = 1;
		}
		this.getPackageContents();
	}
	
	public FocusPackage(String name, Integer cost) {
		super(name, cost);
		this.packageName = getPackageByLabel(name);
		this.pkgType = Type.Focus;
		if (this.ppCost != 1 && this.ppCost != 3 && this.ppCost != 5){
			System.out.println("Error: You have the wrong cost for a package: " + this.ppCost);
			this.ppCost = 1;
		}
		this.getPackageContents();
	}


	public enum List {
		Academic          ("Academic"),
		Activist          ("Activist"),
		Assassin          ("Assassin"),
		Bodyguard         ("Bodyguard"),
		BotJammer         ("Bot Jammer"),
		CombatAsync       ("Combat Async"),
		ConArtist         ("Con Artist"),
		ControllerAsync   ("Controller Async"),
		CovertOps         ("Covert Ops"),
		Dealer            ("Dealer"),
		EgoHunter         ("Ego Hunter"),
		Enforcer          ("Enforcer"),
		Explorer          ("Explorer"),
		Face              ("Face"),
		Genehacker        ("Genehacker"),
		Hacker            ("Hacker"),
		Icon              ("Icon"),
		Investigator      ("Investigator"),
		Journo            ("Journo"),
		Medic             ("Medic"),
		Pirate            ("Pirate"),
		Psychosurgeon     ("Psychosurgeon"),
		SavantAsync       ("Savant Async"),
		ScannerAsync      ("Scanner Async"),
		Scavenger         ("Scavenger"),
		Scientist         ("Scientist"),
		SmartAnimalHandler("Smart Animal Handler"),
		Smuggler          ("Smuggler"),
		Soldier           ("Soldier"),
		Spy               ("Spy"),
		Techie            ("Techie"),
		Thief             ("Thief"),
		Wrecker           ("Wrecker"),
		;
		
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


	public static List getPackageByLabel(String label) {
		for (List l : List.values()){
			if (l.equals(label))
				return l;
		}
		return null;
	}
	
	
	private void getPackageContents() {
		switch(this.packageName){
		case Academic:
			this.suggestedMotivations.add("+Open Source");
			this.suggestedMotivations.add("+Education");
			this.suggestedMotivations.add("+Personal Development");
			this.suggestedMotivations.add("+Personal Career");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Research", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("r-rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 50)); 
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Investigation", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Networking", "Scientists", 25));
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Instruction", 20)); 
				this.skillList.add(new Skill("Research", 40));
			} else {
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("r-rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 50)); 
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Investigation", 50)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Networking", "Scientists", 50));
				this.skillList.add(new Skill("Perception", 30));
				this.skillList.add(new Skill("Persuasion", 30)); 
				this.skillList.add(new Skill("Profession", "Instruction", 50)); 
				this.skillList.add(new Skill("Research", 45));
			} 
			break;
		case Activist:
			this.suggestedMotivations.add("+Mercurial Cause");
			this.suggestedMotivations.add("+Bioconservatism");
			this.suggestedMotivations.add("+Privacy");
			this.suggestedMotivations.add("+Venusian Sovereignty");
			this.suggestedMotivations.add("+Terraforming");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Persuasion", 40));
				this.skillList.add(new Skill("Research", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Fray", 20));
				this.skillList.add(new Skill("Infosec", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 50)); 
				this.skillList.add(new Skill("Investigation", 20)); 
				this.skillList.add(new Skill("Language", "Choice", 20)); 
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Persuasion", 40)); 
				this.skillList.add(new Skill("Research", 30));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("SAV", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Infiltration", 30));
				this.skillList.add(new Skill("Infosec", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 50));
				this.skillList.add(new Skill("Interest", "Choice", 20));
				this.skillList.add(new Skill("Investigation", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Persuasion", 50)); 
				this.skillList.add(new Skill("Protocol", 30));
				this.skillList.add(new Skill("Research", 40)); 
				this.skillList.add(new Skill("Unarmed Combat", 20));
			}
			break;
		case Assassin:
			this.suggestedMotivations.add("+Personal Career");
			this.suggestedMotivations.add("+Privacy");
			this.suggestedMotivations.add("+Survival");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Kinetic Weapons", 40));
				this.skillList.add(new Skill("Profession", "Assassin", 30));
				this.skillList.add(new Skill("Unarmed Combat", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Blades", 25));
				this.skillList.add(new Skill("Fray", 30)); 
				this.skillList.add(new Skill("Infiltration", 40));
				this.skillList.add(new Skill("Kinetic Weapons", 50));
				this.skillList.add(new Skill("Language", "Choice", 40));
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Assassin", 50));
				this.skillList.add(new Skill("Unarmed Combat", 40));
			} else {
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Art", "Choice", 30)); 
				this.skillList.add(new Skill("Blades", 35)); 
				this.skillList.add(new Skill("Disguise", 40)); 
				this.skillList.add(new Skill("Freerunning", 30));
				this.skillList.add(new Skill("Fray", 30)); 
				this.skillList.add(new Skill("Infiltration", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Kinetic Weapons", 50)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Profession", "Assassin", 50)); 
				this.skillList.add(new Skill("Spray Weapons", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 50));
			}			
			break;
		case Bodyguard:
			this.suggestedMotivations.add("+Duty");
			this.suggestedMotivations.add("+Privacy");
			this.suggestedMotivations.add("+Survival");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Kinesics", 40)); 
				this.skillList.add(new Skill("Profession", "Bodyguard", 30));
				this.skillList.add(new Skill("Unarmed Combat", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Beam Weapons", 30)); 
				this.skillList.add(new Skill("Fray", 30));
				this.skillList.add(new Skill("Intimidation", 30)); 
				this.skillList.add(new Skill("Kinesics", 40));
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 40)); 
				this.skillList.add(new Skill("Profession", "Bodyguard", 50)); 
				this.skillList.add(new Skill("Unarmed Combat", 35));
			} else {
				this.bonusList.add(new StatBonus("REF", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Beam Weapons", 30));
				this.skillList.add(new Skill("Fray", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Intimidation", 30));
				this.skillList.add(new Skill("Kinesics", 50));
				this.skillList.add(new Skill("Kinetic Weapons", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Medicine", "Paramedic", 25)); 
				this.skillList.add(new Skill("Perception", 50));
				this.skillList.add(new Skill("Profession", "Bodyguard", 50)); 
				this.skillList.add(new Skill("Unarmed Combat", 50));
			}
			break;
		case BotJammer:
			this.suggestedMotivations.add("+Maker Movement");
			this.suggestedMotivations.add("+DIY");
			this.suggestedMotivations.add("+Thrill Seeking");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Hardware", "Choice", 30));
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Pilot", "Choice", 40));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Flight", 40)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Pilot", "Choice", 50)); 
				this.skillList.add(new Skill("Pilot", "Choice", 30)); 
				this.skillList.add(new Skill("Seeker Weapons", 25));
			} else {
				this.bonusList.add(new StatBonus("REF", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Flight", 50));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Navigation", 20));
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Pilot", "Choice", 50)); 
				this.skillList.add(new Skill("Pilot", "Choice", 30));
				this.skillList.add(new Skill("Pilot", "Choice", 30));
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Seeker Weapons", 25));
			}
			break;
		case CombatAsync:
			this.suggestedMotivations.add("+Self Control");
			this.suggestedMotivations.add("+Survival");
			this.suggestedMotivations.add("Neurodiversity+");
			this.suggestedMotivations.add("-Bloodlust");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Profession", "Squad Tactics", 30)); 
				this.skillList.add(new Skill("Psi Assault", 30));
				this.traitList.add("Psi (Level 2)"); 
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi", 3));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Fray", 20));
				this.skillList.add(new Skill("Infiltration", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Profession", "Squad Tactics", 50)); 
				this.skillList.add(new Skill("Psi Assault", 50)); 
				this.skillList.add(new Skill("Spray Weapons", 20)); 
				this.skillList.add(new Skill("Unarmed Combat", 20));
				this.traitList.add("Psi (Level 2)"); 
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi", 4));
			} else {
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Control", 30)); 
				this.skillList.add(new Skill("Fray", 30)); 
				this.skillList.add(new Skill("Infiltration", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Profession", "Squad Tactics", 50)); 
				this.skillList.add(new Skill("Psi Assault", 50));
				this.skillList.add(new Skill("Sense", 30)); 
				this.skillList.add(new Skill("Spray Weapons", 40)); 
				this.skillList.add(new Skill("Unarmed Combat", 30));
				this.traitList.add("Psi (Level 2)"); 
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi", 8));
			}
			break;
		case ConArtist:
			this.suggestedMotivations.add("+Privacy");
			this.suggestedMotivations.add("+Survival");
			this.suggestedMotivations.add("+Vice");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Deception", 40)); 
				this.skillList.add(new Skill("Profession", "Con Schemes", 30)); 
				this.skillList.add(new Skill("Persuasion", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Deception", 50)); 
				this.skillList.add(new Skill("Disguise", 30)); 
				this.skillList.add(new Skill("Impersonation", 30));
				this.skillList.add(new Skill("Kinesics", 15));
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Palming", 20)); 
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Profession", "Con Schemes", 50)); 
				this.skillList.add(new Skill("Persuasion", 40));
			} else {
				this.bonusList.add(new StatBonus("SAV", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Psychology", 40));
				this.skillList.add(new Skill("Deception", 50));
				this.skillList.add(new Skill("Disguise", 40));
				this.skillList.add(new Skill("Impersonation", 40)); 
				this.skillList.add(new Skill("Infosec", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Kinesics", 25)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Palming", 40));
				this.skillList.add(new Skill("Profession", "Con Schemes", 50));
				this.skillList.add(new Skill("Persuasion", 50));
			}
			break;
		case ControllerAsync:
			this.suggestedMotivations.add("+Self Control");
			this.suggestedMotivations.add("+Neurodiversity");
			this.suggestedMotivations.add("+Exploration");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Psychology", 30));
				this.skillList.add(new Skill("Control", 30));
				this.traitList.add("Psi (Level 2)"); 
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi", 3));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Psychology", 40)); 
				this.skillList.add(new Skill("Control", 50)); 
				this.skillList.add(new Skill("Deception", 25));
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Persuasion", 35)); 
				this.skillList.add(new Skill("Profession", "Psychotherapy", 50)); 
				this.skillList.add(new Skill("Psychosurgery", 20));
				this.traitList.add("Psi (Level 2)");
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi", 4));
			} else {
				this.bonusList.add(new StatBonus("WIL", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Psychology", 40));
				this.skillList.add(new Skill("Control", 50)); 
				this.skillList.add(new Skill("Deception", 40));
				this.skillList.add(new Skill("Intimidation", 25)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Persuasion", 45)); 
				this.skillList.add(new Skill("Profession", "Psychotherapy", 50)); 
				this.skillList.add(new Skill("Psychosurgery", 25)); 
				this.skillList.add(new Skill("Sense", 25));
				this.traitList.add("Psi (Level 2)"); 
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi", 6));
			}
			break;
		case CovertOps:
			this.suggestedMotivations.add("–TITAN Tech");
			this.suggestedMotivations.add("+Survival");
			this.suggestedMotivations.add("+Explosions");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Blades", 30));
				this.skillList.add(new Skill("Infiltration", 40));
				this.skillList.add(new Skill("Profession", "Squad Tactics", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Blades", 20));
				this.skillList.add(new Skill("Demolitions", 25));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Infiltration", 50)); 
				this.skillList.add(new Skill("Kinetic Weapons", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Profession", "Squad Tactics", 50)); 
				this.skillList.add(new Skill("Unarmed Combat", 30));
			} else {
				this.bonusList.add(new StatBonus("COO", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Blades", 30)); 
				this.skillList.add(new Skill("Climbing", 30));
				this.skillList.add(new Skill("Demolitions", 30));
				this.skillList.add(new Skill("Fray", 30));
				this.skillList.add(new Skill("Infiltration", 50));
				this.skillList.add(new Skill("Infosec", 25)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Kinetic Weapons", 40));
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Squad Tactics", 50));
				this.skillList.add(new Skill("Unarmed Combat", 40));
			}
			break;
		case Dealer:
			this.suggestedMotivations.add("+Hard Work");
			this.suggestedMotivations.add("+Hypercapitalism");
			this.suggestedMotivations.add("+Personal Career");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Kinesics", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 30));
				this.skillList.add(new Skill("Persuasion", 40));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Beam Weapons", 20)); 
				this.skillList.add(new Skill("Deception", 25));
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Kinesics", 40));
				this.skillList.add(new Skill("Networking", "Choice", 40));
				this.skillList.add(new Skill("Profession", "Haggling", 50));
				this.skillList.add(new Skill("Perception", 30));
				this.skillList.add(new Skill("Persuasion", 50));
			} else {
				this.bonusList.add(new StatBonus("SAV", 5));
				this.choiceList.add("+100 Rep to the organization of your choosing");
				this.skillList.add(new Skill("Academics", "Economics", 30)); 
				this.skillList.add(new Skill("Beam Weapons", 20));
				this.skillList.add(new Skill("Deception", 25));
				this.skillList.add(new Skill("Fray", 15));
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Kinesics", 40));
				this.skillList.add(new Skill("Networking", "Choice", 50)); 
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 20)); 
				this.skillList.add(new Skill("Perception", 30));
				this.skillList.add(new Skill("Profession", "Haggling", 50));
				this.skillList.add(new Skill("Profession", "Social Engineering", 40)); 
				this.skillList.add(new Skill("Persuasion", 50));
			}
			break;
		case EgoHunter:
			this.suggestedMotivations.add("+The Hunt");
			this.suggestedMotivations.add("+Self Reliance");
			this.suggestedMotivations.add("+Justice");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Investigation", 30)); 
				this.skillList.add(new Skill("Kinesics", 40));
				this.skillList.add(new Skill("Profession", "Skip Tracing", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Infosec", 15));
				this.skillList.add(new Skill("Investigation", 30)); 
				this.skillList.add(new Skill("Kinesics", 50)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 15)); 
				this.skillList.add(new Skill("Profession", "Skip Tracing", 50));
				this.skillList.add(new Skill("Research", 40));
				this.skillList.add(new Skill("Spray Weapons", 25));
				this.skillList.add(new Skill("Unarmed Combat", 30));
			} else {
				this.bonusList.add(new StatBonus("INT", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Fray", 25)); 
				this.skillList.add(new Skill("Infosec", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Investigation", 40));
				this.skillList.add(new Skill("Kinesics", 50)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 20));
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Skip Tracing", 50));
				this.skillList.add(new Skill("Research", 50)); 
				this.skillList.add(new Skill("Spray Weapons", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 30));
			}
			break;
		case Enforcer:
			this.suggestedMotivations.add("+Cartel Growth");
			this.suggestedMotivations.add("+Fascism");
			this.suggestedMotivations.add("+Stability");
			this.suggestedMotivations.add("+Self Reliance");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Intimidation", 40));
				this.skillList.add(new Skill("Profession", "Enforcement", 30));
				this.skillList.add(new Skill("Unarmed Combat", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Clubs", 30)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Freerunning", 30)); 
				this.skillList.add(new Skill("Intimidation", 50));
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Enforcement", 50)); 
				this.skillList.add(new Skill("Spray Weapons", 25)); 
				this.skillList.add(new Skill("Unarmed Combat", 30));
			} else {
				this.bonusList.add(new StatBonus("SOM", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Clubs", 30));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Freerunning", 30));
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Intimidation", 50)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Palming", 20));
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Profession", "Enforcement", 50));
				this.skillList.add(new Skill("Spray Weapons", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 45));
			}
			break;
		case Explorer:
			this.suggestedMotivations.add("+Alien Contact");
			this.suggestedMotivations.add("+Education");
			this.suggestedMotivations.add("+Exploration");
			this.suggestedMotivations.add("+Survival");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Freerunning", 40));
				this.skillList.add(new Skill("Investigation", 30)); 
				this.skillList.add(new Skill("Profession", "Gatecrashing", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Climbing", 30)); 
				this.skillList.add(new Skill("Fray", 25));
				this.skillList.add(new Skill("Freerunning", 35)); 
				this.skillList.add(new Skill("Investigation", 20)); 
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Profession", "Gatecrashing", 50)); 
				this.skillList.add(new Skill("Scrounging", 40));
				this.skillList.add(new Skill("Swimming", 20));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("SOM", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Climbing", 40)); 
				this.skillList.add(new Skill("Fray", 25)); 
				this.skillList.add(new Skill("Freerunning", 40)); 
				this.skillList.add(new Skill("Infiltration", 25)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Investigation", 20));
				this.skillList.add(new Skill("Kinetic Weapons", 30)); 
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Profession", "Gatecrashing", 50)); 
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.skillList.add(new Skill("Scrounging", 40)); 
				this.skillList.add(new Skill("Swimming", 30));
			}
			break;
		case Face:
			this.suggestedMotivations.add("+Thrill Seeking");
			this.suggestedMotivations.add("+Fame");
			this.suggestedMotivations.add("+Hedonism");
			this.suggestedMotivations.add("+Personal Career");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Kinesics", 30)); 
				this.skillList.add(new Skill("Profession", "Social Engineering", 30));
				this.skillList.add(new Skill("Persuasion", 40));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Deception", 35));
				this.skillList.add(new Skill("Kinesics", 30));
				this.skillList.add(new Skill("Language", "Choice", 40));
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Social Engineering", 50)); 
				this.skillList.add(new Skill("Persuasion", 50)); 
				this.skillList.add(new Skill("Protocol", 30));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("SAV", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Psychology", 30));
				this.skillList.add(new Skill("Deception", 40)); 
				this.skillList.add(new Skill("Fray", 15)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Kinesics", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 40));
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Social Engineering", 50));
				this.skillList.add(new Skill("Persuasion", 50)); 
				this.skillList.add(new Skill("Protocol", 35));
			}
			break;
		case Genehacker:
			this.suggestedMotivations.add("+Artistic Expression");
			this.suggestedMotivations.add("+Research");
			this.suggestedMotivations.add("+Morphological Freedom");
			this.suggestedMotivations.add("+Uplift Rights");
			this.suggestedMotivations.add("+Science!");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Genetics", 30));
				this.skillList.add(new Skill("Medicine", "Choice", 40));
				this.skillList.add(new Skill("Medicine", "Choice", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Genetics", 50)); 
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Animal Handling", 20)); 
				this.skillList.add(new Skill("Medicine", "Choice", 50));
				this.skillList.add(new Skill("Medicine", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Scientists", 20));
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Profession", "Lab Tech", 40));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("r-rep", 50));
				this.skillList.add(new Skill("Academics", "Genetics", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Animal Handling", 40));
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Interfacing", 20));
				this.skillList.add(new Skill("Investigation", 30)); 
				this.skillList.add(new Skill("Medicine", "Choice", 50)); 
				this.skillList.add(new Skill("Medicine", "Choice", 40));
				this.skillList.add(new Skill("Medicine", "Choice", 40));
				this.skillList.add(new Skill("Networking", "Scientists", 20));
				this.skillList.add(new Skill("Perception", 30));
				this.skillList.add(new Skill("Profession", "Lab Tech", 40));
			}
			break;
		case Hacker:
			this.suggestedMotivations.add("+Thrill Seeking");
			this.suggestedMotivations.add("-Hackers");
			this.suggestedMotivations.add("+Open Source");
			this.suggestedMotivations.add("+Owning Systems");
			this.suggestedMotivations.add("+Fame");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Infosec", 40)); 
				this.skillList.add(new Skill("Profession", "Mesh Security Ops", 30)); 
				this.skillList.add(new Skill("Programming", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.choiceList.add("Add Academics: Computer Science or Academics: Cryptography at 40");
				this.skillList.add(new Skill("Hardware", "Electronics", 15)); 
				this.skillList.add(new Skill("Infosec", 50));
				this.skillList.add(new Skill("Interfacing", 30));
				this.skillList.add(new Skill("Networking", "Criminal", 20)); 
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Mesh Security Ops", 50)); 
				this.skillList.add(new Skill("Programming", 40));
				this.skillList.add(new Skill("Research", 30));
			} else {
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("g-rep", 50));
				this.skillList.add(new Skill("Academics", "Computer Science", 40)); 
				this.skillList.add(new Skill("Academics", "Cryptography", 40)); 
				this.skillList.add(new Skill("Fray", 10));
				this.skillList.add(new Skill("Hardware", "Electronics", 25)); 
				this.skillList.add(new Skill("Infiltration", 20));
				this.skillList.add(new Skill("Infosec", 50)); 
				this.skillList.add(new Skill("Interfacing", 50)); 
				this.skillList.add(new Skill("Networking", "Criminal", 30)); 
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Profession", "Mesh Security Ops", 40)); 
				this.skillList.add(new Skill("Profession", "Social Engineering", 30));
				this.skillList.add(new Skill("Programming", 50));
				this.skillList.add(new Skill("Research", 40));
			}
			break;
		case Icon:
			this.suggestedMotivations.add("+Thrill Seeking");
			this.suggestedMotivations.add("+Hedonism");
			this.suggestedMotivations.add("+Fame");
			this.suggestedMotivations.add("+Art");
			this.suggestedMotivations.add("+Personal Career");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Art", "Choice", 40));
				this.skillList.add(new Skill("Kinesics", 30)); 
				this.skillList.add(new Skill("Protocol", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Art", "Choice", 50));
				this.skillList.add(new Skill("Disguise", 30)); 
				this.skillList.add(new Skill("Impersonation", 20));
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Kinesics", 45));
				this.skillList.add(new Skill("Perception", 30));
				this.skillList.add(new Skill("Persuasion", 40)); 
				this.skillList.add(new Skill("Protocol", 40));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("SAV", 5));
				this.bonusList.add(new StatBonus("f-rep", 50));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Art", "Choice", 50));
				this.skillList.add(new Skill("Art", "Choice", 30));
				this.skillList.add(new Skill("Disguise", 40));
				this.skillList.add(new Skill("Fray", 10));
				this.skillList.add(new Skill("Impersonation", 25));
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Kinesics", 45));
				this.skillList.add(new Skill("Networking", "Choice", 25)); 
				this.skillList.add(new Skill("Networking", "Socialites", 30));
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Persuasion", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 30));
				this.skillList.add(new Skill("Protocol", 40));
			}
			break;
		case Investigator:
			this.suggestedMotivations.add("+Self Reliance");
			this.suggestedMotivations.add("+The Hunt");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Investigation", 40)); 
				this.skillList.add(new Skill("Profession", "Forensics", 30));
				this.skillList.add(new Skill("Research", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Infosec", 40));
				this.skillList.add(new Skill("Investigation", 50));
				this.skillList.add(new Skill("Kinesics", 30)); 
				this.skillList.add(new Skill("Kinetic Weapons", 20));
				this.skillList.add(new Skill("Perception", 25)); 
				this.skillList.add(new Skill("Profession", "Forensics", 50)); 
				this.skillList.add(new Skill("Profession", "Police Procedures", 40));
				this.skillList.add(new Skill("Research", 40));
			} else {
				this.bonusList.add(new StatBonus("INT", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Infosec", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Intimidation", 30));
				this.skillList.add(new Skill("Investigation", 50));
				this.skillList.add(new Skill("Kinesics", 30)); 
				this.skillList.add(new Skill("Kinetic Weapons", 30));
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Networking", "Choice", 20));
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Forensics", 50));
				this.skillList.add(new Skill("Profession", "Police Procedures", 40));
				this.skillList.add(new Skill("Research", 40));
				this.skillList.add(new Skill("Unarmed Combat", 25));
			}
			break;
		case Journo:
			this.suggestedMotivations.add("+Sousveillance");
			this.suggestedMotivations.add("+Transparency");
			this.suggestedMotivations.add("-Censorship");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Investigation", 30));
				this.skillList.add(new Skill("Persuasion", 40));
				this.skillList.add(new Skill("Profession", "Journalism", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Art", "Performance", 40)); 
				this.skillList.add(new Skill("Intimidation", 20)); 
				this.skillList.add(new Skill("Investigation", 40));
				this.skillList.add(new Skill("Networking", "Choice", 20));
				this.skillList.add(new Skill("Persuasion", 50));
				this.skillList.add(new Skill("Perception", 35)); 
				this.skillList.add(new Skill("Profession", "Journalism", 50)); 
				this.skillList.add(new Skill("Research", 40));
			} else {
				this.bonusList.add(new StatBonus("SAV", 5));
				this.choiceList.add("+100 Rep to the organization of your choosing");
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Art", "Performance", 40));
				this.skillList.add(new Skill("Disguise", 25));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Intimidation", 30)); 
				this.skillList.add(new Skill("Investigation", 40)); 
				this.skillList.add(new Skill("Kinesics", 20)); 
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 25));
				this.skillList.add(new Skill("Persuasion", 50)); 
				this.skillList.add(new Skill("Profession", "Journalism", 50)); 
				this.skillList.add(new Skill("Research", 40));
			}
			break;
		case Medic:
			this.suggestedMotivations.add("+Helping Others");
			this.suggestedMotivations.add("-Violence");
			if (this.ppCost == 1){
				 this.skillList.add(new Skill("Medicine", "Paramedic", 40)); 
				 this.skillList.add(new Skill("Medicine", "Choice", 30)); 
				 this.skillList.add(new Skill("Profession", "Medical Care", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Hardware", "Implants", 25)); 
				this.skillList.add(new Skill("Medicine", "Paramedic", 50)); 
				this.skillList.add(new Skill("Medicine", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 20)); 
				this.skillList.add(new Skill("Perception", 35)); 
				this.skillList.add(new Skill("Persuasion", 20));
				this.skillList.add(new Skill("Profession", "Medical Care", 50));				
			} else {
				this.bonusList.add(new StatBonus("MOX", 2));
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("r-rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Fray", 30)); 
				this.skillList.add(new Skill("Hardware", "Implants", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Interfacing", 15)); 
				this.skillList.add(new Skill("Medicine", "Paramedic", 50)); 
				this.skillList.add(new Skill("Medicine", "Choice", 40));
				this.skillList.add(new Skill("Medicine", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 20)); 
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Persuasion", 20));
				this.skillList.add(new Skill("Profession", "Medical Care", 50));
			}
			break;
		case Pirate:
			this.suggestedMotivations.add("+Thrill Seeking");
			this.suggestedMotivations.add("+Survival");
			this.suggestedMotivations.add("+Wealth");
			this.suggestedMotivations.add("-Authority");
			if (this.ppCost == 1){
				this.bonusList.add(new StatBonus("MOX", 1));
				this.skillList.add(new Skill("Infiltration", 15)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Pilot", "Spacecraft", 40));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("g-rep", 50));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Gunnery", 20)); 
				this.skillList.add(new Skill("Infiltration", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Kinetic Weapons", 20)); 
				this.skillList.add(new Skill("Networking", "Criminals", 20)); 
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Pilot", "Spacecraft", 40)); 
				this.skillList.add(new Skill("Profession", "Piracy", 50)); 
				this.skillList.add(new Skill("Scrounging", 30));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("COO", 5));
				this.bonusList.add(new StatBonus("g-rep", 50));
				this.skillList.add(new Skill("Art", "Choice", 30)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Gunnery", 30));
				this.skillList.add(new Skill("Infiltration", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Intimidation", 20)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Kinetic Weapons", 30)); 
				this.skillList.add(new Skill("Networking", "Criminals", 30)); 
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Pilot", "Spacecraft", 50));
				this.skillList.add(new Skill("Profession", "Piracy", 50)); 
				this.skillList.add(new Skill("Scrounging", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 20));
			}
			break;
		case Psychosurgeon:
			this.suggestedMotivations.add("+Helping Others");
			this.suggestedMotivations.add("+Neurodiversity");
			this.suggestedMotivations.add("-Madness");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Psychology", 30)); 
				this.skillList.add(new Skill("Medicine", "Psychiatry", 30)); 
				this.skillList.add(new Skill("Psychosurgery", 40));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Psychology", 50));
				this.skillList.add(new Skill("Kinesics", 30)); 
				this.skillList.add(new Skill("Medicine", "Psychiatry", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 35)); 
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Persuasion", 20)); 
				this.skillList.add(new Skill("Profession", "Psychotherapy", 40)); 
				this.skillList.add(new Skill("Psychosurgery", 50));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Psychology", 50)); 
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Deception", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Investigation", 25)); 
				this.skillList.add(new Skill("Kinesics", 40)); 
				this.skillList.add(new Skill("Medicine", "Psychiatry", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 35)); 
				this.skillList.add(new Skill("Perception", 30));
				this.skillList.add(new Skill("Persuasion", 30));  
				this.skillList.add(new Skill("Profession", "Psychotherapy", 50)); 
				this.skillList.add(new Skill("Psychosurgery", 50));
			}
			break;
		case SavantAsync:
			this.suggestedMotivations.add("+Self Control");
			this.suggestedMotivations.add("+Introspection");
			this.suggestedMotivations.add("+Neurodiversity");
			this.suggestedMotivations.add("+Personal Development");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Investigation", 30));
				this.traitList.add("Psi (Level 1)"); 
				this.choiceList.add("Add one Mental Disorder trait of your choice"); 
				this.bonusList.add(new StatBonus("psi", 4));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Art", "Choice", 30)); 
				this.skillList.add(new Skill("Hardware", "Choice", 15));
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Investigation", 50)); 
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Perception", 40));
				this.traitList.add("Psi (Level 1)"); 
				this.choiceList.add("Add one Mental Disorder trait of your choice"); 
				this.bonusList.add(new StatBonus("psi", 5));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("COG", 5)); 
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Art", "Choice", 40));
				this.skillList.add(new Skill("Deception", 30)); 
				this.skillList.add(new Skill("Hardware", "Choice", 25));
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Investigation", 50)); 
				this.skillList.add(new Skill("Kinesics", 35));
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Networking", "Choice", 25)); 
				this.skillList.add(new Skill("Perception", 50));
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.traitList.add("Psi (Level 1)"); 
				this.choiceList.add("Add one Mental Disorder trait of your choice"); 
				this.bonusList.add(new StatBonus("psi", 5));
			}
			break;
		case ScannerAsync:
			this.suggestedMotivations.add("+Personal Development");
			this.suggestedMotivations.add("+Neurodiversity");
			this.suggestedMotivations.add("+Exploration");
			this.suggestedMotivations.add("+Self Control");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Sense", 30));
				this.traitList.add("Psi (Level 2)"); 
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi", 3));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Impersonation", 35)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Kinesics", 35)); 
				this.skillList.add(new Skill("Perception", 40)); 
				this.skillList.add(new Skill("Sense", 50)); 
				this.traitList.add("Psi (Level 2)"); 
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi", 4));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("INT", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Impersonation", 35));  
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Investigation", 30)); 
				this.skillList.add(new Skill("Kinesics", 35)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 50)); 
				this.skillList.add(new Skill("Persuasion", 25)); 
				this.skillList.add(new Skill("Profession", "Choice", 30)); 
				this.skillList.add(new Skill("Sense", 50));
				this.traitList.add("Psi (Level 2)"); 
				this.bonusList.add(new StatBonus("Mental Disorder", 2));
				this.bonusList.add(new StatBonus("psi", 6));
			}
			break;
		case Scavenger:
			this.suggestedMotivations.add("+Survival");
			this.suggestedMotivations.add("+Wealth");
			this.suggestedMotivations.add("-Authority");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Hardware", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Scavenging", 30)); 
				this.skillList.add(new Skill("Scrounging", 40));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Art", "Sculpture", 40)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40));
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Perception", 35));
				this.skillList.add(new Skill("Pilot", "Choice", 40)); 
				this.skillList.add(new Skill("Profession", "Scavenging", 50)); 
				this.skillList.add(new Skill("Scrounging", 50));
			} else {
				this.bonusList.add(new StatBonus("MOX", 2));
				this.bonusList.add(new StatBonus("COG", 5)); 
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 25)); 
				this.skillList.add(new Skill("Art", "Sculpture", 40)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Hardware", "Choice", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.choiceList.add("Add two Networking skill of your choice at 30");  
				this.skillList.add(new Skill("Perception", 40)); 
				this.skillList.add(new Skill("Pilot", "Choice", 40)); 
				this.skillList.add(new Skill("Profession", "Scavenging", 40));
				this.skillList.add(new Skill("Scrounging", 50));
			}
			break;
		case Scientist:
			this.suggestedMotivations.add("+Science!");
			this.suggestedMotivations.add("+Research");
			this.suggestedMotivations.add("+Technoprogressivism");
			this.suggestedMotivations.add("-Bioconservatism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Investigation", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("r-rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 50)); 
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Investigation", 40)); 
				this.skillList.add(new Skill("Networking", "Scientists", 35)); 
				this.skillList.add(new Skill("Perception", 40)); 
				this.skillList.add(new Skill("Profession", "Lab Tech", 40)); 
				this.skillList.add(new Skill("Programming", 30)); 
				this.skillList.add(new Skill("Research", 40));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("r-rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 50)); 
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Animal Handling", 20)); 
				this.skillList.add(new Skill("Fray", 15));
				this.skillList.add(new Skill("Hardware", "Electronics", 25)); 
				this.skillList.add(new Skill("Investigation", 50));
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Networking", "Scientists", 40));
				this.skillList.add(new Skill("Perception", 40)); 
				this.skillList.add(new Skill("Profession", "Lab Tech", 40)); 
				this.skillList.add(new Skill("Programming", 40)); 
				this.skillList.add(new Skill("Research", 40));
			}
			break;
		case SmartAnimalHandler:
			this.suggestedMotivations.add("+Nano-ecology");
			this.suggestedMotivations.add("+Research");
			this.suggestedMotivations.add("+Uplift Rights");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Animal Handling", 40)); 
				this.skillList.add(new Skill("Medicine", "Veterinary", 30)); 
				this.skillList.add(new Skill("Profession", "Smart Animal Training", 30)); 
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Zoology", 40)); 
				this.skillList.add(new Skill("Animal Handling", 50));
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Intimidation", 20)); 
				this.skillList.add(new Skill("Kinesics", 40)); 
				this.skillList.add(new Skill("Medicine", "Veterinary", 40));
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Smart Animal Training", 50)); 
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("INT", 5)); 
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Zoology", 40)); 
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Animal Handling", 50));
				this.skillList.add(new Skill("Fray", 30)); 
				this.skillList.add(new Skill("Intimidation", 30)); 
				this.skillList.add(new Skill("Kinesics", 30)); 
				this.skillList.add(new Skill("Medicine", "Veterinary", 50));
				this.skillList.add(new Skill("Networking", "Choice", 30));
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Profession", "Smart Animal Training", 50)); 
				this.skillList.add(new Skill("Profession", "Choice", 30));
				this.skillList.add(new Skill("Scrounging", 30));
			}
			break;
		case Smuggler:
			this.suggestedMotivations.add("+Personal Career");
			this.suggestedMotivations.add("+Survival");
			this.suggestedMotivations.add("+Wealth");
			this.suggestedMotivations.add("-Authority");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Persuasion", 40));
				this.skillList.add(new Skill("Profession", "Smuggling", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Deception", 40));
				this.skillList.add(new Skill("Interest", "Black Markets", 40));
				this.skillList.add(new Skill("Kinesics", 25)); 
				this.skillList.add(new Skill("Networking", "Choice", 40));
				this.skillList.add(new Skill("Networking", "Choice", 20)); 
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Persuasion", 50)); 
				this.skillList.add(new Skill("Profession", "Smuggling", 50));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("INT", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Deception", 40));
				this.skillList.add(new Skill("Fray", 30));
				this.skillList.add(new Skill("Interest", "Black Markets", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Kinesics", 25)); 
				this.skillList.add(new Skill("Networking", "Choice", 40));
				this.skillList.add(new Skill("Networking", "Choice", 40));
				this.skillList.add(new Skill("Perception", 30));
				this.skillList.add(new Skill("Persuasion", 50)); 
				this.skillList.add(new Skill("Profession", "Smuggling", 50));
				this.skillList.add(new Skill("Protocol", 25));
			}
			break;
		case Soldier:
			this.suggestedMotivations.add("+Personal Development");
			this.suggestedMotivations.add("+Survival");
			this.suggestedMotivations.add("+Duty");
			this.suggestedMotivations.add("+Victory");
			this.suggestedMotivations.add("-Peace");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Kinetic Weapons", 40)); 
				this.skillList.add(new Skill("Profession", "Squad Tactics", 30));
				this.skillList.add(new Skill("Unarmed Combat", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Climbing", 25)); 
				this.skillList.add(new Skill("Fray", 20));
				this.skillList.add(new Skill("Freerunning", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Kinetic Weapons", 50)); 
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Squad Tactics", 50)); 
				this.skillList.add(new Skill("Throwing Weapons", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 40));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("SOM", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Blades", 30)); 
				this.skillList.add(new Skill("Climbing", 40)); 
				this.skillList.add(new Skill("Fray", 30)); 
				this.skillList.add(new Skill("Freerunning", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Kinetic Weapons", 50)); 
				this.skillList.add(new Skill("Language", "Choice", 40));
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Profession", "Squad Tactics", 50)); 
				this.skillList.add(new Skill("Throwing Weapons", 30));
				this.skillList.add(new Skill("Unarmed Combat", 40));
			}
			break;
		case Spy:
			this.suggestedMotivations.add("+Secret Identity");
			this.suggestedMotivations.add("-Secrets");			
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Deception", 40)); 
				this.skillList.add(new Skill("Infiltration", 30)); 
				this.skillList.add(new Skill("Profession", "Spycraft", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Cryptography", 40)); 
				this.skillList.add(new Skill("Deception", 50)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Impersonation", 25)); 
				this.skillList.add(new Skill("Infiltration", 40)); 
				this.skillList.add(new Skill("Infosec", 40)); 
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Profession", "Spycraft", 50));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("SAV", 5)); 
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Cryptography", 40)); 
				this.skillList.add(new Skill("Deception", 50)); 
				this.skillList.add(new Skill("Fray", 30)); 
				this.skillList.add(new Skill("Impersonation", 25)); 
				this.skillList.add(new Skill("Infiltration", 40)); 
				this.skillList.add(new Skill("Infosec", 40)); 
				this.skillList.add(new Skill("Investigation", 25)); 
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Palming", 30)); 
				this.skillList.add(new Skill("Perception", 40)); 
				this.skillList.add(new Skill("Profession", "Spycraft", 50));
			}
			break;
		case Techie:
			this.suggestedMotivations.add("+Science!");
			this.suggestedMotivations.add("+Sousveillance");
			this.suggestedMotivations.add("+Technoprogressivism");
			this.suggestedMotivations.add("+Education");
			this.suggestedMotivations.add("+DIY");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Profession", "Choice", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Hardware", "Choice", 50)); 
				this.skillList.add(new Skill("Hardware", "Choice", 25)); 
				this.skillList.add(new Skill("Interfacing", 40)); 
				this.skillList.add(new Skill("Infosec", 20)); 
				this.skillList.add(new Skill("Networking", "Choice", 20));
				this.skillList.add(new Skill("Perception", 20)); 
				this.skillList.add(new Skill("Profession", "Choice", 50));
				this.skillList.add(new Skill("Programming", 30));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("COG", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Fray", 15)); 
				this.skillList.add(new Skill("Hardware", "Choice", 50)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Interfacing", 45));
				this.skillList.add(new Skill("Infosec", 25)); 
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Networking", "Choice", 35)); 
				this.skillList.add(new Skill("Pilot", "Choice", 30));
				this.skillList.add(new Skill("Profession", "Choice", 50)); 
				this.skillList.add(new Skill("Programming", 40));
			}
			break;
		case Thief:
			this.suggestedMotivations.add("+Wealth");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Infiltration", 30)); 
				this.skillList.add(new Skill("Palming", 40)); 
				this.skillList.add(new Skill("Profession", "Thieving", 30));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("g-rep", 50));
				this.skillList.add(new Skill("Climbing", 25)); 
				this.skillList.add(new Skill("Fray", 20)); 
				this.skillList.add(new Skill("Infiltration", 40));
				this.skillList.add(new Skill("Infosec", 20)); 
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Networking", "Criminals", 30)); 
				this.skillList.add(new Skill("Palming", 50)); 
				this.skillList.add(new Skill("Perception", 20));
				this.skillList.add(new Skill("Profession", "Thieving", 50));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("COO", 5));
				this.bonusList.add(new StatBonus("g-rep", 50));
				this.skillList.add(new Skill("Art", "Choice", 30));
				this.skillList.add(new Skill("Blades", 20));
				this.skillList.add(new Skill("Climbing", 40));
				this.skillList.add(new Skill("Fray", 30));
				this.skillList.add(new Skill("Hardware", "Electronics", 15));
				this.skillList.add(new Skill("Infiltration", 40));
				this.skillList.add(new Skill("Infosec", 25)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Networking", "Criminals", 30)); 
				this.skillList.add(new Skill("Palming", 50)); 
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Profession", "Thieving", 50));
			}
			break;
		case Wrecker:
			this.suggestedMotivations.add("–TITANs");
			this.suggestedMotivations.add("+Survival");
			this.suggestedMotivations.add("+Explosions");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Demolitions", 30)); 
				this.skillList.add(new Skill("Interest", "TITAN Tech", 30));
				this.skillList.add(new Skill("Seeker Weapons", 40));
			} else if (this.ppCost == 3){
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Demolitions", 40)); 
				this.skillList.add(new Skill("Fray", 25));
				this.skillList.add(new Skill("Infiltration", 30));
				this.skillList.add(new Skill("Interest", "TITAN Tech", 50)); 
				this.skillList.add(new Skill("Kinetic Weapons", 30)); 
				this.skillList.add(new Skill("Perception", 30)); 
				this.skillList.add(new Skill("Profession", "Squad Tactics", 40)); 
				this.skillList.add(new Skill("Seeker Weapons", 50));
			} else {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("COO", 5));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Demolitions", 40)); 
				this.skillList.add(new Skill("Fray", 40));
				this.skillList.add(new Skill("Infiltration", 30));
				this.skillList.add(new Skill("Infosec", 20)); 
				this.skillList.add(new Skill("Interest", "TITAN Tech", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Kinetic Weapons", 40));
				this.skillList.add(new Skill("Perception", 30));
				this.skillList.add(new Skill("Profession", "Wrecking Machines", 30)); 
				this.skillList.add(new Skill("Profession", "Squad Tactics", 40)); 
				this.skillList.add(new Skill("Seeker Weapons", 50));
				this.skillList.add(new Skill("Throwing Weapons", 30));
			}
			break;
		default:
			break;
		
		}
		
	}

}
