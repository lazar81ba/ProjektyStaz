package dao;

import exceptions.XMLParserException;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;

public interface DAOOperation {
	public void executeOperation(String filename) throws XMLParserException, DatabaseException;
}
