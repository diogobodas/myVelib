package core.station;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import station.PlusStation;
import system.GPS;
import user.User;
import user.Vlibre;
import user.Vmax;

class PlusStationTest {

	@Test
	void testChargeUser() {
		User usr1 = new User(0, new GPS(0,0), "000", new Vlibre(10));
		User usr2 = new User(1, new GPS(1,1), "001");
		User usr3 = new User(0, new GPS(0,0), "000", new Vmax());
		PlusStation station = new PlusStation(0, true, new GPS(0, 0), 10);
		station.chargeUser(usr1, 10, 10);
		assertTrue(usr1.getUsrBalance().getTotalCharge() == 10);
		assertTrue(usr1.getRegistrationCard().getCredit() == 5);
		station.chargeUser(usr2, 10, 10);
		assertTrue(usr2.getUsrBalance().getTotalCharge() == 10);
		station.chargeUser(usr3, 10, 0);
		assertTrue(usr3.getUsrBalance().getTotalCharge() == 10);
		assertTrue(usr3.getRegistrationCard().getCredit() == 5);	
	}

}
