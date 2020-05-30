package exceptions;

public class IrregularOperationException extends Exception {

	private static final long serialVersionUID = 7L;
	private String error_message;
	
	public IrregularOperationException(String msg) {
		error_message = msg;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
	public String toString() {
		return "IrregularOperationException: " + error_message;
	}


}