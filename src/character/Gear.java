package character;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A collection of enumerations and constructors for gear
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
		Communications    ("Communications"),
		CovertAndEspionage("Covert and Espionage"),
		ConventionalDrugs ("Conventional Drugs"),
		Nanodrugs         ("Nanodrugs"),
		Narcoalgorithms   ("Narcoalgorithms"),
		Chemicals         ("Chemicals"),
		Toxins            ("Toxins"),
		Nanotoxins        ("Nanotoxins"),
		Pathogens         ("Pathogens"),
		PsiDrugs          ("Psi Drugs"),
		Armor             ("Armor");
		
		String label;
		GearTypes(String s){
			this.label = s;
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
	@SuppressWarnings("unchecked")
	public static ArrayList<Gear> getGearFromSubtype(GearTypes types[]){
		ArrayList<Gear> returnArray = new ArrayList<Gear>();
		Gear dummyGear = new Gear("a", GearTypes.Chemicals, GearCosts.Expensive);
		
		for (GearTypes t : types){
			if (t.equals(GearTypes.Armor)){
				returnArray.addAll(Armor.getArmorArray());				
			} else {
				String arrayName = t.name() + "List";
				Class<? extends Gear> c = dummyGear.getClass();
				Field f;
				try {
					Gear.loadGearList(t);
					f = c.getDeclaredField(arrayName);
					f.setAccessible(true);
					HashMap<String, Gear> gearArray = (HashMap<String, Gear>)f.get(dummyGear);
					returnArray.addAll(gearArray.values());
					
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return returnArray;		
	}
	

	public static HashMap<String, Gear> CommunicationsList, CovertAndEspionageList, ConventionalDrugsList, 
				NanodrugsList, NarcoalgorithmsList, ChemicalsList, ToxinsList, NanotoxinsList, 
				PathogensList, PsiDrugsList;
	
	
	public static void loadAllGear(){
	}
	
	
	public static void loadGearList(GearTypes type){		
		switch(type){
		case Communications:
			if (CommunicationsList == null){
				CommunicationsList = new HashMap<String, Gear>(16);
				CommunicationsList.put("Fiberoptic Cable", 
						new Gear("Fiberoptic Cable", GearTypes.Communications, GearCosts.Trivial));
				//([A-Za-z]+\.)add\((new Gear\(("[A-Za-z1-5\+ \(\):/\-\']+"), GearTypes\.[A-Za-z]+, GearCosts\.[A-Za-z]+\)\));
				CommunicationsList.put("Laser/Microwave Link", 
						new Gear("Laser/Microwave Link", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Radio Booster", 
						new Gear("Radio Booster", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Neutrino Transceiver",
						new Gear("Neutrino Transceiver", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Miniature Radio Farcaster", 
						new Gear("Miniature Radio Farcaster", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Portable QE Comm",
						new Gear("Portable QE Comm", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Low-QBit Reservoir", 
						new Gear("Low-QBit Reservoir", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("High-QBit Reservoir", 
						new Gear("High-QBit Reservoir", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Mission Recorder", 
						new Gear("Mission Recorder", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Motes",
						new Gear("Motes", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Sensor Motes", 
						new Gear("Sensor Motes", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Neutrino Retreat", 
						new Gear("Neutrino Retreat", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Radio Beacon", 
						new Gear("Radio Beacon", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Satnet in a Can",
						new Gear("Satnet in a Can", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Neo-Cetacean Translation Device", 
						new Gear("Neo-Cetacean Translation Device", GearTypes.Communications, GearCosts.Trivial));
				CommunicationsList.put("Hypersonic Communicator",
						new Gear("Hypersonic Communicator", GearTypes.Communications, GearCosts.Trivial));
			}
			break;
		case CovertAndEspionage:
			if (CovertAndEspionageList == null){
				CovertAndEspionageList = new HashMap<String, Gear>(24);
				CovertAndEspionageList.put("Chameleon Cloak", 
						new Gear("Chameleon Cloak", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Covert Ops Tool", 
						new Gear("Covert Ops Tool", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Cuffband", 
						new Gear("Cuffband", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Dazzler", 
						new Gear("Dazzler", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Disabler", 
						new Gear("Disabler", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Fibereye", 
						new Gear("Fibereye", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Invisibility Cloak", 
						new Gear("Invisibility Cloak", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Microbug", 
						new Gear("Microbug", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Quantum Microbug", 
						new Gear("Quantum Microbug", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Prisoner Mask", 
						new Gear("Prisoner Mask", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Psi Jammer", 
						new Gear("Psi Jammer", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Quantum Computer", 
						new Gear("Quantum Computer", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Smart Dust", 
						new Gear("Smart Dust", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Traction Pads", 
						new Gear("Traction Pads", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("White Noise Machine", 
						new Gear("White Noise Machine", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("X-Ray Emitter", 
						new Gear("X-Ray Emitter", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Acoustic Spotter", 
						new Gear("Acoustic Spotter", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("DNA Chaff", 
						new Gear("DNA Chaff", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Graybox", 
						new Gear("Graybox", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Lens Crazer", 
						new Gear("Lens Crazer", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Lens Spotter", 
						new Gear("Lens Spotter", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Privacy Pod", 
						new Gear("Privacy Pod", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Radar Cloak", 
						new Gear("Radar Cloak", GearTypes.CovertAndEspionage, GearCosts.Trivial));
				CovertAndEspionageList.put("Shroud", 
						new Gear("Shroud", GearTypes.CovertAndEspionage, GearCosts.Trivial));
			}
			break;
		case ConventionalDrugs:
			if (ConventionalDrugsList == null){
				ConventionalDrugsList = new HashMap<String, Gear>(20);
				ConventionalDrugsList.put("Drive", 
						new Gear("Drive", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Klar", 
						new Gear("Klar", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Neem", 
						new Gear("Neem", GearTypes.ConventionalDrugs, GearCosts.Moderate));
				ConventionalDrugsList.put("BringIt", 
						new Gear("BringIt", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Grin", 
						new Gear("Grin", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Kick", 
						new Gear("Kick", GearTypes.ConventionalDrugs, GearCosts.Moderate));
				ConventionalDrugsList.put("MRDR", 
						new Gear("MRDR", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Phlo", 
						new Gear("Phlo", GearTypes.ConventionalDrugs, GearCosts.Moderate));
				ConventionalDrugsList.put("Bananas Furiosas", 
						new Gear("Bananas Furiosas", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Comfurt", 
						new Gear("Comfurt", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Buzz",
						new Gear("Buzz", GearTypes.ConventionalDrugs, GearCosts.Moderate));
				ConventionalDrugsList.put("Mono No Aware", 
						new Gear("Mono No Aware", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Orbital Hash",
						new Gear("Orbital Hash", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Alpha", 
						new Gear("Alpha", GearTypes.ConventionalDrugs, GearCosts.High));
				ConventionalDrugsList.put("Hither", 
						new Gear("Hither", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Juice", 
						new Gear("Juice", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Hydra", 
						new Gear("Hydra", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Kong", 
						new Gear("Kong", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Raptor",
						new Gear("Raptor", GearTypes.ConventionalDrugs, GearCosts.Low));
				ConventionalDrugsList.put("Staste", 
						new Gear("Staste", GearTypes.ConventionalDrugs, GearCosts.Low));
			}
			break;
		case Chemicals:
			if (ChemicalsList == null){
				ChemicalsList = new HashMap<String, Gear>(7);
				ChemicalsList.put("Atropine", 
						new Gear("Atropine",        GearTypes.Chemicals, GearCosts.Trivial));
				ChemicalsList.put("DMSO", 
						new Gear("DMSO",            GearTypes.Chemicals, GearCosts.Trivial));
				ChemicalsList.put("Liquid Thermite", 
						new Gear("Liquid Thermite", GearTypes.Chemicals, GearCosts.Moderate));
				ChemicalsList.put("NotWater", 
						new Gear("NotWater",        GearTypes.Chemicals, GearCosts.Trivial));
				ChemicalsList.put("Scrapper's Gel", 
						new Gear("Scrapper's Gel",  GearTypes.Chemicals, GearCosts.Low));
				ChemicalsList.put("Slip", 
						new Gear("Slip",            GearTypes.Chemicals, GearCosts.Low));
				ChemicalsList.put("Tracker's Dye", 
						new Gear("Tracker's Dye",   GearTypes.Chemicals, GearCosts.Trivial));
			}
			break;
		case Nanodrugs:
			if (NanodrugsList == null){
				NanodrugsList = new HashMap<String, Gear>(7);
				NanodrugsList.put("Frequency", 
						new Gear("Frequency",         GearTypes.Nanodrugs, GearCosts.Moderate));
				NanodrugsList.put("Gravy", 
						new Gear("Gravy",             GearTypes.Nanodrugs, GearCosts.Low));
				NanodrugsList.put("Schizo", 
						new Gear("Schizo",            GearTypes.Nanodrugs, GearCosts.Low));
				NanodrugsList.put("Petals (Trivial)", 
						new Gear("Petals (Trivial)",  GearTypes.Nanodrugs, GearCosts.Trivial));
				NanodrugsList.put("Petals (Low)", 
						new Gear("Petals (Low)",      GearTypes.Nanodrugs, GearCosts.Low));
				NanodrugsList.put("Petals (Moderate)", 
						new Gear("Petals (Moderate)", GearTypes.Nanodrugs, GearCosts.Moderate));
				NanodrugsList.put("Petals (High)", 
						new Gear("Petals (High)",     GearTypes.Nanodrugs, GearCosts.High));
			}
			break;
		case Nanotoxins:
			if (NanotoxinsList == null){
				NanotoxinsList = new HashMap<String, Gear>(4);
				NanotoxinsList.put("Disruption", 
						new Gear("Disruption", GearTypes.Nanotoxins, GearCosts.Moderate));
				NanotoxinsList.put("Necrosis", 
						new Gear("Necrosis",   GearTypes.Nanotoxins, GearCosts.Moderate));
				NanotoxinsList.put("Neuropath", 
						new Gear("Neuropath",  GearTypes.Nanotoxins, GearCosts.Moderate));
				NanotoxinsList.put("Nutcracker",
						new Gear("Nutcracker", GearTypes.Nanotoxins, GearCosts.High));
			}
			break;
		case Narcoalgorithms:
			if (NarcoalgorithmsList == null){
				NarcoalgorithmsList = new HashMap<String, Gear>(2);
				NarcoalgorithmsList.put("DDR", 
						new Gear("DDR",       GearTypes.Narcoalgorithms, GearCosts.Low));
				NarcoalgorithmsList.put("Linkstate", 
						new Gear("Linkstate", GearTypes.Narcoalgorithms, GearCosts.Low));
			}
			break;
		case Pathogens:
			if (PathogensList == null){
				PathogensList = new HashMap<String, Gear>(2);
				PathogensList.put("Degen", 
						new Gear("Degen",   GearTypes.Pathogens, GearCosts.Expensive));
				PathogensList.put("Trigger", 
						new Gear("Trigger", GearTypes.Pathogens, GearCosts.Expensive));
			}
			break;
		case PsiDrugs:
			if (PsiDrugsList == null){
				PsiDrugsList = new HashMap<String, Gear>(3);
				PsiDrugsList.put("Inhibitor", 
						new Gear("Inhibitor",  GearTypes.PsiDrugs, GearCosts.High));
				PsiDrugsList.put("Psi-Opener", 
						new Gear("Psi-Opener", GearTypes.PsiDrugs, GearCosts.Expensive));
				PsiDrugsList.put("PsikeOut", 
						new Gear("PsikeOut",   GearTypes.PsiDrugs, GearCosts.Expensive));

			}
			break;
		case Toxins:
			if (ToxinsList == null){
				ToxinsList = new HashMap<String, Gear>(6);
				ToxinsList.put("BTX", 
						new Gear("BTX",        GearTypes.Toxins, GearCosts.High));
				ToxinsList.put("CR Gas", 
						new Gear("CR Gas",     GearTypes.Toxins, GearCosts.Low));
				ToxinsList.put("Flight", 
						new Gear("Flight",     GearTypes.Toxins, GearCosts.Low));
				ToxinsList.put("Nervex",
						new Gear("Nervex",     GearTypes.Toxins, GearCosts.High));
				ToxinsList.put("Oxytocin-A",
						new Gear("Oxytocin-A", GearTypes.Toxins, GearCosts.Low));
				ToxinsList.put("Twitch", 
						new Gear("Twitch",     GearTypes.Toxins, GearCosts.Low));
			}
			break;
		case Armor:
			System.out.println("Error: this should be handled in the Armor class");
		default:
			break;		
		}
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
