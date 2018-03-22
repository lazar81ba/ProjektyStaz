package exceptions;

public class JSONParserException extends Exception{
	public JSONParserException(Throwable e){
		super("Parser error",e);
	}
	public JSONParserException(String message,Throwable e) {
		super(message,e);
	}
	public JSONParserException(String message) {
		super(message);
	}
}
