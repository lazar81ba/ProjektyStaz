package restClient.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.kamsoft.nfz.project.model.Phone;
import restClient.soapclasses.*;
import restClient.tools.SoapPhonesConverter;

public class SoapService {
	
	PhonesPort port;
	SoapPhonesConverter converter;
	
	@Autowired
	public SoapService(PhonesPort port) {
		this.port=port;
		converter = new SoapPhonesConverter();
	}
	
	public List<Phone> getPhoneByModel(String model){
		GetPhoneByModelRequest getPhoneByModelRequest = new GetPhoneByModelRequest();
		getPhoneByModelRequest.setModel(model);
		GetPhoneByModelResponse response = port.getPhoneByModel(getPhoneByModelRequest);
		List<SoapPhone> list = response.getSoapPhone();
		return converter.convertSoapPhoneListToPhoneList(list);
	}
	
	public Phone getPhoneById(int id) {
		GetPhoneRequest getPhoneRequest = new GetPhoneRequest();
		getPhoneRequest.setId(id);
		GetPhoneResponse response = port.getPhone(getPhoneRequest);
		SoapPhone phone = response.getSoapPhone();
		return converter.convertSoapPhoneToPhone(phone);
	}
	
	public void insertPhone(Phone phone) {
		InsertPhoneRequest insertPhoneRequest = new InsertPhoneRequest();
		insertPhoneRequest.setModel(phone.getModel());
		insertPhoneRequest.setProducer(phone.getProducer());
		insertPhoneRequest.setSerialNumber(phone.getSerial_number());
		insertPhoneRequest.setSystem(phone.getSystem());
		insertPhoneRequest.getComponents().addAll(converter.convertComponentsToSoapComponents(phone.getComponents()));
		port.insertPhone(insertPhoneRequest);
	}
	
	public void updatePhone(Phone phone) {
		UpdatePhoneRequest updatePhoneRequest = new UpdatePhoneRequest();
		updatePhoneRequest.setModel(phone.getModel());
		updatePhoneRequest.setProducer(phone.getProducer());
		updatePhoneRequest.setSerialNumber(phone.getSerial_number());
		updatePhoneRequest.setSystem(phone.getSystem());
		updatePhoneRequest.getComponents().addAll(converter.convertComponentsToSoapComponents(phone.getComponents()));
		port.updatePhone(updatePhoneRequest);
	}
	
	public void deletePhone(int id) {
		DeletePhoneRequest deletePhoneRequest = new DeletePhoneRequest();
		deletePhoneRequest.setId(id);
		port.deletePhone(deletePhoneRequest);
	}
}
