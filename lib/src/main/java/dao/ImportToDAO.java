package dao;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import exceptions.XMLParserException;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import pl.kamsoft.nfz.project.exceptions.InputException;
import pl.kamsoft.nfz.project.model.Phone;
import wrappers.ConnectionWrapper;
import xmlparser.XMLParser;

public class ImportToDAO implements DAOOperation {

	XMLParser parser = null;
	ConnectionWrapper wrapper = null;
	
	private void initialize() {
		wrapper = new ConnectionWrapper();
		parser = new XMLParser();
	}

	public void executeOperation(String filepath) throws XMLParserException, DatabaseException {
		try {
			initialize();
			File file = new File(filepath);
			if (!file.exists())
				throw new XMLParserException("File not exist");
			wrapper.connect();
			wrapper.setAutoCommitFalse();
			parser.initializeXMLEventReader(file);
			List<Phone> list = null;
			while (!parser.endOfXML()) {
				list = parser.parseXMLFileToPhoneList();
				wrapper.insertPhoneWithComponents(list);
			}
			wrapper.commit();
			
		} catch (SQLException e) {
			throw new DatabaseException("Problem with import from xml", e);
		} catch (InputException e) {
			throw new DatabaseException("Problem z danymi wejsciowymi", e);
		} finally {
			wrapper.closeConnection();
			parser.closeXMLReader();
		}
	}
	

}
