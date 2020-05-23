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
		VelibSystem sys = new VelibSystem(5,30);
		Station[] stations = sys.getStations();
		VelibSystem.printSystemInfo();
	}
	
	@Test
	void addUserTest() {
		VelibSystem sys = new VelibSystem(5,30);
		sys.addUser(new GPS(5,5), "0");
		VelibSystem.printSystemInfo();
	}
}
