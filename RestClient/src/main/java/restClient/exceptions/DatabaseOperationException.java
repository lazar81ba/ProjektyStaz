package restClient.exceptions;

public class DatabaseOperationException extends Exception{
	public DatabaseOperationException(Throwable e){
		super("Token expired",e);
	}
	public DatabaseOperationException(String message,Throwable e) {
		super(message,e);
	}
	public DatabaseOperationException(String message) {
		super(message);
	}
}
