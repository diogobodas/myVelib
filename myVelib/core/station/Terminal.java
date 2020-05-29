package station;

import bike.Bike;
import exceptions.IrregularCardException;
import exceptions.IrregularUserException;
import exceptions.UnavailableBikeException;
import system.VelibSystem;
import user.User;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Terminal {
	
	private Station station;
	
	public Terminal(Station s) {
		station = s;
	}
	
	public void releaseBike(User usr, Class <?> bikeType, LocalDateTime time) throws UnavailableBikeException {
		
		ParkingSlot[] pslots = this.station.getSlots();
		for (ParkingSlot slot:pslots) {
			if(slot.getBike() != null)
				if (slot.getBike().getClass() == bikeType) {
					slot.releaseBike(usr, time);
					break;
				}
			
		}
			
	}
	
	public void identifyUser(User usr) throws IrregularCardException, IrregularUserException {
		// try to get user by registration card
		if (usr.getRegistrationCard() != null) {
			User u = VelibSystem.getUserByRegistrationCard(usr.getRegistrationCard());
			if (u == null) {
				// user has card but is not registered in system
				throw new IrregularCardException("User has card but is not registered in system");
			} 
			if (!(u.equals(usr))) {
				// another user has the same registration card
				throw new IrregularCardException("Another user has the same registration card");
			}
			if (u.getBike() != null) {
				// user already has bike under his name
				throw new IrregularUserException("User already has a bike under his name. It cannot rent any other bikes");
			}
			return;
		}
		// user has no registration card -> try to get user by credit card
		ArrayList <User> users_with_card = VelibSystem.getUsersWithCreditCard(usr.getCreditCard());
		if (users_with_card.isEmpty()) {
			// no user is registered with that card
			throw new IrregularUserException("No user is registered with this card");
		}
		// if it user is registered, this loop will find it
		for (User u : users_with_card) {
			if (u.equals(usr)) {
				if (u.getBike() != null)
					throw new IrregularUserException("User already has a bike under his name. It cannot rent any other bikes");
				return;
			}
		}
		// someone else is registered under this card, but it is not the user provided
		throw new IrregularUserException("User not registered");
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
}
