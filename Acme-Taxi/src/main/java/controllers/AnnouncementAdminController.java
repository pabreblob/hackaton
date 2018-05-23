
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.Hibernate;
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

import services.AnnouncementService;
import services.CommentService;
import services.ConfigurationService;
import domain.Announcement;
import domain.Comment;
import domain.User;

@Controller
@RequestMapping("/announcement/admin")
public class AnnouncementAdminController extends AbstractController {

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private ConfigurationService	configurationService;

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
		announcements = this.announcementService.getAllAnnouncements(pageable);
		final Integer total = this.announcementService.countAllAnnouncements();
		result = new ModelAndView("announcement/list");
		result.addObject("announcements", announcements);
		result.addObject("currency", this.configurationService.find().getCurrency());
		result.addObject("requestURI", "announcement/admin/list.do");
		result.addObject("currentDate", new Date());
		result.addObject("total", total);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int announcementId) {
		ModelAndView res;
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		this.announcementService.deleteAdmin(announcementId);
		res = new ModelAndView("redirect:list.do");

		return res;
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
		result.addObject("requestURI", "announcement/admin/display.do");
		return result;
	}
}
