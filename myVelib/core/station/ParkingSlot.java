package station;
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
	
	public void receiveBike(Bike bike) {
		if (this.status == SlotStatus.FREE) {
			User usr = VelibSystem.getUserByBike(bike);
			if (usr != null) {
				double moneyValue = usr.getPaymentMode().getValue(LocalDateTime.now());
				double timeValue = usr.getPaymentMode().getTimeDiscount(LocalDateTime.now());
				double timeCreditDiscount = 0; //CHANGE THIS
				VelibSystem.chargeUserMoney(usr, moneyValue);
				VelibSystem.chargeUserTime(usr, timeValue);
				usr.setPaymentMode(null);
				this.setBike(bike);
				this.setStatus(SlotStatus.OCCUPIED);
				usr.getUsrBalance().updateBalance(timeValue, moneyValue, timeCreditDiscount);
			} else {
				System.out.println("Bike does not belong to any user");
				// Insert custom exception here later
			}
		} else {
			System.out.println("Slot not Free");
			// insert custom exception here later
		}
	}
	
	public void releaseBike(User usr) throws UnavailableBikeException {
		if (this.status == SlotStatus.OCCUPIED) {
			usr.setPaymentMode(Payment.createAdequatePayment(usr, this.bike)); // establishes payment regime for user
			usr.setBike(this.bike); // gives bike
			this.setBike(null);
			this.status = SlotStatus.FREE; // frees slot
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
