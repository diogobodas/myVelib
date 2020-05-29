package station;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import system.VelibSystem;

public class StationBalance {
	
	private int rentCount;
	private int returnCount;
	private HashMap<ParkingSlot,ArrayList<LocalDateTime>> timeSlotDict;
	private Station station;

	public StationBalance(Station station) {
		rentCount = 0;
		returnCount = 0;
		this.station = station;
		int numberOfSlots = station.getSlots().length;
		timeSlotDict = new HashMap<ParkingSlot,ArrayList<LocalDateTime>>();
		for (ParkingSlot slot:station.getSlots())
			timeSlotDict.put(slot,new ArrayList<LocalDateTime>());
	}
	
	public void updateBalance(ParkingSlot slot, LocalDateTime time, boolean receive) {
		
		// boolean receive checks if we are return a bike, otherwise we are renting
		
		ArrayList<LocalDateTime> array = timeSlotDict.get(slot);
		
		if (receive)
			returnCount += 1;
		else
			rentCount += 1;
		
		array.add(time);
		
	}
	
	public void getBalance(LocalDateTime ts,LocalDateTime te) {
		
		String result = "Number of rents:";
		result += String.valueOf(this.rentCount);
		result += System.lineSeparator() + "Number of returns:" + String.valueOf(this.returnCount);
		
		// Calculating the station's average occupation
		
		long denominator = VelibSystem.getN()*Duration.between(ts, te).toMinutes();
		long numerator = 0;
		for (ParkingSlot slot:station.getSlots()) {
			
			long ti;
			// ooo = out of order
			ArrayList<LocalDateTime> adjustedOccupied;
			ArrayList<LocalDateTime> adjustedOoo;
			ArrayList<LocalDateTime> union;
			adjustedOccupied = fitIntoWindow(timeSlotDict.get(slot),ts,te,slot.isStartedFree());
			adjustedOoo = fitIntoWindow(station.getIntervalsOutOfOrder(),ts,te,slot.isStartedFree());
			union = union(adjustedOccupied,adjustedOoo,te);
			ti = addTimeIntervals(union, te);
			numerator += ti;
		}
		double rate = (double) numerator/denominator;
		result += System.lineSeparator() + "Occupation Rate:" + String.valueOf(rate);
	}
	
	public static ArrayList<LocalDateTime> fitIntoWindow(ArrayList<LocalDateTime> array,LocalDateTime ts,LocalDateTime te,boolean isFree) {
		ArrayList<LocalDateTime> result = new ArrayList<LocalDateTime>();
		if (array == null)
			return null;
		if (array.size() == 0)
			return null;
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
	
	public static ArrayList<LocalDateTime> union(ArrayList<LocalDateTime> array1,ArrayList<LocalDateTime> array2,LocalDateTime te) {
		ArrayList<LocalDateTime> result = new ArrayList<LocalDateTime>();
		int i = 0,j = 0;
		boolean open1 = false,open2 = false;
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
		return result;
	}
	
	//aux method for calculating occupation time
	public static long addTimeIntervals(ArrayList<LocalDateTime> array,LocalDateTime te) {
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

	public void showBalance() {
		this.toString();
	}

	@Override
	public String toString() {
		return "StationBalance [rentCount=" + rentCount + ", returnCount=" + returnCount + ", timeSlotDict="
				+ timeSlotDict + ", station=" + station + "]";
	}
	
	
	
}
