package containers;

public class ExceptionContainer {
	
	String exceptionMessage;
	Exception ex;
	
	public ExceptionContainer(Exception ex, String message) {
		this.ex = ex;
		this.exceptionMessage = message;
	}
	
	public Exception getException() {
		return ex;
	}
	
	public String getMessage() {
		return exceptionMessage;
	}
	
}
