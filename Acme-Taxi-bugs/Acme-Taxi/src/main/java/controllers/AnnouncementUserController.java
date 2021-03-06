
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.Hibernate;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
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

import services.AnnouncementService;
import services.CommentService;
import services.ConfigurationService;
import services.UserService;
import domain.Announcement;
import domain.Comment;
import domain.User;
import forms.AnnouncementFinderForm;

@Controller
@RequestMapping("/announcement/user")
public class AnnouncementUserController extends AbstractController {

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private UserService				userService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CommentService			commentService;


	@RequestMapping(value = "/list-created", method = RequestMethod.GET)
	public ModelAndView listCreated(final HttpServletRequest request) {
		ModelAndView result;
		final Collection<Announcement> announcements;
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
		announcements = this.announcementService.getCreatedAnnouncementsByUserId(pageable);
		final Integer total = this.announcementService.countCreatedAnnouncementsByUserId();
		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("currency", this.configurationService.find().getCurrency());
		result.addObject("requestURI", "announcement/user/list-created.do");
		result.addObject("currentDate", new Date());
		result.addObject("total", total);
		return result;
	}

	@RequestMapping(value = "/list-joined", method = RequestMethod.GET)
	public ModelAndView listJoined(final HttpServletRequest request) {
		ModelAndView result;
		final Collection<Announcement> announcements;
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
		announcements = this.announcementService.getJoinedAnnouncementsByUserId(pageable);
		final Integer total = this.announcementService.countJoinedAnnouncementsByUserId();
		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("currency", this.configurationService.find().getCurrency());
		result.addObject("requestURI", "announcement/user/list-joined.do");
		result.addObject("currentDate", new Date());
		result.addObject("total", total);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAvailable(final HttpServletRequest request) {
		ModelAndView result;
		final Collection<Announcement> announcements;
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
		announcements = this.announcementService.getAvailableAnnouncements(pageable);
		final Integer total = this.announcementService.countAvailableAnnouncements();
		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("currency", this.configurationService.find().getCurrency());
		result.addObject("requestURI", "announcement/user/list.do");
		result.addObject("currentDate", new Date());
		result.addObject("total", total);
		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int announcementId) {
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		ModelAndView result;
		result = new ModelAndView("redirect:/announcement/user/list-created.do");
		return result;
	}

	@RequestMapping(value = "/dropout", method = RequestMethod.GET)
	public ModelAndView dropOut(@RequestParam final int announcementId) {
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		ModelAndView result;
		this.announcementService.dropOut(announcementId);
		result = new ModelAndView("redirect:/announcement/user/list-joined.do");
		return result;
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView join(@RequestParam final int announcementId) {
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		ModelAndView result;
		this.announcementService.join(announcementId);
		result = new ModelAndView("redirect:/announcement/user/list-joined.do");
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Announcement announcement;

		announcement = this.announcementService.create();
		result = this.createEditModelAndView(announcement);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int announcementId) {
		ModelAndView res;
		Announcement announcement;

		announcement = this.announcementService.findOne(announcementId);
		Assert.notNull(announcement);
		res = new ModelAndView("announcement/edit");
		res.addObject("announcement", announcement);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Announcement announcement, final BindingResult binding) {
		ModelAndView result;
		final Announcement ann = this.announcementService.reconstruct(announcement, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(ann);
		else
			try {
				final LocalDateTime now = new LocalDateTime();
				final LocalDateTime moment = new LocalDateTime(announcement.getMoment());
				if (!moment.isAfter(now))
					result = this.createEditModelAndView(ann, "announcement.moment.error");
				else {
					this.announcementService.save(ann);
					result = new ModelAndView("redirect:list-created.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(ann, "announcement.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Announcement announcement, final BindingResult binding) {
		ModelAndView res;
		final Announcement ann = this.announcementService.reconstruct(announcement, binding);
		try {
			this.announcementService.delete(ann);
			res = new ModelAndView("redirect:list-created.do");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(ann, "announcement.commit.error");
		}

		return res;
	}

	@RequestMapping(value = "/display-created", method = RequestMethod.GET)
	public ModelAndView displayCreated(@RequestParam final int announcementId) {
		ModelAndView result;
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		final Announcement announcement = this.announcementService.findOne(announcementId);
		Assert.notNull(announcement);
		Assert.isTrue(announcement.getCreator().equals(this.userService.findByPrincipal()));
		final Collection<User> attendants = announcement.getAttendants();

		final Collection<Comment> comments = this.commentService.findCommentsOrdered(announcementId);
		final boolean hasComment = !comments.isEmpty();
		for (final Comment c : comments)
			Hibernate.initialize(c.getReplies());

		result = new ModelAndView("announcement/display");
		result.addObject("announcement", announcement);
		result.addObject("attendants", attendants);
		result.addObject("currency", this.configurationService.find().getCurrency());
		result.addObject("remaining", announcement.getSeats() - announcement.getAttendants().size());
		result.addObject("comments", comments);
		result.addObject("hasComment", hasComment);
		result.addObject("isCreator", true);
		result.addObject("requestURI", "announcement/user/display-created.do");
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int announcementId) {
		ModelAndView result;
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		final Announcement announcement = this.announcementService.findOne(announcementId);
		Assert.notNull(announcement);
		Assert.isTrue(!announcement.getCreator().equals(this.userService.findByPrincipal()));
		final Collection<User> attendants = announcement.getAttendants();
		final boolean joined = announcement.getAttendants().contains(this.userService.findByPrincipal());
		final LocalDateTime now = new LocalDateTime();
		final LocalDateTime moment = new LocalDateTime(announcement.getMoment());
		final boolean joinable = !joined && !announcement.isCancelled() && Hours.hoursBetween(now, moment).getHours() >= 2 && announcement.getAttendants().size() < announcement.getSeats();

		final Collection<Comment> comments = this.commentService.findCommentsOrdered(announcementId);
		final boolean hasComment = !comments.isEmpty();
		for (final Comment c : comments)
			Hibernate.initialize(c.getReplies());

		result = new ModelAndView("announcement/display");
		result.addObject("announcement", announcement);
		result.addObject("attendants", attendants);
		result.addObject("currency", this.configurationService.find().getCurrency());
		result.addObject("remaining", announcement.getSeats() - announcement.getAttendants().size());
		result.addObject("joined", joined);
		result.addObject("joinable", joinable);
		result.addObject("comments", comments);
		result.addObject("hasComment", hasComment);
		result.addObject("isCreator", false);
		result.addObject("requestURI", "announcement/user/display.do");
		return result;
	}

	@RequestMapping("/finder")
	public ModelAndView finder() {
		ModelAndView res;
		final AnnouncementFinderForm announcementFinderForm = new AnnouncementFinderForm();
		res = this.createEditModelAndView(announcementFinderForm);
		return res;
	}

	@RequestMapping(value = "/finderresult", method = RequestMethod.POST, params = "send")
	public ModelAndView finderResult(@Valid final AnnouncementFinderForm announcementFinderForm, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(announcementFinderForm);
		else
			try {
				final String keyword = announcementFinderForm.getKeyword();
				final Double minPrice = announcementFinderForm.getMinPrice();
				final Double maxPrice = announcementFinderForm.getMaxPrice();
				final Date moment = announcementFinderForm.getMoment();
				final String origin = announcementFinderForm.getOrigin();
				final String destination = announcementFinderForm.getDestination();
				final Collection<Announcement> announcements = this.announcementService.findAnnouncementsFinder(keyword, minPrice, maxPrice, moment, origin, destination);
				res = new ModelAndView("announcement/finderresult");
				res.addObject("announcements", announcements);
				res.addObject("currency", this.configurationService.find().getCurrency());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(announcementFinderForm, "announcement.commit.error");
			}
		return res;
	}

	protected ModelAndView createEditModelAndView(final Announcement announcement) {
		ModelAndView result;

		result = this.createEditModelAndView(announcement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Announcement announcement, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("announcement/edit");
		result.addObject("announcement", announcement);
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AnnouncementFinderForm announcementFinderForm) {
		ModelAndView result;

		result = this.createEditModelAndView(announcementFinderForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final AnnouncementFinderForm announcementFinderForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("announcement/finder");
		result.addObject("announcementFinderForm", announcementFinderForm);
		result.addObject("message", messageCode);
		return result;
	}
}
