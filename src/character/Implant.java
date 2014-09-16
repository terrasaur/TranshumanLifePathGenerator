package character;

import java.util.ArrayList;



import character.Gear.GearCosts;
import character.Gear.GearTypes;

public class Implant {
	
	enum ImplantType {
		Standard("Standard Augmentations"),
		Bioware("Bioware"), 
		Cyberware("Cyberware"),
		Nanoware("Nanoware"),
		Cosmetic("Cosmetic Modifications"),
		Robotic("Robotic Enhancements"),
		Software("Software Plug-Ins");
		
		String label;
		ImplantType(String s){
			this.label = s;
		}
	}
	
	ImplantList name;
	Gear subtype;
	
	public Implant(String label){
		this(getAugTypeByString(label));
	}
	public Implant(String label, String subtypeChoice){
		 this(getAugTypeByString(label), subtypeChoice);
	}
	
	public Implant(ImplantList name, String subtypeChoice){
		this.name = name;
		if (this.name == null){			
			return;
		}
		if (this.name.subtypeList != null){			
			if (subtypeChoice == null){
				System.out.println("Error: You need to specify a subtype. Your choices are:");
				System.out.println(this.name.subtypeList);
				return;
			} 

			int idx = this.name.subtypeList.indexOf(new Gear(subtypeChoice));			
			if (idx < 0){
				System.out.println("Error: Subtype " + subtypeChoice + " not found.");
				return;
			}
			
			this.subtype = this.name.subtypeList.get(idx);
			int newCost = this.subtype.cost.ordinal() + 1;
			if (newCost > GearCosts.ExpensivePlus.ordinal())
				newCost = GearCosts.ExpensivePlus.ordinal();
			this.name.cost = GearCosts.values()[newCost];
						
		}
	}


	public Implant(ImplantList name) {
		this.name = name;
		if (this.name == null){			
			return;
		}
		if (this.name.subtypeList != null) {
			System.out.println("Error: You need to specify a subtype. Your choices are:");
			System.out.println(this.name.subtypeList);		
		}
	}
	
	
	private static ImplantList getAugTypeByString(String label) {
		for (ImplantList l : ImplantList.values()){
			if (l.equals(label))
				return l;
		}
		System.out.println("Error: Augmentation not found: " + label);
		return null;
	}



	enum ImplantList {		
		BasicBiomods    ("Basic Biomods",      GearCosts.Moderate, ImplantType.Standard),
		BasicMeshInserts("Basic Mesh Inserts", GearCosts.Moderate, ImplantType.Standard),
		CorticalStack   ("Cortical Stack",     GearCosts.Moderate, ImplantType.Standard),
		Cyberbrain      ("Cyberbrain",         GearCosts.Moderate, ImplantType.Standard),
		
