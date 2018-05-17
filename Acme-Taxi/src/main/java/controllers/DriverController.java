
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

import services.ActorService;
import services.ConfigurationService;
import services.DriverService;
import services.IdNumberPatternService;
import domain.Actor;
import domain.Driver;
import domain.IdNumberPattern;
import forms.DriverForm;

@Controller
@RequestMapping("/driver")
public class DriverController extends AbstractController {

	@Autowired
	private DriverService			driverService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private IdNumberPatternService	idNumberPatternService;

	@Autowired
	private ActorService			actorService;


	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView list() {
	//		ModelAndView result;
	//		Collection<User> users;
	//
	//		users = this.userService.findAll();
	//
	//		result = new ModelAndView("user/list");
	//		result.addObject("users", users);
	//		result.addObject("requestURI", "user/list.do");
	//		return result;
	//	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final DriverForm driverForm = new DriverForm();

		result = new ModelAndView("driver/edit");
		result.addObject("driverForm", driverForm);
		final Collection<String> nationalities = this.configurationService.find().getNationalities();
		result.addObject("nationalities", nationalities);

		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final DriverForm driverForm, final BindingResult binding) {
		ModelAndView result;
		final Driver d = this.driverService.reconstruct(driverForm, binding);
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
			result = this.createEditModelAndView(driverForm);
		else if (driverForm.isAcceptTerms() == false)
			result = this.createEditModelAndView(driverForm, "driver.notAccepted.error");
		else if (!(driverForm.getConfirmPass().equals(driverForm.getPassword())))
			result = this.createEditModelAndView(driverForm, "driver.differentPass.error");
		else if (!Idmatches)
			result = this.createEditModelAndView(driverForm, "driver.idNumber.error");
		else {
			final LocalDate birth = new LocalDate(d.getBirthdate().getYear() + 1900, d.getBirthdate().getMonth() + 1, d.getBirthdate().getDate());
			final LocalDate now = new LocalDate();
			age = Years.yearsBetween(birth, now).getYears();
			if (age < 18)
				result = this.createEditModelAndView(driverForm, "driver.underage.error");
			else
				try {
					this.driverService.save(d);
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(driverForm, "driver.commit.error");
				}
		}
		return result;

	}
	protected ModelAndView createEditModelAndView(final DriverForm driverForm) {
		final ModelAndView result;
		result = this.createEditModelAndView(driverForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final DriverForm driverForm, final String messageCode) {
		final ModelAndView result;
		final Collection<String> nationalities = this.configurationService.find().getNationalities();
		result = new ModelAndView("driver/edit");
		result.addObject("driverForm", driverForm);
		result.addObject("message", messageCode);
		result.addObject("nationalities", nationalities);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int driverId) {
		final ModelAndView result;

		final Driver d = this.driverService.findOne(driverId);
		boolean blockeable = false;
		boolean unblockeable = false;
		try {
			final Actor a = this.actorService.findByPrincipal();
			if (a.getId() != d.getId()) {
				if (!a.getBlockedUsers().contains(d))
					blockeable = true;
				if (a.getBlockedUsers().contains(d))
					unblockeable = true;
			}
		} catch (final Throwable oops) {

		}
		result = new ModelAndView("driver/display");
		result.addObject("driver", this.driverService.findOne(driverId));
		result.addObject("blockeable", blockeable);
		result.addObject("unblockeable", unblockeable);
		result.addObject("requestURI", "driver/display.do");

		return result;
	}
}
