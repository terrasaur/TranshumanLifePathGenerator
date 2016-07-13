package lifepath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dice.Die;

/**
 * This is the AdultPath object (Steps 6-9, plus the charts for step 10).
 * The adult path is weird.
 * Steps to use:
 *   1. Determine age. If you are over 20, call getAdultPrefallPath(focus 
 *      determined from childhood.pathForward);
 *   2. If you aren't Lost Generation, get a Fall Event and set this.fallEvent.
 *      If you don't do this, you won't get async packages if you roll them 
 *      during the Fall.
 *   3. Call getAdultPostfallPath. If you haven't done a preFall path, you need
 *      to feed the postFall path childhood.pathForward
 *   4. Call getPackages() to get the final set of packages determined in this step
 *   5. Determine if you have enough package points from the list from step 4. If
 *      not, call getAdditionalPackages and feed it the number of PP it needs to get. 
 *      It will automatically add packages to the list you feed into it.
 *   6. (optional) Call getPathHistory() to get a string of the roll history
 * @author terrasaur
 *
 */
public class AdultPath {
	protected enum AdultFocus{
		Autonomist   (0),
		Civilian     (1),
		Criminal     (2),
		Elite        (3),
		Enclaver     (4),
		Indenture    (5),
		Military     (6),
		Scientist    (7),
		Spacer       (8),
		Techie       (9),
		Mercurial    (10),
		New          (11),
		Customization(12);
		
		int idx;
		AdultFocus(int i){
			this.idx = i;
		}
		
		static AdultFocus getFocusByString(String s){
			for (AdultFocus f: AdultFocus.values()){
				if (f.name() == s){
					return f;
				}
			}
			return null;
		}

	}
	private static AdultFocus[] focusArray = new AdultFocus[]{AdultFocus.Autonomist,
			AdultFocus.Civilian, AdultFocus.Criminal, AdultFocus.Elite,
			AdultFocus.Enclaver, AdultFocus.Indenture, AdultFocus.Military,
			AdultFocus.Scientist, AdultFocus.Spacer, AdultFocus.Techie};
	private AdultFocus faction;
	private AdultFocus focus = AdultFocus.New;
	protected String preFallPath;
	private LifePathPackage preFallPackage;
	private LifePathPackage postFallFocus;
	private LifePathPackage postFallFaction;
	private Boolean isAsync;
	private String pathHistory;
	PathEvent fallEvent;
	protected ArrayList<String> allowedFactions;
	
	/**
	 * This gets the pre-Fall focus and faction packages for a character, if
	 * that character has a pre-Fall life. If you have a pre-Fall path, you 
	 * do not need to give getPostfallPath a focus later on
	 * @param focus - the starting focus for the character
	 */
	public void getAdultPrefallPath(AdultFocus focus){
		this.preFallPath = "";
		Die d10 = new Die (10);
		int roll = d10.Roll();
		this.pathHistory = "Starting Adult Path with focus and faction " + focus.name() + "\n";	
		
		if (roll >= 7 || focus == AdultFocus.New){
			focus = getAllowedFaction(AdultFocus.New, false);
			this.focus = focus;
			this.preFallPackage = this.getFocusPackage(focus, d10.Roll(), 1);
			this.preFallPath = focus.name() + " Focus: " + this.preFallPackage.label;
		} else if (roll >= 6) {
			this.preFallPackage = this.getCustomizationPackage(true);
			this.preFallPath = "Customization Focus";
			this.focus = AdultFocus.Customization;
			this.pathHistory += "  Decided to customize instead.\n";
		} else {
			focus = getAllowedFaction(focus, false);
			this.focus = focus;
			this.preFallPackage = this.getFocusPackage(focus, d10.Roll(), 1);
			this.preFallPath = focus.name() + " Focus: " + this.preFallPackage.label;
		}
		
		if (this.preFallPackage == null){
			System.out.println("Package not found. Roll was "+ roll);			
		}					
	}
	
	/**
	 * PrettyPrint of path history. Really just removes the trailing newline.
	 * @return String of history
	 */
	public String getPathHistory(){
		return pathHistory.substring(0, this.pathHistory.length() -1);
	}

