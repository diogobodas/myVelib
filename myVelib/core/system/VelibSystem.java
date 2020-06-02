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

/**
 * 
 * Main class used by the myVelib project. It consists of the Java class representing a Velib Network, and also contains all the supplementary methods used by other classes whenever a reference to the system they were into was necessary.
 *
 */
public class VelibSystem {

	public static long seed = 15;
	private static Station[] stations;
	private static ArrayList<User> users;
	private static RidePlanning rideplan;
	private int countUsers = 0;
	static private int N;
	static private int M;
	static private double squareLength;
	static private String name;
	static public LocalDateTime initTime = LocalDateTime.of(2020, 6, 1, 8,30);
	
	
	/**
	 * First constructor created for a VelibSystem network; it should create N stations and have a total of M parking slots.
	 * If M is not divisible by N one station will have Floor(M/N) + M mod N stations. The stations are created with a default value of 70% occupation rate and 30% of electrical bikes.
	 * @param N Number of stations
	 * @param M Total number of slots 
	 */
	public VelibSystem(int N,int M) {
		this.N = N;
		this.M = M;
		VelibSystem.users = new ArrayList<User>();
		VelibSystem.rideplan = new StandardPlanning();
		stations = new Station[N];
		Random rand = new Random(VelibSystem.seed);
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
	
	/**
	 * Second constructor now adding the side of the square area where the network is deployed; see {@link #VelibSystem(int, int)} for more details.
	 * The network is created with default 70% occupation rate and 30% electric bikes
	 * @param N Number of stations
	 * @param M Total number of slots
	 * @param s Side of the square area where the system is deployed
	 */
	public VelibSystem(int N,int M,double s) {
		this.N = N;
		this.M = M;
		this.squareLength = s;
		VelibSystem.users = new ArrayList<User>();
		VelibSystem.rideplan = new StandardPlanning();
		stations = new Station[N];
		Random rand = new Random(VelibSystem.seed);
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
	
	/**
	 * Final version of the constructor, it should be prioritized for usage over the other two. Creates a myVelib network with N stations summing up to M parking slots with a custom percentage of occupation and a custom percentage of regular bikes.
	 * @param N Number of stations
	 * @param M Total number of parking slots
	 * @param s Side of the square area where the system is deployed
	 * @param empty Desired percentage of empty slots. Due to rounding since the number of empty slots is an integer, the actual number of empty slots will be in fact at most the percentage given but may be slightly smaller
	 * @param regular Desired percentage of regular bikes. Again the rounding observation applies and the rate of regular bikes will be at most equal to this percentage. In general it might be slightly smaller.
	 * @param name Name given to identify the system
	 */
	public VelibSystem(int N,int M,double s, double empty, double regular, String name) {
		this.N = N;
		this.M = M;
		this.squareLength = s;
		this.name = name;
		VelibSystem.users = new ArrayList<User>();
		VelibSystem.rideplan = new StandardPlanning();
		stations = new Station[N];
		Random rand = new Random(VelibSystem.seed);
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

	/**
	 * Method for adding user to the system mainly used in testing. It gives the user an unique ID and sets all of its other attributes to null.
	 * @param location GPS oject with coordinates for the user
	 * @param credit_card_number String containing the credit card identifier of the user
	 */
	public void addUser(GPS location, String credit_card_number) {
		VelibSystem.users.add(new User(countUsers, location, credit_card_number));
		countUsers += 1;
	}
	
	/**
	 * Same as {@link #addUser(GPS, String)} but with the addition of a registration card for the user.
	 * @param location GPS object with coordinates for the user
	 * @param credit_card_number String containing the credit card identifier of the user
	 * @param registrationCard Card object with the registrationCard of the user, if he has any
	 */
	public void addUser(GPS location, String credit_card_number,Card registrationCard) {
		VelibSystem.users.add(new User(countUsers, location, credit_card_number,registrationCard));
		countUsers += 1;
	}
	
	/**
	 * Method to add user to the system. It is the one used by the CLUI in the command addUser.
	 * In this method the user also gets a name to identify him, and his credit card is the same as his ID. This was made this way because of the lack of specifications concerning users credit card.
	 * @param name Name of the user being registered
	 * @param registrationCard Card object representing the registration card of the added user
	 */
	public void addUser(String name,Card registrationCard) {
		VelibSystem.users.add(new User(name,countUsers,registrationCard));
		countUsers += 1;
	}
	
	/**
	 * Used to add user to system only in testing context. The function is given directly an user object and should not be used outside of test environment.
	 * @param usr User to add to VelibNetwork.
	 */
	public void addUser(User usr) {
		VelibSystem.users.add(usr);
		countUsers += 1;
	}
	
	/**
	 * Uses the RidePlanning object belonging to the system to plan a ride to the user based on its stations. See {@link system.RidePlanning}
	 * @param start Start coordinate of the user
	 * @param finish End coordinate of the user 
	 * @param bikeType Desired bike type for the ride
	 * @return Array of stations containing the two optimal stations for start and end, in this order.
	 */
	public Station[] PlanRide(GPS start, GPS finish,Class <?> bikeType) {
		return VelibSystem.rideplan.plan(VelibSystem.stations,start,finish,bikeType);
	}
	
	/**
	 * Method for getting user in the ArrayList users of the system based on the bike he possesses
	 * @param b Bike object in which the search is based
	 * @return Null if no user registered in the system has that bike, else returns the user with the given bike
	 */
	public static User getUserByBike(Bike b) {
		for (User u : VelibSystem.users) {
			if (u.getBike() != null) {
				if (u.getBike().getID() == b.getID())
					return u;
			}
		}
		return null;
	}
	
	/**
	 * Method for getting a station based on a slot object. It uses the fact that in our implementation, whenever a system is set online, the parking slot ids are always station_id * 100 plus their position in the station
	 * @param slot ParkingSlot object being searched
	 * @return Station if there is a station with the provided ID, null otherwise.
	 */
	public static Station getStationBySlot(ParkingSlot slot) {
		int stationId = slot.getId()/100;
		for (Station station: VelibSystem.stations) {
			if (station.getId() == stationId)
				return station;
		}
		return null;
	}
	
	/**
	 * Method for getting a station based on a given bike. Uses the station method {@link station.Station#getBikeByID(Bike)}.
	 * @param b Bike object to use for search
	 * @return Station object containing the bike in one of its slots, null otherwise. If the bike exists but is with an user, for example, it will return null
	 */
	public static Station getStationByBike(Bike b) {
		for (Station s: VelibSystem.stations) {
			if (s.getBikeByID(b.getID()) != null)
				return s;
		}
		return null;
	}
	
	/**
	 * Method for searching a user on the ArrayList users of the network based on its registration card.
	 * @param c Card Object with the registration card being searched
	 * @return User object if there is a user registered with that card, null otherwise
	 */
	public static User getUserByRegistrationCard(Card c) {
		for (User u : VelibSystem.users) {
			if (u.getRegistrationCard() != null) {
				if (u.getRegistrationCard().getID() == c.getID())
					return u;
			}
		}
		return null;
	}
	
	/**
	 * Method for searching a station based on its ID
	 * @param id Integer for the station ID
	 * @return Station object having the given id or null if no such object exists
	 */
	public static Station getStationByID(int id) {
		for (Station station : VelibSystem.stations)
			if (station.getId() == id)
				return station;
		return null;
	}
	
	/**
	 * Method for searching a registered user based on its ID. In use context, all users have an unique ID because {@link #addUser(String, Card)} ensures that.
	 * @param id Integer for User ID
	 * @return Returns the user with the corresponding ID if it is registered, null otherwise.
	 */
	public static User getUserByID(int id) {
		for (User user : VelibSystem.users)
			if (user.getID() == id)
				return user;
		return null;
	}
	
	/**
	 * Gets all users that have credit card according to the string given. Since one credit card may be the same for different users, it returns an ArrayList of Users
	 * @param card_number String containing credit card number
	 * @return ArrayList of users having the provided card.
	 */
	public static ArrayList <User> getUsersWithCreditCard(String card_number) {
		ArrayList <User> us = new ArrayList <User> ();
		for (User u : VelibSystem.users) {
			if (u.getCreditCard().equals(card_number)) {
				us.add(u);
			}
		}
		return us;
	}
	
	/**
	 * Method for printing in screen main system info such as stations, users, and rideplanning method
	 */
	public static void printSystemInfo() {
		String stationsInfo = "";
		String usersInfo = "";
		for (Station station:stations)
			stationsInfo += station.toString() + " " + station.getCoordinates().toString() + " ";
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
