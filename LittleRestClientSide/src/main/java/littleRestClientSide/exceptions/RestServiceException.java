package littleRestClientSide.exceptions;

public class RestServiceException extends Exception {
	public RestServiceException(Throwable e){
		super("Problem with RestService",e);
	}
	public RestServiceException(String message,Throwable e) {
		super(message,e);
	}
	public RestServiceException(String message) {
		super(message);
	}
}
