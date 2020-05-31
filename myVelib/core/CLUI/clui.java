package CLUI;

import java.time.LocalDateTime;
import java.util.Random;

import station.Station;
import system.GPS;
import system.VelibSystem;
import user.Vlibre;
import user.Vmax;

/**
 * 
 * Command Line User Interface
 * Reads form testScenarioN
 *
 */
public class clui {
	
	public static void main() {
		VelibSystem sys = null;
		String sysName = null;
	}
	
	public LocalDateTime parseTime(String year,String month,String day,String hour,String minute) throws Exception{
		try {
			return LocalDateTime.of(Integer.valueOf(year), Integer.valueOf(month),Integer.valueOf(day),
					Integer.valueOf(hour),Integer.valueOf(minute));
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * List of all commands and their expected parameters:
	 * 
	 * 
	 * setup <VelibNetworkName> / setup <VelibNetworkName,nStations,nSlots,s,nBikes>
	 * addUser <userName,cardType,VelibNetworkName>
	 * offline <velibNetworkName,stationID,time>
	 * online <velibNetworkName,stationID>
	 * rentBike <userID,stationID,time>
	 * returnBike <userID,stationID,time>
	 * displayStation <velibNetworkName,stationID>
	 * displayUser <velibNetworkName,userID>
	 * sortStation <velibNetworkName,sortPolicy>
	 * display <velibNetworkName>
	 * 
	 * Important remarks for parameters:
	 * Expected time format : "year month day hour minute"
	 * 
	 * @param args
	 * @throws IncompatibleArgumentsException
	 */
	void parseArgumentsAndExecute(VelibSystem sys,String Name,final String args[]) throws IncompatibleArgumentsException,Exception {
		int argsSize = args.length;
		if (argsSize < 1)
			throw new IncompatibleArgumentsException(" No command specified");
		String command = args[0];
		if (sys == null)
			if(command != "setup")
				throw new IncompatibleArgumentsException(" You need to setup your system before running other commands");
		if (command == "setup") {
			if (argsSize == 2) {
				sys = new VelibSystem(10,100,4.0);
				Name = args[1];
			}
			else if (argsSize == 6) {
				//to complete
			}
			else 
				throw new IncompatibleArgumentsException("Number of Arguments do not match command setup");
		}
		// Assuming user is spawned at random location
		else if (command == "addUser"){
			if (argsSize == 4) {
				Random rand = new Random();
				String card = args[2];
				if (card == "vlibre")
					sys.addUser(args[1],new Vlibre());
				else if (card == "vmax")
					sys.addUser(args[1],new Vmax());
				else if (card == "none")
					sys.addUser(args[1],null);
				else
					throw new IncompatibleArgumentsException("Incorrect card Type");
			}
			else
				throw new IncompatibleArgumentsException("Number of Arguments do not match command addUser");
		}
		else if (command == "offline"){
			if (argsSize == 7) {
				LocalDateTime time;
				try {
				time = parseTime(args[2],args[3],args[4],args[5],args[6]);
				}
				catch(Exception e) {
					throw e;
				}
				int ID = Integer.valueOf(args[2]);
				Station station = VelibSystem.getStationByID(ID);
				station.setStationOffline(time);
			}
			else
				throw new IncompatibleArgumentsException("Number of Arguments do not match command offline");
		}
		else if (command == "online"){
			if (argsSize == 3) {
				//to complete
			}
			else
				throw new IncompatibleArgumentsException("Number of Arguments do not match command online");
		}
		else if (command == "rentBike"){
			if (argsSize == 4) {
				//to complete
			}
			else
				throw new IncompatibleArgumentsException("Number of Arguments do not match command rentBike");
		}
		else if (command == "returnBike"){
			if (argsSize == 4) {
				//to complete
			}
			else
				throw new IncompatibleArgumentsException("Number of Arguments do not match command returnBike");
		}
		else if (command == "displayStation"){
			if (argsSize == 3) {
				//to complete
			}
			else
				throw new IncompatibleArgumentsException("Number of Arguments do not match command displayStation");
		}
		else if (command == "displayUser"){
			if (argsSize == 3) {
				//to complete
			}
			else
				throw new IncompatibleArgumentsException("Number of Arguments do not match command displayUser");
		}
		else if (command == "sortStation"){
			if (argsSize == 3) {
				//to complete
			}
			else
				throw new IncompatibleArgumentsException("Number of Arguments do not match command sortStation");
		}
		else if (command == "display"){
			if (argsSize == 2) {
				//to complete
			}
			else
				throw new IncompatibleArgumentsException("Number of Arguments do not match command display");
		}
		else 
			throw new IncompatibleArgumentsException("Command doesn't exist");
	
	}
}
	
