
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

import services.DriverService;
import services.RepairShopService;
import services.ReviewService;
import services.UserService;
import domain.Driver;
import domain.RepairShop;
import domain.Review;

@Controller
@RequestMapping("/review")
public class ReviewController extends AbstractController {

	@Autowired
	private ReviewService		reviewService;
	@Autowired
	private DriverService		driverService;
	@Autowired
	private UserService			userService;
	@Autowired
	private RepairShopService	repairShopService;


	@RequestMapping(value = "/list-driver", method = RequestMethod.GET)
	public ModelAndView listDriver(final int driverId, final HttpServletRequest request) {
		ModelAndView res;
		Assert.notNull(this.driverService.findOne(driverId));
		final Collection<Review> reviews;
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

		final Integer total = this.reviewService.countReviewsByDriverId(driverId);
		reviews = this.reviewService.findReviewsByDriverId(driverId, pageable);
		res = new ModelAndView("review/list");
		res.addObject("reviews", reviews);
		res.addObject("total", total);
		res.addObject("requestURI", "review/list.do");
		return res;
	}

	@RequestMapping(value = "/list-user", method = RequestMethod.GET)
	public ModelAndView listUser(final int userId, final HttpServletRequest request) {
		ModelAndView res;
		Assert.notNull(this.userService.findOne(userId));
		final Collection<Review> reviews;
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

		final Integer total = this.reviewService.countReviewsByUserId(userId);
		reviews = this.reviewService.findReviewsByUserId(userId, pageable);
		res = new ModelAndView("review/list");
		res.addObject("reviews", reviews);
		res.addObject("total", total);
		res.addObject("requestURI", "review/list.do");
		return res;
	}

	@RequestMapping(value = "/list-repairShop", method = RequestMethod.GET)
	public ModelAndView listRepairShop(final int repairShopId, final HttpServletRequest request) {
		ModelAndView res;
		Assert.notNull(this.repairShopService.findOne(repairShopId));
		final Collection<Review> reviews;
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

		final Integer total = this.reviewService.countReviewsByRepairShopId(repairShopId);
		reviews = this.reviewService.findReviewsByRepairShopId(repairShopId, pageable);
		res = new ModelAndView("review/list");
		res.addObject("reviews", reviews);
		res.addObject("total", total);
		res.addObject("requestURI", "review/list.do");
		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reviewId) {
		final ModelAndView result;
		result = new ModelAndView("review/display");
		final Review review = this.reviewService.findOne(reviewId);
		final Driver d = this.driverService.findDriverByReviewId(reviewId);
		final RepairShop rs = this.repairShopService.findRepairShopByReview(reviewId);
		if (d != null)
			result.addObject("driver", d);
		if (rs != null)
			result.addObject("repairShop", rs);
		result.addObject("creator", review.getCreator());
		result.addObject("review", review);
		result.addObject("requestURI", "review/display.do");

		return result;
	}
}
