package tests;

import static org.junit.jupiter.api.Assertions.*;
import system.GPS;

import org.junit.jupiter.api.Test;

import bike.ElectricBike;
import bike.RegularBike;
import station.Station;
import station.Terminal;

class StationTest {

	@Test
	void testHasDesiredBike() {
		Station s = new Station(1, true, new GPS(0,0), new Terminal(), 10);
		assertTrue(s.hasDesiredBike(ElectricBike.class));
		assertTrue(s.hasDesiredBike(RegularBike.class));
	}

	@Test
	void testHasFreeSlot() {
		Station s = new Station(1, true, new GPS(0,0), new Terminal(), 10);
		assertTrue(s.hasFreeSlot());
	}

}