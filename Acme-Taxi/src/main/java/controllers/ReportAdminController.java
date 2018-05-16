
package controllers;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ReportService;
import domain.Report;

@Controller
@RequestMapping("/report/admin")
public class ReportAdminController extends AbstractController {

	@Autowired
	private ReportService	reportService;
	@Autowired
	private ActorService	actorService;


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
		final ModelAndView res = new ModelAndView("report/list");
		res.addObject("reports", this.reportService.getAll(pageable));
		res.addObject("total", this.reportService.countAll());
		res.addObject("requestURI", "report/admin/list.do");
		res.addObject("justOne", false);
		return res;
	}

	@RequestMapping(value = "/listUnread", method = RequestMethod.GET)
	public ModelAndView listUnread(final HttpServletRequest request) {
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
		final ModelAndView res = new ModelAndView("report/listUnread");
		res.addObject("reports", this.reportService.getNotChecked(pageable));
		res.addObject("total", this.reportService.countNotChecked());
		res.addObject("requestURI", "report/admin/listUnread.do");
		res.addObject("justOne", false);
		return res;
	}

	@RequestMapping(value = "/list-user", method = RequestMethod.GET)
	public ModelAndView list_user(final Integer actorId, final HttpServletRequest request) {
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
		try {
			final ModelAndView res = new ModelAndView("report/list-user");
			res.addObject("reports", this.reportService.getReportByActor(actorId, pageable));
			res.addObject("total", this.reportService.countReportByActor(actorId));
			res.addObject("requestURI", "report/admin/list-user.do");
			res.addObject("justOne", true);
			res.addObject("nameWriter", this.actorService.findOne(actorId).getUserAccount().getUsername());
			return res;
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final Integer reportId) {
		try {
			final Report report = this.reportService.findOne(reportId);
			Assert.notNull(report);
			this.reportService.check(report);
			final ModelAndView res = new ModelAndView("report/display");
			res.addObject("report", report);
			return res;
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}
	}

}
