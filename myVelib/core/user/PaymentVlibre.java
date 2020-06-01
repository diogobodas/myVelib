package user;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Extension of abstract class {@link user.Payment} that represents payment regime for Vlibre holders.
 * The general rule is that  the first hour has a reduced cost of one euro  and that the following hours have the normal price. Moreover, it is possible to use time credit discounts here.
 * Can be generalized for the creation of more expensive bikes that respect this rule.
 *
 */
public class PaymentVlibre extends Payment{
	
	private double hour_cost;
	private long minutes_credit;

	/**
	 * Initializes Vlibre payment regime
	 * @param start LocalDateTime of the start of the rental
	 * @param cost Hourly cost for the bike. One for regular bikes, two for electric bikes
	 * @param credit Time credit disposed by the user. Necessary for calculation of rental price and discount.
	 */
	public PaymentVlibre(LocalDateTime start, double cost, long credit) {
		super(start);
		hour_cost = cost;
		minutes_credit = credit;
	}
	/**
	 * Overriden getTimeDiscount method. If the ride is longer than one hour the user is allowed to use its credit minutes as discount. If the time surpassing one hour is higher than the quantity of credits the user has, it automatically discounts all the minutes available. Otherwise, it will just set the ride time to one hour and spend the necessary amount of minute credits to do so.
	 */
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

	/**
	 * Overriden method for getting the correct ride price. The user has a reduced price for the first hour and can use his time credits as discount for the following ones.
	 */
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

	public double getHourCost() {
		return hour_cost;
	}

	public void setHourCost(double hour_cost) {
		this.hour_cost = hour_cost;
	}

	public long getMinutesCredit() {
		return minutes_credit;
	}

	public void setMinutesCredit(long minutes_credit) {
		this.minutes_credit = minutes_credit;
	}
	
	
	
	

}
