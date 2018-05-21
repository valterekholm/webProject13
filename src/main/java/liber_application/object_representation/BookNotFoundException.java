package liber_application.object_representation;

public class BookNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public BookNotFoundException() { super(); }
	public BookNotFoundException(String message) { super(message); }
	public BookNotFoundException(String message, Throwable cause) { super(message, cause); }
	public BookNotFoundException(Throwable cause) { super(cause); }
}
