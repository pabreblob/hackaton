
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import domain.Configuration;
import forms.StringForm;

@Controller
@RequestMapping("/configuration/admin")
public class ConfigurationAdminController extends AbstractController {

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping("/display")
	public ModelAndView display() {
		final Configuration config = this.configurationService.find();

		final ModelAndView res = new ModelAndView("configuration/display");
		res.addObject("config", config);

		return res;
	}

	@RequestMapping("/display-legaltexteng")
	public ModelAndView displayLegalTextEng() {
		final String legalText = this.configurationService.find().getLegalTextEng();

		final ModelAndView res = new ModelAndView("configuration/display-legaltext");
		res.addObject("legalText", legalText);
		res.addObject("eng", true);

		return res;
	}

	@RequestMapping("/display-legaltextesp")
	public ModelAndView displayLegalTextEsp() {
		final String legalText = this.configurationService.find().getLegalTextEsp();

		final ModelAndView res = new ModelAndView("configuration/display-legaltext");
		res.addObject("legalText", legalText);
		res.addObject("eng", false);

		return res;
	}

	@RequestMapping("/display-cookieseng")
	public ModelAndView displayCookiesEng() {
		final String cookies = this.configurationService.find().getCookiesPolicyEng();

		final ModelAndView res = new ModelAndView("configuration/display-cookies");
		res.addObject("cookies", cookies);
		res.addObject("eng", true);

		return res;
	}

	@RequestMapping("/display-cookiesesp")
	public ModelAndView displayCookiesEsp() {
		final String cookies = this.configurationService.find().getCookiesPolicyEsp();

		final ModelAndView res = new ModelAndView("configuration/display-cookies");
		res.addObject("cookies", cookies);
		res.addObject("eng", false);

		return res;
	}

	@RequestMapping("/display-contacteng")
	public ModelAndView displayContact() {
		final String contact = this.configurationService.find().getContactEng();

		final ModelAndView res = new ModelAndView("configuration/display-contact");
		res.addObject("contact", contact);
		res.addObject("eng", true);

		return res;
	}

	@RequestMapping("/display-contactesp")
	public ModelAndView displayContactEsp() {
		final String contact = this.configurationService.find().getContactEsp();

		final ModelAndView res = new ModelAndView("configuration/display-contact");
		res.addObject("contact", contact);
		res.addObject("eng", false);

		return res;
	}

