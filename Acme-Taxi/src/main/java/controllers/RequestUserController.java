
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {

	@Autowired
	private RequestService	requestService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("request/edit");
		res.addObject("request", this.requestService.create());
		return res;
	}

}
