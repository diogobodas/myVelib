package user;

import java.time.Duration;
import java.time.LocalDateTime;

public class PaymentStd extends Payment{
	
	private double hour_cost;

	public PaymentStd(LocalDateTime start, double cost) {
		super(start);
		this.hour_cost = cost;
	}

	@Override
	public double getValue(LocalDateTime end_time) {
		// this method will calculate the price in minutes of used bike 
		// ex: 10h to 14h30 -> 270 min -> cost = 270 * (hour_cost/60)
		long num_minutes = Duration.between(this.getStartTime(), end_time).toMinutes();
		return (hour_cost/60.0) * (double) num_minutes;
	}

}
