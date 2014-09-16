package lifepath;

import java.util.ArrayList;

import character.EPCharacter;
import character.Morph;
import dice.Die;

/**
 * This is probably one of the most involved classes. It takes the path events
 * from the Transhuman tables and translates them into action items. It will
 * attempt to resolve (apply to lifepath / character) the items whenever possible.
 * 
 * Use get<time>Event() to get one. Use event.resolveEvent(character) to resolve
 * a single action or resolvePathEvents(listOfEvents, character) to resolve a list.
 * 
 * @author terrasaur
 *
 */
public class PathEvent {
	protected String fluff;
	protected String label;
	protected String timeline;
	protected ActionType action;
	private String additionalInfo = null;	
	private int value;
	protected String resleeveString = null;

	
	// Yes, I need all of these constructors. Hush.
	public PathEvent(String f, String l){
		this.fluff = f;
		this.label = l;
		this.action = ActionType.todo;
		this.value = 0;
	}
	public PathEvent(String f, ActionType a, String l){
		this.fluff = f;
		this.action = a;
		this.label = l;
		this.value = 0;
	}
	public PathEvent(String f, ActionType a){
		this.fluff = f;
		this.action = a;
		this.value = 0;
	}
	public PathEvent(String f, ActionType a, int value, String l){
		this.fluff = f;
		this.label = l;
		this.action = a;
		this.value = value;
	}
	public PathEvent(String f, ActionType a, int value){
		this.fluff = f;
		this.action = a;
		this.value = value;
		this.additionalInfo = null;
	}
	public PathEvent(String f, ActionType a, String l, String additonalInfo){
		this.fluff = f;
		this.action = a;
		this.label = l;
		this.additionalInfo = additonalInfo;
	}
	public PathEvent(String f, ActionType a, int value, String l, String additonalInfo){
		this.fluff = f;
		this.action = a;
		this.value = value;
		this.label = l;
		this.additionalInfo = additonalInfo;
	}
	
	// The type of action. This indicates how to resolve it. Things without labels
	// are resolved during resolvePathEvents()
	protected enum ActionType{
		addSkill,
		lowerSkill,
		addTrait,
		modifyAptitude,
		modifyStat,
		newMorph,
		getStoryEvent, // resolved at creation
		modifyCredits,
		reroll, // resolved at creation
		overrideFocus, // resolved during lifepath
		overrideFaction, // resolved during lifepath
		overrideBackground, // resolved during lifepath
		gatecrashing,// resolved at creation
		noAction, // does nothing
		todo 
	}

	
	/**
	 * Resolves all path events. Will go through the list and add skills and traits
	 * directly to the character. In cases of 
	 * @param eventList
	 * @param character
	 * @return list of todo strings (for player choices that can't be resolved)
	 */
	public static ArrayList<String> resolvePathEvents(ArrayList<PathEvent> eventList,
			EPCharacter character) {
		ArrayList<String> todo = new ArrayList<String>();
		
		for (PathEvent p : eventList){
			String tempStr = p.resolveEvent(character);
			if (tempStr != null){
				todo.add(tempStr);
			}
		}

		return todo;
	}
	
	/**
	 * Resolves a single event.
	 * @param character Character to apply the resolution to
	 * @return A todo, if any
	 */
	public String resolveEvent(EPCharacter character) {
		String returnStr = null;
		Die d100 = new Die (100);
		int roll;
		switch(this.action){
		case addSkill:
			if (this.additionalInfo != null)
				character.addSkill(this.label, this.additionalInfo, this.value);
			else
				character.addSkill(this.label, this.value);
			break;
		case addTrait:
			character.addTrait(this.label);
			if (this.additionalInfo != null)
				returnStr = this.additionalInfo;
			break;
		case getStoryEvent:
			if (character.modifyStat("MOX", 1) == -1)
				returnStr = "Error: Add 1 moxie";
			break;
		case lowerSkill:
			break;
		case modifyAptitude:
		case modifyStat:
			if (character.modifyStat(this.label, this.value) == -1)
				returnStr = "Error while adding " + this.label + " + " + this.value;
			if (this.additionalInfo != null)
				returnStr = this.additionalInfo;
			break;
		case modifyCredits:
			if (this.label != null){ // only one case, and the label is xd10
				this.value = this.value * new Die(10).Roll();
			}
			character.modifyCredits(this.value);
			break;
		case newMorph:
			Morph m = null;
			if (this.additionalInfo != null){
				if (this.additionalInfo.contains("Trait: ")){
					character.addTrait(this.additionalInfo.substring("Trait: ".length()));
				} else if (this.additionalInfo == "Gain +1 Moxie"){
					character.modifyStat("MOX", 1);
				} else if (this.additionalInfo.contains("Skill: ")){
					// format is "Skill: dd <String>"
					this.additionalInfo = this.additionalInfo.substring("Trait: ".length());
					int value = Integer.parseInt(this.additionalInfo.substring(0, 2));
					character.addSkill(this.additionalInfo.substring(3), value);
				} else {
					returnStr = this.additionalInfo;
				}
			}
			if (this.label == "Any"){
				roll = d100.Roll();
				m = LifePathCharts.getRandomMorph(roll);
			} else if (this.label == "No pod"){ 
				do {
					roll = d100.Roll();
				} while (roll > 55 && roll <= 65); // Pod: 56-65	
				m = LifePathCharts.getRandomMorph(roll);
			} else if (this.label == "No pod or uplift") {
				do {
					roll = d100.Roll();
				} while (roll > 50 && roll <= 65); // Uplift: 51-55,  Pod: 56-65				
				m = LifePathCharts.getRandomMorph(roll);
			} else if (this.label == "Only pod or uplift"){ // 50/50
				if (d100.Roll() <= 50){
					m = LifePathCharts.getUplift();
				} else {
					m = LifePathCharts.getPod();
				}
			} else if (this.label == "Only Synth"){
				m = LifePathCharts.getSynthmorph();
			} else { // Everything else is just weird, tell player to do it manually
				returnStr = "Roll new morph, " + this.label;
				break;
			}
			character.setMorph(m);
			this.resleeveString = getResleeveString(m);
			break;
		case todo:
			returnStr = this.label;
			break;
		default: // Cases not listed do nothing at this point
			break;
		}			
		
		return returnStr;
	}
	
	/**
	 * To keep things tidy, this gets a proper string of what you are resleeved 
	 * as (to prevent such fine cases as 'an Infomorph infomorph')
	 * @param m your new morph
	 * @return A properly formatted string
	 */
	private static String getResleeveString(Morph m){
		String returnStr = " You are resleeved as a " + m.getLabel();
		if (m.getMorphType() == Morph.MorphType.infomorph){
			returnStr = " You are instantiated as";
			if (m.getLabel() == "Digimorph"){
				returnStr+= " a " + m.getLabel();
			} else
				returnStr+= " an " + m.getLabel();
		} else if (!m.getLabel().contains(" Pod")){ 
			// so we don't get things like 'Worker Pod pod'
			returnStr += " " + m.getMorphType().name();
		}
		return  returnStr + ".";
	}
	
	/**
	 * One of the several functions that get an event. This is Table 4 in the book
	 * @param roll 1-100 roll
	 * @return An event object
	 */
	public static PathEvent getBackgroundEvent(Integer roll){
		PathEvent ev = ChartEntry.findResult(PathEvent.backgroundEvent, roll);
		ev.timeline = "Background";
		if (ev.action == ActionType.getStoryEvent){
			//Die d100 = new Die(100);
			ev.fluff = "(story event) " + getStoryEvent(false).fluff;
					//VariableRollObject.findResult(PathEvent.storyEvent, d100.Roll()).;
		} 
		return ev;
	}
	
	/**
	 * One of the several functions that get an event. This is Table 7 in the book
	 * @param roll 1-100 roll
	 * @return An event object
	 */
	public static ArrayList<PathEvent> getPreFallEvent(Integer roll){
		ArrayList<PathEvent> evList = new ArrayList<PathEvent>(); 
		Die d100 = new Die(100);
		PathEvent ev = ChartEntry.findResult(PathEvent.preFallEvent, roll);
		ev.timeline = "Pre-Fall";
		if (ev.action == ActionType.getStoryEvent){
			ev.fluff = "(story event) " + getStoryEvent(false).fluff;
		} 
		evList.add(ev);
		
		while (ev.action == ActionType.reroll){			
			ev = ChartEntry.findResult(PathEvent.preFallEvent, d100.Roll());
			ev.timeline = "Pre-Fall";
			if (ev.action == ActionType.getStoryEvent){
				ev.fluff = "(story event) " + getStoryEvent(false).fluff;
			}
			evList.add(ev);
		} 
		
		return evList;
	}
	
	/**
	 * One of the several functions that get an event. This is Table 8 in the book
	 * @param roll 1-100 roll
	 * @return An event object
	 */
	public static ArrayList<PathEvent> getFallEvent(Integer roll) {
		ArrayList<PathEvent> evList = new ArrayList<PathEvent>(); 
		PathEvent ev = ChartEntry.findResult(PathEvent.fallEvent, roll);
		ev.timeline = "Fall";
		evList.add(ev);
		if (ev.action == ActionType.getStoryEvent){
			//Die d100 = new Die(100);
			ev = getStoryEvent(false); 
			//new PathEvent(VariableRollObject.findResult(PathEvent.storyEvent, d100.Roll()), ActionType.noAction, "");
			ev.timeline = "Fall Story";
			evList.add(ev);
		} 
		return evList;
	}
	
