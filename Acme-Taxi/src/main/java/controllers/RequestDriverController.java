
package controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.DriverService;
import services.RequestService;

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
		final ModelAndView res = new ModelAndView("request/list");
		res.addObject("requests", this.requestService.getRequestToAccept(pageable));
		res.addObject("total", this.requestService.countRequestToAccept());
		res.addObject("now", new Date());
		res.addObject("currency", this.configurationService.find().getCurrency());
		res.addObject("requestURI", "request/driver/listToAccept.do");
		if (this.driverService.findByPrincipal().getCar() == null)
			res.addObject("message", "request.noCar");
		return res;
	}
}
