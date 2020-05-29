package bike;

/**
 * Abstract class representing a bike.
 * Was implemented in this fashion in order to facilitate bike creation using factory pattern. Like this creation of new bike types does not imply change in bike class.
 * Each bike, regardless of type, contains an unique id number
 */
public abstract class Bike {
	
	private int id;
	private static int last_id;
	
	/**
	 *  Constructor for Bike class. Has no parameters and counts ids starting from zero
	 */
	public Bike() {
		this.id = Bike.last_id + 1;
		Bike.last_id += 1;
	}
	
	public int getID() {
		return this.id;
	}
	
}
