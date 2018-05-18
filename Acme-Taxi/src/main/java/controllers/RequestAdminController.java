
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
import services.RequestService;

@Controller
@RequestMapping("/request/admin")
public class RequestAdminController extends AbstractController {

	@Autowired
	private RequestService			requestService;
	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/markedList", method = RequestMethod.GET)
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
		final ModelAndView res = new ModelAndView("request/markedList");
		res.addObject("requests", this.requestService.getMarked(pageable));
		res.addObject("total", this.requestService.countMarked());
		res.addObject("now", new Date());
		res.addObject("currency", this.configurationService.find().getCurrency());
		res.addObject("requestURI", "request/admin/markedList.do");
		return res;
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
		res.addObject("requests", this.requestService.getAll(pageable));
		res.addObject("total", this.requestService.countAll());
		res.addObject("now", new Date());
		res.addObject("currency", this.configurationService.find().getCurrency());
		res.addObject("requestURI", "request/admin/list.do");
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final Integer requestId) {
		try {
			this.requestService.adminDelete(requestId);
			return new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:list.do");
		}
	}
}
