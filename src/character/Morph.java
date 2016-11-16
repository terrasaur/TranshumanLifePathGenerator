package character;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import character.Aptitude.AptitudeType;
import character.Implant.ImplantList;

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
	protected ArrayList<Implant> implants;
	protected ArrayList<String> traits;
	protected String aptChoice;
	protected int aptMax = 30;
	protected int aptMaxSOM = 30;
	protected boolean hasStructuralEnhancement = false;
	
	
	public static enum MorphType{
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
	protected static enum MorphList{
		Flat          ("Flat",             0, MorphType.biomorph, 30, 6),
		Splicer       ("Splicer",         10, MorphType.biomorph, 30, 6),
		Exalt         ("Exalt",           30, MorphType.biomorph, 35, 7),
		Menton        ("Menton",          40, MorphType.biomorph, 35, 7),
		Olympian      ("Olympian",        40, MorphType.biomorph, 40, 8),
		Sylph         ("Sylph",           40, MorphType.biomorph, 35, 7),
		Bouncer       ("Bouncer",         40, MorphType.biomorph, 35, 7),
		Fury          ("Fury",            75, MorphType.biomorph, 50, 10),
		Futura        ("Futura",          40, MorphType.biomorph, 35, 7),
		Ghost         ("Ghost",           70, MorphType.biomorph, 45, 9),
		Hibernoid     ("Hibernoid",       25, MorphType.biomorph, 35, 7),
		Neotenic      ("Neotenic",        25, MorphType.biomorph, 30, 6),
		Remade        ("Remade",          60, MorphType.biomorph, 40, 8),
		Ruster        ("Ruster",          25, MorphType.biomorph, 35, 7),
		LunarFlyer    ("Lunar Flyer",     35, MorphType.biomorph, 30, 6),
		MartianAlpiner("Martian Alpiner", 30, MorphType.biomorph, 40, 8),
		Salamander    ("Salamander",      40, MorphType.biomorph, 60, 12),
		Surya         ("Surya",           50, MorphType.biomorph, 100, 20),
		VenusianGlider("Venusian Glider", 40, MorphType.biomorph, 30, 6),
		Hazer         ("Hazer",           35, MorphType.biomorph, 35, 7),
		Hulder        ("Hulder",          50, MorphType.biomorph, 40, 8),
		Hyperbright   ("Hyperbright",     70, MorphType.biomorph, 35, 7),
		RingFlyer     ("Ring Flyer",      70, MorphType.biomorph, 30, 6),
		Selkie        ("Selkie",          55, MorphType.biomorph, 40, 8),
		Aquanaut      ("Aquanaut",        50, MorphType.biomorph, 40, 8),
		Crasher       ("Crasher",         70, MorphType.biomorph, 40, 8),
		Dvergr        ("Dvergr",          50, MorphType.biomorph, 45, 9),
		Ariel         ("Ariel",           50, MorphType.biomorph, 30, 6),
		Bruiser       ("Bruiser",         60, MorphType.biomorph, 60, 10),
		CloudSkate    ("Cloud Skate",     55, MorphType.biomorph, 35, 7),
		Faust         ("Faust",           85, MorphType.biomorph, 35, 7),
		Freeman       ("Freeman",         10, MorphType.biomorph, 30, 6),
		Grey          ("Grey",            25, MorphType.biomorph, 30, 6),
		Nomad         ("Nomad",           30, MorphType.biomorph, 45, 9),
		Observer      ("Observer",        40, MorphType.biomorph, 35, 7),
		Theseus       ("Theseus",         30, MorphType.biomorph, 40, 8),
				
		NeoAvian   ("Neo-Avian",    25, MorphType.uplift, 20, 4),
		NeoHominid ("Neo-Hominid",  25, MorphType.uplift, 30, 6),
		Octomorph  ("Octomorph",    50, MorphType.uplift, 30, 6),
		Neanderthal("Neanderthal",  40, MorphType.uplift, 40, 8),
		NeoBeluga  ("Neo-Beluga",   45, MorphType.uplift, 35, 7),
		NeoDolphin ("Neo-Dolphin",  40, MorphType.uplift, 30, 6),
		NeoGorilla ("Neo-Gorilla",  35, MorphType.uplift, 40, 8),
		NeoOrca    ("Neo-Orca",     60, MorphType.uplift, 40, 8),
		NeoPig     ("Neo-Pig",      20, MorphType.uplift, 35, 7),
		NeoPorpoise("Neo-Porpoise", 35, MorphType.uplift, 25, 5),
		NeoWhale   ("Neo-Whale",    75, MorphType.uplift, 100, 20),
		
		PleasurePod  ("Pleasure Pod",   20, MorphType.pod, 30, 6),
		WorkerPod    ("Worker Pod",     20, MorphType.pod, 35, 7),
		Novacrab     ("Novacrab",       60, MorphType.pod, 40, 8),
		Digger       ("Digger",         30, MorphType.pod, 35, 7),
		Ripwing      ("Ripwing",        40, MorphType.pod, 35, 7),
		Scurrier     ("Scurrier",       40, MorphType.pod, 30, 6),
		Whiplash     ("Whiplash",       50, MorphType.pod, 40, 8),
		Chickcharnie ("Chickcharnie",   35, MorphType.pod, 35, 7),
		Hypergibbon  ("Hypergibbon",    30, MorphType.pod, 25, 5),
		Shaper       ("Shaper",         45, MorphType.pod, 30, 6),
		Ayah         ("Ayah",           25, MorphType.pod, 35, 7),
		BasicPod     ("Basic Pod",       5, MorphType.pod, 30, 6),
		Critter      ("Critter",        15, MorphType.pod, 35, 7),
		FlyingSquid  ("Flying Squid",   55, MorphType.pod, 40, 8),
		Jenkin       ("Jenkin",         20, MorphType.pod, 35, 7),
		Samsa        ("Samsa",          60, MorphType.pod, 50, 10),
		SecurityPod  ("Security Pod",   30, MorphType.pod, 35, 7),
		SpaceMarine  ("Space Marine",   30, MorphType.pod, 35, 7),
		SpecialistPod("Specialist Pod", 25, MorphType.pod, 35, 7),
		VacuumPod    ("Vacuum Pod",     30, MorphType.pod, 35, 7),
		
		Case        ("Case",           5, MorphType.synthmorph, 20, 4),
		Synth       ("Synth",         30, MorphType.synthmorph, 40, 8),
		Arachnoid   ("Arachnoid",     45, MorphType.synthmorph, 40, 8),
		Dragonfly   ("Dragonfly",     20, MorphType.synthmorph, 25, 5),
		Flexbot     ("Flexbot",       20, MorphType.synthmorph, 25, 5),
		Reaper      ("Reaper",       100, MorphType.synthmorph, 50, 10),
		Slitheroid  ("Slitheroid",    40, MorphType.synthmorph, 45, 9),
		Swarmanoid  ("Swarmanoid",    25, MorphType.synthmorph, 30, 6),
		QMorph      ("Q Morph",      100, MorphType.synthmorph, 120, 24),
		SteelMorph  ("Steel Morph",   50, MorphType.synthmorph, 40, 8),
		SteelMorphMasked("Steel Morph (Masked)",              55, MorphType.synthmorph, 40, 8),
		SteelMorphLiquidSilver("Steel Morph (Liquid Silver)", 70, MorphType.synthmorph, 40, 8),
		Sundiver    ("Sundiver",      70, MorphType.synthmorph, 120, 24),
		Cetus       ("Cetus",         45, MorphType.synthmorph, 40, 8),
		Courier     ("Courier",       70, MorphType.synthmorph, 30, 6),
		Fenrir      ("Fenrir",        -1, MorphType.synthmorph, 70, 14),
		Savant      ("Savant",        65, MorphType.synthmorph, 40, 8),
		Kite        ("Kite",          30, MorphType.synthmorph, 20, 4),
		Spare       ("Spare",          5, MorphType.synthmorph, 15, 3),
		XuFu        ("Xu Fu",         60, MorphType.synthmorph, 40, 8),
		Gargoyle    ("Gargoyle",      40, MorphType.synthmorph, 40, 8),
		Skulker     ("Skulker",       35, MorphType.synthmorph, 30, 6),
		Takko       ("Takko",         60, MorphType.synthmorph, 40, 8),
		Biocore     ("Biocore",       50, MorphType.synthmorph, 40, 8),
		Blackbird   ("Blackbird",     45, MorphType.synthmorph, 25, 5),
		CloudSkimmer("Cloud Skimmer", 65, MorphType.synthmorph, 40, 8),
		Daitya      ("Daitya",        80, MorphType.synthmorph, 100, 20),
		FightingKite("Fighting Kite", 35, MorphType.synthmorph, 30, 6),
		Galatea     ("Galatea",       65, MorphType.synthmorph, 40, 8),
		Griefer     ("Griefer",        5, MorphType.synthmorph, 20, 4),
		Guard       ("Guard",         60, MorphType.synthmorph, 40, 8),
		GuardDeluxe ("Guard Deluxe",  75, MorphType.synthmorph, 50, 10),
		Mimic       ("Mimic",         25, MorphType.synthmorph, 25, 5),
		Nautiloid   ("Nautiloid",    155, MorphType.synthmorph, 200, 40),
		Opteryx     ("Opteryx",       40, MorphType.synthmorph, 25, 5),
		Rover       ("Rover",         60, MorphType.synthmorph, 25, 5),
		SpaceFighterRover("Space Fighter Rover", 60, MorphType.synthmorph, 25, 5),
		SmartSwarm  ("Smart Swarm",   30, MorphType.synthmorph, 30, 6),
		Sphere      ("Sphere",        65, MorphType.synthmorph, 35, 7),
		Synthtaur   ("Synthtaur",     70, MorphType.synthmorph, 60, 12),
		
		Infomorph      ("Infomorph",         0, MorphType.infomorph),
		AgentEidolon   ("Agent Eidolon",    35, MorphType.infomorph),
		Digimorph      ("Digimorph",        25, MorphType.infomorph),
		EliteEidolon   ("Elite Eidolon",    35, MorphType.infomorph),
		HotShotEidolon ("Hot Shot Eidolon", 35, MorphType.infomorph),
		SageEidolon    ("Sage Eidolon",     40, MorphType.infomorph),
		ScholarEidolon ("Scholar Eidolon",  35, MorphType.infomorph),
		SlaveEidolon   ("Slave Eidolon",     5, MorphType.infomorph),
		WireheadEidolon("Wirehead Eidolon", 60, MorphType.infomorph)
		;
		
		String label;
		int cpCost;
		MorphType category;
		int durability;
		int woundThreshold;
		
		 
		// Constructors
		MorphList(String s, int cpCost, MorphType category){
			this.label    = s;
			this.cpCost   = cpCost;
			this.category = category;			
		}
		MorphList(String s, int cpCost, MorphType category, int durability, int woundThreshold){
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
		
		this.implants = new ArrayList<Implant>();
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
	public MorphType getMorphType() {
		return this.type.category;
	}
	
		
	/**
	 * Gets stats for biomorphs
	 */
	private void getBiomorphStats() {
		
		this.implants.add(new Implant(ImplantList.BasicBiomods));
		this.implants.add(new Implant(ImplantList.BasicMeshInserts));
		this.implants.add(new Implant(ImplantList.CorticalStack));
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
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.HyperLinguist));
			this.implants.add(new Implant(ImplantList.MathBoost)); 
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
			this.implants.add(new Implant(ImplantList.CleanMetabolism));
			this.implants.add(new Implant(ImplantList.EnhancedPheromones));			 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Striking Looks (Level 1)");			
			this.stats.get("COO").value = 5;
			this.stats.get("SAV").value = 10;
			break;
		case Bouncer:
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.PrehensileFeet));
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Limber (Level 1)");
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 5;
			break;
		case Fury:
			this.implants.add(new Implant(ImplantList.NeurachemL1));
			this.implants.add(new Implant(ImplantList.BioweaveArmorLight));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ToxinFilters));
			this.aptChoice = "+5 to one other aptitude";
			this.stats.get("COO").value = 5; 
			this.stats.get("REF").value = 5;
			this.stats.get("SOM").value = 10; 
			this.stats.get("WIL").value = 5;
			break;
		case Futura:
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.EmotionalDampers));
			this.aptChoice = "+5 to one other aptitude";
			this.stats.get("COG").value = 5; 
			this.stats.get("SAV").value = 5; 
			this.stats.get("WIL").value = 10;
			break;
		case Ghost:
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.AdrenalBoost));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.GripPads)); 
			this.aptChoice = "+5 to one other aptitude";
			this.stats.get("COO").value = 10; 
			this.stats.get("REF").value = 5; 
			this.stats.get("SOM").value = 5; 
			this.stats.get("WIL").value = 5;
			break;
		case Hibernoid:
			this.implants.add(new Implant(ImplantList.CircadianRegulation));
			this.implants.add(new Implant(ImplantList.Hibernation));
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
			this.implants.add(new Implant(ImplantList.CircadianRegulation));
			this.implants.add(new Implant(ImplantList.CleanMetabolism));
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.TemperatureTolerance));
			this.implants.add(new Implant(ImplantList.ToxinFilters));
			this.aptChoice = "+5 to two other aptitudes";
			this.stats.get("COG").value = 10;
			this.stats.get("SAV").value = 5; 
			this.stats.get("SOM").value = 10;
			this.aptMax    = 40;
			this.aptMaxSOM = 40;
			break;
		case Ruster:
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.TemperatureTolerance)); 
			this.aptChoice = "+5 to one other aptitude";
			this.stats.get("SOM").value = 5;
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case LunarFlyer:
			this.implants.add(new Implant(ImplantList.CleanMetabolism));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.Wings));
			this.traits.add("Flight (Movement Rate 8/40)");			 
			this.stats.get("COG").value = 5; 
			this.stats.get("COO").value = 5; 
			this.aptChoice = "+5 to two other aptitudes";
			break;
		case MartianAlpiner:
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.LowPressureTolerance));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.Respirocytes));
			this.implants.add(new Implant(ImplantList.TemperatureToleranceCold));
			this.stats.get("SOM").value = 5;
			this.traits.add("Skill: 10 Climbing");
			this.traits.add("Fast Metabolism ");
			this.traits.add("Planned Obsolescence"); 
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case Salamander:
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.GasJetSystem));
			this.implants.add(new Implant(ImplantList.Medichines));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.PrehensileFeet));
			this.implants.add(new Implant(ImplantList.VacuumSealing));
			this.traits.add("Coronal Adaptation (solar)");
			this.traits.add("Limber (Level 1)");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Surya:
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.LateralLine));
			this.implants.add(new Implant(ImplantList.Medichines));
			this.implants.add(new Implant(ImplantList.VacuumSealing));
			this.traits.add("Coronal Adaptation");
			this.stats.get("COO").value = 10; 
			this.stats.get("REF").value = 5; 
			this.stats.get("SOM").value = 5;
			this.traits.add("Skill: 10 Free Fall");
			this.traits.add("Large Size");
			break;
		case VenusianGlider:
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.GlidingMembrane));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.PrehensileFeet));
			this.implants.add(new Implant(ImplantList.Respirocytes));
			this.traits.add("Limber (Level 1)");
			this.stats.get("REF").value = 5; 
			this.stats.get("SOM").value = 5; 
			break;
		case Hazer:
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.RadiationSense));
			this.implants.add(new Implant(ImplantList.Respirocytes));
			this.implants.add(new Implant(ImplantList.TemperatureToleranceCold));
			this.stats.get("COO").value = 5; 
			this.stats.get("WIL").value = 5; 
			this.aptChoice = "+5 to two other aptitudes";
			break;
		case Hulder:
			this.implants.add(new Implant(ImplantList.BioweaveArmorLight));
			this.implants.add(new Implant(ImplantList.DiggingClaws));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.LongTermLifeSupport));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.RadiationSense));
			this.implants.add(new Implant(ImplantList.SwimBladder));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.TemperatureToleranceCryonic));			
			this.stats.get("SOM").value = 5; 
			this.stats.get("WIL").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Temperature Intolerance (Warm)");			
			break;
		case Hyperbright:
			this.implants.add(new Implant(ImplantList.CircadianRegulation));
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.EndocrineControl));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.HyperLinguist));
			this.implants.add(new Implant(ImplantList.MathBoost));
			this.implants.add(new Implant(ImplantList.MentalSpeed));
			this.implants.add(new Implant(ImplantList.PrehensileFeet)); 
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
			this.implants.add(new Implant(ImplantList.BioweaveArmorLight));
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.GasJetSystem));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.Hibernation));
			this.implants.add(new Implant(ImplantList.LongTermLifeSupport));
			this.implants.add(new Implant(ImplantList.Medichines));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.PlasmaSailImplant));
			this.implants.add(new Implant(ImplantList.PrehensileFeet));
			this.implants.add(new Implant(ImplantList.RadiationTolerance));
			this.implants.add(new Implant(ImplantList.TemperatureToleranceCryonic));
			this.implants.add(new Implant(ImplantList.VacuumSealing));
			this.traits.add("Bioweave Armor (Light, 2/3)");
			this.traits.add("Limber	(Level 1)");
			this.stats.get("COG").value = 5; 
			this.stats.get("COO").value = 5; 
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Selkie:
			this.implants.add(new Implant(ImplantList.Echolocation));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.Gills));
			this.implants.add(new Implant(ImplantList.HydrostaticPressureAdaptation));
			this.implants.add(new Implant(ImplantList.SwimBladder));
			this.implants.add(new Implant(ImplantList.TemperatureToleranceCold));
			this.implants.add(new Implant(ImplantList.ToxinFilters));
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 10 Swimming");
			this.traits.add("Movement Rate of 1/4 on land");	
			this.aptMax    = 40;
			this.aptMaxSOM = 40;
			break;
		case Aquanaut:
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.Gills));
			this.implants.add(new Implant(ImplantList.Sonar));
			this.implants.add(new Implant(ImplantList.SwimBladder));
			this.implants.add(new Implant(ImplantList.TemperatureToleranceCold));
			this.implants.add(new Implant(ImplantList.ToxinFilters));
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 10 Swimming");
			break;
		case Crasher:
			this.implants.add(new Implant(ImplantList.BioweaveArmorLight));
			this.implants.add(new Implant(ImplantList.CircadianRegulation));
			this.implants.add(new Implant(ImplantList.CleanMetabolism));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.Hibernation));
			this.implants.add(new Implant(ImplantList.Medichines));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.ToxinFilters));
			this.implants.add(new Implant(ImplantList.VacuumSealing));			
			this.traits.add("Bioweave Armor (Light) (2/3)");
			this.stats.get("COG").value = 5;
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to three other aptitudes";
			break;
		case Dvergr:
			this.implants.add(new Implant(ImplantList.HighGAdaptation));
			this.stats.get("SOM").value = 15;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Ariel:
			this.implants.add(new Implant(ImplantList.BioweaveArmorLight));
			this.implants.add(new Implant(ImplantList.Claws));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.LongTermLifeSupport));
			this.implants.add(new Implant(ImplantList.LowPressureTolerance));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.PrehensileFeet));
			this.implants.add(new Implant(ImplantList.RadiationSense));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.TemperatureToleranceCryonic));
			this.implants.add(new Implant(ImplantList.Wings)); 
			this.traits.add("Flight (Movement Rate 8/40)");			
			this.stats.get("COG").value = 5; 
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Temperature Intolerance (Warm)");
			this.traits.add("Non-Mammalian Biochemistry");
			break;
		case Bruiser:
			this.implants.add(new Implant(ImplantList.AdrenalBoost));
			this.implants.add(new Implant(ImplantList.Claws));
			this.implants.add(new Implant(ImplantList.HardenedSkeleton));
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
			this.implants.add(new Implant(ImplantList.BioweaveArmorLight));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.HighGAdaptation));
			this.implants.add(new Implant(ImplantList.HydrostaticPressureAdaptation));
			this.implants.add(new Implant(ImplantList.LongTermLifeSupport));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.RadiationSense));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.SwimBladder));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.TemperatureToleranceCryonic));
			this.implants.add(new Implant(ImplantList.Wings));			
			this.traits.add("Flight(8/40)");
			this.stats.get("COG").value = 5; 
			this.stats.get("REF").value = 5;
			this.stats.get("SOM").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Temperature Intolerance (Warm)");			
			break;
		case Faust:
			this.implants.add(new Implant(ImplantList.CircadianRegulation));
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.EndocrineControl));
			this.implants.add(new Implant(ImplantList.HyperLinguist));
			this.implants.add(new Implant(ImplantList.MathBoost));
			this.traits.add("Psi Chameleon");
			this.traits.add("Psi Defense (Level 1)");			
			this.stats.get("COG").value = 10; 
			this.stats.get("WIL").value = 10; 
			this.stats.get("INT").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Freeman:
			this.implants.add(new Implant(ImplantList.MonitorModule));
			this.implants.add(new Implant(ImplantList.OptogeneticsModule));
			this.implants.add(new Implant(ImplantList.PuppetSock)); 
			this.stats.get("SAV").value = 5; 
			this.aptChoice = "+5 to one other aptitude except WIL";
			this.stats.get("WIL").value = -5;
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case Grey:
			this.implants.add(new Implant(ImplantList.CircadianRegulation));
			this.implants.add(new Implant(ImplantList.CleanMetabolism));
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.EmotionalDampers));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.MathBoost));
			this.stats.get("COG").value = 5; 
			this.stats.get("WIL").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Uncanny Valley");
			this.aptMaxSOM = 20;
			break;
		case Nomad:
			this.implants.add(new Implant(ImplantList.EfficientDigestion));
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.FatStorage));
			this.implants.add(new Implant(ImplantList.Medichines));
			this.implants.add(new Implant(ImplantList.Respirocytes));
			this.implants.add(new Implant(ImplantList.TemperatureToleranceCold));
			this.implants.add(new Implant(ImplantList.ToxinFilters));
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
			this.implants.add(new Implant(ImplantList.BioweaveArmorLight));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.EmergencyFarcaster));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.LowPressureTolerance));
			this.implants.add(new Implant(ImplantList.Medichines));
			this.implants.add(new Implant(ImplantList.Nanophage));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.RadiationSense));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.TemperatureToleranceCold));
			this.implants.add(new Implant(ImplantList.ToxinFilters)); 
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
		this.implants.add(new Implant(ImplantList.BasicBiomods));
		this.implants.add(new Implant(ImplantList.BasicMeshInserts));
		this.implants.add(new Implant(ImplantList.CorticalStack));
		
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
			this.implants.add(new Implant(ImplantList.Vision360Degree));
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
			this.implants.add(new Implant(ImplantList.Echolocation));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
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
			this.implants.add(new Implant(ImplantList.Echolocation));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
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
			this.implants.add(new Implant(ImplantList.PrehensileFeet));			
			this.stats.get("INT").value = 5; 
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case NeoOrca:
			this.implants.add(new Implant(ImplantList.Echolocation));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
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
			this.implants.add(new Implant(ImplantList.Echolocation));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
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
			this.implants.add(new Implant(ImplantList.Echolocation));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
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
		this.implants.add(new Implant(ImplantList.BasicBiomods));
		this.implants.add(new Implant(ImplantList.BasicMeshInserts));
		this.implants.add(new Implant(ImplantList.CorticalStack));
		this.implants.add(new Implant(ImplantList.Cyberbrain));
		this.implants.add(new Implant(ImplantList.MnemonicAugmentation));
		this.implants.add(new Implant(ImplantList.PuppetSock));
		switch (this.type){
		case PleasurePod:			
			this.implants.add(new Implant(ImplantList.CleanMetabolism));
			this.implants.add(new Implant(ImplantList.EnhancedPheromones));
			this.implants.add(new Implant(ImplantList.SexSwitch));
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
			this.implants.add(new Implant(ImplantList.CarapaceArmor));
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.Gills));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.TemperatureTolerance));
			this.implants.add(new Implant(ImplantList.VacuumSealing));
			this.traits.add("10 legs");
			this.traits.add("Carapace Armor (11/11)");
			this.traits.add("Claw Attack (DV 2d10)");
			this.stats.get("SOM").value = 10;
			this.aptChoice = "+5 to two other aptitudes";
			break;
		case Digger:
			this.implants.add(new Implant(ImplantList.DiggingClaws));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.WristMountedTools));
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)");
			break;
		case Ripwing:
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.PrehensileFeet));
			this.traits.add("Beak/Claw Attack (1d10 DV, use Unarmed Combat skill)");
			this.implants.add(new Implant(ImplantList.Wings));
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5;
			this.stats.get("REF").value = 5;
			this.traits.add("Social Stigma (Neogenetic)");
			this.traits.add("Social Stigma (Pod)");
			break;
		case Scurrier:
			this.implants.add(new Implant(ImplantList.GlidingMembrane));
			this.implants.add(new Implant(ImplantList.PrehensileTail)); 
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
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.traits.add("Tendril Attack (Unarmed Combat skill, 1d10 + (SOM / 10) DV, "
					+ "+10 to disarming called shot attacks)");			 
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Alien Biochemistry");
			this.traits.add("Social Stigma (Alien)");
			this.traits.add("Social Stigma (Pod)"); 
			break;
		case Chickcharnie:
			this.implants.add(new Implant(ImplantList.PrehensileFeet));			
			this.traits.add("Beak/Claw Attack (1d10 DV, use Unarmed Combat)");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5; 
			this.stats.get("REF").value = 10;
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case Hypergibbon:
			this.implants.add(new Implant(ImplantList.PrehensileFeet));			
			this.stats.get("INT").value = 5; 
			this.stats.get("REF").value = 5;
			this.traits.add("Skill: 10 Climbing");
			this.traits.add("Skill: 20 Freerunning");
			this.traits.add("Limber (Level 2)");
			this.traits.add("Small Size");
			this.traits.add("Social Stigma (Pod)"); 
			break;
		case Shaper:
			this.implants.add(new Implant(ImplantList.CleanMetabolism));
			this.implants.add(new Implant(ImplantList.EmotionalDampers));
			this.implants.add(new Implant(ImplantList.GaitMasking));
			this.implants.add(new Implant(ImplantList.NanotatIDFlux));
			this.implants.add(new Implant(ImplantList.SexSwitch));
			this.implants.add(new Implant(ImplantList.Skinflex));
			this.stats.get("INT").value = 5; 
			this.stats.get("SAV").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Ayah:
			this.implants.add(new Implant(ImplantList.EnhancedSmell));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.TemperatureTolerance));
			this.implants.add(new Implant(ImplantList.WristMountedTools));
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
			this.implants.add(new Implant(ImplantList.Claws));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.EnhancedSmell));
			this.stats.get("REF").value = 5; 
			this.stats.get("COO").value = 5;
			this.traits.add("Lacks Manipulators");
			this.traits.add("Difficult time in microgravity (-30 to Free Fall Tests)");
			this.traits.add("Non-Human Biochemistry");
			break;
		case FlyingSquid:
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.AccessJacks));
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "8 arms, 2 tentacles"));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.HydrostaticPressureAdaptation));
			this.implants.add(new Implant(ImplantList.PolarizationVision));
			this.traits.add("8 Arms, 2 Tentacles"); 
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Skill: 30 Swimming");
			this.traits.add("Beak Attack (1d10 + 1 DV, AP -1)");
			this.traits.add("Limber (Level 2)");
			this.traits.add("Social Stigma (Pod)");
			this.traits.add("Non-Mammalian Biochemistry");		
			break;
		case Jenkin:
			this.implants.add(new Implant(ImplantList.DigestiveSymbiotes));
			this.implants.add(new Implant(ImplantList.EnhancedPheromones));
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.EnhancedSmell));
			this.implants.add(new Implant(ImplantList.Hibernation));
			this.implants.add(new Implant(ImplantList.PossumCache));
			this.implants.add(new Implant(ImplantList.PrehensileTail));
			this.implants.add(new Implant(ImplantList.TemperatureTolerance));
			this.implants.add(new Implant(ImplantList.ToxinFilters));
			this.stats.get("INT").value = 5; 
			this.stats.get("REF").value = 5; 
			this.stats.get("SOM").value = 5;
			this.traits.add("Bite Attack (1d10 + 1 DV, AP -1)");
			this.traits.add("Social Stigma (Pod)");
			this.traits.add("Unattractive (Level 2)");
			this.aptMax = 25;
			this.aptMaxSOM = 25;
			break;
		case Samsa:
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.CarapaceArmor));
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.Cyberclaws));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "4 Arms, 4 Legs"));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.HardenedSkeleton));
			this.implants.add(new Implant(ImplantList.NeurachemL1));
			this.implants.add(new Implant(ImplantList.TemperatureTolerance));
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
			this.implants.add(new Implant(ImplantList.AdrenalBoost));
			this.implants.add(new Implant(ImplantList.BioweaveArmorLight));
			this.implants.add(new Implant(ImplantList.Claws));
			this.implants.add(new Implant(ImplantList.Eelware));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)"); 
			break;
		case SpaceMarine:
			this.implants.add(new Implant(ImplantList.AdrenalBoost));
			this.implants.add(new Implant(ImplantList.BioweaveArmorLight));
			this.implants.add(new Implant(ImplantList.Claws));
			this.implants.add(new Implant(ImplantList.Eelware));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.PrehensileFeet));
			this.implants.add(new Implant(ImplantList.VacuumSealing));
			this.stats.get("SOM").value = 10; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)"); 
			break;
		case SpecialistPod:
			this.implants.add(new Implant(ImplantList.AccessJacks));
			this.aptChoice = "+10 to one aptitude, +5 to one other aptitude";
			this.traits.add("Social Stigma (Pod)");
			break;
		case VacuumPod:
			this.implants.add(new Implant(ImplantList.BioweaveArmorLight));
			this.implants.add(new Implant(ImplantList.EnhancedRespiration));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.OxygenReserve));
			this.implants.add(new Implant(ImplantList.PrehensileFeet));
			this.implants.add(new Implant(ImplantList.VacuumSealing)); 
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
		this.implants.add(new Implant(ImplantList.AccessJacks));
		this.implants.add(new Implant(ImplantList.BasicMeshInserts));
		this.implants.add(new Implant(ImplantList.CorticalStack));
		this.implants.add(new Implant(ImplantList.Cyberbrain));
		this.implants.add(new Implant(ImplantList.MnemonicAugmentation));
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
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "10 Arms/Legs"));
			this.implants.add(new Implant(ImplantList.Lidar));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.PneumaticLimbs));
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
			this.implants.add(new Implant(ImplantList.FractalDigits));
			this.implants.add(new Implant(ImplantList.ModularDesign));
			this.implants.add(new Implant(ImplantList.NanoscopicVision));
			this.implants.add(new Implant(ImplantList.ShapeAdjusting));
			this.traits.add("Armor 4/4");
			this.traits.add("Small Size");
			break;
		case Reaper:
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.AntiGlare));
			this.implants.add(new Implant(ImplantList.Cyberclaws));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "4"));
			this.implants.add(new Implant(ImplantList.MagneticSystem));
			this.implants.add(new Implant(ImplantList.PneumaticLimbs));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.ReflexBooster));
			this.implants.add(new Implant(ImplantList.ShapeAdjusting));
			this.implants.add(new Implant(ImplantList.StructuralEnhancement));
			this.hasStructuralEnhancement = true;
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.WeaponMountArticulated, "4")); 
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
			this.implants.add(new Implant(ImplantList.SwarmComposition));
			break;
		case QMorph:
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.Echolocation));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtremeHeatShielding));
			this.implants.add(new Implant(ImplantList.ExtremePressureAdaptation));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.WristMountedTools));
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
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.stats.get("SOM").value = 10; 
			this.stats.get("COG").value = 5;
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Armor 8/8");
			this.traits.add("Uncanny Valley");
			this.traits.add("Social Stigma (Clanking Masses)");
			break;
		case SteelMorphMasked:
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.stats.get("SOM").value = 10;
			this.stats.get("COG").value = 5;
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Armor 8/8");
			this.traits.add("Synthetic Mask");
			break;
		case SteelMorphLiquidSilver:
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.ShapeAdjusting));
			this.implants.add(new Implant(ImplantList.Skinflex));
			this.implants.add(new Implant(ImplantList.WristMountedTools));
			this.stats.get("SOM").value = 10; 
			this.stats.get("COG").value = 5; 
			this.aptChoice = "+5 to three other aptitudes";
			this.traits.add("Armor 8/8");
			this.traits.add("Uncanny Valley");
			this.traits.add("Social Stigma (Clanking Masses)");
			break;
		case Sundiver:
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ImplantedArmor, "Combat Armor (heavy)"));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.ReflexBooster));
			this.traits.add("Coronal Adaptation");
			this.stats.get("COO").value = 5;
			this.stats.get("REF").value = 10;
			this.stats.get("REF").bonus = 10; 
			this.traits.add("Armor 16/16");
			this.traits.add("Large Size");
			break;
		case Cetus:
			this.implants.clear(); // does't have some, crazy
			this.implants.add(new Implant(ImplantList.AccessJacks));
			this.implants.add(new Implant(ImplantList.BasicMeshInserts));
			this.implants.add(new Implant(ImplantList.ChemicalSniffer));
			this.implants.add(new Implant(ImplantList.CorticalStack));
			this.implants.add(new Implant(ImplantList.Cyberbrain));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.Echolocation));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "8"));
			this.implants.add(new Implant(ImplantList.Headlights));
			this.implants.add(new Implant(ImplantList.HydrostaticPressureAdaptation));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "8"));
			this.stats.get("COO").value = 5;
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to one other aptitude"; 
			this.traits.add("Armor 8/8");
			break;
		case Courier:
			this.implants.add(new Implant(ImplantList.ChemicalSniffer));
			this.implants.add(new Implant(ImplantList.CryonicProtection));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "4"));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.Headlights));
			this.implants.add(new Implant(ImplantList.HiddenCompartment));
			this.implants.add(new Implant(ImplantList.InternalRocket));
			this.implants.add(new Implant(ImplantList.Lidar));
			this.implants.add(new Implant(ImplantList.MagneticSystem));
			this.implants.add(new Implant(ImplantList.PlasmaSailImplant));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.TelescopingLimbs));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.traits.add("4 Limbs");
			this.stats.get("COO").value = 5; 
			this.stats.get("INT").value = 5;
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 6/6");
			break;
		case Fenrir:
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.AntiGlare));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.EgoSharing, "1"));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "4"));
			this.implants.add(new Implant(ImplantList.ImplantedArmor, "Combat Armor (heavy)"));
			this.implants.add(new Implant(ImplantList.Lidar));
			this.implants.add(new Implant(ImplantList.PneumaticLimbs));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.StructuralEnhancement));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.WeaponMountArticulated, "External, 8"));
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
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.HyperLinguist));
			this.implants.add(new Implant(ImplantList.MathBoost));
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
			this.implants.add(new Implant(ImplantList.AntiGlare));
			this.implants.add(new Implant(ImplantList.ChemicalSniffer));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.Lidar));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.ShapeAdjusting));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor (2/2)");
			this.traits.add("Small Size");
			this.aptMax    = 25;
			this.aptMaxSOM = 25;
			break;
		case Spare:
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "3 Arms/3 Legs"));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.PuppetSock));  
			this.traits.add("Armor (2/2)");
			this.traits.add("Small Size");
			this.aptMax    = 20;
			this.aptMaxSOM = 20;
			break;
		case XuFu:
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.Echolocation));
			this.implants.add(new Implant(ImplantList.ElectricalSense));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "3 Arms/6 Legs"));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.Lidar));
			this.implants.add(new Implant(ImplantList.PneumaticLimbs));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.RadiationSense));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.TelescopingLimbs, "Legs, 1 Arm"));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.stats.get("COO").value = 5; 
			this.stats.get("SOM").value = 5;
			this.traits.add("Armor (8/8)");
			break;
		case Gargoyle:
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.AntiGlare));
			this.implants.add(new Implant(ImplantList.ChemicalSniffer));
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.EnhancedSmell));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.Lidar));
			this.implants.add(new Implant(ImplantList.NanoscopicVision));
			this.implants.add(new Implant(ImplantList.Oracle));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.stats.get("INT").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 6/6");
			break;
		case Skulker:
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.RadarInvisibility));
			this.implants.add(new Implant(ImplantList.SwarmComposition)); 
			break;
		case Takko:
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "8 Arms"));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.PolarizationVision));
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
			this.implants.add(new Implant(ImplantList.AccessJacks));
			this.implants.add(new Implant(ImplantList.BasicMeshInserts));
			this.implants.add(new Implant(ImplantList.BrainBox));
			this.implants.add(new Implant(ImplantList.CorticalStack));
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.stats.get("SOM").value = 5;
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Armor 6/6");
			this.traits.add("Social Stigma (Clanking Masses)");
			break;
		case Blackbird:
			this.implants.add(new Implant(ImplantList.EnhancedHearing));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.Invisibility));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.ReducedSignature));
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
			this.implants.add(new Implant(ImplantList.CryonicProtection));
			this.implants.add(new Implant(ImplantList.DirectionSense));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "4 Arms"));
			this.implants.add(new Implant(ImplantList.HydrostaticPressureAdaptation));
			this.implants.add(new Implant(ImplantList.InternalRocket));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.SwimBladder));
			this.implants.add(new Implant(ImplantList.TelescopingLimbs));
			this.traits.add("4 arms");
			this.stats.get("SOM").value = 10; 
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 10/10");
			break;
		case Daitya:
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.HardenedSkeleton));
			this.implants.add(new Implant(ImplantList.ImplantedArmor, "Industrial Armor"));
			this.implants.add(new Implant(ImplantList.PneumaticLimbs, "Arms"));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.WeaponMountArticulated, "2 Disassembly Tools; 2 fixed"));
			this.implants.add(new Implant(ImplantList.WristMountedTools));			
			this.stats.get("SOM").value = 15; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 10/10 (20/20 with	Industrial Armor)");
			this.traits.add("Large Size");
			this.traits.add("Disassembly tools inflict 3d10 +(SOM / 10) DV at AP -5");
			this.aptMaxSOM = 40;
			break;
		case FightingKite:
			this.implants.add(new Implant(ImplantList.AntiGlare));
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.ChemicalSniffer));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.NeurachemL1));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.RadarAbsorbent));
			this.implants.add(new Implant(ImplantList.ShapeAdjusting));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.WeaponMountArticulated, "Internal, Laser Pulser"));
			this.traits.add("Flight"); 
			this.aptChoice = "+5 to two other aptitudes";
			this.traits.add("Armor (4/4)");
			this.traits.add("Small Size");
			this.aptMaxSOM = 25;
			break;
		case Galatea:
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.EnhancedHearing)); 
			this.stats.get("SAV").value = 10; 
			this.stats.get("COO").value = 5;
			this.stats.get("INT").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 6/6");
			break;
		case Griefer:
			this.implants.add(new Implant(ImplantList.HolographicProjector));
			this.implants.add(new Implant(ImplantList.SoundSystem));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.aptChoice = "-5 to two aptitudes";
			this.traits.add("Lemon");
			this.traits.add("No Cortical Stack");
			this.traits.add("Social Stigma (Griefer)");
			this.aptMax    = 20;
			this.aptMaxSOM = 20;
			break;
		case Guard:
			this.implants.add(new Implant(ImplantList.ChemicalSniffer));
			this.implants.add(new Implant(ImplantList.Cyberclaws));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.HandLaser));
			this.implants.add(new Implant(ImplantList.Lidar));
			this.implants.add(new Implant(ImplantList.NeurachemL1));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.SyntheticMask));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.stats.get("SOM").value = 10; 
			this.stats.get("COO").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 12/12");
			break;
		case GuardDeluxe:
			this.implants.add(new Implant(ImplantList.Nanophage));
			this.implants.add(new Implant(ImplantList.WeaponMountConcealed, "Microwave Agonizer"));
			this.implants.add(new Implant(ImplantList.ChemicalSniffer));
			this.implants.add(new Implant(ImplantList.Cyberclaws));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.HandLaser));
			this.implants.add(new Implant(ImplantList.Lidar));
			this.implants.add(new Implant(ImplantList.NeurachemL1));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.SyntheticMask));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.stats.get("SOM").value = 10; 
			this.stats.get("COO").value = 5;
			this.stats.get("REF").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 12/12");
			break;
		case Mimic:
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "4 Legs, 2 Arms"));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.ShapeAdjusting));
			this.implants.add(new Implant(ImplantList.Skinflex));
			this.implants.add(new Implant(ImplantList.TelescopingLimbs));
			this.stats.get("COO").value = 5;
			this.aptChoice = "+5 to one other aptitude"; 
			this.traits.add("Armor 4/4");
			this.traits.add("Social Stigma (Clanking Masses)");
			this.traits.add("Small Size");
			this.aptMaxSOM = 20;
			break;
		case Nautiloid:
			this.implants.add(new Implant(ImplantList.Echolocation));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "8 Arms, 2 Tentacles"));
			this.implants.add(new Implant(ImplantList.HydrostaticPressureAdaptation));
			this.implants.add(new Implant(ImplantList.ImplantedArmor, "Industrial Armor"));
			this.implants.add(new Implant(ImplantList.InternalRocket));
			this.implants.add(new Implant(ImplantList.PneumaticLimbs, "2 of the Arms"));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.Radar));
			this.implants.add(new Implant(ImplantList.RadiationSense));
			this.implants.add(new Implant(ImplantList.SwimBladder));
			this.implants.add(new Implant(ImplantList.TelescopingLimbs, "2 of the Arms"));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.stats.get("SOM").value = 5; 
			this.aptChoice = "+5 to two other aptitudes"; 
			this.traits.add("Armor 10/10 (20/20 with Industrial Armor)");
			this.traits.add("Very Large Size");
			break;
		case Opteryx:
			this.implants.add(new Implant(ImplantList.Claws));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.PneumaticLimbs, "Legs"));
			this.implants.add(new Implant(ImplantList.PrehensileTail));
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
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.Cyberclaws));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "3 Arms"));
			this.implants.add(new Implant(ImplantList.GasJetSystem));
			this.implants.add(new Implant(ImplantList.HandLaser));
			this.implants.add(new Implant(ImplantList.NeurachemL1));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.RadarAbsorbent));
			this.implants.add(new Implant(ImplantList.ReducedSignature));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.TelescopingLimbs));
			this.implants.add(new Implant(ImplantList.WeaponMountArticulated, "Heavy Rail Pistol"));
			this.stats.get("COO").value = 5; 
			this.stats.get("REF").value = 5;
			this.stats.get("INT").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 10/10");
			this.traits.add("Small Size");
			break;
		case SpaceFighterRover:
			this.implants.add(new Implant(ImplantList.InternalRocket));
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.ChameleonSkin));
			this.implants.add(new Implant(ImplantList.Cyberclaws));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "3 Arms"));
			this.implants.add(new Implant(ImplantList.GasJetSystem));
			this.implants.add(new Implant(ImplantList.HandLaser));
			this.implants.add(new Implant(ImplantList.NeurachemL1));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.RadarAbsorbent));
			this.implants.add(new Implant(ImplantList.ReducedSignature));
			this.implants.add(new Implant(ImplantList.TRayEmitter));
			this.implants.add(new Implant(ImplantList.TelescopingLimbs));
			this.implants.add(new Implant(ImplantList.WeaponMountArticulated, "Heavy Rail Pistol"));
			this.stats.get("COO").value = 5; 
			this.stats.get("REF").value = 5;
			this.stats.get("INT").value = 5;
			this.aptChoice = "+5 to one other aptitude";
			this.traits.add("Armor 10/10");
			this.traits.add("Small Size");
			break;
		case SmartSwarm:
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.ModularDesign));
			this.implants.add(new Implant(ImplantList.SwarmComposition));
			this.implants.add(new Implant(ImplantList.SmartSwarm));
			break;
		case Sphere:
			this.implants.add(new Implant(ImplantList.Vision360Degree));
			this.implants.add(new Implant(ImplantList.EnhancedVision));
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "4 Arms"));
			this.implants.add(new Implant(ImplantList.GasJetSystem));
			this.implants.add(new Implant(ImplantList.PuppetSock));
			this.implants.add(new Implant(ImplantList.TelescopingLimbs));
			this.traits.add("4 arms");
			this.stats.get("COG").value = 10; 
			this.aptChoice = "+5 to three other aptitudes";
			this.traits.add("Armor 6/6");
			break;
		case Synthtaur:
			this.implants.add(new Implant(ImplantList.ExtraLimbs, "6 Arms/ Legs"));
			this.implants.add(new Implant(ImplantList.GripPads));
			this.implants.add(new Implant(ImplantList.PneumaticLimbs, "2 Hind Legs"));
			this.implants.add(new Implant(ImplantList.PrehensileFeet));
			this.implants.add(new Implant(ImplantList.ShapeAdjusting));
			this.implants.add(new Implant(ImplantList.TelescopingLimbs, "4 Lower Arms/Legs"));
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
		this.implants.add(new Implant(ImplantList.MnemonicAugmentation));
		this.aptMax    = 40; 
		this.aptMaxSOM = 40;
		switch (this.type){
		case Infomorph:		
			break;
		case AgentEidolon:
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.HackingAlert));
			this.implants.add(new Implant(ImplantList.MentalSpeed));
			this.stats.get("COG").value = 5; 
			this.aptChoice = "+5 to one other aptitude";
			break;
		case Digimorph:
			this.aptChoice = "+5 to one aptitude";
			break;
		case EliteEidolon:
			this.implants.add(new Implant(ImplantList.MentalSpeed));			
			this.stats.get("INT").value = 5; 
			this.stats.get("SAV").value = 5;
			break;
		case HotShotEidolon:
			this.implants.add(new Implant(ImplantList.IncreasedSpeed));
			this.stats.get("REF").value = 5; 
			this.aptChoice = "+5 to one aptitude";
			break;
		case SageEidolon:
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.implants.add(new Implant(ImplantList.HyperLinguist));
			this.implants.add(new Implant(ImplantList.MathBoost));		
			this.stats.get("COG").value = 10; 
			this.aptChoice = "+5 to one aptitude";
			break;
		case ScholarEidolon:
			this.implants.add(new Implant(ImplantList.EideticMemory));
			this.stats.get("COG").value = 5; 
			this.stats.get("INT").value = 5;
			break;
		case SlaveEidolon:
			this.implants.add(new Implant(ImplantList.Copylock));
			this.traits.add("Modified Behavior (Level 2: Blocked - "
					+ "disobedience to a particular person or group)");			
			this.stats.get("WIL").value = -10;
			break;
		case WireheadEidolon:
			this.implants.add(new Implant(ImplantList.IncreasedSpeed));
			this.implants.add(new Implant(ImplantList.MentalSpeed));
			this.implants.add(new Implant(ImplantList.Panopticon));			
			this.stats.get("REF").value = 10; 
			this.aptChoice = "+5 to one aptitude";
			break; 
		default:
			System.out.println("Infomorph not found " + this.type);
			break;
		}
	}
	

}
