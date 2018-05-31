
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.SponsorService;
import services.SponsorshipService;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ConfigurationService	configurationService;


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

		final Integer total = this.sponsorshipService.countSponsorshipBySponsorId();
		sponsorships = this.sponsorshipService.findSponsorshipByPrincipal(pageable);
		res = new ModelAndView("sponsorship/list");
		res.addObject("sponsorships", sponsorships);
		res.addObject("total", total);
		res.addObject("requestURI", "sponsorship/sponsor/list.do");

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

	//Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res;
		final Sponsorship sponsorship = this.sponsorshipService.create();
		final List<Sponsorship> sponsorships = new ArrayList<>(this.sponsorshipService.findSponsorshipByPrincipal());
		if (sponsorships.size() != 0)
			sponsorship.setCreditCard(sponsorships.get(sponsorships.size() - 1).getCreditCard());
		res = this.createEditModelAndView(sponsorship);
		return res;
	}
	// Cancel
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int sponsorshipId) {
		ModelAndView res;
		Sponsorship s;
		s = this.sponsorshipService.findOne(sponsorshipId);
		Assert.isTrue(s.isAccepted());
		Assert.isTrue(this.sponsorService.findByPrincipal().equals(s.getSponsor()));
		//this.sponsorshipService.cancel(s);
		res = new ModelAndView("redirect:list.do");

		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sponsorshipId) {
		ModelAndView res;
		Sponsorship s;
		s = this.sponsorshipService.findOne(sponsorshipId);
		Assert.isTrue(!s.isAccepted());
		Assert.isTrue(this.sponsorService.findByPrincipal().equals(s.getSponsor()));
		this.sponsorshipService.delete(s);
		res = new ModelAndView("redirect:list.do");

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView res;
		final Sponsorship sp = this.sponsorshipService.reconstruct(sponsorship, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(sp);
		//		else if (SponsorshipSponsorController.checkDate(sp.getCreditCard().getExpMonth(), sp.getCreditCard().getExpYear()) == false) {
		//			res = new ModelAndView("sponsorship/edit");
		//			res.addObject("sponsorship", sp);
		//			res.addObject("message", "sponsorship.invalidcc");

		//		} else
		try {
			this.sponsorshipService.save(sp);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(sp, "sponsorship.commit.error");
		}
		return res;
	}

	private ModelAndView createEditModelAndView(final Sponsorship sponsorship) {

		return this.createEditModelAndView(sponsorship, null);
	}

	private ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String messageCode) {
		final ModelAndView result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("price", sponsorship.getPrice());
		result.addObject("currency", this.configurationService.find().getCurrency());
		result.addObject("message", messageCode);
		return result;
	}
	//	@SuppressWarnings("deprecation")
	//	private static boolean checkDate(final int month, final int year) {
	//		boolean res = false;
	//		final Date now = new Date();
	//		if (now.getYear() - 100 < year)
	//			res = true;
	//		if (now.getYear() - 100 == year && now.getMonth() + 1 <= month)
	//			res = true;
	//
	//		return res;
	//	}
}
