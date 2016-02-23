package dice;

import java.lang.Math;

/****
 * Class for a single die. Dice should have a side that does not 
 * change once the die is created. The default is 6.
 * 
 * @author terrasaur
 *
 */
public class Die {
	private Integer sides;            // Number of sides for the die
	private boolean isExploding;      // If a certain value is rolled, reroll
	private Integer explodeThreshold; // threshold for explode
	private Integer roll;             // Tracks current roll
	
	/** Default constructor */
	public Die() {
		super();
		this.sides = 6;
		this.isExploding = false;
		this.explodeThreshold = 0;
		this.roll = 0;
	}
	
	
	public Die(int sides) {
		super();
		this.sides = sides;
		this.isExploding = false;
		this.explodeThreshold = 0;
		this.roll = 0;
	}
	
	public Die(int sides, boolean isExploding, int explodeThreshold) {
		super();
		this.sides = sides;
		this.isExploding = isExploding;
		this.explodeThreshold = explodeThreshold;
		this.roll = 0;
	}
	
	/***
	 * Getter for number of sides
	 * @return number of sides
	 */
	public int Sides(){
		return sides;
	}
	
	/****
	 * Internal method for getting a single random integer in the 
	 * range 1 to numSides 
	 * @return random number
	 */
	private int getRandInt(){
		return 1 + (int)(Math.random() * ((this.sides - 1) + 1));
	}
	
	/**
	 * Rolls the die once.
	 * @return
	 */
	public Integer Roll(){
		int tempRoll =  getRandInt();
		this.roll = tempRoll;
		while (this.isExploding && tempRoll >= explodeThreshold){
			tempRoll = getRandInt();
			this.roll += tempRoll;
		}
		return this.roll;
	}
	
	/**
	 * @return current roll value
	 */
	public Integer getRoll(){
		return this.roll;
	}
	
	public static int getRandIdx(int size) {
		return (int)(Math.random() * ((size - 1) + 1));
	}

}