	@RequestMapping("/edit")
	public ModelAndView edit() {
		final Configuration configuration = this.configurationService.find();

		final ModelAndView res = new ModelAndView("configuration/edit");
		res.addObject("configuration", configuration);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid Configuration configuration, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("configuration/edit");
			res.addObject("configuration", configuration);
		} else
			try {
				configuration = this.configurationService.save(configuration);
				res = new ModelAndView("configuration/display");
				res.addObject("config", configuration);
			} catch (final Throwable oops) {
				res = new ModelAndView("configuration/edit");
				res.addObject("configuration", configuration);
				res.addObject("message", "configuration.error");
			}
		return res;
	}

	@RequestMapping("/edit-legaltexteng")
	public ModelAndView editLegEng() {
		final StringForm legalText = new StringForm();
		legalText.setText(this.configurationService.find().getLegalTextEng());

		final ModelAndView res = new ModelAndView("configuration/edit-legaltext");
		res.addObject("legalText", legalText);
		res.addObject("eng", true);

		return res;
	}

	@RequestMapping("edit-legaltextesp")
	public ModelAndView editLegEsp() {
		final StringForm legalText = new StringForm();
		legalText.setText(this.configurationService.find().getLegalTextEsp());

		final ModelAndView res = new ModelAndView("configuration/edit-legaltext");
		res.addObject("legalText", legalText);
		res.addObject("eng", false);

		return res;
	}

	@RequestMapping(value = "edit-legaltexteng", method = RequestMethod.POST)
	public ModelAndView saveLegEng(final StringForm legalText, final BindingResult binding) {
		ModelAndView res;
		final Configuration configuration = this.configurationService.find();
		if (binding.hasErrors()) {
			res = new ModelAndView("configuration/edit-legaltext");
			res.addObject("legalText", legalText);
			res.addObject("eng", true);
		} else
			try {
				configuration.setLegalTextEng(legalText.getText());
				this.configurationService.save(configuration);
				res = new ModelAndView("configuration/display");
				res.addObject("config", configuration);
			} catch (final Throwable oops) {
				res = new ModelAndView("configuration/edit-legaltext");
				res.addObject("legalText", legalText);
				res.addObject("eng", true);
				res.addObject("message", "configuration.legalText.error");
			}
		return res;
	}

	@RequestMapping(value = "edit-legaltextesp", method = RequestMethod.POST)
	public ModelAndView saveLegEsp(final StringForm legalText, final BindingResult binding) {
		ModelAndView res;
		final Configuration configuration = this.configurationService.find();
		if (binding.hasErrors()) {
			res = new ModelAndView("configuration/edit-legaltext");
			res.addObject("legalText", legalText);
			res.addObject("eng", false);
		} else
			try {
				configuration.setLegalTextEsp(legalText.getText());
				this.configurationService.save(configuration);
				res = new ModelAndView("configuration/display");
				res.addObject("config", configuration);
			} catch (final Throwable oops) {
				res = new ModelAndView("configuration/edit-legalText");
				res.addObject("legalText", legalText);
				res.addObject("eng", false);
				res.addObject("message", "configuration.legalText.error");
			}
		return res;
	}

	@RequestMapping("/edit-cookieseng")
	public ModelAndView editCookiesEng() {
		final StringForm cookies = new StringForm();
		cookies.setText(this.configurationService.find().getCookiesPolicyEng());

		final ModelAndView res = new ModelAndView("configuration/edit-cookies");
		res.addObject("cookies", cookies);
		res.addObject("eng", true);

		return res;
	}

	@RequestMapping("edit-cookiesesp")
	public ModelAndView editCookiesEsp() {
		final StringForm cookies = new StringForm();
		cookies.setText(this.configurationService.find().getCookiesPolicyEsp());

		final ModelAndView res = new ModelAndView("configuration/edit-cookies");
		res.addObject("cookies", cookies);
		res.addObject("eng", false);

		return res;
	}

	@RequestMapping(value = "edit-cookieseng", method = RequestMethod.POST)
	public ModelAndView saveCookiesEng(final StringForm cookies, final BindingResult binding) {
		ModelAndView res;
		final Configuration configuration = this.configurationService.find();
		if (binding.hasErrors()) {
			res = new ModelAndView("configuration/edit-cookies");
			res.addObject("cookies", cookies);
			res.addObject("eng", true);
		} else
			try {
				configuration.setCookiesPolicyEng(cookies.getText());
				this.configurationService.save(configuration);
				res = new ModelAndView("configuration/display");
				res.addObject("config", configuration);
			} catch (final Throwable oops) {
				res = new ModelAndView("configuration/edit-cookies");
				res.addObject("cookies", cookies);
				res.addObject("eng", true);
				res.addObject("message", "configuration.cookies.error");
			}
		return res;
	}

	@RequestMapping(value = "edit-cookiesesp", method = RequestMethod.POST)
	public ModelAndView saveCookiesEsp(final StringForm cookies, final BindingResult binding) {
		ModelAndView res;
		final Configuration configuration = this.configurationService.find();
		if (binding.hasErrors()) {
			res = new ModelAndView("configuration/edit-cookies");
			res.addObject("cookies", cookies);
			res.addObject("eng", false);
		} else
			try {
				configuration.setCookiesPolicyEsp(cookies.getText());
				this.configurationService.save(configuration);
				res = new ModelAndView("configuration/display");
				res.addObject("config", configuration);
			} catch (final Throwable oops) {
				res = new ModelAndView("configuration/edit-cookies");
				res.addObject("cookies", cookies);
				res.addObject("eng", false);
				res.addObject("message", "configuration.cookies.error");
			}
		return res;
	}

	@RequestMapping("/edit-contacteng")
	public ModelAndView editContactEng() {
		final StringForm contact = new StringForm();
		contact.setText(this.configurationService.find().getContactEng());

		final ModelAndView res = new ModelAndView("configuration/edit-contact");
		res.addObject("contact", contact);
		res.addObject("eng", true);

		return res;
	}

	@RequestMapping("edit-contactesp")
	public ModelAndView editContactEsp() {
		final StringForm contact = new StringForm();
		contact.setText(this.configurationService.find().getContactEsp());

		final ModelAndView res = new ModelAndView("configuration/edit-contact");
		res.addObject("contact", contact);
		res.addObject("eng", false);

		return res;
	}

	@RequestMapping(value = "edit-contacteng", method = RequestMethod.POST)
	public ModelAndView saveContactEng(final StringForm contact, final BindingResult binding) {
		ModelAndView res;
		Configuration configuration = this.configurationService.find();
		if (binding.hasErrors()) {
			res = new ModelAndView("configuration/edit-contact");
			res.addObject("contact", contact);
			res.addObject("eng", true);
		} else
			try {
				configuration.setContactEng(contact.getText());
				configuration = this.configurationService.save(configuration);
				res = new ModelAndView("configuration/display");
				res.addObject("config", configuration);
			} catch (final Throwable oops) {
				res = new ModelAndView("configuration/edit-contact");
				res.addObject("contact", contact);
				res.addObject("eng", true);
				res.addObject("message", "configuration.contact.error");
			}
		return res;
	}

	@RequestMapping(value = "edit-contactesp", method = RequestMethod.POST)
	public ModelAndView saveContactEsp(final StringForm contact, final BindingResult binding) {
		ModelAndView res;
		Configuration configuration = this.configurationService.find();
		if (binding.hasErrors()) {
			res = new ModelAndView("configuration/edit-contact");
			res.addObject("contact", contact);
			res.addObject("eng", false);
		} else
			try {
				configuration.setContactEsp(contact.getText());
				configuration = this.configurationService.save(configuration);
				res = new ModelAndView("configuration/display");
				res.addObject("config", configuration);
			} catch (final Throwable oops) {
				res = new ModelAndView("configuration/edit-contact");
				res.addObject("contact", contact);
				res.addObject("eng", false);
				res.addObject("message", "configuration.contact.error");
			}
		return res;
	}

}
