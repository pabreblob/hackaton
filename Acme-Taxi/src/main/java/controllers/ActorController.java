
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import domain.Actor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService	actorService;


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
		List<Authority> temp;
		for (final Actor a : actors) {
			temp = new ArrayList<>(a.getUserAccount().getAuthorities());
			res += "<tr><td>" + temp.get(0) + "</td><td>" + a.getUserAccount().getUsername() + "</td><td>" + a.getName() + "</td><td>" + a.getSurname() + "</td><td>" + a.getId() + "</td></tr>";
		}
		return res;
	}
}
