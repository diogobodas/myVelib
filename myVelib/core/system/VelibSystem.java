package system;

import user.User;
import bike.Bike;

import java.awt.desktop.UserSessionEvent;
import java.util.ArrayList;
import java.util.Random;

import station.Station;
import station.Terminal;

public class VelibSystem {

	private static Station[] stations;
	private static ArrayList<User> users;
	private static RidePlanning rideplan;
	
	// Creates N stations, summing up to M parking Slots
	// Each station will have Floor(M/N) slots and if
	// M is not divisible by N, one station will have M mod N stations
	public VelibSystem(int N,int M) {
		VelibSystem.users = new ArrayList<User>();
		VelibSystem.rideplan = new StandardPlanning();
		stations = new Station[N];
		int i = 0;
		int residue = M;
		Random rand = new Random();
		while (residue > 0) {
			residue = residue - M/N;
			int numSlots;
			if (residue > 0)
				numSlots = M/N;
			// Then the last station created will have  M mod N stations
			else
				numSlots = M % N;
			// Stations take care of filling correctly electric/regular bikes and empty slots
			stations[i] = new Station(i,true,new GPS(10*rand.nextDouble(),10*rand.nextDouble()),new Terminal(),numSlots);
		}
			
	}
	
	public Station[] getStations() {
		return stations;
	}



	public void setStations(Station[] stations) {
		VelibSystem.stations = stations;
	}



	public ArrayList<User> getUsers() {
		return users;
	}



	public void setUsers(ArrayList<User> users) {
		VelibSystem.users = users;
	}



	public RidePlanning getRideplan() {
		return rideplan;
	}



	public void setRideplan(RidePlanning rideplan) {
		VelibSystem.rideplan = rideplan;
	}



	public void addUser(String cardType) {
		VelibSystem.users.add(new User(cardType));
	}
	
	public Station[] PlanRide(GPS start, GPS finish,String bikeType) {
		return VelibSystem.rideplan.plan(VelibSystem.stations,start,finish,bikeType);
	}
	
	public static User getUserByBike(Bike b) {
		for (User u : VelibSystem.users) {
			if (u.getBike().getID() == b.getID())
				return u;
		}
		return null;
	}
	
	public static void chargeUserMoney(User usr, double value) {
		
	}
	
	public static void chargeUserTime(User usr, double value) {
		
	}
}
