package tests.station;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import bike.RegularBike;
import exceptions.IrregularCardException;
import exceptions.IrregularUserException;
import exceptions.UnavailableBikeException;
import station.Station;
import station.Terminal;
import system.GPS;
import system.VelibSystem;
import user.User;
import user.Vlibre;

class TerminalTest {

	@Test
	void testReleaseBike() {
		VelibSystem sys = new VelibSystem(5,30);
		Terminal terminal = sys.getStations()[0].getTerminal();
		User usr = new User(1, new GPS(1,1), "001");
		sys.addUser(usr);
		try {
			terminal.releaseBike(usr, RegularBike.class, LocalDateTime.of(2020, 5, 30, 10, 30));
			assertTrue(usr.getBike() instanceof RegularBike);
		} catch (UnavailableBikeException e) {
			System.out.println(e);
			fail("Unexpected exception during normal behavior of function");
		}
	}

	@Test
	void testIdentifyUser() {
		VelibSystem sys = new VelibSystem(5,30);
		Terminal t = sys.getStations()[0].getTerminal();
		User usr1 = new User(0, new GPS(0,0), "000", new Vlibre(10));
		User usr2 = new User(1, new GPS(1,1), "001");
		User usr3 = new User(2, new GPS(0,0), "000");
		try {
			t.identifyUser(usr1);
			fail("User has card but is not registered in system. Should have caught exception");
		} catch (Exception e) {
			assertTrue(e instanceof IrregularCardException);
			IrregularCardException ice = (IrregularCardException) e;
			assertTrue(ice.getError_message().equals("User has card but is not registered in system"));
		}
		sys.addUser(usr1);
		usr1.setBike(new RegularBike());
		try {
			t.identifyUser(usr1);
			fail("User already has bike. It should not be able to identify himself");
		} catch (Exception e) {
			assertTrue(e instanceof IrregularUserException);
			IrregularUserException iue = (IrregularUserException) e;
			assertTrue(iue.getError_message().equals("User already has a bike under his name. It cannot rent any other bikes"));
		}
		try {
			t.identifyUser(usr2);
			fail("User is not registered in the system, should have caught exception");
		} catch (Exception e) {
			assertTrue(e instanceof IrregularUserException);
			IrregularUserException iue = (IrregularUserException) e;
			assertTrue(iue.getError_message().equals("No user is registered with this card"));
		}
		sys.addUser(usr2);
		usr2.setBike(new RegularBike());
		try {
			t.identifyUser(usr2);
			fail("User already has bike. It should not be able to identify himself");
		} catch (Exception e) {
			assertTrue(e instanceof IrregularUserException);
			IrregularUserException iue = (IrregularUserException) e;
			assertTrue(iue.getError_message().equals("User already has a bike under his name. It cannot rent any other bikes"));
		}
		try {
			t.identifyUser(usr3);
			fail("User is not registered, should have caught exception");
		} catch (Exception e) {
			assertTrue(e instanceof IrregularUserException);
			IrregularUserException iue = (IrregularUserException) e;
			assertTrue(iue.getError_message().equals("User not registered"));
		}
	}

}
