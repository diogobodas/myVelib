package user;

import java.time.Duration;
import java.time.LocalDateTime;

public class PaymentVmax extends Payment{
	
	private double hour_cost;

	public PaymentVmax(LocalDateTime start) {
		super(start);
		this.hour_cost = 1.0;
	}

	@Override
	public double getValue(LocalDateTime end_time) {
		long num_minutes = Duration.between(this.getStartTime(), end_time).toMinutes();
		if (num_minutes > 60)
			return (hour_cost/60.0) * (num_minutes - 60);
		return 0;
	}

}
