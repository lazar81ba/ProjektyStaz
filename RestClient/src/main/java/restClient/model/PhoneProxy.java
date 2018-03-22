package restClient.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import pl.kamsoft.nfz.project.model.Component;
import pl.kamsoft.nfz.project.model.Phone;

public class PhoneProxy {
	private int id;
	@Size(max=15,message="Too long model")
	private String model;
	@Size(max=15,message="Too long producer")
	private String producer;
	@Size(max=15,message="Too long system")
	private String system;
	private int serial_number;
	private List<Component> list = new ArrayList<Component>(); 
	
	public Phone createPhone() {
		return new Phone.PhoneBuilder()
					.id(id)
					.model(model)
					.producer(producer)
					.system(system)
					.serial_number(serial_number)
					.components(list)
					.build();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public int getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(int serial_number) {
		this.serial_number = serial_number;
	}

	public List<Component> getList() {
		return list;
	}

	public void setList(List<Component> list) {
		this.list = list;
	}
	
}
