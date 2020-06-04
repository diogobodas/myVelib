package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

import user.Card;

import bike.ElectricBike;
import bike.RegularBike;
import controller.MyVelibController;
import station.Station;
import station.StationBalance;
import system.GPS;
import system.VelibSystem;
import user.User;
import user.Vlibre;
import user.Vmax;
import view.MyVelibView;

/**
 * Model to be used during CLUI use of the system (Observed by View)
 * 
 *
 */
@SuppressWarnings("deprecation")
public class MyVelibModel extends Observable{
	
	private VelibSystem system;
	private String name;
	
	/**
	 * Setup for model to be used
	 */
	public MyVelibModel() throws Exception{
		
		FileReader file = null;
		BufferedReader reader = null;
		
		try {
			file = new FileReader("eval/inputFiles/my_velib.ini");
			reader = new BufferedReader(file);
			MyVelibController control = new MyVelibController(this,null);
			String line;
			while((line = reader.readLine()) != null) {
				String[] command = control.parseCommand(line);
				control.executeCommand(command);
				}
			System.out.println("Config file loaded!");
		}
		catch (Exception e) {
			throw new Exception("Ini file not correctly configured");
		}
		finally {
			if(reader != null)
				reader.close();
		}
		
	}
	
	
	
	/**
	 * Method for adding user to existing VelibSystem Model
	 * @param userName Username for created user
	 * @param cardType Type of card, if user possesses any
	 */
	public void addUser(String userName, String cardType) {
		Card card;
		if(cardType.equals("vmax"))
			card = new Vmax();
		else if(cardType.equals("vlibre"))
			card = new Vlibre();
		else 
			card = null;
		system.addUser(userName,card);
		this.setChanged();
		this.notifyObservers("Added user " + userName);
	}
	/**
	 * Sets time window for rate of occupation calculation (Station statistics). 
	 * VERY IMPORTANT to set a time window that comprises all operations! (As specified on the report)
	 * @param ts time Start
	 * @param te time End
	 */
	public void setTimeWindow(LocalDateTime ts,LocalDateTime te) {
		StationBalance.ts = ts;
		StationBalance.te = te;
	}
	/**
	 * setup a system with the given name, 10 stations, 100 slots, map of a square of 4Km X 4Km and 75 bikes. 
	 * @param velibNetworkName
	 */
	public void setup(String velibNetworkName) {
		system = new VelibSystem(10, 100, 4.0, 0.25, 0.7, velibNetworkName);
		name = velibNetworkName;
		this.setChanged();
		this.notifyObservers("Default setup for " + velibNetworkName + ": " + "\n" + "10 stations, 100 slots, 4km square side, occupation rate of 75% and electric bike rate of 30%");	
	}
	/**
	 * setup a system with the given name, nStations stations, nSlots slots, map of a square of sKm X sKm and nBikes bikes. 
	 * @param velibNetworkName
	 * @param nStations int number of stations
	 * @param nSlots int number of slots
	 * @param s double side
	 * @param nBikes int number of bikes
	 */
	public void setup(String velibNetworkName,int nStations,int nSlots,double s,int nBikes) {
		double occupationRate = ( (double) nBikes/(nSlots));
		system = new VelibSystem(nStations, nSlots, s, 1- occupationRate, 0.7, velibNetworkName);
		name = velibNetworkName;
		this.setChanged();
		this.notifyObservers("Default setup for " + velibNetworkName + ": " + "\n" + String.valueOf(nStations) + " stations, "+ String.valueOf(nSlots) + " slots," + String.valueOf(s) + "km square side, occupation rate of " + String.valueOf((double) 100*(occupationRate)) + "% and electric bike rate of 30%");	
	}
	/**
	 * Sets station with id = ID offline.
	 * @param ID int id
	 * @param time LocalDateTime
	 * @throws Exception
	 */
	public void offline(int ID,LocalDateTime time) throws Exception{
		Station station = VelibSystem.getStationByID(ID);
		if (station == null)
			throw new Exception("Station not found");
		station.setStationOffline(time);
		this.setChanged();
		this.notifyObservers("Station with ID:" + String.valueOf(ID) + " is set offline");
	}
	/**
	 * Sets station with id = ID online.
	 * @param ID int id
	 * @param time LocalDateTime
	 * @throws Exception
	 */
	public void online(int ID,LocalDateTime time) throws Exception{
		Station station = VelibSystem.getStationByID(ID);
		if (station == null)
			throw new Exception("Station not found");
		station.setStationOnline(time);
		this.setChanged();
		this.notifyObservers("Station with ID:" + String.valueOf(ID) + " is set online");
	
	}
	/**
	 * User with id = userId rents a bike in station with id = stationID, at specified time of the desired type = bikeType
	 * @param userID int ID of the user 
	 * @param stationID int ID of the station
	 * @param time LocalDateTime time
	 * @param bikeType String in set = {"regular","electric"}
	 * @throws Exception
	 */
	public void rentBike(int userID,int stationID,LocalDateTime time,String bikeType) throws Exception{
		User user = VelibSystem.getUserByID(userID);
		if (user == null)
			throw new Exception("User not found");
		Station station = VelibSystem.getStationByID(stationID);
		if (station == null)
			throw new Exception("Station not found");
		Class <?> type;
		if (bikeType.equals("regular")) {
			type = RegularBike.class;
		}
		else if (bikeType.equals("electric")) {
			type = ElectricBike.class;
		}
		else
			throw new Exception("Bike type does not exist");
		user.rentBike(station, type, time);
		this.setChanged();
		this.notifyObservers("Bike rented by User with Id" + String.valueOf(userID) + "on station with ID:" + String.valueOf(stationID) + " of type" + String.valueOf(bikeType));
	
	}
	/**
	 * User with id = userId drops a bike in station with id = stationID, at specified time.
	 * @param userID int ID of the user 
	 * @param stationID int ID of the station
	 * @param time LocalDateTime time
	 * @throws Exception
	 */
	public void returnBike(int userID,int stationID,LocalDateTime time) throws Exception{
		User user = VelibSystem.getUserByID(userID);
		if (user == null)
			throw new Exception("User not found");
		Station station = VelibSystem.getStationByID(stationID);
		if (station == null)
			throw new Exception("Station not found");
		user.dropBike(station, time);
		this.setChanged();
		this.notifyObservers("Bike returned by User with Id" + String.valueOf(userID) + "on station with ID:" + String.valueOf(stationID));
	
	}
	/**
	 * Prints to the user the optimal start station and final station, respectively to pick up and drop off the desired bike.
	 * @param xStart double x position of start station
	 * @param yStart double y position of start station
	 * @param xEnd double x position of final station
	 * @param yEnd double y position of final station
	 * @param bikeType
	 * @throws Exception
	 */
	public void planRide(double xStart,double yStart,double xEnd,double yEnd,String bikeType) throws Exception{
		Class <?> type;
		if (bikeType.equals("regular"))
			type = RegularBike.class;
		else if (bikeType.equals("electric"))
			type = ElectricBike.class;
		else
			throw new Exception("Invalid bikeType");
		Station[] stations = system.PlanRide(new GPS(xStart,yStart),new GPS(xEnd,yEnd),type);
		System.out.println("You should start on station " + String.valueOf(stations[0].getId()) + " and drop your bike in station " + String.valueOf(stations[1].getId()));
	}

	public VelibSystem getSystem() {
		return system;
	}

	public void setSystem(VelibSystem system) {
		this.system = system;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
