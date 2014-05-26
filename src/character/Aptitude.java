package character;

/**
 * Class that tracks character's attributes. It's not terribly huge.
 * 
 * @author terrasaur
 *
 */
class Aptitude{
	protected AptitudeType name;
	protected Integer base;
	protected Integer morphBonus;
	protected Integer total;
	protected Integer morphImplantBonus = 0;
	
	public Aptitude(String name, Integer base,
			Integer morphBonus, Integer total) {
		super();
		this.name = getAptitudeByName(name);
		this.base = base;
		this.morphBonus = morphBonus;
		this.total = total;
	}
	
	public Aptitude(String name) {
		super();
		this.name = getAptitudeByName(name);
		this.base = 0;
		this.morphBonus = 0;
		this.total = 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String){
			return ((String)obj == this.name.shortText);
		} else {
			return super.equals(obj);
		}
	}
	
	/**
	 * Enumerated values of all aptitudes
	 * @author terrasaur
	 *
	 */
	public enum AptitudeType{
		Cognition   ("COG"),
		Coordination("COO"),
		Intelligence("INT"),
		Reflexes    ("REF"),
		Savvy       ("SAV"),
		Somatics    ("SOM"),
		Willpower   ("WIL");
		
		protected String shortText;
		private AptitudeType(String s){
			this.shortText = s;
		}
	}
	
	public static AptitudeType getAptitudeByName(String s){
		for (AptitudeType a : AptitudeType.values()){
			if (a.shortText == s || a.name() == s)
				return a;
		}
		return null;
	}

	@Override
	public String toString() {
		return  name + ": " + base + " + " + morphBonus + " = " + total;
	}
	
	public void setBaseAptitude(Integer apt){
		this.base  = apt;
		this.total = this.base + this.morphBonus;
	}
	
	public void setMorphBonus(Integer apt, Integer bonus, Integer aptMax){
		this.morphBonus = apt;
		this.morphImplantBonus = bonus;
		if (this.base + this.morphBonus > aptMax)
			this.total = aptMax;
		else
			this.total = this.base + this.morphBonus;
	}

	
}
