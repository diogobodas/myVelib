package CLUI;

public class IncompatibleArgumentsException extends Exception{
	
	private static final long serialVersionUID = 3L;
	private String errorMessage;
	
	public IncompatibleArgumentsException(String message) {
		errorMessage = message;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
