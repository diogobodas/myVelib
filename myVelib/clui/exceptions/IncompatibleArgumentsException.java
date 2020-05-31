package exceptions;

public class IncompatibleArgumentsException extends Exception {

	private static final long serialVersionUID = 1L;
	private String error_message;
	
	public IncompatibleArgumentsException(String msg) {
		error_message = msg;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
	public String toString() {
		return "IncompatibleArgumentsException: " + error_message;
	}

}
