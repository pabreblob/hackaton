
package services;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ReportServiceTest extends AbstractTest {

	@Autowired
	private ReportService	reportService;
	@Autowired
	private ActorService	actorService;


	/**
	 * Tests the creation and saving of a report to the system.
	 * <p>
	 * Functional Requirement 11: Authenticated actors can file a report regarding other actors except the administrator. A report must contain the actor that is being reported, the creator of the report, a reason, the moment it was filed and an optional
	 * image. In order to prevent spamming, an actor can only file a maximum number of reports per week.
	 * <p>
	 * Case 1: An report is created by user1 and it's reporting user2. No exception is expected.<br>
	 * Case 2: An report is created by user1 and it's reporting admin. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: An report is created by user1 and it's reporting user1. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				"user1", "user2", null
			}, {
				"user1", "admin", IllegalArgumentException.class
			}, {
				"user1", "user1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the creation and saving of report.
	 * <p>
	 * This method defines the template used for the tests that check the creation and saving of new reports in the system.
	 * 
	 * @param creator
	 *            The user who creates the report
	 * @param reported
	 *            The user who is reported
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	private void templateCreateAndSave(final String creator, final String reported, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final Actor creatorActor = new ArrayList<>(this.actorService.findByUsername(creator)).get(0);
			final Actor reportedActor = new ArrayList<>(this.actorService.findByUsername(reported)).get(0);
			final Report r = this.reportService.create();
			r.setChecked(false);
			r.setCreator(creatorActor);
			r.setImageUrl("https://www.google.es/");
			r.setMoment(new Date());
			r.setReason("reason");
			r.setReported(reportedActor);
			final Report saved = this.reportService.save(r);
			Assert.notNull(saved);
			Assert.isTrue(!saved.isChecked());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the creation, the saving and the checking of a report to the system.
	 * <p>
	 * Functional Requirement 11: Authenticated actors can file a report regarding other actors except the administrator. A report must contain the actor that is being reported, the creator of the report, a reason, the moment it was filed and an optional
	 * image. In order to prevent spamming, an actor can only file a maximum number of reports per week.
	 * <p>
	 * Case 1: An report is created by user1 and it's reporting user2. No exception is expected.<br>
	 * Case 2: An report is created by user1 and it's reporting admin. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverCreateSaveAndCheck() {
		final Object testingData[][] = {
			{
				"user1", "user2", null
			}, {
				"user1", "admin", IllegalArgumentException.class
			}, {
				"user1", "user1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveAndCheck((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the creation, the saving and the checking.
	 * <p>
	 * This method defines the template used for the tests that check the creation, the saving and the checking of new Report in the system.
	 * 
	 * @param creator
	 *            The username of the actor who is creating the report
	 * @param reported
	 *            The username of the actor who is reported
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected
	 */
	private void templateCreateSaveAndCheck(final String creator, final String reported, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final Actor creatorActor = new ArrayList<>(this.actorService.findByUsername(creator)).get(0);
			final Actor reportedActor = new ArrayList<>(this.actorService.findByUsername(reported)).get(0);
			final Report r = this.reportService.create();
			r.setChecked(false);
			r.setCreator(creatorActor);
			r.setImageUrl("https://www.google.es/");
			r.setMoment(new Date());
			r.setReason("reason");
			r.setReported(reportedActor);
			final Report saved = this.reportService.save(r);
			Assert.notNull(saved);
			this.reportService.check(saved);
			final Report checked = this.reportService.findOne(saved.getId());
			Assert.isTrue(checked.isChecked());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();
		this.checkExceptions(expected, caught);
	}
}
