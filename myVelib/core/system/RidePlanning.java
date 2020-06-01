package system;

import java.util.HashMap;
import java.util.Map.Entry;

import station.Station;

/**
 * 
 * Abstract class used in the strategy design pattern to ensure open-close principle when adding new ride planning methods
 *
 */
public abstract class RidePlanning {
	
	/**
	 * Method for finding out the optimal start/end stations for a user trip
	 * @param stations Array containing all stations
	 * @param start Coordinates for start point of the trip
	 * @param finish Coordinates for end point of the trip
	 * @param bike_type Type of being desired for the trip
	 * @return
	 */
	public abstract Station[] plan(Station[] stations,GPS start,GPS finish,Class <?> bike_type);
 
	/**
	 * Method for returning station with smallest distance given a dictionary of stations and distances from them to a given point
	 * @param distances_dict HashMap containing the Stations as keys and the distances to the point as values
	 * @return Station with smallest distance
	 */
	public Station getClosestStation(HashMap<Station, Double> distances_dict) {
		Entry<Station, Double> min = null;
		for(Entry<Station, Double> entry : distances_dict.entrySet()) {
			if (min == null || min.getValue() > entry.getValue())
				min = entry;
		}
		return min.getKey();
	}
}
