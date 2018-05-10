
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
	//	@Autowired
	//	private UserService			userService;
	//	@Autowired
	//	private AnnouncementService	rendezvousService;
	//	@Autowired
	//	private AdminService adminService;
	//	@Autowired
	//	private SpamWordService spamWordService;
	@Autowired
	private Validator			validator;


	public CommentService() {
		super();
	}

	public Comment create(final Comment c, final Announcement a) {
		//final User author = this.userService.findByPrincipal();
		//Assert.isTrue(author.getReservedRendezvous().contains(rendezvous));
		Assert.isTrue(!a.equals(null) || !c.equals(null));

		final Comment res = new Comment();

		if (!c.equals(null))
			res.setComment(c);

		return res;
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(comment.getId() == 0);
		//Assert.isTrue(comment.getAuthor().equals(this.userService.findByPrincipal()));

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
		//Assert.notNull(this.adminService.findByPrincipal());
		comment.getReplies().removeAll(comment.getReplies());
		this.commentRepository.delete(comment);
	}
	//Other

	public Collection<Comment> findCommentsByUserId(final int userId) {
		return this.commentRepository.findCommentsByUserId(userId);
	}

	public Collection<Comment> findCommentsOrdered(final int announcementId) {
		return this.commentRepository.findCommentsOrdered(announcementId);
	}

	public Comment reconstruct(final Comment comment, final BindingResult binding) {
		final Collection<Comment> replies = new ArrayList<Comment>();
		comment.setReplies(replies);
		final Date moment = new Date(System.currentTimeMillis() - 1000);

		comment.setMoment(moment);
		//comment.setCreator(this.userService.findByPrincipal());
		comment.setMarked(false);
		this.validator.validate(comment, binding);
		return comment;
	}

}
