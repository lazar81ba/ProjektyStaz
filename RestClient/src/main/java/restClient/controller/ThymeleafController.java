package restClient.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import pl.kamsoft.nfz.project.dao.Database;
import pl.kamsoft.nfz.project.model.Phone;
import restClient.model.PhoneProxy;

@Controller
public class ThymeleafController {

	private Database repository;

	@Autowired
	public ThymeleafController(Database base) {
		this.repository = base;
	}

	@RequestMapping(value = "/")
	public String home() {
		return "th/home";
	}

	@RequestMapping(value = "/update")
	public String update() {
		return "th/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(@RequestParam(value = "id", required = false) Object id, Model model) throws Exception {
		if (id == null)
			return "th/update";
		Phone phone;

		phone = repository.select(Integer.valueOf((String) id));
		if (phone == null)
			return "th/update";
		model.addAttribute("phone", phone);
		model.addAttribute("proxy", new PhoneProxy());

		return "th/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute(value = "proxy") @Valid PhoneProxy proxy, BindingResult result)
			throws Exception {
		repository.update(proxy.createPhone());
		return "redirect:/update";
	}

	@RequestMapping(value = "/delete")
	public String delete() {
		return "th/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(value = "id", required = false) Object id, Model model) throws Exception {
		if (id == null)
			return "th/delete";
		Phone phone;

		phone = repository.select(Integer.valueOf((String) id));
		if (phone == null)
			return "th/delete";
		model.addAttribute("phone", phone);
		model.addAttribute("proxy", new PhoneProxy());

		return "th/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute(value = "proxy") @Valid PhoneProxy proxy, BindingResult result)
			throws Exception {
		if (result.hasErrors()) {
			String error = result.getAllErrors().toString();
			return "th/delete";
		}
		repository.delete(proxy.createPhone());
		return "th/delete";
	}

	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public String display(@RequestParam(value = "id", required = false) Object id, Model model) throws Exception {
		if (id == null)
			return "th/getById";
		Phone phone;
		phone = repository.select(Integer.valueOf((String) id));
		if (phone == null)
			return "th/getById";
		model.addAttribute("phone", phone);
		return "th/getById";
	}

	@RequestMapping(value = "/getByModel", method = RequestMethod.GET)
	public String display(@RequestParam(value = "model", required = false) String strModel, Model model)
			throws Exception {
		if (strModel == null)
			return "th/getByModel";
		List<Phone> list;
		list = repository.selectByModel(strModel);
		model.addAttribute("list", list);
		return "th/getByModel";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("proxy", new PhoneProxy());
		return "th/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAdd(@ModelAttribute(value = "proxy") @Valid PhoneProxy proxy, BindingResult result)
			throws Exception {
		if (result.hasErrors()) {
			String error = result.getAllErrors().toString();
			return "th/add";
		}
		repository.insert(proxy.createPhone());
		return "th/add";
	}

}
