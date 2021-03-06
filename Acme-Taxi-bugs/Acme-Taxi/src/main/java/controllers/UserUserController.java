
package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.UserService;
import domain.Announcement;
import domain.User;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController {

	@Autowired
	private UserService			userService;
	@Autowired
	private AnnouncementService	announcementService;


	@RequestMapping(value = "/list-reviewable", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
		ModelAndView res;
		final Collection<User> users;
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
			pageable = new PageRequest(pageNum, 5, dir, sortAtt);
		else
			pageable = new PageRequest(pageNum, 5);

		final Integer total = this.userService.countUsersReviewable();
		users = this.userService.findUsersReviewable(pageable);
		res = new ModelAndView("user/list");
		res.addObject("users", users);
		res.addObject("total", total);
		res.addObject("requestURI", "user/user/list-reviewable.do");
		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final HttpServletRequest request) {
		final ModelAndView result;
		final Collection<Announcement> anns = this.announcementService.getLastCreatedOrJoinedAnnouncements(this.userService.findByPrincipal().getId());

		result = new ModelAndView("user/display");
		result.addObject("user", this.userService.findByPrincipal());
		result.addObject("me", true);
		result.addObject("announcements", anns);
		result.addObject("requestURI", "user/user/display.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final User s = this.userService.findByPrincipal();

		result = new ModelAndView("user/edit");
		result.addObject("user", s);

		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final User user, final BindingResult binding) {
		ModelAndView result;

		final User u = this.userService.reconstructEdition(user, binding);

		int age;

		if (binding.hasErrors())
			result = this.createEditModelAndView(u);
		else {
			final LocalDate birth = new LocalDate(u.getBirthdate().getYear() + 1900, u.getBirthdate().getMonth() + 1, u.getBirthdate().getDate());
			final LocalDate now = new LocalDate();
			age = Years.yearsBetween(birth, now).getYears();
			if (age < 18)
				result = this.createEditModelAndView(u, "user.underage.error");
			else
				try {
					this.userService.save(u);
					result = new ModelAndView("redirect:/user/user/display.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(u, "user.commit.error");
				}
		}

		return result;

	}

	protected ModelAndView createEditModelAndView(final User user) {
		final ModelAndView result;
		result = this.createEditModelAndView(user, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final User u, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("user/edit");
		result.addObject("user", u);
		result.addObject("message", messageCode);
		return result;
	}

}
