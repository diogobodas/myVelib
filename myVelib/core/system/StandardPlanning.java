package system;



import java.util.ArrayList;
import java.util.HashMap;

import station.Station;

/**
 * Implementation of the RidePlanning interface with the default criteria for a ride plan.
 *
 */
public class StandardPlanning extends RidePlanning{

	/**
	 * Implementation of the plan method of the RidePlanning interface according to the default criteria. They are :
	 * 1 - Start and end stations as close as possible from start and end coordinates
	 * 2 - Starting station has at least one bike of the desired type
	 * 3 - End station has at least one free parking slot
	 */
	public Station[] plan(Station[] stations,GPS start,GPS finish,Class <?> bike_type) {
		
		// Let's first clear out all stations out of service
		
		ArrayList<Station> workingStations = new ArrayList<Station>();
		for (Station station:stations)
			if (station.isOn_service())
				workingStations.add(station);
		
		int size = workingStations.size();
		if (size < 2) {
			// System.out.println("Debug: got into less than 2 working stations");
			return null;
		} else if (size == 2) {
			// System.out.println("Debug: Exactly two working stations");
			return stations;
		}
		
		// We will filter stations with available desired bikes and
		// stations with available slots
		ArrayList<Station> startStations = new ArrayList<Station>();
		ArrayList<Station> endStations = new ArrayList<Station>();
		
		for (Station station:workingStations) {
			if (station.hasDesiredBike(bike_type))
				startStations.add(station);
			if (station.hasFreeSlot())
				endStations.add(station);
		}
		
		if (startStations.size() == 0) {
			System.out.println("There is no station with the desired bike");
			return null;
		}
		if (endStations.size() == 0) {
			System.out.println("There is no station with available slots");
			return null; 
		}
		
		// Idea: create a dictionary for each ArrayList with (station: distance)
		// get min for each of them
		
		HashMap<Station,Double> startDict = new HashMap<Station,Double>();
		HashMap<Station,Double> endDict = new HashMap<Station,Double>();
		
		Station bestStart,bestEnd;
		
		for (Station station:startStations) {
			// System.out.println(station.getCoordinates());
			startDict.put(station, GPS.distance(start, station.getCoordinates()));
		}
		
		// System.out.println("Debug: Between startStation loop and endStation loop");
		
		for (Station station:endStations) {
			// System.out.println(station.getCoordinates());
			endDict.put(station, GPS.distance(finish, station.getCoordinates()));
		}
		
		bestStart = this.getClosestStation(startDict);
		bestEnd = this.getClosestStation(endDict);
		
		Station[] result = {bestStart,bestEnd};
		
		return result;
		
	}
	
	public String toString() {
		return "Standard Planning";
	}
	
}