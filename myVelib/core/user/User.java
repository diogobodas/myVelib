package user;

import system.GPS;
import bike.Bike;
import exceptions.IrregularCardException;
import exceptions.IrregularUserException;
import exceptions.UnavailableBikeException;
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
	
	
	public User(int id, GPS location, String card_number) {
		this.id = id;
		this.location = location;
		this.creditCard = card_number; // change later if putting card number
		this.registrationCard = null;
		this.bike = null;
		this.payment_mode = null;
	}

	// Rents a bike through the station's terminal
	void rentBike(Station station, int slot_id) {
		Terminal terminal = station.getTerminal();
		try {
			terminal.identifyUser(this);
			terminal.releaseBike(this, slot_id);
		} catch (UnavailableBikeException e) {
			System.out.println(e);
		} catch (IrregularUserException e) {
			System.out.println(e);
		} catch (IrregularCardException e) {
			System.out.println(e);
		}
	}
	
	void dropBike(Station station) {
		
		//checks if there is an available slot for the drop off
		// insert exception here later? 
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User u = (User) obj;
			return u.getID() == this.id;
		}
		return false;
	}

	public int getID() {
		return id;
	}


	public void setID(int id) {
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
	
}
