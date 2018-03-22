package restClient.exceptions;

public class LocalUserRepositoryException extends Exception{
	public LocalUserRepositoryException(Throwable e){
		super("Problem with Local User Repository",e);
	}
	public LocalUserRepositoryException(String message,Throwable e) {
		super(message,e);
	}
	public LocalUserRepositoryException(String message) {
		super(message);
	}
}
