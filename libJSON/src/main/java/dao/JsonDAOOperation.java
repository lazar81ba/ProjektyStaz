package dao;

import exceptions.JSONParserException;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;

public interface JsonDAOOperation {
	public void executeOperation(String filename) throws JSONParserException, DatabaseException;
}
