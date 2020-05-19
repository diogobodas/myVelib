package tests.user;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import user.PaymentVmax;

class PaymentVmaxTest {

	@Test
	void testGetValue() {
		PaymentVmax payment = new PaymentVmax(LocalDateTime.of(2020, 5, 19, 10, 0));
		double cost = payment.getValue(LocalDateTime.of(2020, 5, 19, 10, 30));
		assertTrue(cost == 0);
		cost = payment.getValue(LocalDateTime.of(2020, 5, 19, 11, 30));
		assertTrue(cost == 0.5);
	}

}