	/**
	 * One of the several functions that get an event. This is Table 11 in the book
	 * @param roll 1-100 roll
	 * @return An event object
	 */
	public static ArrayList<PathEvent> getPostFallEvent(Integer roll) {
		ArrayList<PathEvent> evList = new ArrayList<PathEvent>(); 
		PathEvent ev = ChartEntry.findResult(PathEvent.postFallEvent, roll);
		ev.timeline = "Post-Fall";
		evList.add(ev);
		if (ev.action == ActionType.gatecrashing){
			Die d100 = new Die(100);
			ev = ChartEntry.findResult(PathEvent.gatecrashingEvent, d100.Roll());
			ev.timeline = "Gatecrashing";
			evList.add(ev);
		} 		
		return evList;
	}
	
	/**
	 * One of the several functions that get an event. This is Table 12 in the book
	 * @param roll 1-100 roll
	 * @return An event object
	 */
	public static PathEvent getFirewallEvent(Integer roll) {
		PathEvent ev = ChartEntry.findResult(PathEvent.firewallEvent, roll);
		ev.timeline = "Firewall";	
		return ev;
	}
	
	/**
	 * One of the several functions that get an event. This is Table 16 in the book
	 * @param isPostFall whether or not you are getting this story event post-Fall
	 * @return An event object
	 */
	public static PathEvent getStoryEvent(boolean isPostFall) {
		Die d100 = new Die(100);
		PathEvent ev;
		do{
			ev = ChartEntry.findResult(PathEvent.storyEvent, d100.Roll());			
		} while (!(isPostFall || (!isPostFall && ev.label.equals("Any"))));
		ev.timeline = "Story";
		return ev;
	}
	
	@Override
	public String toString() {
		String prettyStr = this.timeline + " Event: " + this.fluff ;
		if (this.resleeveString != null)
			prettyStr += this.resleeveString;
		return prettyStr;
	}
	
	public String getFullText() {		
		return this.toString() + "\n" + this.label + " " + this.action.name();
	}


