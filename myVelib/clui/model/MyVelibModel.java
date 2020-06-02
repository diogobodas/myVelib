package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

import user.Card;

import bike.ElectricBike;
import bike.RegularBike;
import station.Station;
import system.GPS;
import system.VelibSystem;
import user.User;
import user.Vlibre;
import user.Vmax;

/**
 * Model to be used during CLUI use of the system
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
			file = new FileReader("clui/main/my_velib.ini");
			reader = new BufferedReader(file);
			String line;
			if ((line = reader.readLine()) == null) {
				throw new Exception("Ini file not correctly configured");
			}
			else {
				String[] args = line.split(" ");
				if (args.length != 5)
					throw new Exception("Ini file not correctly configured");
				String name = args[0];
				int nStations = Integer.valueOf(args[1]);
				int nSlots = Integer.valueOf(args[2]);
				double s = Double.valueOf(args[3]);
				int nBikes = Integer.valueOf(args[4]);
				double occupationRate = ( (double) nBikes/(nSlots));
				system = new VelibSystem(nStations,nSlots,s,1 - occupationRate,0.7,name);
			}
		}
		catch (Exception e) {
			throw new Exception(e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e) {}
			}
		}
	}
	
	
	
	/**
	 * Method for adding user to existing VelibSystem Model
	 * @param userName Username for created user
	 * @param cardType Type of card, if user possesses any
	 * @param velibNetworkName Network being updated
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
		this.notifyObservers("Added user");
	}
	
	public void setup(String velibNetworkName) {
		system = new VelibSystem(10, 100, 4.0, 0.25, 0.7, velibNetworkName); 
		this.setChanged();
		this.notifyObservers("Default setup for " + velibNetworkName + ": " + "\n" + "10 stations, 100 bikes, 4km square side, occupation rate of 75% and electric bike rate of 30%");	
	}
	
	public void setup(String velibNetworkName,int nStations,int nSlots,double s,int nBikes) {
		double occupationRate = ( (double) nBikes/(nSlots));
		system = new VelibSystem(nStations, nSlots, s, 1- occupationRate, 0.7, velibNetworkName); 
		this.setChanged();
		this.notifyObservers("Default setup for " + velibNetworkName + ": " + "\n" + String.valueOf(nStations) + " stations, "+ String.valueOf(nSlots) + " bikes," + String.valueOf(s) + "km square side, occupation rate of " + String.valueOf((double) 100*(occupationRate)) + "% and electric bike rate of 30%");	
	}
	
	public void offline(int ID,LocalDateTime time) throws Exception{
		Station station = VelibSystem.getStationByID(ID);
		if (station == null)
			throw new Exception("Station not found");
		station.setStationOffline(time);
		this.setChanged();
		this.notifyObservers("Station with ID:" + String.valueOf(ID) + " is set offline");
	}
	
	public void online(int ID,LocalDateTime time) throws Exception{
		Station station = VelibSystem.getStationByID(ID);
		if (station == null)
			throw new Exception("Station not found");
		station.setStationOnline(time);
		this.setChanged();
		this.notifyObservers("Station with ID:" + String.valueOf(ID) + " is set online");
	
	}
	
	public void rentBike(int userID,int stationID,LocalDateTime time,String bykeType) throws Exception{
		User user = VelibSystem.getUserByID(userID);
		if (user == null)
			throw new Exception("User not found");
		Station station = VelibSystem.getStationByID(stationID);
		if (station == null)
			throw new Exception("Station not found");
		Class <?> type;
		if (bykeType.equals("regular")) {
			type = RegularBike.class;
		}
		else if (bykeType.equals("electric")) {
			type = ElectricBike.class;
		}
		else
			throw new Exception("Bike type does not exist");
		user.rentBike(station, type, time);
		this.setChanged();
		this.notifyObservers("Bike rented by User with Id" + String.valueOf(userID) + "on station with ID:" + String.valueOf(stationID) + " of type" + String.valueOf(bykeType));
	
	}
	
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
