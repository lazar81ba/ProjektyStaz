package littleRestServerSide.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import littleRestServerSide.repository.SimpleRepository;
import pl.kamsoft.nfz.model.Model;

@RestController
public class MainController {
	
	private SimpleRepository repository;
	@Autowired
	public MainController(SimpleRepository repository) {
		this.repository=repository;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getAllModels()  {
		List<Model> list = repository.getModelList();
		return new ResponseEntity<List<Model>>(list,HttpStatus.OK);
	}
	@RequestMapping(value = "/oneModel", method = RequestMethod.GET)
	public ResponseEntity<?> getOneModel()  {
		Model model = repository.getOneModel();
		return new ResponseEntity<Model>(model,HttpStatus.OK);
	}
	@RequestMapping(value = "/oneModel", method = RequestMethod.POST)
	public ResponseEntity<?> insertPhone(@RequestBody Model model) {
		System.out.println(model);
		return new ResponseEntity<>(HttpStatus.OK);	}
}
