package pl.soapservice.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import pl.kamsoft.nfz.project.dao.Database;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import pl.kamsoft.nfz.project.exceptions.InputException;
import pl.kamsoft.nfz.project.model.Phone;
import pl.soapservice.tools.PhonesConverter;
import pl.soapservice.wstest.DeletePhoneRequest;
import pl.soapservice.wstest.DeletePhoneResponse;
import pl.soapservice.wstest.GetAllPhonesRequest;
import pl.soapservice.wstest.GetAllPhonesResponse;
import pl.soapservice.wstest.GetPhoneByModelRequest;
import pl.soapservice.wstest.GetPhoneByModelResponse;
import pl.soapservice.wstest.GetPhoneRequest;
import pl.soapservice.wstest.GetPhoneResponse;
import pl.soapservice.wstest.InsertPhoneRequest;
import pl.soapservice.wstest.InsertPhoneResponse;
import pl.soapservice.wstest.UpdatePhoneRequest;
import pl.soapservice.wstest.UpdatePhoneResponse;

@Endpoint
public class PhoneEndpoint {
	private static final String NAMESPACE_URI = "http://soapservice.pl/wstest";
	private Database database;
	
	@Autowired
	public PhoneEndpoint(Database database) {
			this.database=database;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPhonesRequest")
	@ResponsePayload
	public GetAllPhonesResponse getAllPhones(@RequestPayload GetAllPhonesRequest request) throws DatabaseException {
		GetAllPhonesResponse response = new GetAllPhonesResponse();
		List<Phone> list= database.selectPhonesWithComponents();
		PhonesConverter converter = new PhonesConverter();
		response.getSoapPhone().addAll(converter.convertPhoneList(list));
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPhoneRequest")
	@ResponsePayload
	public GetPhoneResponse getPhone(@RequestPayload GetPhoneRequest request) throws DatabaseException, InputException {
		GetPhoneResponse response = new GetPhoneResponse();
		Phone phone= database.select(request.getId());
		PhonesConverter converter = new PhonesConverter();
		response.setSoapPhone(converter.convertPhone(phone));
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePhoneRequest")
	@ResponsePayload
	public DeletePhoneResponse deletePhone(@RequestPayload DeletePhoneRequest request) throws DatabaseException, InputException {
		DeletePhoneResponse response = new DeletePhoneResponse();
		Phone phone= database.select(request.getId());
		database.delete(phone);
		response.setStatus("Phone deleted");
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPhoneByModelRequest")
	@ResponsePayload
	public GetPhoneByModelResponse getPhonesByModel(@RequestPayload GetPhoneByModelRequest request) throws DatabaseException {
		GetPhoneByModelResponse response = new GetPhoneByModelResponse();
		List<Phone> list= database.selectByModel(request.getModel());
		PhonesConverter converter = new PhonesConverter();
		response.getSoapPhone().addAll(converter.convertPhoneList(list));
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "insertPhoneRequest")
	@ResponsePayload
	public InsertPhoneResponse insertPhone(@RequestPayload InsertPhoneRequest request) throws DatabaseException, InputException {
		InsertPhoneResponse response = new InsertPhoneResponse();
		PhonesConverter converter = new PhonesConverter();
		Phone phone = new Phone.PhoneBuilder().model(request.getModel())
											  .producer(request.getProducer())
											  .serial_number(request.getSerialNumber())
											  .system(request.getSystem())
											  .components(converter.convertSoapComponents(request.getComponents()))
											  .build();
		database.insertPhoneWithComponents(phone);
		response.setStatus("Phone inserted");
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePhoneRequest")
	@ResponsePayload
	public UpdatePhoneResponse updatePhone(@RequestPayload UpdatePhoneRequest request) throws DatabaseException, InputException {
		UpdatePhoneResponse response = new UpdatePhoneResponse();
		PhonesConverter converter = new PhonesConverter();
		database.selectBySerial(request.getSerialNumber());
		Phone phone = new Phone.PhoneBuilder().model(request.getModel())
											  .producer(request.getProducer())
											  .serial_number(request.getSerialNumber())
											  .system(request.getSystem())
											  .components(converter.convertSoapComponents(request.getComponents()))
											  .build();
		database.update(phone);
		response.setStatus("Phone updated");
		return response;
	}
	
}
