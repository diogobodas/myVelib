package user;

import system.GPS;
import system.VelibSystem;
import bike.Bike;
import exceptions.IrregularCardException;
import exceptions.IrregularUserException;
import exceptions.UnavailableBikeException;
import exceptions.UnavailableSlotException;
import station.ParkingSlot;
import station.SlotStatus;
import station.Station;
import station.Terminal;
import java.time.LocalDateTime;
import java.util.Random;

public class User {

	private int id;
	private GPS location;
	private String creditCard;
	private Card registrationCard;
	private Payment payment_mode;
	private Bike bike;
	private UserBalance usrBalance;
	private String name = "Unnamed";
	
	
	public User(int id, GPS location, String card_number) {
		this.id = id;
		this.location = location;
		this.creditCard = card_number; // change later if putting card number
		this.registrationCard = null;
		this.bike = null;
		this.payment_mode = null;
		this.usrBalance = new UserBalance(this);
	}
	
	public User(int id, GPS location, String card_number,Card registrationCard) {
		this.id = id;
		this.location = location;
		this.creditCard = card_number; // change later if putting card number
		this.registrationCard = registrationCard;
		this.bike = null;
		this.payment_mode = null;
		this.usrBalance = new UserBalance(this);
	}
	/**
	 * Used in CLUI addUser
	 * @param name
	 * @param id
	 * @param registrationCard
	 */
	public User(String name,int id,Card registrationCard) {
		this.id = id;
		Random rand = new Random();
		double s = VelibSystem.getSquareLength();
		this.location = new GPS(s*rand.nextDouble(),s*rand.nextDouble());
		this.creditCard = String.valueOf(id); // change later if putting card number
		this.registrationCard = registrationCard;
		this.bike = null;
		this.payment_mode = null;
		this.usrBalance = new UserBalance(this);
		this.name = name;
	}

	public UserBalance getUsrBalance() {
		return usrBalance;
	}

	public void setUsrBalance(UserBalance usrBalance) {
		this.usrBalance = usrBalance;
	}

	// Rents a bike through the station's terminal
	public void rentBike(Station station, Class <?> bikeType, LocalDateTime time) {
		if (!station.isOn_service()) {
			System.out.println("Cannot use this station, it is offline");
			// add custom exception here later?
			return;
		}
		if (!station.hasDesiredBike(bikeType)) {
			System.out.println("The bike is not available in this station");
			// add custom exception here later?
			return;
		}
		Terminal terminal = station.getTerminal();	
		try {
			terminal.identifyUser(this);
			terminal.releaseBike(this,bikeType, time);
		} catch (UnavailableBikeException e) {
			System.out.println(e);
		} catch (IrregularUserException e) {
			System.out.println(e);
		} catch (IrregularCardException e) {
			System.out.println(e);
		}
	}
	
	public void dropBike(Station station, LocalDateTime time) {
		
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
		try {
			freeSlot.receiveBike(this.bike, time);
			this.bike = null;
		} catch (UnavailableBikeException e) {
			System.out.println(e);
		} catch (UnavailableSlotException e) {
			System.out.println(e);
		}
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
		this.usrBalance.setCard(registrationCard);
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
	
	public String toString() {
		String result = "Id: " + String.valueOf(this.id);
		result += " Name: " + String.valueOf(this.name);
		result += " RegistrationCard: ";
		if (this.registrationCard == null)
			result += "None";
		else if (this.registrationCard instanceof Vmax )
			result += "vmax";
		else
			result += "vlibre";
		result += " Bikeholder: ";
		if (this.bike == null)
			result += "no";
		else
			result += "yes";
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
