
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
import org.springframework.web.servlet.ModelAndView;


import services.ConfigurationService;
import services.IdNumberPatternService;
import services.MechanicService;

import domain.IdNumberPattern;
import domain.Mechanic;


@Controller
@RequestMapping("/mechanic/mechanic")
public class MechanicMechanicController extends AbstractController {

	@Autowired
	private MechanicService		mechanicService;
	@Autowired
	private ConfigurationService		configurationService;
	@Autowired
	private IdNumberPatternService		idNumberPatternService;



	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Mechanic mechanic = this.mechanicService.findByPrincipal();
		Collection <String> nationalities=this.configurationService.find().getNationalities();
	

		result = new ModelAndView("mechanic/edit");
		result.addObject("mechanic", mechanic);
		result.addObject("nationalities", nationalities);

		return result;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Mechanic mechanic, final BindingResult binding) {
ModelAndView result;
		
		final Mechanic res = this.mechanicService.reconstructEdition(mechanic, binding);
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(res);
		}else{
			int age;
			final LocalDate birth = new LocalDate(res.getBirthdate().getYear() + 1900, res.getBirthdate().getMonth() + 1, res.getBirthdate().getDate());
			final LocalDate now = new LocalDate();
			age=Years.yearsBetween(birth, now).getYears();
			boolean Idmatches=false;
			List<IdNumberPattern> patterns=new ArrayList<IdNumberPattern>(this.idNumberPatternService.findByNationality(res.getNationality()));
			for(IdNumberPattern p:patterns){
				Pattern pattern=Pattern.compile(p.getPattern());
				Matcher m=pattern.matcher(res.getIdNumber());
				if(m.find()){
					Idmatches=true;
					break;
				}
			}
			if (age<18) {
				result = this.createEditModelAndView(res, "mechanic.underage.error");
			}
			else if (!Idmatches) {
				result = this.createEditModelAndView(res, "mechanic.idNumber.error");
			}else{
				try {
					this.mechanicService.save(res);
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(res, "mechanic.commit.error");
				}
			}
			
		}
		return result;

	}

	protected ModelAndView createEditModelAndView(final Mechanic mechanic) {
		final ModelAndView result;
		result = this.createEditModelAndView(mechanic, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Mechanic mechanic, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("mechanic/edit");
		Collection <String> nationalities=this.configurationService.find().getNationalities();
		result.addObject("mechanic", mechanic);
		result.addObject("message", messageCode);
		result.addObject("nationalities", nationalities);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
			result = new ModelAndView("mechanic/display");
			result.addObject("mechanic", this.mechanicService.findByPrincipal());
				
		return result;
	}
}
