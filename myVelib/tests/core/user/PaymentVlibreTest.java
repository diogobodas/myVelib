package core.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import user.PaymentVlibre;

class PaymentVlibreTest {

	@Test
	void testGetTimeDiscount() {
		PaymentVlibre payment = new PaymentVlibre(LocalDateTime.of(2020, 5, 19, 10, 0), 2, 20);
		long discount = payment.getTimeDiscount(LocalDateTime.of(2020, 5, 19, 11, 15));
		assertTrue(discount == 15);
		discount = payment.getTimeDiscount(LocalDateTime.of(2020, 5, 19, 11, 35));
		assertTrue(discount == 20);
	}

	@Test
	void testGetValue() {
		PaymentVlibre payment = new PaymentVlibre(LocalDateTime.of(2020, 5, 19, 10, 0), 2, 20);
		double cost = payment.getValue(LocalDateTime.of(2020, 5, 19, 11, 15));
		assertTrue(cost == 1.0);
		cost = payment.getValue(LocalDateTime.of(2020, 5, 19, 12, 50));
		assertTrue(cost == 1.5 * 2 + 1); // 1.5 hour normal electric cost + 1 hour reduced cost
		cost = payment.getValue(LocalDateTime.of(2020, 5, 19, 10, 30));
		System.out.println(cost);
		assertTrue(cost == 0.5); // half an hour reduced price
	}

}