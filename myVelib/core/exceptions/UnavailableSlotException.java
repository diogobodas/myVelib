package exceptions;

public class UnavailableSlotException extends Exception {

	private static final long serialVersionUID = 4L;
	private String error_message;
	
	public UnavailableSlotException(String msg) {
		error_message = msg;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
	public String toString() {
		return "UnavailableSlotException: " + error_message;
	}
}
