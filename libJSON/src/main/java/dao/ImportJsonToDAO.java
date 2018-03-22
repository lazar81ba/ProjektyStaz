package dao;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import exceptions.JSONParserException;
import jsonparser.JSONParser;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import pl.kamsoft.nfz.project.exceptions.InputException;
import pl.kamsoft.nfz.project.model.Phone;
import wrappers.ConnectionWrapper;

public class ImportJsonToDAO implements JsonDAOOperation {

	JSONParser parser = null;
	ConnectionWrapper wrapper = null;
	
	private void initialize() {
		wrapper = new ConnectionWrapper();
		parser = new JSONParser();
	}
	
	public void executeOperation(String filename) throws JSONParserException, DatabaseException {
		try {
			initialize();
			
			wrapper.connect();
			wrapper.setAutoCommitFalse();
			parser.initializeReadParser(filename);
			List<Phone> list = null;
			while (!parser.endOfJSON()) {
				list = parser.parseJSONFileToPhoneList();
				wrapper.insertPhoneWithComponents(list);
			}
			wrapper.commit();
			
		} catch (SQLException e) {
			throw new DatabaseException("Problem with import from xml", e);
		} catch (InputException e) {
			throw new DatabaseException("Problem z danymi wejsciowymi", e);
		} finally {
			wrapper.closeConnection();
			parser.closeReadParser();
		}
	}

}
