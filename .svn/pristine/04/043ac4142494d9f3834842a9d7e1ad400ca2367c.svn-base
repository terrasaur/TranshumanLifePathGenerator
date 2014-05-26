package lifepath;

import java.util.ArrayList;

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
public class LifePathCharts {
	
	
	protected static class AptitudeTemplate{
		public AptitudeTemplate(String name, Integer[] aptitudes) {
			super();
			this.name = name;
			this.aptitudes = aptitudes;
		}
		String name;
		Integer[] aptitudes;
	}
	
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
		motive += VariableRollObject.findResult(LifePathCharts.motivations, roll) ;

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
	
	private static Morph getInfomorph() {
		Die d100 = new Die(100);
		return VariableRollObject.findResult(infomorphList, d100.Roll());
	}

	public static Morph getSynthmorph() {
		Die d100 = new Die(100);
		return VariableRollObject.findResult(synthmorphList, d100.Roll());
	}

	public static Morph getPod() {
		Die d100 = new Die(100);
		return VariableRollObject.findResult(podList, d100.Roll());
	}

	private static Morph getBiomorph() {
		Die d100 = new Die(100);
		return VariableRollObject.findResult(biomorphList, d100.Roll());
	}

	/**
	 * Gets a random uplift morph type
	 * @return morph of random uplift
	 */
	public static Morph getUplift(){
		Die d100 = new Die(100);
		return VariableRollObject.findResult(upliftList, d100.Roll());
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
		return VariableRollObject.findResult(psiChiSleights, d100.Roll());
	}
	
	/**
	 * Returns one psi gamma sleight
	 * @return String containing sleight
	 */
	public static String getPsiGammaSleight(){
		Die d100 = new Die(100);
		return VariableRollObject.findResult(psiGammaSleights, d100.Roll());
	}
	
	public static String getMentalDisorder(boolean isAsync){
		return getMentalDisorder(isAsync, false, false);
	}
	
	public static String getMentalDisorder(boolean isAsync, boolean isUplift, boolean isNeoOctopus){
		Die d100 = new Die(100);
		int roll;
		
		do {
			roll = d100.Roll();
		} while ( (!isUplift && (roll >= 15 && roll <= 17) ) ||     //15, 17 Atavism (uplifts only)
				  (!isNeoOctopus && (roll >= 22 && roll <= 24) ) || //22, 24 Autophagy (neo-octopi only)
				  (!isAsync && ((roll >= 9 && roll <= 14) || (roll >= 39 && roll <= 41) ||
						  (roll >= 98 && roll <= 100)))	); // various async only disorders

		return VariableRollObject.findResult(mentalDisorders, roll);
	}

	public static String getRandomRepGroup() {
		Die d10 = new Die(10);
		return VariableRollObject.findResult(repList, d10.Roll());
	}
	
	protected static final AptitudeTemplate[] aptitudeTemplates = new AptitudeTemplate[8];	
	protected static final ArrayList<VariableRollObject<String>> repList    = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> motivations    = 
			new ArrayList<VariableRollObject<String>>();	
	protected static final ArrayList<VariableRollObject<String>> psiChiSleights = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> psiGammaSleights = 
			new ArrayList<VariableRollObject<String>>();
	protected static final ArrayList<VariableRollObject<String>> mentalDisorders = 
			new ArrayList<VariableRollObject<String>>();
	
