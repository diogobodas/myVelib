package tests.user;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import bike.ElectricBike;
import bike.RegularBike;
import exceptions.IrregularCardException;
import exceptions.IrregularUserException;
import exceptions.UnavailableBikeException;
import exceptions.UnavailableSlotException;
import exceptions.UnavailableStationException;
import station.Station;
import station.Terminal;
import system.GPS;
import system.VelibSystem;
import user.User;

class UserTest {

	@Test
	void testRentBike() throws UnavailableBikeException, IrregularUserException, IrregularCardException, UnavailableStationException {
		VelibSystem sys = new VelibSystem(3,30);
		sys.getStations()[0] = new Station(0, false, new GPS(3,4), 10, 0.25, 0.70); // offline station
		sys.getStations()[0].setTerminal(new Terminal(sys.getStations()[0]));
		sys.getStations()[1] = new Station(1, true, new GPS(1,1), 10, 0.25, 1.0); // station with regular bikes only
		sys.getStations()[1].setTerminal(new Terminal(sys.getStations()[1]));
		sys.getStations()[2] = new Station(2, true, new GPS(5,4), 10, 0.25, 0.70); // station with both types of bike
		sys.getStations()[2].setTerminal(new Terminal(sys.getStations()[2]));
		User u1 = new User(0, new GPS(0,0), "000");
		sys.addUser(u1);
		try {
			u1.rentBike(sys.getStations()[0], ElectricBike.class, LocalDateTime.of(2020, 5, 30, 10, 30));
			fail("Should have caught exception");
		} catch(UnavailableStationException e) {
			assertTrue(e.getMessage().equals("Station is offline, cannot rent bike here"));
		}
		try {
			u1.rentBike(sys.getStations()[1], ElectricBike.class, LocalDateTime.of(2020, 5, 30, 10, 30));
			fail("Should have caught exception");
		} catch(UnavailableBikeException e) {
			assertTrue(e.getMessage().equals("Station does not have the desired bike"));
		}
		try {
			u1.rentBike(sys.getStations()[2], ElectricBike.class, LocalDateTime.of(2020, 5, 30, 10, 30));
		} catch(UnavailableBikeException e) {
			fail("Shouldn't have caught exception");
		}
	}

	@Test
	void testDropBike() throws UnavailableBikeException, IrregularUserException, IrregularCardException, UnavailableStationException {
		VelibSystem sys = new VelibSystem(3,30);
		sys.getStations()[0] = new Station(0, false, new GPS(3,4), 10, 0.25, 0.70); // offline station
		sys.getStations()[0].setTerminal(new Terminal(sys.getStations()[0]));
		sys.getStations()[1] = new Station(1, true, new GPS(1,1), 10, 0.25, 1.0); // station with regular bikes only
		sys.getStations()[1].setTerminal(new Terminal(sys.getStations()[1]));
		sys.getStations()[2] = new Station(2, true, new GPS(5,4), 10, 0.25, 0.70); // station with both types of bike
		sys.getStations()[2].setTerminal(new Terminal(sys.getStations()[2]));
		User u1 = new User(0, new GPS(0,0), "000");
		sys.addUser(u1);
		u1.rentBike(sys.getStations()[2], ElectricBike.class, LocalDateTime.of(2020, 5, 30, 10, 30));
		try {
			u1.dropBike(sys.getStations()[0], LocalDateTime.of(2020, 5, 30, 12, 30));
			fail("Should have caught exception");
		} catch(UnavailableSlotException e) {
			assertTrue(e.getMessage().equals("Station has no free parking slot"));
		}
		try {
			u1.dropBike(sys.getStations()[2], LocalDateTime.of(2020, 5, 30, 12, 30));
		} catch(UnavailableSlotException e) {
			fail("Shouldn't have caught exception");
		}
	}

}
