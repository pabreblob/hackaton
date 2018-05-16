/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;

		result = new ModelAndView("welcome/index");

		if (LocaleContextHolder.getLocale().getDisplayLanguage().equals("English"))
			result.addObject("welcome", this.configurationService.find().getWelcomeEng());
		else if (LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish"))
			result.addObject("welcome", this.configurationService.find().getWelcomeEsp());

		return result;
	}
}
