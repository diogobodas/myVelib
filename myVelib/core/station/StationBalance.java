package station;

import java.time.Duration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import system.VelibSystem;

/**
 * 
 * This class implements all functions necessary to keep relevant information or statistics
 * of a station.
 *
 */

public class StationBalance {
	
	/**
	 * @param rentCount: Counts how many times a bike has been rented on this station.
	 * @param returnCount: Counts how many times a bike has been returned on this station.
	 * @param rate: The current rate of occupation, according to ts and te. 
	 * @param timeSLotDict: 
	 * @param station:
	 * ts,te are global parameters, defined in the .ini file
	 * @param ts: Start time for occupation rate calculation
	 * @param te: End time for occupation rate calculation
	 */
	
	private int rentCount;
	private int returnCount;
	private double rate;
	private HashMap<ParkingSlot,ArrayList<LocalDateTime>> timeSlotDict;
	private Station station;
	static LocalDateTime ts = LocalDateTime.of(2020, 6, 1, 12, 30);
	static LocalDateTime te = LocalDateTime.of(2020, 6, 1, 19, 30);
	
	/**
	 * 
	 * @param station: Station that holds the station balance.
	 * Used only on station initialization.
	 */
	public StationBalance(Station station) {
		rentCount = 0;
		returnCount = 0;
		this.station = station;
		int numberOfSlots = station.getSlots().length;
		timeSlotDict = new HashMap<ParkingSlot,ArrayList<LocalDateTime>>();
		for (ParkingSlot slot:station.getSlots())
			timeSlotDict.put(slot,new ArrayList<LocalDateTime>());
		
		calculateRateOfOccupation(ts,te);
	}
	
//	public void initBikeOccupation(ParkingSlot slot) {
//		timeSlotDict.put(slot, )
//	}
	
	/**
	 * Every time a bike is returned or rented in this station, this function is called.
	 * @param slot
	 * @param time
	 * @param receive
	 */
	public void updateBalance(ParkingSlot slot, LocalDateTime time, boolean receive) {
		
		// boolean receive checks if we are return a bike, otherwise we are renting
		
		ArrayList<LocalDateTime> array = timeSlotDict.get(slot);
		
		if (receive)
			returnCount += 1;
		else
			rentCount += 1;
		
		array.add(time);
		
		calculateRateOfOccupation(ts,te);
		
	}
	
	/**
	 * This function makes use of other auxiliary functions defined within the class.
	 * First, that for each slot,We need to make sure that the ti's are calculated
	 * only with operations inside the time window [ts,te].
	 * Then, we take the union of the intervals in which the slot was occupied and
	 * the station was offline.
	 * Finally, we sum the length of this intervals and store it in rate.
	 * @param ts
	 * @param te
	 */
	private void calculateRateOfOccupation(LocalDateTime ts,LocalDateTime te) {
		
		long n = station.getSlots().length;
		long denominator = n*Duration.between(ts, te).toMinutes();
		long numerator = 0;
		int i = 0;
		for (ParkingSlot slot:station.getSlots()) {
			
			long ti;
			// ooo = out of order
			ArrayList<LocalDateTime> adjustedOccupied;
			ArrayList<LocalDateTime> adjustedOoo;
			ArrayList<LocalDateTime> union;
			adjustedOccupied = fitIntoWindow(timeSlotDict.get(slot),ts,te,slot.isStartedFree());
			adjustedOoo = fitIntoWindow(slot.getIntervalsOutOfOrder(),ts,te,slot.isStartedFree());
			union = union(adjustedOccupied,adjustedOoo,te);
			ti = addTimeIntervals(union, te);
			numerator += ti;
			i ++;
		}
		rate = (double) numerator/denominator;
		
	}
	/**
	 * Displays the station statistics
	 */
	public void getBalance() {
		
		String result = "Number of rents:";
		result += String.valueOf(this.rentCount);
		result += System.lineSeparator() + "Number of returns:" + String.valueOf(this.returnCount);
		result += System.lineSeparator() + "Occupation Rate:" + String.valueOf(rate);
		System.out.println(result);
	}
	