		DirectionSense       ("Direction Sense",         GearCosts.Low,       ImplantType.Bioware),
		Echolocation         ("Echolocation",            GearCosts.Low,       ImplantType.Bioware),
		EnhancedHearing      ("Enhanced Hearing",        GearCosts.Low,       ImplantType.Bioware),
		EnhancedSmell        ("Enhanced Smell",          GearCosts.Low,       ImplantType.Bioware),
		EnhancedVision       ("Enhanced Vision",         GearCosts.Low,       ImplantType.Bioware),
		PolarizationVision   ("Polarization Vision",     GearCosts.Low,       ImplantType.Bioware),
		UltravioletVision    ("Ultraviolet Vision",      GearCosts.Trivial,   ImplantType.Bioware),
		EideticMemory        ("Eidetic Memory",          GearCosts.Low,       ImplantType.Bioware),
		HyperLinguist        ("Hyper Linguist",          GearCosts.Low,       ImplantType.Bioware),
		MathBoost            ("Math Boost",              GearCosts.Low,       ImplantType.Bioware),
		MultiplePersonalities("Multiple Personalities",  GearCosts.High,      ImplantType.Bioware),
		AdrenalBoost         ("Adrenal Boost",           GearCosts.High,      ImplantType.Bioware),
		BioweaveArmorLight   ("Bioweave Armor (light)",  GearCosts.Low,       ImplantType.Bioware),
		BioweaveArmorHeavy   ("Bioweave Armor (heavy)",  GearCosts.Moderate,  ImplantType.Bioware),
		CarapaceArmor        ("Carapace Armor",          GearCosts.Moderate,  ImplantType.Bioware),
		ChameleonSkin        ("Chameleon Skin",          GearCosts.Low,       ImplantType.Bioware),
		CircadianRegulation  ("Circadian Regulation",    GearCosts.Moderate,  ImplantType.Bioware),
		Claws                ("Claws",                   GearCosts.Low,       ImplantType.Bioware),
		CleanMetabolism      ("Clean Metabolism",        GearCosts.Moderate,  ImplantType.Bioware),
		DrugGlands           ("Drug Glands",             null,                ImplantType.Bioware, 
				Gear.getGearFromSubtype(new GearTypes[]{GearTypes.ConventionalDrugs, GearTypes.PsiDrugs})),
		Eelware              ("Eelware",                 GearCosts.Low,       ImplantType.Bioware),
		EmotionalDampers     ("Emotional Dampers",       GearCosts.Low,       ImplantType.Bioware),
		EndocrineControl     ("Endocrine Control",       GearCosts.High,      ImplantType.Bioware),
		EnhancedPheromones   ("Enhanced Pheromones",     GearCosts.Low,       ImplantType.Bioware),
		EnhancedRespiration  ("Enhanced Respiration",    GearCosts.Low,       ImplantType.Bioware),
		Gills                ("Gills",                   GearCosts.Low,       ImplantType.Bioware),
		GripPads             ("Grip Pads",               GearCosts.Low,       ImplantType.Bioware),
		Hibernation          ("Hibernation",             GearCosts.Low,       ImplantType.Bioware),
		MuscleAugmentation   ("Muscle Augmentation",     GearCosts.High,      ImplantType.Bioware),
		NeurachemL1          ("Neurachem (L1)",          GearCosts.High,      ImplantType.Bioware),
		NeurachemL2          ("Neurachem (L2)",          GearCosts.Expensive, ImplantType.Bioware),
		PoisonGlands         ("Poison Glands",           null,                ImplantType.Bioware, 
				Gear.getGearFromSubtype(new GearTypes[]{GearTypes.Toxins})),
		PrehensileFeet       ("Prehensile Feet",         GearCosts.Low,       ImplantType.Bioware),
		PrehensileTail       ("Prehensile Tail",         GearCosts.Low,       ImplantType.Bioware),
		SexSwitch            ("Sex Switch",              GearCosts.Moderate,  ImplantType.Bioware),
		SkinPocket           ("Skin Pocket",             GearCosts.Trivial,   ImplantType.Bioware),
		TemperatureTolerance ("Temperature Tolerance",   GearCosts.Low,       ImplantType.Bioware),
		ToxinFilters         ("Toxin Filters",           GearCosts.Moderate,  ImplantType.Bioware),
		VacuumSealing        ("Vacuum Sealing",          GearCosts.High,      ImplantType.Bioware),
		GlidingMembrane      ("Gliding Membrane",        GearCosts.Low,       ImplantType.Bioware),
		LateralLine          ("Lateral Line",            GearCosts.Low,       ImplantType.Bioware),
		LowPressureTolerance ("Low Pressure Tolerance",  GearCosts.Low,       ImplantType.Bioware),
		TemperatureToleranceCold("Temperature Tolerance (Improved Cold)", GearCosts.Moderate, ImplantType.Bioware),
		Wings                ("Wings",                   GearCosts.Moderate,  ImplantType.Bioware),
		DiggingClaws         ("Digging Claws",           GearCosts.Low,       ImplantType.Bioware),
		EnhancedRespirationSpecific("Enhanced Respiration (specific)", GearCosts.Low, ImplantType.Bioware),
		HighGAdaptation      ("High-G Adaptation",       GearCosts.Moderate,  ImplantType.Bioware),
		SwimBladder          ("Swim Bladder",            GearCosts.Low,       ImplantType.Bioware),
		HydrostaticPressureAdaptation("Hydrostatic Pressure Adaptation", GearCosts.Expensive, ImplantType.Bioware),
		RadiationTolerance   ("Radiation Tolerance",     GearCosts.Expensive, ImplantType.Bioware),
		TemperatureToleranceCryonic("Temperature Tolerance (Cryonic)", GearCosts.High, ImplantType.Bioware),
		Winterist            ("Winterist",               GearCosts.Low,       ImplantType.Bioware),
		EfficientDigestion   ("Efficient Digestion",     GearCosts.High,      ImplantType.Bioware),
		FatStorage           ("Fat Storage",             GearCosts.High,      ImplantType.Bioware),
		
