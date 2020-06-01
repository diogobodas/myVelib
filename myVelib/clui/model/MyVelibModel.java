package model;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

import bike.ElectricBike;
import bike.RegularBike;
import station.Station;
import system.VelibSystem;
import user.User;

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
	public MyVelibModel() {
		// completar esse construtor com aqueles bagulhos do .ini
		// ler .ini
		system = null;
	}
	
	/**
	 * Method for adding user to existing VelibSystem Model
	 * @param userName Username for created user
	 * @param cardType Type of card, if user possesses any
	 * @param velibNetworkName Network being updated
	 */
	public void addUser(String userName, String cardType, String velibNetworkName) {
		// completar
		System.out.println("To no addUser");
		this.setChanged();
		this.notifyObservers("Added user");
	}
	
	public void setup(String velibNetworkName) {
		system = new VelibSystem(10, 100, 4.0, 0.25, 0.7, velibNetworkName); 
		this.setChanged();
		this.notifyObservers("Default setup for " + velibNetworkName + ": " + "\n" + "10 stations, 100 bikes, 4km square side, occupation rate of 75% and electric bike rate of 30%");
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
		User user = VelibSystem.getUserByID(userId);
		if (user == null)
			throw new Exception("User not found");
		Station station = VelibSystem.getStationByID(stationID);
		if (station == null)
			throw new Exception("Station not found");
		Class <?> type;
		if (bykeType == "regular") {
			type = RegularBike.class;
		}
		else if (bykeType == "electric") {
			type = ElectricBike.class;
		}
		else
			throw new Exception("Byketype does not exist");
		user.rentBike(station, type, time);
		this.setChanged();
		this.notifyObservers("Bike rented by User with Id" + String.valueOf(userId) + "on station with ID:" + String.valueOf(ID) + " of type" + String.valueOf(bykeType));
	
}
