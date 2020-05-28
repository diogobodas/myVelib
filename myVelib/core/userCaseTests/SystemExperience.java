package userCaseTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bike.RegularBike;
import system.GPS;
import system.VelibSystem;
import user.User;
import user.Vlibre;

class SystemExperience {

	@Test
	void test() {
		// PROBLEM WHEN M IS NOT DIVISBLE BY N!!!
		VelibSystem sys = new VelibSystem(5,30);
		sys.addUser(new GPS(5,5), "153",new Vlibre());
		User usr = sys.getUsers().get(0);
		usr.rentBike(sys.getStations()[0],RegularBike.class);
		System.out.println(usr.getBike());
		usr.dropBike(sys.getStations()[0]);
		System.out.println(usr.getUsrBalance().toString());
		System.out.println(sys.getStations()[0].getBalance());
	}

}
