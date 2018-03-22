package xmlparser;

import exceptions.XMLParserException;

public interface Parser {
	public void startDocument(String name) throws XMLParserException;
	public void endDocument() throws XMLParserException;
	
}
