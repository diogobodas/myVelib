package user;

/**
 * Extension of the Card abstract class representing the Vmax card. It is by choice capable of holding minutes even though they are not used in the Vmax payment regime.
 *
 */
public class Vmax extends Card{

	/**
	 * Initializes Vmax card without any time credit
	 */
	public Vmax() {
		super();
	}
	
	/**
	 * Initializes Vmax card with the time credit given
	 * @param time_credit Integer with the time credit in minutes for the card
	 */
	public Vmax(long time_credit) {
		super(time_credit);
	}
	
}