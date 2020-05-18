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
	public abstract double getValue();
	
	// static method for generating correct payment class
	// Payment abstract class acts as factory for generating correct payment methods through this method
	public static Payment createAdequatePayment(User usr, Bike bike) {
		if (usr.getCard() == null) {
			if (bike.getClass() == RegularBike.class)
				return new PaymentStd(LocalDateTime.now(), 1.0);
			if (bike.getClass() == ElectricBike.class)
				return new PaymentStd(LocalDateTime.now(), 2.0);
		}
		if (usr.getCard().getClass() == Vlibre.class) {
			if (bike.getClass() == RegularBike.class)
				return new PaymentVlibre(LocalDateTime.now(), 1.0, usr.getCard().getCredit());
			if (bike.getClass() == ElectricBike.class)
				return new PaymentVlibre(LocalDateTime.now(), 2.0, usr.getCard().getCredit());
		}
		if (usr.getCard().getClass() == Vmax.class) {
			return new PaymentVmax(LocalDateTime.now());
		}
	}

	public LocalDateTime getRent_start_time() {
		return rent_start_time;
	}

	public void setRent_start_time(LocalDateTime rent_start_time) {
		this.rent_start_time = rent_start_time;
	}
}
