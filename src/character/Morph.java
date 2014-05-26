package character;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import character.Aptitude.AptitudeType;

/**
 * This object contains a morph. It tracks the morph's aptitudes, 
 * type, name, durability and wound threshold, CP cost, and
 * traits. 
 * @author terrasaur
 *
 */
public class Morph {
	
	/**
	 * Helper class that tracks aptitudes and possible bonuses
	 * due to traits / mods
	 * @author terrasaur
	 *
	 */
	protected class MorphAptitude{
		AptitudeType label;
		int value;
		int bonus;
		
		MorphAptitude(AptitudeType name, int val){
			this.label = name;
			this.value = val;
		}
		public String toString() {
			return this.label + ": " + this.value;
		}
		
		MorphAptitude(String name, int val){
			this.label = Aptitude.getAptitudeByName(name);
			this.value = val;
		}
		
	}
	
	protected LinkedHashMap<String, MorphAptitude> stats;
	protected MorphList type;
	protected ArrayList<String> implants;
	protected ArrayList<String> traits;
	protected String aptChoice;
	protected int aptMax = 30;
	protected int aptMaxSOM = 30;
	protected boolean hasStructuralEnhancement = false;
	
	
	public enum Type{
		biomorph,
		pod,
		uplift,
		synthmorph,
		infomorph
	}
	
	/**
	 * The list of the morphs. Right now, just tracking CP cost, type, and 
	 * durability. Will eventually add stats, but since you get to choose your
	 * bonuses I'm putting it off for now.
	 */
	protected enum MorphList{
		Flat          ("Flat",             0, Type.biomorph, 30, 6),
		Splicer       ("Splicer",         10, Type.biomorph, 30, 6),
		Exalt         ("Exalt",           30, Type.biomorph, 35, 7),
		Menton        ("Menton",          40, Type.biomorph, 35, 7),
		Olympian      ("Olympian",        40, Type.biomorph, 40, 8),
		Sylph         ("Sylph",           40, Type.biomorph, 35, 7),
		Bouncer       ("Bouncer",         40, Type.biomorph, 35, 7),
		Fury          ("Fury",            75, Type.biomorph, 50, 10),
		Futura        ("Futura",          40, Type.biomorph, 35, 7),
		Ghost         ("Ghost",           70, Type.biomorph, 45, 9),
		Hibernoid     ("Hibernoid",       25, Type.biomorph, 35, 7),
		Neotenic      ("Neotenic",        25, Type.biomorph, 30, 6),
		Remade        ("Remade",          60, Type.biomorph, 40, 8),
		Ruster        ("Ruster",          25, Type.biomorph, 35, 7),
		LunarFlyer    ("Lunar Flyer",     35, Type.biomorph, 30, 6),
		MartianAlpiner("Martian Alpiner", 30, Type.biomorph, 40, 8),
		Salamander    ("Salamander",      40, Type.biomorph, 60, 12),
		Surya         ("Surya",           50, Type.biomorph, 100, 20),
		VenusianGlider("Venusian Glider", 40, Type.biomorph, 30, 6),
		Hazer         ("Hazer",           35, Type.biomorph, 35, 7),
		Hulder        ("Hulder",          50, Type.biomorph, 40, 8),
		Hyperbright   ("Hyperbright",     70, Type.biomorph, 35, 7),
		RingFlyer     ("Ring Flyer",      70, Type.biomorph, 30, 6),
		Selkie        ("Selkie",          55, Type.biomorph, 40, 8),
		Aquanaut      ("Aquanaut",        50, Type.biomorph, 40, 8),
		Crasher       ("Crasher",         70, Type.biomorph, 40, 8),
		Dvergr        ("Dvergr",          50, Type.biomorph, 45, 9),
		Ariel         ("Ariel",           50, Type.biomorph, 30, 6),
		Bruiser       ("Bruiser",         60, Type.biomorph, 60, 10),
		CloudSkate    ("Cloud Skate",     55, Type.biomorph, 35, 7),
		Faust         ("Faust",           85, Type.biomorph, 35, 7),
		Freeman       ("Freeman",         10, Type.biomorph, 30, 6),
		Grey          ("Grey",            25, Type.biomorph, 30, 6),
		Nomad         ("Nomad",           30, Type.biomorph, 45, 9),
		Observer      ("Observer",        40, Type.biomorph, 35, 7),
		Theseus       ("Theseus",         30, Type.biomorph, 40, 8),
				
		NeoAvian   ("Neo-Avian",    25, Type.uplift, 20, 4),
		NeoHominid ("Neo-Hominid",  25, Type.uplift, 30, 6),
		Octomorph  ("Octomorph",    50, Type.uplift, 30, 6),
		Neanderthal("Neanderthal",  40, Type.uplift, 40, 8),
		NeoBeluga  ("Neo-Beluga",   45, Type.uplift, 35, 7),
		NeoDolphin ("Neo-Dolphin",  40, Type.uplift, 30, 6),
		NeoGorilla ("Neo-Gorilla",  35, Type.uplift, 40, 8),
		NeoOrca    ("Neo-Orca",     60, Type.uplift, 40, 8),
		NeoPig     ("Neo-Pig",      20, Type.uplift, 35, 7),
		NeoPorpoise("Neo-Porpoise", 35, Type.uplift, 25, 5),
		NeoWhale   ("Neo-Whale",    75, Type.uplift, 100, 20),
		
		PleasurePod  ("Pleasure Pod",   20, Type.pod, 30, 6),
		WorkerPod    ("Worker Pod",     20, Type.pod, 35, 7),
		Novacrab     ("Novacrab",       60, Type.pod, 40, 8),
		Digger       ("Digger",         30, Type.pod, 35, 7),
		Ripwing      ("Ripwing",        40, Type.pod, 35, 7),
		Scurrier     ("Scurrier",       40, Type.pod, 30, 6),
		Whiplash     ("Whiplash",       50, Type.pod, 40, 8),
		Chickcharnie ("Chickcharnie",   35, Type.pod, 35, 7),
		Hypergibbon  ("Hypergibbon",    30, Type.pod, 25, 5),
		Shaper       ("Shaper",         45, Type.pod, 30, 6),
		Ayah         ("Ayah",           25, Type.pod, 35, 7),
		BasicPod     ("Basic Pod",       5, Type.pod, 30, 6),
		Critter      ("Critter",        15, Type.pod, 35, 7),
		FlyingSquid  ("Flying Squid",   55, Type.pod, 40, 8),
		Jenkin       ("Jenkin",         20, Type.pod, 35, 7),
		Samsa        ("Samsa",          60, Type.pod, 50, 10),
		SecurityPod  ("Security Pod",   30, Type.pod, 35, 7),
		SpaceMarine  ("Space Marine",   30, Type.pod, 35, 7),
		SpecialistPod("Specialist Pod", 25, Type.pod, 35, 7),
		VacuumPod    ("Vacuum Pod",     30, Type.pod, 35, 7),
		
