package main;

import java.util.Scanner;

import controller.MyVelibController;
import model.MyVelibModel;
import view.MyVelibView;

public class MyVelib {
	public static void main(String[] args) {
		MyVelibModel system = new MyVelibModel();
		MyVelibView ui = new MyVelibView(system);
		MyVelibController controller = new MyVelibController(system, ui);
		Scanner keyboard = new Scanner(System.in);
		String command = "";
		while (controller.isRunning()) {
			System.out.println("Enter command: ");
			command = keyboard.nextLine();
			controller.executeCommand(controller.parseCommand(command));
		}
		keyboard.close();
	}
}
