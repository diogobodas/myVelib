package tests.system;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import bike.ElectricBike;
import bike.RegularBike;
import station.ParkingSlot;
import station.Station;
import system.GPS;
import system.VelibSystem;
import user.User;
import user.Vlibre;

class VelibSystemTest {

	@Test
	void SystemInitTest() {
		VelibSystem sys = new VelibSystem(5,33);
		Station[] stations = sys.getStations();
		assertTrue((stations[0].getSlots().length == 6));
		assertTrue((stations[1].getSlots().length == 6));
		assertTrue((stations[2].getSlots().length == 6));
		assertTrue((stations[3].getSlots().length == 6));
		assertTrue((stations[4].getSlots().length == 9));
	}
	
	@Test
	void addUserTest() {
		VelibSystem sys = new VelibSystem(5,30);
		assertTrue(sys.getUsers().isEmpty());
		sys.addUser("TestUser", null);
		assertFalse(sys.getUsers().isEmpty());
	}
	
	@Test
	void testGetUserByBike() {
		VelibSystem sys = new VelibSystem(5,30);
		User u1 = new User(0, new GPS(0,0), "000");
		User u2 = new User(1, new GPS(2,3), "001", new Vlibre());
		RegularBike b = new RegularBike();
		u2.setBike(new ElectricBike());
		sys.addUser(u1);
		sys.addUser(u2);
		assertNull(VelibSystem.getUserByBike(b));
		u1.setBike(b);
		assertTrue(VelibSystem.getUserByBike(b).equals(u1));
	}
	
	@Test
	void testGetStationBySlot() {
		VelibSystem sys = new VelibSystem(5,33);
		ParkingSlot p1 = new ParkingSlot(701); // there are only 5 stations, so no pslot with id 7xx
		assertNull(VelibSystem.getStationBySlot(p1));
		ParkingSlot p2 = new ParkingSlot(203); // should retrieve station 2
		assertTrue(VelibSystem.getStationBySlot(p2).equals(sys.getStations()[2]));
	}
	
	@Test
	void testGetStationByBike() {
		VelibSystem sys = new VelibSystem(5,33);
		RegularBike b = new RegularBike();
		assertNull(VelibSystem.getStationByBike(b));
		sys.getStations()[3].getSlots()[5].setBike(b);
		assertTrue(VelibSystem.getStationByBike(b).equals(sys.getStations()[3]));
	}
}
