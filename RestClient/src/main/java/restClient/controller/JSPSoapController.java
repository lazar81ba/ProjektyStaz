package restClient.controller;

import java.util.List;

import javax.validation.Valid;
import javax.xml.ws.WebServiceRef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.kamsoft.nfz.project.model.Phone;
import restClient.exceptions.AuthorizationException;
import restClient.exceptions.ExpireTokenException;
import restClient.exceptions.LocalUserRepositoryException;
import restClient.model.PhoneProxy;
import restClient.model.PostParameter;
import restClient.services.PhoneService;
import restClient.services.SoapService;

@Controller
public class JSPSoapController {
	
	private PhoneService service;
	private SoapService soapService;
	
	@Autowired
	public JSPSoapController(PhoneService service, SoapService soapService) {
		this.service = service;
		this.soapService = soapService;
	}
	@RequestMapping(value = "/addSoapJSP", method = RequestMethod.GET)
	public String add(Model model) throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		model.addAttribute("proxy", new PhoneProxy());
		return "jsp/addSoapJSP";
	}
	
	@RequestMapping(value = "/addSoapJSP", method = RequestMethod.POST)
	public String add(@ModelAttribute("proxy") @Valid PhoneProxy proxy, BindingResult result) throws Exception {
		if (result.hasErrors())
			return "jsp/addSoapJSP";
		service.refreshToken();
		soapService.insertPhone(proxy.createPhone());
		return "jsp/home";
	}
	
	@RequestMapping(value = "/getByModelSoapJSP", method = RequestMethod.GET)
	public String getModelJSP(Model model) throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		model.addAttribute("parameter", new PostParameter());
		return "jsp/getByModelSoapJSP";
	}
	
	@RequestMapping(value = "/getByModelSoapJSP", method = RequestMethod.POST)
	public String getModelJSP(@ModelAttribute("parameter") @Valid PostParameter parameter, BindingResult result,
			Model model) throws Exception {
		List<Phone> list = soapService.getPhoneByModel(parameter.getParameter());
		service.refreshToken();
		model.addAttribute("list", list);
		return "jsp/getByModelSoapJSP";

	}
	
	@RequestMapping(value = "/getByIdSoapJSP", method = RequestMethod.GET)
	public String getIdJSP(Model model) throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		model.addAttribute("parameter", new PostParameter());
		return "jsp/getByIdSoapJSP";
	}

	@RequestMapping(value = "/getByIdSoapJSP", method = RequestMethod.POST)
	public String getIdJSP(@ModelAttribute("parameter") @Valid PostParameter parameter, BindingResult result,
			Model model) throws Exception {
		Phone phone = soapService.getPhoneById(Integer.parseInt(parameter.getParameter()));
		service.refreshToken();
		model.addAttribute("phone", phone);
		return "jsp/getByIdSoapJSP";
	}
	
	@RequestMapping(value = "/updateSoapJSP", method = RequestMethod.GET)
	public String updateJSP(Model model) throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		model.addAttribute("parameter", new PostParameter());
		return "jsp/updateSoapJSP";
	}

	@RequestMapping(value = "/updateSoapJSP", method = RequestMethod.POST)
	public String updateJSP(@ModelAttribute("parameter") @Valid PostParameter parameter, BindingResult result,
			Model model) throws Exception {
		int parseIntParameter = Integer.parseInt(parameter.getParameter());
		Phone phone = soapService.getPhoneById(parseIntParameter);
		phone.setId(parseIntParameter);
		service.refreshToken();
		model.addAttribute("phone", phone);
		model.addAttribute("proxy", new PhoneProxy());
		return "jsp/updateSoapJSP";

	}

	@RequestMapping(value = "/updateProxySoapJSP", method = RequestMethod.POST)
	public String updateProxyJSP(@ModelAttribute("proxy") @Valid PhoneProxy proxy, BindingResult result)
			throws Exception {
		soapService.updatePhone(proxy.createPhone());
		service.refreshToken();

		return "redirect:/updateSoapJSP";
	}
	
	@RequestMapping(value = "/deleteSoapJSP", method = RequestMethod.GET)
	public String deleteJSP(Model model) throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		service.refreshToken();
		model.addAttribute("parameter", new PostParameter());
		return "jsp/deleteJSP";
	}

	@RequestMapping(value = "/deleteSoapJSP", method = RequestMethod.POST)
	public String deleteJSP(@ModelAttribute("parameter") @Valid PostParameter parameter, BindingResult result,
			Model model) throws Exception {
		int parseIntParameter = Integer.parseInt(parameter.getParameter());
		Phone phone = soapService.getPhoneById(parseIntParameter);
		phone.setId(parseIntParameter);
		service.refreshToken();
		model.addAttribute("phone", phone);
		model.addAttribute("proxy", new PhoneProxy());
		return "jsp/deleteJSP";

	}

	@RequestMapping(value = "/deleteProxySoapJSP", method = RequestMethod.POST)
	public String deleteProxyJSP(@ModelAttribute("proxy") @Valid PhoneProxy proxy, BindingResult result)
			throws Exception {
		soapService.deletePhone(proxy.getId());
		service.refreshToken();
		return "redirect:/deleteJSP";

	}
	
}
