package system;

import station.Station;

public interface RidePlanning {
	
	Station[] plan(Station[] stations,GPS start,GPS finish,String bikeType);
 
}
