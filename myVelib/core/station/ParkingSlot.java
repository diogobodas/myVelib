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
				VelibSystem.chargeUserMoney(usr, usr.getPaymentMode().getValue(LocalDateTime.now()));
				VelibSystem.chargeUserTime(usr, usr.getPaymentMode().getTimeDiscount(LocalDateTime.now()));
				usr.setPaymentMode(null);
				this.setBike(bike);
				this.setStatus(SlotStatus.OCCUPIED);
				VelibSystem.addUserTime(usr, bike);
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
}
