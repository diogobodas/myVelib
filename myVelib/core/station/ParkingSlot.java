package station;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import bike.Bike;
import exceptions.IrregularOperationException;
import exceptions.UnavailableBikeException;
import exceptions.UnavailableSlotException;
import user.User;
import system.VelibSystem;
import user.Payment;

/**
 * Class representing station parking slot. It is the end point responsible for releasing and receiving bikes.
 * For this, it uses methods receiveBike and releaseBike
 *
 */
public class ParkingSlot {
	// attributes 
	private int id;
	private SlotStatus status;
	private Bike bike;
	boolean startedFree; //aux for stationBalance 
	private ArrayList<LocalDateTime> intervalsOutOfOrder = new ArrayList<LocalDateTime>(); // also used for statistics
	
	/**
	 * Initializes a free slot with unique id.
	 * @param id Parking slot id number
	 */
	public ParkingSlot(int id) {
		this.id = id;
		this.status = SlotStatus.FREE;
		startedFree = true;
	}
	
	/**
	 * Initializes an occupied slot with unique id and bike.
	 * @param id Slot id number
	 * @param bike Bike in slot
	 */
	public ParkingSlot(int id, Bike bike) {
		this.id = id;
		this.status = SlotStatus.OCCUPIED;
		this.bike = bike;
		startedFree = false;
		//station.getBalance().initBikeOcuppation(this);
	}
	/**
	 * toString to print most important features of a slot
	 */
	public String toString() {
		String result = "Slot ID: " + String.valueOf(this.id);
		if (this.status == SlotStatus.OCCUPIED) 
			result += " Status: Occupied ";
		else if (this.status == SlotStatus.FREE) 
			result += " Status : Free ";
		else 
			result += " Status : Out of Order ";
		return result;
	}
	
	/**
	 * Method for receiving bike from user at a given time. This method should be called by the user when returning the bike.
	 * It will automatically call other functions for charging adequate payment and give time credit, and also updates VelibSystem statistics 
	 * @param bike Bike being returned by the user. It must be given as a usr.getBike(), otherwise it will raise an exception
	 * @param time Time of bike return. Must be a java.time.LocalDateTime value
	 * @throws UnavailableBikeException Exception thrown when the bike being returned is not registered under any user and is thus in fact unavailable
	 * @throws UnavailableSlotException Exception thrown when slot is not free. Should not be thrown here in normal usage. Was added for tests before implementation of a few classes
	 */
	public void receiveBike(Bike bike, LocalDateTime time) throws UnavailableBikeException, UnavailableSlotException{
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
				station.getBalance().updateBalance(this, time,true); // update station statistics
			} else {
				throw new UnavailableBikeException("The bike being returned is not registered under any user right now");
			}
		} else {
			throw new UnavailableSlotException("Slot is not available");
		}
	}
	
	/**
	 * Method for releasing bike on command from terminal. It will attribute a payment regime to the user renting the bike, change slot status and user bike and update system statistics.
	 * @param usr User renting the bike. Should be informed by the station terminal after identification
	 * @param time Time of bike rent. Should also be a java.time.LocalDateTime value
	 * @throws UnavailableBikeException Exception thrown when there is no bike in this slot or when it is out of order. Should not be thrown here in normal usage. Was added for tests before implementation of a few classes
	 */
	public void releaseBike(User usr, LocalDateTime time) throws UnavailableBikeException {
		if (this.status == SlotStatus.OCCUPIED) {
			usr.setPaymentMode(Payment.createAdequatePayment(usr, this.bike, time)); // establishes payment regime for user
			usr.setBike(this.bike); // gives bike
			this.setBike(null);
			this.status = SlotStatus.FREE; // frees slot
			// Now, we update station statistics
			Station station = VelibSystem.getStationBySlot(this);
			station.getBalance().updateBalance(this, time,false);
			
		} else {
			throw new UnavailableBikeException("Slot empty or out-of-order");
		}
	}
	
	/**
	 * Method for setting the slot as out of order. Implemented separately from setStatus because a few modifications for the calculation of statistics were needed 
	 * @param time LocalDateTime of when the station was set off.
	 * @throws IrregularOperationException Exception thrown when the slot is already out of order before the command
	 */
	public void setSlotOffline(LocalDateTime time) throws IrregularOperationException {
		if (this.status == SlotStatus.OUT_OF_ORDER)
			throw new IrregularOperationException("Slot is already out-of-order");
		this.intervalsOutOfOrder.add(time);
		this.status = SlotStatus.OUT_OF_ORDER;
	}
	
	/**
	 * Method for putting a slot back working. Implemented separately from setStatus because a few modifications for the calculation of statistics were needed
	 * @param time LocalDateTime of when the station was set on.
	 * @throws IrregularOperationException thrown when the slot is already functional before the command
	 */
	public void setSlotOnline(LocalDateTime time) throws IrregularOperationException {
		if (this.status != SlotStatus.OUT_OF_ORDER)
			throw new IrregularOperationException("Slot is already functional");
		this.intervalsOutOfOrder.add(time);
		if (this.bike == null) {
			this.status = SlotStatus.FREE;
		} else {
			this.status = SlotStatus.OCCUPIED;
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
	
	public ArrayList<LocalDateTime> getIntervalsOutOfOrder() {
		return intervalsOutOfOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * Equality condition for two ParkingSlot objects. Only true if they possess the same id
	 */
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

	public boolean isStartedFree() {
		return startedFree;
	}

	
	
	
}
