
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.CommentService;
import domain.Announcement;
import domain.Comment;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController extends AbstractController {

	@Autowired
	private CommentService		commentService;
	@Autowired
	private AnnouncementService	announcementService;


	@RequestMapping(value = "/create-comment", method = RequestMethod.GET)
	public ModelAndView createComment(@RequestParam final int announcementId, @RequestParam final String c) {
		final Comment comment = this.commentService.create();

		final ModelAndView res = new ModelAndView("comment/edit");
		res.addObject("comment", comment);
		res.addObject("announcementId", announcementId);
		res.addObject("reply", false);

		if (c.equals("t"))
			res.addObject("c", true);
		else if (c.equals("f"))
			res.addObject("c", false);

		return res;
	}

	@RequestMapping(value = "/create-reply", method = RequestMethod.GET)
	public ModelAndView createReply(@RequestParam final int commentId, @RequestParam final String c) {
		final Comment comment = this.commentService.create();
		final int announcementId = this.commentService.findOne(commentId).getAnnouncement().getId();

		final ModelAndView res = new ModelAndView("comment/edit");
		res.addObject("comment", comment);
		res.addObject("commentId", commentId);
		res.addObject("announcementId", announcementId);
		res.addObject("reply", true);

		if (c.equals("t"))
			res.addObject("c", true);
		else if (c.equals("f"))
			res.addObject("c", false);

		return res;
	}

	@RequestMapping(value = "/save-comment", method = RequestMethod.POST, params = "save")
	public ModelAndView saveComment(final Comment comment, final BindingResult binding, @RequestParam final int announcementId, @RequestParam final String c) {
		ModelAndView res;
		final Announcement a = this.announcementService.findOne(announcementId);
		final Comment cres = this.commentService.reconstruct(comment, null, a, binding);

		if (binding.hasErrors()) {
			res = new ModelAndView("comment/edit");
			res.addObject("comment", cres);
			res.addObject("announcementId", announcementId);
			res.addObject("reply", false);

			if (c.equals("t"))
				res.addObject("c", true);
			else if (c.equals("f"))
				res.addObject("c", false);

			System.out.println(binding);
			System.out.println("argo fue malamente");

		} else
			try {
				this.commentService.save(cres);
				if (c.equals("t"))
					res = new ModelAndView("redirect:/announcement/user/display-created.do?announcementId=" + announcementId);
				else
					res = new ModelAndView("redirect:/announcement/user/display.do?announcementId=" + announcementId);

				System.out.println("to va bien pero no");
			} catch (final Throwable oops) {
				res = new ModelAndView("comment/edit");
				res.addObject("comment", cres);
				res.addObject("announcementId", announcementId);
				res.addObject("reply", false);
				res.addObject("message", "comment.commit.error");

				if (c.equals("t"))
					res.addObject("c", true);
				else if (c.equals("f"))
					res.addObject("c", false);

				System.out.println("cabesa que ta'squivocao");
			}
		return res;
	}

	@RequestMapping(value = "/save-reply", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReply(final Comment comment, final BindingResult binding, @RequestParam final int commentId, @RequestParam final String c) {
		ModelAndView res;
		final Comment c2 = this.commentService.findOne(commentId);
		final Announcement a = c2.getAnnouncement();
		final Comment cres = this.commentService.reconstruct(comment, c2, a, binding);

		if (binding.hasErrors()) {
			res = new ModelAndView("comment/edit");
			res.addObject("comment", cres);
			res.addObject("commentId", commentId);
			res.addObject("announcementId", a.getId());
			res.addObject("reply", true);

			if (c.equals("t"))
				res.addObject("c", true);
			else if (c.equals("f"))
				res.addObject("c", false);

		} else
			try {
				this.commentService.save(cres);
				if (c.equals("t"))
					res = new ModelAndView("redirect:/announcement/user/display-created.do?announcementId=" + a.getId());
				else
					res = new ModelAndView("redirect:/announcement/user/display.do?announcementId=" + a.getId());
			} catch (final Throwable oops) {
				res = new ModelAndView("comment/edit");
				res.addObject("comment", cres);
				res.addObject("commentId", commentId);
				res.addObject("announcementId", a.getId());
				res.addObject("reply", true);
				res.addObject("message", "comment.commit.error");

				if (c.equals("t"))
					res.addObject("c", true);
				else if (c.equals("f"))
					res.addObject("c", false);
			}
		return res;
	}

}
