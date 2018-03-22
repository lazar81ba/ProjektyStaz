package littleRestClientSide.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import littleRestClientSide.exceptions.RestServiceException;
import littleRestClientSide.generator.ModelGenerator;
import littleRestClientSide.services.RestService;
import pl.kamsoft.nfz.model.Model;





@Controller
public class MainController {
	
	RestService service;
	@Autowired
	public MainController(RestService service) {
		this.service=service;
	}
	
	@RequestMapping(value = "/")
	public String home()  {
		return "jsp/home";
	}

	@RequestMapping(value = "/getModel")
	public String getModel(org.springframework.ui.Model springModel) throws RestServiceException  {
		Model model =  service.getOneModel();
		springModel.addAttribute("model",model);
		return "jsp/getModel";
	}
	@RequestMapping(value = "/getModelList")
	public String getModelList(org.springframework.ui.Model springModel) throws RestServiceException  {
		List<Model> listModel =  service.getModelList();
		springModel.addAttribute("listModel",listModel);
		return "jsp/getModelList";
	}
	
	@RequestMapping(value = "/sendModel", method=RequestMethod.GET)
	public String sendModelGet()  {
			return "jsp/sendModel";
		
	}
	@RequestMapping(value = "/sendModel", method=RequestMethod.POST)
	public String sendModel() throws RestServiceException  {
		service.sendModel(new ModelGenerator().generateModel());
		return "jsp/home";
	}
}
