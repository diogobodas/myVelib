package user;

import system.GPS;
import system.VelibSystem;
import bike.Bike;
import exceptions.IrregularCardException;
import exceptions.IrregularUserException;
import exceptions.UnavailableBikeException;
import exceptions.UnavailableSlotException;
import exceptions.UnavailableStationException;
import station.ParkingSlot;
import station.SlotStatus;
import station.Station;
import station.Terminal;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * 
 * Class representing the user of the myVelib Network. It has an ID, GPS coordinates, a credit card number, and a registration card.
 * Other attributes were created in order to support system functions such as Payment for correctly handling payment regimes and UserBalance for handling statistics.
 */
public class User {

	private int id;
	private GPS location;
	private String creditCard;
	private Card registrationCard;
	private Payment payment_mode;
	private Bike bike;
	private UserBalance usrBalance;
	private String name = "Unnamed";
	
	/**
	 * Initializes an user through an ID a location and a card number. Used mainly in testing
	 * @param id Integer for numerical ID of the user
	 * @param location GPS object containing the position of the user in the map
	 * @param card_number String with the card number
	 */
	public User(int id, GPS location, String card_number) {
		this.id = id;
		this.location = location;
		this.creditCard = card_number; // change later if putting card number
		this.registrationCard = null;
		this.bike = null;
		this.payment_mode = null;
		this.usrBalance = new UserBalance(this);
	}
	
	/**
	 * Same as {@link #User(int, GPS, String)} but with the addition of a Card object for registration card
	 * @param id Integer for numerical ID of the user
	 * @param location GPS object containing the position of the user in the map
	 * @param card_number String with the card number
	 * @param registrationCard Card object with the user registration card
	 */
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
	 * User constructor used in the CLUI addUser. It gives also a name to the user and initializes his credit card as his ID turned to string
	 * @param name String with the user's name
	 * @param id Integer for numerical ID
	 * @param registrationCard Card object with the registration card. Can be provided as null for users without card
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

	/**
	 * Method for renting a bike through a station terminal
	 * @param station Station instance where the user wishes to rent the bike
	 * @param bikeType Class object having the desired bike type
	 * @param time LocalDateTime of rental start
	 * @throws UnavailableBikeException Thrown by {@link station.Terminal#releaseBike(User, Class, LocalDateTime)}
	 * @throws IrregularUserException Thrown by {@link station.Terminal#identifyUser(User)}
	 * @throws IrregularCardException Thrown by {@link station.Terminal#identifyUser(User)}
	 * @throws UnavailableStationException when station is offline
	 */
	public void rentBike(Station station, Class <?> bikeType, LocalDateTime time) throws UnavailableBikeException, IrregularUserException, IrregularCardException, UnavailableStationException {
		if (!station.isOn_service()) {
			throw new UnavailableStationException("Station is offline, cannot rent bike here");
		}
		if (!station.hasDesiredBike(bikeType)) {
			throw new UnavailableBikeException("Station does not have the desired bike");
		}
		Terminal terminal = station.getTerminal();	
		terminal.identifyUser(this);
		terminal.releaseBike(this,bikeType, time);
	}
	
	/**
	 * Method for returning a bike to a station
	 * @param station Station in which the bike is trying to be returned
	 * @param time LocalDateTime of bike return
	 * @throws UnavailableBikeException Thrown by {@link station.ParkingSlot#receiveBike(Bike, LocalDateTime)}
	 * @throws UnavailableSlotException Thrown by {@link station.ParkingSlot#receiveBike(Bike, LocalDateTime)} or when station has no free slots.
	 */
	public void dropBike(Station station, LocalDateTime time) throws UnavailableBikeException, UnavailableSlotException{
		if (station.hasFreeSlot() == false) {
			throw new UnavailableSlotException("Station has no free parking slot");
		}
		ParkingSlot freeSlot = null;
		for (ParkingSlot slot: station.getSlots()) {
			if (slot.getStatus() == SlotStatus.FREE) {
				freeSlot = slot;
				break;
			}	
		}
		freeSlot.receiveBike(this.bike, time);
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
