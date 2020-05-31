package model;

import java.util.Observable;
import java.util.Observer;

import system.VelibSystem;

/**
 * Model to be used during CLUI use of the system
 * 
 *
 */
@SuppressWarnings("deprecation")
public class MyVelibModel extends Observable{
	
	private VelibSystem system;
	
	/**
	 * Setup for model to be used
	 */
	public MyVelibModel() {
		// completar esse construtor com aqueles bagulhos do .ini
		system = null;
	}
	
	/**
	 * Method for adding user to existing VelibSystem Model
	 * @param userName Username for created user
	 * @param cardType Type of card, if user possesses any
	 * @param velibNetworkName Network being updated
	 */
	public void addUser(String userName, String cardType, String velibNetworkName) {
		// completar
		System.out.println("To no addUser");
		this.setChanged();
		this.notifyObservers("Added user");
	}
	
	public void setup(String velibNetworkName) {
		// completar
		System.out.println("To no setup");
		this.setChanged();
		this.notifyObservers("Correctly setup");
	}
}
