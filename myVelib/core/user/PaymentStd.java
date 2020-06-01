package user;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Extension of abstract class {@link user.Payment} that represents standard payment regime.
 * That means the hourly cost provided (1.0 for the regular bike, 2.0 for the electric bike) is divided by 60 and multiplied by the rental duration in minutes.
 */
public class PaymentStd extends Payment{
	
	private double hour_cost;

	/**
	 * Initializes a standard payment regime
	 * @param start Start LocalDateTime of rental 
	 * @param cost Hourly cost for the bike. One for regular bikes, two for electric, easily extendible for new bikes that are more expensive per hour
	 */
	public PaymentStd(LocalDateTime start, double cost) {
		super(start);
		this.hour_cost = cost;
	}

	/**
	 * Gets the price of rental. See the abstract class documentation for further info.
	 */
	@Override
	public double getValue(LocalDateTime end_time) {
		// this method will calculate the price in minutes of used bike 
		// ex: 10h to 14h30 -> 270 min -> cost = 270 * (hour_cost/60)
		long num_minutes = Duration.between(this.getStartTime(), end_time).toMinutes();
		return (hour_cost/60.0) * (double) num_minutes;
	}

	public double getHourCost() {
		return hour_cost;
	}

	public void setHourCost(double hour_cost) {
		this.hour_cost = hour_cost;
	}
	
	

}