		Case        ("Case",           5, Type.synthmorph, 20, 4),
		Synth       ("Synth",         30, Type.synthmorph, 40, 8),
		Arachnoid   ("Arachnoid",     45, Type.synthmorph, 40, 8),
		Dragonfly   ("Dragonfly",     20, Type.synthmorph, 25, 5),
		Flexbot     ("Flexbot",       20, Type.synthmorph, 25, 5),
		Reaper      ("Reaper",       100, Type.synthmorph, 50, 10),
		Slitheroid  ("Slitheroid",    40, Type.synthmorph, 45, 9),
		Swarmanoid  ("Swarmanoid",    25, Type.synthmorph, 30, 6),
		QMorph      ("Q Morph",      100, Type.synthmorph, 120, 24),
		SteelMorph  ("Steel Morph",   50, Type.synthmorph, 40, 8),
		SteelMorphMasked("Steel Morph (Masked)",              55, Type.synthmorph, 40, 8),
		SteelMorphLiquidSilver("Steel Morph (Liquid Silver)", 70, Type.synthmorph, 40, 8),
		Sundiver    ("Sundiver",      70, Type.synthmorph, 120, 24),
		Cetus       ("Cetus",         45, Type.synthmorph, 40, 8),
		Courier     ("Courier",       70, Type.synthmorph, 30, 6),
		Fenrir      ("Fenrir",        -1, Type.synthmorph, 70, 14),
		Savant      ("Savant",        65, Type.synthmorph, 40, 8),
		Kite        ("Kite",          30, Type.synthmorph, 20, 4),
		Spare       ("Spare",          5, Type.synthmorph, 15, 3),
		XuFu        ("Xu Fu",         60, Type.synthmorph, 40, 8),
		Gargoyle    ("Gargoyle",      40, Type.synthmorph, 40, 8),
		Skulker     ("Skulker",       35, Type.synthmorph, 30, 6),
		Takko       ("Takko",         60, Type.synthmorph, 40, 8),
		Biocore     ("Biocore",       50, Type.synthmorph, 40, 8),
		Blackbird   ("Blackbird",     45, Type.synthmorph, 25, 5),
		CloudSkimmer("Cloud Skimmer", 65, Type.synthmorph, 40, 8),
		Daitya      ("Daitya",        80, Type.synthmorph, 100, 20),
		FightingKite("Fighting Kite", 35, Type.synthmorph, 30, 6),
		Galatea     ("Galatea",       65, Type.synthmorph, 40, 8),
		Griefer     ("Griefer",        5, Type.synthmorph, 20, 4),
		Guard       ("Guard",         60, Type.synthmorph, 40, 8),
		GuardDeluxe ("Guard Deluxe",  75, Type.synthmorph, 50, 10),
		Mimic       ("Mimic",         25, Type.synthmorph, 25, 5),
		Nautiloid   ("Nautiloid",    155, Type.synthmorph, 200, 40),
		Opteryx     ("Opteryx",       40, Type.synthmorph, 25, 5),
		Rover       ("Rover",         60, Type.synthmorph, 25, 5),
		SpaceFighterRover("Space Fighter Rover", 60, Type.synthmorph, 25, 5),
		SmartSwarm  ("Smart Swarm",   30, Type.synthmorph, 30, 6),
		Sphere      ("Sphere",        65, Type.synthmorph, 35, 7),
		Synthtaur   ("Synthtaur",     70, Type.synthmorph, 60, 12),
		
		Infomorph      ("Infomorph",         0, Type.infomorph),
		AgentEidolon   ("Agent Eidolon",    35, Type.infomorph),
		Digimorph      ("Digimorph",        25, Type.infomorph),
		EliteEidolon   ("Elite Eidolon",    35, Type.infomorph),
		HotShotEidolon ("Hot Shot Eidolon", 35, Type.infomorph),
		SageEidolon    ("Sage Eidolon",     40, Type.infomorph),
		ScholarEidolon ("Scholar Eidolon",  35, Type.infomorph),
		SlaveEidolon   ("Slave Eidolon",     5, Type.infomorph),
		WireheadEidolon("Wirehead Eidolon", 60, Type.infomorph)
		;
		
		String label;
		int cpCost;
		Type category;
		int durability;
		int woundThreshold;
		
		 
		// Constructors
		MorphList(String s, int cpCost, Type category){
			this.label    = s;
			this.cpCost   = cpCost;
			this.category = category;			
		}
		MorphList(String s, int cpCost, Type category, int durability, int woundThreshold){
			this.label      = s;
			this.cpCost     = cpCost;
			this.category   = category;			
			this.durability = durability;
			this.woundThreshold = woundThreshold;
		}
		
		public Boolean equals(String s){
			if (s == this.label)
				return true;
			return false;
		}
	}
	
	// Constructors
	public Morph(String string) {
		this(getMorphByLabel(string));
		if (getMorphByLabel(string) == null){
			System.out.println("Morph not found. " + type);
			return;
		}
	}
	
	public Morph(MorphList type) {
		this.type = type;
		
		this.implants = new ArrayList<String>();
		this.traits   = new ArrayList<String>();
		
		this.stats    = new LinkedHashMap<String, MorphAptitude>();		
		this.stats.put("COG", new MorphAptitude("COG", 0));
		this.stats.put("COO", new MorphAptitude("COO", 0));
		this.stats.put("INT", new MorphAptitude("INT", 0));
		this.stats.put("REF", new MorphAptitude("REF", 0));
		this.stats.put("SAV", new MorphAptitude("SAV", 0));
		this.stats.put("SOM", new MorphAptitude("SOM", 0));
		this.stats.put("WIL", new MorphAptitude("WIL", 0));
		
		switch(this.type.category){
		case biomorph:
			this.getBiomorphStats();
			break;
		case uplift:
			this.getUpliftStats();			
			break;
		case pod:
			this.getPodStats();
			break;
		case synthmorph:
			this.getSynthmorphStats();
			break;
		case infomorph:
			this.getInfomorphStats();
			break;
		default:
			break;
		
		}
		
	}


	/**
	 * Loops over the list of packages, and returns the proper list
	 * enum given a string of the pretty-print name.
	 * @param label
	 * @return
	 */
	public static MorphList getMorphByLabel(String label) {
		for (MorphList l : MorphList.values()){
			if (l.equals(label))
				return l;
		}
		return null;
	}

	@Override
	public String toString() {
		return this.type.label + " " + this.stats.toString();
	}

	/**
	 * Gets the morph label
	 * @return label
	 */
	public String getLabel() {
		return this.type.label;
	}

