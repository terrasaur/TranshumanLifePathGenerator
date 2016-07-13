package character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A collection of enumerations and constructors for gear. This is the base gear type, 
 * and all other types of gear can derive from this. This contains the implementation 
 * for gear that simply has a cost, type, and name. Other things like weapons and armor
 * have their own classes
 * 
 * @author terrasaur
 *
 */
public class Gear {

	static enum GearCosts{
		// Don't change the order. I care.
		Trivial,
		Low,
		Moderate,
		High,
		Expensive,
		ExpensivePlus
	}
	
	static enum GearTypes{
		Communications    ( 0, "Communications"),
		CovertAndEspionage( 1, "Covert and Espionage"),
		ConventionalDrugs ( 2, "Conventional Drugs"),
		Nanodrugs         ( 3, "Nanodrugs"),
		Narcoalgorithms   ( 4, "Narcoalgorithms"),
		Chemicals         ( 5, "Chemicals"),
		Toxins            ( 6, "Toxins"),
		Nanotoxins        ( 7, "Nanotoxins"),
		Pathogens         ( 8, "Pathogens"),
		PsiDrugs          ( 9, "Psi Drugs"),
		Armor             (-1, "Armor");
		
		String label;
		int idx;
		GearTypes(int idx, String s){
			this.label = s;
			this.idx = idx;
		}
	}
	
	String label;
	GearCosts cost;
	GearTypes type;
	
	public Gear(String label) {
		this(label, null, null);
	}
	
	public Gear(String label, GearTypes type, GearCosts cost){
		this.label = label;
		this.cost = cost;
		this.type = type;
	}
	



	/**
	 * Gets a list of any of the gear types specified. Additionally, initializes
	 * the gear list if it has't been initialized.
	 * @param types - gear to be found
	 * @return an array list of all gear of those specified types
	 */	
	public static ArrayList<Gear> getGearFromSubtype(List<GearTypes> types){		
		ArrayList<Gear> returnArray = new ArrayList<Gear>();
				
		for (GearTypes t : types){
			if (t.equals(GearTypes.Armor)){
				returnArray.addAll(Armor.getArmorArray());				
			} else {
				if (!gearLists.containsKey(t))
					loadAllGear();
				returnArray.addAll(gearLists.get(t).values());
			}
		}
		return returnArray;		
	}
	

	// The map of maps. Get the gear type, then get the string name of the gear
	public static HashMap<GearTypes, HashMap<String, Gear>> gearLists = 
			new HashMap<GearTypes, HashMap<String, Gear>>(20);


