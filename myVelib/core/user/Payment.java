package user;
import user.User;
import bike.Bike;
import bike.ElectricBike;
import bike.RegularBike;

import java.time.LocalDateTime;

public abstract class Payment {
	// attributes
	private LocalDateTime rent_start_time;
	
	public Payment(LocalDateTime start) {
		rent_start_time = start;
	}
	
	// abstract method to be implemented by concrete classes with payments
	// should return value of the payment to be done
	public abstract double getValue(LocalDateTime end_time);
	
	// Method for returning eventual discounts in time. Since not all payment methods use it
	// It is defaulted in the abstract class as as a return 0 method
	// The time discount is counted exclusively in integer minutes (seconds are not considered)
	// When used, should return a the discount in minutes
	public long getTimeDiscount(LocalDateTime end_time) {
		return 0;
	}
	
	// static method for generating correct payment class
	// Payment abstract class acts as factory for generating correct payment methods through this method
	public static Payment createAdequatePayment(User usr, Bike bike) {
		if (usr.getRegistrationCard() == null) {
			if (bike.getClass() == RegularBike.class)
				return (Payment) new PaymentStd(LocalDateTime.now(), 1.0);
			else
				return (Payment) new PaymentStd(LocalDateTime.now(), 2.0);
		}
		else if (usr.getRegistrationCard().getClass() == Vlibre.class) {
			if (bike.getClass() == RegularBike.class)
				return (Payment) new PaymentVlibre(LocalDateTime.now(), 1.0,(long) usr.getRegistrationCard().getCredit());
			else 
				return (Payment) new PaymentVlibre(LocalDateTime.now(), 2.0, (long) usr.getRegistrationCard().getCredit());
		}
		else 
			return (Payment) new PaymentVmax(LocalDateTime.now());
	}

	public LocalDateTime getStartTime() {
		return rent_start_time;
	}

	public void setStartTime(LocalDateTime rent_start_time) {
		this.rent_start_time = rent_start_time;
	}
}
