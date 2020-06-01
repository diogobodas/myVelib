package main;

import java.util.Scanner;

import controller.MyVelibController;
import exceptions.IncompatibleArgumentsException;
import model.MyVelibModel;
import view.MyVelibView;

public class MyVelib {
	public static void main(String[] args) {
		try {
			MyVelibModel system = new MyVelibModel();
			MyVelibView ui = new MyVelibView(system);
			MyVelibController controller = new MyVelibController(system, ui);
			Scanner keyboard = new Scanner(System.in);
			String command = "";
			while (controller.isRunning()) {
				System.out.println("Enter command: ");
				command = keyboard.nextLine();
				try {
				controller.executeCommand(controller.parseCommand(command));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			keyboard.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
