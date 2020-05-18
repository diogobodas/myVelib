package station;
import bike.Bike;
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
				VelibSystem.chargeUserMoney(usr, usr.getPayment().getValue());
				usr.setPayment(null);
				this.setBike(bike);
				this.setStatus(SlotStatus.OCCUPIED);
			} else {
				System.out.println("Bike does not belong to any user");
				// Insert custom exception here later
			}
		} else {
			System.out.println("Slot not Free");
			// insert custom exception here later
		}
	}
	
	public void releaseBike(User usr) {
		if (this.status == SlotStatus.OCCUPIED) {
			usr.setPayment(Payment.createAdequatePayment(usr, this.bike)); // establishes payment regime for user
			usr.setBike(this.bike); // gives bike
			this.status = SlotStatus.FREE; // frees slot
		} else {
			System.out.println("Slot has no bike");
			// insert custom exception here later
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
