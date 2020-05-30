package tests.station;

import static org.junit.jupiter.api.Assertions.*;
import system.GPS;

import org.junit.jupiter.api.Test;

import bike.ElectricBike;
import bike.RegularBike;
import station.ParkingSlot;
import station.SlotStatus;
import station.Station;
import station.Terminal;

class StationTest {

	@Test
	void testHasDesiredBike() {
		Station s = new Station(1, true, new GPS(0,0), 10);
		assertTrue(s.hasDesiredBike(ElectricBike.class));
		assertTrue(s.hasDesiredBike(RegularBike.class));
	}

	@Test
	void testHasFreeSlot() {
		Station s = new Station(1, true, new GPS(0,0), 10);
		assertTrue(s.hasFreeSlot());
		Station s1 = new Station(2, true, new GPS(0,0), 1);
		ParkingSlot[] slots = {new ParkingSlot(200)};
		slots[0].setStatus(SlotStatus.OCCUPIED);
		assertFalse(s1.hasFreeSlot());
	}
	
	@Test
	void testGetBikeByID() {
		Station s = new Station(1, true, new GPS(0,0), 10);
		int id = s.getSlots()[6].getBike().getID();
		assertTrue(s.getBikeByID(id) != null);
		assertTrue(s.getBikeByID(id).equals(s.getSlots()[6].getBike()));
	}
	
	@Test
	void testChargeUser() {
		fail("Not yet implmented");
	}
	}

}