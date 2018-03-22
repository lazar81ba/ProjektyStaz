package restClient.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import restClient.exceptions.DatabaseOperationException;
import restClient.model.URLRepresentation;

public class GetRestUrlDetailsFromDB implements DatabaseOperation {

	private String url = "jdbc:oracle:thin:@GLORA2.kamsoft.local:1521/SZKOL";
	private String user = "Hibernate";
	private String pass = "Hibernate";
	
	private Connection connect() throws SQLException {
		return DriverManager.getConnection(this.url, this.user, this.pass);
	}

	private void close(Connection conn, Statement stat, ResultSet result) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

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
	
	private URLRepresentation selectUrl() throws DatabaseOperationException {
		ResultSet result = null;
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		URLRepresentation restURL = new URLRepresentation();
		try {
			conn= connect();
			preparedStatement = conn.prepareStatement(
					"SELECT SCHEMA,SERVER_NAME,PORT,SERVICE_NAME FROM REST");
			result = preparedStatement.executeQuery();
			if (result.next()) {
				restURL.setSchema(result.getString("SCHEMA"));
				restURL.setServerName(result.getString("SERVER_NAME"));
				restURL.setPort(result.getString("PORT"));
				restURL.setServiceName(result.getString("SERVICE_NAME"));
			}
			return restURL;
		} catch (SQLException e) {
			throw new DatabaseOperationException("Problem with selecting URL details", e);
		} finally {
			close(conn, preparedStatement, result);
		}
	}
	
	@Override
	public URLRepresentation execute() throws DatabaseOperationException {
		return selectUrl();
	}

}
