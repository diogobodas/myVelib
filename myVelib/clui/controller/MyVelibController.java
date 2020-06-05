package controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
/**
 * Controller class that permits an user of the application to interact with the model.
 * 
 *
 */
public class MyVelibController {
	
	private MyVelibModel model;
	private MyVelibView view;
	private boolean running;
	/**
	 * Basic constructor for the controller
	 * @param model in which the constructor operates (Observable)
	 * @param view (Observer)
	 */
	public MyVelibController(MyVelibModel model, MyVelibView view) {
		this.model = model;
		this.view = view;
		this.running = true;
	}
	
	/**
	 * Method called to read the user case scenario files.
	 * @param fileName String, only the filename without .txt, file assumed to be on eval folder.
	 * @throws Exception
	 */
	public void readAndWrite(String fileName) throws Exception {
		FileReader file = null;
		BufferedReader reader = null;
		PrintStream stdout = System.out;
		try {
			file = new FileReader("eval/inputFiles/" + fileName + ".txt");
			reader = new BufferedReader(file);
			
			FileOutputStream fileOut = new FileOutputStream("eval/outputFiles/" + fileName + "output.txt");
			PrintStream out = new PrintStream(fileOut);
			System.setOut(out);
			
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.charAt(0) == '/')
					continue;
				String[] args = parseCommand(line);
				executeCommand(args);
			}
			System.setOut(stdout);
			out.close();
		}
		catch (Exception e) {
			System.setOut(stdout);
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
	/**
	 * A simple parser for the command + arguments.
	 * @param command
	 * @return String[] result - Such that result[0] is the command, and result[i] for i = 1..result.length[] - 1 is an argument.
	 */
	public String[] parseCommand(String command) {
		return command.split(" "); // transforms single string into array of strings separated by whitespace
	}
	/**
	 * Main method for controller, identifies a command + arguments from parseCommand result, and calls appropriate function from either model or
	 * view. List of all possible commands + arguments in our report, as well as their explanation were left to the report.
	 * @param command
	 * @throws IncompatibleArgumentsException
	 * @throws Exception
	 */
	public void executeCommand(String[] command) throws IncompatibleArgumentsException,Exception {
		switch (command[0]) {
		
		case "setTimeWindow":
			if(command.length == 11) {
				try {
					LocalDateTime ts = parseTime(command[1],command[2],command[3],command[4],command[5]);
					if (ts == null)
						throw new IncompatibleArgumentsException("Invalid time for time start");
					LocalDateTime te = parseTime(command[6],command[7],command[8],command[9],command[10]);
					if (te == null)
						throw new IncompatibleArgumentsException("Invalid time for time end");
					else 
						model.setTimeWindow(ts, te);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments");
			break;
		
		case "runtest":
			if (command.length == 2) {
				try {
					readAndWrite(command[1]);
					System.out.println("File wrote successfully");
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			else
				throw new IncompatibleArgumentsException("Wrong number of arguments for runtest");	
		break;
		
		case "addUser":
			if (command.length == 3) {
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
			
		case "userState":
			if (command.length == 2) {
				try {
					Integer ID = Integer.valueOf(command[1]);
					view.userState(model, ID);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			break;
			
		case "stationState":
			if (command.length == 2) {
				try {
					Integer ID = Integer.valueOf(command[1]);
					view.stationState(model, ID);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			break;
			
		case "displayUser":
			if (command.length == 2) {
				try {
					Integer ID = Integer.valueOf(command[1]);
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
			if (command.length == 2) {
				try {
					Integer ID = Integer.valueOf(command[1]);
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
			if(command.length == 7) {
				try {
					LocalDateTime time = parseTime(command[2],command[3],command[4],command[5],command[6]);
					if (time == null)
						throw new IncompatibleArgumentsException("Invalid time for offline");
					else {
						Integer ID = Integer.valueOf(command[1]);
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
			if(command.length == 7) {
				try {
					LocalDateTime time = parseTime(command[2],command[3],command[4],command[5],command[6]);
					if (time == null)
						throw new IncompatibleArgumentsException("Invalid time for offline");
					else {
						Integer ID = Integer.valueOf(command[1]);
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
			if (command.length == 2) {
				if (command[1].equals("mostUsed") || command[1].equals("leastOccupied"))
					try {
						view.sortStation(model,command[1]);
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
			if(command.length == 1) {
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

	/**
	 *  Auxiliary function that parses a String sequence into a LocalDateTime class instance, which is used extensively on our project.
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public LocalDateTime parseTime(String year,String month,String day,String hour,String minute) {
		try {
			int y = Integer.valueOf(year);
			int m = Integer.valueOf(month);
			int d = Integer.valueOf(day);
			int h = Integer.valueOf(hour);
			int min = Integer.valueOf(minute);
			return LocalDateTime.of(y,m,d,h,min);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}
