package user;

import java.time.LocalDateTime;

public class PaymentVlibre extends Payment{
	
	private double hour_cost;
	private double hour_credit;

	public PaymentVlibre(LocalDateTime start, double cost, double credit) {
		super(start);
		hour_cost = cost;
		hour_credit = credit;
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
