package restClient.tools;

import java.util.ArrayList;
import java.util.List;

import pl.kamsoft.nfz.project.model.Component;
import pl.kamsoft.nfz.project.model.Phone;
import restClient.soapclasses.SoapComponent;
import restClient.soapclasses.SoapPhone;


public class SoapPhonesConverter {
	public List<Phone> convertSoapPhoneListToPhoneList(List<SoapPhone> list) {
		List<Phone> finalList = new ArrayList<Phone>();
		for (SoapPhone phone : list) {
			finalList.add(convertSoapPhoneToPhone(phone));
		}
		return finalList;
	}

	public Phone convertSoapPhoneToPhone(SoapPhone phone) {
		Phone finalPhone = new Phone.PhoneBuilder()
									.id(phone.getId())
									.model(phone.getModel())
									.producer(phone.getProducer())
									.system(phone.getSystem())
									.serial_number(phone.getSerialNumber())
									.components(convertSoapComponentsToComponents(phone.getComponents()))
									.build();
		return finalPhone;
	}

	public List<Component> convertSoapComponentsToComponents(List<SoapComponent> list){
		List<Component> finalList = new ArrayList<Component>();
		if(list!=null) {
			for(SoapComponent component:list) {
				Component tmp = new Component(component.getComponent());
				finalList.add(tmp);
			}
		}
		return finalList;
	}
	
	public SoapPhone convertPhoneToSoapPhone(Phone phone) {
		SoapPhone finalPhone = new SoapPhone();
		finalPhone.setId(phone.getId());
		finalPhone.setModel(phone.getModel());
		finalPhone.setProducer(phone.getProducer());
		finalPhone.setSystem(phone.getSystem());
		finalPhone.setSerialNumber(phone.getSerial_number());
		finalPhone.getComponents().addAll(convertComponentsToSoapComponents(phone.getComponents()));
		return finalPhone;
	}
	
	public List<SoapComponent> convertComponentsToSoapComponents(List<Component> list) {

		List<SoapComponent> finalList = new ArrayList<SoapComponent>();
		if (list != null) {
			for (Component component : list) {
				SoapComponent tmp = new SoapComponent();
				tmp.setId(component.getId());
				tmp.setComponent(component.getComponent());
				finalList.add(tmp);
			}
		}
		return finalList;
	}
}
