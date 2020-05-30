package station;
import java.time.LocalDateTime;
import java.util.ArrayList;

import bike.Bike;
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
	private StationBalance balance;
	private ArrayList<LocalDateTime> intervalsOutOfOrder = new ArrayList<LocalDateTime>();
	
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
	
	public void chargeUser(User usr, double money, long time_credit) {
		usr.getUsrBalance().addCharge(money);
		if (usr.getRegistrationCard() != null)
			usr.getRegistrationCard().takeCredit(time_credit);
	}
	

	public void online(LocalDateTime time) {
		if (this.intervalsOutOfOrder.size() % 2 == 1) {
			this.intervalsOutOfOrder.add(time);
			for (ParkingSlot slot:slots)
				slot.setStatus(SlotStatus.OUT_OF_ORDER);
		}
		else
			System.out.println("Station is already online");
	}
	
	public void offline(LocalDateTime time) {
		if (this.intervalsOutOfOrder.size() % 2 == 0) {
			this.intervalsOutOfOrder.add(time);
			for (ParkingSlot slot:slots) {
				if (slot.getBike() == null)
					slot.setStatus(SlotStatus.FREE);
				else
					slot.setStatus(SlotStatus.OCCUPIED);
			}
		}
		else
			System.out.println("Station is already offline");
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

	public ArrayList<LocalDateTime> getIntervalsOutOfOrder() {
		return intervalsOutOfOrder;
	}

	public void setIntervalsOutOfOrder(ArrayList<LocalDateTime> intervalsOutOfOrder) {
		this.intervalsOutOfOrder = intervalsOutOfOrder;
	}
	
	
}
	
