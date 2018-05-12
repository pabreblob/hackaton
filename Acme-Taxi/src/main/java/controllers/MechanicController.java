
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import services.ConfigurationService;
import services.IdNumberPatternService;
import services.MechanicService;

import domain.IdNumberPattern;
import domain.Mechanic;
import forms.MechanicForm;


@Controller
@RequestMapping("/mechanic")
public class MechanicController extends AbstractController {

	@Autowired
	private MechanicService		mechanicService;
	@Autowired
	private ConfigurationService		configurationService;
	@Autowired
	private IdNumberPatternService		idNumberPatternService;



	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final MechanicForm mechanicForm = new MechanicForm();
		Collection <String> nationalities=this.configurationService.find().getNationalities();
	

		result = new ModelAndView("mechanic/edit");
		result.addObject("mechanicForm", mechanicForm);
		result.addObject("nationalities", nationalities);

		return result;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MechanicForm mechanicForm, final BindingResult binding) {
		ModelAndView result;
		
		final Mechanic mechanic = this.mechanicService.reconstructCreation(mechanicForm, binding);
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(mechanicForm);
		}else{
			int age;
			final LocalDate birth = new LocalDate(mechanic.getBirthdate().getYear() + 1900, mechanic.getBirthdate().getMonth() + 1, mechanic.getBirthdate().getDate());
			final LocalDate now = new LocalDate();
			age=Years.yearsBetween(birth, now).getYears();
			boolean Idmatches=false;
			List<IdNumberPattern> patterns=new ArrayList<IdNumberPattern>(this.idNumberPatternService.findByNationality(mechanic.getNationality()));
			for(IdNumberPattern p:patterns){
				Pattern pattern=Pattern.compile(p.getPattern());
				Matcher m=pattern.matcher(mechanic.getIdNumber());
				if(m.find()){
					Idmatches=true;
					break;
				}
			}
			if (mechanicForm.isAcceptTerms() == false) {
				result = this.createEditModelAndView(mechanicForm, "mechanic.notAccepted.error");
			}else if (!(mechanicForm.getConfirmPass().equals(mechanicForm.getUserAccount().getPassword()))) {
				result = this.createEditModelAndView(mechanicForm, "mechanic.differentPass.error");
			}else if (age<18) {
				result = this.createEditModelAndView(mechanicForm, "mechanic.underage.error");
			}
			else if (!Idmatches) {
				result = this.createEditModelAndView(mechanicForm, "mechanic.idNumber.error");
			}else{
				try {
					this.mechanicService.save(mechanic);
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(mechanicForm, "mechanic.commit.error");
				}
			}
			
		}
		return result;

	}

	protected ModelAndView createEditModelAndView(final MechanicForm mechanicForm) {
		final ModelAndView result;
		result = this.createEditModelAndView(mechanicForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MechanicForm mechanicForm, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("mechanic/edit");
		Collection <String> nationalities=this.configurationService.find().getNationalities();
		result.addObject("mechanicForm", mechanicForm);
		result.addObject("message", messageCode);
		result.addObject("nationalities", nationalities);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final Integer mechanicId) {
		final ModelAndView result;
			result = new ModelAndView("mechanic/display");
			result.addObject("mechanic", this.mechanicService.findOne(mechanicId));
				
		return result;
	}
}
