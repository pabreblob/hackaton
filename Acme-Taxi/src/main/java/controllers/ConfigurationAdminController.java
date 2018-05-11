
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import domain.Configuration;

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

}
