
package controllers;

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
import services.UserService;
import domain.Actor;
import domain.User;
import forms.UserForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService		userService;
	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final UserForm userForm = new UserForm();

		result = new ModelAndView("user/edit");
		result.addObject("userForm", userForm);

		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final UserForm userForm, final BindingResult binding) {
		ModelAndView result;
		final User u = this.userService.reconstruct(userForm, binding);
		int age;

		if (binding.hasErrors())
			result = this.createEditModelAndView(userForm);
		else if (userForm.isAcceptTerms() == false)
			result = this.createEditModelAndView(userForm, "user.notAccepted.error");
		else if (!(userForm.getConfirmPass().equals(userForm.getPassword())))
			result = this.createEditModelAndView(userForm, "user.differentPass.error");
		else {
			final LocalDate birth = new LocalDate(u.getBirthdate().getYear() + 1900, u.getBirthdate().getMonth() + 1, u.getBirthdate().getDate());
			final LocalDate now = new LocalDate();
			age = Years.yearsBetween(birth, now).getYears();
			if (age < 18)
				result = this.createEditModelAndView(userForm, "user.underage.error");
			else
				try {
					this.userService.save(u);
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(userForm, "user.commit.error");
				}
		}
		return result;

	}
	protected ModelAndView createEditModelAndView(final UserForm userForm) {
		final ModelAndView result;
		result = this.createEditModelAndView(userForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("user/edit");
		result.addObject("userForm", userForm);
		result.addObject("message", messageCode);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) {
		final ModelAndView result;

		final User u = this.userService.findOne(userId);
		boolean blockeable = false;
		boolean unblockeable = false;
		boolean me = true;
		try {
			final Actor a = this.actorService.findByPrincipal();
			if (a.getId() != u.getId()) {
				me = false;
				if (!a.getBlockedUsers().contains(u))
					blockeable = true;
				if (a.getBlockedUsers().contains(u))
					unblockeable = true;
			}
		} catch (final Throwable oops) {

		}
		result = new ModelAndView("user/display");
		result.addObject("user", this.userService.findOne(userId));
		result.addObject("blockeable", blockeable);
		result.addObject("unblockeable", unblockeable);
		result.addObject("me", me);
		result.addObject("requestURI", "user/display.do");

		return result;
	}

}
