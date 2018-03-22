package pl.soapservice.tools;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import pl.kamsoft.nfz.project.model.Component;
import pl.kamsoft.nfz.project.model.Phone;
import pl.soapservice.wstest.SoapComponent;
import pl.soapservice.wstest.SoapPhone;

public class PhonesConverter {
	public List<SoapPhone> convertPhoneList(List<Phone> list) {
		List<SoapPhone> finalList = new ArrayList<SoapPhone>();
		for (Phone phone : list) {
			finalList.add(convertPhone(phone));
		}
		return finalList;
	}

	public SoapPhone convertPhone(Phone phone) {
		SoapPhone finalPhone = new SoapPhone();
		finalPhone.setId(phone.getId());
		finalPhone.setModel(phone.getModel());
		finalPhone.setProducer(phone.getProducer());
		finalPhone.setSystem(phone.getSystem());
		finalPhone.setSerialNumber(phone.getSerial_number());
		finalPhone.getComponents().addAll(convertComponents(phone.getComponents()));
		return finalPhone;
	}

	public List<SoapComponent> convertComponents(List<Component> list) {

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
	
	public List<Component> convertSoapComponents(List<SoapComponent> list){
		List<Component> finalList = new ArrayList<Component>();
		if(list!=null) {
			for(SoapComponent component:list) {
				Component tmp = new Component(component.getComponent());
				finalList.add(tmp);
			}
		}
		return finalList;
	}
}
