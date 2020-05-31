package station;

import java.util.Comparator;

/**
 * 
 * This class allows the system to sort stations
 * in accordance with the sum of rents + returns.
 *
 */
public class MostUsedComparator implements Comparator<Station>{

	
	public int compare(Station s1,Station s2) {
		StationBalance sb1 = s1.getBalance(), sb2 = s2.getBalance();
		return (sb1.getRentCount() + sb1.getReturnCount()) - 
				(sb2.getRentCount() + sb2.getReturnCount());
	}
	
}
