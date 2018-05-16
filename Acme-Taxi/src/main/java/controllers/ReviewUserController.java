
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import services.DriverService;
import services.ReviewService;
import services.UserService;
import domain.Driver;
import domain.Review;
import domain.User;
import forms.ReviewForm;

@Controller
@RequestMapping("/review/user")
public class ReviewUserController extends AbstractController {

	@Autowired
	private ReviewService	reviewService;
	@Autowired
	private DriverService	driverService;
	@Autowired
	private UserService		userService;


	@RequestMapping(value = "/list-created", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
		ModelAndView res;
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

		final Integer total = this.reviewService.countReviewsByPrincipal();
		reviews = this.reviewService.findReviewsByPrincipal(pageable);
		res = new ModelAndView("review/list");
		res.addObject("reviews", reviews);
		res.addObject("total", total);
		res.addObject("requestURI", "review/user/list-created.do");
		return res;
	}

	@RequestMapping(value = "/create-driver", method = RequestMethod.GET)
	public ModelAndView createDriver(@RequestParam final int driverId) {
		final ModelAndView result;
		final ReviewForm reviewForm = new ReviewForm();
		final User u = this.userService.findByPrincipal();
		final Driver d = this.driverService.findOne(driverId);
		Assert.notNull(d);
		reviewForm.setDriver(d);
		reviewForm.setCreator(u);

		result = new ModelAndView("review/edit");
		result.addObject("reviewForm", reviewForm);
		result.addObject("form", "true");
		result.addObject("cancel", "driver/user/list.do");

		return result;
	}

	@RequestMapping(value = "/save-driver", method = RequestMethod.POST, params = "save")
	public ModelAndView saveDriver(final ReviewForm reviewForm, final BindingResult binding) {
		ModelAndView result;
		reviewForm.setMoment(new Date(System.currentTimeMillis() - 1000));
		final Review r = this.reviewService.reconstructDriver(reviewForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewForm(reviewForm);
		else
			try {
				this.reviewService.saveDriver(r, reviewForm.getDriver().getId());
				result = new ModelAndView("redirect:list-created.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewForm(reviewForm, "review.commit.error");
			}
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteDriver(@RequestParam final int reviewId) {
		ModelAndView res;
		this.reviewService.delete(reviewId);
		res = new ModelAndView("redirect:list-created.do");
		return res;
	}

	//Editing
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reviewId) {
		ModelAndView res;
		Review r;
		r = this.reviewService.findOne(reviewId);
		Assert.notNull(r);
		Assert.isTrue(r.getCreator().equals(this.userService.findByPrincipal()));

		res = this.createEditModelAndView(r);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Review r, final BindingResult binding) {
		ModelAndView res;
		try {
			this.reviewService.update(r);
			res = new ModelAndView("redirect:list-created.do");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(r, "review.commit.error");
		}
		return res;

	}

	protected ModelAndView createEditModelAndViewForm(final ReviewForm reviewForm) {
		final ModelAndView result;
		result = this.createEditModelAndViewForm(reviewForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewForm(final ReviewForm reviewForm, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("review/edit");
		result.addObject("reviewForm", reviewForm);
		result.addObject("form", "true");
		result.addObject("cancel", "driver/user/list.do");
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Review r) {
		return this.createEditModelAndView(r, null);
	}
	protected ModelAndView createEditModelAndView(final Review r, final String messageCode) {
		ModelAndView res;
		res = new ModelAndView("review/edit");
		res.addObject("review", r);
		res.addObject("form", "false");
		res.addObject("cancel", "driver/user/list.do");
		res.addObject("message", messageCode);
		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reviewId) {
		final ModelAndView result;
		result = new ModelAndView("review/display");
		final Review review = this.reviewService.findOne(reviewId);
		Assert.isTrue(review.getCreator().equals(this.userService.findByPrincipal()));
		result.addObject("review", review);
		result.addObject("requestURI", "review/user/display.do");

		return result;
	}
}
