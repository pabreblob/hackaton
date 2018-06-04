/*
 * AbstractController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.MessageService;
import services.SponsorshipService;

@Controller
public class AbstractController {

	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private SponsorshipService		sponsorshipService;
	@Autowired
	private MessageService			messageService;


	// Panic handler ----------------------------------------------------------

	@ModelAttribute
	public void banner(final Model model) {
		model.addAttribute("bannerUrl", this.configurationService.find().getBannerUrl());
		if (LocaleContextHolder.getLocale().getDisplayLanguage().equals("English"))
			model.addAttribute("acceptCookies", this.configurationService.find().getAcceptCookiesEng());
		else if (LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish"))
			model.addAttribute("acceptCookies", this.configurationService.find().getAcceptCookiesEsp());
		model.addAttribute("footer", this.configurationService.find().getFooter());
		if (this.sponsorshipService.getRandomSponsorship() != null)
			model.addAttribute("spons", this.sponsorshipService.getRandomSponsorship().getPictureUrl());
		try {
			final Integer unread = this.messageService.countTotalUnreadMessages();
			model.addAttribute("unread", unread);
		} catch (final Throwable oops) {

		}
	}

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	@RequestMapping(value = "/misc/terms")
	public ModelAndView terms() {
		ModelAndView result;

		result = new ModelAndView("misc/terms");
		if (LocaleContextHolder.getLocale().getDisplayLanguage().equals("English"))
			result.addObject("legalText", this.configurationService.find().getLegalTextEng());
		else if (LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish"))
			result.addObject("legalText", this.configurationService.find().getLegalTextEsp());

		return result;
	}
	@RequestMapping(value = "/misc/contact")
	public ModelAndView contact() {
		ModelAndView result;

		result = new ModelAndView("misc/contact");
		if (LocaleContextHolder.getLocale().getDisplayLanguage().equals("English"))
			result.addObject("contact", this.configurationService.find().getContactEng());
		else if (LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish"))
			result.addObject("contact", this.configurationService.find().getContactEsp());

		return result;
	}
	@RequestMapping(value = "/misc/cookies")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("misc/cookies");
		if (LocaleContextHolder.getLocale().getDisplayLanguage().equals("English"))
			result.addObject("cookies", this.configurationService.find().getCookiesPolicyEng());
		else if (LocaleContextHolder.getLocale().getDisplayLanguage().equals("Spanish"))
			result.addObject("cookies", this.configurationService.find().getCookiesPolicyEsp());

		return result;
	}

}
