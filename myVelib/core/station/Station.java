package station;
import bike.Bike;
import system.GPS;
import user.User;

public class Station {
	// attributes
	private int id;
	private boolean on_service;
	private GPS coordinates;
	private Terminal terminal;
	private ParkingSlot[] slots;
	private StationBalance balance;
	
	// constructor
	public Station(int id_num, boolean online, GPS coord, int number_of_slots) {
		id = id_num;
		on_service = online;
		coordinates = coord;
		terminal = null;
		SlotCreator slot_factory = new SlotCreator();
		slots = slot_factory.fillSlots(id_num,number_of_slots);
		balance = new StationBalance(this);
	}
	
	public Station(int id_num, boolean online, GPS coord, Terminal t, int number_of_slots) {
		id = id_num;
		on_service = online;
		coordinates = coord;
		terminal = t;
		SlotCreator slot_factory = new SlotCreator();
		slots = slot_factory.fillSlots(id_num,number_of_slots);
		balance = new StationBalance(this);
	}

	public boolean hasDesiredBike(Class<?> bikeType) {
		for (int i = 0; i < this.slots.length; i++) {
			if (slots[i].getBike() != null)
				if (slots[i].getBike().getClass() == bikeType)
					return true;
		}
		return false;
	}
	
	public boolean hasFreeSlot() {
		for (int i = 0; i < this.slots.length; i++) {
			if (slots[i].getBike() == null)
				return true;
		}
		return false;	
	}
	
	public Bike getBikeByID(Bike b) {
		for (ParkingSlot p : this.slots) {
			if (p.getBike().getID() == b.getID())
				return p.getBike();
		}
		return null;
	}
	
	public void chargeUser(User usr, double money, long time_credit) {
		usr.getUsrBalance().addCharge(money);
		if (usr.getRegistrationCard() != null)
			usr.getRegistrationCard().takeCredit(time_credit);
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
