
package controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.DriverService;
import services.RequestService;
import domain.Configuration;
import domain.Request;

@Controller
@RequestMapping("/request/driver")
public class RequestDriverController extends AbstractController {

	@Autowired
	private RequestService			requestService;
	@Autowired
	private DriverService			driverService;
	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/listToAccept", method = RequestMethod.GET)
	public ModelAndView listToAccept(final HttpServletRequest request) {
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
		final ModelAndView res = new ModelAndView("request/listToAccept");
		res.addObject("requests", this.requestService.getRequestToAccept(pageable));
		res.addObject("total", this.requestService.countRequestToAccept());
		res.addObject("now", new Date());
		res.addObject("currency", this.configurationService.find().getCurrency());
		res.addObject("requestURI", "request/driver/listToAccept.do");
		if (this.driverService.findByPrincipal().getCar() == null)
			res.addObject("message", "request.noCar");
		return res;
	}

	@RequestMapping(value = "/listToDo", method = RequestMethod.GET)
	public ModelAndView listToDo(final HttpServletRequest request) {
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
		final ModelAndView res = new ModelAndView("request/listToDo");
		res.addObject("requests", this.requestService.getRequestToDo(pageable));
		res.addObject("total", this.requestService.countRequestToDo());
		res.addObject("now", new Date());
		res.addObject("currency", this.configurationService.find().getCurrency());
		res.addObject("requestURI", "request/driver/listToDo.do");
		return res;
	}

	@RequestMapping(value = "/oldList", method = RequestMethod.GET)
	public ModelAndView listOld(final HttpServletRequest request) {
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
		final ModelAndView res = new ModelAndView("request/oldList");
		res.addObject("requests", this.requestService.getFinishedRequest(pageable));
		res.addObject("total", this.requestService.countFinishedRequest());
		res.addObject("now", new Date());
		res.addObject("currency", this.configurationService.find().getCurrency());
		res.addObject("requestURI", "request/driver/oldList.do");
		return res;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(final int requestId) {
		try {
			this.requestService.accept(requestId);
			return new ModelAndView("redirect:listToDo.do");
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:listToAccept.do");
		}
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final int requestId) {
		try {
			final Request r = this.requestService.findOne(requestId);
			Assert.notNull(r);
			if (!(r.getDriver() == null)) {
				final LocalDate moment = new LocalDate(r.getMoment());
				final LocalDate now = new LocalDate();
				Assert.isTrue(moment.isAfter(now));
			}
			final ModelAndView res = new ModelAndView("request/display");
			final Configuration conf = this.configurationService.find();
			res.addObject("currency", conf.getCurrency());
			res.addObject("request", r);
			String estimated = "";
			final int hours = (int) (r.getEstimatedTime() / 3600);
			final int minutes = (int) ((r.getEstimatedTime() - hours * 3600) / 60);
			estimated = hours + "h " + minutes + "min";
			res.addObject("estimated", estimated);
			res.addObject("maxPass", this.driverService.findByPrincipal().getCar().getMaxPassengers());
			res.addObject("now", new Date());
			return res;
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}
	}
}