	/**
	 * Getter for morph type
	 * @return
	 */
	public Type getMorphType() {
		return this.type.category;
	}
	
		
	/**
	 * Gets stats for biomorphs
	 */
	private void getBiomorphStats() {
		
		this.implants.add("Basic Biomods");
		this.implants.add("Basic Mesh Inserts");
		this.implants.add("Cortical Stack");
		switch (this.type){
		case Flat:
			this.implants.clear(); // no implants for you
			this.aptMax    = 20;
			this.aptMaxSOM = 20;
			break;
		case Splicer:
			this.aptChoice = "+5 to one other aptitude";
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case Exalt:
			this.stats.get("COG").value = 5;
			this.aptChoice = "+5 to three other aptitudes";			
			break;
		case Menton:
			this.implants.add("Eidetic Memory");
			this.implants.add("Hyper Linguist");
			this.implants.add("Math Boost"); 
			this.stats.get("COG").value = 10;
			this.stats.get("INT").value = 5;
			this.stats.get("WIL").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Olympian:
			this.aptChoice = "+5 to one other aptitude";
			this.stats.get("COO").value = 5; 
			this.stats.get("REF").value = 5; 
			this.stats.get("SOM").value = 10;
			break;
		case Sylph:
			this.implants.add("Clean Metabolism");
			this.implants.add("Enhanced Pheromones");			 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Striking Looks (Level 1)");			
			this.stats.get("COO").value = 5;
			this.stats.get("SAV").value = 10;
			break;
		case Bouncer:
			this.implants.add("Grip Pads");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Prehensile Feet");
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Limber (Level 1)");
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 5;
			break;
		case Fury:
			this.implants.add("Neurachem (Level 1)");
			this.implants.add("Bioweave Armor (Light)");
			this.implants.add("Enhanced Vision");
			this.implants.add("Toxin Filters");
			this.aptChoice = "+5 to one other aptitude";
			this.stats.get("COO").value = 5; 
			this.stats.get("REF").value = 5;
			this.stats.get("SOM").value = 10; 
			this.stats.get("WIL").value = 5;
			break;
		case Futura:
			this.implants.add("Eidetic Memory");
			this.implants.add("Emotional Dampers");
			this.aptChoice = "+5 to one other aptitude";
			this.stats.get("COG").value = 5; 
			this.stats.get("SAV").value = 5; 
			this.stats.get("WIL").value = 10;
			break;
		case Ghost:
			this.implants.add("Chameleon Skin");
			this.implants.add("Adrenal Boost");
			this.implants.add("Enhanced Vision");
			this.implants.add("Grip Pads"); 
			this.aptChoice = "+5 to one other aptitude";
			this.stats.get("COO").value = 10; 
			this.stats.get("REF").value = 5; 
			this.stats.get("SOM").value = 5; 
			this.stats.get("WIL").value = 5;
			break;
		case Hibernoid:
			this.implants.add("Circadian Regulation");
			this.implants.add("Hibernation");
			this.aptChoice = "+5 to one other aptitude";
			this.stats.get("INT").value = 5;
			break;
		case Neotenic:
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Small Size");
			this.stats.get("COO").value = 5;
			this.stats.get("INT").value = 5;
			this.stats.get("REF").value = 5;
			this.aptMaxSOM = 20;
			break;
		case Remade:
			this.implants.add("Circadian Regulation");
			this.implants.add("Clean Metabolism");
			this.implants.add("Eidetic Memory");
			this.implants.add("Enhanced Respiration");
			this.implants.add("Temperature Tolerance");
			this.implants.add("Toxin Filters");
			this.aptChoice = "+5 to two other aptitudes";
			this.stats.get("COG").value = 10;
			this.stats.get("SAV").value = 5; 
			this.stats.get("SOM").value = 10;
			this.aptMax    = 40;
			this.aptMaxSOM = 40;
			break;
		case Ruster:
			this.implants.add("Enhanced Respiration");
			this.implants.add("Temperature Tolerance"); 
			this.aptChoice = "+5 to one other aptitude";
			this.stats.get("SOM").value = 5;
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case LunarFlyer:
			this.implants.add("Clean Metabolism");
			this.implants.add("Enhanced Vision");
			this.implants.add("Wings");
			this.traits.add("Flight (Movement Rate 8/40)");			 
			this.stats.get("COG").value = 5; 
			this.stats.get("COO").value = 5; 
			this.aptChoice = "+5 to two other aptitudes";
			break;
		case MartianAlpiner:
			this.implants.add("Direction Sense");
			this.implants.add("Grip Pads");
			this.implants.add("Low Pressure Tolerance");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Respirocytes");
			this.implants.add("Temperature Tolerance (Improved Cold)");
			this.stats.get("SOM").value = 5;
			this.traits.add("Skill: 10 Climbing");
			this.traits.add("Fast Metabolism ");
			this.traits.add("Planned Obsolescence"); 
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case Salamander:
			this.implants.add("Chameleon Skin");
			this.implants.add("Enhanced Vision");
			this.implants.add("Gas Jet System");
			this.implants.add("Medichines");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Prehensile Feet");
			this.implants.add("Vacuum Sealing");
			this.traits.add("Coronal Adaptation (solar)");
			this.traits.add("Limber (Level 1)");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Surya:
			this.implants.add("Enhanced Hearing");
			this.implants.add("Enhanced Vision");
			this.implants.add("Lateral Line");
			this.implants.add("Medichines");
			this.implants.add("Vacuum Sealing");
			this.traits.add("Coronal Adaptation");
			this.stats.get("COO").value = 10; 
			this.stats.get("REF").value = 5; 
			this.stats.get("SOM").value = 5;
			this.traits.add("Skill: 10 Free Fall");
			this.traits.add("Large Size");
			break;
		case VenusianGlider:
			this.implants.add("Enhanced Respiration");
			this.implants.add("Enhanced Vision");
			this.implants.add("Gliding Membranes");
			this.implants.add("Grip Pads");
			this.implants.add("Prehensile Feet");
			this.implants.add("Respirocytes");
			this.traits.add("Limber (Level 1)");
			this.stats.get("REF").value = 5; 
			this.stats.get("SOM").value = 5; 
			break;
		case Hazer:
			this.implants.add("Enhanced Vision");
			this.implants.add("Radiation Sense");
			this.implants.add("Respirocytes");
			this.implants.add("Temperature Tolerance (Improved Cold)");
			this.stats.get("COO").value = 5; 
			this.stats.get("WIL").value = 5; 
			this.aptChoice = "+5 to two other aptitudes";
			break;
		case Hulder:
			this.implants.add("Bioweave Armor (Light)");
			this.implants.add("Digging Claws");
			this.implants.add("Direction Sense");
			this.implants.add("Enhanced Vision");
			this.implants.add("Long-Term Life Support");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Radiation Sense");
			this.implants.add("Swim Bladder");
			this.implants.add("T-Ray Emitter");
			this.implants.add("Temperature Tolerance (Cryonic)");			
			this.stats.get("SOM").value = 5; 
			this.stats.get("WIL").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Temperature Intolerance (Warm)");			
			break;
		case Hyperbright:
			this.implants.add("Circadian Regulation");
			this.implants.add("Eidetic Memory");
			this.implants.add("Endocrine Control");
			this.implants.add("Grip Pads");
			this.implants.add("Hyper-Linguist");
			this.implants.add("Math Boost");
			this.implants.add("Mental Speed");
			this.implants.add("Prehensile Feet"); 
			this.stats.get("COG").value = 15; 
			this.stats.get("INT").value = 10; 
			this.stats.get("WIL").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Addiction (minor, to comfurt)");
			this.traits.add("Fast Metabolism");
			this.traits.add("Uncanny Valley");
			this.aptMax    = 40;
			this.aptMaxSOM = 30;
			break;
		case RingFlyer:
			this.implants.add("Bioweave Armor (Light)");
			this.implants.add("Enhanced Respiration");
			this.implants.add("Enhanced Vision");
			this.implants.add("Gas Jet System");
			this.implants.add("Grip Pads");
			this.implants.add("Hibernation");
			this.implants.add("Long-Term Life Support");
			this.implants.add("Medichines");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Plasma Sail Implant");
			this.implants.add("Prehensile Feet");
			this.implants.add("Radiation Tolerance");
			this.implants.add("Temperature Tolerance (Cryonic)");
			this.implants.add("Vacuum Sealing");
			this.traits.add("Bioweave Armor (Light, 2/3)");
			this.traits.add("Limber	(Level 1)");
			this.stats.get("COG").value = 5; 
			this.stats.get("COO").value = 5; 
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Selkie:
			this.implants.add("Echolocation");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Enhanced Vision");
			this.implants.add("Enhanced Respiration");
			this.implants.add("Gills");
			this.implants.add("Hydrostatic Pressure Adaptation");
			this.implants.add("Swim Bladder");
			this.implants.add("Temperature Tolerance (Improved Cold)");
			this.implants.add("Toxin Filters");
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 10 Swimming");
			this.traits.add("Movement Rate of 1/4 on land");	
			this.aptMax    = 40;
			this.aptMaxSOM = 40;
			break;
		case Aquanaut:
			this.implants.add("Enhanced Respiration");
			this.implants.add("Gills, Sonar");
			this.implants.add("Swim Bladder");
			this.implants.add("Temperature Tolerance (Improved Cold)");
			this.implants.add("Toxin Filters");
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 10 Swimming");
			break;
		case Crasher:
			this.implants.add("Bioweave Armor (Light)");
			this.implants.add("Circadian Regulation");
			this.implants.add("Clean Metabolism");
			this.implants.add("Direction Sense");
			this.implants.add("Eidetic Memory");
			this.implants.add("Enhanced Respiration");
			this.implants.add("Enhanced Vision");
			this.implants.add("Grip Pads");
			this.implants.add("Hibernation");
			this.implants.add("Medichines");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Toxin Filters");
			this.implants.add("Vacuum Sealing");			
			this.traits.add("Bioweave Armor (Light) (2/3)");
			this.stats.get("COG").value = 5;
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to three other aptitudes";
			break;
		case Dvergr:
			this.implants.add("High-G Adaptation");
			this.stats.get("SOM").value = 15;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Ariel:
			this.implants.add("Bioweave Armor (Light)");
			this.implants.add("Claws");
			this.implants.add("Direction Sense");
			this.implants.add("Enhanced Vision");
			this.implants.add("Long-Term Life Support");
			this.implants.add("Low Pressure Tolerance");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Prehensile Feet");
			this.implants.add("Radiation Sense");
			this.implants.add("T-Ray Emitter");
			this.implants.add("Temperature Tolerance (Cryonic)");
			this.implants.add("Wings"); 
			this.traits.add("Flight (Movement Rate 8/40)");			
			this.stats.get("COG").value = 5; 
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Temperature Intolerance (Warm)");
			this.traits.add("Non-Mammalian Biochemistry");
			break;
		case Bruiser:
			this.implants.add("Adrenal Boost");
			this.implants.add("Claws");
			this.implants.add("Hardened Skeleton");
			this.aptMaxSOM = 40;
			this.traits.add("Skill: 10 Intimidation");
			this.stats.get("SOM").value = 15; 
			this.stats.get("REF").value = 5;
			this.stats.get("REF").bonus = 10;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Unattractive (Level 1)");			
			this.traits.add("Large Size");
			break;
		case CloudSkate:
			this.implants.add("Bioweave Armor (Light)");
			this.implants.add("Direction Sense");
			this.implants.add("Enhanced Vision");
			this.implants.add("High-G Adaptation");
			this.implants.add("Hydrostatic Pressure Adaptation");
			this.implants.add("Long-Term Life Support");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Radiation Sense");
			this.implants.add("Radar");
			this.implants.add("Swim Bladder");
			this.implants.add("T-Ray Emitter");
			this.implants.add("Temperature Tolerance (Cryonic)");
			this.implants.add("Wings");			
			this.traits.add("Flight(8/40)");
			this.stats.get("COG").value = 5; 
			this.stats.get("REF").value = 5;
			this.stats.get("SOM").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Temperature Intolerance (Warm)");			
			break;
		case Faust:
			this.implants.add("Circadian Regulation");
			this.implants.add("Eidetic Memory");
			this.implants.add("Endocrine Control");
			this.implants.add("Hyper-Linguist");
			this.implants.add("Math Boost");
			this.traits.add("Psi Chameleon");
			this.traits.add("Psi Defense (Level 1)");			
			this.stats.get("COG").value = 10; 
			this.stats.get("WIL").value = 10; 
			this.stats.get("INT").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Freeman:
			this.implants.add("Monitor Module");
			this.implants.add("Optogenetics Module");
			this.implants.add("Puppet Sock"); 
			this.stats.get("SAV").value = 5; 
			this.aptChoice = "+5 to one other aptitude except WIL";
			this.stats.get("WIL").value = -5;
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case Grey:
			this.implants.add("Circadian Regulation");
			this.implants.add("Clean Metabolism");
			this.implants.add("Eidetic Memory");
			this.implants.add("Emotional Dampers");
			this.implants.add("Enhanced Vision");
			this.implants.add("Math Boost");
			this.stats.get("COG").value = 5; 
			this.stats.get("WIL").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Uncanny Valley");
			this.aptMaxSOM = 20;
			break;
		case Nomad:
			this.implants.add("Efficient Digestion");
			this.implants.add("Enhanced Respiration");
			this.implants.add("Fat Storage");
			this.implants.add("Medichines");
			this.implants.add("Respirocytes");
			this.implants.add("Temperature Tolerance (Improved Cold)");
			this.implants.add("Toxin Filters");
			this.stats.get("REF").value = 5;
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Uncanny Valley");
			break;
		case Observer:
			this.stats.get("INT").value = 10;
			this.stats.get("COG").value = 5;
			this.stats.get("REF").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Theseus:
			this.implants.add("Bioweave Armor (Light)");
			this.implants.add("Direction Sense");
			this.implants.add("Emergency Farcaster");
			this.implants.add("Enhanced Vision");
			this.implants.add("Low Pressure Tolerance");
			this.implants.add("Medichines");
			this.implants.add("Nanophages");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Radiation Sense");
			this.implants.add("T-Ray Emitter");
			this.implants.add("Temperature Tolerance (Cold)");
			this.implants.add("Toxin Filters"); 
			this.stats.get("SOM").value = 5; 
			this.stats.get("WIL").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			break;
		default:
			System.out.println("Biomorph not found." + this.type);
			break;
		}
				
	}
	
	
	private void getUpliftStats() {
		this.implants.add("Basic Biomods");
		this.implants.add("Basic Mesh Inserts");
		this.implants.add("Cortical Stack");
		
		switch (this.type){			
		case NeoAvian:
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Beak/Claw Attack (1d10 DV, use Unarmed Combat)");
			this.stats.get("INT").value = 5;
			this.stats.get("REF").value = 10;
			this.aptMax    = 25;
			this.aptMaxSOM = 20;
			break;
		case NeoHominid:
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 10 Climbing");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5; 
			this.stats.get("SOM").value = 5;
			this.aptMax = 25;
			this.aptMaxSOM = 25;
			break;
		case Octomorph:
			this.implants.add("360-degree Vision");
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("8 Arms");
			this.traits.add("Beak Attack (1d10 DV, use Unarmed Combat)");
			this.traits.add("Ink Attack (blinding, use Exotic Ranged: Ink Attack skill"); 
			this.traits.add("Skill: 10 Climbing");
			this.traits.add("Skill: 30 Swimming");
			this.traits.add("Limber (Level 2)");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5; 
			break;
		case Neanderthal:
			this.stats.get("COG").value = 5; 
			this.stats.get("INT").value = 5; 
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case NeoBeluga:
			this.implants.add("Echolocation");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Oxygen Reserve");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5;
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 40 Swimming");
			this.traits.add("Ramming Attack (1d10 DV)");
			this.traits.add("No sense of smell");
			this.traits.add("Large Size");
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case NeoDolphin:
			this.implants.add("Echolocation");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Oxygen Reserve");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5; 
			this.stats.get("SOM").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 40 Swimming");
			this.traits.add("Ramming Attack (1d10 DV, use Unarmed Combat skill)"); 
			this.traits.add("No sense of smell");
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case NeoGorilla:
			this.implants.add("Prehensile Feet");			
			this.stats.get("INT").value = 5; 
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case NeoOrca:
			this.implants.add("Echolocation");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Oxygen Reserve");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5; 
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 40 Swimming");
			this.traits.add("Skill: 20 Unarmed Combat");
			this.traits.add("Bite Attack (2d10 DV, use Unarmed Combat skill)"); 
			this.traits.add("No sense of smell");
			this.traits.add("Large Size");
			break;
		case NeoPig:
			this.stats.get("SOM").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case NeoPorpoise:
			this.implants.add("Echolocation");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Oxygen Reserve");
			this.stats.get("INT").value = 5; 
			this.stats.get("SOM").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 50 Swimming");
			this.traits.add("Ramming Attack (1d10 DV),"); 
			this.traits.add("No sense of smell");
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case NeoWhale:
			this.implants.add("Echolocation");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Oxygen Reserve");
			this.stats.get("COO").value = 5;
			this.stats.get("INT").value = 5;
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 40 Swimming"); 
			this.traits.add("Ramming Attack (2d10 DV, use Unarmed Combat skill)"); 
			this.traits.add("Bite Attack (4d10 DV, sperm whales only, use Unarmed Combat skill)"); 
			this.traits.add("No sense of smell");
			this.traits.add("Very Large Size");
			break;
		default:
			System.out.println("Uplift not found" + this.type);
			break;
		}
		
	}
	
