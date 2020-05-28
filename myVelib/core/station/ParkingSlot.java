package station;
import java.time.Duration;
import java.time.LocalDateTime;

import bike.Bike;
import exceptions.UnavailableBikeException;
import user.User;
import system.VelibSystem;
import user.Payment;

public class ParkingSlot {
	// attributes 
	private int id;
	private SlotStatus status;
	private Bike bike;
	
	//constructors
	public ParkingSlot(int id) {
		this.id = id;
		this.status = SlotStatus.FREE;
	}
	
	public ParkingSlot(int id, Bike bike) {
		this.id = id;
		this.status = SlotStatus.OCCUPIED;
		this.bike = bike;
	}
	
	public void receiveBike(Bike bike, LocalDateTime time) {
		if (this.status == SlotStatus.FREE) {
			User usr = VelibSystem.getUserByBike(bike);
			Station station = VelibSystem.getStationBySlot(this);
			if (usr != null) {
				double moneyValue = usr.getPaymentMode().getValue(time); // get payment value
				long timeValue = usr.getPaymentMode().getTimeDiscount(time); // get time credit to discount
				long bikeTime = Duration.between(usr.getPaymentMode().getStartTime(), time).toMinutes(); // get bike use time
				station.chargeUser(usr, moneyValue, timeValue); // charges user money and time credit
				usr.setPaymentMode(null); // changes user payment because bike is returned
				this.setBike(bike); // returns bike
				this.setStatus(SlotStatus.OCCUPIED);
				usr.getUsrBalance().updateBalance(bikeTime); // updates user statistics
				station.getBalance().updateBalance(this, time); // update station statistics
			} else {
				System.out.println("Bike does not belong to any user");
				// Insert custom exception here later
			}
		} else {
			System.out.println("Slot not Free");
			// insert custom exception here later
		}
	}
	
	public void releaseBike(User usr, LocalDateTime time) throws UnavailableBikeException {
		if (this.status == SlotStatus.OCCUPIED) {
			usr.setPaymentMode(Payment.createAdequatePayment(usr, this.bike, time)); // establishes payment regime for user
			usr.setBike(this.bike); // gives bike
			this.setBike(null);
			this.status = SlotStatus.FREE; // frees slot
			// Now, we update station statistics
			Station station = VelibSystem.getStationBySlot(this);
			station.getBalance().updateBalance(this, time);
			
		} else {
			throw new UnavailableBikeException("Slot empty or out-of-order");
		}
	}
	
	// getters / setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SlotStatus getStatus() {
		return status;
	}
	public void setStatus(SlotStatus status) {
		this.status = status;
	}

	public Bike getBike() {
		return bike;
	}

	public void setBike(Bike bike) {
		this.bike = bike;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParkingSlot other = (ParkingSlot) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
