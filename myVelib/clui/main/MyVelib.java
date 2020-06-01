package main;

import java.io.FileOutputStream;
import java.io.PrintStream;
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
			System.out.println("Enter 0 for interactive, 1 to read an instruction file");
			command = keyboard.nextLine();
			if (command.equals("0")) {
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
			}
			else {
				System.out.println("Please specify file name");
				String fileName = keyboard.nextLine();
				controller.readAndWrite(fileName);
			}
			keyboard.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
