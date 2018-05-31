
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Announcement;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository	commentRepository;
	@Autowired
	private UserService			userService;
	@Autowired
	private AdminService		adminService;
	//	@Autowired
	//	private SpamWordService		spamWordService;
	@Autowired
	private Validator			validator;


	public CommentService() {
		super();
	}

	public Comment create() {
		Assert.notNull(this.userService.findByPrincipal());

		final Comment res = new Comment();

		return res;
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(comment.getId() == 0);
		Assert.isTrue(comment.getCreator().equals(this.userService.findByPrincipal()));

		comment.setMoment(new Date(System.currentTimeMillis() - 1000));

		//		final Collection<SpamWord> sw = this.spamWordService.findAll();
		//		boolean spamw = false;
		//		for (final SpamWord word : sw) {
		//			spamw = comment.getBody().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
		//			if (spamw)
		//				break;
		//		}
		//		if (spamw)
		//			comment.setMarked(true);

		final Comment res = this.commentRepository.save(comment);

		if (res.getComment() != null)
			res.getComment().getReplies().add(res);

		return res;

	}

	public Comment findOne(final int commentId) {
		Assert.isTrue(commentId != 0);
		return this.commentRepository.findOne(commentId);
	}

	public Collection<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public void delete(final Comment comment) {
		Assert.notNull(comment);
		Assert.notNull(this.adminService.findByPrincipal());
		if (comment.getComment() != null)
			comment.getComment().getReplies().remove(comment);
		this.commentRepository.delete(comment);
	}
	//Other

	public Collection<Comment> findCommentsByUserId(final int userId) {
		return this.commentRepository.findCommentsByUserId(userId);
	}

	public Collection<Comment> findCommentsOrdered(final int announcementId) {
		return this.commentRepository.findCommentsOrdered(announcementId);
	}

	public Collection<Comment> findCommentsByParentId(final int commentId) {
		return this.commentRepository.findCommentsByParentId(commentId);
	}

	public Collection<Comment> findMarkedComments(final Pageable pageable) {
		return this.commentRepository.findMarkedComments(pageable).getContent();
	}

	public Comment reconstruct(final Comment comment, final Comment c, final Announcement a, final BindingResult binding) {
		Assert.notNull(comment);
		Assert.notNull(binding);
		Assert.notNull(this.userService.findByPrincipal());
		Assert.notNull(a);

		final Collection<Comment> replies = new ArrayList<Comment>();
		comment.setReplies(replies);

		final Date moment = new Date(System.currentTimeMillis() - 1000);
		comment.setMoment(moment);

		if (c != null)
			comment.setComment(c);

		comment.setAnnouncement(a);

		comment.setCreator(this.userService.findByPrincipal());
		comment.setMarked(false);
		this.validator.validate(comment, binding);
		return comment;
	}

}
