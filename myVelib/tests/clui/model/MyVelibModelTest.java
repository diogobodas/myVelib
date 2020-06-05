package clui.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import model.MyVelibModel;
import station.StationBalance;
import system.VelibSystem;
import user.Vlibre;
import user.Vmax;

class MyVelibModelTest {

	@Test
	void testAddUser() {
		try {
			MyVelibModel model = new MyVelibModel();
			model.setup("testSystem");
			model.addUser("Charles","vmax");
			model.addUser("Carlos", "none");
			model.addUser("Isabella", "vlibre");
			assertTrue(VelibSystem.getUserByID(0).getName().equals("Charles") && 
					VelibSystem.getUserByID(0).getRegistrationCard().getClass() == stringToCardClass("vmax"));
			assertTrue(VelibSystem.getUserByID(1).getName().equals("Carlos") && 
					VelibSystem.getUserByID(1).getRegistrationCard() == null);
			assertTrue(VelibSystem.getUserByID(2).getName().equals("Isabella") && 
					VelibSystem.getUserByID(2).getRegistrationCard().getClass() == stringToCardClass("vlibre"));
			System.out.println("JSAFKJAS");
		}
		catch (Exception e) {
			fail("Model not correctly initialized");
		}
	}
	
	Class <?> stringToCardClass(String card){
		if (card.equals("vmax"))
			return Vmax.class;
		else if (card.equals("vlibre"))
			return Vlibre.class;
		else if (card.equals("none"))
			return null;
		else
			fail("Incorrect card type");
		return null;
	}

	@Test
	void testSetTimeWindow() {
		try {
			MyVelibModel model = new MyVelibModel();
			LocalDateTime ts = LocalDateTime.of(2020,7,13,10,00);
			LocalDateTime te = LocalDateTime.of(2020,7,14,19,00);
			model.setTimeWindow(ts, te);
			assertTrue(StationBalance.ts == ts);
			assertTrue(StationBalance.te == te);
		}
		catch (Exception e) {
			fail("Test failed");
		}
	}

	@Test
	void testSetup() {
		try {
			MyVelibModel model = new MyVelibModel();
			model.setup("setupTest");
			model.setup("setupTest",9,47,1.25,37);
			assert(true);
			try {
				model.setup("test",100,10,1,5); // more stations that slots
				model.setup("test",10,100,2,200); // more bikes than slots
			}
			catch( Exception e) {
				assert(true);
			}
		}
		catch (Exception e) {
			fail("Test failed");
		}
	}

	@Test
	void testOffline() {
		try {
			MyVelibModel model = new MyVelibModel();
			model.offline(0, LocalDateTime.of(2020,6,1,12,30));
			assertTrue(true);
			try {
				model.offline(0, LocalDateTime.of(2020,6,1,12,45));
				fail("It is already offline");
			}
			catch(Exception e) {
				assertTrue(true);
			}
		}
		catch (Exception e) {
			fail("Model did not load correctly");
		}
 	}

	@Test
	void testOnline() {
			try {
		MyVelibModel model = new MyVelibModel();
		model.online(0, LocalDateTime.of(2020,6,1,12,30));
		fail("Model already online");
		}
		catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	void testRentBike() {
		try {
			MyVelibModel model = new MyVelibModel();
			model.rentBike(0,0,LocalDateTime.of(2020,6,1,13,00),"regular");
			assertTrue(true);
			try {
				model.rentBike(0,0,LocalDateTime.of(2020,6,1,13,00),"electric");
				fail("User already has a bike!");
			}
			catch (Exception e) {
				assertTrue(true);
			}
		}
		catch (Exception e) {
			fail("Model not configured");
		}
	}

	@Test
	void testReturnBike1() {
		try {
			MyVelibModel model = new MyVelibModel();
			try {
				model.returnBike(0,0,LocalDateTime.of(2020,6,1,13,45));
				fail("User does not have a bike");
			}
			catch (Exception e) {
				assertTrue(true)
;			}
		}
		catch (Exception e) {
			fail("Model not configured");
		}
	}
	
	void testReturnBike2() {
		try {
			MyVelibModel model = new MyVelibModel();
			try {
				model.rentBike(0, 0,LocalDateTime.of(2020,6,1,13,45),"electric");
				model.returnBike(0,0,LocalDateTime.of(2020,6,1,13,45));
				assertTrue(true);
			}
			catch (Exception e) {
				fail("Failed renting bike");
;			}
		}
		catch (Exception e) {
			fail("Model not configured");
		}
	}
}
