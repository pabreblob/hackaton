
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import domain.Announcement;
import domain.Comment;

@Controller
@RequestMapping("/comment/admin")
public class CommentAdminController extends AbstractController {

	@Autowired
	private CommentService				commentService;
	@Autowired
	private AnnouncementAdminController	announcementAdminController;


	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam final int commentId) {
		final Comment c = this.commentService.findOne(commentId);
		final Announcement a = c.getAnnouncement();
		ModelAndView res;
		if (!c.getReplies().isEmpty()) {
			res = this.announcementAdminController.display(a.getId());
			res.addObject("message", "comment.reply.error");
		} else
			try {
				this.commentService.delete(c);
				res = new ModelAndView("redirect:/announcement/admin/display.do?announcementId=" + a.getId());
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/announcement/admin/display.do?announcementId=" + a.getId());
				res.addObject("message", "comment.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/list")
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
		final Collection<Comment> marked = this.commentService.findMarkedComments(pageable);
		final Integer total = marked.size();
		final ModelAndView res = new ModelAndView("comment/list");
		res.addObject("marked", marked);
		res.addObject("requestURI", "comment/admin/list.do");
		res.addObject("total", total);

		return res;

	}

}
