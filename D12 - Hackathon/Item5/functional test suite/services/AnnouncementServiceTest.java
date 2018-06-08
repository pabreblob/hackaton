
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Announcement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnnouncementServiceTest extends AbstractTest {

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private UserService			userService;


	/**
	 * Tests the creation of announcements.
	 * <p>
	 * This method is used to test the creation of empty announcements before passing them to the corresponding views.
	 * <p>
	 * 15.7. An actor who is authenticated as a user must be able to: Create a new announcement.
	 * 
	 */
	@Test
	public void testCreateAnnouncement() {
		super.authenticate("user1");
		final Announcement res = this.announcementService.create();
		Assert.notNull(res);
		super.authenticate(null);
	}

	/**
	 * Tests the saving of announcements.
	 * <p>
	 * This method tests the creation and later saving of announcements as it would be done by a user in the corresponding views.
	 * <p>
	 * 15.7. An actor who is authenticated as a user must be able to: Create a new announcement.
	 * 
	 * Case 1: User1 creates an announcement. No exception is expected.
	 * 
	 * Case 2: Driver1 tries to create an announcement. An IllegalArgumentException is expected.
	 * 
	 * Case 3: An unauthenticated user tries to create an announcement. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverSaveAnnouncement() {
		final Object testingData[][] = {
			{
				"user1", null
			}, {
				"driver1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSaveAnnouncement((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the saving of announcements.
	 * <p>
	 * This method defines the template used for the tests that check the saving of announcements.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateSaveAnnouncement(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the edition of announcements.
	 * <p>
	 * This method tests the creation and later edition of announcements as it would be done by a user in the corresponding views.
	 * <p>
	 * 15.7. An actor who is authenticated as a user must be able to: Create a new announcement. He may edit or delete the announcement as long as no other user has joined it.
	 * 
	 * Case 1: User1 edits an announcement. No exception is expected.
	 * 
	 * Case 2: User1 edits an announcement with an attendant. An IllegalArgumentException is expected.
	 * 
	 * Case 3: An unauthenticated user tries to edit an announcement. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverEditAnnouncement() {
		final Object testingData[][] = {
			{
				"user1", null, false
			}, {
				"user1", IllegalArgumentException.class, true
			}, {
				null, IllegalArgumentException.class, false
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditAnnouncement((String) testingData[i][0], (Class<?>) testingData[i][1], (boolean) testingData[i][2]);
	}

	/**
	 * Template for testing the edition of announcements.
	 * <p>
	 * This method defines the template used for the tests that check the edition of announcements.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 * @param attendants
	 *            Specify if other users will join the announcement.
	 */
	public void templateEditAnnouncement(final String username, final Class<?> expected, final boolean attendants) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			if (attendants) {
				super.authenticate(null);
				super.authenticate("user2");
				this.announcementService.join(saved.getId());
				final Announcement found2 = this.announcementService.findOne(saved.getId());
				Assert.isTrue(found2.getAttendants().contains(this.userService.findByPrincipal()));
				super.authenticate(null);
				super.authenticate("user1");
			}
			final Announcement found3 = this.announcementService.findOne(saved.getId());
			found3.setTitle("New title");
			this.announcementService.save(found3);
			final Announcement found4 = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found4.getTitle().equals("New title"));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of announcements.
	 * <p>
	 * This method tests the creation and later deletion of announcements as it would be done by a user in the corresponding views.
	 * <p>
	 * 15.7. An actor who is authenticated as a user must be able to: Create a new announcement. He may edit or delete the announcement as long as no other user has joined it.
	 * 
	 * Case 1: User1 deletes an announcement. No exception is expected.
	 * 
	 * Case 2: User1 deletes an announcement with an attendant. An IllegalArgumentException is expected.
	 * 
	 * Case 3: An unauthenticated user tries to delete an announcement. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverDeleteAnnouncement() {
		final Object testingData[][] = {
			{
				"user1", null, false
			}, {
				"user1", IllegalArgumentException.class, true
			}, {
				null, IllegalArgumentException.class, false
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteAnnouncement((String) testingData[i][0], (Class<?>) testingData[i][1], (boolean) testingData[i][2]);
	}

	/**
	 * Template for testing the deletion of announcements.
	 * <p>
	 * This method defines the template used for the tests that check the deletion of announcements.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 * @param attendants
	 *            Specify if other users will join the announcement.
	 */
	public void templateDeleteAnnouncement(final String username, final Class<?> expected, final boolean attendants) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			if (attendants) {
				super.authenticate(null);
				super.authenticate("user2");
				this.announcementService.join(saved.getId());
				final Announcement found2 = this.announcementService.findOne(saved.getId());
				Assert.isTrue(found2.getAttendants().contains(this.userService.findByPrincipal()));
				super.authenticate(null);
				super.authenticate("user1");
			}
			this.announcementService.delete(saved);
			final Announcement found3 = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found3 == null);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of announcements by the admin.
	 * <p>
	 * This method tests the creation and later deletion of announcements as it would be done by the admin in the corresponding views.
	 * <p>
	 * 19.13. An actor who is authenticated as an admin must be able to: Delete any content that he may consider inappropriate, excluding private messages.
	 * 
	 * Case 1: User1 creates an announcement and the admin deletes it. No exception is expected.
	 * 
	 * Case 2: User2 creates an announcement and the admin deletes it. No exception is expected.
	 */
	@Test
	public void driverDeleteAdminAnnouncement() {
		final Object testingData[][] = {
			{
				"user1", null
			}, {
				"user2", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteAdminAnnouncement((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the deletion of announcements by the admin.
	 * <p>
	 * This method defines the template used for the tests that check the deletion of announcements by the admin.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateDeleteAdminAnnouncement(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			super.authenticate(null);
			super.authenticate("admin");
			this.announcementService.deleteAdmin(saved.getId());
			final Announcement found3 = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found3 == null);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the joining of announcements.
	 * <p>
	 * This method tests the creation and later joining of announcements as it would be done by the users in the corresponding views.
	 * <p>
	 * 15.8. An actor who is authenticated as a user must be able to: Join any announcement as long as there's still more than 2 hours left until the car departs and there are any seats left.
	 * 
	 * Case 1: User1 creates an announcement and user2 joins it. No exception is expected.
	 * 
	 * Case 2: User1 creates an announcement and tries to join it. An IllegalArgumentException is expected.
	 * 
	 * Case 3: User1 creates an announcement and user2 tries to join it with less than two hours left. An IllegalArgumentException is expected.
	 * 
	 * Case 4: An unauthenticated user tries to join an announcement. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverJoinAnnouncement() {
		final Object testingData[][] = {
			{
				"user1", "user2", false, null
			}, {
				"user1", "user1", false, IllegalArgumentException.class
			}, {
				"user1", "user2", true, IllegalArgumentException.class
			}, {
				"user1", null, false, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateJoinAnnouncement((String) testingData[i][0], (String) testingData[i][1], (boolean) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * Template for testing the joining of announcements.
	 * <p>
	 * This method defines the template used for the tests that check the joining of announcements.
	 * 
	 * @param username
	 *            The username of the user that logs in and creates the announcement.
	 * @param username2
	 *            The username of the user that logs in and joins the announcement.
	 * @param twohours
	 *            Specify if the user joins an announcement with less than two hours left.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateJoinAnnouncement(final String username, final String username2, final boolean twohours, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			final Date moment = twohours ? new Date(System.currentTimeMillis() + 999) : new Date(System.currentTimeMillis() + 9999999);
			res.setMoment(moment);
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			super.authenticate(null);
			super.authenticate(username2);
			this.announcementService.join(saved.getId());
			final Announcement found2 = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found2.getAttendants().contains(this.userService.findByPrincipal()));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the dropping out from announcements.
	 * <p>
	 * This method tests the creation and later dropping out from announcements as it would be done by the users in the corresponding views.
	 * <p>
	 * 15.8. An actor who is authenticated as a user must be able to: ... Users can also drop out from the announcement.
	 * 
	 * Case 1: User1 creates an announcement and user2 joins and drops out from it. No exception is expected.
	 * 
	 * Case 2: User1 creates an announcement and tries to join and drop out from it. An IllegalArgumentException is expected.
	 * 
	 * Case 3: An unauthenticated user tries to join and drop out from an announcement. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverDropOutAnnouncement() {
		final Object testingData[][] = {
			{
				"user1", "user2", null
			}, {
				"user1", "user1", IllegalArgumentException.class
			}, {
				"user1", null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDropOutAnnouncement((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the dropping out from announcements.
	 * <p>
	 * This method defines the template used for the tests that check the dropping out from announcements.
	 * 
	 * @param username
	 *            The username of the user that logs in and creates the announcement.
	 * @param username2
	 *            The username of the user that logs in, joins and drops out from the announcement.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateDropOutAnnouncement(final String username, final String username2, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			super.authenticate(null);
			super.authenticate(username2);
			this.announcementService.join(saved.getId());
			final Announcement found2 = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found2.getAttendants().contains(this.userService.findByPrincipal()));
			this.announcementService.dropOut(saved.getId());
			final Announcement found3 = this.announcementService.findOne(saved.getId());
			Assert.isTrue(!found3.getAttendants().contains(this.userService.findByPrincipal()));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the cancellation of announcements.
	 * <p>
	 * This method tests the creation and later cancellation of announcements as it would be done by the users in the corresponding views.
	 * <p>
	 * 15.7. An actor who is authenticated as a user must be able to: Create a new announcement. ... Once another user has joined it, it can be cancelled if the automobile has not departed.
	 * 
	 * Case 1: User1 cancels an announcement. No exception is expected.
	 * 
	 * Case 2: User1 cancels an announcement with an attendant. No exception is expected.
	 * 
	 * Case 3: Driver1 tries to cancel an announcement. An IllegalArgumentException is expected.
	 * 
	 * Case 4: An unauthenticated user tries to cancel an announcement. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverCancelAnnouncement() {
		final Object testingData[][] = {
			{
				"user1", null, false
			}, {
				"user1", null, true
			}, {
				"driver1", IllegalArgumentException.class, false
			}, {
				null, IllegalArgumentException.class, false
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCancelAnnouncement((String) testingData[i][0], (Class<?>) testingData[i][1], (boolean) testingData[i][2]);
	}

	/**
	 * Template for testing the cancellation of announcements.
	 * <p>
	 * This method defines the template used for the tests that check the cancellation of announcements.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 * @param attendants
	 *            Specify if other users will join the announcement.
	 */
	public void templateCancelAnnouncement(final String username, final Class<?> expected, final boolean attendants) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			if (attendants) {
				super.authenticate(null);
				super.authenticate("user2");
				this.announcementService.join(saved.getId());
				final Announcement found2 = this.announcementService.findOne(saved.getId());
				Assert.isTrue(found2.getAttendants().contains(this.userService.findByPrincipal()));
				super.authenticate(null);
				super.authenticate("user1");
			}
			this.announcementService.cancel(saved.getId());
			final Announcement found3 = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found3.isCancelled());
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of created announcements.
	 * <p>
	 * This method tests the creation and later listing of created announcements as it would be done by a user in the corresponding views.
	 * <p>
	 * 15.8. An actor who is authenticated as a user must be able to: List the announcements he has created and the ones he has joined.
	 * 
	 * Case 1: User1 lists their created announcements. No exception is expected.
	 * 
	 * Case 2: User2 lists their created announcements. No exception is expected.
	 * 
	 * Case 3: Driver1 tries to list their created announcements. An IllegalArgumentException is expected.
	 * 
	 * Case 4: An unauthenticated user tries to list their created announcements. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverListCreatedAnnouncements() {
		final Object testingData[][] = {
			{
				"user1", null
			}, {
				"user2", null
			}, {
				"driver1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListCreatedAnnouncements((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the listing of created announcements.
	 * <p>
	 * This method defines the template used for the tests that check the listing of created announcements.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateListCreatedAnnouncements(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			final Collection<Announcement> created = this.announcementService.getCreatedAnnouncementsByUserId(new PageRequest(0, 10));
			Assert.isTrue(created.contains(found));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of joined announcements.
	 * <p>
	 * This method tests the creation and later listing of joined announcements as it would be done by a user in the corresponding views.
	 * <p>
	 * 15.8. An actor who is authenticated as a user must be able to: List the announcements he has created and the ones he has joined.
	 * 
	 * Case 1: User1 lists their joined announcements. No exception is expected.
	 * 
	 * Case 2: User2 lists their joined announcements. No exception is expected.
	 * 
	 * Case 3: Driver1 tries to list their joined announcements. An IllegalArgumentException is expected.
	 * 
	 * Case 4: An unauthenticated user tries to list their joined announcements. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverListJoinedAnnouncements() {
		final Object testingData[][] = {
			{
				"user1", "user2", null
			}, {
				"user2", "user3", null
			}, {
				"user1", "driver1", IllegalArgumentException.class
			}, {
				"user1", null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListJoinedAnnouncements((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the listing of joined announcements.
	 * <p>
	 * This method defines the template used for the tests that check the listing of joined announcements.
	 * 
	 * @param username
	 *            The username of the user that logs in and creates the announcement.
	 * @param username2
	 *            The username of the user that logs in and joins the announcement.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateListJoinedAnnouncements(final String username, final String username2, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			super.authenticate(null);
			super.authenticate(username2);
			this.announcementService.join(saved.getId());
			final Collection<Announcement> joined = this.announcementService.getJoinedAnnouncementsByUserId(new PageRequest(0, 10));
			Assert.isTrue(joined.contains(found));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of available announcements.
	 * <p>
	 * This method tests the creation and later listing of available announcements as it would be done by a user in the corresponding views.
	 * <p>
	 * 15.8. An actor who is authenticated as a user must be able to: Join any announcement as long as there's still more than 2 hours left until the car departs and there are any seats left.
	 * 
	 * Case 1: User1 creates an announcement and user2 lists available announcements. No exception is expected.
	 * 
	 * Case 2: User2 creates an announcement and user1 lists available announcements. No exception is expected.
	 * 
	 * Case 3: Driver1 tries to list available announcements. An IllegalArgumentException is expected.
	 * 
	 * Case 4: An unauthenticated user tries to list available announcements. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverListAvailableAnnouncements() {
		final Object testingData[][] = {
			{
				"user1", "user2", null
			}, {
				"user2", "user1", null
			}, {
				"user1", "driver1", IllegalArgumentException.class
			}, {
				"user1", null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListAvailableAnnouncements((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the listing of available announcements.
	 * <p>
	 * This method defines the template used for the tests that check the listing of available announcements.
	 * 
	 * @param username
	 *            The username of the user that logs in and creates an announcement.
	 * @param username2
	 *            The username of the user that logs in and lists available announcements.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateListAvailableAnnouncements(final String username, final String username2, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			super.authenticate(null);
			super.authenticate(username2);
			final Collection<Announcement> available = this.announcementService.getAvailableAnnouncements(new PageRequest(0, 10));
			Assert.isTrue(available.contains(found));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of created or joined announcements.
	 * <p>
	 * This method tests the creation and later listing of created or joined announcements as it would be done by an actor in the corresponding views.
	 * <p>
	 * 13.4. An actor who is not authenticated must be able to: Access to the profile of the user that has published an announcement. In the user's profile, the last 10 announcements he has joined or created must be listed.
	 * 
	 * Case 1: User1 lists their created or joined announcements. No exception is expected.
	 * 
	 * Case 2: An unauthenticated user tries to list the created or joined announcements of user1. No exception is expected.
	 */
	@Test
	public void driverListCreatedOrJoinedAnnouncements() {
		final Object testingData[][] = {
			{
				"user1", null
			}, {
				"user2", null
			}, {
				null, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListCreatedOrJoinedAnnouncements((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the listing of created or joined announcements.
	 * <p>
	 * This method defines the template used for the tests that check the listing of created or joined announcements.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateListCreatedOrJoinedAnnouncements(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate("user1");
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			final int userId = this.userService.findByPrincipal().getId();
			super.authenticate(null);
			super.authenticate(username);
			final Collection<Announcement> createdOrJoined = this.announcementService.getLastCreatedOrJoinedAnnouncements(userId);
			Assert.isTrue(createdOrJoined.size() <= 10);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of announcements using the finder.
	 * <p>
	 * This method tests the creation and later listing announcements using the finder as it would be done by an user in the corresponding views.
	 * <p>
	 * 10. Users have an announcement finder where they can specify some search criteria.
	 * 
	 * Case 1: User1 lists available announcements using the finder. No exception is expected.
	 * 
	 * Case 2: User2 lists available announcements using the finder. No exception is expected.
	 * 
	 * Case 3: Driver1 tries to list available announcements using the finder. An IllegalArgumentException is expected.
	 * 
	 * Case 4: An unauthenticated user tries to list available announcements using the finder. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverFindAnnouncements() {
		final Object testingData[][] = {
			{
				"user1", null
			}, {
				"user2", null
			}, {
				"driver1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindAnnouncements((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the listing of announcements using the finder.
	 * <p>
	 * This method defines the template used for the tests that check the listing of announcements using the finder.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateFindAnnouncements(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate("user1");
			final Announcement res = this.announcementService.create();
			res.setTitle("Title");
			res.setDescription("Hello");
			res.setOrigin("Sevilla");
			res.setDestination("Madrid");
			res.setPricePerPerson(5);
			res.setMoment(new Date(System.currentTimeMillis() + 9999999));
			res.setSeats(4);
			final Announcement saved = this.announcementService.save(res);
			final Announcement found = this.announcementService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.userService.findByPrincipal().equals(saved.getCreator()));
			Assert.isTrue(saved.getAttendants().isEmpty());
			super.authenticate(null);
			super.authenticate(username);
			final Collection<Announcement> finder = this.announcementService.findAnnouncementsFinder("Hello", 5., 20., null, null, null);
			if (username.equals("user1"))
				Assert.isTrue(!finder.contains(found));
			else
				Assert.isTrue(finder.contains(found));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
