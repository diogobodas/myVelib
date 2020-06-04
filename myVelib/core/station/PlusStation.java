package station;

import system.GPS;
import user.User;

/**
 * Class representing plus station. It is an extension of the station class that gives time credit to users who drop their bikes into its slots.
 * 
 *
 */
public class PlusStation extends Station {

	/**
	 * Initializes PlusStation. Behaves in the same way as its parent class. See {@link station.Station}
	 */
	public PlusStation(int id_num, boolean online, GPS coord, Terminal t, int number_of_slots) {
		super(id_num, online, coord, t, number_of_slots);
	}
	
	/**
	 * Initializes PlusStation. Behaves in the same way as its parent class. See {@link station.Station}
	 */
	public PlusStation(int id_num, boolean online, GPS coord, int number_of_slots) {
		super(id_num, online, coord, number_of_slots);
	}
	
	/**
	 * Charges user and adds time credit. Overrides {@link station.Station#chargeUser(User, double, long)}.
	 * @param usr User to be charged.
	 * @param money Value to be charged of user.
	 * @param time_credit Time credit to be removed from user's current credit balance.
	 */
	@Override
	public void chargeUser(User usr, double money, long time_credit) {
		usr.getUsrBalance().addCharge(money);
		if (usr.getRegistrationCard() != null) {
			usr.getRegistrationCard().takeCredit(time_credit);
			usr.getRegistrationCard().addCredit(5); // adds 5 minutes to credit since it's plus station
		}
	}

}
