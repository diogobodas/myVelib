package station;

import java.util.Comparator;

/**
 * This class allows the system to sort stations in accordance with the occupation rate, from the least occupied to the most.
 * 
 *
 */
public class LeastOccupiedComparator implements Comparator<Station>{
	
	/**
	 * Compare method for two stations. If s2 has a bigger occupation rate than s1 then s2 bigger than s1
	 */
	public int compare(Station s1,Station s2) {
		return (int) (s1.getBalance().getRate()*100 - s2.getBalance().getRate()*100);
	}
}
