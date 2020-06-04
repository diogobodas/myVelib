package view;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

import bike.RegularBike;
import exceptions.IncompatibleArgumentsException;
import model.MyVelibModel;
import station.LeastOccupiedComparator;
import station.MostUsedComparator;
import station.ParkingSlot;
import station.Station;
import system.VelibSystem;
import user.User;
import user.Vmax;

@SuppressWarnings("deprecation")
/**
 * View for the Velub System CLUI. (Observes MyVelibModel)
 *
 */
public class MyVelibView implements Observer{
	
	private MyVelibModel model;
	
	/**
	 * Basic constructor for MyVelibView, needs a model to perform its operations.
	 * @param model MyVelibModel 
	 */
	public MyVelibView(MyVelibModel model) {
		super();
		this.model = model;
		this.model.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
	}
	/**
	 * Prints user's statistics
	 * @param m MyVelibModel
	 * @param ID
	 * @throws Exception
	 */
	public void displayUser(MyVelibModel m,Integer ID) throws Exception{
		User user = VelibSystem.getUserByID(ID);
		if (user == null)
			throw new Exception("User not found");
		System.out.println(user.getName() + "'s " + user.getUsrBalance().toString());
		
	}
	/**
	 * Prints user's state and information, such as ID, name, location, its bike type if any and also its card type if any.
	 * @param m MyVelibModel
	 * @param ID
	 * @throws Exception
	 */
	public void userState(MyVelibModel m,Integer ID) throws Exception {
		User user = VelibSystem.getUserByID(ID);
		if (user == null)
			throw new Exception("User not found");
		String toPrint;
		toPrint = ("User ID: " + String.valueOf(user.getID()) + ", User name: " + user.getName() + ", Location: (" + 
			String.valueOf(user.getLocation().getX())) + "," + String.valueOf(user.getLocation().getY()) + "), ";
		
		if (user.getBike() == null)
			toPrint += "No bike, ";
		else if ( user.getBike().getClass() == RegularBike.class)
			toPrint += "Holds a regular bike, ";
		else
			toPrint += "Holds an electric bike, ";
		if (user.getRegistrationCard() == null)
			toPrint+= "No Card";
		else if( user.getRegistrationCard().getClass() == Vmax.class)
			toPrint += "Holds a vmax card";
		else
			toPrint += "Holds a vlibre card";
		System.out.println(toPrint);
	}
	/**
	 * Prints station state and information, such as id, location and status of each of its slots.
	 * @param m MyVelibModel
	 * @param ID
	 * @throws Exception
	 */
	public void stationState(MyVelibModel m,Integer ID) throws Exception{
		Station station = VelibSystem.getStationByID(ID); 
		if (station == null)
			throw new Exception("User not found");
		String toPrint;
		toPrint = "Station ID :" + String.valueOf(ID) + " ";
		toPrint += "Location: (" + String.valueOf(station.getCoordinates().getX()) + "," +
		String.valueOf(station.getCoordinates().getY()) + "), ";
		for (ParkingSlot slot:station.getSlots()) {
			toPrint += slot.toString();
		}
		System.out.println(toPrint);
	}
	/**
	 * Prints station's statistics.
	 * @param m MyVelibModel
	 * @param ID
	 * @throws Exception
	 */
	public void displayStation(MyVelibModel m,Integer ID) throws Exception{
		Station station = VelibSystem.getStationByID(ID);
		if (station == null)
			throw new Exception("Station not found");
		System.out.println("Station with ID:" + String.valueOf(station.getId()) + " " +  station.getBalance().toString());
	} 
	/**
	 * Prints all station sorted by a specified policy.
	 * @param model MyVelibModel
	 * @param policy String in set = {"leastOccupied","mostUsed"}
	 * @throws Exception
	 */
	public void sortStation(MyVelibModel model,String policy) throws Exception{
		ArrayList<Station> stations = new ArrayList<Station>(Arrays.asList(model.getSystem().getStations()));
		if (policy.equals("mostUsed")) {
			MostUsedComparator comparator = new MostUsedComparator();
			Collections.sort(stations,comparator);
		}
		else if (policy.equals("leastOccupied")) {
			LeastOccupiedComparator comparator = new LeastOccupiedComparator();
			Collections.sort(stations,comparator);
		}
		else
			throw new IncompatibleArgumentsException("Policy for sorting stations non existant");
	
		System.out.println("Station sorted with policy: " + policy);
		
		for (Station station:stations) {
			System.out.println(station.getBalance().toString());
		}
	}
	/**
	 * Display's all the system's most relevant information, including which stations are online or offline.
	 * @param model MyVelibModel
	 */
	public void display(MyVelibModel model) {
		System.out.println("System name: " + model.getName());
		VelibSystem.printSystemInfo();
	}

}
