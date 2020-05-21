package exceptions;

public class IrregularUserException extends Exception {

	private static final long serialVersionUID = 3L;
	private String error_message;
	
	public IrregularUserException(String msg) {
		error_message = msg;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
	public String toString() {
		return "IrregularUserException: " + error_message;
	}


}
