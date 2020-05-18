package user;

import java.time.LocalDateTime;

public class PaymentStd extends Payment{
	
	private double hour_cost;

	public PaymentStd(LocalDateTime start, double cost) {
		super(start);
		this.hour_cost = cost;
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
