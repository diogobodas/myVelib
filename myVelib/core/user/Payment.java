package user;
import user.User;
import bike.Bike;
import bike.ElectricBike;
import bike.RegularBike;

import java.time.LocalDateTime;

/**
 * 
 * Abstract class representing a payment regime for an user. It was implemented to ensure the strategy design pattern for getting the value of the rents.
 *
 */
public abstract class Payment {
	// attributes
	private LocalDateTime rent_start_time;
	
	/**
	 * Initializes a Payment object and saves in it the starting time of the rent operation, so that final value can be easily deduced
	 * @param start LocalDateTime object for the time the bike was rented.
	 */
	public Payment(LocalDateTime start) {
		rent_start_time = start;
	}
	
	/**
	 * Method that gets the correct price of the bike rent
	 * @param end_time LocalDateTime object for the return of the bike. 
	 * @return Double with the price of the bike rental.
	 */
	public abstract double getValue(LocalDateTime end_time);
	
	/**
	 * Gets the value of the time discount that can be used for one ride when it applies. By default it returns zero. See {@link user.PaymentVlibre} to see how it is overriden.
	 * @param end_time LocalDateTime object for the return of the bike. 
	 * @return Integer with the number of minutes to be used in discount
	 */
	public long getTimeDiscount(LocalDateTime end_time) {
		return 0;
	}
	
	/**
	 * Static method for generating the correct payment class. It is intended to be so that the Payment abstract class acts as a factory for generating correct payment regimes following the factory pattern
	 * @param usr User instance that is renting the bike 
	 * @param bike Bike instance being rented
	 * @param time LocalDateTime with time of rental
	 * @return Payment instance with adequate payment regime
	 */
	public static Payment createAdequatePayment(User usr, Bike bike, LocalDateTime time) {
		if (usr.getRegistrationCard() == null) {
			if (bike.getClass() == RegularBike.class)
				return (Payment) new PaymentStd(time, 1.0);
			else
				return (Payment) new PaymentStd(time, 2.0);
		}
		else if (usr.getRegistrationCard().getClass() == Vlibre.class) {
			if (bike.getClass() == RegularBike.class)
				return (Payment) new PaymentVlibre(time, 1.0,(long) usr.getRegistrationCard().getCredit());
			else 
				return (Payment) new PaymentVlibre(time, 2.0, (long) usr.getRegistrationCard().getCredit());
		}
		else 
			return (Payment) new PaymentVmax(time);
	}

	public LocalDateTime getStartTime() {
		return rent_start_time;
	}

	public void setStartTime(LocalDateTime rent_start_time) {
		this.rent_start_time = rent_start_time;
	}
}
