package core.user;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import user.PaymentStd;

class PaymentStdTest {

	@Test
	void testGetValue() {
		PaymentStd payment = new PaymentStd(LocalDateTime.of(2020, 5, 19, 10, 0), 2);
		double cost = payment.getValue(LocalDateTime.of(2020, 5, 19, 14, 30));
		assertTrue(cost == 4.5 * 2);
	}

}
