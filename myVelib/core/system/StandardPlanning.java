package system;



import java.util.ArrayList;
import java.util.HashMap;

import station.Station;

public class StandardPlanning implements RidePlanning{

	
	public Station[] plan(Station[] stations,GPS start,GPS finish,Class <?> bike_type) {
		
		// Let's first clear out all stations out of service
		
		ArrayList<Station> workingStations = new ArrayList<Station>();
		for (Station station:stations)
			if (station.isOn_service())
				workingStations.add(station);
		
		int size = workingStations.size();
		if (size < 2)
			return null;
		else if (size == 2)
			return stations;
		
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
			java.lang.System.out.println("There is no station with the desired bike");
			return null;
		}
		if (endStations.size() == 0) {
			java.lang.System.out.println("There is no station with available slots");
		}
		
		// Idea: create a dictionary for each ArrayList with (station: distance)
		// get min for each of them
		
		HashMap<Station,Double> startDict = new HashMap<Station,Double>();
		HashMap<Station,Double> endDict = new HashMap<Station,Double>();
		
		Station bestStart,bestEnd;
		
		for (Station station:startStations)
			startDict.put(station, GPS.distance(start, station.getCoordinates()));
		
		for (Station station:endStations)
			endDict.put(station, GPS.distance(finish, station.getCoordinates()));
		
		bestStart = VelibSystem.argmin(startDict, startStations);
		bestEnd = VelibSystem.argmin(endDict,endStations);
		
		Station[] result = {bestStart,bestEnd};
		
		return result;
		
	}
	
	public String toString() {
		return "Standard Planning";
	}
	
}
