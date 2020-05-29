package tests.station;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import bike.RegularBike;
import exceptions.UnavailableBikeException;
import exceptions.UnavailableSlotException;
import user.PaymentStd;
import user.User;
import user.Vlibre;
import station.ParkingSlot;
import station.SlotStatus;
import station.Station;
import station.Terminal;
import system.GPS;
import system.VelibSystem;

class ParkingSlotTest {

	VelibSystem sys = new VelibSystem(5,30);
	Station [] stations = {new Station(1, true, new GPS(0,0), 3)};
	ParkingSlot [] slots = {new ParkingSlot(100), new ParkingSlot(101), new ParkingSlot(102)};
	User usr1 = new User(0, new GPS(0,0), "000");
	RegularBike bike1 = new RegularBike();
	
	@Test
	void testReceiveBike() throws UnavailableBikeException, UnavailableSlotException {
		sys.addUser(usr1);
		sys.setStations(stations);
		stations[0].setSlots(slots);
		usr1.setBike(bike1);
		usr1.setPaymentMode(new PaymentStd(LocalDateTime.of(2020, 5, 28, 10, 30), 1.0));
		try {
			stations[0].getSlots()[0].receiveBike(bike1, LocalDateTime.of(2020, 5, 28, 12, 30));
			assertTrue(stations[0].getSlots()[0].getStatus().equals(SlotStatus.OCCUPIED));
			assertTrue(stations[0].getSlots()[0].getBike().equals(bike1));
			assertTrue(usr1.getPaymentMode() == null);
		} catch (Exception e) {
			fail("Unexpected exception found during ok execution of bike return operation");
		}
		try {
			stations[0].getSlots()[1].receiveBike(new RegularBike(), LocalDateTime.of(2020, 5, 28, 12, 30));
			fail("Did not catch expected exception");
		} catch (UnavailableBikeException e) {}
		usr1.setBike(bike1);
		try {
			stations[0].getSlots()[0].receiveBike(bike1, LocalDateTime.of(2020, 5, 28, 12, 30));
			fail("Did not catch expected exception");
		} catch (UnavailableSlotException e) {}
	}

	@Test
	void testReleaseBike() {
		fail("Not yet implemented");
	}

	@Test
	void testEqualsObject() {
		fail("Not yet implemented");
	}

}
