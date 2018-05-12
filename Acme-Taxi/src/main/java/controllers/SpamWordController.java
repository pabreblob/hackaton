
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SpamWordService;

@Controller
@RequestMapping("/spamWord")
public class SpamWordController extends AbstractController {

	@Autowired
	private SpamWordService	spamWordService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("spamWord/list");
		res.addObject("spamWords", this.spamWordService.findAll());
		return res;
	}
}
