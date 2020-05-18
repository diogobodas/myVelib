package station;
import system.GPS;

public class Station {
	// attributes
	private int id;
	private boolean on_service;
	private GPS coordinates;
	private ParkingSlot[] slots;
	private Terminal terminal;
	
	// constructor
	public Station(int id_num, boolean online, GPS coord, Terminal t, int number_of_slots) {
		id = id_num;
		on_service = online;
		coordinates = coord;
		terminal = t;
		slots = new ParkingSlot[number_of_slots];
		for (int i = 0; i < number_of_slots; i++) {
			slots[i] = new ParkingSlot(i, SlotStatus.FREE);
		}
	}
	
	// method for releasing bike
	public void releaseBike() {
		// TO DO
	}
	
	// method for receiving bike
	public void receiveBike() {
		// TO DO
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
	
	
	public boolean hasDesiredBike(String bikeType) {
		
	}
	
	public boolean hasFreeSlot() {
		
	}
	
}
