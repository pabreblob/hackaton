
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.ReportService;
import domain.Actor;
import domain.Admin;
import domain.Report;

@Controller
@RequestMapping("/report/actor")
public class ReportActorController extends AbstractController {

	@Autowired
	private ReportService			reportService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView create(final Integer actorId) {
		try {
			final ModelAndView res = new ModelAndView("report/create");
			final Report r = this.reportService.create();
			final Actor reported = this.actorService.findOne(actorId);
			Assert.isTrue(!(reported instanceof Admin));
			Assert.isTrue(reported.getId() != this.actorService.findByPrincipal().getId());
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
		final Report r = this.reportService.recontruct(report, bindingResult);
		if (bindingResult.hasErrors()) {
			final ModelAndView res = new ModelAndView("report/create");
			res.addObject("report", r);
			res.addObject("reported", r.getReported().getName() + " " + r.getReported().getSurname());
			return res;
		}
		try {
			if (this.reportService.countReportThisWeek(this.actorService.findByPrincipal().getId()) + 1 > this.configurationService.find().getLimitReportsWeek())
				throw new IllegalArgumentException();
			this.reportService.save(r);
			return new ModelAndView("redirect:/actor/display.do?actorId=" + r.getReported().getId());
		} catch (final Throwable oops) {
			final ModelAndView res = new ModelAndView("report/create");
			res.addObject("report", r);
			res.addObject("reported", r.getReported().getName() + " " + r.getReported().getSurname());
			if (this.reportService.countReportThisWeek(this.actorService.findByPrincipal().getId()) >= this.configurationService.find().getLimitReportsWeek())
				res.addObject("message", "report.limit");
			else
				res.addObject("message", "report.cannotCommit");
			return res;
		}
	}
}
