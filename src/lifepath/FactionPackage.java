package lifepath;

import character.EPCharacter;
import character.Skill;
import dice.Die;


public class FactionPackage extends LifePathPackage {
	List packageName;

	public FactionPackage(List name, Integer cost) {
		super(name.text, cost);
		this.packageName = name;
		this.pkgType = PackageType.Faction;
		if (this.ppCost != 1 && this.ppCost != 3){
			System.out.println("Error: You have the wrong cost for a package: " + this.ppCost);
			this.ppCost = 1;
		}
		this.getPackageContents();
	}


	public FactionPackage(String name, Integer cost) {
		super(name, cost);
		this.packageName = getPackageByLabel(name);
		this.pkgType = PackageType.Faction;
		if (this.ppCost != 1 && this.ppCost != 3){
			System.out.println("Error: You have the wrong cost for a package: " + this.ppCost);
			this.ppCost = 1;
		}
		this.getPackageContents();
	}
	
	/**
	 * List of all factions. Each package has a list enum containing its list objects.
	 * When creating a package, the class will look up the package in this list.
	 * If it can't find it, it will throw an error (or rather just complain a bit, 
	 * since I don't have realsies error handling like a chump)
	 * @author terrasaur
	 *
	 */
	public enum List {
		Anarchist        ("Anarchist"),
		Argonaut         ("Argonaut"),
		Barsoomian       ("Barsoomian"),
		Belter           ("Belter"),
		Bioconservative  ("Bioconservative"),
		Brinker          ("Brinker"),
		Criminal         ("Criminal"),
		Europan          ("Europan"),
		Exhuman          ("Exhuman"),
		Extropian        ("Extropian"),
		Hypercorp        ("Hypercorp"),
		Jovian           ("Jovian"),
		Lunar            ("Lunar"),
		MercurialInfolife("Mercurial: Infolife"),
		MercurialUplift  ("Mercurial: Uplift"),
		NanoEcologist    ("Nano-Ecologist"),
		Orbital          ("Orbital"),
		Outster          ("Out'ster"),
		Precautionist    ("Precautionist"),
		Preservationist  ("Preservationist"),
		Reclaimer        ("Reclaimer"),
		Ringer           ("Ringer"),
		Sapient          ("Sapient"),
		Scum             ("Scum"),
		Sifter           ("Sifter"),
		SingularitySeeker("Singularity Seeker"),
		Skimmer          ("Skimmer"),
		Socialite        ("Socialite"),
		Solarian         ("Solarian"),
		Titanian         ("Titanian"),
		Ultimate         ("Ultimate"),
		Venusian         ("Venusian"),
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

