package system;

import station.Station;

public interface RidePlanning {
	
	public Station[] plan(Station[] stations,GPS start,GPS finish,Class <?> bike_type);
 
}
