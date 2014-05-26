package lifepath;

import java.util.ArrayList;

class VariableRollObject<T> {

	T object;
	Integer rollMin;
	Integer rollMax;
	
	/**
	 * Constructor. Value should be a string key, which can really be anything.
	 * Todo: Effects........
	 * @param label string of result of roll
	 * @param rollMin minimum label that must be rolled to achieve this result
	 * @param rollMax maximum label
	 */
	public VariableRollObject(T label, Integer rollMin, Integer rollMax) {
		super();
		this.object = label;
		this.rollMin = rollMin;
		this.rollMax = rollMax;
	}
	public VariableRollObject(Integer rollMin, Integer rollMax, T label) {
		super();
		this.object = label;
		this.rollMin = rollMin;
		this.rollMax = rollMax;
	}
	public VariableRollObject(Integer roll, T label) {
		super();
		this.object = label;
		this.rollMin = roll;
		this.rollMax = roll;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Integer || arg0.getClass() == int.class){
			if ((Integer)arg0 >= rollMin && (Integer)arg0 <= rollMax){
				return true;
			} else {
				return false;
			}
		} else {
			return super.equals(arg0);
		}
	}

	@Override
	public String toString() {
		return this.rollMin + "-" + this.rollMax + ": " + this.object.toString();
	}
	
	static <T> T findResult(VariableRollObject<T>[] chart, Integer roll){
		for (int i = 0; i < chart.length; i++){
			if (chart[i] == null){
				return null;
			}
			if (chart[i].equals(roll)){
				return chart[i].object;
			}
		}
		return null;		
	}
	public static <T> T findResult(ArrayList<VariableRollObject<T>> chart, Integer roll) {
		for (int i = 0; i < chart.size(); i++){
			if (chart.get(i).equals(roll)){
				return chart.get(i).object;
			}
		}
		return null;
	}
	
}
