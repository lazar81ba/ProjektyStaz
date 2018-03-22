package serverApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.kamsoft.nfz.project.dao.Database;
import pl.kamsoft.nfz.project.exceptions.DatabaseException;
import pl.kamsoft.nfz.project.model.Phone;

@RestController
@RequestMapping("/phones/model")
public class PhonesModelController {
	
	
	private Database repository;

	@Autowired
	public PhonesModelController(Database base) {
		this.repository = base;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> notAllowed() throws DatabaseException {
		return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}
	@RequestMapping(value = "/{model}", method = RequestMethod.GET)
	public ResponseEntity<?> getByModel(@PathVariable("model") String model) throws DatabaseException {
		List<Phone> list = repository.selectByModel(model);
		return new ResponseEntity<List<Phone>>(list,HttpStatus.OK);
	}

}
