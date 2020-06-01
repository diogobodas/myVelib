package user;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Extension of abstract class {@link user.Payment} that represents payment regime for Vlibre holders.
 * In this payment regime, the first hour is always free and the following ones cost one euro regardless of bike type.
 *
 */
public class PaymentVmax extends Payment{
	
	private double hour_cost;

	/**
	 * Initialized Vmax payment regime
	 * @param start LocalDateTime of rental start
	 */
	public PaymentVmax(LocalDateTime start) {
		super(start);
		this.hour_cost = 1.0;
	}

	/**
	 * Overriden method for getting the price of a ride according to Vmax rules
	 */
	@Override
	public double getValue(LocalDateTime end_time) {
		long num_minutes = Duration.between(this.getStartTime(), end_time).toMinutes();
		if (num_minutes > 60)
			return (hour_cost/60.0) * (num_minutes - 60);
		return 0;
	}
	
	public double getHourCost() {
		return hour_cost;
	}

	public void setHourCost(double hour_cost) {
		this.hour_cost = hour_cost;
	}

}