	/**
	 * This function return intervals of occupation of the slot, inside the interval [ts,te].
	 * @param array
	 * @param ts
	 * @param te
	 * @param isFree: isFree indicates if the slot was initially occupied with a bike or not,
	 * this changes which intervals represent an occupied slot.
	 * @return
	 */
	public static ArrayList<LocalDateTime> fitIntoWindow(ArrayList<LocalDateTime> array,LocalDateTime ts,LocalDateTime te,boolean isFree) {
		ArrayList<LocalDateTime> result = new ArrayList<LocalDateTime>();
		if (array == null)
			return null;
		if (array.size() == 0) {
			if (isFree) {
				return result;
			}
			else {
				result.add(ts);
				result.add(te);
				return result;
			}
				
		}
		int i = 0;
		// finds first timestamp bigger than ts
		while(i < array.size()) {
			if(array.get(i).isAfter(ts))
				break;
			i += 1;
		}
		if (array.get(i).isAfter(te)) {
			System.out.println("Empty time window");
			return null;
		}
		if (isFree) {
			if (i % 2 == 0)
				result.add(ts);
			else {
				result.add(array.get(i));
				i += 1;
			}
		}
		else {
			if (i % 2 == 1)
				result.add(ts);
			else {
				result.add(array.get(i));
				i += 1;
			}
		}
		
		// First time is in. Now, we iterate until te
		
		while (i < array.size() && array.get(i).isBefore(te)) {
			result.add(array.get(i));
			i += 1;
		}
		
		if (isFree) {
			if (i % 2 == 0)
				result.add(te);
		}
		else {
			if (i % 2 == 1)
				result.add(te);
		}
		return result;
	}
	/**
	 * Performs the Union of intervals, assuming the arrays are already in standard form (sorted,in the time window).
	 * @param array1
	 * @param array2
	 * @param te
	 * @return
	 */
	public static ArrayList<LocalDateTime> union(ArrayList<LocalDateTime> array1,ArrayList<LocalDateTime> array2,LocalDateTime te) {
		ArrayList<LocalDateTime> result = new ArrayList<LocalDateTime>();
		int i = 0,j = 0;
		boolean open1 = false,open2 = false;
		if (array1 == null)
			return array2;
		if (array2 == null)
			return array1;
		while( i < array1.size() || i < array2.size()) {
			LocalDateTime nextTime = null;
			if (i >= array1.size()) {
				if (open1 == false) {
					nextTime = array2.get(j);
					result.add(nextTime);
					open2 = !open2;
					j +=1;
				}
			}
			else if (j >= array2.size()) {
				if (open2 == false) {
					nextTime = array1.get(i);
					result.add(nextTime);
					open1 = !open1;
					i +=1;
				}
			}
			else {
				if(array1.get(i).isBefore(array2.get(j))) {
					nextTime = array1.get(i);
					if(open1 == false || open2 == false)
						result.add(nextTime);
					open1 = !open1;
					i += 1;
					
				}
				else {
					nextTime = array2.get(j);
					if(open1 == false && open2 == false)
						result.add(nextTime);
					open2 = !open2;
					j += 1;
				}
				
			}
			
		}
		
		
		if(open1 || open2)
			result.add(te);
		
		//Finally, we will remove duplicates, if there are any
		
		Set<LocalDateTime> set = new LinkedHashSet<LocalDateTime>();
		set.addAll(result);
		result.clear();
		result.addAll(set);
		Collections.sort(result);
		
		return result;
	}
	
	/**
	 * Auxiliary method to calculate length of time intervals.
	 * @param array
	 * @param te
	 * @return sum of time intervals in minutes.
	 */
	public static long addTimeIntervals(ArrayList<LocalDateTime> array,LocalDateTime te) {
		if (array == null)
			return 0;
		int size = array.size();
		int i = 0;
		long result = 0;
		while(size - i> 0) {
			if (size - i > 1) {
				result += Duration.between(array.get(i), array.get(i+1)).toMinutes();
				i += 2;
			}
			else {
				result += Duration.between(array.get(i), te).toMinutes();
				i += 1;
			}
		}
		return result;
	}

	/*
	 * Method defined just for test purposes.
	 */
	@Override
	public String toString() {
		return "StationBalance [rentCount=" + rentCount + ", return Count=" + returnCount + ", rate of occupation="
				+ rate + "]";
	}

	public int getRentCount() {
		return rentCount;
	}

	public void setRentCount(int rentCount) {
		this.rentCount = rentCount;
	}

	public int getReturnCount() {
		return returnCount;
	}

	public void setReturnCount(int returnCount) {
		this.returnCount = returnCount;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
	
}