		AntiGlare           ("Anti-Glare",            GearCosts.Low,       ImplantType.Cyberware),
		ElectricalSense     ("Electrical Sense",      GearCosts.Low,       ImplantType.Cyberware),
		RadiationSense      ("Radiation Sense",       GearCosts.Low,       ImplantType.Cyberware),
		TRayEmitter         ("T-Ray Emitter",         GearCosts.Low,       ImplantType.Cyberware),
		HearingFilter       ("Hearing Filter",        GearCosts.Low,       ImplantType.Cyberware),
		SmellFilter         ("Smell Filter",          GearCosts.Low,       ImplantType.Cyberware),
		VisionFilter        ("Vision Filter",         GearCosts.Low,       ImplantType.Cyberware),
		LifeRecorder        ("Life Recorder",         GearCosts.Low,       ImplantType.Cyberware),
		MemoryLock          ("Memory Lock",           GearCosts.Low,       ImplantType.Cyberware),
		AccessJacks         ("Access Jacks",          GearCosts.Low,       ImplantType.Cyberware),
		DeadSwitch          ("Dead Switch",           GearCosts.Low,       ImplantType.Cyberware),
		EmergencyFarcaster  ("Emergency Farcaster",   GearCosts.Expensive, ImplantType.Cyberware),
		GhostriderModule    ("Ghostrider Module",     GearCosts.Low,       ImplantType.Cyberware),
		MnemonicAugmentation("Mnemonic Augmentation", GearCosts.Low,       ImplantType.Cyberware),
		Multitasking        ("Multitasking",          GearCosts.High,      ImplantType.Cyberware),
		PuppetSock          ("Puppet Sock",           GearCosts.Moderate,  ImplantType.Cyberware),
		Cyberclaws          ("Cyberclaws",            GearCosts.Low,       ImplantType.Cyberware),
		Cyberlimb           ("Cyberlimb",             GearCosts.Moderate,  ImplantType.Cyberware),
		CyberlimbPlus       ("Cyberlimb+",            GearCosts.High,      ImplantType.Cyberware),
		HandLaser           ("Hand Laser",            GearCosts.Moderate,  ImplantType.Cyberware),
		HardenedSkeleton    ("Hardened Skeleton",     GearCosts.High,      ImplantType.Cyberware),
		OxygenReserve       ("Oxygen Reserve",        GearCosts.Low,       ImplantType.Cyberware),
		ReflexBooster       ("Reflex Booster",        GearCosts.Expensive, ImplantType.Cyberware),
		GasJetSystem        ("Gas Jet System",        GearCosts.Moderate,  ImplantType.Cyberware),
		ParallelProcessor   ("Parallel Processor",    GearCosts.High,      ImplantType.Cyberware),
		PlasmaSailImplant   ("Plasma Sail Implant",   GearCosts.Expensive, ImplantType.Cyberware),
		MonitorModule       ("Monitor Module",        GearCosts.Moderate,  ImplantType.Cyberware),
		OptogeneticsModule  ("Optogenetics Module",   GearCosts.Low,       ImplantType.Cyberware),
		PossumCache         ("Possum Cache",          GearCosts.Low,       ImplantType.Cyberware),
		
		ImplantedNanotoxin ("Implanted Nanotoxin",    null,                ImplantType.Nanoware,
				Gear.getGearFromSubtype(new GearTypes[]{GearTypes.Nanotoxins, GearTypes.Nanodrugs})),
		Medichines         ("Medichines",             GearCosts.Low,       ImplantType.Nanoware),
		MentalSpeed        ("Mental Speed",           GearCosts.High,      ImplantType.Nanoware),
		Nanophage          ("Nanophage",              GearCosts.Moderate,  ImplantType.Nanoware),
		Oracle             ("Oracle",                 GearCosts.Moderate,  ImplantType.Nanoware),
		Respirocytes       ("Respirocytes",           GearCosts.Moderate,  ImplantType.Nanoware),
		Skillware          ("Skillware",              GearCosts.High,      ImplantType.Nanoware),
		Skinflex           ("Skinflex",               GearCosts.Moderate,  ImplantType.Nanoware),
		Skinlink           ("Skinlink",               GearCosts.Moderate,  ImplantType.Nanoware),
		WristMountedTools  ("Wrist-Mounted Tools",    GearCosts.Moderate,  ImplantType.Nanoware),
		GaitMasking        ("Gait Masking",           GearCosts.Expensive, ImplantType.Nanoware),
		NanotatIDFlux      ("Nanotat ID Flux",        GearCosts.Expensive, ImplantType.Nanoware),
		SkeletalDisguise   ("Skeletal Disguise",      GearCosts.Expensive, ImplantType.Nanoware),
		PersonalPowerPlant ("Personal Power Plant",   GearCosts.Expensive, ImplantType.Nanoware),
		LongTermLifeSupport("Long Term Life Support", GearCosts.Expensive, ImplantType.Nanoware),
		NeuralEnhancers    ("Neural Enhancers",       GearCosts.Expensive, ImplantType.Nanoware),
		DigestiveSymbiotes ("Digestive Symbiotes",    GearCosts.Low,       ImplantType.Nanoware),
		
