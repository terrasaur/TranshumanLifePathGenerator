package lifepath;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import lifepath.BackgroundPackage;
import character.Morph;
import dice.Die;

/**
 * Youth Path object for Life Path. Encapsulates step 3 from the book.
 * To use, make a new YouthPath object and simply call getYouthPath
 * @author terrasaur
 *
 */
public class YouthPath {
	
	// Type of background, made as an enum for the reflection stuff later on
	protected enum BackgroundType {
		Earthborn("Earthborn"),
		Orbital("Orbital"),
		Lunar("Lunar"),
		Martian("Martian Settler"),
		Sunward("Sunward Hab"),
		Rimward("Rimward Hab"),
		Migrant("Migrant"),
		Created("Created"),//, not Born"),
		Colonist("Colonial Staff")
		;
		
		private String text;
		
		private BackgroundType(String s){
			this.text = s;
		}
		
		public String toString(){
			return text;
		}
		
		public Boolean equals(String s){
			if (s == this.text)
				return true;
			else if (s == this.name())
				return true;
			return false;
		}

		public static BackgroundType getBackgroundByLabel(String label) {
			for (BackgroundType bg : BackgroundType.values()){
				if (bg.equals(label))
					return bg;
			}
			return null;
		}

	}
	
	// This probably can go away. It ends up complicating things more than I 
	// thought it would
	protected class FluffPackage {
		LifePathPackage p;
		String fluff;
		
