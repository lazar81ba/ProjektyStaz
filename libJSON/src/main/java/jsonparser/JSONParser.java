package jsonparser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import exceptions.JSONParserException;
import pl.kamsoft.nfz.project.model.Component;
import pl.kamsoft.nfz.project.model.Phone;

public class JSONParser {

	private List<Phone> list = null;
	private List<Component> listComponent = null;
	JsonGenerator jsonGenerator = null;
	JsonParser jsonParser = null;
	boolean startedTableReading = false;
	FileWriter writter = null;

	private void close() {
		try {
			if (jsonGenerator != null)
				jsonGenerator.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (writter != null)
				writter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startDocument(String filepath) throws JSONParserException {
		try {

			writter = new FileWriter(filepath, false);
			JsonFactory jasonFactory = new JsonFactory();
			jsonGenerator = jasonFactory.createJsonGenerator(writter);
			jsonGenerator.setPrettyPrinter(new MinimalPrettyPrinter(""));

			jsonGenerator.writeStartObject();
			jsonGenerator.writeRaw('\n');
			jsonGenerator.writeFieldName("database");
			jsonGenerator.writeStartArray();
			jsonGenerator.writeRaw('\n');

		} catch (IOException e) {
			throw new JSONParserException("Start document error", e);
		}

	}

	public void parsePhoneList(List<Phone> list) throws JSONParserException {

		try {
			for (Phone phone : list) {
				jsonGenerator.writeStartObject();
				jsonGenerator.writeStringField("model", phone.getModel());
				jsonGenerator.writeStringField("producer", phone.getProducer());
				jsonGenerator.writeStringField("system", phone.getSystem());
				jsonGenerator.writeNumberField("serial_number", phone.getSerial_number());
				jsonGenerator.writeRaw('\n');
				jsonGenerator.writeFieldName("components");
				jsonGenerator.writeStartArray();
				for (Component component : phone.getComponents()) {
					jsonGenerator.writeStartObject();
					jsonGenerator.writeStringField("component", component.getComponent());
					jsonGenerator.writeEndObject();
					jsonGenerator.writeRaw('\n');
				}
				jsonGenerator.writeEndArray();
				jsonGenerator.writeEndObject();
				jsonGenerator.writeRaw('\n');

			}
		} catch (IOException e) {
			throw new JSONParserException("Parse Phone List error", e);
		}

	}

	public void endDocument() throws JSONParserException {
		try {
			jsonGenerator.writeEndArray();
		} catch (IOException e) {
			throw new JSONParserException("EndDocument error", e);
		} finally {
			close();
		}
	}

	public void initializeReadParser(String filepath) throws JSONParserException {
		try {
			File file = new File(filepath);
			if (!file.exists())
				throw new JSONParserException("File not exist");
			JsonFactory jsonFactory = new JsonFactory();
			jsonParser = jsonFactory.createJsonParser(new File(filepath));
			jsonParser.nextToken();
		} catch (JsonParseException e) {
			throw new JSONParserException("Problem with file parser", e);
		} catch (IOException e) {
			throw new JSONParserException("Problem with file", e);
		}
	}

	public void closeReadParser() {
		try {
			if (jsonParser != null)
				jsonParser.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean endOfJSON() throws JSONParserException {
		try {
			if (jsonParser != null)
				if (!jsonParser.hasCurrentToken()) {
					closeReadParser();
					return true;
				}
			return false;
		} catch (Exception e) {
			throw new JSONParserException("Problem with JSONend", e);
		}
	}

	public List<Phone> parseJSONFileToPhoneList() throws JSONParserException {
		list = new ArrayList<Phone>();
		try {
			JsonToken token = null;
			token = jsonParser.nextToken();
			while (token != null) {
				if (list.size() <= 50) {
					if (token == JsonToken.START_ARRAY || startedTableReading) {
						if (token == JsonToken.END_ARRAY) {
							while (token != null) {
								token = jsonParser.nextToken();
							}
							break;
						}
						if (!startedTableReading) {
							startedTableReading = true;
							token = jsonParser.nextToken();
							continue;
						}
						list.add(handleObject(token));
					}
					token = jsonParser.nextToken();
				} else
					break;
			}
			return list;
		} catch (JsonParseException e) {
			throw new JSONParserException("Parser exception", e);
		} catch (IOException e) {
			throw new JSONParserException("IO exception", e);

		}

	}

	private Phone handleObject(JsonToken token) throws JsonParseException, IOException {
		listComponent = new ArrayList<Component>();
		Map<String, String> phoneAttributes = new HashMap<String, String>();
		while (token != JsonToken.END_OBJECT) {
			String strToken = jsonParser.getText();
			if (strToken != null) {
				if (strToken.equals("model")) {
					token = jsonParser.nextToken();
					if (token == JsonToken.VALUE_STRING)
						phoneAttributes.put("model", jsonParser.getText());
				}
				if (strToken.equals("producer")) {
					token = jsonParser.nextToken();
					if (token == JsonToken.VALUE_STRING)
						phoneAttributes.put("producer", jsonParser.getText());
				}
				if (strToken.equals("system")) {
					token = jsonParser.nextToken();
					if (token == JsonToken.VALUE_STRING)
						phoneAttributes.put("system", jsonParser.getText());
				}
				if (strToken.equals("serial_number")) {
					token = jsonParser.nextToken();
					if (token == JsonToken.VALUE_NUMBER_INT)
						phoneAttributes.put("serial_number", jsonParser.getText());
				}
				if (strToken.equals("components")) {
					while (token != JsonToken.END_ARRAY) {
						if (jsonParser.getText().equals("component")) {
							token = jsonParser.nextToken();
							if (token == JsonToken.VALUE_STRING)
								listComponent.add(new Component(jsonParser.getText()));
						}
						token = jsonParser.nextToken();
					}
				}
			}
			token = jsonParser.nextToken();

		}
		return createPhone(listComponent, phoneAttributes);
	}

	private Phone createPhone(List<Component> list, Map<String, String> phoneAttributes) {
		String model = null;
		String producer = null;
		String system = null;
		Integer serial = null;
		if (phoneAttributes.containsKey("model"))
			model = phoneAttributes.get("model");
		if (phoneAttributes.containsKey("producer"))
			producer = phoneAttributes.get("producer");
		if (phoneAttributes.containsKey("system"))
			system = phoneAttributes.get("system");
		if (phoneAttributes.containsKey("serial_number"))
			serial = Integer.valueOf(phoneAttributes.get("serial_number"));
		return new Phone.PhoneBuilder().model(model).producer(producer).system(system).serial_number(serial.intValue())
				.components(listComponent).build();
	}
}
