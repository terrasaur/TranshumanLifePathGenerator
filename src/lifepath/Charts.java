package lifepath;

import java.util.Arrays;
import java.util.List;

import character.Morph;
import dice.Die;


/**
 * This is basically a list of charts that I am going to keep in a different class
 * because everything needs to be hand-coded and I don't want to clutter up 
 * the main LifePath class.
 * 
 * These are supposed to be charts unique to Transhuman. Though it might make sense
 * to put these in their associated Character objects, I want the Character class
 * to be not dependent on anything in the Lifepath in any way.
 * 
 * @author terrasaur
 *
 */
public class Charts {
	
	// Small class to store lists of starting aptitude values.
	protected static class AptitudeTemplate{
		public AptitudeTemplate(String name, Integer[] aptitudes) {
			super();
			this.name = name;
			this.aptitudes = aptitudes;
		}
		String name;
		Integer[] aptitudes;
	}
	
	/**
	 * Gets some starting credits, according to the chart
	 * @param roll 1-10
	 * @param roll2 1-10
	 * @return Starting credits
	 */
	static protected int getStartingCredits(int roll, int roll2){
		if (roll == 1){
			return 0;
		} else if (roll == 2){
			return 5000;
		} else if (roll <= 5){
			return 10000 + (roll2 * 1000);
		} else if (roll <= 8){
			return 20000 + (roll2 * 1000);
		} else if (roll == 9){
			return 40000;
		} else {
			return 50000;
		}
	}
	
	// Returns a string containing a random motivation
	public static String getRandomMotivation() {
		Die d100 = new Die(100);
		String motive;
		if (d100.Roll() <= 50){
			motive = "+";
		} else {
			motive = "-";
		}
		int roll = d100.Roll();
		if (roll == 69 || roll == 70 || roll == 75 || roll == 76){//AGI/Indenture/Infomorph/Pod/Uplift
			int roll2 = d100.Roll();
			if (roll2 <= 20)
				motive += "AGI ";
			else if (roll2 <= 40)
				motive += "Indenture "; 
			else if (roll2 <= 60)
				motive += "Infomorph ";
			else if (roll2 <= 80)
				motive += "Pod ";
			else
				motive += "Uplift ";
		}
		motive += ChartEntry.findResult(Charts.motivations, roll) ;

		return motive;
	}
	
	/**
	 * Returns a random morph, given a roll. You can feed in specific rolls to make 
	 * sure you get or don't get certain types.
	 * Biomorph:    0-50
	 * Uplift:     51-55
	 * Pod:        56-65
	 * Synthmorph: 66-95
	 * Infomorph:  96-100
	 * @param roll 1-100
	 * @return A random morph
	 */
	public static Morph getRandomMorph(Integer roll){
		if (roll <= 50){
			return getBiomorph();
		} else if (roll <= 55) {
			return getUplift();
		} else if (roll <= 65) {
			return getPod();
		} else if (roll <= 95) {
			return getSynthmorph();
		} else {
			return getInfomorph();
		}
	}
	
	/**
	 * Gets one informorph from the list
	 * @return Morph object
	 */
	private static Morph getInfomorph() {
		Die d100 = new Die(100);
		return ChartEntry.findResult(infomorphList, d100.Roll());
	}

	/**
	 * Gets one synth morph randomly from the list
	 * @return Morph object
	 */
	public static Morph getSynthmorph() {
		Die d100 = new Die(100);
		return ChartEntry.findResult(synthmorphList, d100.Roll());
	}

	/**
	 * Gets one pod from the list
	 * @return Morph object
	 */
	public static Morph getPod() {
		Die d100 = new Die(100);
		return ChartEntry.findResult(podList, d100.Roll());
	}

	/**
	 * Gets a random biomorph
	 * @return Morph object
	 */
	private static Morph getBiomorph() {
		Die d100 = new Die(100);
		return ChartEntry.findResult(biomorphList, d100.Roll());
	}