		Bodysculpting    ("Bodysculpting",      GearCosts.Low,     ImplantType.Cosmetic),
		Nanotats         ("Nanotats",           GearCosts.Low,     ImplantType.Cosmetic),
		Piercings        ("Piercings",          GearCosts.Trivial, ImplantType.Cosmetic),
		Scarification    ("Scarification",      GearCosts.Trivial, ImplantType.Cosmetic),
		ScentAlteration  ("Scent Alteration",   GearCosts.Low,     ImplantType.Cosmetic),
		Skindyes         ("Skindyes",           GearCosts.Low,     ImplantType.Cosmetic),
		SubdermalImplants("Subdermal Implants", GearCosts.Trivial, ImplantType.Cosmetic),
		
		Headlights        ("Headlights",          GearCosts.Low,       ImplantType.Robotic),
		Hopper            ("Hopper",              GearCosts.Moderate,  ImplantType.Robotic),
		Hovercraft        ("Hovercraft",          GearCosts.Low,       ImplantType.Robotic),
		Ionic             ("Ionic",               GearCosts.High,      ImplantType.Robotic),
		Microlight        ("Microlight",          GearCosts.Low,       ImplantType.Robotic),
		Radar             ("Radar",               GearCosts.Low,       ImplantType.Robotic),
		Roller            ("Roller",              GearCosts.Moderate,  ImplantType.Robotic),
		Rotorcraft        ("Rotorcraft",          GearCosts.Low,       ImplantType.Robotic),
		Snake             ("Snake",               GearCosts.Moderate,  ImplantType.Robotic),
		Sonar             ("Sonar",               GearCosts.Low,       ImplantType.Robotic),
		Submarine         ("Submarine",           GearCosts.Moderate,  ImplantType.Robotic),
		Tracked           ("Tracked",             GearCosts.Low,       ImplantType.Robotic),
		ThrustVector      ("Thrust Vector",       GearCosts.Moderate,  ImplantType.Robotic),
		Walker            ("Walker",              GearCosts.Low,       ImplantType.Robotic),
		Wheeled           ("Wheeled",             GearCosts.Low,       ImplantType.Robotic),
		Winged            ("Winged",              GearCosts.Low,       ImplantType.Robotic),
		SmartWings        ("Smart Wings",         GearCosts.Low,       ImplantType.Robotic),
		ExtraLimbs        ("Extra Limbs",         GearCosts.Low,       ImplantType.Robotic),
		FractalDigits     ("Fractal Digits",      GearCosts.Moderate,  ImplantType.Robotic),
		HiddenCompartment ("Hidden Compartment",  GearCosts.Low,       ImplantType.Robotic),
		MagneticSystem    ("Magnetic System",     GearCosts.Low,       ImplantType.Robotic),
		ModularDesign     ("Modular Design",      GearCosts.High,      ImplantType.Robotic),
		PneumaticLimbs    ("Pneumatic Limbs",     GearCosts.Low,       ImplantType.Robotic),
		TelescopingLimbs  ("Telescoping Limbs",   GearCosts.Low,       ImplantType.Robotic),
		ShapeAdjusting    ("Shape Adjusting",     GearCosts.High,      ImplantType.Robotic),
		StructuralEnhancement    ("Structural Enhancement",     GearCosts.Moderate, ImplantType.Robotic),
		SwarmComposition  ("Swarm Composition",   GearCosts.High,      ImplantType.Robotic),
		SyntheticMask     ("Synthetic Mask",      GearCosts.Moderate,  ImplantType.Robotic),
		WeaponMountFixed         ("Weapon Mount (fixed)",       GearCosts.Low, ImplantType.Robotic),
		WeaponMountSwiveling     ("Weapon Mount (swiveling)",   GearCosts.Low, ImplantType.Robotic),
		WeaponMountConcealed     ("Weapon Mount (concealed)",   GearCosts.Moderate, ImplantType.Robotic),
		WeaponMountArticulated   ("Weapon Mount (articulated)", GearCosts.Moderate, ImplantType.Robotic),
		Vision360Degree   ("360-Degree Vision",   GearCosts.Low,       ImplantType.Robotic),
		ChemicalSniffer   ("Chemical Sniffer",    GearCosts.Moderate,  ImplantType.Robotic),
		Lidar             ("Lidar",               GearCosts.Low,       ImplantType.Robotic),
		NanoscopicVision  ("Nanoscopic Vision",   GearCosts.Moderate,  ImplantType.Robotic),
		RadarSystem       ("Radar System",        GearCosts.Low,       ImplantType.Robotic),
		Invisibility      ("Invisibility",        GearCosts.Expensive, ImplantType.Robotic),
		RadarAbsorbent    ("Radar Absorbent",     GearCosts.Moderate,  ImplantType.Robotic),
		ReducedSignature  ("Reduced Signature",   GearCosts.High,      ImplantType.Robotic),
		RadarInvisibility ("Radar Invisibility",  GearCosts.Expensive, ImplantType.Robotic),
		ExtremeHeatShielding     ("Extreme Heat Shielding",      GearCosts.Expensive, ImplantType.Robotic),
		ExtremePressureAdaptation("Extreme Pressure Adaptation", GearCosts.Expensive, ImplantType.Robotic),
		RadiationShielding("Radiation Shielding", GearCosts.High,      ImplantType.Robotic),
		CryonicProtection ("Cryonic Protection",  GearCosts.Low,       ImplantType.Robotic),
		EgoSharing        ("Ego Sharing",         GearCosts.Expensive, ImplantType.Robotic),
		BrainBox          ("Brain Box",           GearCosts.Moderate,  ImplantType.Robotic),
		HolographicProjector("Holographic Projector", GearCosts.Low,   ImplantType.Robotic),
		InternalRocket    ("Internal Rocket",     GearCosts.Moderate,  ImplantType.Robotic),
		SmartSwarm        ("Smart Swarm",         GearCosts.High,      ImplantType.Robotic),
		SoundSystem       ("Sound System",        GearCosts.Trivial,   ImplantType.Robotic),
		ImplantedArmor    ("Implanted Armor",     null,                ImplantType.Robotic,
				Gear.getGearFromSubtype(new GearTypes[]{GearTypes.Armor})),
		
