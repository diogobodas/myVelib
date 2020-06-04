package core.system;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import station.Station;
import system.GPS;
import system.RidePlanning;
import system.StandardPlanning;

class RidePlanningTest {

	@Test
	void testGetClosestStation() {
		Station s1 = new Station(1, true, new GPS(0,0), 10);
		Station s2 = new Station(2, true, new GPS(1,0), 10);
		Station s3 = new Station(3, true, new GPS(2,0), 10);
		HashMap<Station, Double> dict = new HashMap<Station, Double>();
		dict.put(s1, 1.4);
		dict.put(s2, 1.0);
		dict.put(s3, 1.7);
		Station closest = new StandardPlanning().getClosestStation(dict);
		assertTrue(closest.equals(s2));
	}

}