	/**
	 * Gets a random uplift morph type
	 * @return morph of random uplift
	 */
	public static Morph getUplift(){
		Die d100 = new Die(100);
		return ChartEntry.findResult(upliftList, d100.Roll());
	}
	
	/**
	 * Gets a random psi sleight
	 * @param psiRating rating - have to have 2 if you want gamma sleights
	 * @return String of sleight
	 */
	public static String getPsiSleight(int psiRating){
		Die d100 = new Die(100);		
		if (psiRating == 2 && d100.Roll() >= 70){
			return getPsiGammaSleight();
		} else {
			return getPsiChiSleight();
			
		}
	}
	
	/**
	 * Returns one psi Chi sleight
	 * @return String containing sleight
	 */
	public static String getPsiChiSleight() {
		Die d100 = new Die(100);
		return ChartEntry.findResult(psiChiSleights, d100.Roll());
	}
	
	/**
	 * Returns one psi gamma sleight
	 * @return String containing sleight
	 */
	public static String getPsiGammaSleight(){
		Die d100 = new Die(100);
		return ChartEntry.findResult(psiGammaSleights, d100.Roll());
	}
	
	/**
	 * Gets a random mental disorder
	 * @param isAsync if you are an async 
	 * @return string containing disorder label
	 */
	public static String getMentalDisorder(boolean isAsync){
		return getMentalDisorder(isAsync, false, false);
	}
	
	/**
	 * Gets a random mental disorder
	 * @param isAsync if you are an async 
	 * @param isUplift optional, defaults to false, if you are an uplift
	 * @param isNeoOctopus optional, if your uplift type is neo-octopus
	 * @return string containing disorder label
	 */
	public static String getMentalDisorder(boolean isAsync, boolean isUplift, boolean isNeoOctopus){
		Die d100 = new Die(100);
		int roll;
		
		do {
			roll = d100.Roll();
		} while ( (!isUplift && (roll >= 15 && roll <= 17) ) ||     //15, 17 Atavism (uplifts only)
				  (!isNeoOctopus && (roll >= 22 && roll <= 24) ) || //22, 24 Autophagy (neo-octopi only)
				  (!isAsync && ((roll >= 9 && roll <= 14) || (roll >= 39 && roll <= 41) ||
						  (roll >= 98 && roll <= 100)))	); // various async only disorders

		return ChartEntry.findResult(mentalDisorders, roll);
	}

	public static String getRandomRepGroup() {
		Die d10 = new Die(10);
		return ChartEntry.findResult(repList, d10.Roll());
	}
	
	private static ChartEntry<String> newCE_Str (int min, int max, String label){
		return new ChartEntry<String>(min, max, label);
	}
	
	private static ChartEntry<String> newCE_Str (int min, String label){
		return new ChartEntry<String>(min, label);
	}
	
	protected static final List<AptitudeTemplate> aptitudeTemplates = Arrays.asList(
			new AptitudeTemplate("Brawler",       new Integer[]{10, 20, 15, 20, 10, 20, 10}),
			new AptitudeTemplate("Dilettante",    new Integer[]{15, 15, 15, 15, 15, 15, 15}),
			new AptitudeTemplate("Extrovert",     new Integer[]{15, 15, 15, 15, 20, 10, 15}),
			new AptitudeTemplate("Inquisitive",   new Integer[]{20, 10, 20, 10, 20, 10, 15}),
			new AptitudeTemplate("Researcher",    new Integer[]{20, 15, 20, 15, 10, 10, 15}),
			new AptitudeTemplate("Survivor",      new Integer[]{10, 15, 15, 15, 10, 20, 20}),
			new AptitudeTemplate("Techie",        new Integer[]{20, 15, 10, 15, 15, 15, 15}),
			new AptitudeTemplate("Thrill Seeker", new Integer[]{10, 20, 15, 20, 15, 15, 10}));	
	
