package core.station;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import system.GPS;
import user.User;
import user.Vlibre;
import user.Vmax;

import org.junit.jupiter.api.Test;

import bike.ElectricBike;
import bike.RegularBike;
import exceptions.IrregularOperationException;
import station.ParkingSlot;
import station.PlusStation;
import station.SlotStatus;
import station.Station;
import station.Terminal;

class StationTest {

	@Test
	void testHasDesiredBike() {
		Station s = new Station(1, true, new GPS(0,0), 10);
		assertTrue(s.hasDesiredBike(ElectricBike.class));
		assertTrue(s.hasDesiredBike(RegularBike.class));
	}

	@Test
	void testHasFreeSlot() {
		Station s = new Station(1, true, new GPS(0,0), 10);
		assertTrue(s.hasFreeSlot());
		Station s1 = new Station(2, true, new GPS(0,0), 1);
		ParkingSlot[] slots = {new ParkingSlot(200)};
		slots[0].setStatus(SlotStatus.OCCUPIED);
		assertFalse(s1.hasFreeSlot());
	}
	
	@Test
	void testGetBikeByID() {
		Station s = new Station(1, true, new GPS(0,0), 10);
		int id = s.getSlots()[6].getBike().getID();
		assertTrue(s.getBikeByID(id) != null);
		assertTrue(s.getBikeByID(id).equals(s.getSlots()[6].getBike()));
	}
	
	@Test
	void testChargeUser() {
		User usr1 = new User(0, new GPS(0,0), "000", new Vlibre(10));
		User usr2 = new User(1, new GPS(1,1), "001");
		User usr3 = new User(0, new GPS(0,0), "000", new Vmax());
		Station station = new Station(0, true, new GPS(0, 0), 10);
		station.chargeUser(usr1, 10, 10);
		assertTrue(usr1.getUsrBalance().getTotalCharge() == 10);
		assertTrue(usr1.getRegistrationCard().getCredit() == 0);
		station.chargeUser(usr2, 10, 10);
		assertTrue(usr2.getUsrBalance().getTotalCharge() == 10);
		station.chargeUser(usr3, 10, 0);
		assertTrue(usr3.getUsrBalance().getTotalCharge() == 10);
		assertTrue(usr3.getRegistrationCard().getCredit() == 0);
	}
	
	@Test
	void testSetStationOffline() {
		Station station = new Station(1, true, new GPS(0, 0), 4);
		ParkingSlot[] slots = {new ParkingSlot(100), new ParkingSlot(101, new RegularBike()), new ParkingSlot(102, new ElectricBike()), new ParkingSlot(103)};
		try {
			slots[2].setSlotOffline(LocalDateTime.of(2020, 5, 30, 10, 30));
			slots[3].setSlotOffline(LocalDateTime.of(2020, 5, 30, 12, 30));
			station.setSlots(slots);
			station.setStationOffline(LocalDateTime.of(2020, 5, 30, 14, 30));
			assertFalse(station.isOn_service());
			for (ParkingSlot slot : station.getSlots())
				assertTrue(slot.getStatus().equals(SlotStatus.OUT_OF_ORDER));
		} catch (IrregularOperationException e) {
			System.out.println(e);
			fail("Unexpected exception during correct calling of method");
		}
		try {
			station.setStationOffline(LocalDateTime.of(2020, 5, 30, 15, 30));
			fail("Should not be able to set offline station that is already offline");
		} catch (IrregularOperationException e) {}
	}
	
	@Test
	void testSetStationOnline() {
		Station station = new Station(1, true, new GPS(0, 0), 4);
		ParkingSlot[] slots = {new ParkingSlot(100), new ParkingSlot(101, new RegularBike()), new ParkingSlot(102, new ElectricBike()), new ParkingSlot(103)};
		try {
			slots[2].setSlotOffline(LocalDateTime.of(2020, 5, 30, 10, 30));
			slots[3].setSlotOffline(LocalDateTime.of(2020, 5, 30, 12, 30));
			station.setSlots(slots);
			station.setStationOnline(LocalDateTime.of(2020, 5, 30, 15, 30));
			fail("Should not be able to set online station that is already online");
		} catch (IrregularOperationException e) {}
		try {
			SlotStatus [] slot_status = new SlotStatus[station.getSlots().length];
			for (int i = 0; i < station.getSlots().length; i++)
				slot_status[i] = station.getSlots()[i].getStatus();
			station.setStationOffline(LocalDateTime.of(2020, 5, 30, 15, 30));
			station.setStationOnline(LocalDateTime.of(2020, 5, 30, 16, 30));
			assertTrue(station.isOn_service());
			for (int i = 0; i < station.getSlots().length; i++)
				assertTrue(station.getSlots()[i].getStatus().equals(slot_status[i]));
		} catch (IrregularOperationException e) {
			System.out.println(e);
			fail("Unexpected exception during correct calling of method");
		}
	}
	

}