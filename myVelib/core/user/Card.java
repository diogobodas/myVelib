package user;

public abstract class Card {
	
	private int id;
	private double timeCredit = 0;
	private static int countCards = 0;
	
	Card(double timeCredit) {
		this.id = countCards;
		countCards += 1;
		this.timeCredit = timeCredit;
	}
	
	//Creates a card with credit set as zero
	Card() {
		this.id = countCards;
		countCards += 1;
	}
	
	public double getCredit() {
		return timeCredit;
	}
	
	public void setCredit(double credit) {
		this.timeCredit = credit;
	}
	
	public int getId() {
		return id;
	}
	
}
