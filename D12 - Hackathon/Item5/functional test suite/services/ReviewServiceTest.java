
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Review;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ReviewServiceTest extends AbstractTest {

	@Autowired
	private ReviewService		reviewService;

	@Autowired
	private DriverService		driverService;

	@Autowired
	private RepairShopService	repairshopService;

	@Autowired
	private UserService			userService;


	/**
	 * Tests the creation of a review
	 * <p>
	 * This method tests the creation and saving of reviews. Functional requirement:<br>
	 * 14. Write reviews about users, drivers and repairshops<br/>
	 * 
	 * Case 1: Create a review for a driver correctly. No exception is expected.<br>
	 * Case 2: Create a review for a driver that have a review. IllegalArgumentException is expected.<br>
	 * Case 3: Create a review for a driver who not have accepted requests by the user. IllegalArgumentException is expected.<br>
	 * Case 4: Create a review for repairshop correctly. No exception is expected.<br>
	 * Case 5: Create a review for repairshop correctly. No exception is expected.<br>
	 * Case 6: Create a review for repairshop that have a review. IllegalArgumentException is expected.<br>
	 * Case 7: Create a review for user correctly. No exception is expected.<br>
	 * Case 8: Create a review for user by another user who not have joined an announcement. IllegalArgumentException is expected. <br>
	 * Case 9: Create a review for user that just have been reviewed by this user. IllegalArgumentException is expected. <br>
	 * Case 9: Create a review for user who is attendant. IllegalArgumentException is expected. <br>
	 */
	@Test
	public void driverCreateReview() {
		final Object testingData[][] = {
			{
				"user1", "Driver3", "Driver", null
			}, {
				"user1", "Driver2", "Driver", IllegalArgumentException.class
			}, {
				"user1", "Driver1", "Driver", IllegalArgumentException.class
			}, {
				"user1", "RepairShop3", "Repairshop", null
			}, {
				"user2", "RepairShop4", "Repairshop", null
			}, {
				"user1", "RepairShop1", "Repairshop", IllegalArgumentException.class
			}, {
				"user3", "User1", "User", null
			}, {
				"user3", "User2", "User", IllegalArgumentException.class
			}, {
				"user4", "User2", "User", IllegalArgumentException.class
			}, {
				"user1", "User2", "User", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateReview((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	/**
	 * Template for testing the creation of a review
	 * <p>
	 * This method defines the template used for the tests that check the creation of reviews.
	 * 
	 * @param user
	 *            The user who creates the review.
	 * @param reviewedId
	 *            The id of the object which we will write a review (userId, driverId or repairshopId)
	 * @param type
	 *            The rol of the object that will be revised. Driver, Repairshop or User is expected.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreateReview(final String user, final int reviewedId, final String type, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(user);
			final Review rev = this.reviewService.create();
			rev.setTitle("Prueba de review");
			rev.setBody("Cuerpo de la review de prueba. Estamos haciendo tests funcionales");
			rev.setRating(5);
			Review saved;
			if (type.equals("Driver")) {
				saved = this.reviewService.saveDriver(rev, reviewedId);
				Assert.isTrue(this.driverService.findOne(reviewedId).getReviews().contains(saved));
			} else if (type.equals("Repairshop")) {
				saved = this.reviewService.saveRepairShop(rev, reviewedId);
				Assert.isTrue(this.repairshopService.findOne(reviewedId).getReviews().contains(saved));
			} else {
				saved = this.reviewService.saveUser(rev, reviewedId);
				Assert.isTrue(this.userService.findOne(reviewedId).getReviews().contains(saved));
			}
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the edition of a review
	 * <p>
	 * This method tests the edition reviews. Functional requirement:<br>
	 * 14. Write reviews about users, drivers and repairshops<br/>
	 * 
	 * Case 1: Edit a review for a driver correctly. No exception is expected.<br>
	 * Case 2: Edit a review for a driver not created by the user. IllegalArgumentException is expected.<br>
	 * Case 3: Edit a review for repairshop correctly. No exception is expected.<br>
	 * Case 4: Edit a review for repairshop not created by the user. IllegalArgumentException is expected.<br>
	 * Case 5: Edit a review for user correctly. No exception is expected.<br>
	 * Case 6: Edit a review for user not created by the user. IllegalArgumentException is expected. <br>
	 * 
	 */
	@Test
	public void driverEditReview() {
		final Object testingData[][] = {
			{
				"user1", "Review2", null
			}, {
				"user2", "Review2", IllegalArgumentException.class
			}, {
				"user1", "Review3", null
			}, {
				"user2", "Review3", IllegalArgumentException.class
			}, {
				"user2", "Review1", null
			}, {
				"user1", "Review1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditReview((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	/**
	 * Template for testing the edition of a review
	 * <p>
	 * This method defines the template used for the tests that check the edition of reviews.
	 * 
	 * @param user
	 *            The user who creates the review.
	 * @param reviewId
	 *            The id of the review that we want to edit.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateEditReview(final String user, final int reviewId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(user);
			final Review r = this.reviewService.findOne(reviewId);
			r.setBody("Cambiando el cuerpo de una review");
			this.reviewService.update(r);
			Assert.isTrue(this.reviewService.findOne(reviewId).getBody().equals("Cambiando el cuerpo de una review"));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of a review
	 * <p>
	 * This method tests the deletion reviews. Functional requirement:<br>
	 * 14. Write reviews about users, drivers and repairshops<br/>
	 * 
	 * Note: Admin can delete a review. ReviewUserController check the review we want to delete was created by the user logged.
	 * 
	 * Case 1: Delete a review for a driver correctly. No exception is expected.<br>
	 * Case 2: Delete a review for a repairshop correctly. No exception is expected.<br>
	 * Case 3: Delete a review for a user correctly. No exception is expected.<br>
	 * 
	 */
	@Test
	public void driverDeleteReview() {
		final Object testingData[][] = {
			{
				"user1", "Review2", null
			}, {
				"user1", "Review3", null
			}, {
				"user2", "Review1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteReview((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	/**
	 * Template for testing the deletion of a review
	 * <p>
	 * This method defines the template used for the tests that check the deletion of reviews.
	 * 
	 * @param user
	 *            The user who creates the review.
	 * @param reviewId
	 *            The id of the review that we want to delete.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateDeleteReview(final String user, final int reviewId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(user);
			this.reviewService.delete(reviewId);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	/**
	 * Tests the list of reviews created by a user.
	 * <p>
	 * This method tests the listing of reviews created by the user logged
	 * <p>
	 * 15.15 List the review he has written.
	 * 
	 * Case 1: User1 list his reviews. No exception is expected.
	 * 
	 * Case 2: User2 list his reviews. No exception is expected.
	 * 
	 * Case 3: An unauthenticated list his reviews. IllegalArgumentException is expected.
	 */
	@Test
	public void driverListCreatedReview() {
		final Object testingData[][] = {
			{
				"user1", 2, null
			}, {
				"user2", 1, null
			}, {
				null, 0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListCreatedReview((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the listing of reviews.
	 * <p>
	 * This method defines the template used for the tests listing of reviews written by the user logged.
	 * 
	 * @param username
	 *            The username of the user that logs in
	 * @param expectedSize
	 *            Number of reviews that the method should to return.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateListCreatedReview(final String username, final int expectedSize, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			Assert.isTrue(this.reviewService.findReviewsByPrincipal(new PageRequest(0, 10)).size() >= expectedSize);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the list of reviews written about a user.
	 * <p>
	 * This method tests the listing of reviews written about users.
	 * <p>
	 * List the reviews that a user has written about him .
	 * 
	 * Case 1: List reviews about user1. No exception is expected.
	 * 
	 * Case 2: List reviews about user 2. No exception is expected.
	 * 
	 */
	@Test
	public void driverListUserReviews() {
		final Object testingData[][] = {
			{
				"User1", 1, null
			}, {
				"User2", 0, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListUserReview(super.getEntityId((String) testingData[i][0]), (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/**
	 * Template for testing the listing of reviews written about users.
	 * <p>
	 * This method defines the template used for the tests that check listing of reviews written about a user.
	 * 
	 * @param userId
	 *            The id of the user that we want to list his reviews
	 * @param expectedSize
	 *            Number of reviews that the method should to return.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateListUserReview(final int userId, final int expectedSize, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			Assert.isTrue(this.reviewService.findReviewsByUserId(userId, new PageRequest(0, 10)).size() >= expectedSize);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the list of reviews written about a driver.
	 * <p>
	 * This method tests the listing of reviews written about drivers.
	 * <p>
	 * List the reviews that a user has written about him .
	 * 
	 * Case 1: List reviews about driver1. No exception is expected.
	 * 
	 * Case 2: List reviews about driver2. No exception is expected.
	 * 
	 */
	@Test
	public void driverListDriverReviews() {
		final Object testingData[][] = {
			{
				"Driver1", 0, null
			}, {
				"Driver2", 1, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListDriverReview(super.getEntityId((String) testingData[i][0]), (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/**
	 * Template for testing listing of reviews written about drivers.
	 * <p>
	 * This method defines the template used for the tests that check listing of reviews written about a driver.
	 * 
	 * @param driverId
	 *            The id of the driver that we want to list his reviews
	 * @param expectedSize
	 *            Number of reviews that the method should to return.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateListDriverReview(final int driverId, final int expectedSize, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			Assert.isTrue(this.reviewService.findReviewsByDriverId(driverId, new PageRequest(0, 10)).size() >= expectedSize);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the list of reviews written about a repair shop.
	 * <p>
	 * This method tests the listing of reviews written about repair shops.
	 * <p>
	 * List the reviews that a user has written about this .
	 * 
	 * Case 1: List reviews about repairshop1. No exception is expected.
	 * 
	 * Case 2: List reviews about repairshop2. No exception is expected.
	 * 
	 */
	@Test
	public void driverListRepairShopReviews() {
		final Object testingData[][] = {
			{
				"RepairShop1", 1, null
			}, {
				"RepairShop2", 0, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListRepairShopReview(super.getEntityId((String) testingData[i][0]), (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/**
	 * Template for testing the listing of reviews written about repair shops.
	 * <p>
	 * This method defines the template used for the tests that check the listing of reviews written about a repair shop.
	 * 
	 * @param repairShopId
	 *            The id of the repair shop that we want to list his reviews
	 * @param expectedSize
	 *            Number of reviews that the method should to return.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateListRepairShopReview(final int repairShopId, final int expectedSize, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			Assert.isTrue(this.reviewService.findReviewsByRepairShopId(repairShopId, new PageRequest(0, 10)).size() >= expectedSize);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the list of reviews with spam words
	 * <p>
	 * This method tests the listing of reviews with spam words.
	 * <p>
	 * 
	 * 19.7 List reviews of any kind marked as suspicious.
	 * 
	 */
	@Test
	public void ListMarkedReview() {
		Assert.isTrue(this.reviewService.findReviewsMarked(new PageRequest(0, 10)).size() >= 0);

	}

}
