package exceptions;

/**
 * Exception thrown when an irregular operation is sent. For instance trying to set online an online station
 *
 */
public class IrregularOperationException extends Exception {

	private static final long serialVersionUID = 5L;
	private String error_message;
	
	/**
	 * Constructor for the Exception
	 * @param msg String given as error message to be displayed
	 */
	public IrregularOperationException(String msg) {
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
		return "IrregularOperationException: " + error_message;
	}


}