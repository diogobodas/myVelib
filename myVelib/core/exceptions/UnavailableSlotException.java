package exceptions;

/**
 * Exception thrown when someone tries to return a bike in a slot that is already occupied
 *
 */
public class UnavailableSlotException extends Exception {

	private static final long serialVersionUID = 4L;
	private String error_message;
	
	/**
	 * Constructor for the Exception
	 * @param msg String given as error message to be displayed
	 */
	public UnavailableSlotException(String msg) {
		error_message = msg;
	}

	@Override
	public String getMessage() {
		return error_message;
	}

	public void setMessage(String error_message) {
		this.error_message = error_message;
	}
	
	public String toString() {
		return "UnavailableSlotException: " + error_message;
	}
}
