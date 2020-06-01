package exceptions;

/**
 * Exception thrown when there is an issue with user given. For instance when one tries to rent a bike without being registered
 * 
 *
 */
public class IrregularUserException extends Exception {

	private static final long serialVersionUID = 3L;
	private String error_message;
	
	/**
	 * Constructor for the Exception
	 * @param msg String given as error message to be displayed
	 */
	public IrregularUserException(String msg) {
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
		return "IrregularUserException: " + error_message;
	}


}
