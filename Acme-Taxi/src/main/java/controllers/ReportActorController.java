
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ReportService;
import domain.Actor;
import domain.Admin;
import domain.Report;

@Controller
@RequestMapping("/report/actor")
public class ReportActorController extends AbstractController {

	@Autowired
	private ReportService	reportService;
	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView create(final Integer actorId) {
		try {
			final ModelAndView res = new ModelAndView("report/create");
			final Report r = this.reportService.create();
			final Actor reported = this.actorService.findOne(actorId);
			Assert.isTrue(!(reported instanceof Admin));
			r.setReported(reported);
			res.addObject("report", r);
			res.addObject("reported", reported.getName() + " " + reported.getSurname());
			return res;
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final Report report, final BindingResult bindingResult) {

	}
}
