package user;

/**
 * Extension of the card abstract class of Vlibre cards. It holds only the time credit for the card
 *
 */
public class Vlibre extends Card{

	/**
	 * Initializes Vlibre card without credit
	 */
	public Vlibre() {
		super();
	}
	
	/**
	 * Initializes Vlibre card with given time credit
	 * @param time_credit Integer representing the number of minutes of time credit available
	 */
	public Vlibre(long time_credit) {
		super(time_credit);
	}
	
}
