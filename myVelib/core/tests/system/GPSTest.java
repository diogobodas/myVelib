package tests.system;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import system.GPS;

class GPSTest {

	@Test
	void testDistance() {
		GPS p1 = new GPS(0,0);
		GPS p2 = new GPS(3,4);
		assertTrue(GPS.distance(p1, p1) == 0);
		assertTrue(GPS.distance(p1, p2) == 5.0);
	}

}
