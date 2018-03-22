package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.JSONParserException;
import jsonparser.JSONParser;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import pl.kamsoft.nfz.project.model.Component;
import pl.kamsoft.nfz.project.model.Phone;
import wrappers.ConnectionWrapper;

public class ExportJsonFromDAO implements JsonDAOOperation {

	private List<Phone> list = null;
	private List<Component> listComponent = null;
	private Phone tmpPhone = null;
	private JSONParser parser = null;
	private ConnectionWrapper wrapper = null;

	private void addReadyPhone() {
		list.add(new Phone.PhoneBuilder().model(tmpPhone.getModel()).producer(tmpPhone.getProducer())
				.system(tmpPhone.getSystem()).serial_number(tmpPhone.getSerial_number()).components(listComponent)
				.build());
		tmpPhone = null;
		listComponent = new ArrayList<Component>();
	}

	private void createNewTmpPhone(ResultSet result) throws SQLException {
		int i = 2;
		tmpPhone = new Phone.PhoneBuilder().model(result.getString(i++)).producer(result.getString(i++))
				.system(result.getString(i++)).serial_number(result.getInt(i++)).build();
	}

	private void addComponentToList(ResultSet result) throws SQLException {
		String component = result.getString(6);
		if (component != null)
			listComponent.add(new Component(component));
	}

	private void parseAndClearList() throws JSONParserException {
		if (parser != null)
			parser.parsePhoneList(list);
		else
			throw new JSONParserException("No parser is initialized");
		list.clear();
	}
	
	private void initialize() {
		list = new ArrayList<Phone>();
		listComponent = new ArrayList<Component>();
		wrapper = new ConnectionWrapper();
		parser = new JSONParser();
	}

	public void executeOperation(String filename) throws JSONParserException, DatabaseException {
		try {
			initialize();
			wrapper.connect();
			ResultSet result = wrapper.selectPhonesWithComponents();
			parser.startDocument(filename);
			while (result.next()) {
				int thisPhoneSerial = result.getInt(5);
				if (list.size() >= 50) {
					parseAndClearList();
				}
				if (tmpPhone != null && tmpPhone.getSerial_number() != thisPhoneSerial) {
					addReadyPhone();
				}
				if (tmpPhone == null) {
					createNewTmpPhone(result);
				}
				addComponentToList(result);
				if (result.isLast()) {
					addReadyPhone();
					parseAndClearList();
				}
			}
			parser.endDocument();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}finally {
			wrapper.closeConnection();
		}
	}
}
