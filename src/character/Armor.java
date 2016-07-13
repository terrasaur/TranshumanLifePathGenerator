package character;

import java.util.ArrayList;


public class Armor extends Gear {

	private static boolean isInitialized = false;
	private ArmorList name;
	
	public Armor(ArmorList armorType) {
		super(armorType.label, GearTypes.Armor, armorType.cost);
		this.name = armorType;
	}	

	//armorList\.add\(new Armor\("([A-Za-z \)\(]+)", (GearCosts\.[A-Za-z]+)\)\);\R
	
	protected static enum ArmorList{
		ArmorClothing        ("Armor Clothing",           GearCosts.Trivial,    3,  4),
		ArmorVest            ("Armor Vest",               GearCosts.Low,        6,  6),
		BattlesuitExoskeleton("Battlesuit Exoskeleton",   GearCosts.Expensive, 21, 21),
		BodyArmorLight       ("Body Armor (light)",       GearCosts.Low,       10, 10),
		BodyArmorHeavy       ("Body Armor (heavy)",       GearCosts.Moderate,  13, 13),
		CrashSuit            ("Crash Suit",               GearCosts.Low,        4,  6),
		Exowalker            ("Exowalker",                GearCosts.Moderate,   2,  4),
		Hardsuit             ("Hardsuit",                 GearCosts.High,      15, 15),
		HyperdenseExoskeleton("Hyperdense Exoskeleton",   GearCosts.Expensive,  6, 12),
		SmartClothing        ("Smart Clothing",           GearCosts.Low,        0,  0),
		SmartVacClothing     ("Smart Vac Clothing",       GearCosts.Moderate,   2,  4),
		SmartVacsuitLight    ("Smart Vacsuit (light)",    GearCosts.Moderate,   5,  5),
		SmartVacsuitStandard ("Smart Vacsuit (standard)", GearCosts.High,       7,  7),
		SprayArmor           ("Spray Armor",              GearCosts.Low,        2,  2),
		IndustrialArmor      ("Industrial Armor",         GearCosts.Moderate,  10, 10),
		CombatArmorLight     ("Combat Armor (light)",     GearCosts.Moderate,  14, 12),
		CombatArmorHeavy     ("Combat Armor (heavy)",     GearCosts.High,      16, 16),
		Transporter          ("Transporter",              GearCosts.High,       2,  4),
		Trike                ("Trike",                    GearCosts.Moderate,   2,  4),
		VacsuitLight         ("Vacsuit (light)",          GearCosts.Low,        5,  5),
		VacsuitStandard      ("Vacsuit (standard)",       GearCosts.Moderate,   7,  7),
		HighDiveSuit         ("High-Dive Suit",           GearCosts.High,      13, 10),
		MercurySuit          ("Mercury Suit",             GearCosts.Expensive, 15, 15),
		SolarSurvivalSuit    ("Solar Survival Suit",      GearCosts.Expensive, 20, 15),
		VenusSuit            ("Venus Suit",               GearCosts.Expensive, 15, 15),
		CrasherSuit          ("Crasher Suit",             GearCosts.Expensive, 10, 10),
		FaradaySuit          ("Faraday Suit",             GearCosts.Moderate,   0,  0),
		;
		
		String label;
		GearCosts cost;
		int energyRating, kineticRating;
		ArmorList(String label, GearCosts cost, int energy, int kinetic){
			this.label = label;
			this.cost = cost;
			this.energyRating = energy;
			this.kineticRating = kinetic;
		}
	}

	
	// Make this into an enum probably
	private static ArrayList<Armor> armorList;
	public static ArrayList<? extends Gear> getArmorArray() {
		if (!isInitialized){
			// load the armor list
			armorList = new ArrayList<Armor>();
			armorList.add(new Armor(ArmorList.ArmorClothing));
			armorList.add(new Armor(ArmorList.ArmorVest));
			armorList.add(new Armor(ArmorList.BattlesuitExoskeleton));
			armorList.add(new Armor(ArmorList.BodyArmorLight));
			armorList.add(new Armor(ArmorList.BodyArmorHeavy));
			armorList.add(new Armor(ArmorList.CrashSuit));
			armorList.add(new Armor(ArmorList.Exowalker));
			armorList.add(new Armor(ArmorList.Hardsuit));
			armorList.add(new Armor(ArmorList.HyperdenseExoskeleton));
			armorList.add(new Armor(ArmorList.SmartClothing));
			armorList.add(new Armor(ArmorList.SmartVacClothing));
			armorList.add(new Armor(ArmorList.SmartVacsuitLight));
			armorList.add(new Armor(ArmorList.SmartVacsuitStandard));
			armorList.add(new Armor(ArmorList.SprayArmor));
			armorList.add(new Armor(ArmorList.IndustrialArmor));
			armorList.add(new Armor(ArmorList.CombatArmorLight));
			armorList.add(new Armor(ArmorList.CombatArmorHeavy));
			armorList.add(new Armor(ArmorList.Transporter));
			armorList.add(new Armor(ArmorList.Trike));
			armorList.add(new Armor(ArmorList.VacsuitLight));
			armorList.add(new Armor(ArmorList.VacsuitStandard));
			armorList.add(new Armor(ArmorList.HighDiveSuit));
			armorList.add(new Armor(ArmorList.MercurySuit));
			armorList.add(new Armor(ArmorList.SolarSurvivalSuit));
			armorList.add(new Armor(ArmorList.VenusSuit));
			armorList.add(new Armor(ArmorList.CrasherSuit));
			armorList.add(new Armor(ArmorList.FaradaySuit));

			isInitialized = true;
		}
		
		return armorList;
	}

	@Override
	public String toString() {		
		return this.label;
	}
	
	public String getRating(){
		return this.name.energyRating + "\\" + this.name.kineticRating;
	}

}
