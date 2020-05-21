package user;

public abstract class Card {
	
	private int id;
	private long timeCredit = 0;
	private static int countCards = 0;
	
	public Card(long timeCredit) {
		this.id = countCards;
		countCards += 1;
		this.timeCredit = timeCredit;
	}
	
	//Creates a card with credit set as zero
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
	
	public int getID() {
		return id;
	}
	
}