		Autodelete     ("Autodelete",       GearCosts.Low,       ImplantType.Software),
		Copylock       ("Copylock",         GearCosts.Low,       ImplantType.Software),
		DigitalVeil    ("Digital Veil",     GearCosts.Expensive, ImplantType.Software),
		EmergencyBackup("Emergency Backup", GearCosts.Moderate,  ImplantType.Software),
		HackingAlert   ("Hacking Alert",    GearCosts.Low,       ImplantType.Software),
		Persistence    ("Persistence",      GearCosts.Low,       ImplantType.Software),
		ActiveCountermeasures("Active Countermeasures", GearCosts.High, ImplantType.Software),
		Impersonate    ("Impersonate",      GearCosts.High,      ImplantType.Software),
		IncreasedSpeed ("Increased Speed",  GearCosts.Expensive, ImplantType.Software),
		MentalRepair   ("Mental Repair",    GearCosts.High,      ImplantType.Software),
		MentalStability("Mental Stability", GearCosts.Expensive, ImplantType.Software),
		Panopticon     ("Panopticon",       GearCosts.High,      ImplantType.Software),
		;
		String label;
		GearCosts cost;
		ImplantType augType;
		ArrayList<Gear> subtypeList;
		
		ImplantList(String label, GearCosts cost, ImplantType type){
			this(label, cost, type, null);
		}

		ImplantList(String label, GearCosts cost, ImplantType type,	ArrayList<Gear> subtypeList) {
			this.label = label;
			this.cost = cost;
			this.augType = type;
			this.subtypeList = subtypeList;
		}	
		
		public Boolean equals(String str){
			if (str.equals(this.label))
				return true;
			return false;
		}
	}	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Implant){
			return this.name.equals(((Implant) obj).name);
		}
		if (obj instanceof String){
			return this.name.label.equals((String)obj);
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		String prettyString = name.label ;
		if (this.subtype != null)
			prettyString += " (" + this.subtype + ")";
		//prettyString+= ": "	+ name.augType + ", " + name.cost + " cost";
		return prettyString;
	}

	
	/**
	 * Simple testing function for augmentation stuff
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//ArrayList<Gear> drugsArray = Gear.getGearFromSubtype(
		//		new GearTypes[]{GearTypes.ConventionalDrugs, GearTypes.PsiDrugs});
		
		//System.out.println(drugsArray);
		Implant a = new Implant("Drug Glands", "Klar");
		System.out.println(a);
	}


}
