package lifepath;

import java.util.List;

class ChartEntry<T> {

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
	public ChartEntry(T label, Integer rollMin, Integer rollMax) {
		super();
		this.object = label;
		this.rollMin = rollMin;
		this.rollMax = rollMax;
	}
	public ChartEntry(Integer rollMin, Integer rollMax, T label) {
		super();
		this.object = label;
		this.rollMin = rollMin;
		this.rollMax = rollMax;
	}
	public ChartEntry(Integer roll, T label) {
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
	
	/**
	 * Gets a result given a roll. Loops through the array and compares the roll 
	 * result to the min and max of the chart entry
	 * @param chart The chart to loop through
	 * @param roll The roll (1-100)
	 * @return The chart entry object
	 */
	static <T> T findResult(ChartEntry<T>[] chart, Integer roll){
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
	
	/**
	 * Gets a result given a roll. Loops through the array and compares the roll 
	 * result to the min and max of the chart entry
	 * @param chart The chart to loop through
	 * @param roll The roll (1-100)
	 * @return The chart entry object
	 */
	public static <T> T findResult(List<ChartEntry<T>> chart, Integer roll) {
		for (int i = 0; i < chart.size(); i++){
			if (chart.get(i).equals(roll)){
				return chart.get(i).object;
			}
		}
		return null;
	}
	

}
