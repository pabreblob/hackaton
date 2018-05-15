
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
import services.DriverService;
import services.IdNumberPatternService;
import domain.Driver;
import domain.IdNumberPattern;

@Controller
@RequestMapping("/driver/driver")
public class DriverDriverController extends AbstractController {

	@Autowired
	private DriverService			driverService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private IdNumberPatternService	idNumberPatternService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Driver s = this.driverService.findByPrincipal();
		final Collection<String> nationalities = this.configurationService.find().getNationalities();

		result = new ModelAndView("driver/edit");
		result.addObject("driver", s);
		result.addObject("nationalities", nationalities);

		return result;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Driver driver, final BindingResult binding) {
		ModelAndView result;

		final Driver d = this.driverService.reconstructEdition(driver, binding);

		int age;
		boolean Idmatches = false;
		final List<IdNumberPattern> patterns = new ArrayList<IdNumberPattern>(this.idNumberPatternService.findByNationality(d.getNationality()));
		for (final IdNumberPattern p : patterns) {
			final Pattern pattern = Pattern.compile(p.getPattern());
			final Matcher m = pattern.matcher(d.getIdNumber());
			if (m.find()) {
				Idmatches = true;
				break;
			}
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(d);
		else if (!Idmatches)
			result = this.createEditModelAndView(d, "driver.idNumber.error");
		else {
			final LocalDate birth = new LocalDate(d.getBirthdate().getYear() + 1900, d.getBirthdate().getMonth() + 1, d.getBirthdate().getDate());
			final LocalDate now = new LocalDate();
			age = Years.yearsBetween(birth, now).getYears();
			if (age < 18)
				result = this.createEditModelAndView(d, "driver.underage.error");
			else
				try {
					this.driverService.save(d);
					result = new ModelAndView("redirect:/driver/driver/display.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(d, "d.commit.error");
				}
		}

		return result;

	}

	protected ModelAndView createEditModelAndView(final Driver driver) {
		final ModelAndView result;
		result = this.createEditModelAndView(driver, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Driver d, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("driver/edit");
		final Collection<String> nationalities = this.configurationService.find().getNationalities();
		result.addObject("driver", d);
		result.addObject("message", messageCode);
		result.addObject("nationalities", nationalities);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		result = new ModelAndView("driver/display");
		result.addObject("driver", this.driverService.findByPrincipal());
		result.addObject("requestURI", "driver/driver/display.do");
		return result;
	}
}
