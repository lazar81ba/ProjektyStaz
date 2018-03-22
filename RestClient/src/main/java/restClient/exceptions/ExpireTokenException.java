package restClient.exceptions;

public class ExpireTokenException extends Exception{
	public ExpireTokenException(Throwable e){
		super("Token expired",e);
	}
	public ExpireTokenException(String message,Throwable e) {
		super(message,e);
	}
	public ExpireTokenException(String message) {
		super(message);
	}


}
