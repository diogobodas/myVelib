package user;

import system.GPS;

public class User {

	private int id;
	private GPS location;
	private String creditCard;
	private Card registrationCard;
	
	
	public User(int id,GPS location,Card registrationCard) {
		this.id = id;
		this.location = location;
		this.creditCard = ( (Integer) id).toString();
		this.registrationCard = registrationCard;
	}
	
}
