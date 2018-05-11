
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.IdNumberPatternService;
import forms.StringForm;

@Controller
@RequestMapping("/idNumberPattern")
public class IdNumberPatternController extends AbstractController {

	@Autowired
	private IdNumberPatternService	idNumberPatternService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(required = false) final String nationality) {
		final ModelAndView result = new ModelAndView("idNumberPattern/list");
		final Collection<String> nationalities = this.configurationService.find().getNationalities();
		if (nationality == null || !nationalities.contains(nationality)) {
			result.addObject("idNumberPatterns", this.idNumberPatternService.findAll());
			result.addObject("nationalityFilter", null);
		} else {
			result.addObject("nationalityFilter", nationality);
			result.addObject("idNumberPatterns", this.idNumberPatternService.findByNationality(nationality));
		}
		result.addObject("nationalities", nationalities); //Nacionalidades para la lista
		result.addObject("stringForm", new StringForm()); //StringForm para el formulario
		return result;
	}
	@RequestMapping(value = "/filteredList", method = RequestMethod.POST)
	public ModelAndView filteringList(@Valid final StringForm stringForm, final BindingResult br) {
		System.out.println("Texto: " + stringForm.getText());
		if (br.hasErrors() || stringForm.getText() == "-")
			return new ModelAndView("redirect: list.do");
		else
			return new ModelAndView("redirect: list.do?nationality=" + stringForm.getText());
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final Integer idNumberPatternId) {
		try {
			this.idNumberPatternService.delete(idNumberPatternId);
		} catch (final Throwable oops) {
		}
		return new ModelAndView("redirect: list.do");
	}
}
