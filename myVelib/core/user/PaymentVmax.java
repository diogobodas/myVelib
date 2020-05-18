package user;

import java.time.LocalDateTime;

public class PaymentVmax extends Payment{
	
	private double hour_cost;

	public PaymentVmax(LocalDateTime start) {
		super(start);
		this.hour_cost = 1.0;
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
