package restClient.exceptions;

public class RestException extends Exception{
	public RestException(Throwable e){
		super("Problem with REST",e);
	}
	public RestException(String message,Throwable e) {
		super(message,e);
	}
	public RestException(String message) {
		super(message);
	}
}
