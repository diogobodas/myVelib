package user;

/**
 * 
 * Abstract class representing a registration card in the myVelib Network. It contains an unique ID and the time credit that the user can eventually use for discounts.
 *
 */
public abstract class Card {
	
	private int id;
	private long timeCredit = 0;
	private static int countCards = 0;
	
	/**
	 * Initializes a registration card with the given time credit
	 * @param timeCredit Integer representing the time credit in minutes
	 */
	public Card(long timeCredit) {
		this.id = countCards;
		countCards += 1;
		this.timeCredit = timeCredit;
	}
	
	/**
	 * Second version of constructor that automatically sets the time credit to zero. See {@link #Card(long)}
	 */
	public Card() {
		this.id = countCards;
		countCards += 1;
	}
	
	public double getCredit() {
		return timeCredit;
	}
	
	public void setCredit(long credit) {
		this.timeCredit = credit;
	}
	
	public void addCredit(long credit) {
		this.timeCredit += credit;
	}
	
	public void takeCredit(long credit) {
		this.timeCredit -= credit;
	}
	
	public int getID() {
		return id;
	}
	
}