	protected static final List<ChartEntry<String>> repList = Arrays.asList(
			newCE_Str(1, 2, "@-rep"),	newCE_Str(3, 4, "c-rep"),
			newCE_Str(5, "e-rep"),		newCE_Str(6, "f-rep"),
			newCE_Str(7, "g-rep"),		newCE_Str(8, "i-rep"),
			newCE_Str(9, "r-rep"),		newCE_Str(10, "x-rep"));
	
	protected static final List<ChartEntry<String>> motivations = Arrays.asList(
			newCE_Str(1,  2, "Acceptance/Assimilation"),newCE_Str(3,  4, "Alien Contact"),
			newCE_Str(5,  6, "Anarchism"),				newCE_Str(7,  8, "Artistic Expression"),
			newCE_Str(9,  10, "Authority/Leadership"),	newCE_Str(11, 12, "Biochauvinism"),
			newCE_Str(13, 14, "Bioconservatism"),		newCE_Str(15, 16, "Destroying the TITANs"),
			newCE_Str(17, 18, "DIY"),					newCE_Str(19, 20, "Education"),
			newCE_Str(21, 22, "Exploration"),			newCE_Str(23, 24, "Fame"),
			newCE_Str(25, 26, "Family"),				newCE_Str(27, 28, "Fascism"),
			newCE_Str(29, 30, "Hard Work"),				newCE_Str(31, 32, "Hedonism"),
			newCE_Str(33, 34, "Hypercapitalism"),		newCE_Str(35, 36, "Immortality"),
			newCE_Str(37, 38, "Independence"),			newCE_Str(39, 40, "Individualism"),
			newCE_Str(41, 42, "Law and Order"),			newCE_Str(43, 44, "Libertarianism"),
			newCE_Str(45, 46, "Martian Liberation"),	newCE_Str(47, 48, "Morphological Freedom"),
			newCE_Str(49, 50, "Nano-Ecology"),			newCE_Str(51, 52, "Neurodiversity"),
			newCE_Str(53, 54, "Open Source"),			newCE_Str(55, 56, "Personal Career"),
			newCE_Str(57, 58, "Personal Development"),	newCE_Str(59, 60, "Philanthropy"),
			newCE_Str(61, 62, "Preservationism"),		newCE_Str(63, 64, "Reclaiming Earth"),
			newCE_Str(65, 66, "Religion"),				newCE_Str(67, 68, "Research"),
			newCE_Str(69, 70, "Rights"), 				// (AGI/Indenture/Infomorph/Pod/Uplift) 
			newCE_Str(71, 72, "Science!"),				newCE_Str(73, 74, "Self Reliance"),
			newCE_Str(75, 76, "Slavery"), 				// (AGI/Indenture/Infomorph/Pod/Uplift)
			newCE_Str(77, 78, "Socialism"),				newCE_Str(79, 80, "Sousveillance"),
			newCE_Str(81, 82, "Stability"),				newCE_Str(83, 84, "Survival"),
			newCE_Str(85, 86, "Thrill Seeking"),		newCE_Str(87, 88, "Technoprogressivism"),
			newCE_Str(89, 90, "Transparency"),			newCE_Str(91, 92, "Vengeance"),
			newCE_Str(93, 94, "Venusian Sovereignty"),	newCE_Str(95, 96, "Vice"),
			newCE_Str(97, 98, "Wealth"),				newCE_Str(99, 100, "X-Risks"));
	
