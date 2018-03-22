package restClient.exceptions;

public class AuthorizationException extends Exception{
	public AuthorizationException(Throwable e){
		super("Problem with authorization",e);
	}
	public AuthorizationException(String message,Throwable e) {
		super(message,e);
	}
	public AuthorizationException(String message) {
		super(message);
	}

}
