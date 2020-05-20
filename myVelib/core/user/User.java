package user;

import system.GPS;
import bike.Bike;
import station.ParkingSlot;
import station.SlotStatus;
import station.Station;
import station.Terminal;

public class User {

	private int id;
	private GPS location;
	private String creditCard;
	private Card registrationCard;
	private Payment payment_mode;
	private Bike bike;
	
	
	public User(int id,GPS location,Card registrationCard) {
		this.id = id;
		this.location = location;
		this.creditCard = ( (Integer) id).toString(); // change later if putting card number
		this.registrationCard = registrationCard;
		this.bike = null;
		this.payment_mode = null;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public GPS getLocation() {
		return location;
	}


	public void setLocation(GPS location) {
		this.location = location;
	}


	public String getCreditCard() {
		return creditCard;
	}


	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}


	public Card getRegistrationCard() {
		return registrationCard;
	}


	public void setRegistrationCard(Card registrationCard) {
		this.registrationCard = registrationCard;
	}


	public Payment getPaymentMode() {
		return payment_mode;
	}


	public void setPaymentMode(Payment payment_mode) {
		this.payment_mode = payment_mode;
	}


	public Bike getBike() {
		return bike;
	}


	public void setBike(Bike bike) {
		this.bike = bike;
	}
	
	// Rents a bike through the station's terminal
	void rentBike(Class <?> bikeType,Station station) {
		
		//checks if there is an available bike of the desired type 
		//if (station.hasDesiredBike(bikeType) == false) {
		//	System.out.println("Sorry, the desired bike is not available in the specified station");
		//	return;
		//}
		
		Terminal terminal = station.getTerminal();
		this.bike = terminal.releaseBike(bikeType);
	}
	
	void dropBike(Station station) {
		
		//checks if there is an available slot for the drop off
		if (station.hasFreeSlot() == false) {
			System.out.println("Sorry, there are no free slots to drop off your bike in this station");
			return;
		}
		
		ParkingSlot freeSlot = null;
		
		//finds free slot
		for (ParkingSlot slot: station.getSlots()) {
			if (slot.getStatus() == SlotStatus.FREE) {
				freeSlot = slot;
				break;
			}	
		}
		
		// drops off bike
		freeSlot.receiveBike(this.bike);
		this.bike = null;
	}
	
}
