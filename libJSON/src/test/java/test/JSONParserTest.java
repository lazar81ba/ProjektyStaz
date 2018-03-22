package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import exceptions.JSONParserException;
import jsonparser.JSONParser;
import pl.kamsoft.nfz.project.model.Component;
import pl.kamsoft.nfz.project.model.Phone;

public class JSONParserTest {
	List<Phone> phoneList=null;
	
//	@Before
//	public void initialize() {
//		phoneList=new ArrayList<Phone>();
//		for(int i=0;i<95;i++) {
//			List<Component> componentList = new ArrayList<Component>();
//			componentList.add(new Component("component"+i));
//			phoneList.add(new Phone.PhoneBuilder().model("model").producer("producer").system("system").serial_number(i).components(componentList).build());
//		}
//	}
////
////	@Test
////	public void testWriteDocument() throws IOException, JSONParserException{
////		JSONParser parser = new JSONParser();
////		final String DOWNLOAD_FOLDER = "./src/main/download/";
////		JSONTempFileWrapper fileWrapper = new JSONTempFileWrapper();
////		fileWrapper.initializeFile(DOWNLOAD_FOLDER);
////		parser.startDocument(fileWrapper.getPath());
////		parser.parsePhoneList(phoneList);
////		parser.endDocument();
////	}
//	
//	@Test
//	public void testReadDocument() throws JSONParserException {
//		JSONParser parser = new JSONParser();
//		final String DOWNLOAD_FOLDER = "./src/main/download/j.json";
//		List<Phone> phones=null;
//		parser.initializeReadParser(DOWNLOAD_FOLDER);
//		while(!parser.endOfJSON()) {
//			phones = parser.parseJSONFileToPhoneList();
//		}
//	}
	
}