	/**
	 * Loads all gear into its gear map. This gets done automatically when you 
	 * try to pull a type of gear that hasn't been loaded yet, but you could do it 
	 * yourself whenever you like. If it's already loaded, nothing will happen.
	 */
	public static void loadAllGear(){
		for (GearTypes g : GearTypes.values()){
			if (g.idx >= 0){
				loadGearList(g);
			}
		}
	}
	
	
	/**
	 * Loads a gear list of a specific type. I suppose you could use this if 
	 * you were worried about speed of loading.
	 * @param type the GearType you wish to load
	 */
	public static void loadGearList(GearTypes type){
		if (gearLists.containsKey(type))
			return; // it's already there, just boot
		
		HashMap<String, Gear> newMap = null;
		
		switch(type){
		case Communications:
			newMap = new HashMap<String, Gear>(16);
				
			newMap.put("Fiberoptic Cable", 
					new Gear("Fiberoptic Cable", GearTypes.Communications, GearCosts.Trivial));
			//([A-Za-z]+\.)add\((new Gear\(("[A-Za-z1-5\+ \(\):/\-\']+"), GearTypes\.[A-Za-z]+, GearCosts\.[A-Za-z]+\)\));
			newMap.put("Laser/Microwave Link", 
					new Gear("Laser/Microwave Link", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Radio Booster", 
					new Gear("Radio Booster", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Neutrino Transceiver",
					new Gear("Neutrino Transceiver", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Miniature Radio Farcaster", 
					new Gear("Miniature Radio Farcaster", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Portable QE Comm",
					new Gear("Portable QE Comm", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Low-QBit Reservoir", 
					new Gear("Low-QBit Reservoir", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("High-QBit Reservoir", 
					new Gear("High-QBit Reservoir", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Mission Recorder", 
					new Gear("Mission Recorder", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Motes",
					new Gear("Motes", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Sensor Motes", 
					new Gear("Sensor Motes", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Neutrino Retreat", 
					new Gear("Neutrino Retreat", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Radio Beacon", 
					new Gear("Radio Beacon", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Satnet in a Can",
					new Gear("Satnet in a Can", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Neo-Cetacean Translation Device", 
					new Gear("Neo-Cetacean Translation Device", GearTypes.Communications, GearCosts.Trivial));
			newMap.put("Hypersonic Communicator",
					new Gear("Hypersonic Communicator", GearTypes.Communications, GearCosts.Trivial));

			break;
		case CovertAndEspionage:
			newMap = new HashMap<String, Gear>(24);
			newMap.put("Chameleon Cloak", 
					new Gear("Chameleon Cloak", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Covert Ops Tool", 
					new Gear("Covert Ops Tool", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Cuffband", 
					new Gear("Cuffband", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Dazzler", 
					new Gear("Dazzler", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Disabler", 
					new Gear("Disabler", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Fibereye", 
					new Gear("Fibereye", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Invisibility Cloak", 
					new Gear("Invisibility Cloak", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Microbug", 
					new Gear("Microbug", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Quantum Microbug", 
					new Gear("Quantum Microbug", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Prisoner Mask", 
					new Gear("Prisoner Mask", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Psi Jammer", 
					new Gear("Psi Jammer", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Quantum Computer", 
					new Gear("Quantum Computer", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Smart Dust", 
					new Gear("Smart Dust", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Traction Pads", 
					new Gear("Traction Pads", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("White Noise Machine", 
					new Gear("White Noise Machine", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("X-Ray Emitter", 
					new Gear("X-Ray Emitter", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Acoustic Spotter", 
					new Gear("Acoustic Spotter", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("DNA Chaff", 
					new Gear("DNA Chaff", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Graybox", 
					new Gear("Graybox", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Lens Crazer", 
					new Gear("Lens Crazer", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Lens Spotter", 
					new Gear("Lens Spotter", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Privacy Pod", 
					new Gear("Privacy Pod", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Radar Cloak", 
					new Gear("Radar Cloak", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			newMap.put("Shroud", 
					new Gear("Shroud", GearTypes.CovertAndEspionage, GearCosts.Trivial));

			break;
		case ConventionalDrugs:
			newMap = new HashMap<String, Gear>(20);
			newMap.put("Drive", 
					new Gear("Drive", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Klar", 
					new Gear("Klar", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Neem", 
					new Gear("Neem", GearTypes.ConventionalDrugs, GearCosts.Moderate));
			newMap.put("BringIt", 
					new Gear("BringIt", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Grin", 
					new Gear("Grin", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Kick", 
					new Gear("Kick", GearTypes.ConventionalDrugs, GearCosts.Moderate));
			newMap.put("MRDR", 
					new Gear("MRDR", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Phlo", 
					new Gear("Phlo", GearTypes.ConventionalDrugs, GearCosts.Moderate));
			newMap.put("Bananas Furiosas", 
					new Gear("Bananas Furiosas", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Comfurt", 
					new Gear("Comfurt", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Buzz",
					new Gear("Buzz", GearTypes.ConventionalDrugs, GearCosts.Moderate));
			newMap.put("Mono No Aware", 
					new Gear("Mono No Aware", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Orbital Hash",
					new Gear("Orbital Hash", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Alpha", 
					new Gear("Alpha", GearTypes.ConventionalDrugs, GearCosts.High));
			newMap.put("Hither", 
					new Gear("Hither", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Juice", 
					new Gear("Juice", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Hydra", 
					new Gear("Hydra", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Kong", 
					new Gear("Kong", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Raptor",
					new Gear("Raptor", GearTypes.ConventionalDrugs, GearCosts.Low));
			newMap.put("Staste", 
					new Gear("Staste", GearTypes.ConventionalDrugs, GearCosts.Low));

			break;
		case Chemicals:
			newMap = new HashMap<String, Gear>(7);
			newMap.put("Atropine", 
					new Gear("Atropine",        GearTypes.Chemicals, GearCosts.Trivial));
			newMap.put("DMSO", 
					new Gear("DMSO",            GearTypes.Chemicals, GearCosts.Trivial));
			newMap.put("Liquid Thermite", 
					new Gear("Liquid Thermite", GearTypes.Chemicals, GearCosts.Moderate));
			newMap.put("NotWater", 
					new Gear("NotWater",        GearTypes.Chemicals, GearCosts.Trivial));
			newMap.put("Scrapper's Gel", 
					new Gear("Scrapper's Gel",  GearTypes.Chemicals, GearCosts.Low));
			newMap.put("Slip", 
					new Gear("Slip",            GearTypes.Chemicals, GearCosts.Low));
			newMap.put("Tracker's Dye", 
					new Gear("Tracker's Dye",   GearTypes.Chemicals, GearCosts.Trivial));

			break;
		case Nanodrugs:
			newMap = new HashMap<String, Gear>(7);
			newMap.put("Frequency", 
					new Gear("Frequency",         GearTypes.Nanodrugs, GearCosts.Moderate));
			newMap.put("Gravy", 
					new Gear("Gravy",             GearTypes.Nanodrugs, GearCosts.Low));
			newMap.put("Schizo", 
					new Gear("Schizo",            GearTypes.Nanodrugs, GearCosts.Low));
			newMap.put("Petals (Trivial)", 
					new Gear("Petals (Trivial)",  GearTypes.Nanodrugs, GearCosts.Trivial));
			newMap.put("Petals (Low)", 
					new Gear("Petals (Low)",      GearTypes.Nanodrugs, GearCosts.Low));
			newMap.put("Petals (Moderate)", 
					new Gear("Petals (Moderate)", GearTypes.Nanodrugs, GearCosts.Moderate));
			newMap.put("Petals (High)", 
					new Gear("Petals (High)",     GearTypes.Nanodrugs, GearCosts.High));

			break;
		case Nanotoxins:
			newMap = new HashMap<String, Gear>(4);
			newMap.put("Disruption", 
					new Gear("Disruption", GearTypes.Nanotoxins, GearCosts.Moderate));
			newMap.put("Necrosis", 
					new Gear("Necrosis",   GearTypes.Nanotoxins, GearCosts.Moderate));
			newMap.put("Neuropath", 
					new Gear("Neuropath",  GearTypes.Nanotoxins, GearCosts.Moderate));
			newMap.put("Nutcracker",
					new Gear("Nutcracker", GearTypes.Nanotoxins, GearCosts.High));

			break;
		case Narcoalgorithms:
			newMap = new HashMap<String, Gear>(2);
			newMap.put("DDR", 
					new Gear("DDR",       GearTypes.Narcoalgorithms, GearCosts.Low));
			newMap.put("Linkstate", 
					new Gear("Linkstate", GearTypes.Narcoalgorithms, GearCosts.Low));

			break;
		case Pathogens:
			newMap = new HashMap<String, Gear>(2);
			newMap.put("Degen", 
					new Gear("Degen",   GearTypes.Pathogens, GearCosts.Expensive));
			newMap.put("Trigger", 
					new Gear("Trigger", GearTypes.Pathogens, GearCosts.Expensive));

			break;
		case PsiDrugs:
			newMap = new HashMap<String, Gear>(3);
			newMap.put("Inhibitor", 
					new Gear("Inhibitor",  GearTypes.PsiDrugs, GearCosts.High));
			newMap.put("Psi-Opener", 
					new Gear("Psi-Opener", GearTypes.PsiDrugs, GearCosts.Expensive));
			newMap.put("PsikeOut", 
					new Gear("PsikeOut",   GearTypes.PsiDrugs, GearCosts.Expensive));


			break;
		case Toxins:
			newMap = new HashMap<String, Gear>(6);
			newMap.put("BTX", 
					new Gear("BTX",        GearTypes.Toxins, GearCosts.High));
			newMap.put("CR Gas", 
					new Gear("CR Gas",     GearTypes.Toxins, GearCosts.Low));
			newMap.put("Flight", 
					new Gear("Flight",     GearTypes.Toxins, GearCosts.Low));
			newMap.put("Nervex",
					new Gear("Nervex",     GearTypes.Toxins, GearCosts.High));
			newMap.put("Oxytocin-A",
					new Gear("Oxytocin-A", GearTypes.Toxins, GearCosts.Low));
			newMap.put("Twitch", 
					new Gear("Twitch",     GearTypes.Toxins, GearCosts.Low));

			break;
		case Armor:
			System.out.println("Error: this should be handled in the Armor class");
		default:
			break;		
		}
		
		// now add it to the list
		if (newMap != null)
			gearLists.put(type, newMap);
	}


	@Override
	public String toString() {		
		return this.label;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Gear){
			return this.label == ((Gear) obj).label;
		}
		return super.equals(obj);
	}


}
