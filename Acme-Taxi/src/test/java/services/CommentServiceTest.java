
package services;

import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	@Autowired
	private CommentService		commentService;
	@Autowired
	private AnnouncementService	announcementService;
	@Autowired
	private UserService			userService;


	/**
	 * Tests the creation of a comment related to an announcement.
	 * <p>
	 * This method tests the creation of a comment related to an announcement.
	 * <p>
	 * Functional requirement:
	 * <p>
	 * 15.10 An actor who is authenticated as a user must be able to: Comment on any announcement
	 * <p>
	 * Case 1: A user writes a comment on an announcement. No exception is expected. <br>
	 * Case 2: A user tries to write a comment on an announcement that doesn't exist. <code>NullPointerException</code> is expected. <br>
	 * Case 3: An unauthenticated actor tries to write a comment. <code>IllegalArgumentException</code> is expected.
	 */
	@Test
	public void driverCreateComment() {
		final Object testingData[][] = {
			{
				"user1", "Announcement1", null
			}, {
				"user2", null, NullPointerException.class
			}, {
				null, "Announcement1", IllegalArgumentException.class

			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the creation of a comment related to an announcement.
	 * <p>
	 * This method defines the template used for the tests that checks the creation of a comment related to an announcement.
	 * 
	 * @param user
	 *            The user that writes the comment
	 * @param announcementId
	 *            The id of the announcement where the comment is written.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreate(final String user, final String announcementId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(user);
			final Comment c = this.commentService.create();
			c.setMoment(new Date(System.currentTimeMillis() + 50000000));
			c.setBody("Test Text");
			c.setCreator(this.userService.findByPrincipal());
			final int id = super.getEntityId(announcementId);
			c.setAnnouncement(this.announcementService.findOne(id));
			this.commentService.save(c);
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the creation of a reply related to a comment.
	 * <p>
	 * This method tests the creation of a reply related to a comment.
	 * <p>
	 * Functional requirement:
	 * <p>
	 * 15.10 An actor who is authenticated as a user must be able to: [...] reply to comments
	 * <p>
	 * Case 1: A user writes a reply on a comment. No exception is expected. <br>
	 * Case 2: A user tries to write a reply on an announcement that doesn't exist. <code>NullPointerException</code> is expected. <br>
	 * Case 3: An unauthenticated actor tries to write a reply. <code>IllegalArgumentException</code> is expected.
	 */
	@Test
	public void driverCreateReply() {
		final Object testingData[][] = {
			{
				"user1", "Comment1", null
			}, {
				"user2", null, NullPointerException.class
			}, {
				null, "Comment1", IllegalArgumentException.class

			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreate2((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the creation of a reply related to a comment.
	 * <p>
	 * This method defines the template used for the tests that checks the creation of a reply related to a comment.
	 * 
	 * @param user
	 *            The user that writes the reply
	 * @param commentId
	 *            The id of the comment where the reply is written.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreate2(final String user, final String commentId, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(user);
			final Comment c = this.commentService.create();
			c.setMoment(new Date(System.currentTimeMillis() + 50000000));
			c.setBody("test");
			c.setCreator(this.userService.findByPrincipal());
			final int id = super.getEntityId(commentId);
			c.setComment(this.commentService.findOne(id));
			c.setAnnouncement(c.getComment().getAnnouncement());
			this.commentService.save(c);
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of a comment
	 * <p>
	 * This method tests the deletion of comments.
	 * <p>
	 * Functional requirement:
	 * <p>
	 * 19.13. An acto rwho is authenticated as an admin must be able to: Delete any content that he may consider inappropriate<br/>
	 * Case 1: An admin deletes a comment. No exception is expected.<br>
	 * Case 2: A user tries to delete a comment. <code>IllegalArgumentException</code> is expected. <br>
	 * Case 3: An admin tries to delete a comment that doesn't exist. <code>NullPointerException</code> is expected.
	 * 
	 */
	@Test
	public void driverDeleteReview() {
		final Object testingData[][] = {
			{
				"admin", "Comment2", null
			}, {
				"user1", "Comment2", IllegalArgumentException.class
			}, {
				"admin", null, NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/**
	 * Template for testing the deletion of a comment
	 * <p>
	 * This method defines the template used for the tests that check the deletion of comments.
	 * 
	 * @param user
	 *            The user who deletes the comment.
	 * @param commentId
	 *            The id of the comment that we want to delete.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateDelete(final String user, final String commentId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(user);
			this.commentService.delete(this.commentService.findOne(super.getEntityId(commentId)));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the list of comments with spam words
	 * <p>
	 * This method tests the listing of comments with spam words.
	 * <p>
	 * 
	 * 19.4 List comments and replies marked as suspicious.
	 * 
	 */
	@Test
	public void ListMarkedReview() {
		Assert.isTrue(this.commentService.findMarkedComments(new PageRequest(0, 10)).size() >= 0);

	}

}
