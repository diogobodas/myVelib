package station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class StationBalance {
	
	private int rentCount;
	private int returnCount;
	private HashMap<ParkingSlot,ArrayList<LocalDateTime>> timeSlotDict;
	private Station station;

	public StationBalance(Station station) {
		rentCount = 0;
		returnCount = 0;
		this.station = station;
		int numberOfSlots = station.getSlots().length;
		timeSlotDict = new HashMap<ParkingSlot,ArrayList<LocalDateTime>>();
		for (ParkingSlot slot:station.getSlots())
			timeSlotDict.put(slot,new ArrayList<LocalDateTime>());
	}
	
	public void updateBalance(ParkingSlot slot, LocalDateTime time) {
		
		// if there is an odd number of timestamps for a slot, we are renting, else we are returning
		
		ArrayList<LocalDateTime> array = timeSlotDict.get(slot);
		
		int countTimeSlot = array.size();
		if (countTimeSlot % 2 == 0)
			rentCount += 1;
		else
			returnCount += 1;
		
		array.add(time);
		
	}
	
	public void getBalance(LocalDateTime ts,LocalDateTime te) {
		
	}
	
	public void showBalance() {
		this.toString();
	}

	@Override
	public String toString() {
		return "StationBalance [rentCount=" + rentCount + ", returnCount=" + returnCount + ", timeSlotDict="
				+ timeSlotDict + ", station=" + station + "]";
	}
	
	
	
}
