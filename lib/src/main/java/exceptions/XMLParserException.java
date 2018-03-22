package exceptions;

public class XMLParserException extends Exception{
	public XMLParserException(Throwable e){
		super("Parser error",e);
	}
	public XMLParserException(String message,Throwable e) {
		super(message,e);
	}
	public XMLParserException(String message) {
		super(message);
	}

}
