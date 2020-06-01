package exceptions;

/**
 * Exception thrown when bike is unavailable. Example: user requests electric bike but there are none at the station
 *
 */
public class UnavailableBikeException extends Exception {

	private static final long serialVersionUID = 2L;
	private String error_message;
	
	/**
	 * Constructor for the Exception
	 * @param msg String given as error message to be displayed
	 */
	public UnavailableBikeException(String msg) {
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
		return "UnavailableBikeException: " + error_message;
	}
}
