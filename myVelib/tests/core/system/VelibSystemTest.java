package core.system;

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
import user.Vmax;

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
	
	@Test
	void testGetUserByRegistrationCard() {
		VelibSystem sys = new VelibSystem(5,33);
		Vmax vmax = new Vmax();
		Vlibre vlibre = new Vlibre();
		User u1 = new User(0, new GPS(0,0), "000");
		User u2 = new User(1, new GPS(0,0), "010", vmax);
		User u3 = new User(2, new GPS(0,0), "110", vlibre);
		sys.addUser(u1);
		sys.addUser(u3);
		assertNull(VelibSystem.getUserByRegistrationCard(vmax));
		sys.addUser(u2);
		assertTrue(VelibSystem.getUserByRegistrationCard(vlibre).equals(u3));
		assertTrue(VelibSystem.getUserByRegistrationCard(vmax).equals(u2));
	}
	
	@Test
	void testGetStationByID() {
		VelibSystem sys = new VelibSystem(5,33); // should create network with 5 stations numbered 0 to 4
		for(int i = 0; i < 5; i++)
			assertTrue(VelibSystem.getStationByID(i).equals(sys.getStations()[i]));
		assertNull(VelibSystem.getStationByID(7));
	}
	
	@Test
	void testgetUserByID() {
		VelibSystem sys = new VelibSystem(5,33);
		Vmax vmax = new Vmax();
		Vlibre vlibre = new Vlibre();
		sys.addUser("U1", null);
		sys.addUser("U2", vmax);
		sys.addUser("U3", vlibre);
		assertNull(VelibSystem.getUserByID(5));
		assertNull(VelibSystem.getUserByID(0).getRegistrationCard());
		assertTrue(VelibSystem.getUserByID(1).getRegistrationCard().equals(vmax));
		assertTrue(VelibSystem.getUserByID(2).getRegistrationCard().equals(vlibre));
	}
	
	@Test
	void testGetUsersWithCreditCard() {
		VelibSystem sys = new VelibSystem(5,33);
		sys.addUser(new GPS(0,0), "000");
		sys.addUser(new GPS(0,0), "001");
		assertTrue(VelibSystem.getUsersWithCreditCard("002").isEmpty());
		assertTrue(VelibSystem.getUsersWithCreditCard("000").size() == 1);
		sys.addUser(new GPS(0,0), "001");
		assertTrue(VelibSystem.getUsersWithCreditCard("001").size() == 2);
		
	}
	
}
