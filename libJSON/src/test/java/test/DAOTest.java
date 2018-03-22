package test;

import java.io.IOException;

import org.junit.Test;

import dao.JsonDAOFactory;
import dao.JsonDAOOperation;
import exceptions.JSONParserException;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;

public class DAOTest {
	private final String DOWNLOAD_FOLDER = "./src/main/download/";
	private JsonDAOOperation dao=null;
	
//	@Test
//	public void testExport() throws DatabaseException, JSONParserException, IOException {
//		dao = JsonDAOFactory.build("export");
//		JSONTempFileWrapper fileWrapper = new JSONTempFileWrapper();
//		fileWrapper.initializeFile(DOWNLOAD_FOLDER);
//		dao.executeOperation(fileWrapper.getPath());
//	}
//	
//	@Test
//	public void testImport(){
//		String testPath="./src/main/download/j.json";
//		dao = JsonDAOFactory.build("import");
//		try {
//			dao.executeOperation(testPath);
//		} catch (DatabaseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONParserException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
