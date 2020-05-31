package station;

import java.util.Comparator;

/**
 * This class allows the system to sort stations
 * in accordance with the occupation rate, from the least occupied to the most.
 * 
 *
 */
public class LeastOccupiedComparator implements Comparator<Station>{
	
	/**
	 * If s2 has a bigger rate than s1, then s1 > s2.
	 */
	public int compare(Station s1,Station s2) {
		if (s2.getBalance().getRate() > s1.getBalance().getRate())
			return 1;
		else
			return -1;
	}
}
