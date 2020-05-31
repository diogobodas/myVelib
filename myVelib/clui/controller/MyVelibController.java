package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	
	// copiei e colei teu código embaixo.
	// minha sugestão, fazer dois métodos um parse string e o outro execute pra poder usar switch case
		
	public String[] parseCommand(String command) {
		return command.split(" "); // transforms single string into array of strings separated by whitespace
	}
	
	public void executeCommand(String[] command) {
		switch (command[0]) {
		
		case "addUser":
			// completar com tratamento de erros
			model.addUser(command[1], command[2], command[3]);
			break;
			
		case "setup":
			// completar com tratamento de erros
			model.setup(command[1]);
			break;
			
		case "displayUsers":
			// completar com tratamento de erros
			view.displayUsers(model);
			break;
			
		case "displayStations":
			// completar com tratamento de erros
			view.displayStations(model);
			break;
			
		case "exit":
			// completar com tratamento de erros
			this.running = false;
			break;
			
		// continuar com as outras opções .......
		// sugiro ir copiando e colando pedaços da função debaixo
			
		default:
			// Aqui o certo mesmo seria jogar uma exceçao de comando inválido
			System.out.println("Invalid command");
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

	public LocalDateTime parseTime(String year,String month,String day,String hour,String minute) throws Exception{
		try {
			return LocalDateTime.of(Integer.valueOf(year), Integer.valueOf(month),Integer.valueOf(day),
					Integer.valueOf(hour),Integer.valueOf(minute));
		}
		catch(Exception e) {
			throw e; // muda aqui dps mano, não faz sentido tu pegar um exceção pra jogar ela pra cima de novo
			// tinha que entrar uma msg de erro ou algo do tipo
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
