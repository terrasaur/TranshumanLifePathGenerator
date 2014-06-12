package lifepath;


import character.Skill;

public class CustomizationPackage extends LifePathPackage {

	List packageName;
	
	public CustomizationPackage(List name, Integer cost) {
		super(name.text, cost);
		this.packageName = name;
		this.pkgType = Type.Customization;
		if (this.ppCost != 1 ){
			System.out.println("Error: You have the wrong cost for a package: " + this.ppCost);
			this.ppCost = 1;
		}
		this.getPackageContents();
	}
	
	public CustomizationPackage(String name, Integer cost) {
		super(name, cost);
		this.packageName = getPackageByLabel(name);
		this.pkgType = Type.Customization;
		if (this.ppCost != 1){
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
		Artist          ("Artist"),
		Async           ("Async"),
		AsyncAdept      ("Async Adept"),
		Athletics       ("Athletics"),
		ComputerTraining("Computer Training"),
		Connected       ("Connected"),
		EssentialSkills ("Essential Skills"),
		Gearhead        ("Gearhead"),
		HeavyWeapons    ("Heavy Weapons Training"),
		JackOfAllTrades ("Jack-of-all-Trades"),
		Lucky           ("Lucky"),
		MartialArts     ("Martial Arts"),
		Mentalist       ("Mentalist"),
		Networker       ("Networker"),
		Paramedic       ("Paramedic"),
		Slacker         ("Slacker"),
		Sneaker         ("Sneaker"),
		SocialButterfly ("Social Butterfly"),
		Spacer          ("Spacer"),
		Student         ("Student"),
		SurvivalTraining("Survival Training"),
		TechTraining    ("Tech Training"),
		WeaponsTraining ("Weapons Training");		
		
		String text;
		
		private List(String s){
			this.text = s;
		}
		
		@Override
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
	 * Looks up a package by the label string. If it doesn't exist, returns null.
	 * @param label A string of the package name
	 * @return The package's list name by enum value
	 */
	public static List getPackageByLabel(String label) {
		for (List l : List.values()){
			if (l.equals(label))
				return l;
		}
		return null;
	}


	/**
	 * Assigns package contents.
	 */
	private void getPackageContents() {
		switch (this.packageName){
		case Artist:
			this.skillList.add(new Skill("Art", "Choice", 40)); 
			this.skillList.add(new Skill("Disguise", 30)); 
			this.skillList.add(new Skill("Interest", "Choice", 30));
			break;
		case Async:
			this.skillList.add(new Skill("Interest", "Choice", 45));
			this.traitList.add("Psi (Level 1)"); 
			this.bonusList.add(new StatBonus("Mental Disorder", 1));
			this.bonusList.add(new StatBonus("psi", 5));
			this.choiceList.add("Add Psi Chameleon or Psi Defense (Level 1)"); 
			break;
		case AsyncAdept:
			this.choiceList.add("Add any one Psi skill at 40"); 
			this.traitList.add("Psi (Level 2)"); 
			this.bonusList.add(new StatBonus("Mental Disorder", 2));
			this.bonusList.add(new StatBonus("psi", 7));
			break;
		case Athletics:
			this.skillList.add(new Skill("Climbing", 30)); 
			this.skillList.add(new Skill("Freerunning", 40));
			this.skillList.add(new Skill("Swimming", 30));
			break;
		case ComputerTraining:
			this.skillList.add(new Skill("Infosec", 40)); 
			this.skillList.add(new Skill("Interfacing", 30)); 
			this.skillList.add(new Skill("Programming", 30));
			break;
		case Connected:
			this.skillList.add(new Skill("Networking", "Choice", 40)); 
			this.choiceList.add("Add any one skill at 40");  
			this.choiceList.add("Add either the Allies or Patron trait"); 
			this.creditsMod = 30000;
			break;
		case EssentialSkills:
			this.skillList.add(new Skill("Fray", 30)); 
			this.skillList.add(new Skill("Networking", "Choice", 30)); 
			this.skillList.add(new Skill("Perception", 40));
			break;
		case Gearhead:
			this.skillList.add(new Skill("Hardware", "Choice", 40));
			this.creditsMod = 60000;
			break;
		case HeavyWeapons:
			this.skillList.add(new Skill("Demolitions", 30)); 
			this.skillList.add(new Skill("Gunnery", 30)); 
			this.skillList.add(new Skill("Seeker Weapons", 40));
			break;
		case JackOfAllTrades:
			this.choiceList.add("Add any one skill at 40");  
			this.choiceList.add("Add two one skills at 30"); 
			break;
		case Lucky:			
			this.choiceList.add("Add any one skill at 30");  
			this.choiceList.add("Add two one skills at 25");
			this.bonusList.add(new StatBonus("MOX", 3));
			break;
		case MartialArts:
			this.skillList.add(new Skill("Blades", 30));  
			this.skillList.add(new Skill("Throwing Weapons", 30)); 
			this.skillList.add(new Skill("Unarmed Combat", 40));
			break;
		case Mentalist:
			this.choiceList.add("Add any two Psi skills at 40"); 
			this.bonusList.add(new StatBonus("psi", 4));
			break;
		case Networker:
			this.choiceList.add("+100 Rep to any organizations of your choosing");
			this.skillList.add(new Skill("Networking", "Choice", 30));
			this.skillList.add(new Skill("Networking", "Choice", 30));
			this.skillList.add(new Skill("Networking", "Choice", 30));
			break;
		case Paramedic:
			this.skillList.add(new Skill("Medicine", "Paramedic", 40)); 
			this.skillList.add(new Skill("Medicine", "Nanomedicine", 30));
			this.skillList.add(new Skill("Programming", 30));
			break;
		case Slacker:
			this.skillList.add(new Skill("Interest", "Choice", 40)); 
			this.skillList.add(new Skill("Interest", "Choice", 30));
			this.skillList.add(new Skill("Scrounging", 30));
			break;
		case Sneaker:
			this.skillList.add(new Skill("Disguise", 40));  
			this.skillList.add(new Skill("Impersonation", 30)); 
			this.skillList.add(new Skill("Infiltration", 30));
			break;
		case SocialButterfly:
			this.skillList.add(new Skill("Deception", 30)); 
			this.skillList.add(new Skill("Persuasion", 40));
			this.skillList.add(new Skill("Protocol", 30));
			break;
		case Spacer:
			this.skillList.add(new Skill("Free Fall", 40)); 
			this.skillList.add(new Skill("Kinesics", 30)); 
			this.skillList.add(new Skill("Medicine", "Choice", 30));
			break;
		case Student:
			this.skillList.add(new Skill("Academics", "Choice", 40)); 
			this.skillList.add(new Skill("Interest", "Choice", 30)); 
			this.skillList.add(new Skill("Research", 30));
			break;
		case SurvivalTraining:
			this.skillList.add(new Skill("Kinetic Weapons", 30)); 
			this.skillList.add(new Skill("Medicine", "Paramedic", 30)); 
			this.skillList.add(new Skill("Scrounging", 40)); 
			break;
		case TechTraining:
			this.skillList.add(new Skill("Hardware", "Choice", 40)); 
			this.skillList.add(new Skill("Hardware", "Choice", 30));
			this.skillList.add(new Skill("Programming", 30));
			break;
		case WeaponsTraining:
			this.skillList.add(new Skill("Beam Weapons", 30));  
			this.skillList.add(new Skill("Kinetic Weapons", 40));  
			this.skillList.add(new Skill("Spray Weapons", 30));
			break;
		default:			
			break;
		
		}
		
	}

}
