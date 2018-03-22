package restClient.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import pl.kamsoft.nfz.project.dao.Database;
import pl.kamsoft.nfz.project.model.Phone;
import restClient.exceptions.AuthorizationException;
import restClient.exceptions.ExpireTokenException;
import restClient.exceptions.LocalUserRepositoryException;
import restClient.model.PhoneProxy;
import restClient.model.PostParameter;
import restClient.services.PhoneService;


@Controller
public class JSPController {
	private Database repository;
	private PhoneService service;
	@Autowired
	public JSPController(Database base,PhoneService service) {
		this.repository = base;
		this.service = service;
	}

	@RequestMapping(value = "/home")
	public String home() throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		return "jsp/home";
	}

	
	@RequestMapping(value = "/addJSP", method = RequestMethod.GET)
	public String add(Model model) throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		model.addAttribute("proxy", new PhoneProxy());
		return "jsp/addJSP";
	}

	@RequestMapping(value = "/addJSP", method = RequestMethod.POST)
	public String add(@ModelAttribute("proxy") @Valid PhoneProxy proxy, BindingResult result) throws Exception {
		if (result.hasErrors())
			return "jsp/addJSP";
		service.addPhone(proxy.createPhone());
		service.refreshToken();
		return "jsp/home";
	}

	@RequestMapping(value = "/getByModelJSP", method = RequestMethod.GET)
	public String getModelJSP(Model model) throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		model.addAttribute("parameter", new PostParameter());
		return "jsp/getByModelJSP";
	}

	@RequestMapping(value = "/getByModelJSP", method = RequestMethod.POST)
	public String getModelJSP(@ModelAttribute("parameter") @Valid PostParameter parameter, BindingResult result,
			Model model) throws Exception {
		List<Phone> list = service.getPhoneByModel(parameter.getParameter());
		service.refreshToken();
		model.addAttribute("list", list);
		return "jsp/getByModelJSP";

	}

	@RequestMapping(value = "/getByIdJSP", method = RequestMethod.GET)
	public String getIdJSP(Model model) throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		model.addAttribute("parameter", new PostParameter());
		return "jsp/getByIdJSP";
	}

	@RequestMapping(value = "/getByIdJSP", method = RequestMethod.POST)
	public String getIdJSP(@ModelAttribute("parameter") @Valid PostParameter parameter, BindingResult result,
			Model model) throws Exception {
		Phone phone = service.getPhone(parameter.getParameter());
		service.refreshToken();
		model.addAttribute("phone", phone);
		return "jsp/getByIdJSP";
	}

	@RequestMapping(value = "/updateJSP", method = RequestMethod.GET)
	public String updateJSP(Model model) throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		model.addAttribute("parameter", new PostParameter());
		return "jsp/updateJSP";
	}

	@RequestMapping(value = "/updateJSP", method = RequestMethod.POST)
	public String updateJSP(@ModelAttribute("parameter") @Valid PostParameter parameter, BindingResult result,
			Model model) throws Exception {
		Phone phone = service.getPhone(parameter.getParameter());
		phone.setId(Integer.valueOf(parameter.getParameter()));
		service.refreshToken();
		model.addAttribute("phone", phone);
		model.addAttribute("proxy", new PhoneProxy());
		return "jsp/updateJSP";

	}

	@RequestMapping(value = "/updateProxyJSP", method = RequestMethod.POST)
	public String updateProxyJSP(@ModelAttribute("proxy") @Valid PhoneProxy proxy, BindingResult result)
			throws Exception {
		service.updatePhone(proxy.createPhone());
		service.refreshToken();

		return "redirect:/updateJSP";

	}

	@RequestMapping(value = "/deleteJSP", method = RequestMethod.GET)
	public String deleteJSP(Model model) throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		model.addAttribute("parameter", new PostParameter());
		return "jsp/deleteJSP";
	}

	@RequestMapping(value = "/deleteJSP", method = RequestMethod.POST)
	public String deleteJSP(@ModelAttribute("parameter") @Valid PostParameter parameter, BindingResult result,
			Model model) throws Exception {
		Phone phone = service.getPhone(parameter.getParameter());
		phone.setId(Integer.valueOf(parameter.getParameter()));
		service.refreshToken();
		model.addAttribute("phone", phone);
		model.addAttribute("proxy", new PhoneProxy());
		return "jsp/deleteJSP";

	}

	@RequestMapping(value = "/deleteProxyJSP", method = RequestMethod.POST)
	public String deleteProxyJSP(@ModelAttribute("proxy") @Valid PhoneProxy proxy, BindingResult result)
			throws Exception {
		service.deletePhone(Integer.toString(proxy.getId()));
		service.refreshToken();
		return "redirect:/deleteJSP";

	}
}
