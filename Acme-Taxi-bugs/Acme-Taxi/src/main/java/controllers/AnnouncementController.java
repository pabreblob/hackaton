
package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.CommentService;
import services.ConfigurationService;
import domain.Announcement;
import domain.Comment;
import domain.User;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController extends AbstractController {

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private MessageSource			messageSource;

	@Autowired
	private CommentService			commentService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
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
		announcements = this.announcementService.getCurrentAnnouncements(pageable);
		final Integer total = this.announcementService.countCurrentAnnouncements();
		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("currency", this.configurationService.find().getCurrency());
		result.addObject("requestURI", "announcement/list.do");
		result.addObject("currentDate", new Date());
		result.addObject("total", total);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int announcementId) {
		ModelAndView result;
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		final Announcement announcement = this.announcementService.findOne(announcementId);
		Assert.notNull(announcement);
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
		result.addObject("requestURI", "announcement/display.do");
		return result;
	}

	@RequestMapping("/search")
	public ModelAndView search() {
		final ModelAndView res = new ModelAndView("announcement/search");
		return res;
	}

	@RequestMapping(value = "/searchcont", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
	public @ResponseBody
	String search(@RequestParam final String keyword) {
		final Collection<Announcement> announcements = this.announcementService.findAnnouncementsByKeyword(keyword);
		String res = "";
		final String yesmess = this.messageSource.getMessage("announcement.yes", null, LocaleContextHolder.getLocale());
		final String nomess = this.messageSource.getMessage("announcement.no", null, LocaleContextHolder.getLocale());
		for (final Announcement a : announcements) {
			final String petsa = a.isPetsAllowed() ? yesmess : nomess;
			final String smokinga = a.isSmokingAllowed() ? yesmess : nomess;
			final String cancelled = a.isCancelled() ? yesmess : nomess;
			final String currency = this.configurationService.find().getCurrency();
			res += "<tr><td>" + a.getTitle() + "</td><td>" + a.getOrigin() + "</td><td>" + a.getDestination() + "</td><td>" + a.getPricePerPerson() + " " + currency + "</td><td>"
				+ new SimpleDateFormat(this.messageSource.getMessage("moment.date.format", null, LocaleContextHolder.getLocale())).format(a.getMoment()) + "</td><td>" + petsa + "</td><td>" + smokinga + "</td><td>" + cancelled
				+ "</td><td><a href='announcement/display.do?announcementId=" + a.getId() + "'>" + this.messageSource.getMessage("announcement.display", null, LocaleContextHolder.getLocale()) + "</a></td></tr>";
		}
		return res;
	}
}
