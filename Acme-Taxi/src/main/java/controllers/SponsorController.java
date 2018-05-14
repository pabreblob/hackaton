
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
import forms.SponsorForm;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	IdNumberPatternService			idNumberPatternService;


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
		final SponsorForm sponsorForm = new SponsorForm();

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsorForm", sponsorForm);
		final Collection<String> nationalities = this.configurationService.find().getNationalities();
		System.out.println(nationalities);
		result.addObject("nationalities", nationalities);

		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final SponsorForm sponsorForm, final BindingResult binding) {
		ModelAndView result;
		final Sponsor s = this.sponsorService.reconstruct(sponsorForm, binding);
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
			result = this.createEditModelAndView(sponsorForm);
		else if (sponsorForm.isAcceptTerms() == false)
			result = this.createEditModelAndView(sponsorForm, "sponsor.notAccepted.error");
		else if (!(sponsorForm.getConfirmPass().equals(sponsorForm.getPassword())))
			result = this.createEditModelAndView(sponsorForm, "sponsor.differentPass.error");
		else if (!Idmatches)
			result = this.createEditModelAndView(sponsorForm, "sponsor.idNumber.error");
		else {
			final LocalDate birth = new LocalDate(s.getBirthdate().getYear() + 1900, s.getBirthdate().getMonth() + 1, s.getBirthdate().getDate());
			final LocalDate now = new LocalDate();
			age = Years.yearsBetween(birth, now).getYears();
			if (age < 18)
				result = this.createEditModelAndView(sponsorForm, "sponsor.underage.error");
			else
				try {
					this.sponsorService.save(s);
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(sponsorForm, "sponsor.commit.error");
				}
		}
		return result;

	}
	protected ModelAndView createEditModelAndView(final SponsorForm sponsorForm) {
		final ModelAndView result;
		result = this.createEditModelAndView(sponsorForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SponsorForm sponsorForm, final String messageCode) {
		final ModelAndView result;
		final Collection<String> nationalities = this.configurationService.find().getNationalities();
		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsorForm", sponsorForm);
		result.addObject("message", messageCode);
		result.addObject("nationalities", nationalities);
		return result;
	}

	//	@RequestMapping(value = "/display", method = RequestMethod.GET)
	//	public ModelAndView display(@RequestParam(required = false) final Integer userId) {
	//		final ModelAndView result;
	//		User principal = null;
	//		try {
	//			principal = this.userService.findByPrincipal();
	//		} catch (final Throwable oops) {
	//		}
	//
	//		if (userId == null && principal == null)
	//			result = new ModelAndView("redirect:/welcome/index.do");
	//		else {
	//			if (userId != null && this.userService.findOne(userId) == null)
	//				return new ModelAndView("redirect:/welcome/index.do");
	//			result = new ModelAndView("user/display");
	//
	//			if (userId != null && principal == null) {
	//				result.addObject("user", this.userService.findOne(userId));
	//				result.addObject("articles", this.articleService.findPublishedArticlesByUser(userId));
	//				result.addObject("chirps", this.chirpService.findByCreatorId(userId));
	//				result.addObject("following", null);
	//				return result;
	//			} else if (userId == null && principal != null) {
	//				result.addObject("user", principal);
	//				result.addObject("articles", this.articleService.findPublishedArticlesByUser(principal.getId()));
	//				result.addObject("chirps", this.chirpService.findByCreatorId(principal.getId()));
	//				result.addObject("following", null);
	//				return result;
	//			} else {
	//				final User u = this.userService.findOne(userId);
	//				result.addObject("user", u);
	//				result.addObject("articles", this.articleService.findPublishedArticlesByUser(userId));
	//				result.addObject("chirps", this.chirpService.findByCreatorId(userId));
	//				if (userId != principal.getId()) {
	//					if (principal.getFollowing().contains(u))
	//						result.addObject("following", true);
	//					else
	//						result.addObject("following", false);
	//				} else
	//					result.addObject("following", null);
	//			}
	//		}
	//		return result;
	//	}
}