package tests.user;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import bike.ElectricBike;
import bike.RegularBike;
import system.GPS;
import user.Payment;
import user.PaymentStd;
import user.PaymentVlibre;
import user.PaymentVmax;
import user.User;
import user.Vlibre;
import user.Vmax;

class PaymentTest {

	@Test
	void testCreateAdequatePayment() {
		User u1 = new User(0, new GPS(0,0), "000", null);
		User u2 = new User(0, new GPS(0,0), "000", new Vlibre());
		User u3 = new User(0, new GPS(0,0), "000", new Vmax());
		RegularBike regbike = new RegularBike();
		ElectricBike elecbike = new ElectricBike();
		Payment p = Payment.createAdequatePayment(u1, regbike, LocalDateTime.of(2020, 06, 1, 10, 30));
		assertTrue(p instanceof PaymentStd);
		PaymentStd pstd = (PaymentStd) p;
		assertTrue(pstd.getHourCost() == 1.0);
		p = Payment.createAdequatePayment(u1, elecbike, LocalDateTime.of(2020, 06, 1, 10, 30));
		assertTrue(p instanceof PaymentStd);
		pstd = (PaymentStd) p;
		assertTrue(pstd.getHourCost() == 2.0);
		p = Payment.createAdequatePayment(u2, regbike, LocalDateTime.of(2020, 06, 1, 10, 30));
		assertTrue(p instanceof PaymentVlibre);
		PaymentVlibre pvlibre = (PaymentVlibre) p;
		assertTrue(pvlibre.getHourCost() == 1.0);
		assertTrue(pvlibre.getMinutesCredit() == 0);
		p = Payment.createAdequatePayment(u2, elecbike, LocalDateTime.of(2020, 06, 1, 10, 30));
		assertTrue(p instanceof PaymentVlibre);
		pvlibre = (PaymentVlibre) p;
		assertTrue(pvlibre.getHourCost() == 2.0);
		assertTrue(pvlibre.getMinutesCredit() == 0);
		p = Payment.createAdequatePayment(u3, regbike, LocalDateTime.of(2020, 06, 1, 10, 30));
		assertTrue(p instanceof PaymentVmax);
		PaymentVmax pvmax = (PaymentVmax) p;
		assertTrue(pvmax.getHourCost() == 1.0);
		p = Payment.createAdequatePayment(u3, elecbike, LocalDateTime.of(2020, 06, 1, 10, 30));
		assertTrue(p instanceof PaymentVmax);
		pvmax = (PaymentVmax) p;
		assertTrue(pvmax.getHourCost() == 1.0);
	}

}