	private void getPodStats() {
		this.implants.add("Basic Biomods");
		this.implants.add("Basic Mesh Inserts");
		this.implants.add("Cortical Stack");
		this.implants.add("Cyberbrain");
		this.implants.add("Mnemonic Augmentation");
		this.implants.add("Puppet Sock");
		switch (this.type){
		case PleasurePod:			
			this.implants.add("Clean Metabolism");
			this.implants.add("Enhanced Pheromones");
			this.implants.add("Sex Switch");
			this.stats.get("INT").value = 5;
			this.stats.get("SAV").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)");
			break;
		case WorkerPod:
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)");
			break;
		case Novacrab:
			this.implants.add("Carapace Armor");
			this.implants.add("Enhanced Respiration");
			this.implants.add("Gills");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Temperature Tolerance");
			this.implants.add("Vacuum Sealing");
			this.traits.add("10 legs");
			this.traits.add("Carapace Armor (11/11)");
			this.traits.add("Claw Attack (DV 2d10)");
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to two other aptitudes";
			break;
		case Digger:
			this.implants.add("Digging Claws");
			this.implants.add("Enhanced Vision");
			this.implants.add("Wrist-Mounted Tools");
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)");
			break;
		case Ripwing:
			this.implants.add("Chameleon Skin");
			this.implants.add("Enhanced Vision");
			this.implants.add("Prehensile Feet");
			this.implants.add("Beak/Claw Attack (1d10 DV, use Unarmed Combat skill)");
			this.implants.add("Flight");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5;
			this.stats.get("REF").value = 5;
			this.traits.add("Social Stigma (Neogenetic)");
			this.traits.add("Social Stigma (Pod)");
			break;
		case Scurrier:
			this.implants.add("Gliding Membrane");
			this.implants.add("Prehensile Tail"); 
			this.stats.get("SAV").value = 5;
			this.stats.get("COO").value = 10; 
			this.traits.add("6 Limbs");
			this.traits.add("Limber (Level 1)");
			this.traits.add("Skill: 10 Climbing");
			this.traits.add("Skill: 10 Freerunning"); 
			this.traits.add("Small Size");
			this.traits.add("Alien Biochemistry");
			this.traits.add(" Social Stigma (Alien)");
			this.traits.add("Social Stigma (Pod)");
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case Whiplash:
			this.implants.add("Chameleon Skin");
			this.implants.add("Tendril Attack (Unarmed Combat skill, 1d10 + (SOM ÷ 10) DV, "
					+ "+10 to disarming called shot attacks)");			 
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Alien Biochemistry");
			this.traits.add("Social Stigma (Alien)");
			this.traits.add("Social Stigma (Pod)"); 
			break;
		case Chickcharnie:
			this.implants.add("Prehensile Feet");			
			this.traits.add("Beak/Claw Attack (1d10 DV, use Unarmed Combat)");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5; 
			this.stats.get("REF").value = 10;
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case Hypergibbon:
			this.implants.add("Prehensile Feet");			
			this.stats.get("INT").value = 5; 
			this.stats.get("REF").value = 5;
			this.traits.add("Skill: 10 Climbing");
			this.traits.add("Skill: 20 Freerunning");
			this.traits.add("Limber (Level 2)");
			this.traits.add("Small Size");
			this.traits.add("Social Stigma (Pod)"); 
			break;
		case Shaper:
			this.implants.add("Clean Metabolism");
			this.implants.add("Emotional Dampers");
			this.implants.add("Gait Masking");
			this.implants.add("Nanotat ID Flux");
			this.implants.add("Sex Switch");
			this.implants.add("Skinflex");
			this.stats.get("INT").value = 5; 
			this.stats.get("SAV").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Ayah:
			this.implants.add("Enhanced Smell");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Temperature Tolerance");
			this.implants.add("Wrist-Mounted Tools");
			this.stats.get("SAV").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)");
			break;
		case BasicPod:
			this.aptChoice = "+5 to one aptitude";
			this.traits.add("Social Stigma (Pod)");
			this.aptMax = 25;
			this.aptMaxSOM = 25;
			break;
		case Critter:
			this.implants.add("Claws");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Enhanced Smell");
			this.stats.get("REF").value = 5; 
			this.stats.get("COO").value = 5;
			this.traits.add("Lacks Manipulators");
			this.traits.add("Difficult time in microgravity (–30 to Free Fall Tests)");
			this.traits.add("Non-Human Biochemistry");
			break;
		case FlyingSquid:
			this.implants.add("360-Degree Vision");
			this.implants.add("Access Jacks");
			this.implants.add("Chameleon Skin");
			this.implants.add("Extra Limbs (8 arms, 2 tentacles)");
			this.implants.add("Grip Pads");
			this.implants.add("Hydrostatic Pressure Adaptation");
			this.implants.add("Polarization Vision");
			this.traits.add("8 Arms, 2 Tentacles"); 
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 30 Swimming");
			this.traits.add("Beak Attack (1d10 + 1 DV, AP –1)");
			this.traits.add("Limber (Level 2)");
			this.traits.add("Social Stigma (Pod)");
			this.traits.add("Non-Mammalian Biochemistry");		
			break;
		case Jenkin:
			this.implants.add("Digestive Symbiotes");
			this.implants.add("Enhanced Pheromones");
			this.implants.add("Enhanced Respiration");
			this.implants.add("Enhanced Smell");
			this.implants.add("Hibernation");
			this.implants.add("Possum Cache");
			this.implants.add("Prehensile Tail");
			this.implants.add("Temperature Tolerance");
			this.implants.add("Toxin Filters");
			this.stats.get("INT").value = 5; 
			this.stats.get("REF").value = 5; 
			this.stats.get("SOM").value = 5;
			this.traits.add("Bite Attack (1d10 + 1 DV, AP –1)");
			this.traits.add("Social Stigma (Pod)");
			this.traits.add("Unattractive (Level 2)");
			this.aptMax = 25;
			this.aptMaxSOM = 25;
			break;
		case Samsa:
			this.implants.add("360-Degree Vision");
			this.implants.add("Carapace Armor");
			this.implants.add("Chameleon Skin");
			this.implants.add("Cyberclaws");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (4 Arms, 4 Legs)");
			this.implants.add("Grip Pads");
			this.implants.add("Hardened Skeleton");
			this.implants.add("Neurachem (Level 1)");
			this.implants.add("Temperature Tolerance");
			this.traits.add("Skill: 20 Intimidation");
			this.traits.add("4 arms");
			this.stats.get("SOM").value = 10;
			this.stats.get("REF").value = 5;
			this.stats.get("COO").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Carapace Armor (11/11)"); 
			this.traits.add("Social Stigma (Pod)");
			this.traits.add("Unattractive (Level 2)");
			break;
		case SecurityPod:
			this.implants.add("Adrenal Boost");
			this.implants.add("Bioweave Armor (Light)");
			this.implants.add("Claws");
			this.implants.add("Eelware");
			this.implants.add("Enhanced Vision");
			this.implants.add("Grip Pads");
			this.implants.add("T-Ray Emitter");
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)"); 
			break;
		case SpaceMarine:
			this.implants.add("Adrenal Boost");
			this.implants.add("Bioweave Armor (Light)");
			this.implants.add("Claws");
			this.implants.add("Eelware");
			this.implants.add("Enhanced Vision");
			this.implants.add("Grip Pads");
			this.implants.add("T-Ray Emitter");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Prehensile Feet");
			this.implants.add("Vacuum Sealing");
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)"); 
			break;
		case SpecialistPod:
			this.implants.add("Access Jacks");
			this.aptChoice = "+10 to one aptitude, +5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)");
			break;
		case VacuumPod:
			this.implants.add("Bioweave Armor (Light)");
			this.implants.add("Enhanced Respiration");
			this.implants.add("Grip Pads");
			this.implants.add("Oxygen Reserve");
			this.implants.add("Prehensile Feet");
			this.implants.add("Vacuum Sealing"); 
			this.traits.add("Limber (Level 1)");
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)"); 
			break;
		default:
			System.out.println("Pod not found" + this.type);
			break;

		}
		
	}


	private void getSynthmorphStats() {
		this.implants.add("Access Jacks");
		this.implants.add("Basic Mesh Inserts");
		this.implants.add("Cortical Stack");
		this.implants.add("Cyberbrain");
		this.implants.add("Mnemonic Augmentation");
		switch (this.type){
		case Case:			
			this.traits.add("Armor 4/4");
			this.traits.add("Lemon");
			this.traits.add("Social Stigma (Clanking Masses)");
			this.aptChoice = "-5 to one aptitude";
			this.aptMax    = 20;
			this.aptMaxSOM = 20;
			break;
		case Synth:
			this.traits.add("Armor 6/6");
			this.traits.add("Uncanny Valley");
			this.traits.add("Social Stigma (Clanking Masses)");
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Arachnoid:
			this.implants.add("Extra Limbs (10 Arms/Legs)");
			this.implants.add("Lidar");
			this.implants.add("Radar");
			this.implants.add("Pneumatic Limbs");
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 10; 
			this.traits.add("Armor 8/8");
			break;
		case Dragonfly:
			this.traits.add("Flight"); 
			this.stats.get("REF").value = 5; 
			this.traits.add("Armor 2/2");
			this.traits.add("Small Size");
			this.aptMaxSOM = 20;
			break;
		case Flexbot:
			this.implants.add("Fractal Digits");
			this.implants.add("Modular Design");
			this.implants.add("Nanoscopic Vision");
			this.implants.add("Shape Adjusting");
			this.traits.add("Armor 4/4");
			this.traits.add("Small Size");
			break;
		case Reaper:
			this.implants.add("360-Degree Vision");
			this.implants.add("Anti-Glare");
			this.implants.add("Cyber Claws");
			this.implants.add("Extra Limbs (4)");
			this.implants.add("Magnetic System");
			this.implants.add("Pneumatic Limbs");
			this.implants.add("Radar");
			this.implants.add("Reflex Booster");
			this.implants.add("Shape Adjusting");
			this.implants.add("Structural Enhancement");
			this.hasStructuralEnhancement = true;
			this.implants.add("T-Ray Emitter");
			this.implants.add("Weapon Mount (Articulated, 4)"); 
			this.traits.add("4 Limbs"); 
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 10;
			this.stats.get("SOM").bonus = 10; 
			this.stats.get("SOM").value = 10; 
			this.traits.add("Armor 16/16");
			this.aptMax    = 40;
			this.aptMaxSOM = 40;
			break;
		case Slitheroid:
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 5;  
			this.traits.add("Armor 8/8");
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Swarmanoid:
			this.implants.add("Swarm Composition");
			break;
		case QMorph:
			this.implants.add("Direction Sense");
			this.implants.add("Echolocation");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extreme Heat Shielding");
			this.implants.add("Extreme Pressure Adaptation");
			this.implants.add("Radar");
			this.implants.add(" T-Ray Emitter");
			this.implants.add("Wrist-Mounted Tools");
			this.traits.add("High Temperature Operation");
			this.traits.add("8 limbs");
			this.traits.add("Claw Attack (DV 2d10)");
			this.stats.get("COO").value = -5; 
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 18/18");
			this.traits.add("Only works on the Venusian surface");
			break;
		case SteelMorph:
			this.implants.add("Eidetic Memory");
			this.stats.get("SOM").value = 10; 
			this.stats.get("COG").value = 5;
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Armor 8/8");
			this.traits.add("Uncanny Valley");
			this.traits.add("Social Stigma (Clanking Masses)");
			break;
		case SteelMorphMasked:
			this.implants.add("Eidetic Memory");
			this.stats.get("SOM").value = 10;
			this.stats.get("COG").value = 5;
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Armor 8/8");
			this.traits.add("Synthetic Mask");
			break;
		case SteelMorphLiquidSilver:
			this.implants.add("Eidetic Memory");
			this.implants.add("Shape Adjusting");
			this.implants.add("Skinflex");
			this.implants.add("Wrist-Mounted Tools");
			this.stats.get("SOM").value = 10; 
			this.stats.get("COG").value = 5; 
			this.aptChoice = "+5 to three other aptitudes";
			this.traits.add("Armor 8/8");
			this.traits.add("Uncanny Valley");
			this.traits.add("Social Stigma (Clanking Masses)");
			break;
		case Sundiver:
			this.implants.add("360-Degree Vision");
			this.implants.add("Enhanced Vision");
			this.implants.add("Heavy Combat Armor");
			this.implants.add("Radar");
			this.implants.add("Reflex Booster");
			this.traits.add("Coronal Adaptation");
			this.stats.get("COO").value = 5;
			this.stats.get("REF").value = 10;
			this.stats.get("REF").bonus = 10; 
			this.traits.add("Armor 16/16");
			this.traits.add("Large Size");
			break;
		case Cetus:
			this.implants.clear(); // does't have some, crazy
			this.implants.add("Access Jacks");
			this.implants.add("Basic Mesh Inserts");
			this.implants.add("Chemical Sniffer");
			this.implants.add("Cortical Stack");
			this.implants.add("Cyberbrain");
			this.implants.add("Direction Sense");
			this.implants.add("Echolocation");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (8)");
			this.implants.add("Headlights");
			this.implants.add("Hydrostatic Pressure Adaptation");
			this.traits.add("8 Limbs");
			this.stats.get("COO").value = 5;
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to one other aptitude"; 
			this.traits.add("Armor 8/8");
			break;
		case Courier:
			this.implants.add("Chemical Sniffer");
			this.implants.add("Cryonic Protection");
			this.implants.add("Direction Sense");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (4)");
			this.implants.add("Grip Pads");
			this.implants.add("Headlights");
			this.implants.add("Hidden Compartment");
			this.implants.add("Internal Rocket");
			this.implants.add("Lidar");
			this.implants.add("Magnetic System");
			this.implants.add("Plasma Sail Implant");
			this.implants.add("Radar");
			this.implants.add("Retracting/Telescoping Limbs");
			this.implants.add("T-ray Emitter");
			this.traits.add("4 Limbs");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5;
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 6/6");
			break;
		case Fenrir:
			this.implants.add("360-Degree Vision");
			this.implants.add("Anti-Glare");
			this.implants.add("Direction Sense");
			this.implants.add("Ego Sharing (1)");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (4)");
			this.implants.add("Heavy Combat Armor");
			this.implants.add("Lidar");
			this.implants.add("Pneumatic Limbs");
			this.implants.add("Radar");
			this.implants.add("Structural Enhancement");
			this.implants.add("T-Ray Emitter");
			this.implants.add("Weapon Mount (External, Articulated, 8)");
			this.traits.add("4 Limbs");
			this.stats.get("REF").value = 5;
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 16/16 (32/32 with Heavy Combat Armor)");
			this.traits.add("Large Size");
			this.aptMax    = 35;
			this.aptMaxSOM = 35;
			break;
		case Savant:
			this.implants.add("Eidetic Memory");
			this.implants.add("Hyper-Linguist");
			this.implants.add("Math Boost");
			this.stats.get("COG").value = 10; 
			this.stats.get("INT").value = 5; 
			this.stats.get("SAV").value = 5; 
			this.stats.get("SOM").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 8/8");
			this.aptMax    = 40;
			this.aptMaxSOM = 30;
			break;
		case Kite:
			this.implants.add("Anti-Glare");
			this.implants.add("Chemical Sniffer");
			this.implants.add("Direction Sense");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Enhanced Vision");
			this.implants.add("Lidar");
			this.implants.add("Radar");
			this.implants.add("Shape Adjusting");
			this.implants.add("T-Ray Emitter");
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor (2/2)");
			this.traits.add("Small Size");
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case Spare:
			this.implants.add("Extra Limbs (3 Arms/3 Legs)");
			this.implants.add("Grip Pads");
			this.implants.add("Puppet Sock");  
			this.traits.add("Armor (2/2)");
			this.traits.add("Small Size");
			this.aptMax    = 20;
			this.aptMaxSOM = 20;
			break;
		case XuFu:
			this.implants.add("360° Vision");
			this.implants.add("Direction Sense");
			this.implants.add("Echolocation");
			this.implants.add("Electrical Sense");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (3 Arms/6 Legs)");
			this.implants.add("Grip Pads");
			this.implants.add("Lidar");
			this.implants.add("Pneumatic Limbs");
			this.implants.add("Radar");
			this.implants.add("Radiation Sense");
			this.implants.add("Puppet Sock");
			this.implants.add("Telescoping Limbs (Legs, 1 Arm)");
			this.implants.add("T-Ray Emitter");
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 5;
			this.traits.add("Armor (8/8)");
			break;
		case Gargoyle:
			this.implants.add("360-Degree Vision");
			this.implants.add("Anti-Glare");
			this.implants.add("Chemical Sniffer");
			this.implants.add("Enhanced Hearing");
			this.implants.add("Enhanced Smell");
			this.implants.add("Enhanced Vision");
			this.implants.add("Lidar");
			this.implants.add("Nanoscopic Vision");
			this.implants.add("Oracles");
			this.implants.add("Radar");
			this.implants.add("T-Ray Emitter");
			this.stats.get("INT").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 6/6");
			break;
		case Skulker:
			this.implants.add("Chameleon Skin");
			this.implants.add("Radar Invisibility");
			this.implants.add("Swarm Composition"); 
			break;
		case Takko:
			this.implants.add("360-Degree Vision");
			this.implants.add("Chameleon Skin");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (8 Arms)");
			this.implants.add("Grip Pads");
			this.implants.add("Polarization Vision");
			this.traits.add("Beak Attack (1d10 + 2 DV, use Unarmed Combat skill)");
			this.traits.add("Skill: 10 Free Fall"); 
			this.traits.add("Skill: 10 Climbing");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5; 
			this.stats.get("SOM").value = 5; 
			this.traits.add("Armor 8/8");
			break;
		case Biocore:
			this.traits.clear(); // no cyberbrain or mnemotic augmentation
			this.implants.add("Access Jacks");
			this.implants.add("Basic Mesh Inserts");
			this.implants.add("Brain Box");
			this.implants.add("Cortical Stack");
			this.implants.add("Eidetic Memory");
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Armor 6/6");
			this.traits.add("Social Stigma (Clanking Masses)");
			break;
		case Blackbird:
			this.implants.add("Enhanced Hearing");
			this.implants.add("Enhanced Vision");
			this.implants.add("Invisibility");
			this.implants.add("Puppet Sock");
			this.implants.add("Reduced Signature");
			this.stats.get("COO").value = 5; 
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 10 Flight");
			this.traits.add("Skill: 10 Infiltration");
			this.traits.add("Armor 2/2");
			this.traits.add("Beak/Claw Attack (1d10 + 1 DV, AP -1)");
			this.traits.add("Small Size");
			this.aptMaxSOM = 20;
			break;
		case CloudSkimmer:
			this.implants.add("Cryonic Protection");
			this.implants.add("Direction Sense");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (4 Arms)");
			this.implants.add("Hydrostatic Pressure Adaptation");
			this.implants.add("Internal Rocket");
			this.implants.add("Puppet Sock");
			this.implants.add("Radar");
			this.implants.add("Swim Bladder");
			this.implants.add("Telescoping Limbs");
			this.traits.add("4 arms");
			this.stats.get("SOM").value = 10; 
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 10/10");
			break;
		case Daitya:
			this.implants.add("Grip Pads");
			this.implants.add("Hardened Skeleton");
			this.implants.add("Industrial Armor");
			this.implants.add("Pneumatic Limbs (Arms)");
			this.implants.add("Puppet Sock");
			this.implants.add("Radar");
			this.implants.add("4 Weapon Mounts (Disassembly Tools; 2 fixed, 2 articulated)");
			this.implants.add("Wrist-Mounted Tools");			
			this.stats.get("SOM").value = 15; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 10/10 (20/20 with	Industrial Armor)");
			this.traits.add("Large Size");
			this.traits.add("Disassembly tools inflict 3d10 +(SOM ÷ 10) DV at AP -5");
			this.aptMaxSOM = 40;
			break;
		case FightingKite:
			this.implants.add("Anti-Glare");
			this.implants.add("Chameleon Skin");
			this.implants.add("Chemical Sniffer");
			this.implants.add("Enhanced Vision");
			this.implants.add("Neurachem (Level 1)");
			this.implants.add("Radar");
			this.implants.add("Radar Absorbent");
			this.implants.add("Shape Adjusting");
			this.implants.add("T-Ray Emitter");
			this.implants.add("Weapon Mount (Internal, Articulated, Laser Pulser)");
			this.traits.add("Flight"); 
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Armor (4/4)");
			this.traits.add("Small Size");
			this.aptMaxSOM = 25;
			break;
		case Galatea:
			this.implants.add("Chameleon Skin");
			this.implants.add("Enhanced Hearing"); 
			this.stats.get("SAV").value = 10; 
			this.stats.get("COO").value = 5;
			this.stats.get("INT").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 6/6");
			break;
		case Griefer:
			this.implants.add("Holographic Projector");
			this.implants.add("Loudspeakers");
			this.implants.add("Puppet Sock");
			this.aptChoice = "-5 to two aptitudes";
			this.traits.add("Lemon");
			this.traits.add("No Cortical Stack");
			this.traits.add("Social Stigma (Griefer)");
			this.aptMax    = 20;
			this.aptMaxSOM = 20;
			break;
		case Guard:
			this.implants.add("Chemical Sniffer");
			this.implants.add("Cyberclaws");
			this.implants.add("Enhanced Vision");
			this.implants.add("Hand Laser");
			this.implants.add("Lidar");
			this.implants.add("Neurachem (Level 1)");
			this.implants.add("Puppet Sock");
			this.implants.add("Synthetic Mask");
			this.implants.add("T-Ray Emitter");
			this.stats.get("SOM").value = 10; 
			this.stats.get("COO").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 12/12");
			break;
		case GuardDeluxe:
			this.implants.add("Nanophages");
			this.implants.add("Weapon Mount (Microwave Agonizer, Concealed)");
			this.implants.add("Chemical Sniffer");
			this.implants.add("Cyberclaws");
			this.implants.add("Enhanced Vision");
			this.implants.add("Hand Laser");
			this.implants.add("Lidar");
			this.implants.add("Neurachem (Level 1)");
			this.implants.add("Puppet Sock");
			this.implants.add("Synthetic Mask");
			this.implants.add("T-Ray Emitter");
			this.stats.get("SOM").value = 10; 
			this.stats.get("COO").value = 5;
			this.stats.get("REF").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 12/12");
			break;
		case Mimic:
			this.implants.add("Chameleon Skin");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (4 Legs, 2 Arms)");
			this.implants.add("Grip Pads");
			this.implants.add("Puppet Sock");
			this.implants.add("Shape Adjusting");
			this.implants.add("Skinflex");
			this.implants.add("Telescoping Limbs");
			this.stats.get("COO").value = 5;
			this.aptChoice = "+5 to one other aptitude"; 
			this.traits.add("Armor 4/4");
			this.traits.add("Social Stigma (Clanking Masses)");
			this.traits.add("Small Size");
			this.aptMaxSOM = 20;
			break;
		case Nautiloid:
			this.implants.add("Echolocation");
			this.implants.add("Extra Limbs (8 Arms, 2 Tentacles)");
			this.implants.add("Hydrostatic Pressure Adaptation");
			this.implants.add("Industrial Armor");
			this.implants.add("Internal Rocket");
			this.implants.add("Pneumatic Limbs (2 of the Arms)");
			this.implants.add("Puppet Sock");
			this.implants.add("Radar");
			this.implants.add("Radiation Sense");
			this.implants.add("Swim Bladder");
			this.implants.add("Telescoping Limbs (2 of the Arms)");
			this.implants.add("T-Ray Emitter");
			this.stats.get("SOM").value = 5; 
			this.aptChoice = "+5 to two other aptitudes"; 
			this.traits.add("Armor 10/10 (20/20 with Industrial Armor)");
			this.traits.add("Very Large Size");
			break;
		case Opteryx:
			this.implants.add("Claws");
			this.implants.add("Enhanced Vision");
			this.implants.add("Grip Pads");
			this.implants.add("Pneumatic Limbs (Legs)");
			this.implants.add("Prehensile Tail");
			this.stats.get("COO").value = 5;
			this.stats.get("INT").value = 5; 
			this.stats.get("REF").value = 5; 
			this.traits.add("Skill: 10 Climbing");
			this.traits.add("Skill: 10 Flight");
			this.traits.add("Armor 2/2");
			this.traits.add("Small Size");
			this.aptMaxSOM = 25;
			break;
		case Rover:
			this.implants.add("360-Degree Vision");
			this.implants.add("Chameleon Skin");
			this.implants.add("Cyberclaws");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (3 Arms)");
			this.implants.add("Gas-Jet System");
			this.implants.add("Hand Laser");
			this.implants.add("Neurachem (Level 1)");
			this.implants.add("Puppet Sock");
			this.implants.add("Radar Absorbent");
			this.implants.add("Reduced Signature");
			this.implants.add("T-Ray Emitter");
			this.implants.add("Telescoping Limbs");
			this.implants.add("Weapon Mount (Articulated, Heavy Rail Pistol)");
			this.stats.get("COO").value = 5; 
			this.stats.get("REF").value = 5;
			this.stats.get("INT").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 10/10");
			this.traits.add("Small Size");
			break;
		case SpaceFighterRover:
			this.implants.add("Internal Rocket");
			this.implants.add("360-Degree Vision");
			this.implants.add("Chameleon Skin");
			this.implants.add("Cyberclaws");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (3 Arms)");
			this.implants.add("Gas-Jet System");
			this.implants.add("Hand Laser");
			this.implants.add("Neurachem (Level 1)");
			this.implants.add("Puppet Sock");
			this.implants.add("Radar Absorbent");
			this.implants.add("Reduced Signature");
			this.implants.add("T-Ray Emitter");
			this.implants.add("Telescoping Limbs");
			this.implants.add("Weapon Mount (Articulated, Heavy Rail Pistol)");
			this.stats.get("COO").value = 5; 
			this.stats.get("REF").value = 5;
			this.stats.get("INT").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 10/10");
			this.traits.add("Small Size");
			break;
		case SmartSwarm:
			this.implants.add("360-Degree Vision");
			this.implants.add("Modular Design");
			this.implants.add("Swarm Composition");
			this.implants.add("Smart Swarm");
			break;
		case Sphere:
			this.implants.add("360-Degree Vision");
			this.implants.add("Enhanced Vision");
			this.implants.add("Extra Limbs (4 Arms)");
			this.implants.add("Gas-Jet System");
			this.implants.add("Puppet Sock");
			this.implants.add("Telescoping Limbs");
			this.traits.add("4 arms");
			this.stats.get("COG").value = 10; 
			this.aptChoice = "+5 to three other aptitudes";
			this.traits.add("Armor 6/6");
			break;
		case Synthtaur:
			this.implants.add("Extra Limbs (6 Arms/ Legs)");
			this.implants.add("Grip Pads");
			this.implants.add("Pneumatic Limbs (2 Hind Legs)");
			this.implants.add("Prehensile Feet");
			this.implants.add("Shape Adjusting");
			this.implants.add("Telescoping Limbs (4 Lower Arms/Legs)");
			this.traits.add("6 Arms/Legs");
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Armor 8/8");
			this.traits.add("Large Size");
			this.aptMaxSOM = 40;
			break;
		default:
			System.out.println("Synthmorph not found" + this.type);
			break;
		
		}
		// this.implants.add("");
		// this.traits.add("");
		//this.stats.get("").value = ;
		// \+(\d+) (\w+),
		// this\.stats\.get\(\"$2\"\)\.value = $1;
		
	}

	/**
	 * Fills out infomorph stats
	 */
	private void getInfomorphStats() {
		this.implants.add("Mnemonic Augmentation");
		this.aptMax    = 40; 
		this.aptMaxSOM = 40;
		switch (this.type){
		case Infomorph:		
			break;
		case AgentEidolon:
			this.implants.add("Eidetic Memory");
			this.implants.add("Hacking Alert");
			this.implants.add("Mental Speed");
			this.stats.get("COG").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Digimorph:
			this.aptChoice = "+5 to one aptitude";
			break;
		case EliteEidolon:
			this.implants.add("Mental Speed");			
			this.stats.get("INT").value = 5; 
			this.stats.get("SAV").value = 5;
			break;
		case HotShotEidolon:
			this.implants.add("Increased Speed");
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to one aptitude";
			break;
		case SageEidolon:
			this.implants.add("Eidetic Memory");
			this.implants.add("Hyper Linguist");
			this.implants.add("Math Boost");		
			this.stats.get("COG").value = 10; 
			this.aptChoice = "+5 to one aptitude";
			break;
		case ScholarEidolon:
			this.implants.add("Eidetic Memory");
			this.stats.get("COG").value = 5; 
			this.stats.get("INT").value = 5;
			break;
		case SlaveEidolon:
			this.implants.add("Copylock");
			this.implants.add("Modified Behavior (Level 2: Blocked - "
					+ "disobedience to a particular person or group)");			
			this.stats.get("WIL").value = -10;
			break;
		case WireheadEidolon:
			this.implants.add("Increased Speed");
			this.implants.add("Mental Speed");
			this.implants.add("Panopticon");			
			this.stats.get("REF").value = 10; 
			this.aptChoice = "+5 to one aptitude";
			break; 
		default:
			System.out.println("Infomorph not found " + this.type);
			break;
		}
	}
	

}
