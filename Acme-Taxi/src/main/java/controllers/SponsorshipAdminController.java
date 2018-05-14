
package controllers;

import java.util.Collection;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorshipService;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/admin")
public class SponsorshipAdminController extends AbstractController {

	@Autowired
	private SponsorshipService	sponsorshipService;


	//Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
		ModelAndView res;
		final Collection<Sponsorship> sponsorships;
		Pageable pageable;
		Direction dir = null;
		Integer pageNum = 0;
		final String pageNumStr = request.getParameter(new ParamEncoder("sponsorship").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
		final String sortAtt = request.getParameter(new ParamEncoder("sponsorship").encodeParameterName(TableTagParameters.PARAMETER_SORT));
		final String sortOrder = request.getParameter(new ParamEncoder("sponsorship").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
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

		final Integer total = this.sponsorshipService.countSponsorshipNotAccepted();
		sponsorships = this.sponsorshipService.findSponsorshipNotAccepted(pageable);
		res = new ModelAndView("sponsorship/list");
		res.addObject("sponsorships", sponsorships);
		res.addObject("total", total);
		res.addObject("requestURI", "sponsorship/admin/list.do");

		return res;
	}

	// Accept
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int sponsorshipId) {
		ModelAndView res;
		Sponsorship s;
		s = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(s);
		Assert.isTrue(!s.isAccepted());
		this.sponsorshipService.accept(s);
		res = new ModelAndView("redirect:list.do");

		return res;
	}
	//Displaying
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView res;
		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(sponsorship);
		res = new ModelAndView("sponsorship/display");
		res.addObject("sponsorship", sponsorship);
		return res;
	}

}