	protected static final List<ChartEntry<String>> psiChiSleights = Arrays.asList(
			newCE_Str(1,  5,  "Ambience Sense"),		newCE_Str(6,  10, "Cognitive Boost"),
			newCE_Str(11, 15, "Downtime"),				newCE_Str(16, 19, "Eco-empathy"),
			newCE_Str(20, 24, "Emotion Control"),		newCE_Str(25, 28, "Enhanced Creativity"),
			newCE_Str(29, 32, "Filter"),				newCE_Str(33, 37, "Grok"),
			newCE_Str(38, 42, "High Pain Threshold"),	newCE_Str(43, 46, "Hyperthymesia"),
			newCE_Str(47, 51, "Instinct"),				newCE_Str(52, 56, "Multitasking"),
			newCE_Str(57, 61, "Pattern Recognition"),	newCE_Str(62, 66, "Predictive Boost"),
			newCE_Str(67, 71, "Qualia"),				newCE_Str(72, 75, "Savant Calculation"),
			newCE_Str(76, 80, "Sensory Boost"),			newCE_Str(81, 85, "Superior Kinesics"),
			newCE_Str(86, 90, "Time Sense"),			newCE_Str(91, 95, "Unconscious Lead"),
			newCE_Str(96, 100, "Xeno-empathy"));
	
	protected static final List<ChartEntry<String>> psiGammaSleights = Arrays.asList(
			newCE_Str(1,  4,  "Alienation"),		newCE_Str(5,  8,  "Aphasic Touch"),
			newCE_Str(9,  12, "Charisma"),			newCE_Str(13, 16, "Cloud Memory"),
			newCE_Str(17, 21, "Deep Scan"),			newCE_Str(22, 25, "Drive Emotion"),
			newCE_Str(26, 30, "Ego Sense"),			newCE_Str(31, 34, "Empathic Scan"),
			newCE_Str(35, 38, "Implant Memory"),	newCE_Str(39, 42, "Implant Skill"),
			newCE_Str(43, 46, "Mimic"),				newCE_Str(47, 51, "Mindlink"),
			newCE_Str(52, 56, "Omni Awareness"),	newCE_Str(57, 60, "Penetration"),
			newCE_Str(61, 65, "Psi Shield"),		newCE_Str(66, 70, "Psychic Stab"),
			newCE_Str(71, 74, "Scramble"),			newCE_Str(75, 78, "Sense Block"),
			newCE_Str(79, 82, "Sense Infection"),	newCE_Str(83, 86, "Spam"),
			newCE_Str(87, 90, "Static"),			newCE_Str(91, 95, "Subliminal"),
			newCE_Str(96, 100, "Thought Browse"));
	
	protected static final List<ChartEntry<String>> mentalDisorders = Arrays.asList(
			newCE_Str(1,  8,  "Addiction"),				newCE_Str(9,  11, "Alien Behavior Disorder"), 
			newCE_Str(12, 14, "Alien Sensory Disorder"),newCE_Str(15, 17, "Atavism"),
			newCE_Str(18, 21, "Attention Deficit Hyperactivity Disorder"),
			newCE_Str(22, 24, "Autophagy"), 			newCE_Str(25, 30, "Bipolar Disorder"),
			newCE_Str(31, 34, "Body Dysmorphia"),		newCE_Str(35, 38, "Borderline Personality Disorder"),
			newCE_Str(39, 41, "Cosmic Anxiety Disorder"),newCE_Str(42, 47, "Depression"),
			newCE_Str(48, 51, "Fugue"),					newCE_Str(52, 57, "General Anxiety Disorder"),
			newCE_Str(58, 61, "Hypochondria"),			newCE_Str(62, 67, "Impulse Control Disorder"),
			newCE_Str(68, 71, "Insomnia"),				newCE_Str(72, 74, "Megalomania"),
			newCE_Str(75, 79, "Multiple Personality Disorder"),
			newCE_Str(80, 84, "Obsessive Compulsive Disorder"),
			newCE_Str(85, 88, "Phobia "),				newCE_Str(89, 94, "Post-Traumatic Stress Disorder"),
			newCE_Str(95, 97, "Schizophrenia"),			newCE_Str(98, 100, "Species Dysmorphia"));
	
	private static ChartEntry<Morph> newCE_Morph (int min, int max, Morph label){
		return new ChartEntry<Morph>(min, max, label);
	}
	
	private static ChartEntry<Morph> newCE_Morph (int min, Morph label){
		return new ChartEntry<Morph>(min, label);
	}
	
