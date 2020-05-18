package station;
import java.lang.Enum;

public class ParkingSlot {
	// attributes 
	private int id;
	private SlotStatus status;
	
	//constructor
	public ParkingSlot(int id, SlotStatus status) {
		this.id = id;
		this.status = status;
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
	public SlotStatus getStatus() {
		return status;
	}
	public void setStatus(SlotStatus status) {
		this.status = status;
	}
	
	

}
