
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
	private CommentService	commentService;


	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam final int commentId) {
		final Comment c = this.commentService.findOne(commentId);
		final Announcement a = c.getAnnouncement();
		ModelAndView res;

		try {
			this.commentService.delete(c);
			res = new ModelAndView("redirect:/announcement/admin/display.do?announcementId=" + a.getId());
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/announcement/admin/display.do?announcementId=" + a.getId());
			res.addObject("message", "comment.commit.error");
		}
		return res;
	}

}