	protected static final List<ChartEntry<Morph>> biomorphList = Arrays.asList(
			newCE_Morph(1,  3,  new Morph("Flat")),			newCE_Morph(4,  13, new Morph("Splicer")),
			newCE_Morph(14, 21, new Morph("Exalt")),		newCE_Morph(22, 26, new Morph("Menton")),
			newCE_Morph(27, 34, new Morph("Olympian")),		newCE_Morph(35, 39, new Morph("Sylph")),
			newCE_Morph(40, 46, new Morph("Bouncer")),		newCE_Morph(47, 49, new Morph("Fury")),
			newCE_Morph(50, new Morph("Futura")),			newCE_Morph(51, 53, new Morph("Ghost")),
			newCE_Morph(54, 56, new Morph("Hibernoid")),	newCE_Morph(57, 59, new Morph("Neotenic")),
			newCE_Morph(60, 62, new Morph("Remade")),		newCE_Morph(63, 69, new Morph("Ruster")),
			newCE_Morph(70, new Morph("Lunar Flyer")),		newCE_Morph(71, 72, new Morph("Martian Alpiner")),
			newCE_Morph(73, new Morph("Salamander")),		newCE_Morph(74, new Morph("Surya")),
			newCE_Morph(75, new Morph("Venusian Glider")),	newCE_Morph(76, 77, new Morph("Hazer")),
			newCE_Morph(78, new Morph("Hulder")),			newCE_Morph(79, new Morph("Hyperbright")),
			newCE_Morph(80, new Morph("Ring Flyer")),		newCE_Morph(81, new Morph("Selkie")),
			newCE_Morph(82, new Morph("Aquanaut")),			newCE_Morph(83, 85, new Morph("Crasher")),
			newCE_Morph(86, new Morph("Dvergr")),			newCE_Morph(87, new Morph("Ariel")),
			newCE_Morph(88, 89, new Morph("Bruiser")),		newCE_Morph(90, new Morph("Cloud Skate")),
			newCE_Morph(91, new Morph("Faust")),			newCE_Morph(92, new Morph("Freeman")),
			newCE_Morph(93, new Morph("Grey")),				newCE_Morph(94, 95, new Morph("Nomad")),
			newCE_Morph(96, 99, new Morph("Observer")),		newCE_Morph(100, new Morph("Theseus")));
	
	protected static final List<ChartEntry<Morph>> upliftList = Arrays.asList(
			newCE_Morph(1,  30, new Morph("Neo-Avian")),	newCE_Morph(31, 50, new Morph("Neo-Hominid")),
			newCE_Morph(51, 70, new Morph("Octomorph")),	newCE_Morph(71, 75, new Morph("Neanderthal")),
			newCE_Morph(76, new Morph("Neo-Beluga")),		newCE_Morph(77, new Morph("Neo-Dolphin")),
			newCE_Morph(78, 92, new Morph("Neo-Gorilla")),	newCE_Morph(93, new Morph("Neo-Orca")),
			newCE_Morph(94, 98, new Morph("Neo-Pig")),		newCE_Morph(99, new Morph("Neo-Porpoise")),
			newCE_Morph(100, new Morph("Neo-Whale")));
	
	protected static final List<ChartEntry<Morph>> podList = Arrays.asList(
			newCE_Morph(1,  15, new Morph("Pleasure Pod")),	newCE_Morph(16, 30, new Morph("Worker Pod")),
			newCE_Morph(31, 33, new Morph("Novacrab")),		newCE_Morph(34, 35, new Morph("Digger")),
			newCE_Morph(36, 38, new Morph("Ripwing")),		newCE_Morph(39, new Morph("Scurrier")),
			newCE_Morph(40, new Morph("Whiplash")),			newCE_Morph(41, 42, new Morph("Chickcharnie")),
			newCE_Morph(43, 44, new Morph("Hypergibbon")),	newCE_Morph(45, 46, new Morph("Shaper")),
			newCE_Morph(47, 53, new Morph("Ayah")),			newCE_Morph(54, 62, new Morph("Basic Pod")),
			newCE_Morph(63, 67, new Morph("Critter")),		newCE_Morph(68, 70, new Morph("Flying Squid")),
			newCE_Morph(71, 72, new Morph("Jenkin")),		newCE_Morph(73, 75, new Morph("Samsa")),
			newCE_Morph(76, 83, new Morph("Security Pod")),	newCE_Morph(84, 86, new Morph("Space Marine")),
			newCE_Morph(87, 95, new Morph("Specialist Pod")),newCE_Morph(96, 100, new Morph("Vacuum Pod")));
	
