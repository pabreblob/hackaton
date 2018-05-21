
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorShipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private SponsorService		sponsorService;


	/**
	 * Tests the creation of a sponsorship
	 * <p>
	 * This method tests the creation of sponsorships. Functional requirement:<br>
	 * 18.1. Create a sponsorship<br/>
	 * 
	 * Case 1: Create a sponsorship correctly. No exception is expected.<br>
	 * Case 2: Create a sponsorship cancelled. IllegalArgumentException is expected.<br>
	 * Case 3: Create a sponsorship accepted. IllegalArgumentException is expected. <br>
	 * 
	 */
	@Test
	public void driverCreateSponsorship() {
		final Object testingData[][] = {
			{
				"sponsor1", false, false, null
			}, {
				"sponsor1", false, true, IllegalArgumentException.class
			}, {
				"sponsor1", true, false, IllegalArgumentException.class

			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSponsorship((String) testingData[i][0], (boolean) testingData[i][1], (boolean) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * Template for testing the creation of a sponsorship.
	 * <p>
	 * This method defines the template used for the tests that check the creation of sponsorships.
	 * 
	 * @param sponsor
	 *            The sponsor who creates the sponsorship.
	 * @param accepted
	 *            It indicates that a sponsorship is accepted
	 * @param cancelled
	 *            It indicates that a sponsorship is cancelled
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreateSponsorship(final String sponsor, final boolean accepted, final boolean cancelled, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(sponsor);
			final Sponsorship sp = this.sponsorshipService.create();
			sp.setAccepted(accepted);
			sp.setCancelled(cancelled);
			this.sponsorshipService.save(sp);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of a sponsorship
	 * <p>
	 * This method tests the deletion of sponsorships. Functional requirement:<br>
	 * 18.2. Delete a sponsorship<br/>
	 * 
	 * Case 1: Delete a sponsorship correctly. No exception is expected.<br>
	 * Case 2: Delete a sponsorship by the administrator. No exception is expected.<br>
	 * 
	 */
	@Test
	public void driverDeleteSponsorship() {
		final Object testingData[][] = {
			{
				"sponsor1", "Sponsorship1", null
			}, {
				"admin", "Sponsorship2", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteSponsorship((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the deletion of a sponsorship.
	 * <p>
	 * This method defines the template used for the tests that check the deletion of sponsorships.
	 * 
	 * @param sponsor
	 *            The sponsor who creates the sponsorship.
	 * @param sponsorshipId
	 *            The id of the sponsorship that I want to delete
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateDeleteSponsorship(final String sponsor, final int sponsorshipId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(sponsor);
			final Sponsorship s = this.sponsorshipService.findOne(sponsorshipId);
			this.sponsorshipService.delete(s);
			Assert.isTrue(!this.sponsorshipService.findAll().contains(s));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the cancellation of a sponsorship
	 * <p>
	 * This method tests the cancellation of sponsorships. Functional requirement:<br>
	 * 18.2. Cancel a sponsorship that has been accepted by the admin<br/>
	 * Case 1: Cancel a sponsorship by another sponsor. IllegalArgumentException is expected.<br>
	 * Case 1: Cancel a sponsorship correctly. No exception is expected.<br>
	 * Case 2: Cancel a sponsorship not accepted. IllegalArgumentException is expected.<br>
	 * 
	 */
	@Test
	public void driverCancelSponsorship() {
		final Object testingData[][] = {
			{
				"sponsor2", "Sponsorship4", IllegalArgumentException.class
			}, {
				"sponsor1", "Sponsorship4", null
			}, {
				"sponsor1", "Sponsorship5", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCancelSponsorship((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the cancellation of a sponsorship.
	 * <p>
	 * This method defines the template used for the tests that check the cancellation of sponsorships.
	 * 
	 * @param sponsor
	 *            The sponsor who creates the sponsorship.
	 * @param sponsorshipId
	 *            The id of the sponsorship that I want to cancel
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCancelSponsorship(final String sponsor, final int sponsorshipId, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(sponsor);
			final Sponsorship s = this.sponsorshipService.findOne(sponsorshipId);
			this.sponsorshipService.cancel(s);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the listing of sponsorship not accepted yet.
	 * <p>
	 * This method tests the admin's list of sponsorship that not have been accepted yet.
	 * <p>
	 * 19.12. List all sponsorship that have not yet been accepted.
	 * 
	 * Case 1: Admin list the sponsorship not accepted yet. No exception is expected.
	 * 
	 */
	@Test
	public void driverListCreatedAnnouncements() {
		final Object testingData[][] = {
			{
				null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListSponsorshipNotAccepted((Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the listing of created announcements.
	 * <p>
	 * This method defines the template used for the tests that check the listing of created announcements.
	 * 
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateListSponsorshipNotAccepted(final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate("sponsor2");
			final Sponsorship sp = this.sponsorshipService.create();
			this.sponsorshipService.save(sp);
			final Sponsorship saved = this.sponsorshipService.save(sp);
			final Sponsorship found = this.sponsorshipService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			Assert.isTrue(this.sponsorService.findByPrincipal().equals(saved.getSponsor()));
			final Collection<Sponsorship> notAccepted = this.sponsorshipService.findSponsorshipNotAccepted(new PageRequest(0, 10));
			Assert.isTrue(notAccepted.contains(found));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