	/**
	 * Gets a customization package from the list. 	 
	 * @param preFall if this is a pre-Fall customization package
	 * @return selected customization package 
	 */
	private LifePathPackage getCustomizationPackage(boolean preFall) {
		return this.getCustomizationPackage(preFall, false);		
	}
	/**
	 * Gets a customization package from the list. 
	 * @param preFall if this is a pre-Fall customization package
	 * @param isAsync Optional, defaults to false
	 * @return selected customization package 
	 */
	private LifePathPackage getCustomizationPackage(boolean preFall, boolean isAsync) {
		Die d100 = new Die(100);
		int roll = d100.Roll();
		
		while ((roll>=5 && roll <=12 && preFall) || 
				(roll >= 51 && roll <= 55 && !isAsync)){
			// must reroll, these are psi traits
			roll = d100.Roll();
		}
		if (roll >=5 && roll <=12 && !preFall){
			// rolled an async trait
			this.isAsync = true;
		}
		// Customization packages are always 1 pp
		return new CustomizationPackage(ChartEntry.findResult(customizationPackages, roll), 1);
	}

	
	/**
	 * Gets a focus package that isn't the same as your pre-fall focus package.
	 * If you have no pre-fall focus package, this will just call getFocusPackage.
	 * @param focus your new focus
	 * @param roll 1-10
	 * @param pp - package points
	 * @return a package for the post fall
	 */
	private LifePathPackage getPostFallFocusPackage(AdultFocus focus, Integer roll, int pp) {
		LifePathPackage p = this.getFocusPackage(focus, roll, pp);
		if (this.preFallPackage == null){
			return p;
		}

		Die d10 = new Die(10);
		while(this.preFallPackage.equals(p)){
			this.focus = getAllowedFaction(this.focus, true);	
			p = this.getFocusPackage(this.focus, d10.Roll(), pp);			
		}		
		
		return p;
	}
	
	/**
	 * Gets a focus package given a focus faction. 
	 * @param focus Faction from AdultFocus
	 * @param roll 1-10 roll
	 * Updates this.focus and this.package
	 * @return one package
	 */
	private LifePathPackage getFocusPackage(AdultFocus focus, Integer roll, int pp) {
		this.focus = focus;
		FocusPackage.List focusType = focusArrays.get(focus.idx).get(roll-1);
		if (focusType == null)
			return null;
		return new FocusPackage(focusType, pp);	
	}

	/**
	 * I probably added this to stop a warning or something
	 * @return The current faction that the path is on
	 */
	public AdultFocus getFaction() {
		return faction;
	}

	/**
	 * Returns all packages in the Adult Path
	 * @return package in array list form
	 */
	public ArrayList<LifePathPackage> getPackages() {
		ArrayList<LifePathPackage> packages = new ArrayList<LifePathPackage>();
		if (this.preFallPackage != null){
			packages.add(this.preFallPackage);
		}
		if (this.postFallFocus != null){
			packages.add(this.postFallFocus);
		}
		if (this.postFallFaction != null){
			packages.add(this.postFallFaction);
		}
		return packages;
	}

