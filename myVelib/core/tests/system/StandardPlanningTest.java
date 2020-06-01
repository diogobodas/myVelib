package tests.system;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bike.ElectricBike;
import station.Station;
import system.GPS;
import system.StandardPlanning;

class StandardPlanningTest {

	@Test
	void testPlan() {
		Station s1 = new Station(1, true, new GPS(0,0), 10, 1.0, 1.0); // empty station
		Station s2 = new Station(2, true, new GPS(3,4), 10, 0.25, 0.70); // station with both types of bike
		Station s3 = new Station(3, true, new GPS(1,1), 10, 0.25, 1.0); // station with regular bikes only
		Station s4 = new Station(4, true, new GPS(10,10), 10, 0.0, 0.7); // full station
		Station s5 = new Station(5, true, new GPS(8,8), 10, 0.25, 0.7); // non-full station
		Station[] input_stations = {s1,s2,s3,s4,s5};
		StandardPlanning stdPlan = new StandardPlanning();
		Station[] output_stations = stdPlan.plan(input_stations, new GPS(0,0), new GPS(10,10), ElectricBike.class);
		assertTrue(output_stations[0].equals(s2));
		assertTrue(output_stations[1].equals(s5));
	}

}
