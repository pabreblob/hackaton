
package controllers;

import java.util.Date;
import java.util.List;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.RequestService;
import services.UserService;
import utilities.GoogleMaps;
import domain.Configuration;
import domain.Request;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {

	@Autowired
	private RequestService			requestService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private UserService				userService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("request/edit");
		res.addObject("request", this.requestService.create());
		return res;
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ModelAndView confirm(final Request request, final BindingResult bindingResult) {
		final Request r = this.requestService.reconstruct(request, bindingResult);
		if (bindingResult.hasErrors()) {
			final ModelAndView res = new ModelAndView("request/edit");
			res.addObject("request", r);
			return res;
		}
		final List<Integer> distanciaYTiempo = GoogleMaps.getDistanceAndDuration(request.getOrigin(), request.getDestination());
		final LocalDate now = new LocalDate();
		final LocalDate moment = new LocalDate(r.getMoment());
		try {
			Assert.notNull(distanciaYTiempo);
			Assert.isTrue(moment.isAfter(now));
			final Configuration conf = this.configurationService.find();
			double price = (1 + conf.getVat()) * (conf.getMinimumFee() + (conf.getPricePerKm() * (distanciaYTiempo.get(0) * 1.0 / 1000)));
			int temp = (int) (price * 100);
			price = (temp * 1.0) / 100;
			r.setPrice(price);
			r.setDistance(distanciaYTiempo.get(0));
			double time = 1.0 * distanciaYTiempo.get(1) / 3600;
			temp = (int) (time * 100);
			time = (temp * 1.0) / 100;
			r.setEstimatedTime(time);
			final ModelAndView res = new ModelAndView("request/confirm");
			res.addObject("currency", conf.getCurrency());
			res.addObject("request", r);
			String estimated = "";
			final int hours = distanciaYTiempo.get(1) / 3600;
			final int minutes = (distanciaYTiempo.get(1) - hours * 3600) / 60;
			estimated = hours + "h " + minutes + "min";
			res.addObject("estimated", estimated);
			return res;
		} catch (final Throwable oops) {
			if (distanciaYTiempo == null) {
				final ModelAndView res = new ModelAndView("request/edit");
				res.addObject("request", r);
				res.addObject("message", "request.originOrDestinationFail");
				return res;
			}
			if (!moment.isAfter(now)) {
				final ModelAndView res = new ModelAndView("request/edit");
				res.addObject("request", r);
				res.addObject("message", "request.dateMustBeFuture");
				return res;
			}
			final ModelAndView res = new ModelAndView("request/edit");
			res.addObject("request", r);
			res.addObject("message", "request.cannotCommit");
			return res;
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final Request request, final BindingResult bindingResult) {
		final Request r = this.requestService.reconstruct(request, bindingResult);
		try {
			final Request saved = this.requestService.save(r);
			return new ModelAndView("redirect:/request/user/display.do?requestId=" + saved.getId());
		} catch (final Throwable oops) {
			final ModelAndView res = new ModelAndView("request/edit");
			res.addObject("request", r);
			res.addObject("message", "request.cannotCommit");
			return res;
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
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

		final ModelAndView res = new ModelAndView("request/list");
		res.addObject("requests", this.requestService.getRequestByUser(this.userService.findByPrincipal().getId(), pageable));
		res.addObject("total", this.requestService.countRequestByUser(this.userService.findByPrincipal().getId()));
		res.addObject("now", new Date());
		res.addObject("currency", this.configurationService.find().getCurrency());
		res.addObject("requestURI", "request/user/list.do");
		return res;
	}

}