	/**
	 * This gets the adult post-fall faction and focus.
	 * You must get and assign the Fall event before you call this if you 
	 * want to have the Fall event calculated properly.
	 * @param focus (optional): path-forward focus from childhood, not needed if you are >20
	 * @param allowedFactions 
	 * @param isAgiOrUplift if you are a mercurial
	 * @param isAsync if you are an async
	 */
	public void getAdultPostfallPath(AdultFocus focus,
			boolean isAGI, boolean isUplift, boolean isAsync) {
		this.focus = focus;
		//this.factionList = allowedFactions;
		this.pathHistory = "Starting Adult Path with focus and faction " + focus.name() + "\n";
		this.getAdultPostfallPath(isAGI, isUplift, isAsync);
	}
	public void getAdultPostfallPath(boolean isAGI, boolean isUplift, boolean isAsync) {
		Die d10 = new Die(10);		
		this.isAsync = isAsync;
		this.postFallFaction = null;
		this.postFallFocus = null;
		
		int factionPP, focusPP;
		
		// roll for table 9: adult path
		int roll = d10.Roll();
		if (roll <= 2){ // Faction paragon 
			this.pathHistory += "  Faction Paragon\n";
			factionPP = 3;
			focusPP   = 1;
		} else if (roll <= 5) { // Equally balanced 
			this.pathHistory += "  Equally balanced\n";
			factionPP = 3;
			focusPP   = 3;
		} else if (roll <= 7) {//  Defined by your actions 
			this.pathHistory += "  Defined by your actions\n";
			factionPP = 1;
			focusPP   = 3;
		} else { // You get the job done 
			this.pathHistory += "  You get the job done\n";
			factionPP = 1;
			focusPP   = 5;	
		}
		
		if (isAsync){
			this.pathHistory += "  Enabling Async: Lost Generation\n";
		}
		
		// Some fall events cause havoc with the faction/focus packages
		if (this.fallEvent != null){
			if (this.fallEvent.action == PathEvent.ActionType.overrideFaction){				
				this.pathHistory += "  Fall event overrides faction to " + this.fallEvent.label + "\n";
				
				FactionPackage.List newFaction = FactionPackage.getPackageByLabel(this.fallEvent.label);
				if (newFaction == null){
					//System.out.println(this.fallEvent.getFullText());
					this.postFallFaction = new BackgroundPackage(
							BackgroundPackage.getPackageByLabel(this.fallEvent.label), factionPP);
				} else
					this.postFallFaction = new FactionPackage(newFaction, factionPP);
				
			} else if (this.fallEvent.action == PathEvent.ActionType.overrideFocus){ // Probably an async now
				this.pathHistory += "  Fall event overrides focus to " + this.fallEvent.label + "\n";
				
				if (this.fallEvent.label == "Async" || this.fallEvent.label == "Async Adept"){
					focusPP = 1;
				}
				if (this.fallEvent.label.contains("Async")){
					this.isAsync = true;
					this.pathHistory += "  Enabling Async: Watts-MacLeod Strain\n";
				}
				FocusPackage.List newFocus = FocusPackage.getPackageByLabel(this.fallEvent.label);
				if (newFocus == null){
					//System.out.println(this.fallEvent.getFullText());
					this.postFallFocus = new CustomizationPackage(
							CustomizationPackage.getPackageByLabel(this.fallEvent.label), focusPP);
				} else
					this.postFallFocus = new FocusPackage(newFocus, focusPP);
			}
		}

		// Table 9.1
		this.focus = getAllowedFaction(this.focus, true);


		// Table 9.2
		if ((isAGI || isUplift) && this.allowedFactions.contains("Mercurial")){
			this.faction = AdultFocus.Mercurial;
			this.pathHistory += "  Changed faction to " + faction.name() + "\n";
		} else {
			this.faction = getAllowedFaction(this.focus, true);
		}
				
		// roll for table 9.5-9.14: adult path
		if (this.postFallFocus == null) {
			this.postFallFocus   = this.getPostFallFocusPackage(this.focus,   d10.Roll(), focusPP);
		}
		if (this.postFallFaction == null){
			this.postFallFaction = this.getFactionPackage      (this.faction, d10.Roll(), factionPP, isUplift);
		}

	}
	
	/**
	 * Gets an allowed faction.
	 *  
	 * @param currFaction current faction
	 * @param rollForChange if you want to roll a d10 to see if you need to switch
	 * @return allowed faction
	 */
	private AdultFocus getAllowedFaction(AdultFocus currFaction, boolean rollForChange){
		Die d10 = new Die(10);
		boolean rollNewFaction = false;	
		AdultFocus newFaction = null;
		
		if (this.allowedFactions != null && this.allowedFactions.size() == 1){
			if (currFaction.name() != this.allowedFactions.get(0))
				this.pathHistory += "  Changed focus to " + this.allowedFactions.get(0) + "\n";
			return AdultFocus.getFocusByString(this.allowedFactions.get(0));
		}
		
		// Table 9.1 and 9.2, plus some error checking
		if (currFaction == AdultFocus.Customization || currFaction == AdultFocus.New ||
				(rollForChange && d10.Roll() > 6)){ 
			rollNewFaction = true;
		} 
		
		// If there aren't any restrictions, just roll
		if (this.allowedFactions == null || this.allowedFactions.contains("(Select All)")){
			if (rollNewFaction){
				newFaction = focusArray[d10.Roll() - 1];
				if (newFaction != currFaction)
					this.pathHistory += "  Changed focus to " + currFaction.name() + "\n";				
				return newFaction; // table 6.1
			} else
				return currFaction;
		}
		
		// After this, we have to check for factions that are in the allowed faction table
		if (!rollNewFaction && this.allowedFactions.contains(currFaction.name()))
				return currFaction; // current is valid

		
		// If the current isn't on the list or you need to reroll
		do {
			newFaction = focusArray[d10.Roll() - 1]; // table 6.1
		} while (! this.allowedFactions.contains(newFaction.name()) );
		
		if (!this.allowedFactions.contains(currFaction.name()))
			this.pathHistory += "  Due to restrictions, changed " + currFaction.name() + 
			                       " to " + newFaction.name() + "\n";
		else
			this.pathHistory += "  Changed focus to " + newFaction.name() + "\n";
		
		return newFaction;
	}

