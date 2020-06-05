package clui.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import controller.MyVelibController;
import model.MyVelibModel;
import view.MyVelibView;

class MyVelibControllerTest {

	@Test
	void testReadAndWrite() throws Exception {
		MyVelibModel system = new MyVelibModel();
		MyVelibView ui = new MyVelibView(system);
		MyVelibController controller = new MyVelibController(system, ui);
		try {
			controller.readAndWrite("inexistent");
			fail("Should have caught an exception, the file does not exist");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testParseCommand() throws Exception {
		MyVelibModel system = new MyVelibModel();
		MyVelibView ui = new MyVelibView(system);
		MyVelibController controller = new MyVelibController(system, ui);
		String[] split_command = {"rentBike", "0", "0", "2020", "6", "1", "10", "30", "electric"};
		String command = "rentBike 0 0 2020 6 1 10 30 electric";
		String[] test_split = controller.parseCommand(command);
		for(int i = 0; i < split_command.length; i++)
			assertTrue(test_split[i].equals(split_command[i]));
	}

	@Test
	void testExecuteCommand() {
		fail("Not yet implemented");
	}

	@Test
	void testParseTime() throws Exception {
		MyVelibModel system = new MyVelibModel();
		MyVelibView ui = new MyVelibView(system);
		MyVelibController controller = new MyVelibController(system, ui);
		LocalDateTime time = null;
		time = controller.parseTime("twentytwenty", "5", "1", "20", "0");
		assertNull(time);
		time = controller.parseTime("2020", "5", "1", "20:00", "0");
		assertNull(time);
		time = controller.parseTime("2020", "5.0", "1", "20", "0");
		assertNull(time);
		time = controller.parseTime("2020", "5", "1", "20", "-1");
		assertNull(time);
		time = controller.parseTime("2020", "5", "1", "20", "0");
		assertFalse(time == null);
	}

}
