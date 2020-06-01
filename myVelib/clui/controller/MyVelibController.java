package controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Random;

import exceptions.IncompatibleArgumentsException;
import model.MyVelibModel;
import station.Station;
import system.VelibSystem;
import user.Vlibre;
import user.Vmax;
import view.MyVelibView;

public class MyVelibController {
	
	private MyVelibModel model;
	private MyVelibView view;
	private boolean running;
	
	public MyVelibController(MyVelibModel model, MyVelibView view) {
		this.model = model;
		this.view = view;
		this.running = true;
	}
	
	public void readAndWrite(String fileName) throws Exception{
		PrintStream out = new PrintStream(new FileOutputStream("clui/main/" + fileName + "output.txt"));
		System.setOut(out);
		FileReader file = null;
		BufferedReader reader = null;
		try {
			file = new FileReader("clui/main/" + fileName + ".txt");
			reader = new BufferedReader(file);
			String line;
			while ((line = reader.readLine()) != null) {
				String[] args = parseCommand(line);
				executeCommand(args);
			}
		}
		catch (Exception e) {
			throw new Exception(e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e) {}
			}
		}
	}
		
	public String[] parseCommand(String command) {
		return command.split(" "); // transforms single string into array of strings separated by whitespace
	}
	
	public void executeCommand(String[] command) throws IncompatibleArgumentsException,Exception {
		switch (command[0]) {
		
		case "addUser":
			if (command.length == 4) {
				if (command[2].equals("vmax") || command[2].equals("vlibre") || command[2].equals("none")) {
					try {
						model.addUser(command[1], command[2]);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				else
					throw new IncompatibleArgumentsException("Card name does not exist for addUser command");
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "setup":
			if (command.length == 2) {
				try { 
					model.setup(command[1]);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else if (command.length == 6) {
				try { 
					int nStations = Integer.valueOf(command[2]);
					int nSlots = Integer.valueOf(command[3]);
					double s = Double.valueOf(command[4]);
					int nBikes = Integer.valueOf(command[5]);
					model.setup(command[1],nStations,nSlots,s,nBikes);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "displayUser":
			if (command.length == 3) {
				try {
					Integer ID = Integer.valueOf(command[2]);
					view.displayUser(model,ID);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "displayStation":
			if (command.length == 3) {
				try {
					Integer ID = Integer.valueOf(command[2]);
					view.displayStation(model,ID);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "offline":
			if(command.length == 8) {
				try {
					LocalDateTime time = parseTime(command[3],command[4],command[5],command[6],command[7]);
					if (time == null)
						throw new IncompatibleArgumentsException("Invalid time for offline");
					else {
						Integer ID = Integer.valueOf(command[2]);
						model.offline(ID,time);
					}
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "online":
			if(command.length == 8) {
				try {
					LocalDateTime time = parseTime(command[3],command[4],command[5],command[6],command[7]);
					if (time == null)
						throw new IncompatibleArgumentsException("Invalid time for offline");
					else {
						Integer ID = Integer.valueOf(command[2]);
						model.online(ID,time);
					}
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "rentBike":
			if(command.length == 9) {
				try {
					LocalDateTime time = parseTime(command[3],command[4],command[5],command[6],command[7]);
					if (time == null)
						throw new IncompatibleArgumentsException("Invalid time for offline");
					else {
						int userID = Integer.valueOf(command[1]);
						int stationID = Integer.valueOf(command[2]);
						model.rentBike(userID,stationID,time,command[8]);
					}
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "returnBike":
			if(command.length == 8) {
				try {
					LocalDateTime time = parseTime(command[3],command[4],command[5],command[6],command[7]);
					if (time == null)
						throw new IncompatibleArgumentsException("Invalid time for offline");
					else {
						int userID = Integer.valueOf(command[1]);
						int stationID = Integer.valueOf(command[2]);
						model.returnBike(userID,stationID,time);
					}
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "sortStation":
			if (command.length == 3) {
				if (command[2].equals("mostUsed") || command[2].equals("leastOccupied"))
					try {
						view.sortStation(model,command[2]);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				else
					throw new IncompatibleArgumentsException("Policy does not exist for sortStations command");
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "display":
			if(command.length == 2) {
				try {
					view.display(model);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "planRide":
			if (command.length == 6) {
				double xStart = Double.valueOf(command[1]);
				double yStart = Double.valueOf(command[2]);
				double xEnd = Double.valueOf(command[3]);
				double yEnd = Double.valueOf(command[4]);
				model.planRide(xStart,yStart,xEnd,yEnd,command[5]);
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
			
		case "readAndWriteS":
			
		case "exit":
			this.running = false;
			break;
			
		default:
			throw new IncompatibleArgumentsException("Invalid command");
		}
	}
	
	public MyVelibModel getModel() {
		return model;
	}

	public void setModel(MyVelibModel model) {
		this.model = model;
	}

	public MyVelibView getView() {
		return view;
	}

	public void setView(MyVelibView view) {
		this.view = view;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public LocalDateTime parseTime(String year,String month,String day,String hour,String minute) {
		try {
			return LocalDateTime.of(Integer.valueOf(year), Integer.valueOf(month),Integer.valueOf(day),
					Integer.valueOf(hour),Integer.valueOf(minute));
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
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
	 * online <velibNetworkName,stationID,time>
	 * rentBike <userID,stationID,time,type>
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
	// aqui te deixo adaptar as paradas
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
				String card = args[2];
				System.out.println(card);
				if (card.equals("vlibre"))
					sys.addUser(args[1],new Vlibre());
				else if (card.equals("vmax")) {
					sys.addUser(args[1],new Vmax());
				}
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
