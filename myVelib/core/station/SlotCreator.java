package station;

import bike.ElectricBike;
import bike.RegularBike;

public class SlotCreator {
	// Factory class used exclusively to create slots for each station according to parameters that may change
	
	// In case of new bike type, add percentage of this new type and change method
	double percent_empty = 0.3;
	double percent_regular = 0.7;
	
	public ParkingSlot[] fillSlots(int number_of_slots) {
		ParkingSlot[] slots = new ParkingSlot[number_of_slots];
		int empty_slots = (int) Math.floor(percent_empty * number_of_slots); // at least 70% of slots are occupied
		int number_regulars = (int) Math.floor(percent_regular * (number_of_slots - empty_slots)); // at least 30% of bikes are electric
		for (int i = 0; i < number_of_slots; i++) {
			if (i < empty_slots) {
				slots[i] = new ParkingSlot(i); // fill empty places
			} else if (i < empty_slots+number_regulars) {
				slots[i] = new ParkingSlot(i, new RegularBike()); // fill regular bikes
			} else {
				slots[i] = new ParkingSlot(i, new ElectricBike()); // fill electric bikes
			}
		}
		return slots;
	}
	
}
