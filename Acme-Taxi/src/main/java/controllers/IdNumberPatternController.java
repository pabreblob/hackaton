
package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.IdNumberPatternService;
import domain.IdNumberPattern;
import forms.IdNumberPatternForm;
import forms.StringForm;

@Controller
@RequestMapping("/idNumberPattern")
public class IdNumberPatternController extends AbstractController {

	@Autowired
	private IdNumberPatternService	idNumberPatternService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/list")
	public ModelAndView list(final HttpServletRequest request, @RequestParam(required = false) final String nationality) {
		Pageable pageable;
		Direction dir = null;
		Integer pageNum = 0;
		final String pageNumStr = request.getParameter(new ParamEncoder("row").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
		final String sortAtt = request.getParameter(new ParamEncoder("row").encodeParameterName(TableTagParameters.PARAMETER_SORT));
		final String sortOrder = request.getParameter(new ParamEncoder("row").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		if (sortOrder != null)
			if (sortOrder.equals("1"))
				dir = Direction.ASC;
			else
				dir = Direction.DESC;
		if (pageNumStr != null)
			pageNum = Integer.parseInt(pageNumStr) - 1;
		if (sortAtt != null && dir != null)
			pageable = new PageRequest(pageNum, 20, dir, sortAtt);
		else
			pageable = new PageRequest(pageNum, 20);

		final ModelAndView result = new ModelAndView("idNumberPattern/list");
		final Collection<String> nationalities = this.configurationService.find().getNationalities();
		if (nationality == null || !nationalities.contains(nationality)) {
			result.addObject("idNumberPatterns", this.idNumberPatternService.getIdNumberPattern(pageable));
			result.addObject("total", this.idNumberPatternService.countIdNumberPattern());
			result.addObject("nationalityFilter", null);
		} else {
			result.addObject("nationalityFilter", nationality);
			result.addObject("idNumberPatterns", this.idNumberPatternService.getIdNumberPatternByNationality(nationality, pageable));
			result.addObject("total", this.idNumberPatternService.countIdNumberPatternByNationality(nationality));
		}
		result.addObject("nationalities", nationalities); //Nacionalidades para la lista
		result.addObject("stringForm", new StringForm()); //StringForm para el formulario
		return result;
	}
	//	public ModelAndView list(@RequestParam(required = false) final String nationality) {
	//		final ModelAndView result = new ModelAndView("idNumberPattern/list");
	//		final Collection<String> nationalities = this.configurationService.find().getNationalities();
	//		if (nationality == null || !nationalities.contains(nationality)) {
	//			result.addObject("idNumberPatterns", this.idNumberPatternService.findAll());
	//			result.addObject("nationalityFilter", null);
	//		} else {
	//			result.addObject("nationalityFilter", nationality);
	//			result.addObject("idNumberPatterns", this.idNumberPatternService.findByNationality(nationality));
	//		}
	//		result.addObject("nationalities", nationalities); //Nacionalidades para la lista
	//		result.addObject("stringForm", new StringForm()); //StringForm para el formulario
	//		return result;
	//	}
	@RequestMapping(value = "/filteredList", method = RequestMethod.POST)
	public ModelAndView filteringList(@Valid final StringForm stringForm, final BindingResult br) {
		System.out.println("Texto: " + stringForm.getText());
		if (br.hasErrors() || stringForm.getText() == "" || stringForm.getText() == null)
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
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("idNumberPattern/edit");
		final IdNumberPatternForm form = new IdNumberPatternForm();
		res.addObject("form", form);
		res.addObject("nationalities", this.configurationService.find().getNationalities());
		return res;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final IdNumberPatternForm form, final BindingResult br) {
		final ModelAndView res = new ModelAndView("idNumberPattern/edit");
		final IdNumberPattern idn = this.idNumberPatternService.recontruct(form, br);
		if (br.hasErrors())
			res.addObject("form", form);
		else
			try {
				this.idNumberPatternService.save(idn);
				return new ModelAndView("redirect: list.do");
			} catch (final Throwable oops) {
				res.addObject("form", form);
				res.addObject("message", "idNumberPattern.cannotCommit");
			}
		res.addObject("nationalities", this.configurationService.find().getNationalities());
		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final Integer idNumberPatternId) {
		final ModelAndView res = new ModelAndView();
		try {
			final IdNumberPattern idn = this.idNumberPatternService.findOne(idNumberPatternId);
			final IdNumberPatternForm form = new IdNumberPatternForm();
			form.setId(idn.getId());
			form.setText(idn.getPattern());
			res.addObject("form", form);
			res.addObject("nationalities", this.configurationService.find().getNationalities());
		} catch (final Throwable oops) {
			return new ModelAndView("redirect: list.do");
		}
		return res;

	}
}
