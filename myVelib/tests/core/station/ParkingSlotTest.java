package core.station;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import bike.ElectricBike;
import bike.RegularBike;
import exceptions.IrregularOperationException;
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
	ElectricBike bike2 = new ElectricBike();
	
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
		sys.addUser(usr1);
		sys.setStations(stations);
		slots[0].setBike(bike1);
		slots[0].setStatus(SlotStatus.OCCUPIED);
		slots[2].setBike(bike2);
		slots[2].setStatus(SlotStatus.OUT_OF_ORDER);
		stations[0].setSlots(slots);
		try {
			slots[0].releaseBike(usr1, LocalDateTime.of(2020, 5, 28, 12, 30));
			assertTrue(usr1.getBike().equals(bike1));
			assertTrue(slots[0].getBike() == null);
			assertTrue(slots[0].getStatus().equals(SlotStatus.FREE));
		} catch (Exception e) {
			fail("Unexpected exception occurred during normal method behavior");
		}
		usr1.setBike(null);
		try {
			slots[1].releaseBike(usr1, LocalDateTime.of(2020, 5, 28, 12, 30));
			fail("Expected to catch exception because slot is empty");
		} catch (UnavailableBikeException e) {}
		try {
			slots[2].releaseBike(usr1, LocalDateTime.of(2020, 5, 28, 12, 30));
			fail("Expected to catch exception because slot is out of order");
		} catch (UnavailableBikeException e) {}	
	}

	@Test
	void testEqualsObject() {
		ParkingSlot s = new ParkingSlot(100);
		assertTrue(s.equals(slots[0]));
		assertFalse(s.equals(slots[2]));
	}
	
	@Test
	void testSetSlotOffline() {
		ParkingSlot s = new ParkingSlot(100);
		try {
			s.setSlotOffline(LocalDateTime.of(2020, 5, 30, 10, 30));
			assertTrue(s.getStatus().equals(SlotStatus.OUT_OF_ORDER));
		} catch (IrregularOperationException e) {
			fail("Slot is unexpectedly out of order");
		}
		try {
			s.setSlotOffline(LocalDateTime.of(2020, 5, 30, 12, 30));
			fail("Should not be able to set offline slot already out-of-order");
		} catch (IrregularOperationException e) {}
	}
	
	@Test
	void testSetSlotOnline() {
		ParkingSlot s = new ParkingSlot(100);
		try {
			s.setSlotOnline(LocalDateTime.of(2020, 5, 30, 12, 30));
			fail("Should not be able to set online slot already functional");
		} catch (IrregularOperationException e) {}
		try {
			s.setSlotOffline(LocalDateTime.of(2020, 5, 30, 10, 30));
			s.setSlotOnline(LocalDateTime.of(2020, 5, 30, 12, 30));
			assertFalse(s.getStatus().equals(SlotStatus.OUT_OF_ORDER));
		} catch (IrregularOperationException e) {
			fail("Unable to set offline slot online");
		}
		
	}

}
