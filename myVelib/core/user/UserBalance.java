package user;

public class UserBalance {
	
	private int rideNumber;
	private long totalBikeTime;
	private double totalCharge;
	private Card card;
	
	public UserBalance(User usr) {
		rideNumber = 0;
		totalBikeTime = 0;
		totalCharge = 0;
		card = usr.getRegistrationCard();
	}
	
	public void updateBalance(long bikeTime, double charge) {
		rideNumber += 1;
		totalBikeTime += bikeTime;
		totalCharge += charge;
	}
	
	public void showBalance() {
		this.toString();
	}
	
	@Override
	public String toString() {
		String s =  "UserBalance [rideNumber=" + rideNumber + ", totalBikeTime=" + totalBikeTime + ", totalCharge="
				+ totalCharge;
		if (card != null)
			s += " timeCredit=" + card.getCredit();
		s += "]";
		return s;
	}

	public int getRideNumber() {
		return rideNumber;
	}
	public void setRideNumber(int rideNumber) {
		this.rideNumber = rideNumber;
	}
	public double getTotalBikeTime() {
		return totalBikeTime;
	}
	public void setTotalBikeTime(long totalBikeTime) {
		this.totalBikeTime = totalBikeTime;
	}
	public double getTotalCharge() {
		return totalCharge;
	}
	public void setTotalCharge(double totalCharge) {
		this.totalCharge = totalCharge;
	}
}
