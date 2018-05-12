
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SpamWordService;
import domain.SpamWord;
import forms.SpamWordForm;

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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("spamWord/edit");
		res.addObject("form", new SpamWordForm());
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int spamWordId) {
		final ModelAndView res = new ModelAndView("spamWord/edit");
		final SpamWord sp = this.spamWordService.findOne(spamWordId);
		final SpamWordForm form = new SpamWordForm();
		form.setId(spamWordId);
		form.setWord(sp.getWord());
		res.addObject("form", form);
		return res;
	}
}
