
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import domain.Actor;
import domain.Admin;
import domain.Driver;
import domain.Mechanic;
import domain.Sponsor;
import domain.User;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private MessageSource	messageSource;


	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("actor/list");
		return res;
	}

	@RequestMapping(value = "/ajaxsearchcont", method = RequestMethod.GET)
	public @ResponseBody
	String ajaxsearch(@RequestParam final String username) {
		final Collection<Actor> actors = this.actorService.findByUsername(username);
		String res = "";
		String auth = null;
		List<Authority> roles;
		final String link = this.messageSource.getMessage("actor.link", null, LocaleContextHolder.getLocale());
		final String linkEnd = this.messageSource.getMessage("actor.linkEnd", null, LocaleContextHolder.getLocale());
		for (final Actor a : actors) {
			roles = new ArrayList<>(a.getUserAccount().getAuthorities());
			switch (roles.get(0).getAuthority()) {
			case "ADMIN":
				auth = this.messageSource.getMessage("actor.admin", null, LocaleContextHolder.getLocale());
				break;
			case "USER":
				auth = this.messageSource.getMessage("actor.user", null, LocaleContextHolder.getLocale());
				break;
			case "DRIVER":
				auth = this.messageSource.getMessage("actor.driver", null, LocaleContextHolder.getLocale());
				break;
			case "SPONSOR":
				auth = this.messageSource.getMessage("actor.sponsor", null, LocaleContextHolder.getLocale());
				break;
			case "MECHANIC":
				auth = this.messageSource.getMessage("actor.mechanic", null, LocaleContextHolder.getLocale());
				break;
			}
			res += "<tr><td>" + auth + "</td><td>" + a.getUserAccount().getUsername() + "</td><td>" + a.getName() + "</td><td>" + a.getSurname() + "</td><td>" + link + a.getId() + linkEnd + "</td></tr>";
		}
		return res;
	}

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam final int actorId) {
		final Actor a = this.actorService.findOne(actorId);
		Assert.notNull(a);
		if (a instanceof Admin)
			return new ModelAndView("redirect:/admin/display.do?adminId=" + actorId);
		else if (a instanceof Mechanic)
			return new ModelAndView("redirect:/mechanic/display.do?mechanicId=" + actorId);
		else if (a instanceof User)
			return new ModelAndView("redirect:/user/display.do?userId=" + actorId);
		else if (a instanceof Sponsor)
			return new ModelAndView("redirect:/sponsor/display.do?sponsorId=" + actorId);
		else if (a instanceof Driver)
			return new ModelAndView("redirect:/driver/display.do?driverId=" + actorId);
		else
			throw new IllegalArgumentException();

	}
}
