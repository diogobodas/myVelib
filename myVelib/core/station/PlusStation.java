package station;

import system.GPS;

public class PlusStation extends Station {

	public PlusStation(int id_num, boolean online, GPS coord, Terminal t, int number_of_slots) {
		super(id_num, online, coord, t, number_of_slots);
	}
	
	public void giveTimeCredit() {
		// TO DO
	}

}
