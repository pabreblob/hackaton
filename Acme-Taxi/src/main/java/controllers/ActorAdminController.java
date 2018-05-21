
package controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;

@Controller
@RequestMapping("/actor/admin")
public class ActorAdminController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/listSuspicious", method = RequestMethod.GET)
	public ModelAndView listSuspicious(final HttpServletRequest request) {
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

		final ModelAndView res = new ModelAndView("actor/listSuspicious");
		res.addObject("actors", this.actorService.getSuspiciousActor(pageable));
		res.addObject("total", this.actorService.countSuspiciousActor());
		return res;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(final Integer actorId, @RequestParam(required = false) final String returnUri) {
		try {
			this.actorService.ban(actorId);
		} catch (final Throwable oops) {
		}
		if (returnUri == null)
			return new ModelAndView("redirect: ");
		else if (returnUri.equals("suspicious"))
			return new ModelAndView("redirect: listSuspicious.do");
		else
			return new ModelAndView("redirect:/actor/display.do?actorId=" + actorId);
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(final Integer actorId, @RequestParam(required = false) final String returnUri) {
		try {
			this.actorService.unban(actorId);
		} catch (final Throwable oops) {
		}
		if (returnUri == null)
			return new ModelAndView("redirect: ");
		else if (returnUri.equals("suspicious"))
			return new ModelAndView("redirect: listSuspicious.do");
		else
			return new ModelAndView("redirect:/actor/display.do?actorId=" + actorId);
	}

}
