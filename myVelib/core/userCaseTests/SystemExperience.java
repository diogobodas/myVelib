package userCaseTests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import bike.RegularBike;
import exceptions.IrregularCardException;
import exceptions.IrregularUserException;
import exceptions.UnavailableBikeException;
import exceptions.UnavailableSlotException;
import exceptions.UnavailableStationException;
import system.GPS;
import system.VelibSystem;
import user.User;
import user.Vlibre;

class SystemExperience {

	@Test
	void test() throws UnavailableBikeException, IrregularUserException, IrregularCardException, UnavailableStationException, UnavailableSlotException {
		// PROBLEM WHEN M IS NOT DIVISBLE BY N!!!
		VelibSystem sys = new VelibSystem(5,30);
		sys.addUser(new GPS(5,5), "153", new Vlibre());
		User usr = sys.getUsers().get(0);
		usr.rentBike(sys.getStations()[0],RegularBike.class, LocalDateTime.of(2020, 5, 28, 10, 30));
		System.out.println(usr.getBike());
		usr.dropBike(sys.getStations()[0], LocalDateTime.of(2020, 5, 28, 12, 30));
		System.out.println(usr.getUsrBalance().toString());
		System.out.println(sys.getStations()[0].getBalance());
	}

}