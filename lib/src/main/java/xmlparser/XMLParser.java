package xmlparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

import exceptions.XMLParserException;
import pl.kamsoft.nfz.project.model.Component;
import pl.kamsoft.nfz.project.model.Phone;

public class XMLParser implements Parser {

	private List<Phone> list = null;
	private List<Component> listComponent = null;
	private XMLStreamWriter xMLStreamWriter = null;
	private FileWriter writter = null;
	String filePath=null;
	
	private void close() throws XMLParserException {
		
		try {
			if (xMLStreamWriter != null)
				xMLStreamWriter.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		try {
			if (writter != null)
				writter.close();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
		validateXML(new File(filePath));
	}

	private void validateXML(File file) throws XMLParserException {
		FileReader reader=null;
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			URL url = new URL("http://10.12.0.113:8080/RestClient/resources/phoneSchema.xsd");
			Schema mySchema = schemaFactory.newSchema(url);
			reader = new FileReader(file);
			Validator val = mySchema.newValidator();
			val.validate(new StreamSource(reader));
		} catch (SAXException e) {
			throw new XMLParserException("File not valid", e);
		} catch (IOException e) {
			throw new XMLParserException("IO exception", e);
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void startDocument(String filePath) throws XMLParserException {
		try {
			this.filePath=filePath;
			XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
			writter =  new FileWriter(filePath, false);
			xMLStreamWriter = new IndentingXMLStreamWriter(
					xMLOutputFactory.createXMLStreamWriter(writter));
			xMLStreamWriter.writeStartDocument();
			xMLStreamWriter.writeStartElement("database");
		} catch (XMLStreamException e) {
			throw new XMLParserException("Cannot start document", e);
		} catch (FileNotFoundException e) {
			throw new XMLParserException("File not found", e);
		} catch (IOException e) {
			throw new XMLParserException("IO exception", e);
		}
	}

	private void handleStartElements(XMLEvent xmlEvent, Map<String, String> phoneAttributes) {
		StartElement startElement = xmlEvent.asStartElement();
		if (startElement.getName().getLocalPart().equals("phone")) {
			String[] attNames = { "model", "producer", "system", "serial_number" };
			Iterator<Attribute> it = startElement.getAttributes();
			while (it.hasNext()) {
				Attribute attribute = it.next();
				if (Arrays.asList(attNames).contains(attribute.getName().toString())) {
					phoneAttributes.put(attribute.getName().toString(), attribute.getValue());
				}
			}
		} else if (startElement.getName().getLocalPart().equals("component")) {
			Attribute componentAttr = startElement.getAttributeByName(new QName("component"));
			listComponent.add(new Component(componentAttr.getValue()));
		}
	}

	private void handleEndElements(XMLEvent xmlEvent, Map<String, String> phoneAttributes) {
		EndElement endElement = xmlEvent.asEndElement();
		if (endElement.getName().getLocalPart().equals("phone")) {
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
			list.add(new Phone.PhoneBuilder().model(model).producer(producer).system(system)
					.serial_number(serial.intValue()).components(listComponent).build());
			listComponent = new ArrayList<Component>();
			phoneAttributes = new HashMap<String, String>();
		}
	}


	public void endDocument() throws XMLParserException {

		try {
			xMLStreamWriter.writeEndElement();
			xMLStreamWriter.writeEndDocument();

			xMLStreamWriter.flush();
		} catch (XMLStreamException e) {
			throw new XMLParserException("Cannot close document", e);
		} finally {
			close();
		}
	}


	public void parsePhoneList(List<Phone> list) throws XMLParserException {
		try {

			for (Phone phone : list) {
				xMLStreamWriter.writeStartElement("phone");
				xMLStreamWriter.writeAttribute("model", phone.getModel());
				xMLStreamWriter.writeAttribute("producer", phone.getProducer());
				xMLStreamWriter.writeAttribute("system", phone.getSystem());
				xMLStreamWriter.writeAttribute("serial_number", Integer.toString(phone.getSerial_number()));

				for (Component component : phone.getComponents()) {

					xMLStreamWriter.writeStartElement("component");
					xMLStreamWriter.writeAttribute("component", component.getComponent());
					xMLStreamWriter.writeEndElement();
				}
				xMLStreamWriter.writeEndElement();
			}

		} catch (XMLStreamException e) {
			throw new XMLParserException("Cannot parse list", e);
		}
	}

	private XMLEventReader xmlEventReader = null;
	private XMLInputFactory xmlInputFactory = null;

	public void initializeXMLEventReader(File file) throws XMLParserException {
		validateXML(file);
		xmlInputFactory = XMLInputFactory.newInstance();
		try {
			xmlEventReader = xmlInputFactory.createXMLEventReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			throw new XMLParserException("Cannot find file", e);
		} catch (XMLStreamException e) {
			throw new XMLParserException("XML Stream error", e);
		}
	}

	public void closeXMLReader() {
		if(xmlEventReader!=null)
			try {
				xmlEventReader.close();
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public boolean endOfXML() {
		if(xmlEventReader!=null)
			if(xmlEventReader.hasNext()==false) {
				closeXMLReader();
				return true;
			}
		return false;
	}
	
	public List<Phone> parseXMLFileToPhoneList() throws XMLParserException {
		list = new ArrayList<Phone>();
		listComponent = new ArrayList<Component>();
		Map<String, String> phoneAttributes = new HashMap<String, String>();
		try {
			XMLEvent xmlEvent = null;
			while (xmlEventReader.hasNext()) {
				if (list.size() <= 50) {
					xmlEvent = xmlEventReader.nextEvent();
					if (xmlEvent.isStartElement())
						handleStartElements(xmlEvent, phoneAttributes);
					if (xmlEvent.isEndElement())
						handleEndElements(xmlEvent, phoneAttributes);
				} else
					break;
			}
			return list;
			
		} catch (XMLStreamException e) {
			throw new XMLParserException("XML Stream error", e);
		}
	}

}