	// All the rest is hard-coded event tables. Good luck!
	static final protected ArrayList<ChartEntry<PathEvent>> backgroundEvent   = new ArrayList<ChartEntry<PathEvent>>(40);
	static final protected ArrayList<ChartEntry<PathEvent>> preFallEvent      = new ArrayList<ChartEntry<PathEvent>>(40);
	static final protected ArrayList<ChartEntry<PathEvent>> storyEvent        = new ArrayList<ChartEntry<PathEvent>>(30);
	static final protected ArrayList<ChartEntry<PathEvent>> fallEvent         = new ArrayList<ChartEntry<PathEvent>>(40);
	static final protected ArrayList<ChartEntry<PathEvent>> postFallEvent     = new ArrayList<ChartEntry<PathEvent>>(80);
	static final protected ArrayList<ChartEntry<PathEvent>> gatecrashingEvent = new ArrayList<ChartEntry<PathEvent>>(20);
	static final protected ArrayList<ChartEntry<PathEvent>> firewallEvent     = new ArrayList<ChartEntry<PathEvent>>(20);
	static 	{
		backgroundEvent.add(new ChartEntry<PathEvent>(1,  20,  
				new PathEvent("Story Event.", ActionType.getStoryEvent, 1, "MOX")));
		backgroundEvent.add(new ChartEntry<PathEvent>(21, 22,
				new PathEvent("Traumatic childhood accident.", ActionType.addTrait, "Mental Disorder (PTSD)")));
		backgroundEvent.add(new ChartEntry<PathEvent>(23, 24, 
				new PathEvent("You are not only good with your hands, you are good with both hands.", ActionType.addTrait, "Ambidexterity")));
		backgroundEvent.add(new ChartEntry<PathEvent>(25, 26, 
				new PathEvent("The people that raise you do not tolerate foolishness.", ActionType.addTrait, "Common Sense")));
		backgroundEvent.add(new ChartEntry<PathEvent>(27, 28, 
				new PathEvent("You are raised in an abusive environment.", ActionType.addTrait, "Pain Tolerance (Level 1)")));
		backgroundEvent.add(new ChartEntry<PathEvent>(29, 30,	
				new PathEvent("You fall in with the wrong crowd.", ActionType.overrideBackground, "")));
		backgroundEvent.add(new ChartEntry<PathEvent>(31, 32, 
				new PathEvent("You are kept at home and not allowed to play sports or with other kids.", ActionType.modifyAptitude, -5, "COO")));
		backgroundEvent.add(new ChartEntry<PathEvent>(33, 34, 
				new PathEvent("You grow up in a maze-like urban area or a difficult rural area.", ActionType.addTrait, "Direction Sense")));
		backgroundEvent.add(new ChartEntry<PathEvent>(35, 36,
				new PathEvent("You are raised in dangerous conditions where you have to adapt or die.", ActionType.addTrait, "Fast Learner")));
		backgroundEvent.add(new ChartEntry<PathEvent>(37, 38, 
				new PathEvent("You skip too much school.", "–20 to one skill")));
		backgroundEvent.add(new ChartEntry<PathEvent>(39, 40, 
				new PathEvent("Growing up isolated from others, you develop introvert tendencies.", ActionType.modifyAptitude, -5, "SAV")));
		backgroundEvent.add(new ChartEntry<PathEvent>(41, 42, 
				new PathEvent("While your friends throw themselves into VR gaming worlds, you simply throw up.", ActionType.addTrait, "VR Vertigo")));
		backgroundEvent.add(new ChartEntry<PathEvent>(43, 44, 
				new PathEvent("Raised in an environment of constant stimulus, you are forced to tune out.", ActionType.addTrait, "Oblivious")));
		backgroundEvent.add(new ChartEntry<PathEvent>(45, 46, 
				new PathEvent("You grow up in a melting-pot, polyglot culture.", ActionType.addTrait, "Hyper Linguist")));
		backgroundEvent.add(new ChartEntry<PathEvent>(47, 48, 
				new PathEvent("You party too hard.", "–20 to one skill")));
		backgroundEvent.add(new ChartEntry<PathEvent>(49, 50, 
				new PathEvent("Your childhood education is poor to non-existent.", ActionType.addTrait, "Illiterate")));
		backgroundEvent.add(new ChartEntry<PathEvent>(51, 52, 
				new PathEvent("A healthy amount of physical activities improves your abilities.", ActionType.modifyAptitude, 5, "COO")));
		backgroundEvent.add(new ChartEntry<PathEvent>(53, 54, 
				new PathEvent("One (or both) of your parents is bilingual.", "Gain +20 to a Language skill of your choice")));
		backgroundEvent.add(new ChartEntry<PathEvent>(55, 56, 
				new PathEvent("A misunderstood situation makes you the laughing stock of your peer group.", ActionType.addTrait, "Socially Graceless")));
		backgroundEvent.add(new ChartEntry<PathEvent>(57, 58, 
				new PathEvent("Shoplifting and stealing are either hobbies or a necessity.", ActionType.addSkill, 10, "Palming")));
		backgroundEvent.add(new ChartEntry<PathEvent>(59, 60, 
				new PathEvent("You experiment with drugs, but it isn't for you.", ActionType.addTrait, "Drug Exception")));
		backgroundEvent.add(new ChartEntry<PathEvent>(61, 62, 
				new PathEvent("A dysfunctional home life keeps you from doing well in school.", ActionType.modifyAptitude, -5, "COG")));
		backgroundEvent.add(new ChartEntry<PathEvent>(63, 64, 
				new PathEvent("Being a bully has its advantages.", ActionType.addSkill, 10, "Intimidation")));
		backgroundEvent.add(new ChartEntry<PathEvent>(65, 66, 
				new PathEvent("Your first experiments with forking prove to you that merging will not be easy.", 
						ActionType.addTrait, "Divergent Personality")));
		backgroundEvent.add(new ChartEntry<PathEvent>(67, 68, 
				new PathEvent("You receive your first cortical stack at an early age—and luckily just in time.", ActionType.newMorph, "Any")));
		backgroundEvent.add(new ChartEntry<PathEvent>(69, 70, 
				new PathEvent("You are raised around animals.", ActionType.addSkill, 10, "Animal Handling")));
		backgroundEvent.add(new ChartEntry<PathEvent>(71, 72, 
				new PathEvent("Your parents raise you with some unusual ideas.", ActionType.addTrait, "Faulty Education")));
		backgroundEvent.add(new ChartEntry<PathEvent>(73, 74, 
				new PathEvent("You cheat your way through school.", "–10 to one skill")));
		backgroundEvent.add(new ChartEntry<PathEvent>(75, 76, 
				new PathEvent("You enjoy urban exploration and getting into off-limits areas.", "Gain +10 to either Climbing or Infiltration skill")));
		backgroundEvent.add(new ChartEntry<PathEvent>(77, 78, 
				new PathEvent("You hone your headshot skills in VR combat simulations.", ActionType.addTrait, "Murder Simulator Addict")));
		backgroundEvent.add(new ChartEntry<PathEvent>(79, 80, 
				new PathEvent("Because you excel in your studies, you are placed in an advanced program.", ActionType.modifyAptitude, 5, "COG")));
		backgroundEvent.add(new ChartEntry<PathEvent>(81, 82, 
				new PathEvent("Your parents are highly private and extremely strict.", ActionType.addTrait, "Poorly Socialized")));
		backgroundEvent.add(new ChartEntry<PathEvent>(83, 84, 
				new PathEvent("You leave home at an early age and forge your own path.", ActionType.modifyStat, 1, "MOX")));
		backgroundEvent.add(new ChartEntry<PathEvent>(85, 86,
				new PathEvent("One of your personality quirks gains you some attention from peers, but then it becomes a "
						+ "permanent and noticeable part of your daily behavior.", ActionType.addTrait, "Identifiable Quirk")));
		backgroundEvent.add(new ChartEntry<PathEvent>(87, 88, 
				new PathEvent("As a free-range kid, you learn how to get around on your own.", ActionType.addSkill, 10, "Navigation")));
		backgroundEvent.add(new ChartEntry<PathEvent>(89, 90, 
				new PathEvent("One of your parental figures abandons you as a child.", ActionType.addTrait, "Trusting Heart")));
		backgroundEvent.add(new ChartEntry<PathEvent>(91, 92, 
				new PathEvent("A series of childhood injuries leaves you struggling to catch up physically.", ActionType.modifyAptitude, -5, "SOM")));
		backgroundEvent.add(new ChartEntry<PathEvent>(93, 94, 
				new PathEvent("You lose all of your close friends in a horribly awkward teen social situation.", ActionType.addTrait, "Not a Team Player")));
		backgroundEvent.add(new ChartEntry<PathEvent>(95, 96, 
				new PathEvent("Tormented by bullies as a kid, you learn to stand up for yourself.", ActionType.addSkill, 10, "Unarmed Combat")));
		backgroundEvent.add(new ChartEntry<PathEvent>(97, 98, 
				new PathEvent("The strictness of your parents leaves you only one choice: to excel at lying.", ActionType.addSkill, 10, "Deception")));
		backgroundEvent.add(new ChartEntry<PathEvent>(99, 100, 
				new PathEvent("Adult generations are never quite as on-top of technological changes, and you use that to your advantage.", 
						ActionType.addSkill, 10, "Infosec")));

		preFallEvent.add(new ChartEntry<PathEvent>(1,  20, 
				new PathEvent("Story Event.", ActionType.getStoryEvent, 1, "MOX")));
		preFallEvent.add(new ChartEntry<PathEvent>(21, 22, 
				new PathEvent("You save an animal from danger.", ActionType.addTrait, "Animal Empathy")));
		preFallEvent.add(new ChartEntry<PathEvent>(23, 24, 
				new PathEvent("You take up a sport.", "+10 to Climbing, Fray, Free Fall, Freerunning, or Swimming")));
		preFallEvent.add(new ChartEntry<PathEvent>(25, 26, 
				new PathEvent("Your inability to improve holds you back from an important "
						+ " promotion/advancement.", ActionType.addTrait, "Slow Learner")));
		preFallEvent.add(new ChartEntry<PathEvent>(27, 28, 
				new PathEvent("You simply are not very comfortable with that whole resleeving thing.", ActionType.addTrait, "Morphing Disorder (Level 1)")));
		preFallEvent.add(new ChartEntry<PathEvent>(29, 30, 
				new PathEvent("You are not a slacker. You take on part-time jobs or additional training.", "+20 to one skill")));
		preFallEvent.add(new ChartEntry<PathEvent>(31, 32, 
				new PathEvent("You travel extensively.", "+10 to two different Language skills")));
		preFallEvent.add(new ChartEntry<PathEvent>(33, 34, 
				new PathEvent("Regular attention to your health and exercise improves your abilities.", ActionType.modifyAptitude, 5, "SOM")));
		preFallEvent.add(new ChartEntry<PathEvent>(35, 36, 
				new PathEvent("You decide you want to experiment.", ActionType.newMorph, "No pod")));
		preFallEvent.add(new ChartEntry<PathEvent>(37, 38, 
				new PathEvent("You are fired and your new career hopes are now dashed.", ActionType.reroll, "Re-roll your Adult Pre-Fall Path")));
		preFallEvent.add(new ChartEntry<PathEvent>(39, 40, 
				new PathEvent("You pick up a new hobby.", "+20 to one Art or Interest skill")));
		preFallEvent.add(new ChartEntry<PathEvent>(41, 42, 
				new PathEvent("An unfortunate habitat failure, traffic accident, or fire ends your life.", ActionType.newMorph,	"No pod or uplift")));
		preFallEvent.add(new ChartEntry<PathEvent>(43, 44, 
				new PathEvent("You travel extensively.", "+20 to Navigation skill")));
		preFallEvent.add(new ChartEntry<PathEvent>(45, 46, 
				new PathEvent("Your work requires you to change your morph.", ActionType.newMorph, "No pod or uplift")));
		preFallEvent.add(new ChartEntry<PathEvent>(47, 48, 
				new PathEvent("Curiosity gets the better of you.", "Lose –5 to one aptitude")));
		preFallEvent.add(new ChartEntry<PathEvent>(49, 50, 
				new PathEvent("You lose a limb in a traumatic incident, but grow it back.",  ActionType.modifyAptitude, 1, "MOX")));
		preFallEvent.add(new ChartEntry<PathEvent>(51, 52, 
				new PathEvent("An experience with still-unrefined psychosurgery leaves you forever altered.", ActionType.addTrait, "Anomalous Mind")));
		preFallEvent.add(new ChartEntry<PathEvent>(53, 54, 
				new PathEvent("Something goes seriously glitchy with your muse, and you are nearly hurt as a result.", ActionType.addTrait, "Phobia Disorder (Muse)")));
		preFallEvent.add(new ChartEntry<PathEvent>(55, 56, 
				new PathEvent("You are a pioneer in the practice of egocasting.", ActionType.newMorph, "Any", "Trait: Ego Plasticity (Level 1)")));
		preFallEvent.add(new ChartEntry<PathEvent>(57, 58, 
				new PathEvent("You save someone from drowning.", ActionType.addSkill, 10, "Swimming")));
		preFallEvent.add(new ChartEntry<PathEvent>(59, 60, 
				new PathEvent("You experiment with some minor self-modification.", ActionType.addTrait, "Modified Behavior (Level 1)")));
		preFallEvent.add(new ChartEntry<PathEvent>(61, 62, 
				new PathEvent("You exhibit a serious lack of willpower in coping with your adult life.", ActionType.addTrait, "Addiction (Major)")));
		preFallEvent.add(new ChartEntry<PathEvent>(63, 64, 
				new PathEvent("You experience a deadly vehicle accident.", ActionType.newMorph, "Any", "Lose 10,000 from your starting credit")));
		preFallEvent.add(new ChartEntry<PathEvent>(65, 66, 
				new PathEvent("A hacker friend shows you a few things, and then you show them a few things.", ActionType.addTrait, "Intuitive Cracker (Level 1)")));
		preFallEvent.add(new ChartEntry<PathEvent>(67, 68, 
				new PathEvent("The poor state of affairs on Earth before the Fall impacts you heavily.", ActionType.addTrait, "Mental Disorder (Depression)")));
		preFallEvent.add(new ChartEntry<PathEvent>(69, 70, 
				new PathEvent("You spend some time in some of the rougher, crisis-impacted areas on Earth before the Fall.", ActionType.addSkill, 10, "Fray")));
		preFallEvent.add(new ChartEntry<PathEvent>(71, 72, 
				new PathEvent("A period of poverty leaves you with the skills to get by.", ActionType.addSkill, 10, "Scrounging")));
		preFallEvent.add(new ChartEntry<PathEvent>(73, 74, 
				new PathEvent("You are implicated in a news-making scandal.", ActionType.modifyStat, -10, "c-rep")));
		preFallEvent.add(new ChartEntry<PathEvent>(75, 76, 
				new PathEvent("You pick up a new hobby.", "Gain +10 to one Art or Interest skill")));
		preFallEvent.add(new ChartEntry<PathEvent>(77, 78, 
				new PathEvent("You are an early adopter of psychosurgery, finding that your mind adapts well to changes.", 
						ActionType.addTrait, "Malleable Mind (Level 1)")));
		preFallEvent.add(new ChartEntry<PathEvent>(79, 80, 
				new PathEvent("The stress of rapid technological change overwhelms you.", ActionType.addTrait, "Mental Disorder (ADHD)")));
		preFallEvent.add(new ChartEntry<PathEvent>(81, 82, 
				new PathEvent("You study or train hard.", "Gain a free specialization in one skill")));
		preFallEvent.add(new ChartEntry<PathEvent>(83, 84, 
				new PathEvent("You take up martial arts training.", ActionType.addSkill, 10, "Unarmed Combat")));
		preFallEvent.add(new ChartEntry<PathEvent>(85, 86, 
				new PathEvent("You choose your own path over what others tell you to do.", 
				"Replace any one skill you have gained so far with any other skill of your choosing at the same rating")));
		preFallEvent.add(new ChartEntry<PathEvent>(87, 88, 
				new PathEvent("You take care of someone in a way that makes them obligated to help you out for a long time to come.", 
						ActionType.addTrait, "Personal Connection")));
		preFallEvent.add(new ChartEntry<PathEvent>(89, 90, 
				new PathEvent("You learn the hard way that ignoring money management lessons was a bad idea.", "Lose 10,000 from your starting credit")));
		preFallEvent.add(new ChartEntry<PathEvent>(91, 92, 
				new PathEvent("You take the fall for a crime you may or may not have been complicit in.",  ActionType.modifyAptitude, 1, "MOX", "Lose –10 c-rep")));
		preFallEvent.add(new ChartEntry<PathEvent>(93, 94, 
				new PathEvent("An accident on a space elevator leaves you fearful of space.", ActionType.addTrait, "Phobia Disorder (Microgravity)")));
		preFallEvent.add(new ChartEntry<PathEvent>(95, 96, 
				new PathEvent("You start up an unusual hobby.", "Gain +20 to one Exotic Melee Weapon or Exotic Ranged Weapon skill")));
		preFallEvent.add(new ChartEntry<PathEvent>(97, 98, 
				new PathEvent("You lose a bet and spend a month sleeved in a pod or uplift morph before it was cool.", ActionType.newMorph, "Only pod or uplift")));
		preFallEvent.add(new ChartEntry<PathEvent>(99, 100, 
				new PathEvent("You make some life decisions that prove prescient after the Fall.", ActionType.modifyCredits, 20000)));

		storyEvent.add(new ChartEntry<PathEvent>(1,  2,  
				new PathEvent("After a long stretch of bad, you hit bottom. No way left to go but up.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(3,  4,  
				new PathEvent("You participate as a test subject in a research project. You suffer no ill effects … that you can tell.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(5,  6,  
				new PathEvent("A prominent journalist befriends you as a source and occasional confidante.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(7,  8,  
				new PathEvent("You hear from an unknown source that Oversight has taken an interest in your affairs.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(9,  10, 
				new PathEvent("You have an unfortunate run-in with Jovian Republic troops, but manage to extricate yourself.", ActionType.noAction, "Post-Fall Only")));
		storyEvent.add(new ChartEntry<PathEvent>(11, 12, 
				new PathEvent("After years, you finally get a chance to inflict revenge on someone. Do you take it or walk away?", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(13, 14, 
				new PathEvent("You witness/survive a major disaster, such as a habitat failure, "
						+ "ship collision, terrorist attack, or a freak but deadly accident.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(15, 16, 
				new PathEvent("Circumstances force you to move from one end of the solar system to the other.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(17, 18, 
				new PathEvent("Your habitat goes through a regime change. Which side are you on?", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(19, 20, 
				new PathEvent("You are falsely accused of a crime but then cleared.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(21, 22, 
				new PathEvent("You develop a long-term rival. The relationship is complex and non-dangerous, "
						+ "but it does occasionally interfere or consume your attention.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(23, 24,
				new PathEvent("You develop a long-term life-partner relationship.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(25, 26, 
				new PathEvent("You suffer through the failure of a major long-term relationship.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(27, 28, 
				new PathEvent("You enter into a convenience-based contract-defined romantic relationship.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(29, 30, 
				new PathEvent("You develop an ongoing polyamorous relationship with a group of friends.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(31, 32, 
				new PathEvent("You are pursued by an irritating but (mostly) harmless suitor/stalker.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(33, 34, 
				new PathEvent("You are recruited to secretly help some faction. Randomly determine that faction from the "
						+ "Factions table.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(35, 36, 
				new PathEvent("You are re-united with a lover/relative/friend thought lost during the Fall.", ActionType.noAction, "Post-Fall Only")));
		storyEvent.add(new ChartEntry<PathEvent>(37, 38, 
				new PathEvent("Political upheaval in your local habitat/polity throws your life into turmoil.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(39, 40, 
				new PathEvent("You are the only survivor of a deadly accident on board a ship or small hab, "
						+ "which raises some suspicion …", ActionType.noAction, "Post-Fall Only")));
		storyEvent.add(new ChartEntry<PathEvent>(41, 42, 
				new PathEvent("Your life has been blissfully serene and untroubled. Your friends may secretly hate you.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(43, 44, 
				new PathEvent("You find out that one or both of your parents weren't really your parents.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(45, 46, 
				new PathEvent("You pursue a period of self-isolation and introspection.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(47, 48, 
				new PathEvent("You catch an authority figure doing something illicit, but you don't have the means to prove it.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(49, 50, 
				new PathEvent("You take a sabbatical with the Solarians, ringers, or other space-faring clade.", ActionType.noAction, "Post-Fall Only")));
		storyEvent.add(new ChartEntry<PathEvent>(51, 52, 
				new PathEvent("You have an affair.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(53, 54, 
				new PathEvent("You are privileged enough to meet a Factor.", ActionType.noAction, "Post-Fall Only")));
		storyEvent.add(new ChartEntry<PathEvent>(55, 56, 
				new PathEvent("You discover an unknown and intriguing or devastating secret about your family's past.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(57, 58, 
				new PathEvent("An unfortunate accident leaves you stuck in a healing vat for a couple of weeks.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(59, 60, 
				new PathEvent("While traveling by spacecraft, a malfunction takes you months off course.", ActionType.noAction, "Post-Fall Only")));
		storyEvent.add(new ChartEntry<PathEvent>(61, 62, 
				new PathEvent("You use someone to get ahead.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(63, 64, 
				new PathEvent("Someone uses you to get ahead.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(65, 66, 
				new PathEvent("You unexpectedly make a close friend with someone from a rival or even hostile faction.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(67, 68, 
				new PathEvent("You have a falling out with a formerly close friend.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(69, 70, 
				new PathEvent("You are forced into a thankless position of heavy responsibility.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(71, 72, 
				new PathEvent("Facing unwanted responsibilities, you pack up and move on.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(73, 74, 
				new PathEvent("You are persecuted for your nature or beliefs.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(75, 76, 
				new PathEvent("You finalize a particularly good research paper, work of art commercial enterprise, "
						+ "or similar achievement.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(77, 78, 
				new PathEvent("Someone close to you opts for a real, final death.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(79, 80, 
				new PathEvent("You discover a new subculture to embed yourself in.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(81, 82, 
				new PathEvent("You befriend a brinker with some interesting ideas and unbelievable stories. Well, almost unbelievable.", ActionType.noAction, "Post-Fall Only")));
		storyEvent.add(new ChartEntry<PathEvent>(83, 84, 
				new PathEvent("You find repeat evidence that someone has you under close surveillance—but why?", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(85, 86, 
				new PathEvent("You are fairly certain that your new friend is secretly a singularity seeker.", ActionType.noAction, "Post-Fall Only")));
		storyEvent.add(new ChartEntry<PathEvent>(87, 88, 
				new PathEvent("You come across an interesting surveillance blind-spot in your local habitat.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(89, 90, 
				new PathEvent("A string of disappearances in your habitat has everyone on edge.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(91, 92, 
				new PathEvent("Someone you know has come across some disturbing information on a powerful entity, "
						+ "and they are considering blowing the whistle.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(93, 94, 
				new PathEvent("You don't have what it takes, and your current job/prospect ends in a washout.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(95, 96, 
				new PathEvent("Your inquisitive nature leads you to discover a secret that could get you into trouble.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(97, 98, 
				new PathEvent("You receive a wake-up call that challenges your current priorities.", ActionType.noAction, "Any")));
		storyEvent.add(new ChartEntry<PathEvent>(99, 100, 
				new PathEvent("Your current job/pursuits take you somewhere dangerous.", ActionType.noAction, "Any")));
	
	
		fallEvent.add(new ChartEntry<PathEvent>(1,  10, 
				new PathEvent("You somehow stay safe and untouched by the chaos and horror", ActionType.getStoryEvent, 1, "MOX")));
		fallEvent.add(new ChartEntry<PathEvent>(11, 12, 
				new PathEvent("You stay to the end, fighting a rear-guard action.", ActionType.newMorph, "Any", "Gain +1 Moxie")));
		fallEvent.add(new ChartEntry<PathEvent>(13, 14, 
				new PathEvent("You are trapped on Earth when the homeworld is interdicted and quarantined.", ActionType.overrideFaction, "Earth Survivor")));
		fallEvent.add(new ChartEntry<PathEvent>(15, 16, 
				new PathEvent("You exemplify yourself in destroying TITAN machines.", ActionType.overrideFocus, "Wrecker")));
		fallEvent.add(new ChartEntry<PathEvent>(17, 18, 
				new PathEvent("Exposure to the Watts-MacLeod exsurgent virus opens your awareness to aspects of the world of which others are blind.",
						ActionType.overrideFocus, "Savant Async")));
		fallEvent.add(new ChartEntry<PathEvent>(19, 20, 
				new PathEvent("Infected with the Watts-MacLeod exsurgent virus, you learn to use your new powers forcefully.", ActionType.overrideFocus, "Controller Async")));
		fallEvent.add(new ChartEntry<PathEvent>(21, 22, 
				new PathEvent("After you are infected, you are trained to use your psi to kill.", ActionType.overrideFocus, "Combat Async")));
		fallEvent.add(new ChartEntry<PathEvent>(23, 24, 
				new PathEvent("Infection permanently changes your perceptions.", ActionType.overrideFocus, "Scanner Async")));
		fallEvent.add(new ChartEntry<PathEvent>(25, 26, 
				new PathEvent("You rack up a major debt to pay a bribe to get yourself off-world.", ActionType.addTrait, "Debt (Level 3)")));
		fallEvent.add(new ChartEntry<PathEvent>(27, 28, 
				new PathEvent("You assume that you died on Earth, but you don't know for sure.", ActionType.newMorph, "Any", "Trait: Edited Memories")));
		fallEvent.add(new ChartEntry<PathEvent>(29, 30, 
				new PathEvent("You know that the TITANs killed or uploaded you, according to reports.", ActionType.newMorph, "Any")));
		fallEvent.add(new ChartEntry<PathEvent>(31, 32, 
				new PathEvent("You fall victim to the TITANs in Earth orbit, on Luna, or on Mars.", ActionType.newMorph, "Any")));
		fallEvent.add(new ChartEntry<PathEvent>(33, 34, 
				new PathEvent("You are slain, not by the TITANs, but by hostile action from a rival government, faction, or hypercorp.", ActionType.newMorph, "Any")));
		fallEvent.add(new ChartEntry<PathEvent>(35, 36, 
				new PathEvent("You hide your body away in cold storage on Earth before farcasting off to safety.", ActionType.newMorph, "Any")));
		fallEvent.add(new ChartEntry<PathEvent>(37, 38, 
				new PathEvent("Your ego escapes the devastation of Earth, only to be locked away in cold storage for years. You are only recently resleeved.",
						ActionType.newMorph, "Any", "Trait: Real World Naiveté")));
		fallEvent.add(new ChartEntry<PathEvent>(39, 40, 
				new PathEvent("Your ego survives the Fall but is locked in simulspace for years before you are resleeved.", 
						ActionType.newMorph, "Any", "Skill: 20 Interfacing")));
		fallEvent.add(new ChartEntry<PathEvent>(41, 42, 
				new PathEvent("After escaping the Fall, you are forced into indentured service before you are resleeved.",
						ActionType.newMorph, "Only Synth", "Gain +20 to one Technical skill")));
		fallEvent.add(new ChartEntry<PathEvent>(43, 44, 
				new PathEvent("You do what you can to help, but you still lose almost everyone in your life.", ActionType.modifyStat, 1, "MOX")));
		fallEvent.add(new ChartEntry<PathEvent>(45, 46, 
				new PathEvent("You heroically sacrifice yourself so that others can escape.", ActionType.newMorph, "Any", "Gain +10 to one rep score")));
		fallEvent.add(new ChartEntry<PathEvent>(47, 48, 
				new PathEvent("You risk your life in a desperate holding action.", ActionType.addTrait, "Brave")));
		fallEvent.add(new ChartEntry<PathEvent>(49, 50, 
				new PathEvent("You are infected … but get better.", ActionType.overrideFocus, "Async")));
		fallEvent.add(new ChartEntry<PathEvent>(51, 52, 
				new PathEvent("You learn the hard way how susceptible you are to exsurgent influence.", ActionType.addTrait, "Psi Vulnerability")));
		fallEvent.add(new ChartEntry<PathEvent>(53, 54, 
				new PathEvent("You encounter an exsurgent and are permanently changed by the experience.", ActionType.overrideFocus, "Async Adept")));
		fallEvent.add(new ChartEntry<PathEvent>(55, 56, 
				new PathEvent("The near extinction of your species hardens your resolve.", ActionType.modifyAptitude, 5, "WIL")));
		fallEvent.add(new ChartEntry<PathEvent>(57, 58, 
				new PathEvent("You hide and survive an encounter with exsurgents where others die.", ActionType.addTrait, "Psi Chameleon")));
		fallEvent.add(new ChartEntry<PathEvent>(59, 60, 
				new PathEvent("You end up in possession of a rare Earth artifact worth 1d10 x 10,000 credits.", "Choose your expensive Earth artifact")));
		fallEvent.add(new ChartEntry<PathEvent>(61, 62, 
				new PathEvent("You make a bad call that gets people killed. Now you question your gut feelings.", ActionType.modifyAptitude, -5, "INT")));
		fallEvent.add(new ChartEntry<PathEvent>(63, 64, 
				new PathEvent("You were horrified at the idea of resleeving, but the alternative seemed worse—or so you thought.", 
						ActionType.newMorph, "Any", "Trait: Morphing Disorder (Level 3)")));
		fallEvent.add(new ChartEntry<PathEvent>(65, 66, 
				new PathEvent("A nanoviral infection leaves you permanently damaged.", ActionType.addTrait, "Neural Damage")));
		fallEvent.add(new ChartEntry<PathEvent>(67, 68, 
				new PathEvent("You have unfortunate memories of some … thing … eating your face off.",
						ActionType.newMorph, "Any", "Trait: Timid")));
		fallEvent.add(new ChartEntry<PathEvent>(69, 70, 
				new PathEvent("You witness unspeakable horrors during the Fall, standing idly by while others die.", ActionType.addTrait, "Combat Paralysis")));
		fallEvent.add(new ChartEntry<PathEvent>(71, 72, 
				new PathEvent("Several near-death experiences hone your reflexes.", ActionType.modifyAptitude, 5, "REF")));
		fallEvent.add(new ChartEntry<PathEvent>(73, 74, 
				new PathEvent("Your relatives die, but you are left as the sole heir to the family's modest wealth that made it off-world.", ActionType.modifyCredits, 25000)));
		fallEvent.add(new ChartEntry<PathEvent>(75, 76, 
				new PathEvent("You will never get the image of headhunter drones at work out of your mind.", ActionType.addTrait, "Mental Disorder (PTSD)")));
		fallEvent.add(new ChartEntry<PathEvent>(77, 78, 
				new PathEvent("You exhibit natural leadership in a time of crisis.", "Gain +10 to Persuasion or Intimidation")));
		fallEvent.add(new ChartEntry<PathEvent>(79, 80, 
				new PathEvent("The only way you can cope with the loss of your former life is through drugs.", ActionType.addTrait, "Drug Fiend")));
		fallEvent.add(new ChartEntry<PathEvent>(81, 82, 
				new PathEvent("You cope with the horrors you experience in the midst of evacuation the only way you could—by postponing the trauma until you are safe.", 
						ActionType.addTrait, "Trauma Tolerance (Level 1)")));
		fallEvent.add(new ChartEntry<PathEvent>(83, 84, 
				new PathEvent("Your willingness to profit from others' misery gains you respect in some circles.", 
						ActionType.modifyAptitude, 10, "g-rep", "Lose 5 rep in one network of your choice")));
		fallEvent.add(new ChartEntry<PathEvent>(85, 86, 
				new PathEvent("You experience things during the Fall that would leave others a shattered mess.", ActionType.addTrait, "Hardening")));
		fallEvent.add(new ChartEntry<PathEvent>(87, 88, 
				new PathEvent("You lose everything—and nearly lose your mind as well. It will never recover its former strength.", ActionType.addTrait, "Frail Sanity")));
		fallEvent.add(new ChartEntry<PathEvent>(89, 90, 
				new PathEvent("Not only do you die during the Fall, your backups are lost as well. You live on as a beta fork of your original self.", 
						ActionType.newMorph, "Any", "Trait: Beta")));
		fallEvent.add(new ChartEntry<PathEvent>(91, 92, 
				new PathEvent("You lose a fortune during the Fall.", ActionType.modifyCredits, -10000)));
		fallEvent.add(new ChartEntry<PathEvent>(93, 94, 
				new PathEvent("You die during the Fall, but that doesn't stop you from going to die again, and then again, and then again some more.", 
						ActionType.newMorph, "Any", "Trait: Phoenix (Level 1)")));
		fallEvent.add(new ChartEntry<PathEvent>(95, 96, 
				new PathEvent("After committing a crime, you are sentenced to indentured service, but the Fall lets you skip out.", 
						ActionType.addTrait, "Deferred Indenture (Level 2)")));
		fallEvent.add(new ChartEntry<PathEvent>(97, 98, 
				new PathEvent("You rack up an impressive kill score fighting TITAN machines.", ActionType.addTrait, "Tacnet Sniper")));
		fallEvent.add(new ChartEntry<PathEvent>(99, 100, 
				new PathEvent("Your willingness to make lives a priority over material things earns you respect.", ActionType.modifyStat, 10, "@-rep")));
		
		
		postFallEvent.add(new ChartEntry<PathEvent>(1, 3, 
				new PathEvent("You are hired by a wealthy private party for some exclusive exoplanet missions.", ActionType.gatecrashing, "Roll on the Gatecrashing Event table")));
		postFallEvent.add(new ChartEntry<PathEvent>(4, 
				new PathEvent("You reinvent yourself.", "Gain +5 to one aptitude and –5 to another aptitude")));
		postFallEvent.add(new ChartEntry<PathEvent>(5, 6,
				new PathEvent("Your employer/collective sends you on a gatecrashing op.", ActionType.gatecrashing, "Roll on the Gatecrashing Event table")));
		postFallEvent.add(new ChartEntry<PathEvent>(7, 
				new PathEvent("You score an achievement that leaves you indelibly marked in your faction's consciousness.", ActionType.addTrait, "Untarnished Reputation")));
		postFallEvent.add(new ChartEntry<PathEvent>(8, 
				new PathEvent("You create a major diplomatic incident.", ActionType.addTrait, "Black Mark (Level 3)")));
		postFallEvent.add(new ChartEntry<PathEvent>(9, 
				new PathEvent("You fall victim to a terrorist attack or factional dispute.", ActionType.newMorph, "Any")));
		postFallEvent.add(new ChartEntry<PathEvent>(10, 
				new PathEvent("The merging of an overdue fork goes poorly, and coincidental inaccessible backups leave you permanently changed.", 
						ActionType.addTrait, "Botched Merge")));
		postFallEvent.add(new ChartEntry<PathEvent>(11, 13, 
				new PathEvent("You are recruited to aid on a scientific mission.", ActionType.gatecrashing, "Roll on the Gatecrashing Event table")));
		postFallEvent.add(new ChartEntry<PathEvent>(14, 
				new PathEvent("In order to keep up with the stress of your responsibilities, you fall into bad habits.", ActionType.addTrait, "Addiction (Moderate)")));
		postFallEvent.add(new ChartEntry<PathEvent>(15, 
				new PathEvent("You do the right thing but piss off someone with power in the process.", 
						"Gain 10 rep in a network of your choice, but also gain the Enemy trait")));
		postFallEvent.add(new ChartEntry<PathEvent>(16, 
				new PathEvent("You use the post-Fall chaos to establish a new identity.", "Gain a Fake Ego ID for free")));
		postFallEvent.add(new ChartEntry<PathEvent>(17, 
				new PathEvent("After an unfortunate incident leads to lack (lost memories due to resleeving from an old backup), you decide security of mind is a worthy investment.", 
						ActionType.addTrait, "Edited Memories", "Gain 1 year of backup insurance")));
		postFallEvent.add(new ChartEntry<PathEvent>(18, 
				new PathEvent("After surviving the Fall and then only barely surviving a post-Fall clash, you decide that some self-defense training may be in order.", 
						"Gain +20 to one Combat skill of your choice")));
		postFallEvent.add(new ChartEntry<PathEvent>(19, 
				new PathEvent("Through a strange set of circumstances, you end up with a rare Delphinium Six petal flower", "Add a Delphinum Six petal flower to your inventory")));
		postFallEvent.add(new ChartEntry<PathEvent>(20, 
				new PathEvent("You commit a crime, get caught, and suffer the punishment.", ActionType.addTrait, "Modified Behavior (Level 3)")));
		postFallEvent.add(new ChartEntry<PathEvent>(21, 
				new PathEvent("You have died enough times that your mind really can't take it any more.", ActionType.addTrait, "Phobia Disorder (Thanatophobia)")));
		postFallEvent.add(new ChartEntry<PathEvent>(22, 24, 
				new PathEvent("You save up and buy yourself a spot on a gatecrashing op.", ActionType.gatecrashing, "Roll on the Gatecrashing Event table")));
		postFallEvent.add(new ChartEntry<PathEvent>(25, 
				new PathEvent("An ownerless bot begins following you and never leaves your side.", "Gain a servitor, saucer, or gnat bot for free")));
		postFallEvent.add(new ChartEntry<PathEvent>(26, 
				new PathEvent("An unknown party leaves you a portable QE comm unit with a high-capacity reservoir, telling you only to expect a call in the future.", 
						"Talk to your GM about your contact")));
		postFallEvent.add(new ChartEntry<PathEvent>(27, 
				new PathEvent("You travel extensively.", ActionType.newMorph, "Any")));
		postFallEvent.add(new ChartEntry<PathEvent>(28, 
				new PathEvent("You've gotten really good at this resleeving thing.", ActionType.newMorph, "Any", "Trait: Adaptability (Level 1)")));
		postFallEvent.add(new ChartEntry<PathEvent>(29, 
				new PathEvent("You narrowly avoid death in a disastrous accident.", ActionType.addTrait, "Danger Sense")));
		postFallEvent.add(new ChartEntry<PathEvent>(30, 
				new PathEvent("You barely survive a murder attempt.", ActionType.modifyAptitude, 5, "INT")));
		postFallEvent.add(new ChartEntry<PathEvent>(31, 
				new PathEvent("A close-call with people out to get you puts you on the defensive.", ActionType.addTrait, "Informational Control")));
		postFallEvent.add(new ChartEntry<PathEvent>(32, 
				new PathEvent("You've grown particular in your taste in morphs.", ActionType.addTrait, "Right At Home")));
		postFallEvent.add(new ChartEntry<PathEvent>(33, 
				new PathEvent("You fall on hard times.", ActionType.modifyCredits, -10000)));
		postFallEvent.add(new ChartEntry<PathEvent>(34, 
				new PathEvent("You get caught in the cross-fire of a regional conflict.", ActionType.newMorph, "Any")));
		postFallEvent.add(new ChartEntry<PathEvent>(35, 
				new PathEvent("Your hectic lifestyle has increased your perceptive skills.", ActionType.addTrait, "Situational Awareness")));
		postFallEvent.add(new ChartEntry<PathEvent>(36, 
				new PathEvent("A long string of personal failures has you questioning your own resolve.", ActionType.modifyAptitude, -5, "WIL")));
		postFallEvent.add(new ChartEntry<PathEvent>(37, 
				new PathEvent("No matter how often your friends warn you, you are promiscuous about your online data.", ActionType.addTrait, "Data Footprint")));
		postFallEvent.add(new ChartEntry<PathEvent>(38, 
				new PathEvent("Nothing ever seems to go your way—your cursed luck is legendary.", ActionType.addTrait, "Bad Luck")));
		postFallEvent.add(new ChartEntry<PathEvent>(39, 
				new PathEvent("You complete a major project of importance to your work/faction.", "Gain +10 to one rep score")));
		postFallEvent.add(new ChartEntry<PathEvent>(40, 
				new PathEvent("A project of importance to your work/faction fails under your direction.", "Lose –10 to one rep score")));
		postFallEvent.add(new ChartEntry<PathEvent>(41, 
				new PathEvent("You take up arms in a regional conflict.", "Gain +10 to one Combat skill")));
		postFallEvent.add(new ChartEntry<PathEvent>(42, 
				new PathEvent("Your work requires you to change your morph.", 
						"Roll randomly on the Choosing a Morph table, but ignore results that don't fit your most recent path")));
		postFallEvent.add(new ChartEntry<PathEvent>(43, 
				new PathEvent("You make an unpopular choice that burns many bridges.", ActionType.addTrait, "Blacklisted")));
		postFallEvent.add(new ChartEntry<PathEvent>(44, 
				new PathEvent("A friend or relative opts for true death, but bequeaths you their estate.", ActionType.modifyCredits, 25000)));
		postFallEvent.add(new ChartEntry<PathEvent>(45, 
				new PathEvent("You are the unfortunate butt of a widespread online meme, but it works to your advantage.", ActionType.addTrait, "You're That Guy!")));
		postFallEvent.add(new ChartEntry<PathEvent>(46, 
				new PathEvent("You are the unfortunate butt of a widespread online meme, and it continues to haunt you.", ActionType.addTrait, "Wait, That Was You?")));
		postFallEvent.add(new ChartEntry<PathEvent>(47, 
				new PathEvent("As transhumanity re-organizes, you find a role in influencing others.", ActionType.modifyAptitude, 5, "SAV")));
		postFallEvent.add(new ChartEntry<PathEvent>(48 , 
				new PathEvent("You have difficulty coming to grips with regular resleeving.", ActionType.addTrait, "Identity Crisis")));
		postFallEvent.add(new ChartEntry<PathEvent>(49 , 
				new PathEvent("One of your resleeves goes particularly poorly, and now it haunts you.", ActionType.addTrait, "Morphing Disorder (Level 2)")));
		postFallEvent.add(new ChartEntry<PathEvent>(50 , 
				new PathEvent("You work hard to establish a solid network.", "+20 to one Networking skill")));
		postFallEvent.add(new ChartEntry<PathEvent>(51 , 
				new PathEvent("You take up a sport.", "+10 to Climbing, Fray, Free Fall, Freerunning, or Swimming")));
		postFallEvent.add(new ChartEntry<PathEvent>(52 , 
				new PathEvent("You decide to experiment with your morph.", ActionType.newMorph, "Any")));
		postFallEvent.add(new ChartEntry<PathEvent>(53 , 
				new PathEvent("Practice makes perfect, and your hard work pays off.", "Increase one aptitude by +5")));
		postFallEvent.add(new ChartEntry<PathEvent>(54 , 
				new PathEvent("You are the victim of an unfortunate crime.", ActionType.modifyCredits, -10000)));
		postFallEvent.add(new ChartEntry<PathEvent>(55 , 
				new PathEvent("Someone steals your identity.", ActionType.addTrait, "Stolen Identity")));
		postFallEvent.add(new ChartEntry<PathEvent>(56 , 
				new PathEvent("You play a prominent role in mediating a factional conflict.", ActionType.addSkill, 20, "Protocol")));
		postFallEvent.add(new ChartEntry<PathEvent>(57 , 
				new PathEvent("You spend a significant portion of your life in one habitat.", ActionType.addTrait, "Home Turf")));
		postFallEvent.add(new ChartEntry<PathEvent>(58 , 
				new PathEvent("You take a bullet for someone you don't even know.", ActionType.newMorph, "Any", "Gain +1 Moxie")));
		postFallEvent.add(new ChartEntry<PathEvent>(59 , 
				new PathEvent("You piss off some powerful people and are made into an example.", ActionType.addTrait, "Black Mark (Level 1)")));
		postFallEvent.add(new ChartEntry<PathEvent>(60 , 
				new PathEvent("You make friends with a group of AGIs online.", ActionType.addTrait, "AGI Affinity")));
		postFallEvent.add(new ChartEntry<PathEvent>(61 , 
				new PathEvent("Due to a sudden financial crisis, you draw an emergency loan with unfavorable terms from an unforgiving loan shark.", 
						ActionType.addTrait, "Debt (Level 1)")));
		postFallEvent.add(new ChartEntry<PathEvent>(62 , 
				new PathEvent("You commit a serious crime, but get away—for now.", ActionType.addTrait, "On the Run")));
		postFallEvent.add(new ChartEntry<PathEvent>(63, 65, 
				new PathEvent(" You win the gatecrashing lottery and a free ticket to Pandora.", ActionType.gatecrashing, "Roll on the Gatecrashing Event table")));
		postFallEvent.add(new ChartEntry<PathEvent>(66 , 
				new PathEvent("You go into business.", ActionType.addTrait, "Entrepreneur (Level 1)")));
		postFallEvent.add(new ChartEntry<PathEvent>(67 , 
				new PathEvent("You become embroiled in a messy professional dispute.", "Lose –5 to one rep score")));
		postFallEvent.add(new ChartEntry<PathEvent>(68 , 
				new PathEvent("You lose a contractual dispute in Extropian space.", ActionType.addTrait, "Deferred Indenture (Level 1)")));
		postFallEvent.add(new ChartEntry<PathEvent>(69 , 
				new PathEvent("You rack up some debts and are forced to downgrade your lifestyle.", 
						"Roll randomly on the Choosing a Morph table, but re-roll any morph that is not at least 10 CP cheaper than your current morph")));
		postFallEvent.add(new ChartEntry<PathEvent>(70 , 
				new PathEvent("You decide you need some help.", ActionType.addTrait, "Established Fork")));
		postFallEvent.add(new ChartEntry<PathEvent>(71 , 
				new PathEvent("You run afoul of a criminal cartel agent. You walk away unscathed, but the matter is far from resolved.",
						ActionType.addTrait, "Enemy")));
		postFallEvent.add(new ChartEntry<PathEvent>(72 , 
				new PathEvent("You get stuck with a boring, repetitive job, but at least you get really good at it.", "Gain one specialization for free")));
		postFallEvent.add(new ChartEntry<PathEvent>(73 , 
				new PathEvent("Doing your part to aid transhumanity's regrowth, you have a kid.", ActionType.addTrait, "Dependent")));
		postFallEvent.add(new ChartEntry<PathEvent>(74 , 
				new PathEvent("You have an unfortunately catastrophic sleeving accident, but the insurance paid well.", 
						"Start play with 10 points of stress and a random derangement, but you may choose your starting morph")));
		postFallEvent.add(new ChartEntry<PathEvent>(75 , 
				new PathEvent("You achieve something that the members of your faction will never forget.", ActionType.addTrait, "Gold Star")));
		postFallEvent.add(new ChartEntry<PathEvent>(76 , 
				new PathEvent("You score an impressive win in a public competition.", "Gain +5 to one rep score")));
		postFallEvent.add(new ChartEntry<PathEvent>(77 , 
				new PathEvent("You split off an alpha fork to handle an important situation, but it decides not to come back.", ActionType.addTrait, "Errant Fork")));
		postFallEvent.add(new ChartEntry<PathEvent>(78 , 
				new PathEvent("You take in an abandoned animal.", "Gain a smart dog, smart monkey, or smart rat for free")));
		postFallEvent.add(new ChartEntry<PathEvent>(79 , 
				new PathEvent("You are the victim of a crime, but the perpetrator is caught. Now you hold their indenture contract.", ActionType.addTrait, "Indenture Holder")));
		postFallEvent.add(new ChartEntry<PathEvent>(80 , 
				new PathEvent("Everything is going great, but you still somehow manage to fuck up something major in your life.", ActionType.modifyStat, 1, "MOX")));
		postFallEvent.add(new ChartEntry<PathEvent>(81 , 
				new PathEvent("A severe personal failure inspires you to make some radical changes.", ActionType.addTrait, "Modified Behavior (Level 2)")));
		postFallEvent.add(new ChartEntry<PathEvent>(82 , 
				new PathEvent("You find your one true love. You don't feel like your full self when you are away from them.", ActionType.addTrait, "Intense Relationship")));
		postFallEvent.add(new ChartEntry<PathEvent>(83 , 
				new PathEvent("You team up with a partner to get the job done.", ActionType.addTrait, "Minion/Partner")));
		postFallEvent.add(new ChartEntry<PathEvent>(84 , 
				new PathEvent("Someone you respect shows their true colors, and they aren't pretty.", ActionType.modifyStat, 1, "MOX")));
		postFallEvent.add(new ChartEntry<PathEvent>(85 , 
				new PathEvent("Your exceptional nature is noticed.", ActionType.addTrait, "Patron")));
		postFallEvent.add(new ChartEntry<PathEvent>(86 , 
				new PathEvent("You get fired/kicked out.", "Lose 10,000 from your starting credit or –10 rep")));
		postFallEvent.add(new ChartEntry<PathEvent>(87 , 
				new PathEvent("An established university offers you a steady position.", ActionType.addTrait, "Tenure")));
		postFallEvent.add(new ChartEntry<PathEvent>(88 , 
				new PathEvent("A fork goes missing. It could be nothing, but it was in possession of some compromising information about yourself.",
						ActionType.addTrait, "Lost Fork")));
		postFallEvent.add(new ChartEntry<PathEvent>(89 , 
				new PathEvent("You fall for the smooth lies of a convincing member of another faction. You realize your error only after the damage is done.", 
						"Lose 10 rep from the rep network appropriate to your faction"))); 
		postFallEvent.add(new ChartEntry<PathEvent>(90, 
				new PathEvent("A trolling hacker ruins your life but leaves you with sporting goodbye offering.", 
						"Lose 10 rep in one network of your choice, but gain a kaos AI for free")));
		postFallEvent.add(new ChartEntry<PathEvent>(91, 
				new PathEvent("You are forced to resleeve in less-than-favorable conditions and end up with a morph with issues.", ActionType.newMorph, 
						"Any", "Trait: Aggressive GRM")));
		postFallEvent.add(new ChartEntry<PathEvent>(92, 
				new PathEvent("You fall in with a new crowd—one that will have your back.", ActionType.addTrait, "Allies")));
		postFallEvent.add(new ChartEntry<PathEvent>(93, 
				new PathEvent("You are part of a group that discovers a derelict ship and makes a great salvaging score.", ActionType.modifyCredits, 20000)));
		postFallEvent.add(new ChartEntry<PathEvent>(94, 
				new PathEvent("You join a cooperative project.", ActionType.addTrait, "Entrepreneur (Level 1)")));
		postFallEvent.add(new ChartEntry<PathEvent>(95, 
				new PathEvent("You fight back against a perceived injustice, but are forced to flee the repercussions.", ActionType.addTrait, "On the Run")));
		postFallEvent.add(new ChartEntry<PathEvent>(96, 
				new PathEvent("You drop everything to re-evaluate your priorities.", "Lose -30 to one skill, but gain 50 CP to spend on whatever you wish")));
		postFallEvent.add(new ChartEntry<PathEvent>(97, 
				new PathEvent("Unknown to you, someone takes an unfriendly interest in your affairs.", ActionType.addTrait, "Subverted Mind")));
		postFallEvent.add(new ChartEntry<PathEvent>(98, 
				new PathEvent("You manage to get yourself killed three times in one week. At least you're getting used to resleeving.", ActionType.newMorph, "Any",
						"Trait: Phoenix (Level 2)")));
		postFallEvent.add(new ChartEntry<PathEvent>(99, 
				new PathEvent("Someone leaves you in charge of their spacecraft.", ActionType.addTrait, "Spacecraft")));
		postFallEvent.add(new ChartEntry<PathEvent>(100, 
				new PathEvent("You are complicit in a faction suffering a major setback.", ActionType.addTrait, "Black Mark (Level 2)")));


		gatecrashingEvent.add(new ChartEntry<PathEvent>(1,  20, 
				new PathEvent("You go on 1d10 gatecrashing missions, with no major consequences. You do see some cool things, though.", ActionType.modifyStat, 1, "MOX")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(21, 48, 
				new PathEvent("You serve on 1d10 missions and pick up some new skills.", 
						"Replace a 1 PP Focus, Faction, or Customization package you acquired in Step 10 with the Explorer package at 1 PP")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(49, 50, 
				new PathEvent("You make a new home on Portal (p. 122, Gatecrashing) or some other exoplanet outpost.", 
						"Work with the GM to determine your exoplanet home")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(51, 52, 
				new PathEvent("You go through a gate but never come through on the other side.", ActionType.newMorph, "Any", "Start game with 10 points of stress")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(53, 54, 
				new PathEvent("You receive some focused training in gate operations.", "Gain the Infosec (Gate Hacking) specialization")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(55, 56, 
				new PathEvent("You acquire an alien pet. You're not allowed to bring it back to the solar system, however.", 
						"Work with the gamemaster to determine the your alien pet's characteristics")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(57, 58, 
				new PathEvent("You find an alien artifact, but they didn't let you keep it.", ActionType.modifyCredits, 20000)));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(59, 60, 
				new PathEvent("You discover a new xenocritter and its unique predatory capabilities.", ActionType.newMorph, "Any")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(61, 62, 
				new PathEvent("Your mission backers upgrade/downgrade your capabilities.", ActionType.newMorph, "Any")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(63, 64, 
				new PathEvent("You participate in some eye-opening research.", ActionType.modifyAptitude, 5, "COG")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(65, 66, 
				new PathEvent("You discover left-behind TITAN machines.", ActionType.newMorph, "Any")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(67, 68, 
				new PathEvent("You go on a mission and never return. Your sponsors refuse to talk about it.", ActionType.newMorph, "Any")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(69, 70, 
				new PathEvent("Your mission scores a major resource find.", ActionType.modifyCredits, 20000)));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(71, 72, 
				new PathEvent("You receive some focused training in gate operations.", "Gain the Interfacing (Gate Operations) specialization")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(73, 74, 
				new PathEvent("You join a semi-successful colonization effort for a year.", "Gain +20 to one Profession skill of your choice")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(75, 76, 
				new PathEvent("You uncover evidence of a previously unknown but long-dead alien race.", "Gain +10 to one rep score of your choice")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(77, 78, 
				new PathEvent("You put in several months of grueling work on a terraforming project.", "Gain +10 to one Academic, Profession, or Technical skill")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(79, 80, 
				new PathEvent("You severely botch a rescue operation. Lives are lost and stacks are not recovered.", "Lose 10 rep from the network of your choice")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(81, 82, 
				new PathEvent("You experience something while going through a gate that makes you never want to go through again.", 
						ActionType.addTrait, "Phobia Disorder (Pandora Gates)")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(83, 84, 
				new PathEvent("You participate in a dangerous rescue operation.", "Gain +10 rep in the network of your choice")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(85, 86, 
				new PathEvent("You receive some focused training in gate operations.", ActionType.addSkill, 20, "Academics", "Gate Operations")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(87, 88, 
				new PathEvent("You come into possession of your very own blue box", ActionType.noAction, "")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(89, 90, 
				new PathEvent("You survive a lengthy gatehopping adventure.", ActionType.modifyStat, 20, "x-rep")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(91, 92, 
				new PathEvent("Your mission is sabotaged by an unknown party.", ActionType.newMorph, "Any")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(93, 94, 
				new PathEvent("You step through a gate and arrive somewhere other than you expected. Your jaunt is adventurous, but you make it back safe.", 
						ActionType.addSkill, 10, "Profession", "Gatecrashing")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(95, 96, 
				new PathEvent("You receive some focused training in gate operations.", "Gain the Programming (Gate Interface) specialization")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(97, 98, 
				new PathEvent("You end up in the middle of an exoplanet property-claim dispute.", ActionType.newMorph, "Any")));
		gatecrashingEvent.add(new ChartEntry<PathEvent>(99, 100, 
				new PathEvent("You participate in a rescue op that cashes in on a sizable recovery bond.", ActionType.modifyCredits, 10000)));
	

		firewallEvent.add(new ChartEntry<PathEvent>(1,  25, 
				new PathEvent("You are recruited by someone you know because of your skill sets.", "Gain +10 to one skill")));
		firewallEvent.add(new ChartEntry<PathEvent>(26, 48 , 
				new PathEvent("You accidentally stumble onto a Firewall op, and luckily for you they decide the best option is to recruit you.", ActionType.modifyStat, 1, "MOX")));
		firewallEvent.add(new ChartEntry<PathEvent>(49, 50 , 
				new PathEvent("You work with/for someone who turns out to be an exhuman supporter. Once you get over the shock, Firewall recruits you.", 
						ActionType.addSkill, 10, "Interest", "Exhumans")));
		firewallEvent.add(new ChartEntry<PathEvent>(51, 52 , 
				new PathEvent("Firewall recruits you as an informant, to help keep tabs on someone or something they are worried about.", ActionType.addSkill, 20, "Infosec")));
		firewallEvent.add(new ChartEntry<PathEvent>(53, 54 , 
				new PathEvent("Your ego was jailed/lost on Earth/forknapped/in cold storage, but Firewall broke you out in return for your aid.", ActionType.modifyStat, 1, "MOX")));
		firewallEvent.add(new ChartEntry<PathEvent>(55, 56 , 
				new PathEvent("You spot someone acting suspiciously and report them. They turn out to be a Firewall async. You are repaid with recruitment and training.", 
						ActionType.addTrait, "Async Familiarity")));
		firewallEvent.add(new ChartEntry<PathEvent>(57, 58 , 
				new PathEvent("You are infected and secretly operate as a sleeper exsurgent for months or even years. Firewall restores you from an old backup.", 
						ActionType.newMorph, "Any", "Trait: Edited Memories")));
		firewallEvent.add(new ChartEntry<PathEvent>(59, 60 , 
				new PathEvent("You have an unexpected close encounter with the TQZ on Mars, the New Mumbai Containment Zone of Luna, or Iapetus.", ActionType.newMorph, "Any")));
		firewallEvent.add(new ChartEntry<PathEvent>(61, 62 , 
				new PathEvent("An exhuman raid leaves you and others dead; Firewall helps sort out the mess.", ActionType.newMorph, "Any")));
		firewallEvent.add(new ChartEntry<PathEvent>(63, 64 , 
				new PathEvent("You are one of the few survivors of an exsurgent outbreak on your habitat.", ActionType.addTrait, "Psi Defense")));
		firewallEvent.add(new ChartEntry<PathEvent>(65, 66 , 
				new PathEvent("You find a relic. Bad things happen. Firewall cleans up the mess.", "–10 to one rep score.")));
		firewallEvent.add(new ChartEntry<PathEvent>(67, 68 , 
				new PathEvent("You were a member/supporter of one of the groups that evolved into Firewall from before the Fall. You took some time off, but now you’re back.", 
						ActionType.modifyStat, 10, "i-rep")));
		firewallEvent.add(new ChartEntry<PathEvent>(69, 70 , 
				new PathEvent("You single-handedly foil an impending outbreak, but the local authorities blame you for the carnage. Firewall helps you escape.", ActionType.addTrait, "On the Run")));
		firewallEvent.add(new ChartEntry<PathEvent>(71, 72 , 
				new PathEvent("A previously dormant TITAN nanoplague rampages through your habitat.", ActionType.newMorph, "Any")));
		firewallEvent.add(new ChartEntry<PathEvent>(73, 74 , 
				new PathEvent("Your ship stops to investigate/help a derelict ship and is never heard from again.", ActionType.newMorph, "Any")));
		firewallEvent.add(new ChartEntry<PathEvent>(75, 76 , 
				new PathEvent("You discover a lost cache on an isolated asteroid. Firewall actually lets you keep some of the find.", ActionType.modifyCredits, 5000, "x 1d10")));
		firewallEvent.add(new ChartEntry<PathEvent>(77, 78 , 
				new PathEvent("You step into an unknown fray and are lucky enough to pick the right side.", ActionType.modifyStat, 10, "i-rep")));
		firewallEvent.add(new ChartEntry<PathEvent>(79, 80 , 
				new PathEvent("You uncover a conspiracy within your faction and Firewall steps in to help you deal with it.", "Gain +10 to one rep score")));
		firewallEvent.add(new ChartEntry<PathEvent>(81, 82 , 
				new PathEvent("You are recruited to help Firewall cover up a secret or outbreak.", ActionType.modifyAptitude, 10, "i-rep", "Lose –10 to one rep score of your choice")));
		firewallEvent.add(new ChartEntry<PathEvent>(83, 84 , 
				new PathEvent("You uncover a sleeper exsurgent cell the hard way.", ActionType.addTrait, "Phobia Disorder (Exsurgents)")));
		firewallEvent.add(new ChartEntry<PathEvent>(85, 86 , 
				new PathEvent("You become aware of someone smuggling or dealing TITAN technology. Firewall steps in and deals with it, then recruits you.", ActionType.modifyAptitude, -5, "g-rep")));
		firewallEvent.add(new ChartEntry<PathEvent>(87, 88 , 
				new PathEvent("You were a member/supporter of one of the groups that evolved into Firewall from before the Fall.", ActionType.modifyStat, 10, "i-rep")));
		firewallEvent.add(new ChartEntry<PathEvent>(89, 90 ,
				new PathEvent("You are involved in a cover-up of a TITAN- or exsurgent-related secret during the Fall. Firewall finds you and brings you in to get the story.", ActionType.modifyAptitude, 10, "i-rep")));
		firewallEvent.add(new ChartEntry<PathEvent>(91, 92 , 
				new PathEvent("Someone you loved becomes infected. You keep it secret and protect them for a time, until everything goes bad. Firewall rescues you, then recruits you.", 
						ActionType.addSkill, 10, "Interest", "Exsurgents")));
		firewallEvent.add(new ChartEntry<PathEvent>(93, 94 , 
				new PathEvent("You are a bit too good at ferreting out certain secrets online. Firewall brings you in to the fold to keep your mouth shut.",
						ActionType.addSkill, 10, "Infosec")));
		firewallEvent.add(new ChartEntry<PathEvent>(95, 96 , 
				new PathEvent("Someone you are close to is a Firewall proxy; they brought you in to help them out.", ActionType.modifyStat, 1, "MOX")));
		firewallEvent.add(new ChartEntry<PathEvent>(97, 98 , 
				new PathEvent("Thanks to a particular skill you have, Firewall has consulted with you for years without revealing themselves. They decide to fill you in on the full story.", 
						"Gain or increase a Firewall-relevant Knowledge skill (such as Interest: Exsurgents or Academics: Nanotechnology) by +30.")));
		firewallEvent.add(new ChartEntry<PathEvent>(99, 100 , 
				new PathEvent("A package you are hired to deliver turns out to be an alien artifact. When it causes problems, you go to a friend of a friend, who turns out to be a Firewall proxy. "
						+ "They solve the problem, but you fail to make the delivery.", ActionType.addTrait, "Enemy")));
		
	}




}
