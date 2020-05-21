package exceptions;

public class UnavailableBikeException extends Exception {

	private static final long serialVersionUID = 2L;
	private String error_message;
	
	public UnavailableBikeException(String msg) {
		error_message = msg;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
	public String toString() {
		return "UnavailableBikeException: " + error_message;
	}
}
