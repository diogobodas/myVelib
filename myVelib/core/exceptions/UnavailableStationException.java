package exceptions;

/**
 * Exception thrown when someone tries to return a bike in a an offline station
 *
 */
public class UnavailableStationException extends Exception {

	private static final long serialVersionUID = 6L;
	private String error_message;
	
	/**
	 * Constructor for the Exception
	 * @param msg String given as error message to be displayed
	 */
	public UnavailableStationException(String msg) {
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
		return "UnavailableStationException: " + error_message;
	}
}
