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
@RequestMapping("/phones")
public class PhonesController {
	private Database repository;

	@Autowired
	public PhonesController(Database base) {
		this.repository = base;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getAllPhones() throws DatabaseException {
		List<Phone> list = repository.selectPhonesWithComponents();
		return new ResponseEntity<List<Phone>>(list,HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public  ResponseEntity<?> getPhone(@PathVariable("id") int id) throws InputException, DatabaseException {
		Phone phone = repository.select(id);
		return new ResponseEntity<Phone>(phone,HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePhone(@PathVariable("id") int id) throws InputException, DatabaseException {
		Phone phone = repository.select(id);
		repository.delete(phone);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> insertPhone(@RequestBody Phone phone) throws InputException, DatabaseException {
		repository.insertPhoneWithComponents(phone);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePhone(@PathVariable("id") int id, @RequestBody Phone phone)
			throws InputException, DatabaseException {
		repository.update(phone);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
