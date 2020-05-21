package system;

import user.User;
import user.Card;

import bike.Bike;

import java.awt.desktop.UserSessionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import station.Station;
import station.Terminal;

public class VelibSystem {

	private static Station[] stations;
	private static ArrayList<User> users;
	private static RidePlanning rideplan;
	private int countUsers = 0;
	
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
			stations[i] = new Station(i,true,new GPS(10*rand.nextDouble(),10*rand.nextDouble()),numSlots);
			stations[i].setTerminal(new Terminal(stations[i]));
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



	public void addUser(GPS location, String credit_card_number) {
		VelibSystem.users.add(new User(countUsers, location, credit_card_number));
		countUsers += 1;
	}
	
	public Station[] PlanRide(GPS start, GPS finish,Class <?> bikeType) {
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
		// complete later
	}
	
	public static void chargeUserTime(User usr, double value) {
		// complete later
	}
	
	public static void addUserTime(User usr, Bike bike) {
		// complete later
	}
	
	public static Station getStationByBike(Bike b) {
		for (Station s: VelibSystem.stations) {
			if (s.getBikeByID(b) != null)
				return s;
		}
		System.out.println("Bike is not in any station");
		return null;
	}
	
	public static User getUserByRegistrationCard(Card c) {
		for (User u : VelibSystem.users) {
			if (u.getRegistrationCard().getID() == c.getID())
				return u;
		}
		return null;
	}
	
	public static ArrayList <User> getUsersWithCreditCard(String card_number) {
		ArrayList <User> us = new ArrayList <User> ();
		for (User u : VelibSystem.users) {
			if (u.getCreditCard().equals(card_number)) {
				us.add(u);
			}
		}
		return us;
	}
	
	// auxiliary function for RidePlanning interface implementation
	public static Station argmin(HashMap<Station,Double> dict, ArrayList<Station> keys) {
		
		Station minStation = null;
		Double minValue = Double.MAX_VALUE;
		for(Station station:keys) {
			double value = dict.get(station);
			if(value < minValue) {
				minValue = value;
				minStation = station;
			}
		}
		return minStation;
	}
	
	// Sort of a toString analog
	public static void printSystemInfo() {
		String stationsInfo = "";
		String usersInfo = "";
		for (Station station:stations)
			stationsInfo += station.toString() + " ";
		for (User user:users) 
			usersInfo += user.toString() + " ";
		System.out.println("Stations " + stationsInfo);
		System.out.println("Users " + usersInfo);
		System.out.println("RidePlanning" + rideplan.toString());
	}
}
