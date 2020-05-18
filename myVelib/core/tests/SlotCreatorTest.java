package tests;

import static org.junit.jupiter.api.Assertions.*;
import bike.ElectricBike;
import bike.RegularBike;
import station.ParkingSlot;
import station.SlotCreator;

import org.junit.jupiter.api.Test;

class SlotCreatorTest {

	@Test
	void testFillSlots() {
		int n_slots = 17;
		SlotCreator factory = new SlotCreator();
		ParkingSlot[] slots = factory.fillSlots(n_slots);
		int empty = 0;
		int regular = 0;
		int electric = 0;
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].getBike() == null) {
				empty += 1;
			} else if (slots[i].getBike() instanceof RegularBike) {
				regular += 1;
			} else if (slots[i].getBike() instanceof ElectricBike) {
				electric += 1;
			} else {
				fail("Unsupported bike type. Change this test if a new bike was added");
			}
		}
		assertTrue(empty <= n_slots * factory.percent_empty);
		assertTrue(regular <= (n_slots - empty) * factory.percent_regular);
		assertTrue(electric >= (regular + electric) * (1 - factory.percent_regular));
	}

}
