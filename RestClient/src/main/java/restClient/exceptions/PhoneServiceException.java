package restClient.exceptions;

public class PhoneServiceException extends Exception{
	public PhoneServiceException(Throwable e){
		super("Token expired",e);
	}
	public PhoneServiceException(String message,Throwable e) {
		super(message,e);
	}
	public PhoneServiceException(String message) {
		super(message);
	}

}
