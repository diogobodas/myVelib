package user;

public class UserBalance {
	
	private int rideNumber;
	private double totalBikeTime;
	private double totalCharge;
	private double timeCredit;
	
	UserBalance(User usr) {
		rideNumber = 0;
		totalBikeTime = 0;
		totalCharge = 0;
		if (usr.getRegistrationCard() == null)
			timeCredit = 0;
		else 
			timeCredit = usr.getRegistrationCard().getCredit();
	}
	
	public void updateBalance(double bikeTime, double charge,double timeCreditDiscount) {
		rideNumber += 1;
		totalBikeTime += bikeTime;
		totalCharge += charge;
		if (timeCredit < timeCreditDiscount)
			timeCredit = 0;
		else
			timeCredit -= timeCreditDiscount;
	}
	
	public void showBalance() {
		this.toString();
	}
	
	@Override
	public String toString() {
		return "UserBalance [rideNumber=" + rideNumber + ", totalBikeTime=" + totalBikeTime + ", totalCharge="
				+ totalCharge + ", timeCredit=" + timeCredit + "]";
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
	public void setTotalBikeTime(double totalBikeTime) {
		this.totalBikeTime = totalBikeTime;
	}
	public double getTotalCharge() {
		return totalCharge;
	}
	public void setTotalCharge(double totalCharge) {
		this.totalCharge = totalCharge;
	}
	public double getTimeCredit() {
		return timeCredit;
	}
	public void setTimeCredit(double timeCredit) {
		this.timeCredit = timeCredit;
	}
	
	

}
