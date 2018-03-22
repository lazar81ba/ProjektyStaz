package restClient.dao;

import restClient.exceptions.DatabaseOperationException;

public interface DatabaseOperation {
	public Object execute() throws DatabaseOperationException;

}