	/**
	 * Gets one faction package
	 * @param faction faction to get the package from
	 * @param roll 1-10
	 * @param pp package points the package costs
	 * @param isUplift 
	 * @return One package
	 */
	private LifePathPackage getFactionPackage(AdultFocus faction, Integer roll, int pp, boolean isUplift) {
		this.faction = faction;
		if (faction.idx >= factionArrays.size()) // you have an invalid faction
			return null;
		
		FactionPackage.List factionType = factionArrays.get(faction.idx).get(roll-1);
		return new FactionPackage(factionType , pp);
	}
	
	/**
	 * Gets a number of additional packages
	 * @param list 
	 * @param numPackages Number of packages to get
	 * @return An array of packages
	 */
	public ArrayList<LifePathPackage> getAdditionalPackages(ArrayList<LifePathPackage> list, int numPackages, boolean isUplift) {
		Die d10 = new Die(10);
		int roll;
		LifePathPackage p = null;
		AdultFocus focus, faction;
		
		for (int i = 0; i < numPackages; i++){
			do {
				roll = d10.Roll();
				if (roll <= 4){
					// new focus package
					focus = getAllowedFaction(this.focus, true);

					p = this.getFocusPackage(focus, d10.Roll(), 1);
				} else if (roll <= 7){
					//new faction package
					if (this.faction == AdultFocus.Mercurial){
						faction = AdultFocus.Mercurial;
					} else 
						faction = getAllowedFaction(this.focus, true);
					
					p = this.getFactionPackage(faction, d10.Roll(), 1, isUplift);
				} else {
					// new customization package
					p = this.getCustomizationPackage(false, this.isAsync);
					this.pathHistory += "  Decided to customize.\n";
				}
			} while (this.addPackage(list, p) == false); // already exists
		}
		return list;
	}
	
	/**
	 * Attempts to adds a package to a list, but does not if it is a duplicate
	 * @param list List of existing packages
	 * @param p Package to add
	 * @return whether or not the package was added
	 */
	private boolean addPackage(ArrayList<LifePathPackage> list, LifePathPackage p){
		if (list.contains(p) == true){ // if it already exists, reroll
			return false;
		} else {
			list.add(p);
			return true;
		}
	}
	
	private static ChartEntry<CustomizationPackage.List> newCE_CP (Integer min, Integer max,
			CustomizationPackage.List entry){
		return new ChartEntry<CustomizationPackage.List>(min, max, entry);
	}
	
	static final protected List<ChartEntry<CustomizationPackage.List>> customizationPackages = Arrays.asList(
			newCE_CP( 1,  4, CustomizationPackage.List.Artist),
			newCE_CP( 5,  8, CustomizationPackage.List.Async),
			newCE_CP( 9, 12, CustomizationPackage.List.AsyncAdept),
			newCE_CP(13, 16, CustomizationPackage.List.Athletics),
			newCE_CP(17, 20, CustomizationPackage.List.ComputerTraining),
			newCE_CP(21, 24, CustomizationPackage.List.Connected),
			newCE_CP(25, 28, CustomizationPackage.List.Gearhead),
			newCE_CP(29, 32, CustomizationPackage.List.HeavyWeapons),
			newCE_CP(33, 39, CustomizationPackage.List.JackOfAllTrades),
			newCE_CP(40, 46, CustomizationPackage.List.Lucky),
			newCE_CP(47, 50, CustomizationPackage.List.MartialArts),
			newCE_CP(51, 54, CustomizationPackage.List.Mentalist),
			newCE_CP(55, 61, CustomizationPackage.List.Networker),
			newCE_CP(62, 65, CustomizationPackage.List.Paramedic),
			newCE_CP(66, 69, CustomizationPackage.List.Slacker),
			newCE_CP(70, 73, CustomizationPackage.List.Sneaker),
			newCE_CP(74, 77, CustomizationPackage.List.SocialButterfly),
			newCE_CP(78, 81, CustomizationPackage.List.Spacer),
			newCE_CP(82, 85, CustomizationPackage.List.Student),
			newCE_CP(86, 89, CustomizationPackage.List.SurvivalTraining),
			newCE_CP(90, 93, CustomizationPackage.List.TechTraining),
			newCE_CP(94, 100,CustomizationPackage.List.WeaponsTraining));
	
