package user;

import java.time.Duration;
import java.time.LocalDateTime;

public class PaymentVlibre extends Payment{
	
	private double hour_cost;
	private long minutes_credit;

	// attention cost is given by hour, but credit is considered in minutes
	public PaymentVlibre(LocalDateTime start, double cost, long credit) {
		super(start);
		hour_cost = cost;
		minutes_credit = credit;
	}
	
	@Override
	public long getTimeDiscount(LocalDateTime end_time) {
		long num_minutes = Duration.between(this.getStartTime(), end_time).toMinutes();
		if (num_minutes <= 60) {
			return 0;
		} else {
			num_minutes = num_minutes - 60;
			if (num_minutes >= minutes_credit) {
				return minutes_credit;
			} else {
				return num_minutes;
			}
		}
	}

	// Note for report :
	// We considered that for the Vlibre there is the time discount and a discount of 
	// one euro in the price for one hour, which is equal to the description given and is
	// reflected on the code. In case a new bike is added, it can also follow this same rule
	// or one can extend through creation of another payment subclass
	@Override
	public double getValue(LocalDateTime end_time) {
		long num_minutes = Duration.between(this.getStartTime(), end_time).toMinutes();
		long time_discount = this.getTimeDiscount(end_time);
		num_minutes = num_minutes - time_discount;
		if (num_minutes <= 60) {
			return (double) num_minutes * ((hour_cost - 1)/60.0);
		} else {
			return (double) hour_cost - 1 + (hour_cost/60.0) * (num_minutes - 60);
		}
	}

}
