package restClient.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.kamsoft.nfz.project.dao.Database;
import restClient.model.URLRepresentation;

@Controller
public class LoginController {
	private Database repository;
	private URLRepresentation restURL;


	@Autowired
	public LoginController(Database base,URLRepresentation restURL) {
		this.repository = base;
		this.restURL=restURL;

	}
	
		@RequestMapping(value = "/login", method = RequestMethod.GET)
		public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
			ModelAndView model = new ModelAndView();
			if (error != null) {
				model.addObject("error", "Invalid username and password!");
			}

			if (logout != null) {
				model.addObject("msg", "You've been logged out successfully.");
			}
			model.setViewName("jsp/login");

			return model;
		}

}
