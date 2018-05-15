
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;

@Controller
@RequestMapping("/actor/actor")
public class ActorActorController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/block", method = RequestMethod.GET)
	public ModelAndView block(final Integer actorId) {
		try {
			this.actorService.block(actorId);
			return new ModelAndView("redirect:/actor/display.do?actorId=" + actorId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}
	}

	@RequestMapping(value = "/unblock", method = RequestMethod.GET)
	public ModelAndView unblock(final Integer actorId) {
		try {
			this.actorService.unblock(actorId);
			return new ModelAndView("redirect:/actor/display.do?actorId=" + actorId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}
	}

}
