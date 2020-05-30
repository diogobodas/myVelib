package station;
import java.time.LocalDateTime;
import java.util.ArrayList;

import bike.Bike;
import exceptions.IrregularOperationException;
import system.GPS;
import user.User;


/**
 * Class representing station. It is a central part of the system, where slots are stored.
 * A terminal is available for the user to interact with the system. In addition, it holds a station balance,
 * to calculate the station statistics depending on input parameters ( a time window).
 * Finally, an array is used to save the times the whole station is offline.
 */
public class Station {
	// attributes
	private int id;
	private boolean on_service;
	private GPS coordinates;
	private Terminal terminal;
	private ParkingSlot[] slots;
	private SlotStatus[] slots_status; // used to remember previous status for slots when station is set offline
	private StationBalance balance;
	
	/**
	 * Initializes station without terminal through the use of {@link #station.SlotCreator} to assure correct filling of the slots.
	 * @param id_num ID of the station. Is an integer
	 * @param online Boolean variable representing if the station is online or not
	 * @param coord GPS coordinates of the station. It is a {@link #system.GPS} type
	 * @param number_of_slots Number of slots of this station. Is an integer.
	 */
	public Station(int id_num, boolean online, GPS coord, int number_of_slots) {
		id = id_num;
		on_service = online;
		coordinates = coord;
		terminal = null;
		SlotCreator slot_factory = new SlotCreator();
		slots = slot_factory.fillSlots(id_num,number_of_slots);
		balance = new StationBalance(this);
	}
	
	/**
	 * Initializes station with terminal through the use of {@link #station.SlotCreator} to assure correct filling of the slots.
	 * Only to be used in testing. The correct version used in the system initialization does not include the terminal because it needs a reference station for correct use.
	 * @param id_num ID of the station. Is an integer
	 * @param online Boolean variable representing if the station is online or not
	 * @param coord GPS coordinates of the station. It is a {@link #system.GPS} type
	 * @param number_of_slots Number of slots of this station. Is an integer.
	 */
	public Station(int id_num, boolean online, GPS coord, Terminal t, int number_of_slots) {
		id = id_num;
		on_service = online;
		coordinates = coord;
		terminal = t;
		SlotCreator slot_factory = new SlotCreator();
		slots = slot_factory.fillSlots(id_num,number_of_slots);
		balance = new StationBalance(this);
	}

	/**
	 * Method for checking if station has the bike type desired by the user
	 * @param bikeType Class object with the desired bike type
	 * @return Boolean that is true if it has the desired bike type, false if it hasn't
	 */
	public boolean hasDesiredBike(Class<?> bikeType) {
		for (int i = 0; i < this.slots.length; i++) {
			if (slots[i].getBike() != null)
				if (slots[i].getBike().getClass() == bikeType)
					return true;
		}
		return false;
	}
	
	/**
	 * Method for checking if the station possesses free slots
	 * @return Boolean that is true if it has a free slot, false if it has not
	 */
	public boolean hasFreeSlot() {
		for (int i = 0; i < this.slots.length; i++) {
			if (slots[i].getStatus() == SlotStatus.FREE)
				return true;
		}
		return false;	
	}
	
	/**
	 * Method for getting a Bike object from this station based on its ID. Should be used only to aid {@link #system.VelibSystem} methods
	 * @param id ID number of the bike being searched
	 * @return Bike object with desired id, if it is in this station
	 */
	public Bike getBikeByID(int id) {
		for (ParkingSlot p : this.slots) {
			if (p.getBike() != null) {
				if (p.getBike().getID() == id)
					return p.getBike();
			}
		}
		return null;
	}
	
	/**
	 * Charges user with a certain amount of money and discounts a certain time credit.
	 * @param usr User object to be charged
	 * @param money Double value containing the money value being charged
	 * @param time_credit Time credit being discounted from cumulated value
	 */
	public void chargeUser(User usr, double money, long time_credit) {
		usr.getUsrBalance().addCharge(money);
		if (usr.getRegistrationCard() != null)
			usr.getRegistrationCard().takeCredit(time_credit);
	}
	
	/**
	 * Used by the CLI to set the station online
	 * @param time Time when station is set online. Used for statistics
	 * @throws IrregularOperationException Throws this exception when someone tries to put online a station already online
	 */
	public void setStationOnline(LocalDateTime time) throws IrregularOperationException {
		if (this.on_service)
			throw new IrregularOperationException("Station is already online");
		for (int i = 0; i < slots.length; i++) {
			if (slots_status[i] != SlotStatus.OUT_OF_ORDER)
				slots[i].setSlotOnline(time);
		}
		slots_status = null;
		this.on_service = true;
	}
	
	/**
	 * Used by the CLI to set the station offline
	 * @param time Time when station is set offline. Used for statistics
	 * @throws IrregularOperationException Throws this exception when someone tries to put offline a station already offline
	 */
	public void setStationOffline(LocalDateTime time) throws IrregularOperationException {
		if (!this.on_service)
			throw new IrregularOperationException("Station is already offline");
		slots_status = new SlotStatus[slots.length];
		for(int i = 0; i < slots.length; i++) {
			slots_status[i] = slots[i].getStatus();
			if (slots[i].getStatus() != SlotStatus.OUT_OF_ORDER)
				slots[i].setSlotOffline(time);
		}
		this.on_service = false;
	}

	// getters / setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isOn_service() {
		return on_service;
	}

	public void setOn_service(boolean on_service) {
		this.on_service = on_service;
	}

	public GPS getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(GPS coordinates) {
		this.coordinates = coordinates;
	}

	public ParkingSlot[] getSlots() {
		return slots;
	}

	public void setSlots(ParkingSlot[] slots) {
		this.slots = slots;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public StationBalance getBalance() {
		return balance;
	}

	public void setBalance(StationBalance balance) {
		this.balance = balance;
	}
	
	
}
	