	protected static final ArrayList<VariableRollObject<Morph>> biomorphList = 
			new ArrayList<VariableRollObject<Morph>>();
	protected static final ArrayList<VariableRollObject<Morph>> upliftList = 
			new ArrayList<VariableRollObject<Morph>>();
	protected static final ArrayList<VariableRollObject<Morph>> podList = 
			new ArrayList<VariableRollObject<Morph>>();
	protected static final ArrayList<VariableRollObject<Morph>> synthmorphList = 
			new ArrayList<VariableRollObject<Morph>>();
	protected static final ArrayList<VariableRollObject<Morph>> infomorphList =
			new ArrayList<VariableRollObject<Morph>>();
	
	
	static 	{
		aptitudeTemplates[0] = new AptitudeTemplate("Brawler",       new Integer[]{10, 20, 15, 20, 10, 20, 10});
		aptitudeTemplates[1] = new AptitudeTemplate("Dilettante",    new Integer[]{15, 15, 15, 15, 15, 15, 15});
		aptitudeTemplates[2] = new AptitudeTemplate("Extrovert",     new Integer[]{15, 15, 15, 15, 20, 10, 15});
		aptitudeTemplates[3] = new AptitudeTemplate("Inquisitive",   new Integer[]{20, 10, 20, 10, 20, 10, 15});
		aptitudeTemplates[4] = new AptitudeTemplate("Researcher",    new Integer[]{20, 15, 20, 15, 10, 10, 15});
		aptitudeTemplates[5] = new AptitudeTemplate("Survivor",      new Integer[]{10, 15, 15, 15, 10, 20, 20});
		aptitudeTemplates[6] = new AptitudeTemplate("Techie",        new Integer[]{20, 15, 10, 15, 15, 15, 15});
		aptitudeTemplates[7] = new AptitudeTemplate("Thrill Seeker", new Integer[]{10, 20, 15, 20, 15, 15, 10});
		
		repList.add(new VariableRollObject<String>(1, 2, "@-rep"));
		repList.add(new VariableRollObject<String>(3, 4, "c-rep"));
		repList.add(new VariableRollObject<String>(5, "e-rep"));
		repList.add(new VariableRollObject<String>(6, "f-rep"));
		repList.add(new VariableRollObject<String>(7, "g-rep"));
		repList.add(new VariableRollObject<String>(8, "i-rep"));
		repList.add(new VariableRollObject<String>(9, "r-rep"));
		repList.add(new VariableRollObject<String>(10, "x-rep"));

	
		motivations.add(new VariableRollObject<String>(1,  2, "Acceptance/Assimilation"));
		motivations.add(new VariableRollObject<String>(3,  4, "Alien Contact"));
		motivations.add(new VariableRollObject<String>(5,  6, "Anarchism"));
		motivations.add(new VariableRollObject<String>(7,  8, "Artistic Expression"));
		motivations.add(new VariableRollObject<String>(9,  10, "Authority/Leadership"));
		motivations.add(new VariableRollObject<String>(11, 12, "Biochauvinism"));
		motivations.add(new VariableRollObject<String>(13, 14, "Bioconservatism"));
		motivations.add(new VariableRollObject<String>(15, 16, "Destroying the TITANs"));
		motivations.add(new VariableRollObject<String>(17, 18, "DIY"));
		motivations.add(new VariableRollObject<String>(19, 20, "Education"));
		motivations.add(new VariableRollObject<String>(21, 22, "Exploration"));
		motivations.add(new VariableRollObject<String>(23, 24, "Fame"));
		motivations.add(new VariableRollObject<String>(25, 26, "Family"));
		motivations.add(new VariableRollObject<String>(27, 28, "Fascism"));
		motivations.add(new VariableRollObject<String>(29, 30, "Hard Work"));
		motivations.add(new VariableRollObject<String>(31, 32, "Hedonism"));
		motivations.add(new VariableRollObject<String>(33, 34, "Hypercapitalism"));
		motivations.add(new VariableRollObject<String>(35, 36, "Immortality"));
		motivations.add(new VariableRollObject<String>(37, 38, "Independence"));
		motivations.add(new VariableRollObject<String>(39, 40, "Individualism"));
		motivations.add(new VariableRollObject<String>(41, 42, "Law and Order"));
		motivations.add(new VariableRollObject<String>(43, 44, "Libertarianism"));
		motivations.add(new VariableRollObject<String>(45, 46, "Martian Liberation"));
		motivations.add(new VariableRollObject<String>(47, 48, "Morphological Freedom"));
		motivations.add(new VariableRollObject<String>(49, 50, "Nano-Ecology"));
		motivations.add(new VariableRollObject<String>(51, 52, "Neurodiversity"));
		motivations.add(new VariableRollObject<String>(53, 54, "Open Source"));
		motivations.add(new VariableRollObject<String>(55, 56, "Personal Career"));
		motivations.add(new VariableRollObject<String>(57, 58, "Personal Development"));
		motivations.add(new VariableRollObject<String>(59, 60, "Philanthropy"));
		motivations.add(new VariableRollObject<String>(61, 62, "Preservationism"));
		motivations.add(new VariableRollObject<String>(63, 64, "Reclaiming Earth"));
		motivations.add(new VariableRollObject<String>(65, 66, "Religion"));
		motivations.add(new VariableRollObject<String>(67, 68, "Research"));
		motivations.add(new VariableRollObject<String>(69, 70, "Rights")); // (AGI/Indenture/Infomorph/Pod/Uplift) 
		motivations.add(new VariableRollObject<String>(71, 72, "Science!"));
		motivations.add(new VariableRollObject<String>(73, 74, "Self Reliance"));
		motivations.add(new VariableRollObject<String>(75, 76, "Slavery")); // (AGI/Indenture/Infomorph/Pod/Uplift)
		motivations.add(new VariableRollObject<String>(77, 78, "Socialism"));
		motivations.add(new VariableRollObject<String>(79, 80, "Sousveillance"));
		motivations.add(new VariableRollObject<String>(81, 82, "Stability"));
		motivations.add(new VariableRollObject<String>(83, 84, "Survival"));
		motivations.add(new VariableRollObject<String>(85, 86, "Thrill Seeking"));
		motivations.add(new VariableRollObject<String>(87, 88, "Technoprogressivism"));
		motivations.add(new VariableRollObject<String>(89, 90, "Transparency"));
		motivations.add(new VariableRollObject<String>(91, 92, "Vengeance"));
		motivations.add(new VariableRollObject<String>(93, 94, "Venusian Sovereignty"));
		motivations.add(new VariableRollObject<String>(95, 96, "Vice"));
		motivations.add(new VariableRollObject<String>(97, 98, "Wealth"));
		motivations.add(new VariableRollObject<String>(99, 100, "X-Risks"));
		
		
		psiChiSleights.add(new VariableRollObject<String>(1,  5,  "Ambience Sense"));
		psiChiSleights.add(new VariableRollObject<String>(6,  10, "Cognitive Boost"));
		psiChiSleights.add(new VariableRollObject<String>(11, 15, "Downtime"));
		psiChiSleights.add(new VariableRollObject<String>(16, 19, "Eco-empathy"));
		psiChiSleights.add(new VariableRollObject<String>(20, 24, "Emotion Control"));
		psiChiSleights.add(new VariableRollObject<String>(25, 28, "Enhanced Creativity"));
		psiChiSleights.add(new VariableRollObject<String>(29, 32, "Filter"));
		psiChiSleights.add(new VariableRollObject<String>(33, 37, "Grok"));
		psiChiSleights.add(new VariableRollObject<String>(38, 42, "High Pain Threshold"));
		psiChiSleights.add(new VariableRollObject<String>(43, 46, "Hyperthymesia"));
		psiChiSleights.add(new VariableRollObject<String>(47, 51, "Instinct"));
		psiChiSleights.add(new VariableRollObject<String>(52, 56, "Multitasking"));
		psiChiSleights.add(new VariableRollObject<String>(57, 61, "Pattern Recognition"));
		psiChiSleights.add(new VariableRollObject<String>(62, 66, "Predictive Boost"));
		psiChiSleights.add(new VariableRollObject<String>(67, 71, "Qualia"));
		psiChiSleights.add(new VariableRollObject<String>(72, 75, "Savant Calculation"));
		psiChiSleights.add(new VariableRollObject<String>(76, 80, "Sensory Boost"));
		psiChiSleights.add(new VariableRollObject<String>(81, 85, "Superior Kinesics"));
		psiChiSleights.add(new VariableRollObject<String>(86, 90, "Time Sense"));
		psiChiSleights.add(new VariableRollObject<String>(91, 95, "Unconscious Lead"));
		psiChiSleights.add(new VariableRollObject<String>(96, 100, "Xeno-empathy"));
		
		psiGammaSleights.add(new VariableRollObject<String>(1,  4,  "Alienation"));
		psiGammaSleights.add(new VariableRollObject<String>(5,  8,  "Aphasic Touch"));
		psiGammaSleights.add(new VariableRollObject<String>(9,  12, "Charisma"));
		psiGammaSleights.add(new VariableRollObject<String>(13, 16, "Cloud Memory"));
		psiGammaSleights.add(new VariableRollObject<String>(17, 21, "Deep Scan"));
		psiGammaSleights.add(new VariableRollObject<String>(22, 25, "Drive Emotion"));
		psiGammaSleights.add(new VariableRollObject<String>(26, 30, "Ego Sense"));
		psiGammaSleights.add(new VariableRollObject<String>(31, 34, "Empathic Scan"));
		psiGammaSleights.add(new VariableRollObject<String>(35, 38, "Implant Memory"));
		psiGammaSleights.add(new VariableRollObject<String>(39, 42, "Implant Skill"));
		psiGammaSleights.add(new VariableRollObject<String>(43, 46, "Mimic"));
		psiGammaSleights.add(new VariableRollObject<String>(47, 51, "Mindlink"));
		psiGammaSleights.add(new VariableRollObject<String>(52, 56, "Omni Awareness"));
		psiGammaSleights.add(new VariableRollObject<String>(57, 60, "Penetration"));
		psiGammaSleights.add(new VariableRollObject<String>(61, 65, "Psi Shield"));
		psiGammaSleights.add(new VariableRollObject<String>(66, 70, "Psychic Stab"));
		psiGammaSleights.add(new VariableRollObject<String>(71, 74, "Scramble"));
		psiGammaSleights.add(new VariableRollObject<String>(75, 78, "Sense Block"));
		psiGammaSleights.add(new VariableRollObject<String>(79, 82, "Sense Infection"));
		psiGammaSleights.add(new VariableRollObject<String>(83, 86, "Spam"));
		psiGammaSleights.add(new VariableRollObject<String>(87, 90, "Static"));
		psiGammaSleights.add(new VariableRollObject<String>(91, 95, "Subliminal"));
		psiGammaSleights.add(new VariableRollObject<String>(96, 100, "Thought Browse"));
		
		mentalDisorders.add(new VariableRollObject<String>(1,  8,  "Addiction"));
		mentalDisorders.add(new VariableRollObject<String>(9,  11, "Alien Behavior Disorder")); 
		mentalDisorders.add(new VariableRollObject<String>(12, 14, "Alien Sensory Disorder")); 
		mentalDisorders.add(new VariableRollObject<String>(15, 17, "Atavism"));
		mentalDisorders.add(new VariableRollObject<String>(18, 21, "Attention Deficit Hyperactivity Disorder"));
		mentalDisorders.add(new VariableRollObject<String>(22, 24, "Autophagy")); 
		mentalDisorders.add(new VariableRollObject<String>(25, 30, "Bipolar Disorder"));
		mentalDisorders.add(new VariableRollObject<String>(31, 34, "Body Dysmorphia"));
		mentalDisorders.add(new VariableRollObject<String>(35, 38, "Borderline Personality Disorder"));
		mentalDisorders.add(new VariableRollObject<String>(39, 41, "Cosmic Anxiety Disorder")); 
		mentalDisorders.add(new VariableRollObject<String>(42, 47, "Depression"));
		mentalDisorders.add(new VariableRollObject<String>(48, 51, "Fugue"));
		mentalDisorders.add(new VariableRollObject<String>(52, 57, "General Anxiety Disorder"));
		mentalDisorders.add(new VariableRollObject<String>(58, 61, "Hypochondria"));
		mentalDisorders.add(new VariableRollObject<String>(62, 67, "Impulse Control Disorder"));
		mentalDisorders.add(new VariableRollObject<String>(68, 71, "Insomnia"));
		mentalDisorders.add(new VariableRollObject<String>(72, 74, "Megalomania"));
		mentalDisorders.add(new VariableRollObject<String>(75, 79, "Multiple Personality Disorder"));
		mentalDisorders.add(new VariableRollObject<String>(80, 84, "Obsessive Compulsive Disorder"));
		mentalDisorders.add(new VariableRollObject<String>(85, 88, "Phobia "));
		mentalDisorders.add(new VariableRollObject<String>(89, 94, "Post-Traumatic Stress Disorder"));
		mentalDisorders.add(new VariableRollObject<String>(95, 97, "Schizophrenia"));
		mentalDisorders.add(new VariableRollObject<String>(98, 100, "Species Dysmorphia"));
		
		biomorphList.add(new VariableRollObject<Morph>(1,  3,  new Morph("Flat")));
		biomorphList.add(new VariableRollObject<Morph>(4,  13, new Morph("Splicer")));
		biomorphList.add(new VariableRollObject<Morph>(14, 21, new Morph("Exalt")));
		biomorphList.add(new VariableRollObject<Morph>(22, 26, new Morph("Menton")));
		biomorphList.add(new VariableRollObject<Morph>(27, 34, new Morph("Olympian")));
		biomorphList.add(new VariableRollObject<Morph>(35, 39, new Morph("Sylph")));
		biomorphList.add(new VariableRollObject<Morph>(40, 46, new Morph("Bouncer")));
		biomorphList.add(new VariableRollObject<Morph>(47, 49, new Morph("Fury")));
		biomorphList.add(new VariableRollObject<Morph>(50, new Morph("Futura")));
		biomorphList.add(new VariableRollObject<Morph>(51, 53, new Morph("Ghost")));
		biomorphList.add(new VariableRollObject<Morph>(54, 56, new Morph("Hibernoid")));
		biomorphList.add(new VariableRollObject<Morph>(57, 59, new Morph("Neotenic")));
		biomorphList.add(new VariableRollObject<Morph>(60, 62, new Morph("Remade")));
		biomorphList.add(new VariableRollObject<Morph>(63, 69, new Morph("Ruster")));
		biomorphList.add(new VariableRollObject<Morph>(70, new Morph("Lunar Flyer")));
		biomorphList.add(new VariableRollObject<Morph>(71, 72, new Morph("Martian Alpiner")));
		biomorphList.add(new VariableRollObject<Morph>(73, new Morph("Salamander")));
		biomorphList.add(new VariableRollObject<Morph>(74, new Morph("Surya")));
		biomorphList.add(new VariableRollObject<Morph>(75, new Morph("Venusian Glider")));
		biomorphList.add(new VariableRollObject<Morph>(76, 77, new Morph("Hazer")));
		biomorphList.add(new VariableRollObject<Morph>(78, new Morph("Hulder")));
		biomorphList.add(new VariableRollObject<Morph>(79, new Morph("Hyperbright")));
		biomorphList.add(new VariableRollObject<Morph>(80, new Morph("Ring Flyer")));
		biomorphList.add(new VariableRollObject<Morph>(81, new Morph("Selkie")));
		biomorphList.add(new VariableRollObject<Morph>(82, new Morph("Aquanaut")));
		biomorphList.add(new VariableRollObject<Morph>(83, 85, new Morph("Crasher")));
		biomorphList.add(new VariableRollObject<Morph>(86, new Morph("Dvergr")));
		biomorphList.add(new VariableRollObject<Morph>(87, new Morph("Ariel")));
		biomorphList.add(new VariableRollObject<Morph>(88, 89, new Morph("Bruiser")));
		biomorphList.add(new VariableRollObject<Morph>(90, new Morph("Cloud Skate")));
		biomorphList.add(new VariableRollObject<Morph>(91, new Morph("Faust")));
		biomorphList.add(new VariableRollObject<Morph>(92, new Morph("Freeman")));
		biomorphList.add(new VariableRollObject<Morph>(93, new Morph("Grey")));
		biomorphList.add(new VariableRollObject<Morph>(94, 95, new Morph("Nomad")));
		biomorphList.add(new VariableRollObject<Morph>(96, 99, new Morph("Observer")));
		biomorphList.add(new VariableRollObject<Morph>(100, new Morph("Theseus")));
		
		
		upliftList.add(new VariableRollObject<Morph>(1,  30, new Morph("Neo-Avian")));
		upliftList.add(new VariableRollObject<Morph>(31, 50, new Morph("Neo-Hominid")));
		upliftList.add(new VariableRollObject<Morph>(51, 70, new Morph("Octomorph")));
		upliftList.add(new VariableRollObject<Morph>(71, 75, new Morph("Neanderthal")));
		upliftList.add(new VariableRollObject<Morph>(76, new Morph("Neo-Beluga")));
		upliftList.add(new VariableRollObject<Morph>(77, new Morph("Neo-Dolphin")));
		upliftList.add(new VariableRollObject<Morph>(78, 92, new Morph("Neo-Gorilla")));
		upliftList.add(new VariableRollObject<Morph>(93, new Morph("Neo-Orca")));
		upliftList.add(new VariableRollObject<Morph>(94, 98, new Morph("Neo-Pig")));
		upliftList.add(new VariableRollObject<Morph>(99, new Morph("Neo-Porpoise")));
		upliftList.add(new VariableRollObject<Morph>(100, new Morph("Neo-Whale")));
		
		podList.add(new VariableRollObject<Morph>(1,  15, new Morph("Pleasure Pod")));
		podList.add(new VariableRollObject<Morph>(16, 30, new Morph("Worker Pod")));
		podList.add(new VariableRollObject<Morph>(31, 33, new Morph("Novacrab")));
		podList.add(new VariableRollObject<Morph>(34, 35, new Morph("Digger")));
		podList.add(new VariableRollObject<Morph>(36, 38, new Morph("Ripwing")));
		podList.add(new VariableRollObject<Morph>(39, new Morph("Scurrier")));
		podList.add(new VariableRollObject<Morph>(40, new Morph("Whiplash")));
		podList.add(new VariableRollObject<Morph>(41, 42, new Morph("Chickcharnie")));
		podList.add(new VariableRollObject<Morph>(43, 44, new Morph("Hypergibbon")));
		podList.add(new VariableRollObject<Morph>(45, 46, new Morph("Shaper")));
		podList.add(new VariableRollObject<Morph>(47, 53, new Morph("Ayah")));
		podList.add(new VariableRollObject<Morph>(54, 62, new Morph("Basic Pod")));
		podList.add(new VariableRollObject<Morph>(63, 67, new Morph("Critter")));
		podList.add(new VariableRollObject<Morph>(68, 70, new Morph("Flying Squid")));
		podList.add(new VariableRollObject<Morph>(71, 72, new Morph("Jenkin")));
		podList.add(new VariableRollObject<Morph>(73, 75, new Morph("Samsa")));
		podList.add(new VariableRollObject<Morph>(76, 83, new Morph("Security Pod")));
		podList.add(new VariableRollObject<Morph>(84, 86, new Morph("Space Marine")));
		podList.add(new VariableRollObject<Morph>(87, 95, new Morph("Specialist Pod")));
		podList.add(new VariableRollObject<Morph>(96, 100, new Morph("Vacuum Pod")));
		
		synthmorphList.add(new VariableRollObject<Morph>(1,  20, new Morph("Case")));
		synthmorphList.add(new VariableRollObject<Morph>(21, 35, new Morph("Synth")));
		synthmorphList.add(new VariableRollObject<Morph>(36, 40, new Morph("Arachnoid")));
		synthmorphList.add(new VariableRollObject<Morph>(41, 45, new Morph("Dragonfly")));
		synthmorphList.add(new VariableRollObject<Morph>(46, 49, new Morph("Flexbot")));
		synthmorphList.add(new VariableRollObject<Morph>(50, new Morph("Reaper")));
		synthmorphList.add(new VariableRollObject<Morph>(51, 54, new Morph("Slitheroid")));
		synthmorphList.add(new VariableRollObject<Morph>(55, 58, new Morph("Swarmanoid")));
		synthmorphList.add(new VariableRollObject<Morph>(59, new Morph("Q Morph")));
		synthmorphList.add(new VariableRollObject<Morph>(60, 61, new Morph("Steel Morph")));
		synthmorphList.add(new VariableRollObject<Morph>(62, new Morph("Steel Morph (Masked)")));
		synthmorphList.add(new VariableRollObject<Morph>(63, new Morph("Steel Morph (Liquid Silver)")));
		synthmorphList.add(new VariableRollObject<Morph>(64, new Morph("Sundiver")));
		synthmorphList.add(new VariableRollObject<Morph>(65, new Morph("Cetus")));
		synthmorphList.add(new VariableRollObject<Morph>(66, new Morph("Courier")));
		synthmorphList.add(new VariableRollObject<Morph>(67, new Morph("Fenrir")));
		synthmorphList.add(new VariableRollObject<Morph>(68, new Morph("Savant")));
		synthmorphList.add(new VariableRollObject<Morph>(69, new Morph("Kite")));
		synthmorphList.add(new VariableRollObject<Morph>(70, new Morph("Spare")));
		synthmorphList.add(new VariableRollObject<Morph>(71, 72, new Morph("Xu Fu")));
		synthmorphList.add(new VariableRollObject<Morph>(73, 74, new Morph("Gargoyle")));
		synthmorphList.add(new VariableRollObject<Morph>(75, new Morph("Skulker")));
		synthmorphList.add(new VariableRollObject<Morph>(76, 77, new Morph("Takko")));
		synthmorphList.add(new VariableRollObject<Morph>(78, new Morph("Biocore")));
		synthmorphList.add(new VariableRollObject<Morph>(79, 80, new Morph("Blackbird")));
		synthmorphList.add(new VariableRollObject<Morph>(81, new Morph("Cloud Skimmer")));
		synthmorphList.add(new VariableRollObject<Morph>(82, new Morph("Daitya")));
		synthmorphList.add(new VariableRollObject<Morph>(83, new Morph("Fighting Kite")));
		synthmorphList.add(new VariableRollObject<Morph>(84, 85, new Morph("Galatea")));
		synthmorphList.add(new VariableRollObject<Morph>(86, new Morph("Griefer")));
		synthmorphList.add(new VariableRollObject<Morph>(87, 88, new Morph("Guard")));
		synthmorphList.add(new VariableRollObject<Morph>(89, new Morph("Guard Deluxe")));
		synthmorphList.add(new VariableRollObject<Morph>(90, new Morph("Mimic")));
		synthmorphList.add(new VariableRollObject<Morph>(91, new Morph("Nautiloid")));
		synthmorphList.add(new VariableRollObject<Morph>(92, 93, new Morph("Opteryx")));
		synthmorphList.add(new VariableRollObject<Morph>(94, 95, new Morph("Rover")));
		synthmorphList.add(new VariableRollObject<Morph>(96, new Morph("Space Fighter Rover")));
		synthmorphList.add(new VariableRollObject<Morph>(97, new Morph("Smart Swarm")));
		synthmorphList.add(new VariableRollObject<Morph>(98, 99, new Morph("Sphere")));
		synthmorphList.add(new VariableRollObject<Morph>(100, new Morph("Synthtaur")));
		
		infomorphList.add(new VariableRollObject<Morph>(1,  50, new Morph("Infomorph")));
		infomorphList.add(new VariableRollObject<Morph>(51, 57, new Morph("Agent Eidolon")));
		infomorphList.add(new VariableRollObject<Morph>(58, 70, new Morph("Digimorph")));
		infomorphList.add(new VariableRollObject<Morph>(71, 74, new Morph("Elite Eidolon")));
		infomorphList.add(new VariableRollObject<Morph>(75, 81, new Morph("Hot Shot Eidolon")));
		infomorphList.add(new VariableRollObject<Morph>(82, 85, new Morph("Sage Eidolon")));
		infomorphList.add(new VariableRollObject<Morph>(86, 92, new Morph("Scholar Eidolon")));
		infomorphList.add(new VariableRollObject<Morph>(93, new Morph("Slave Eidolon")));
		infomorphList.add(new VariableRollObject<Morph>(94, 100, new Morph("Wirehead Eidolon")));

	}




}

