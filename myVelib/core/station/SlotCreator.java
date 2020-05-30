package station;

import bike.ElectricBike;
import bike.RegularBike;

/**
 * Factory class used exclusively to create slots for each station according to parameters that may change, namely minimal percentage of occupied slots and minimal percentage of electric bikes.
 * In case of creation of a new bike type, this class needs to be modified with the correct percentage of presence in the stations.
 * For now, the information concerning minimal occupation rate and minimal electric bike rate is stored in the attributes percentage_empty and percentage_regular, because they represent the complement of these information, that is, the maximal percentage of empty slots and regular bikes.
 *
 */
public class SlotCreator {
	
	private double percent_empty = 0.3;
	private double percent_regular = 0.7;
	
	/**
	 * Method for adequately filling the slots of a newly creation station. It is the only method that should be used by this class, and represents the factory creation method.
	 * @param stationId ID number of the station whose slots are being filled
	 * @param number_of_slots number of slots to be filled
	 * @return a ParkingSlot array containing all slots correctly initialized and respecting the constraints for each station concerning minimal occupation and bike type rates.
	 */
	public ParkingSlot[] fillSlots(int stationId, int number_of_slots) {
		ParkingSlot[] slots = new ParkingSlot[number_of_slots];
		int empty_slots = (int) Math.floor(percent_empty * number_of_slots); // at least 70% of slots are occupied
		int number_regulars = (int) Math.floor(percent_regular * (number_of_slots - empty_slots)); // at least 30% of bikes are electric
		for (int i = 0; i < number_of_slots; i++) {
			if (i < empty_slots) {
				slots[i] = new ParkingSlot(stationId*100+ i); // fill empty places
			} else if (i < empty_slots+number_regulars) {
				slots[i] = new ParkingSlot(stationId*100 + i, new RegularBike()); // fill regular bikes
			} else {
				slots[i] = new ParkingSlot(stationId*100 + i, new ElectricBike()); // fill electric bikes
			}
		}
		return slots;
	}

	public double getPercent_empty() {
		return percent_empty;
	}

	public void setPercent_empty(double percent_empty) {
		this.percent_empty = percent_empty;
	}

	public double getPercent_regular() {
		return percent_regular;
	}

	public void setPercent_regular(double percent_regular) {
		this.percent_regular = percent_regular;
	}
	
	
	
}