	/**
	 * Looks up a package by the label string. If it doesn't exist, returns null.
	 * This is public because of special conditions with the Fall event changing the 
	 * factions to a specific package. I am sure this could be handled in a more 
	 * graceful manner.
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
	 * Applies a package to a character. Overridden to add the faction package 
	 * to the character's faction.
	 * 
	 * If you have multiple faction packages, this will just append your factions
	 * onto one another. Congrats on that Jovian-Singularity Seeker-Anarchist
	 */
	protected void applyToCharacter(EPCharacter c) {
		super.applyToCharacter(c);
		c.setFaction(this.packageName.text);
	}

	
	/**
	 * Sets the contents for all Faction packages upon creation. This is a 
	 * massive switch and hand-coded because I am afraid of databases. But I did
	 * use regular expressions at least. They are in the LifePathPackage object.
	 */
	private void getPackageContents() {
		Die d10 = new Die(10);
		switch(this.packageName){
		case Anarchist:
			this.suggestedMotivations.add("+Morphological Freedom"); 
			this.suggestedMotivations.add("+Anarchism"); 
			this.suggestedMotivations.add("+Liberty");
			this.suggestedMotivations.add("-Authority");
			this.suggestedMotivations.add("+Community");
			this.suggestedMotivations.add("-Hypercapitalism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Political Science", 30));
				this.skillList.add(new Skill("Kinetic Weapons", 30)); 
				this.skillList.add(new Skill("Networking", "Autonomists", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("@-rep", 50)); 
				this.skillList.add(new Skill("Academics", "Political Science", 50));
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Infosec", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Kinetic Weapons", 20)); 
				this.skillList.add(new Skill("Networking", "Autonomists", 50)); 
				this.skillList.add(new Skill("Persuasion", 30)); 
				this.skillList.add(new Skill("Scrounging", 30));
			}
			break;
		case Argonaut:
			this.suggestedMotivations.add("+Open Source"); 
			this.suggestedMotivations.add("+Research"); 
			this.suggestedMotivations.add("+Technoprogressivism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 40));
				this.skillList.add(new Skill("Investigation", 30)); 
				this.skillList.add(new Skill("Research", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("r-rep", 50)); 
				this.skillList.add(new Skill("Academics", "Choice", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Investigation", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Scientists", 40)); 
				this.skillList.add(new Skill("Programming", 40)); 
				this.skillList.add(new Skill("Research", 40));
			}
			break;
		case Barsoomian:
			this.suggestedMotivations.add("+Barsoomian Movement"); 
			this.suggestedMotivations.add("+Technoprogressivism");
			this.suggestedMotivations.add("+Anarchism"); 
			this.suggestedMotivations.add("+Community");
			this.suggestedMotivations.add("-Hypercorps");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Navigation", 30)); 
				this.skillList.add(new Skill("Pilot", "Choice", 40));
				this.skillList.add(new Skill("Profession", "Choice", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("@-rep", 50)); 
				this.skillList.add(new Skill("Hardware", "Choice", 35)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Kinetic Weapons", 30)); 
				this.skillList.add(new Skill("Navigation", 30));
				this.skillList.add(new Skill("Networking", "Autonomists", 40)); 
				this.skillList.add(new Skill("Pilot", "Choice", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 50)); 
				this.skillList.add(new Skill("Scrounging", 30));
			}
			break;
		case Belter:
			this.suggestedMotivations.add("+Morphological Freedom"); 
			this.suggestedMotivations.add("+Extropianism"); 
			this.suggestedMotivations.add("+Hypercapitalism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Free Fall", 40)); 
				this.skillList.add(new Skill("Navigation", 30)); 
				this.skillList.add(new Skill("Profession", "Asteroid Mining", 30));				
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Free Fall", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Kinetic Weapons", 30)); 
				this.skillList.add(new Skill("Navigation", 30)); 
				this.skillList.add(new Skill("Networking", "Choice", 30));
				this.skillList.add(new Skill("Profession", "Asteroid Mining", 50));
				this.skillList.add(new Skill("Persuasion", 20)); 
				this.skillList.add(new Skill("Pilot", "Spacecraft", 30));
			}
			break;
		case Bioconservative:
			this.suggestedMotivations.add("+Bioconservatism"); 
			this.suggestedMotivations.add("+Religion"); 
			this.suggestedMotivations.add("-AGI Rights");
			this.suggestedMotivations.add("-Uplift Rights");
			this.suggestedMotivations.add("-X-Risks");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Freerunning", 30));
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Pilot", "Choice", 30));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Demolitions", 30)); 
				this.skillList.add(new Skill("Freerunning", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 50)); 
				this.skillList.add(new Skill("Kinetic Weapons", 40)); 
				this.skillList.add(new Skill("Medicine", "Paramedic", 30));
				this.skillList.add(new Skill("Pilot", "Choice", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Scrounging", 30));
			}
			break;
		case Brinker:
			this.suggestedMotivations.add("+Self Reliance"); 
			this.suggestedMotivations.add("+Bioconservatism"); 
			this.suggestedMotivations.add("+Exhumanism");
			this.suggestedMotivations.add("+Religion");
			this.suggestedMotivations.add("+Solitude");
			this.suggestedMotivations.add("+Privacy");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Pilot", "Spacecraft", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 30)); 			
			this.skillList.add(new Skill("Scrounging", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Gunnery", 20)); 
				this.skillList.add(new Skill("Hardware", "Industrial", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 25));
				this.skillList.add(new Skill("Pilot", "Spacecraft", 50)); 
				this.skillList.add(new Skill("Profession", "Choice", 50)); 
				this.skillList.add(new Skill("Scrounging", 40));
			}
			break;
		case Criminal:
			this.suggestedMotivations.add("+Thrill Seeking"); 
			this.suggestedMotivations.add("+Survival"); 
			this.suggestedMotivations.add("+Wealth");
			this.suggestedMotivations.add("-Law and Order");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Interest", "Criminal Groups", 30));
				this.skillList.add(new Skill("Palming", 30));
				this.skillList.add(new Skill("Intimidation", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("g-rep", 50)); 
				this.skillList.add(new Skill("Deception", 30)); 
				this.skillList.add(new Skill("Infiltration", 30)); 
				this.skillList.add(new Skill("Interest", "Criminal Groups", 50));
				 this.skillList.add(new Skill("Intimidation", 40)); 
				this.skillList.add(new Skill("Networking", "Criminals", 40));
				this.skillList.add(new Skill("Palming", 30));
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Unarmed Combat", 35));
			}
			break;
		case Europan:
			this.suggestedMotivations.add("+Morphological Freedom"); 
			this.suggestedMotivations.add("+Uplift Rights"); 
			this.suggestedMotivations.add("+Exploration");
			this.suggestedMotivations.add("-Bioconservatism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Pilot", "Submarine", 30)); 
				this.skillList.add(new Skill("Swimming", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Academics", "Choice", 20)); 
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Navigation", 35)); 
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Pilot", "Submarine", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 35));
				this.skillList.add(new Skill("Swimming", 50));
			}
			break;
		case Exhuman:
			this.suggestedMotivations.add("+Morphological Freedom"); 
			this.suggestedMotivations.add("+Exhumanism"); 
			this.suggestedMotivations.add("+Research");
			this.suggestedMotivations.add("+Personal Development");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Interest", "Exhumans", 30)); 
				this.skillList.add(new Skill("Medicine", "Biosculpting", 40)); 
				this.skillList.add(new Skill("Psychosurgery", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1)); 
				this.skillList.add(new Skill("Academics", "Genetics", 40));
				this.skillList.add(new Skill("Disguise", 15)); 
				this.skillList.add(new Skill("Interest", "Exhumans", 50)); 
				this.skillList.add(new Skill("Intimidation", 30)); 
				this.skillList.add(new Skill("Medicine", "Biosculpting", 50));
				this.skillList.add(new Skill("Medicine", "Choice", 30)); 
				this.skillList.add(new Skill("Psychosurgery", 30));
				this.skillList.add(new Skill("Unarmed Combat", 40));
			}
			break;		
		case Extropian:
			this.suggestedMotivations.add("+Morphological Freedom"); 
			this.suggestedMotivations.add("+Extropianism"); 
			this.suggestedMotivations.add("+Personal Development");
			this.suggestedMotivations.add("-Bioconservativism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Interest", "Cutting-Edge Technology", 30));				
				this.skillList.add(new Skill("Networking", "Autonomists", 30));
				this.skillList.add(new Skill("Persuasion", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("@-rep", 50)); 
				this.bonusList.add(new StatBonus("c-rep", 50)); 
				this.skillList.add(new Skill("Free Fall", 20)); 
				this.skillList.add(new Skill("Interest", "Cutting-Edge Technology", 30)); 
				this.skillList.add(new Skill("Interfacing", 20)); 
				this.skillList.add(new Skill("Kinesics", 30)); 
				this.skillList.add(new Skill("Networking", "Autonomists", 40));
				this.skillList.add(new Skill("Networking", "Hypercorps", 40)); 
				this.skillList.add(new Skill("Persuasion", 50)); 
				this.skillList.add(new Skill("Profession", "Choice", 50));
			}
			break;
		case Hypercorp:
			this.suggestedMotivations.add("+Hypercapitalism"); 
			this.suggestedMotivations.add("+Stability"); 
			this.suggestedMotivations.add("+Wealth");
			this.suggestedMotivations.add("-Anarchism");
			this.suggestedMotivations.add("-AGI Rights");
			this.suggestedMotivations.add("-Uplift Rights");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Economics", 30)); 
				this.skillList.add(new Skill("Persuasion", 30));
				this.skillList.add(new Skill("Protocol", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("c-rep", 50));
				this.skillList.add(new Skill("Academics", "Economics", 50)); 
				this.skillList.add(new Skill("Interfacing", 40)); 
				this.skillList.add(new Skill("Networking", "Hypercorps", 40));
				this.skillList.add(new Skill("Networking", "Media", 20)); 
				this.skillList.add(new Skill("Persuasion", 40));
				this.skillList.add(new Skill("Profession", "Choice", 40)); 
				this.skillList.add(new Skill("Protocol", 50));
			}
			break;
		case Jovian:
			this.suggestedMotivations.add("+Jovian Republic"); 
			this.suggestedMotivations.add("-AGI Rights"); 
			this.suggestedMotivations.add("-Transhumanism");
			this.suggestedMotivations.add("-Uplift Rights");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Pilot", "Choice", 30)); 
				this.skillList.add(new Skill("Profession", "Military Ops", 30)); 
				this.skillList.add(new Skill("Seeker Weapons", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("c-rep", 50));
				this.skillList.add(new Skill("Academics", "Military Science", 30)); 
				this.skillList.add(new Skill("Intimidation", 25)); 
				this.skillList.add(new Skill("Kinetic Weapons", 40)); 
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Language", "English", 40));
					else
						this.skillList.add(new Skill("Language", "Spanish", 40));
				else
					this.choiceList.add("Add Language: English or Language: Spanish at 40");
				this.skillList.add(new Skill("Networking", "Hypercorps", 40));
				this.skillList.add(new Skill("Profession", "Military Ops", 30));
				this.skillList.add(new Skill("Seeker Weapons", 40));
				this.skillList.add(new Skill("Unarmed Combat", 35));
			}
			break;
		case Lunar:
			this.suggestedMotivations.add("+Preserving Traditions"); 
			this.suggestedMotivations.add("+Hypercapitalism"); 
			this.suggestedMotivations.add("+Bioconservatism");
			this.suggestedMotivations.add("+Reclaiming Earth");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Art", "Choice", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 30)); 
				this.skillList.add(new Skill("Protocol", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("c-rep", 50));
				this.skillList.add(new Skill("Academics", "Pre-Fall History", 50)); 
				this.skillList.add(new Skill("Art", "Choice", 40));
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Kinesics", 40));
				this.skillList.add(new Skill("Language", "Choice", 40));
				this.skillList.add(new Skill("Networking", "Hypercorps", 40)); 
				this.skillList.add(new Skill("Protocol", 40));
			}
			break;
		case MercurialInfolife:
			this.suggestedMotivations.add("+AGI Rights"); 
			this.suggestedMotivations.add("+Mercurial Cause"); 
			this.suggestedMotivations.add("-Bioconservatism");
			this.suggestedMotivations.add("-Assimilation");
			this.suggestedMotivations.add("-Sapient Cause");
			if (this.ppCost == 1){
				this.choiceList.add("Add any one skill of your choice at 40"); 
				this.skillList.add(new Skill("Interest", "Infolife Clades", 30)); 
				this.skillList.add(new Skill("Interfacing", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.choiceList.add("Add any one skill of your choice at 50"); 
				this.skillList.add(new Skill("Interest", "Infolife Clades", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 30)); 
				this.skillList.add(new Skill("Intimidation", 20));
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Persuasion", 30)); 
				this.skillList.add(new Skill("Programming", 30));
			}
			break;
		case MercurialUplift:
			this.suggestedMotivations.add("+Mercurial Cause"); 
			this.suggestedMotivations.add("+Uplift Rights"); 
			this.suggestedMotivations.add("-Bioconservatism");
			this.suggestedMotivations.add("-Assimilation");
			this.suggestedMotivations.add("-Sapient Cause");
			if (this.ppCost == 1){
				this.choiceList.add("Add any one skill of your choice at 40"); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Interest", "Uplift Clades", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.choiceList.add("Add any one skill of your choice at 50"); 
				this.skillList.add(new Skill("Interest", "Uplift Clades", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Intimidation", 20)); 
				this.skillList.add(new Skill("Medicine", "Uplifts", 30));
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Persuasion", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 30));
			}
			break;
		case NanoEcologist:
			this.suggestedMotivations.add("+Nano-ecology"); 
			this.suggestedMotivations.add("+Research"); 
			this.suggestedMotivations.add("+Exploration");
			this.suggestedMotivations.add("+Technoprogressivism");
			if (this.ppCost == 1){
				if (this.getRandomSkills)
					if (d10.Roll() > 5)
						this.skillList.add(new Skill("Academics", "Ecology", 30));
					else
						this.skillList.add(new Skill("Academics", "Nanotechnology", 30));
				else
					this.choiceList.add("Add Academics: Ecology or Academics: Nanotechnology at 30"); 
				this.skillList.add(new Skill("Freerunning", 30)); 
			this.skillList.add(new Skill("Programming", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("e-rep", 50));
				this.skillList.add(new Skill("Academics", "Ecology", 40)); 
				this.skillList.add(new Skill("Academics", "Nanotechnology", 50)); 
				this.skillList.add(new Skill("Freerunning", 30));
				this.skillList.add(new Skill("Interfacing", 25)); 
				this.skillList.add(new Skill("Investigation", 30)); 
				this.skillList.add(new Skill("Medicine", "Nanomedicine", 30));
				this.skillList.add(new Skill("Networking", "Ecologists", 40)); 
				this.skillList.add(new Skill("Programming", 50));
			}
			break;
		case Orbital:
			this.suggestedMotivations.add("+Reclaiming Earth"); 
			this.suggestedMotivations.add("+Bioconservatism"); 
			this.suggestedMotivations.add("+Precautionism");
			this.suggestedMotivations.add("-AGI Rights");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 30));
				this.skillList.add(new Skill("Pilot", "Choice", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Clubs", 20)); 
				this.skillList.add(new Skill("Free Fall", 35)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 25)); 
				this.skillList.add(new Skill("Intimidation", 20)); 
				this.skillList.add(new Skill("Language", "Choice", 50));
				this.skillList.add(new Skill("Networking", "Choice", 40)); 
				this.skillList.add(new Skill("Pilot", "Choice", 50));
			}
			break;
		case Outster:
			this.suggestedMotivations.add("+AGI Rights"); 
			this.suggestedMotivations.add("+Exploration"); 
			this.suggestedMotivations.add("+Research");
			this.suggestedMotivations.add("+Morphological Freedom");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Free Fall", 30)); 
				this.skillList.add(new Skill("Interest", "Simulspace", 30));
				this.skillList.add(new Skill("Interfacing", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Free Fall", 30));
				this.skillList.add(new Skill("Infosec", 20)); 
				this.skillList.add(new Skill("Interest", "Simulspace", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 50)); 
				this.skillList.add(new Skill("Pilot", "Spacecraft", 25));
				this.skillList.add(new Skill("Programming", 40)); 
				this.skillList.add(new Skill("Psychosurgery", 25));
			}
			break;
		case Precautionist:
			this.suggestedMotivations.add("+Reclaiming Earth"); 
			this.suggestedMotivations.add("+Precautionism"); 
			this.suggestedMotivations.add("-Technoprogressivism");
			this.suggestedMotivations.add("-Bioconservatism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Infosec", 30));
				this.skillList.add(new Skill("Research", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 50)); 
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Hardware", "Choice", 20)); 
				this.skillList.add(new Skill("Infosec", 20));
				this.skillList.add(new Skill("Interfacing", 30));
				this.skillList.add(new Skill("Investigation", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 30));
				this.skillList.add(new Skill("Research", 50));
			}
			break;
		case Preservationist:
			this.suggestedMotivations.add("+Environmentalism"); 
			this.suggestedMotivations.add("+Preservationism"); 
			this.suggestedMotivations.add("+Research");
			this.suggestedMotivations.add("-Gatecrashing");
			this.suggestedMotivations.add("-Nano-ecology");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Ecology", 40)); 
				this.skillList.add(new Skill("Freerunning", 30));
				this.skillList.add(new Skill("Investigation", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("e-rep", 50));
				this.skillList.add(new Skill("Academics", "Ecology", 50)); 
				this.skillList.add(new Skill("Art", "Choice", 30)); 
				this.skillList.add(new Skill("Freerunning", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 30)); 
				this.skillList.add(new Skill("Investigation", 40));
				this.skillList.add(new Skill("Medicine", "Choice", 30)); 
				this.skillList.add(new Skill("Navigation", 35)); 
				this.skillList.add(new Skill("Networking", "Ecologists", 40));
			}
			break;
		case Reclaimer:
			this.suggestedMotivations.add("+Reclaiming Earth"); 
			this.suggestedMotivations.add("+Bioconservatism");
			this.suggestedMotivations.add("-AGI Rights");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Freerunning", 30)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Language", "Choice", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Demolitions", 20));
				this.skillList.add(new Skill("Freerunning", 30)); 
				this.skillList.add(new Skill("Hardware", "Choice", 50)); 
				this.skillList.add(new Skill("Infosec", 30)); 
				this.skillList.add(new Skill("Language", "Choice", 50)); 
				this.skillList.add(new Skill("Language", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 20)); 
				this.skillList.add(new Skill("Pilot", "Choice", 25)); 
				this.skillList.add(new Skill("Seeker Weapons", 30));
			}
			break;
		case Ringer:
			this.suggestedMotivations.add("+Morphological Freedom"); 
			this.suggestedMotivations.add("+Exploration"); 
			this.suggestedMotivations.add("+Personal Development");
			this.suggestedMotivations.add("+Research");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Flight", 40));
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Scrounging", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 50));
				this.skillList.add(new Skill("Beam Weapons", 25)); 
				this.skillList.add(new Skill("Flight", 50));
				this.skillList.add(new Skill("Free Fall", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Pilot", "Spacecraft", 30)); 
				this.skillList.add(new Skill("Scrounging", 30));
			}
			break;
		case Sapient:
			this.suggestedMotivations.add("+AGI Rights"); 
			this.suggestedMotivations.add("+Sapient Cause"); 
			this.suggestedMotivations.add("+Uplift Rights");
			this.suggestedMotivations.add("-Bioconservatism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Kinesics", 30));
				this.skillList.add(new Skill("Protocol", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Interest", "Choice", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 40));
				this.skillList.add(new Skill("Kinesics", 40)); 
				this.skillList.add(new Skill("Medicine", "Uplifts", 20)); 
				this.skillList.add(new Skill("Networking", "Choice", 30));
				this.skillList.add(new Skill("Persuasion", 40));
				this.skillList.add(new Skill("Protocol", 50));
				this.skillList.add(new Skill("Psychosurgery", 25));
			}
			break;
		case Scum:
			this.suggestedMotivations.add("+Morphological Freedom"); 
			this.suggestedMotivations.add("+Hedonism"); 
			this.suggestedMotivations.add("+Individualism");
			this.suggestedMotivations.add("+Anarchism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Art", "Choice", 30)); 
				this.skillList.add(new Skill("Free Fall", 30));
				this.skillList.add(new Skill("Networking", "Autonomists", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("@-rep", 50));
				this.skillList.add(new Skill("Art", "Choice", 50));
				this.skillList.add(new Skill("Free Fall", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Kinesics", 40)); 
				this.skillList.add(new Skill("Medicine", "Biosculpting", 30)); 
				this.skillList.add(new Skill("Networking", "Autonomists", 50));
				this.skillList.add(new Skill("Persuasion", 30));
			}
			break;
		case Sifter:
			this.suggestedMotivations.add("+Hard Work"); 
			this.suggestedMotivations.add("+Mercurian Independence"); 
			this.suggestedMotivations.add("-Hypercapitalism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Navigation", 30)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 40));
				this.skillList.add(new Skill("Profession", "Mining", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Climbing", 25)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Navigation", 40)); 
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Palming", 25)); 
				this.skillList.add(new Skill("Pilot", "Groundcraft", 50)); 
				this.skillList.add(new Skill("Profession", "Mining", 50)); 
				this.skillList.add(new Skill("Unarmed Combat", 20));
			}
			break;
		case SingularitySeeker:
			this.suggestedMotivations.add("+Morphological Freedom"); 
			this.suggestedMotivations.add("+Exploration"); 
			this.suggestedMotivations.add("+Personal Development");
			this.suggestedMotivations.add("+Research");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Infosec", 30)); 
				this.skillList.add(new Skill("Interest", "TITAN Tech", 40));
				this.skillList.add(new Skill("Interfacing", 30));
			} else if (this.ppCost == 3) {
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Hardware", "Choice", 40)); 
				this.skillList.add(new Skill("Infosec", 40)); 
				this.skillList.add(new Skill("Interest", "TITAN Tech", 50)); 
				this.skillList.add(new Skill("Interfacing", 30));
				this.skillList.add(new Skill("Medicine", "Nanomedicine", 30));
				this.skillList.add(new Skill("Programming", 40));
				this.skillList.add(new Skill("Psychosurgery", 30));
			}
			break;
		case Skimmer:
			this.suggestedMotivations.add("+Hard Work, +Independence, +Thrill Seeking"); 
			this.suggestedMotivations.add("+"); 
			this.suggestedMotivations.add("+");
			this.suggestedMotivations.add("-");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Flight", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Pilot", "Aircraft", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Flight", 40)); 
				this.skillList.add(new Skill("Gunnery", 30)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Navigation", 35)); 
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Pilot", "Aircraft", 40));
				this.skillList.add(new Skill("Pilot", "Spacecraft", 30)); 
				this.skillList.add(new Skill("Profession", "Gas Mining", 50));
			}
			break;
		case Socialite:
			this.suggestedMotivations.add("+Artistic Expression"); 
			this.suggestedMotivations.add("+Fame"); 
			this.suggestedMotivations.add("+Wealth"); 
			this.suggestedMotivations.add("+Hypercapitalism");
			this.suggestedMotivations.add("-Anarchism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Art", "Choice", 30)); 
				this.skillList.add(new Skill("Networking", "Media", 40)); 
				this.skillList.add(new Skill("Kinesics", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("f-rep", 50));
				this.skillList.add(new Skill("Art", "Choice", 40)); 
				this.skillList.add(new Skill("Deception", 25));
				this.skillList.add(new Skill("Interest", "Choice", 50)); 
				this.skillList.add(new Skill("Intimidation", 30)); 
				this.skillList.add(new Skill("Kinesics", 40)); 
				this.skillList.add(new Skill("Networking", "Media", 40));	
				this.skillList.add(new Skill("Persuasion", 30)); 
				this.skillList.add(new Skill("Protocol", 40));
			}
			break;
		case Solarian:
			this.suggestedMotivations.add("+Personal Development"); 
			this.suggestedMotivations.add("+Exploration"); 
			this.suggestedMotivations.add("+Research");
			this.suggestedMotivations.add("+Morphological Freedom");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Flight", 40)); 
				this.skillList.add(new Skill("Interest", "Choice", 30));
				this.skillList.add(new Skill("Medicine", "Nanomedicine", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 2));
				this.bonusList.add(new StatBonus("Choose Rep", 50));
				this.skillList.add(new Skill("Flight", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 50)); 
				this.skillList.add(new Skill("Interest", "Choice", 40)); 
				this.skillList.add(new Skill("Medicine", "Nanomedicine", 40)); 
				this.skillList.add(new Skill("Navigation", 30));
				this.skillList.add(new Skill("Networking", "Choice", 30)); 
				this.skillList.add(new Skill("Pilot", "Spacecraft", 25));
			}
			break;
		case Titanian:
			this.suggestedMotivations.add("+Research"); 
			this.suggestedMotivations.add("+Technosocialism"); 
			this.suggestedMotivations.add("-Hypercapitalism");
			this.suggestedMotivations.add("-Bioconservatism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Networking", "Autonomists", 40));
				this.skillList.add(new Skill("Programming", 30));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("@-rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 50)); 
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Interfacing", 25)); 
				this.skillList.add(new Skill("Networking", "Autonomists", 50)); 
				this.skillList.add(new Skill("Persuasion", 30)); 
				this.skillList.add(new Skill("Pilot", "Aircraft", 30));
				this.skillList.add(new Skill("Programming", 40)); 
				this.skillList.add(new Skill("Research", 30));
			}
			break;
		case Ultimate:
			this.suggestedMotivations.add("+Personal Development"); 
			this.suggestedMotivations.add("+Hypercapitalism"); 
			this.suggestedMotivations.add("+Ultimates");
			this.suggestedMotivations.add("-Bioconservatism");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30));
				this.skillList.add(new Skill("Freerunning", 30)); 
				this.skillList.add(new Skill("Unarmed Combat", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("c-rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 50)); 
				this.skillList.add(new Skill("Academics", "Choice", 40)); 
				this.skillList.add(new Skill("Blades", 25));
				this.skillList.add(new Skill("Climbing", 30));
				this.skillList.add(new Skill("Freerunning", 40));
				this.skillList.add(new Skill("Intimidation", 30));
				this.skillList.add(new Skill("Kinetic Weapons", 30));
				this.skillList.add(new Skill("Unarmed Combat", 50));
			}
			break;
		case Venusian:
			this.suggestedMotivations.add("+Personal Development"); 
			this.suggestedMotivations.add("+Hypercapitalism"); 
			this.suggestedMotivations.add("+Uplift Rights");
			if (this.ppCost == 1){
				this.skillList.add(new Skill("Academics", "Choice", 30)); 
				this.skillList.add(new Skill("Networking", "Hypercorps", 30)); 
				this.skillList.add(new Skill("Pilot", "Aircraft", 40));
			} else if (this.ppCost == 3) {
				this.bonusList.add(new StatBonus("MOX", 1));
				this.bonusList.add(new StatBonus("c-rep", 50));
				this.skillList.add(new Skill("Academics", "Choice", 50)); 
				this.skillList.add(new Skill("Beam Weapons", 25)); 
				this.skillList.add(new Skill("Kinesics", 30));
				this.skillList.add(new Skill("Navigation", 25)); 
				this.skillList.add(new Skill("Networking", "Hypercorps", 40)); 
				this.skillList.add(new Skill("Pilot", "Aircraft", 40)); 
				this.skillList.add(new Skill("Profession", "Choice", 40));
				this.skillList.add(new Skill("Protocol", 30));
			}
			break;
		default:
			break;
		
		}
		
	}
}
