package tests.system;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import station.Station;
import system.GPS;
import system.VelibSystem;
import user.User;

class VelibSystemTest {

	@Test
	void SystemInitTest() {
		VelibSystem sys = new VelibSystem(5,33);
		Station[] stations = sys.getStations();
		assert((stations[0].getSlots().length == 6));
		assert((stations[1].getSlots().length == 6));
		assert((stations[2].getSlots().length == 6));
		assert((stations[3].getSlots().length == 6));
		assert((stations[4].getSlots().length == 9));
		System.out.println("Rate of Ocuppation for station 0");
		System.out.println(String.valueOf(stations[0].getBalance().getRate()));
	}
	
	@Test
	void addUserTest() {
		VelibSystem sys = new VelibSystem(5,30);
		sys.addUser(new GPS(5,5), "0");
		//VelibSystem.printSystemInfo();
	}
}
