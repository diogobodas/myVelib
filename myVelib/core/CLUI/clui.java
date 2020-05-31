package CLUI;

import system.VelibSystem;

/**
 * 
 * Command Line User Interface
 * Reads form testScenarioN
 *
 */
public class clui {
	
	public static void main() {
		VelibSystem sys = null;
		
	}
	/**
	 * 
	 * List of all commands and their expected parameters:
	 * 
	 * setup <>,<> / setup <>
	 * 
	 * 
	 * @param args
	 * @throws IncompatibleArgumentsException
	 */
	void parseArgumentsAndExecute(VelibSystem sys,final String args[]) throws IncompatibleArgumentsException {
		int argsSize = args.length;
		if (argsSize < 1)
			throw new IncompatibleArgumentsException(" No command specified");
		String command = args[0];
		if (sys == null)
			if(command != "setup")
				throw new IncompatibleArgumentsException(" You need to setup your system before running other commands");
		if (command == "setup") {
			if (argsSize == 2) {
				
			}
			else if (argsSize == 3) {
				//to complete
			}
			else 
				throw new IncompatibleArgumentsException("Number of Arguments do not match command setup");
		}
		else if (command == "addUser"){
			if (argsSize == 4) {
				//to complete
			}
			else
				throw new IncompatibleArgumentsException("Number of Arguments do not match command addUser");
		}
		else if (command == "offline"){
			if (argsSize == 3) {
				//to complete
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
	
