package view;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

import exceptions.IncompatibleArgumentsException;
import model.MyVelibModel;
import station.LeastOccupiedComparator;
import station.MostUsedComparator;
import station.Station;
import system.VelibSystem;
import user.User;

@SuppressWarnings("deprecation")
/**
 * View for the Velub System CLUI.
 *
 */
public class MyVelibView implements Observer{
	
	private MyVelibModel model;
	
	public MyVelibView(MyVelibModel model) {
		super();
		this.model = model;
		this.model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
	}
	
	public void displayUser(MyVelibModel m,Integer ID) throws Exception{
		User user = VelibSystem.getUserByID(ID);
		if (user == null)
			throw new Exception("User not found");
		System.out.println(user.getName() + "'s " + user.getUsrBalance().toString());
		
	}
	
	public void displayStation(MyVelibModel m,Integer ID) throws Exception{
		Station station = VelibSystem.getStationByID(ID);
		if (station == null)
			throw new Exception("User not found");
		System.out.println("Station with ID:" + String.valueOf(station.getId()) + " " +  station.getBalance().toString());
	} 
	
	public void sortStation(MyVelibModel model,String policy) throws Exception{
		ArrayList<Station> stations = new ArrayList<Station>(Arrays.asList(model.getSystem().getStations()));
		if (policy.equals("mostUsed")) {
			LeastOccupiedComparator comparator = new LeastOccupiedComparator();
			Collections.sort(stations,comparator);
		}
		else if (policy.equals("leastOccupied")) {
			MostUsedComparator comparator = new MostUsedComparator();
			Collections.sort(stations,comparator);
		}
		else
			throw new IncompatibleArgumentsException("Policy for sorting stations non existant");
	
		for (Station station:stations) {
			System.out.println(station.toString());
		}
	}
	
	public void display(MyVelibModel model) {
		System.out.println("System name: " + model.getName());
		VelibSystem.printSystemInfo();
	}

}
