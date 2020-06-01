package system;

import user.User;
import user.Card;

import bike.Bike;

import java.awt.desktop.UserSessionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import station.ParkingSlot;
import station.Station;
import station.Terminal;

public class VelibSystem {

	private static Station[] stations;
	private static ArrayList<User> users;
	private static RidePlanning rideplan;
	private int countUsers = 0;
	static private int N;
	static private int M;
	static private double squareLength;
	static private String name;
	static public LocalDateTime initTime = LocalDateTime.of(2020, 6, 1, 8,30);
	
	// Creates N stations, summing up to M parking Slots
	// Each station will have Floor(M/N) slots and if
	// M is not divisible by N, one station will have Floor(M/N) + M mod N stations
	public VelibSystem(int N,int M) {
		this.N = N;
		this.M = M;
		VelibSystem.users = new ArrayList<User>();
		VelibSystem.rideplan = new StandardPlanning();
		stations = new Station[N];
		Random rand = new Random();
		for (int i = 0; i < N;i ++) {
			int numSlots;
			if (i < N -1)
				numSlots = M/N;
			else
				numSlots = M/N + M % N;
			stations[i] = new Station(i,true,new GPS(10*rand.nextDouble(),10*rand.nextDouble()),numSlots);
			stations[i].setTerminal(new Terminal(stations[i]));
		}
			
	}
	
	public VelibSystem(int N,int M,double s) {
		this.N = N;
		this.M = M;
		this.squareLength = s;
		VelibSystem.users = new ArrayList<User>();
		VelibSystem.rideplan = new StandardPlanning();
		stations = new Station[N];
		Random rand = new Random();
		for (int i = 0; i < N;i ++) {
			int numSlots;
			if (i < N -1)
				numSlots = M/N;
			else
				numSlots = M/N + M % N;
			stations[i] = new Station(i,true,new GPS(s*rand.nextDouble(),s*rand.nextDouble()),numSlots);
			stations[i].setTerminal(new Terminal(stations[i]));
		}
			
	}
	
	public VelibSystem(int N,int M,double s, double empty, double regular, String name) {
		this.N = N;
		this.M = M;
		this.squareLength = s;
		this.name = name;
		VelibSystem.users = new ArrayList<User>();
		VelibSystem.rideplan = new StandardPlanning();
		stations = new Station[N];
		Random rand = new Random();
		for (int i = 0; i < N;i ++) {
			int numSlots;
			if (i < N -1)
				numSlots = M/N;
			else
				numSlots = M/N + M % N;
			stations[i] = new Station(i,true,new GPS(s*rand.nextDouble(),s*rand.nextDouble()),numSlots, empty, regular);
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
	
	public void addUser(GPS location, String credit_card_number,Card registrationCard) {
		VelibSystem.users.add(new User(countUsers, location, credit_card_number,registrationCard));
		countUsers += 1;
	}
	
	/**
	 * Used in CLUI addUser
	 * @param name
	 * @param registrationCard
	 */
	public void addUser(String name,Card registrationCard) {
		VelibSystem.users.add(new User(name,countUsers,registrationCard));
		countUsers += 1;
	}
	
	public void addUser(User usr) {
		VelibSystem.users.add(usr);
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
	
	public static Station getStationBySlot(ParkingSlot slot) {
		
		int stationId = slot.getId()/100;
		for (Station station: VelibSystem.stations) {
			if (station.getId() == stationId)
				return station;
		}
		return null;

	}
	
	public static Station getStationByBike(Bike b) {
		for (Station s: VelibSystem.stations) {
			if (s.getBikeByID(b.getID()) != null)
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
	
	public static Station getStationByID(int id) {
		for (Station station : VelibSystem.stations)
			if (station.getId() == id)
				return station;
		return null;
	}
	
	public static User getUserByID(int id) {
		for (User user : VelibSystem.users)
			if (user.getID() == id)
				return user;
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

	public static int getN() {
		return N;
	}

	public static int getM() {
		return M;
	}

	public static double getSquareLength() {
		return squareLength;
	}
	
}
