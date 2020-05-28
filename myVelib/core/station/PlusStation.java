package station;

import system.GPS;
import user.User;

public class PlusStation extends Station {

	public PlusStation(int id_num, boolean online, GPS coord, Terminal t, int number_of_slots) {
		super(id_num, online, coord, t, number_of_slots);
	}
	
	@Override
	public void chargeUser(User usr, double money, long time_credit) {
		usr.getUsrBalance().addCharge(money);
		usr.getRegistrationCard().takeCredit(time_credit);
		usr.getRegistrationCard().addCredit(5); // adds 5 minutes to credit since it's plus station
	}

}