	private static List<List<FocusPackage.List>> focusArrays = Arrays.asList(
			// autonomist
			Arrays.asList(FocusPackage.List.Academic,  FocusPackage.List.Activist, FocusPackage.List.BotJammer, 
					FocusPackage.List.CovertOps,  FocusPackage.List.Explorer, FocusPackage.List.Genehacker, 
					FocusPackage.List.Hacker,  FocusPackage.List.Medic, FocusPackage.List.Scientist, 
					FocusPackage.List.Techie),
			//civilian
			Arrays.asList(FocusPackage.List.Activist, FocusPackage.List.ConArtist, FocusPackage.List.Dealer,
					FocusPackage.List.Face, FocusPackage.List.Investigator, FocusPackage.List.Journo, 
					FocusPackage.List.SmartAnimalHandler, FocusPackage.List.Soldier, FocusPackage.List.Techie,
					FocusPackage.List.Thief),
			//criminal
			Arrays.asList(FocusPackage.List.Assassin, FocusPackage.List.ConArtist, FocusPackage.List.CovertOps, 
					FocusPackage.List.Dealer, FocusPackage.List.EgoHunter, FocusPackage.List.Enforcer, 
					FocusPackage.List.Hacker, FocusPackage.List.Pirate, FocusPackage.List.Smuggler, 
					FocusPackage.List.Thief),
			//elite
			Arrays.asList(FocusPackage.List.Academic, FocusPackage.List.Dealer, FocusPackage.List.Face, 
					FocusPackage.List.Face, FocusPackage.List.Icon, FocusPackage.List.Icon, 
					FocusPackage.List.Journo, FocusPackage.List.Medic, 	FocusPackage.List.Psychosurgeon, 
					FocusPackage.List.Scientist),
			//enclaver
			Arrays.asList(FocusPackage.List.Academic, FocusPackage.List.ConArtist, FocusPackage.List.Dealer, 
					FocusPackage.List.Dealer, FocusPackage.List.Face, FocusPackage.List.Icon,
					FocusPackage.List.Investigator, FocusPackage.List.Journo, FocusPackage.List.Medic, 
					FocusPackage.List.Psychosurgeon),
			//indenture
			Arrays.asList(FocusPackage.List.Activist, FocusPackage.List.Bodyguard, FocusPackage.List.BotJammer, 
					FocusPackage.List.ConArtist, FocusPackage.List.Enforcer, FocusPackage.List.Pirate, 
					FocusPackage.List.Scavenger, FocusPackage.List.SmartAnimalHandler, 
					FocusPackage.List.Smuggler, FocusPackage.List.Thief),
			//military
			Arrays.asList(FocusPackage.List.Assassin, FocusPackage.List.Bodyguard, FocusPackage.List.CovertOps,
					FocusPackage.List.EgoHunter, FocusPackage.List.Enforcer, FocusPackage.List.Investigator,
					FocusPackage.List.Soldier, FocusPackage.List.Soldier, FocusPackage.List.Soldier,
					FocusPackage.List.Spy),
			//scientist
			Arrays.asList(FocusPackage.List.Academic, FocusPackage.List.Explorer, FocusPackage.List.Genehacker,
					FocusPackage.List.Investigator, FocusPackage.List.Medic, FocusPackage.List.Psychosurgeon, 
					FocusPackage.List.Scientist, FocusPackage.List.Scientist, 
					FocusPackage.List.SmartAnimalHandler, FocusPackage.List.Techie),
			//spacer
			Arrays.asList(FocusPackage.List.BotJammer, FocusPackage.List.EgoHunter, FocusPackage.List.Explorer,
					FocusPackage.List.Explorer, FocusPackage.List.Pirate, FocusPackage.List.Scavenger, 
					FocusPackage.List.Soldier, FocusPackage.List.Smuggler, FocusPackage.List.Smuggler, 
					FocusPackage.List.Spy),
			//techie
			Arrays.asList(FocusPackage.List.BotJammer, FocusPackage.List.Explorer, FocusPackage.List.Genehacker, 
					FocusPackage.List.Hacker, FocusPackage.List.Hacker, FocusPackage.List.Scavenger, 
					FocusPackage.List.Scientist, FocusPackage.List.Spy, FocusPackage.List.Techie,
					FocusPackage.List.Techie)); // no mercurial
	
	
	private static List<List<FactionPackage.List>> factionArrays = Arrays.asList(
			//autonomist
			Arrays.asList(FactionPackage.List.Anarchist, FactionPackage.List.Argonaut, 
					FactionPackage.List.Barsoomian, FactionPackage.List.Brinker, FactionPackage.List.Criminal, 
					FactionPackage.List.Europan, FactionPackage.List.Extropian, FactionPackage.List.Ringer, 
					FactionPackage.List.Scum, FactionPackage.List.Titanian),
			// civilian
			Arrays.asList(FactionPackage.List.Belter, FactionPackage.List.Bioconservative, 
					FactionPackage.List.Criminal, FactionPackage.List.Hypercorp, FactionPackage.List.Lunar, 
					FactionPackage.List.Orbital, FactionPackage.List.Reclaimer, FactionPackage.List.Sifter, 
					FactionPackage.List.Skimmer, FactionPackage.List.Titanian),
			//criminal
			Arrays.asList(FactionPackage.List.Anarchist, FactionPackage.List.Belter, FactionPackage.List.Brinker,
					FactionPackage.List.Criminal, FactionPackage.List.Exhuman, FactionPackage.List.Extropian, 
					FactionPackage.List.Lunar, FactionPackage.List.Orbital, FactionPackage.List.Ringer,
					FactionPackage.List.Scum),
			//elite
			Arrays.asList(FactionPackage.List.Bioconservative, FactionPackage.List.Brinker, 
					FactionPackage.List.Exhuman, FactionPackage.List.Extropian, FactionPackage.List.Hypercorp, 
					FactionPackage.List.Orbital, FactionPackage.List.Socialite, FactionPackage.List.Precautionist,
					FactionPackage.List.Ultimate, FactionPackage.List.Venusian),
			//enclaver
			Arrays.asList(FactionPackage.List.Bioconservative, FactionPackage.List.Extropian, 
					FactionPackage.List.Hypercorp, FactionPackage.List.Jovian, FactionPackage.List.Lunar, 
					FactionPackage.List.Orbital, FactionPackage.List.Socialite, 
					FactionPackage.List.Preservationist, FactionPackage.List.Reclaimer, 
					FactionPackage.List.Venusian),
			//indenture
			Arrays.asList(FactionPackage.List.Anarchist, FactionPackage.List.Barsoomian, 
					FactionPackage.List.Hypercorp, FactionPackage.List.Lunar, FactionPackage.List.Scum, 
					FactionPackage.List.Preservationist, FactionPackage.List.Reclaimer, 
					FactionPackage.List.Sifter, FactionPackage.List.Skimmer, FactionPackage.List.Venusian),
			//military
			Arrays.asList(FactionPackage.List.Bioconservative, FactionPackage.List.Brinker, 
					FactionPackage.List.Criminal, FactionPackage.List.Hypercorp, FactionPackage.List.Jovian, 
					FactionPackage.List.Lunar, FactionPackage.List.Orbital, FactionPackage.List.Reclaimer, 
					FactionPackage.List.Precautionist, FactionPackage.List.Ultimate),
			//scientist
			Arrays.asList(FactionPackage.List.Argonaut, FactionPackage.List.Europan, FactionPackage.List.Exhuman,
					FactionPackage.List.Hypercorp, FactionPackage.List.NanoEcologist, 
					FactionPackage.List.Precautionist, FactionPackage.List.SingularitySeeker, 
					FactionPackage.List.Solarian, FactionPackage.List.Titanian, FactionPackage.List.Venusian),
			//spacer
			Arrays.asList(FactionPackage.List.Belter, FactionPackage.List.Brinker, FactionPackage.List.Criminal, 
					FactionPackage.List.Extropian, FactionPackage.List.Outster, FactionPackage.List.Scum,
					FactionPackage.List.Ringer, FactionPackage.List.SingularitySeeker, 
					FactionPackage.List.Skimmer, FactionPackage.List.Solarian),
			//technie
			Arrays.asList(FactionPackage.List.Anarchist, FactionPackage.List.Argonaut, 
					FactionPackage.List.Barsoomian, FactionPackage.List.Extropian, FactionPackage.List.Hypercorp,
					FactionPackage.List.NanoEcologist, FactionPackage.List.Sifter, 
					FactionPackage.List.SingularitySeeker, FactionPackage.List.Titanian, 
					FactionPackage.List.Venusian),
			//mercurial
			Arrays.asList(FactionPackage.List.Anarchist, FactionPackage.List.Argonaut, 
					FactionPackage.List.Brinker, FactionPackage.List.Criminal, FactionPackage.List.Europan, 
					FactionPackage.List.Hypercorp, FactionPackage.List.MercurialInfolife, 
					FactionPackage.List.Sapient, FactionPackage.List.Solarian, FactionPackage.List.Venusian));

}
