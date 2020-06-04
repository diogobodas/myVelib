package core.station;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import station.StationBalance;

class StationBalanceTest {

	@Test
	void addtimeIntervalsTestOdd() {
		ArrayList<LocalDateTime> array = new ArrayList<LocalDateTime>();
		array.add(LocalDateTime.of(2020, 5, 28, 10, 30));
		array.add(LocalDateTime.of(2020, 5, 28, 11, 30));
		array.add(LocalDateTime.of(2020, 5, 28, 13, 30));
		long result = StationBalance.addTimeIntervals(array,LocalDateTime.of(2020, 5, 28, 14, 00));
		System.out.println(String.valueOf(result));
	}
	
	@Test
	void addtimeIntervalsTestEven() {
		ArrayList<LocalDateTime> array = new ArrayList<LocalDateTime>();
		array.add(LocalDateTime.of(2020, 5, 28, 10, 30));
		array.add(LocalDateTime.of(2020, 5, 28, 11, 30));
		array.add(LocalDateTime.of(2020, 5, 28, 13, 30));
		array.add(LocalDateTime.of(2020, 5, 28, 14, 30));
		long result = StationBalance.addTimeIntervals(array,LocalDateTime.of(2020, 5, 28, 15, 00));
		System.out.println(String.valueOf(result));
		
	}
	
	@Test
	void fitIntoWindowTest() {
		
		ArrayList<LocalDateTime> test = new ArrayList<LocalDateTime>();
		ArrayList<LocalDateTime> result;
		test.add(LocalDateTime.of(2020, 5, 28, 9, 15));
		test.add(LocalDateTime.of(2020, 5, 28, 9, 30));
		test.add(LocalDateTime.of(2020, 5, 28, 9, 45));
		test.add(LocalDateTime.of(2020, 5, 28, 10, 30));
		test.add(LocalDateTime.of(2020, 5, 28, 10, 45));
		test.add(LocalDateTime.of(2020, 5, 28, 10, 55));
		result = StationBalance.fitIntoWindow(test,LocalDateTime.of(2020, 5, 28, 10, 0),LocalDateTime.of(2020, 5, 28, 10, 50),true);
	}
	
	@Test
	void unionTest() {
		ArrayList<LocalDateTime> test1 = new ArrayList<LocalDateTime>();
		ArrayList<LocalDateTime> test2 = new ArrayList<LocalDateTime>();
		ArrayList<LocalDateTime> result;
		test1.add(LocalDateTime.of(2020, 5, 28, 9, 15));
		test1.add(LocalDateTime.of(2020, 5, 28, 9, 30));
		test1.add(LocalDateTime.of(2020, 5, 28, 9, 45));
		test1.add(LocalDateTime.of(2020, 5, 28, 10, 30));
		test1.add(LocalDateTime.of(2020, 5, 28, 10, 45));
		test1.add(LocalDateTime.of(2020, 5, 28, 10, 55));
		test2.add(LocalDateTime.of(2020, 5, 28, 10, 15));
		test2.add(LocalDateTime.of(2020, 5, 28, 10, 45));
		result = StationBalance.union(test1,test2,LocalDateTime.of(2020, 5, 28, 14, 50));
		System.out.println(result.toString());
		long test = StationBalance.addTimeIntervals(result, LocalDateTime.of(2020, 5, 28, 14, 50));
		System.out.println(String.valueOf(test));
	}

}
