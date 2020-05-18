package system;

import user.User;
import java.util.ArrayList;
import java.util.Random;

import station.Station;
import station.Terminal;

public class System {

	private Station[] stations;
	private ArrayList<User> users;
	private RidePlanning rideplan;
	
	// Creates N stations, summing up to M parking Slots
	// Each station will have Floor(M/N) slots and if
	// M is not divisible by N, one station will have M mod N stations
	public System(int N,int M) {
		this.users = new ArrayList<User>();
		this.rideplan = new StandardPlanning();
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
		this.stations = stations;
	}



	public ArrayList<User> getUsers() {
		return users;
	}



	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}



	public RidePlanning getRideplan() {
		return rideplan;
	}



	public void setRideplan(RidePlanning rideplan) {
		this.rideplan = rideplan;
	}



	public void addUser(String cardType) {
		this.users.add(new User(cardType));
	}
	
	public Station[] PlanRide(GPS start, GPS finish,String bikeType) {
		return this.rideplan.plan(this.stations,start,finish,bikeType);
	}
}
