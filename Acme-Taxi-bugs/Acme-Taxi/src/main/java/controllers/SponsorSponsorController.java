
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
import services.SponsorService;
import domain.IdNumberPattern;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor/sponsor")
public class SponsorSponsorController extends AbstractController {

	@Autowired
	private SponsorService			sponsorService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private IdNumberPatternService	idNumberPatternService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Sponsor s = this.sponsorService.findByPrincipal();
		final Collection<String> nationalities = this.configurationService.find().getNationalities();

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", s);
		result.addObject("nationalities", nationalities);

		return result;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		final Sponsor s = this.sponsorService.reconstructEdition(sponsor, binding);

		int age;
		boolean Idmatches = false;
		final List<IdNumberPattern> patterns = new ArrayList<IdNumberPattern>(this.idNumberPatternService.findByNationality(s.getNationality()));
		for (final IdNumberPattern p : patterns) {
			final Pattern pattern = Pattern.compile(p.getPattern());
			final Matcher m = pattern.matcher(s.getIdNumber());
			if (m.find()) {
				Idmatches = true;
				break;
			}
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsor);
		else if (!Idmatches)
			result = this.createEditModelAndView(sponsor, "sponsor.idNumber.error");
		else {
			final LocalDate birth = new LocalDate(s.getBirthdate().getYear() + 1900, s.getBirthdate().getMonth() + 1, s.getBirthdate().getDate());
			final LocalDate now = new LocalDate();
			age = Years.yearsBetween(birth, now).getYears();
			if (age < 18)
				result = this.createEditModelAndView(s, "sponsor.underage.error");
			else
				try {
					this.sponsorService.save(s);
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(s, "sponsor.commit.error");
				}
		}

		return result;

	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor) {
		final ModelAndView result;
		result = this.createEditModelAndView(sponsor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsor sponsor, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("sponsor/edit");
		final Collection<String> nationalities = this.configurationService.find().getNationalities();
		result.addObject("sponsor", sponsor);
		result.addObject("message", messageCode);
		result.addObject("nationalities", nationalities);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		result = new ModelAndView("sponsor/display");
		result.addObject("sponsor", this.sponsorService.findByPrincipal());
		result.addObject("requestURI", "sponsor/sponsor/display.do");
		return result;
	}
}