	protected static final List<ChartEntry<Morph>> synthmorphList = Arrays.asList(
			newCE_Morph(1,  20, new Morph("Case")),			newCE_Morph(21, 35, new Morph("Synth")),
			newCE_Morph(36, 40, new Morph("Arachnoid")),	newCE_Morph(41, 45, new Morph("Dragonfly")),
			newCE_Morph(46, 49, new Morph("Flexbot")),		newCE_Morph(50, new Morph("Reaper")),
			newCE_Morph(51, 54, new Morph("Slitheroid")),	newCE_Morph(55, 58, new Morph("Swarmanoid")),
			newCE_Morph(59, new Morph("Q Morph")),			newCE_Morph(60, 61, new Morph("Steel Morph")),
			newCE_Morph(62, new Morph("Steel Morph (Masked)")),
			newCE_Morph(63, new Morph("Steel Morph (Liquid Silver)")),
			newCE_Morph(64, new Morph("Sundiver")),			newCE_Morph(65, new Morph("Cetus")),
			newCE_Morph(66, new Morph("Courier")),			newCE_Morph(67, new Morph("Fenrir")),
			newCE_Morph(68, new Morph("Savant")),			newCE_Morph(69, new Morph("Kite")),
			newCE_Morph(70, new Morph("Spare")),			newCE_Morph(71, 72, new Morph("Xu Fu")),
			newCE_Morph(73, 74, new Morph("Gargoyle")),		newCE_Morph(75, new Morph("Skulker")),
			newCE_Morph(76, 77, new Morph("Takko")),		newCE_Morph(78, new Morph("Biocore")),
			newCE_Morph(79, 80, new Morph("Blackbird")),	newCE_Morph(81, new Morph("Cloud Skimmer")),
			newCE_Morph(82, new Morph("Daitya")),			newCE_Morph(83, new Morph("Fighting Kite")),
			newCE_Morph(84, 85, new Morph("Galatea")),		newCE_Morph(86, new Morph("Griefer")),
			newCE_Morph(87, 88, new Morph("Guard")),		newCE_Morph(89, new Morph("Guard Deluxe")),
			newCE_Morph(90, new Morph("Mimic")),			newCE_Morph(91, new Morph("Nautiloid")),
			newCE_Morph(92, 93, new Morph("Opteryx")),		newCE_Morph(94, 95, new Morph("Rover")),
			newCE_Morph(96, new Morph("Space Fighter Rover")),
			newCE_Morph(97, new Morph("Smart Swarm")),		newCE_Morph(98, 99, new Morph("Sphere")),
			newCE_Morph(100, new Morph("Synthtaur")));
	
	protected static final List<ChartEntry<Morph>> infomorphList =	Arrays.asList(
			newCE_Morph(1,  50, new Morph("Infomorph")),		newCE_Morph(51, 57, new Morph("Agent Eidolon")),
			newCE_Morph(58, 70, new Morph("Digimorph")),		newCE_Morph(71, 74, new Morph("Elite Eidolon")),
			newCE_Morph(75, 81, new Morph("Hot Shot Eidolon")),	newCE_Morph(82, 85, new Morph("Sage Eidolon")),
			newCE_Morph(86, 92, new Morph("Scholar Eidolon")),	newCE_Morph(93, new Morph("Slave Eidolon")),
			newCE_Morph(94, 100, new Morph("Wirehead Eidolon")));
	
	


}

