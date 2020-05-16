package mainInfrastructure;

import java.util.ArrayList;
import java.util.Random;

public class System {

	private Station[] stations;
	private ArrayList<User> users;
	private RidePlanner rideplanner;
	
	// Creates N stations, summing up to M parking Slots
	// Each station will have Floor(M/N) stations and if
	// M is not divisible by N, one station will have M mod N stations
	System(int N,int M) {
		this.users = new ArrayList<User>();
		this.rideplanner = new RidePlanner();
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
			stations[i] = new Station(i,true,new GPS(10*rand.nextDouble()(),10*rand.nextDouble()),numSlots);
		}
			
	}
	
	public Station[] getStations() {
		return stations;
	}



	public void setStations(Station[] stations) {
		this.stations = stations;
	}



	public ArrayList<User> getUsers() {
		return users;
	}



	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}



	public RidePlanner getRideplanner() {
		return rideplanner;
	}



	public void setRideplanner(RidePlanner rideplanner) {
		this.rideplanner = rideplanner;
	}



	public void addUser(String cardType) {
		this.users.add(new User(cardType));
	}
	
	public Station[] PlanRide(GPS start, GPS finish) {
		return this.rideplanner.(start,finish);
	}
}
