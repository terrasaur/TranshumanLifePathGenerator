package lifepath;

import java.util.Arrays;
import java.util.List;

import character.Morph;
import character.Skill;
import dice.Die;

/**
 * This really doesn't need to be its own class but I kind of like having things be 
 * separated. This could be merged with LifePathCharts. This really just handles 
 * random skills and their various fields. 
 * @author terrasaur
 *
 */
public class RandomSkillCharts {	
	private static Die d100 = new Die(100);
	
	/**
	 * Gets a random skill field, given a skill
	 * @param s - should include type of skill to assign
	 * @return whether or not a field was found
	 */
	public static boolean setRandomSkillField(Skill s) {
		String label = s.getText();
		
		if (label == null){
			System.out.println(s.toString());
			return false;
		} else if (label == "Academics"){
			s.setField(getRandomAcademicsField());
		} else if (label == "Art"){
			s.setField(ChartEntry.findResult(artFields, d100.Roll()));
		} else if (label == "Hardware"){
			s.setField(ChartEntry.findResult(hardwareFields, d100.Roll()));
		} else if (label == "Interest"){
			s.setField(getRandomInterestField());
		} else if (label == "Profession"){
			s.setField(ChartEntry.findResult(professionFields, d100.Roll()));
		} else if (label == "Medicine"){
			s.setField(getRandomMedicineField());
		} else if (label == "Language"){
			s.setField(ChartEntry.findResult(languageFields, d100.Roll()));
		} else if (label == "Network"){
			s.setField(ChartEntry.findResult(networkFields, d100.Roll()));
		} else if (label == "Pilot"){
			s.setField(ChartEntry.findResult(pilotFields, d100.Roll()));
		} else {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gets a random academics field. Uses the table found on p.42 of Transhuman 
	 * @return string containing field
	 */
	public static String getRandomAcademicsField(){
		int roll = d100.Roll();
		String label = ChartEntry.findResult(academicsFields, roll);;
		
		// Various weird cases
		if (roll >= 51 && roll <= 53){ //51–53 (Pre-Fall/Post-Fall) History
			if (d100.Roll() <= 50)
				label = "Pre-Fall " + label;
			else
				label = "Post-Fall " + label;
		} else if ((roll >= 04 && roll <= 07) || (roll >= 17 && roll <= 19) || 
				   (roll >= 45 && roll <= 47) || (roll >= 54 && roll <= 56) ||
				   (roll >= 98 && roll <= 100) ) { // (Xeno) something - many cases	
			if (d100.Roll() <= 50)
				label = "Xeno" + label.toLowerCase();
		} else if ( (roll >= 11 && roll <= 16)  || (roll >= 83 && roll <= 88) ||
				    (roll >= 96 && roll <= 97) ) { // (Astro) something
			if (d100.Roll() <= 50)
				label = "Astro" + label.toLowerCase();
		} 

		return label;
	}

	/**
	 * Gets a random interest. Might roll on place, faction, or network depending 
	 * on specifics of the interest. Tables found on p 42-42 of Transhuman
	 * @return string containing field
	 */
	public static String getRandomInterestField(){
		int roll = d100.Roll();
		String label = ChartEntry.findResult(interestFields, roll);
		
		if(label.contains("/")) { //23, 24 Drugs/Petals/Narcoalgorithms
			String[] opts = label.split("/");
			return opts[Die.getRandIdx(opts.length)];
		}
		if (roll >= 51 && roll <= 52){ //51–52 (Topic) Mesh Forums
			if (d100.Roll() <= 25)
				label = ChartEntry.findResult(factionFields, d100.Roll()) + label;
			else
				label = ChartEntry.findResult(professionFields, d100.Roll()) + label;
			
		}
		if (roll >= 69 && roll <= 70) {//69–70 (Location) Places of Interest
			label = ChartEntry.findResult(placeFields, d100.Roll()) + label;
		}
		return label;
	}
		

	/**
	 * Gets a random medicine field. Uses the table found on p.43 of Transhuman 
	 * @return string containing field
	 */
	public static String getRandomMedicineField(){
		int roll = d100.Roll();
		
		if (roll >= 91 && roll <= 95){	// 91–95 Uplifts (by type)
			Morph upliftType = Charts.getUplift();
			return "Uplift (" + upliftType.getLabel() + ")";			
		}
		return ChartEntry.findResult(medicineFields, roll);
	}

	
	private static ChartEntry<String> newCE_Str (int min, int max, String label){
		return new ChartEntry<String>(min, max, label);
	}
	
	private static ChartEntry<String> newCE_Str (int min, String label){
		return new ChartEntry<String>(min, label);
	}

	protected static final List<ChartEntry<String>> academicsFields =  Arrays.asList(
			newCE_Str(1,  3,  "Anthropology"),		newCE_Str(4,  7,  "Archeology"), 
			newCE_Str(8,  10, "Astronomy"), 		newCE_Str(11, 16, "Biology"), 
			newCE_Str(17, 19, "Botany"), 			newCE_Str(20, 24, "Chemistry"), 
			newCE_Str(25, 26, "Climatology"), 		newCE_Str(27, 29, "Cognitive Science"), 
			newCE_Str(30, 33, "Computer Science"), 	newCE_Str(34, 36, "Cryptography"), 
			newCE_Str(37, 38, "Ecology"), 			newCE_Str(39, 40, "Economics"), 
			newCE_Str(41, 44, "Engineering"), 		newCE_Str(45, 47, "Genetics"), 
			newCE_Str(48, 50, "Geology"), 			newCE_Str(51, 53, "History"), 
			newCE_Str(54, 56, "Linguistics"), 		newCE_Str(57, 59, "Mathematics"),
			newCE_Str(60, 63, "Materials Science"),	newCE_Str(64, 66, "Memetics"), 
			newCE_Str(67, 68, "Microbiology"), 		newCE_Str(69, 71, "Military Science"),
			newCE_Str(72, 75, "Nanotechnology"),	newCE_Str(76, 79, "Neuroscience"), 
			newCE_Str(80, 82, "Philosophy"),		newCE_Str(83, 88, "Physics"), 
			newCE_Str(89, 91, "Political Science"),	newCE_Str(92, 95, "Psychology"), 
			newCE_Str(96, 97, "Sociology"), 		newCE_Str(98, 100, "Zoology"));
	
	protected static final List<ChartEntry<String>> artFields =  Arrays.asList(
			newCE_Str(1,  5,  "Architecture"),			newCE_Str(6,  10, "Bodysculpting"),
			newCE_Str(11, 15, "Cooking"),				newCE_Str(16, 20, "Criticism"),
			newCE_Str(21, 25, "Dance"),					newCE_Str(26, 30, "Digital Art"),
			newCE_Str(31, 35, "Drama"),					newCE_Str(36, 40, "Drawing"),
			newCE_Str(41, 45, "Erotic Entertainment"),	newCE_Str(46, 50, "Fashion"),
			newCE_Str(51, 55, "Game Design"),			newCE_Str(56, 60, "Music"),
			newCE_Str(61, 65, "Painting"),				newCE_Str(66, 70, "Performance"),
			newCE_Str(71, 75, "Poetry"),				newCE_Str(76, 80, "Sculpture"),
			newCE_Str(81, 85, "Simulspace Design"),		newCE_Str(86, 90, "Singing"),
			newCE_Str(91, 95, "Speech"),				newCE_Str(96, 100, "Writing"));
	
	protected static final List<ChartEntry<String>> hardwareFields =   Arrays.asList(
			newCE_Str(1,  13, "Aerospace"),		newCE_Str(14, 21, "Armorer"),
			newCE_Str(22, 41, "Electronics"),	newCE_Str(42, 53, "Groundcraft"),
			newCE_Str(54, 61, "Implants"),		newCE_Str(62, 74, "Industrial"),
			newCE_Str(75, 82, "Nautical"),		newCE_Str(83, 100, "Robotics"));
	
	protected static final List<ChartEntry<String>> placeFields =      Arrays.asList(
			newCE_Str(01, 06, "Earth"),			newCE_Str(07, 13, "Earth Orbital"),
			newCE_Str(14, 17, "Europan"),		newCE_Str(18, 20, "Extrasolar"),
			newCE_Str(21, 22, "Inner Fringe"),	newCE_Str(23, 29, "Jovian"),
			newCE_Str(30, 32, "Jovian Trojans"),newCE_Str(33, 44, "Lunar"),
			newCE_Str(45, 52, "Main Belt"),		newCE_Str(53, 70, "Martian"),
			newCE_Str(71, 75, "Mercurian"),		newCE_Str(76, 77, "Neptunian"),
			newCE_Str(78, "Neptunian Trojans"),	newCE_Str(79, 81, "Outer Fringe"),
			newCE_Str(82, 88, "Saturnian"),		newCE_Str(89, 90, "Solar"),
			newCE_Str(91, 94, "Uranian"),		newCE_Str(95, 100, "Venusian"));
	
	protected static final List<ChartEntry<String>> factionFields =    Arrays.asList(
			newCE_Str(1,  6,  "Anarchist"),			newCE_Str(7,  10, "Argonaut"),
			newCE_Str(11, 15, "Barsoomian"),		newCE_Str(16, 17, "Belter"),
			newCE_Str(18, 19, "Bioconservative"),	newCE_Str(20, 24, "Brinker"),
			newCE_Str(25, 31, "Criminal"),			newCE_Str(32, 33, "Exhuman"),
			newCE_Str(34, 37, "Extropian"),			newCE_Str(38, "Factors"),
			newCE_Str(39, 42, "Firewall"),			newCE_Str(43, 51, "Hypercorp"),
			newCE_Str(52, 55, "Jovian"),			newCE_Str(56, 61, "Lunar"),
			newCE_Str(62, 64, "Mercurial"),			newCE_Str(65, "Nano-Ecologist"),
			newCE_Str(66, 67, "Orbital"),			newCE_Str(68, "Out'ster"),
			newCE_Str(69, "Precautionist"),			newCE_Str(70, "Preservationist"),
			newCE_Str(71, 74, "Reclaimer"),			newCE_Str(75, 77, "Ringer"),
			newCE_Str(78, 80, "Sapient"),			newCE_Str(81, 84, "Scum"),
			newCE_Str(85, "Sifter"),				newCE_Str(86, "Skimmer"),
			newCE_Str(87, 90, "Socialite"),			newCE_Str(91, "Solarian"),
			newCE_Str(92, 96, "Titanian"),			newCE_Str(97, 100, "Ultimate"));
	
	protected static final List<ChartEntry<String>> interestFields =   Arrays.asList(
			newCE_Str(1,  2,  "Alien Relics"),		newCE_Str(3,  4,  "Ancient Sports"),
			newCE_Str(5,  6,  "Art"),				newCE_Str(7, "Wine"),
			newCE_Str(8, "Beers"), 					newCE_Str(9, "Red Markets"),
			newCE_Str(10, "Black Markets"),			newCE_Str(11, 12, "Blogs"),
			newCE_Str(13, 14, "Celebrity Gossip"),	newCE_Str(15, 16, "Conspiracies"),
			newCE_Str(17, "Cultural Trends"),		newCE_Str(18, "Cultural Memes"),
			newCE_Str(19, 20, "Cutting-Edge Technology"), newCE_Str(21, 22, "Drug Dealers"),
			newCE_Str(23, 24, "Drugs/Petals/Narcoalgorithms"),
			newCE_Str(25, "Markets"),				newCE_Str(26, "Economics"),
			newCE_Str(27, 28, "Exoplanets"),		newCE_Str(29, 30, "Food"),
			newCE_Str(31, 32, "Gambling"),			newCE_Str(33, 34, "Gangs"),
			newCE_Str(35, 36, "Gatecrashing"),		newCE_Str(37, 38, "Groups"),
			newCE_Str(39, 40, "Habitats"),			newCE_Str(41, 42, "History"),
			newCE_Str(43, 44, "Law"),				newCE_Str(45, 46, "Literature"),
			newCE_Str(47, 48, "MARGs"),				newCE_Str(49, 50, "Martial Arts"),
			newCE_Str(51, 52, " Mesh Forums"),		newCE_Str(53, 54, "Morphs"),
			newCE_Str(55, 56, "Music"),				newCE_Str(57, 58, "Nanofab Designs"),
			newCE_Str(59, 60, "Nightclubs"),		newCE_Str(61, 62, "Old-Earth Nation-States"),
			newCE_Str(63, 64, "Old-Earth Trivia"),	newCE_Str(65, 66, "Old-Earth Relics"),
			newCE_Str(67, 68, "Pandora Gates"),		newCE_Str(69, 70, " Places of Interest"),
			newCE_Str(71, 72, "Politics"),			newCE_Str(73, 74, "Pornography"),
			newCE_Str(75, "Uplift Rights"),			newCE_Str(76, "AGI Rights"),
			newCE_Str(77, 78, "Robot Models"),		newCE_Str(79, 80, "Rumors"),
			newCE_Str(81, 82, "Science Fiction"),	newCE_Str(83, 84, "Smart Animals"),
			newCE_Str(85, 86, "Spaceship Models"),	newCE_Str(87, 88, "Sports"),
			newCE_Str(89, 90, "TITANs"),			newCE_Str(91, 92, "Trivia"),
			newCE_Str(93, 94, "Vehicle Models"),	newCE_Str(95, 96, "Weapons"),
			newCE_Str(97, 98, "X-Casters"),			newCE_Str(99, 100, "XP"));
	
	protected static final List<ChartEntry<String>> languageFields =   Arrays.asList(
			newCE_Str(1,  6,  "Arabic"),		newCE_Str(7,  9,  "Bengali"),
			newCE_Str(10, 14, "Cantonese/Yue"),	newCE_Str(15, 15, "Dutch"),
			newCE_Str(16, 24, "English"),		newCE_Str(25, 27, "Farsi/Persian"),
			newCE_Str(28, 31, "French"),		newCE_Str(32, 35, "German"),
			newCE_Str(36, 41, "Hindi"),			newCE_Str(42, 42, "Italian"),
			newCE_Str(43, 47, "Japanese"),		newCE_Str(48, 51, "Javanese"),
			newCE_Str(52, 53, "Korean"),		newCE_Str(54, 62, "Mandarin"),
			newCE_Str(63, 63, "Polish"),		newCE_Str(64, 68, "Portuguese"),
			newCE_Str(69, 71, "Punjabi"),		newCE_Str(72, 76, "Russian"),
			newCE_Str(77, 78, "Skandinaviska"),	newCE_Str(79, 84, "Spanish"),
			newCE_Str(85, 85, "Swedish"),		newCE_Str(86, 87, "Tamil"),
			newCE_Str(88, 89, "Turkish"),		newCE_Str(90, 92, "Urdu"),
			newCE_Str(93, 94, "Vietnamese"),	newCE_Str(95, 98, "Wu"),
			newCE_Str(99, 100, "Other"));
	
	protected static final List<ChartEntry<String>> medicineFields =   Arrays.asList(
			newCE_Str(1,  8,  "Biosculpting"),		newCE_Str(9,  13, "Exotic Biomorphs"),
			newCE_Str(14, 21, "Gene Therapy"),		newCE_Str(22, 34, "General Practice"),
			newCE_Str(35, 42, "Implant Surgery"),	newCE_Str(43, 52, "Nanomedicine"),
			newCE_Str(53, 65, "Paramedic"),			newCE_Str(66, 70, "Pods"),
			newCE_Str(71, 75, "Psychiatry"),		newCE_Str(76, 80, "Remote Surgery"),
			newCE_Str(81, 90, "Trauma Surgery"),	newCE_Str(91, 95, "Uplifts"),
			newCE_Str(96, 100, "Veterinary"));
	
	protected static final List<ChartEntry<String>> networkFields =    Arrays.asList(
			newCE_Str(1,  20, "Autonomists"),	newCE_Str(21, 34, "Criminals"),
			newCE_Str(35, 44, "Ecologists"),	newCE_Str(45, 54, "Firewall"),
			newCE_Str(55, 72, "Hypercorps"),	newCE_Str(73, 86, "Media"),
			newCE_Str(87, 100, "Scientists"));
	
	protected static final List<ChartEntry<String>> pilotFields =      Arrays.asList(
			newCE_Str(1,  20, "Aircraft"),			newCE_Str(21, 40, "Anthroform"),
			newCE_Str(41, 45, "Exotic Vehicle"),	newCE_Str(46, 70, "Groundcraft"),
			newCE_Str(71, 95, "Spacecraft"),		newCE_Str(96, 100, "Watercraft"));
	
	protected static final List<ChartEntry<String>> professionFields = Arrays.asList(
			newCE_Str(1,  2,  "Accounting"),		newCE_Str(3,  4,  "Administration"),
			newCE_Str(5,  6,  "Appraisal"),			newCE_Str(7,  8,  "Artisan"),
			newCE_Str(9, "Asteroid Mining"),		newCE_Str(10, "Prospecting"),
			newCE_Str(11, 12, "Banking"),			newCE_Str(13, 14, "Biodesign"),
			newCE_Str(15, 16, "Bodyguarding"),		newCE_Str(17, 18, "Cool Hunting"),
			newCE_Str(19, 20, "Con Schemes"),		newCE_Str(21, 22, "Culture Jamming"),
			newCE_Str(23, 24, "Customs Procedures"),newCE_Str(25, 26, "Darknet Operations"),
			newCE_Str(27, 28, "Distribution"),		newCE_Str(29, 30, "Ego Hunting"),
			newCE_Str(31, 32, "Escorting"),			newCE_Str(33, 34, "Excavation"),
			newCE_Str(35, 36, "First Contact"),		newCE_Str(37, 38, "Forensics"),
			newCE_Str(39, 40, "Gatecrashing"),		newCE_Str(41, 42, "Habitat Operations"),
			newCE_Str(43, 44, "Hacktivism"),		newCE_Str(45, 46, "Info Brokerage"),
			newCE_Str(47, 48, "Instruction"),		newCE_Str(49, 50, "Journalism"),
			newCE_Str(51, 52, "Lab Technician"),	newCE_Str(53, 54, "Law"),
			newCE_Str(55, 56, "Medical Care"),		newCE_Str(57, 58, "Mesh Networking"),
			newCE_Str(59, 60, "Mesh Security"),		newCE_Str(61, 62, "Military Operations"),
			newCE_Str(63, 64, "Minifacturing"),		newCE_Str(65, 66, "Mining"),
			newCE_Str(67, 68, "Morph Brokerage"),	newCE_Str(69, 70, "Police Procedures"),
			newCE_Str(71, 72, "Protection Rackets"),newCE_Str(73, 74, "Psychotherapy"),
			newCE_Str(75, 76, "Scavenging"),		newCE_Str(77, 78, "Salvage Ops"),
			newCE_Str(79, 80, "Security Operations"),newCE_Str(81, 82, "Smuggling Tricks"),
			newCE_Str(83, 84, "Social Engineering"),newCE_Str(85, 86, "Spacecraft Systems"),
			newCE_Str(87, 88, "Spaceship Crew"),	newCE_Str(89, "Spin Control"),
			newCE_Str(90, "Public Relations"),		newCE_Str(91, 92, "Squad Tactics"),
			newCE_Str(93, 94, "Surveying"),			newCE_Str(95, 96, "Terraforming"),
			newCE_Str(97, 98, "Viral Marketing"),	newCE_Str(99, 100, "XP Production"));
	
}
