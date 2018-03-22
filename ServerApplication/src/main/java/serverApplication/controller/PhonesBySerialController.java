package serverApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.kamsoft.nfz.project.dao.Database;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import pl.kamsoft.nfz.project.exceptions.InputException;
import pl.kamsoft.nfz.project.model.Phone;

@RestController
@RequestMapping("/phones/serial")
public class PhonesBySerialController {
	private Database repository;

	@Autowired
	public PhonesBySerialController(Database base) {
		this.repository = base;
	}

	@RequestMapping(value = "/{serial}", method = RequestMethod.GET)
	public ResponseEntity<?> getPhoneBySerial(@PathVariable("serial") int serial)
			throws InputException, DatabaseException {
		Phone phone = repository.selectBySerial(serial);
		return new ResponseEntity<Phone>(phone, HttpStatus.OK);

	}

	@RequestMapping(value = "/{serial}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePhoneBySerial(@PathVariable("serial") int serial)
			throws InputException, DatabaseException {
		Phone phone = repository.selectBySerial(serial);
		repository.delete(phone);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{serial}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePhoneBySerial(@PathVariable("serial") int serial, @RequestBody Phone phone)
			throws InputException, DatabaseException {
		repository.update(phone);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> insertPhoneBySerial(@RequestBody Phone phone) throws InputException, DatabaseException {
		repository.insertPhoneWithComponents(phone);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> notAllowed() throws DatabaseException {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
}
