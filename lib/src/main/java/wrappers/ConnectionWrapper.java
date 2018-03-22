package wrappers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import pl.kamsoft.nfz.project.exceptions.InputException;
import pl.kamsoft.nfz.project.model.Component;
import pl.kamsoft.nfz.project.model.Phone;
import pl.kamsoft.nfz.project.validation.PhoneValidator;
import pl.kamsoft.nfz.project.validation.Validator;

public class ConnectionWrapper {
	Connection conn = null;
	ResultSet result = null;
	PreparedStatement preparedStatement = null;
	private String url = "jdbc:oracle:thin:@GLORA2.kamsoft.local:1521/SZKOL";
	private String user = "Hibernate";
	private String pass = "Hibernate";

	
	public void connect() throws SQLException {
		conn = DriverManager.getConnection(url, user, pass);
	}

	public void closeConnection() {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		if (preparedStatement != null)
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		if (result != null)
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void setAutoCommitFalse() throws DatabaseException {
		if(conn!=null)
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				throw new DatabaseException("SetAutoCommit error",e);
			}
	}
	
	public void commit() throws DatabaseException {
		if(conn!=null)
			try {
				conn.commit();
			} catch (SQLException e) {
				throw new DatabaseException("Commit error",e);
			}
	}
	public ResultSet selectPhonesWithComponents() throws DatabaseException {
		try {
			preparedStatement = conn.prepareStatement(
					"SELECT id,model,producer,system,serial_number,COMPONENT FROM completephonelist",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			result = preparedStatement.executeQuery();
			return result;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	private void close(Statement stat, ResultSet result) {
		if (stat != null)
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		if (result != null)
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	private boolean arePhonesInDB(List<Phone> phones) throws DatabaseException {
		ResultSet result2 = null;
		PreparedStatement preparedStatement2 = null;
		try {
			String sql = " SELECT COUNT(*) FROM PHONES WHERE serial_number IN (";
			for (Phone phone : phones) {
				sql += phone.getSerial_number() + ",";
			}
			if (sql.endsWith(",")) {
				sql = sql.substring(0, sql.length() - 1);
			}
			sql += ")";
			preparedStatement2 = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			result2 = preparedStatement2.executeQuery();
			int count = 0;
			if (result2.next()) {
				count = result2.getInt(1);
				if (result2.next())
					throw new DatabaseException("To many records in ResultSet");
			}
			if (count == phones.size())
				return true;
			else
				return false;
		} catch (SQLException e) {
			throw new DatabaseException("Cannot select from DB", e);
		} finally {
			close(preparedStatement, result);
		}
	}
	
	private void insertComponents(List<Component> components, int id) throws DatabaseException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement("insert into COMPONENTS(COMPONENT,ID_DEVICE) VALUES(?,?)");
			for (Component comp : components) {
				int j = 1;
				preparedStatement.setString(j++, comp.getComponent());
				preparedStatement.setInt(j++, id);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			throw new DatabaseException("Problem with component insert", e);
		} finally {
			close( preparedStatement, null);
		}

	}

	private int insertPhoneWithProcedure(Phone phone) throws DatabaseException, InputException {
		CallableStatement stmt = null;
		String sql = "{call add_phone (?,?,?,?,?)}";
		try {
			stmt = conn.prepareCall(sql);
			int i = 1;
			stmt.setString(i++, phone.getModel());
			stmt.setString(i++, phone.getProducer());
			stmt.setString(i++, phone.getSystem());
			stmt.setInt(i++, phone.getSerial_number());
			stmt.registerOutParameter(i, java.sql.Types.NUMERIC);
			stmt.execute();
			return stmt.getInt(i);
		} catch (SQLException e) {
			throw new DatabaseException("Problem with phone insert", e);
		} finally {
			close( stmt, null);

		}
	}

	public void insertPhoneWithComponents(List<Phone> phones) throws InputException, DatabaseException {
		Validator phoneValidator = new PhoneValidator();
		try {
			if (!arePhonesInDB(phones)) {
				for (Phone phone : phones) {
					phoneValidator.validate(phone);
					int newPhoneId = insertPhoneWithProcedure(phone);
					insertComponents(phone.getComponents(), newPhoneId);
				}
			} else
				throw new InputException("Phone is in database");
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
}