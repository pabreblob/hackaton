
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
import forms.LegalTextForm;

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

	@RequestMapping("/edit")
	public ModelAndView edit() {
		final Configuration configuration = this.configurationService.find();

		final ModelAndView res = new ModelAndView("configuration/edit");
		res.addObject("configuration", configuration);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("configuration/edit");
			res.addObject("configuration", configuration);
		} else
			try {
				this.configurationService.save(configuration);
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
		final LegalTextForm legalText = new LegalTextForm();
		legalText.setText(this.configurationService.find().getLegalTextEng());

		final ModelAndView res = new ModelAndView("configuration/edit-legaltext");
		res.addObject("legalText", legalText);
		res.addObject("eng", true);

		return res;
	}

	@RequestMapping("edit-legaltextesp")
	public ModelAndView editLegEsp() {
		final LegalTextForm legalText = new LegalTextForm();
		legalText.setText(this.configurationService.find().getLegalTextEsp());

		final ModelAndView res = new ModelAndView("configuration/edit-legaltext");
		res.addObject("legalText", legalText);
		res.addObject("eng", false);

		return res;
	}

	@RequestMapping(value = "edit-legaltexteng", method = RequestMethod.POST)
	public ModelAndView saveLegEng(final LegalTextForm legalText, final BindingResult binding) {
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
	public ModelAndView saveLegEsp(final LegalTextForm legalText, final BindingResult binding) {
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

}
