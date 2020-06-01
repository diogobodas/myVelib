package exceptions;

/**
 * 
 * Exception thrown when there is a problem with user registration card
 *
 */
public class IrregularCardException extends Exception {

	private static final long serialVersionUID = 1L;
	private String error_message;
	
	/**
	 * Constructor for the Exception
	 * @param msg String given as error message to be displayed
	 */
	public IrregularCardException(String msg) {
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
		return "IrregularCardException: " + error_message;
	}

}