		FluffPackage(LifePathPackage p, String s){
			this.p = p;
			this.fluff = s;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof LifePathPackage){
				// If we're comparing strings, look for package name 
				return this.p.equals((LifePathPackage)obj);
			}
			if (obj instanceof String){
				// If we're comparing strings, look for package name 
				return this.p.label == (String)obj;
			}
			if (obj instanceof FluffPackage){
				// If we're comparing fluff objects, ignore fluff 
				return this.p.label == ((FluffPackage)obj).p.label;
			}
			return super.equals(obj);
		}
	}
	protected ArrayList<FluffPackage> packages;
	protected ArrayList<String> allowedBackgrounds;

	protected ArrayList<BackgroundType> backgrounds;
	protected Morph startingMorph;
	protected AdultPath.AdultFocus pathForward;
	protected Boolean goDirectlyToAdult;
	private String youthType;
	protected boolean isUplift;
	protected boolean isAGI;
	protected boolean isAsync;
	
	@SuppressWarnings("unchecked")
	static final protected ChartEntry<BackgroundType>[] backgroundTypes = new ChartEntry[BackgroundType.values().length];
	static 	{
		backgroundTypes[0] =  new ChartEntry<BackgroundType>(1,  50,  BackgroundType.Earthborn);
		backgroundTypes[1] =  new ChartEntry<BackgroundType>(51, 60,  BackgroundType.Orbital);
		backgroundTypes[2] =  new ChartEntry<BackgroundType>(61, 68,  BackgroundType.Lunar);
		backgroundTypes[3] =  new ChartEntry<BackgroundType>(69, 76,  BackgroundType.Martian);
		backgroundTypes[4] =  new ChartEntry<BackgroundType>(77, 82,  BackgroundType.Sunward);
		backgroundTypes[5] =  new ChartEntry<BackgroundType>(83, 89,  BackgroundType.Rimward);
		backgroundTypes[6] =  new ChartEntry<BackgroundType>(90, 95,  BackgroundType.Migrant);
		backgroundTypes[7] =  new ChartEntry<BackgroundType>(96, 100, BackgroundType.Created);
	}
	
	public YouthPath() {
		this.packages    = new ArrayList<FluffPackage>();
		this.backgrounds = new ArrayList<BackgroundType>();
	}


	public ArrayList<BackgroundType> getBackgrounds() {
		return this.backgrounds;
	}

	/**
	 * Generates all the Youth Path backgrounds and packages given a roll.
	 * This resets the current YouthPath, so save away a previous version if 
	 * you want to keep the previous results
	 * @param allowedFactions 
	 * @param roll 1-10 which type of youth this is
	 */
	public void getYouthPath(){		
		Die d10  = new Die(10);
		this.backgrounds.clear();
		this.packages.clear();
		this.startingMorph = null;
		this.goDirectlyToAdult = false;
		this.isUplift = false;
		this.isAGI = false;
		this.isAsync = false;
		BackgroundType tempType = null;
		FluffPackage p = null;
		int roll = d10.Roll();
		tempType = getAllowedBackground();
		//tempType = BackgroundType.Created;
		//roll = 10;
		
		if (roll < 7){ 
			this.youthType = "Wholesome Youth";
			// Roll once; take the result as a 3 PP package.
			this.addPackage(this.getOnePackage(tempType, 3), tempType);
		} else if (roll < 10) {
			this.youthType = "Split Youth";
			// Roll twice; take the results as two 1 PP packages.			
			this.addPackage(this.getOnePackage(tempType, 1), tempType); // can't be a duplicate
			
			tempType = getAllowedBackground();
			p = this.getOnePackage(tempType, 1);
			if (!this.goDirectlyToAdult){
				while (this.addPackage(p, tempType) == false){ // if it already exists, reroll
					tempType = getAllowedBackground();
					p = this.getOnePackage(tempType, 1);
				}
			}
		} else {
			this.youthType = "Fractured Youth";
			// Roll three times; take the results as three 1 PP packages.
			this.packages.add(this.getOnePackage(tempType, 1));	
			tempType = getAllowedBackground();
			p = this.getOnePackage(tempType, 1);
			
			for (int i = 0; i < 2; i++){
				if (!this.goDirectlyToAdult){
					while (this.addPackage(p, tempType) == false){ // if it already exists, reroll
						tempType = getAllowedBackground();
						p = this.getOnePackage(tempType, 1);
					}
				}
			}
		}
	}
	
	/** 
	 * Gets an allowed roll value. Checks this.allowedBackgrounds for allowed types
	 * @return resulting background type
	 */
	private BackgroundType getAllowedBackground(){
		Die d100 = new Die(100);
		if (this.allowedBackgrounds == null || this.allowedBackgrounds.contains("(Select All)"))
			return ChartEntry.findResult(YouthPath.backgroundTypes, d100.Roll());
				
		boolean createdSubtypeSelected = false;
		// If you have a subtype selected
		if ( this.allowedBackgrounds.contains("   AGI") ||	this.allowedBackgrounds.contains("   Uplift") ||
				this.allowedBackgrounds.contains("   Lost Generation")){
			// And you don't have all subtypes selected
			if ((this.allowedBackgrounds.size() == 4 && this.allowedBackgrounds.contains("Created")) ||
					this.allowedBackgrounds.size() == 1 )
				return BackgroundType.Created;
			// You get some weird stuff later on
			createdSubtypeSelected = true;
		}
				
		if (this.allowedBackgrounds.size() == 1 ) {// only one member
			return BackgroundType.getBackgroundByLabel(this.allowedBackgrounds.get(0));
		}
		
		// most cases probably
		BackgroundType result ;
		do{
			result = ChartEntry.findResult(YouthPath.backgroundTypes, d100.Roll());
		} while ( ! (this.allowedBackgrounds.contains(result.text) || 
				(createdSubtypeSelected && result.equals("Created"))));
		
		return result;
	}
	
	/**
	 * Adds a single package to a list. Will update backgrounds list as well,
	 * and also will clear the list if it sees that this.goDirectlyToAdult is flagged.
	 * @param p Package to add
	 * @param t Background to associate with the package
	 * @return True if it has added it, false if it was a duplicate
	 */
	private boolean addPackage(FluffPackage p, BackgroundType t){
		if (this.packages.contains(p) == true){ // if it already exists, reroll
			return false;
		} else {
			if (this.goDirectlyToAdult){ // Remove all packages except this one
				this.packages.clear();
				this.backgrounds.clear();
				this.packages.add(p);
				this.backgrounds.add(t);
			} else {
				this.packages.add(p);
				this.backgrounds.add(t);
			}
			return true;
		}
	}
	

	/**
	 * What it says on the tin. There's one Background Path that says to do this.
	 */
	public boolean overrideLastPackageWithStreetRat() {
		FluffPackage lastPackage = this.packages.get(this.packages.size()-1);
		FluffPackage newPackage = new FluffPackage(new BackgroundPackage(BackgroundPackage.List.StreetRat, lastPackage.p.ppCost), 
				lastPackage.fluff + " (but you fell in with the wrong crowd)");
		if (this.packages.contains(newPackage)){  
			// if it already exists, reroll your background until you get something else
			return false;
		} else {			
			this.packages.remove(this.packages.size()-1);
			this.packages.add(newPackage);
			return true;
		}
	}


	/**
	 * Gets one package as per the Transhuman charts for that background type.
	 * Also sets the package's PP to whatever you pass in.
	 * @param type type of background you wish to have a package from
	 * @param pp Package Point cost of the background in question (1, 3, or 5)
	 * @return One package object
	 * Also updates this.bgFluff with a fluff string. If you discard the package, clean this up.
	 */
	private FluffPackage getOnePackage(BackgroundType type, Integer pp){
		Class<? extends YouthPath> c = this.getClass();
		Object obj = this;
		FluffPackage p = null;
		Class<?>[] args = new Class[1];
		args[0] = Integer.class;
		
		String methodName = "get" + type.name() + "Youth";	
		if (type == BackgroundType.Colonist){
			//System.out.println("Error: Cannot get Colonist background through getOnePackage()");
			return this.getColonistYouth("", pp);
		}

		try {
			Method method = c.getDeclaredMethod(methodName, args);
			p = (FluffPackage) method.invoke(obj, pp);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	
		return p;
	}

	
	
	/**
	 * This sets the Earthborn youth package. It will assign the 
	 * starting morph and path forward as well.
	 * @param pp Package Point cost of the package in question
	 * @return the package it found
	 */@SuppressWarnings("unused")
	private FluffPackage getEarthbornYouth(Integer pp){
		Die d10 = new Die(10);
		int roll = d10.Roll();
		String fluff = "Earthborn";
		
		if (roll == 1){
			fluff += ": Born with a silver nanoswarm in your blood";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Exalt");
			}
			this.pathForward = AdultPath.AdultFocus.Elite;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.HypereliteScion, pp), fluff); 
		} else if (roll == 2) {
			fluff += ": Celebrity child";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Sylph");
			}
			this.pathForward = AdultPath.AdultFocus.Elite;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.HypereliteMedia, pp), fluff);
		} else if (roll <= 5) {
			fluff += " Privileged: Enclave born";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Splicer");
			}
			this.pathForward = AdultPath.AdultFocus.Enclaver;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.FallEvacueeEnclaver, pp), fluff);
		} else if (roll <= 7) {
			fluff += " Precariat: Poverty just a step away";
			if (this.startingMorph == null){
				if (d10.Roll()< 5){
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Flat");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Civilian;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ReinstantiatedCivilian, pp), fluff);
		} else if (roll == 8){
			fluff += " Troubled: Raised among disaster or war";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Flat");
			}
			int roll2 = d10.Roll();
			if (roll2 < 6){
				this.pathForward = AdultPath.AdultFocus.Criminal;
			} else if (roll2 < 10){ 
				this.pathForward = AdultPath.AdultFocus.Civilian;
			} else {
				this.pathForward = AdultPath.AdultFocus.Autonomist;
			}
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.FallEvacueeUnderclass, pp), fluff);
		} else if (roll == 9){
			fluff += ": Raised on the street";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Flat");
			}
			this.pathForward = AdultPath.AdultFocus.Criminal;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.StreetRat, pp), fluff) ;
		} else {
			fluff += ": Raised in a collective/communal grouping";
			if (this.startingMorph == null){
				if (d10.Roll()< 5){
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Flat");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Autonomist;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ReinstantiatedCivilian, pp), fluff);
		}
	}
	
	/**
	 * Gets one package for a Orbital youth. May return a Colonial result.
	 * @param pp Package Point cost
	 * @return Orbital (or possibly Colonial) package.
	 * Sets Starting Morph if none is set, overwrites Path Forward regardless
	 * Adds a string to bgFluff explaining the roll.
	 */
	@SuppressWarnings("unused")
	private FluffPackage getOrbitalYouth(Integer pp){
		Die d10 = new Die(10);
		int roll = d10.Roll();
		String fluff = "Orbital: ";
		
		if (roll == 1){
			fluff += "Elite";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Exalt");
			}
			this.pathForward = AdultPath.AdultFocus.Elite;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.HypereliteScion, pp), fluff); 
		} else if (roll == 2) {
			fluff += "A new star born above the Earth";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Sylph");
			}
			this.pathForward = AdultPath.AdultFocus.Elite;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.HypereliteMedia, pp), fluff);
		} else if (roll <= 4){
			fluff = "Orbital Colonist: Floating above the masses";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Splicer");
			}
			this.pathForward = AdultPath.AdultFocus.Enclaver;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.FallEvacueeEnclaver, pp), fluff);
		} else if (roll <= 6){
			return this.getColonistYouth(BackgroundType.Orbital.name(), pp);
		} else if (roll <= 8){
			fluff += "One of the lucky few to live above";
			if (this.startingMorph == null){
				int roll2 = d10.Roll();
				if (roll2 < 5){
					this.startingMorph = new Morph("Splicer");
				} else if (roll2 < 10){
					this.startingMorph = new Morph("Flat");
				} else {
					this.startingMorph = new Morph("Bouncer");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Civilian;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ReinstantiatedCivilian, pp), fluff);
		} else {
			fluff += "Worker family";
			if (this.startingMorph == null){
				if (d10.Roll() < 9) {
					this.startingMorph = new Morph("Flat");
				} else {
					this.startingMorph = new Morph("Bouncer");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Indenture;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.Indenture, pp), fluff);
		}
	}
	
	
	/**
	 * Gets one package for a Lunar youth. May return a Colonial result.
	 * @param pp Package Point cost
	 * @return Lunar (or possibly Colonial) package.
	 * Sets Starting Morph if none is set, overwrites Path Forward regardless
	 * Adds a string to bgFluff explaining the roll.
	 */
	@SuppressWarnings("unused")
	private FluffPackage getLunarYouth(Integer pp){
		String fluff = "Lunar: ";
		Die d10 = new Die(10);
		int roll = d10.Roll();
		
		if (roll == 1){
			fluff += "Elite";
			if (this.startingMorph == null){
				if (d10.Roll() < 8){
					this.startingMorph = new Morph("Exalt");
				} else {
					this.startingMorph = new Morph("Sylph");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Elite;
			if (d10.Roll() < 8){
				return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.HypereliteScion, pp), fluff);
			} else {
				return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.HypereliteMedia, pp), fluff);
			}
		} else if (roll <= 3){
			fluff = "Lunar Colonist: privileged homesteader";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Splicer");
			}
			this.pathForward = AdultPath.AdultFocus.Enclaver;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.FallEvacueeEnclaver, pp), fluff);
		} else if (roll <= 5){
			return this.getColonistYouth(BackgroundType.Lunar.name(), pp);
		} else if (roll <= 7){
			fluff += "Raised with a view of Earth";
			if (this.startingMorph == null){
				if (d10.Roll() < 5){
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Flat");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Civilian;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ReinstantiatedCivilian, pp), fluff);
		} else {
			fluff += "Work force";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Flat");
			}
			this.pathForward = AdultPath.AdultFocus.Indenture;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.Indenture, pp), fluff);
		}

	}
	
	/**
	 * Gets one package for a Martian youth. May return a Colonial result.
	 * @param pp Package Point cost
	 * @return Martian (or possibly Colonial) package.
	 * Sets Starting Morph if none is set, overwrites Path Forward regardless
	 * Adds a string to bgFluff explaining the roll.
	 */
	@SuppressWarnings("unused")
	private FluffPackage getMartianYouth(Integer pp){
		String fluff = "Martian: ";
		Die d10 = new Die(10);
		int roll = d10.Roll();
		if (roll == 1){
			fluff += "Elite";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Olympian");
			}
			this.pathForward = AdultPath.AdultFocus.Elite;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.HypereliteScion, pp), fluff);
		} else if (roll <= 3){
			fluff = "Martian Colonist: Privileged homesteader";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Splicer");
			}
			this.pathForward = AdultPath.AdultFocus.Enclaver;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.FallEvacueeEnclaver, pp), fluff);
		} else if (roll <= 5) {
			return this.getColonistYouth(BackgroundType.Martian.name(), pp);
		} else if (roll == 6){
			fluff += "Risk-taking settler";
			if (this.startingMorph == null){
				if (d10.Roll() < 5) {
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Flat");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Civilian;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ReinstantiatedCivilian, pp), fluff);
		} else if (roll <= 9) {
			fluff += "Slave labor";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Flat");
			}
			this.pathForward = AdultPath.AdultFocus.Indenture;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.Indenture, pp), fluff);
		} else {
			fluff += "Pre-Barsoomian nomad";
			if (this.startingMorph == null){
				if (d10.Roll() < 5) {
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Flat");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Autonomist;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.Drifter, pp), fluff);
		}
	}
		
	/**
	 * Gets one package for a Sunward youth. May return a Colonial result.
	 * @param pp Package Point cost
	 * @return Sunward (or possibly Colonial) package.
	 * Sets Starting Morph if none is set, overwrites Path Forward regardless
	 * Adds a string to bgFluff explaining the roll.
	 */
	@SuppressWarnings("unused")
	private FluffPackage getSunwardYouth(Integer pp){
		String fluff = "Sunward: ";
		Die d10 = new Die(10);
		int roll = d10.Roll();
		
		if (roll == 1){
			fluff += "Pioneer dynasty";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Exalt");
			}
			this.pathForward = AdultPath.AdultFocus.Elite;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.HypereliteScion, pp), fluff);
		} else if (roll <= 3){
			fluff = "Venusian Colonist: Privileged homesteader";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Splicer");
			}
			this.pathForward = AdultPath.AdultFocus.Enclaver;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.FallEvacueeEnclaver, pp), fluff);
		} else if (roll <= 6) {
			return this.getColonistYouth("Venusian", pp);
		} else {
			fluff += "Mercurian slave labor";
			if (this.startingMorph == null){
				if (d10.Roll() < 8){
					this.startingMorph = new Morph("Flat");
				} else {
					this.startingMorph = new Morph("Case");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Indenture;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.Indenture, pp), fluff);
		}
	}
	
	
	/**
	 * Gets one package for a Rimward youth. May return a Colonial result.
	 * @param pp Package Point cost
	 * @return Rimward (or possibly Colonial) package.
	 * Sets Starting Morph if none is set, overwrites Path Forward regardless
	 * Adds a string to bgFluff explaining the roll.
	 */
	@SuppressWarnings("unused")
	private FluffPackage getRimwardYouth(Integer pp){
		String fluff = "Rimward: ";
		Die d10 = new Die(10);
		int roll = d10.Roll();
		
		if (roll == 1){
			fluff += "Extropia founders";
			if (this.startingMorph == null){
				this.startingMorph = new Morph("Bouncer");
			}
			this.pathForward = AdultPath.AdultFocus.Elite;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.FallEvacueeEnclaver, pp), fluff);
		} else if (roll == 2) {
			fluff = "Jovian Colonist: Shelter among giants";
			if (this.startingMorph == null){
				int roll2 = d10.Roll();
				if (roll2 <= 5){
					this.startingMorph = new Morph("Flat");
				} else if (roll2 < 9){
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Olympian");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Military;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.IsolateSurvivalist, pp), fluff);
		} else if (roll == 3) {
			fluff += "Titanian colonist";
			if (d10.Roll() < 9){
				this.startingMorph = new Morph("Splicer");
			} else {
				this.startingMorph = new Morph("Menton");
			}
			this.pathForward = AdultPath.AdultFocus.Scientist;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ColonistScienceStaff, pp), fluff);
		} else if (roll <= 5) {
			fluff += "Anarchist colonist";
			if (d10.Roll() < 7){
				this.startingMorph = new Morph("Splicer");
			} else {
				this.startingMorph = new Morph("Bouncer");
			}
			this.pathForward = AdultPath.AdultFocus.Autonomist;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ColonistTechStaff, pp), fluff);
		} else if (roll <= 8) {
			return this.getColonistYouth("Small Rimward Outpost", pp);
		} else {
			fluff += "Asteroid miner";
			int roll2 = d10.Roll();
			if (roll2 < 5){
				this.startingMorph = new Morph("Flat");
			} else if (roll2 < 8){
				this.startingMorph = new Morph("Case");
			} else {
				this.startingMorph = new Morph("Bouncer");
			}
			this.pathForward = AdultPath.AdultFocus.Indenture;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.Indenture, pp), fluff);
		}		
	}

		
	/**
	 * Gets one package for a Migrant youth
	 * @param pp Package Point cost
	 * @return Migrant package.
	 * Sets Starting Morph if none is set, overwrites Path Forward regardless
	 * Adds a string to bgFluff explaining the roll.
	 */
	@SuppressWarnings("unused")
	private FluffPackage getMigrantYouth(Integer pp){
		String fluff = "Migrant: ";
		Die d10 = new Die(10);
		int roll = d10.Roll();
		
		if (roll <= 3) {
			fluff += "Wandering the system";
			if (this.startingMorph == null){
				// 1–3   (p. 16) 1–6 , 7–0 
				if (d10.Roll() < 7){
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Bouncer");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Spacer;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.Drifter, pp), fluff);
		} else if (roll <= 5){
			fluff += "Found freedom in orbit";
			if (this.startingMorph == null) {
			
				int roll2 = d10.Roll();
				if (roll2 < 6){
					this.startingMorph = new Morph("Splicer");
				} else if (roll2 < 9){
					this.startingMorph = new Morph("Bouncer");
				} else { 
					Die d100 = new Die(100);
					this.startingMorph = LifePathCharts.getRandomMorph(d100.Roll());
				}
			}
			this.pathForward = AdultPath.AdultFocus.Autonomist;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.OriginalScum, pp), fluff);
		} else if (roll <= 8) {
			fluff += "Supply ship crew";
			if (this.startingMorph == null){
				int roll2 = d10.Roll();
				if (roll2 < 5){
					this.startingMorph = new Morph("Splicer");
				} else if (roll2 < 8){
					this.startingMorph = new Morph("Bouncer");
				} else {
					this.startingMorph = new Morph("Hibernoid");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Spacer;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ColonistFlightStaff, pp), fluff);
		} else {
			fluff += "Migrant worker";
			if (this.startingMorph == null){
				int roll2 = d10.Roll();
				if (roll2 < 5){
					this.startingMorph = new Morph("Flat");
				} else if (roll2 < 8){
					this.startingMorph = new Morph("Case");
				} else {
					this.startingMorph = new Morph("Bouncer");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Indenture;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.Indenture, pp), fluff);
		}
	}
	

	/**
	 * Gets one package for a Colonial youth. Colonial is not called directly; 
	 * it may only be called from other youth paths.
	 * @param pp Package Point cost
	 * @return Colonial package.
	 * Sets Starting Morph if none is set, overwrites Path Forward regardless
	 * Adds a string to bgFluff explaining the roll.
	 */
	private FluffPackage getColonistYouth(String callingPath, Integer pp){
		String fluff = callingPath + " Colonist: ";
		Die d10 = new Die(10);
		int roll = d10.Roll();
		if (roll == 1){
			fluff += "Born to lead";
			if (this.startingMorph == null){
				if (d10.Roll() < 8){
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Exalt");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Enclaver;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ColonistCommandStaff, pp), fluff);
		} else if (roll <= 3){
			fluff += "Space crew";
			if (this.startingMorph == null){
				if (d10.Roll() < 5){
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Bouncer");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Spacer;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ColonistFlightStaff, pp), fluff);
		} else if (roll <= 5){
			fluff += "Peacekeeper";
			if (this.startingMorph == null){
				int roll2 = d10.Roll();
				if (roll2 < 4){
					this.startingMorph = new Morph("Flat");
				} else if (roll2 < 8) {
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Olympian");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Military;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ColonistSecurityStaff, pp), fluff);
		} else if (roll <= 7){
			fluff += "Researcher";
			if (this.startingMorph == null){
				if (d10.Roll() < 5){
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Menton");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Scientist;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ColonistScienceStaff, pp), fluff);
		} else {
			fluff += "You kept the habitat functioning";
			if (this.startingMorph == null){
				int roll2 = d10.Roll();
				if (roll2 < 4){
					this.startingMorph = new Morph("Flat");
				} else if (roll2 < 9) {
					this.startingMorph = new Morph("Splicer");
				} else {
					this.startingMorph = new Morph("Bouncer");
				}
			}
			this.pathForward = AdultPath.AdultFocus.Techie;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.ColonistTechStaff, pp), fluff);
		}

	}
	
	/**
	 * Gets one package for a Created youth. Created may exit YouthPath creation
	 * and wipe previous packages using the flag goDirectlyToAdult
	 * @param pp Package Point cost
	 * @return Created package.
	 * Sets Starting Morph, overwrites Path Forward
	 * Adds a string to bgFluff explaining the roll.
	 */
	@SuppressWarnings("unused")
	private FluffPackage getCreatedYouth(Integer pp){
		String fluff = "Created: ";
		Die d10 = new Die(10);
		int roll = getCreatedRoll();
		if (roll < 8) { // AGI
			this.isAGI = true;
			if (d10.Roll() <=3) { // Created after the Fall
				fluff = "Created (After the Fall): ";
				this.goDirectlyToAdult = true;
			}
		} else if (roll == 8){ // Lost Generation
			this.isAsync = true;
		} else if (roll > 8){ // Uplift	
			this.isUplift = true; 
		}

		if (roll <= 3){
			fluff += "Almost human";
			if (this.startingMorph == null || this.goDirectlyToAdult){
				this.startingMorph = new Morph("Infomorph");
			}
			this.pathForward = AdultPath.AdultFocus.Civilian;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.InfolifeHumanitiesAGI, pp), fluff);
		} else if (roll <= 5){
			fluff += "More machine than man";
			if (this.startingMorph == null || this.goDirectlyToAdult){
				this.startingMorph = new Morph("Infomorph");
			}
			this.pathForward = AdultPath.AdultFocus.Techie;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.InfolifeMachineAGI, pp), fluff);
		} else if (roll <= 7){
			fluff += "Created by and for science";
			if (this.startingMorph == null || this.goDirectlyToAdult){
				this.startingMorph = new Morph("Infomorph");
			}
			this.pathForward = AdultPath.AdultFocus.Scientist;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.InfolifeResearchAGI, pp), fluff);
		} else if (roll == 8) {
			fluff += "An experiment gone horribly wrong";
			this.goDirectlyToAdult = true;
			this.startingMorph = new Morph("Futura");
			
			this.pathForward = AdultPath.AdultFocus.New;
			if (d10.Roll() < 6){
				return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.LostDisturbedChild, pp), fluff);
			} else {
				return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.LostMaskedNormalcy, pp), fluff);
			}
		} else if (roll == 9) {
			fluff += "Second-class citizenship was not for you";
			this.startingMorph = LifePathCharts.getUplift();
			this.pathForward = AdultPath.AdultFocus.Civilian;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.UpliftEscapee, pp), fluff);
		} else {
			fluff += "Living proof that uplift works";
			this.startingMorph = LifePathCharts.getUplift();
			this.pathForward = AdultPath.AdultFocus.Civilian;
			return new FluffPackage(new BackgroundPackage(BackgroundPackage.List.UpliftStandardSpecimen, pp), fluff);
		}

	}



	/**
	 * Gets a roll that is valid from the allowedBackgrounds list
	 * @return
	 */
	private int getCreatedRoll() {
		Die d10 = new Die(10);
		if (this.allowedBackgrounds.contains("Created"))
			return d10.Roll(); // all subtypes OK
		
		boolean agi = false, uplift = false, lost = false;
		int result;
		if (this.allowedBackgrounds.contains("   AGI") )
			 agi = true;
		if (this.allowedBackgrounds.contains("   Uplift") )
			uplift = true;
		if (this.allowedBackgrounds.contains("   Lost Generation"))
			lost = true;

		do {
			result = d10.Roll();
		} while (! ((agi && result < 8) || (lost && result == 8) || (uplift && result > 8)));
		return result;
	}


	@Override
	public String toString() {
		String fs = "", ps = "";
		for (FluffPackage f : this.packages){
			fs += "     " + f.fluff + "\n";
			ps +=  ", " + f.p;
		}
		ps = ps.substring(2); // remove first ','
		return this.youthType + "\n" + fs.substring(0, fs.length()-1); // + "Packages: " + ps;
	}


	/**
	 * Getter for the package list
	 * @return array list of packages
	 */
	public ArrayList<LifePathPackage> getPackages() {
		ArrayList<LifePathPackage> p = new ArrayList<LifePathPackage>();
		for (FluffPackage f : this.packages){
			p.add(f.p);
		}
		return p;
	}


}
