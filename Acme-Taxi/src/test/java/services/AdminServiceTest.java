
package services;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Admin;
import domain.Driver;
import domain.Mechanic;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminServiceTest extends AbstractTest {

	@Autowired
	private AdminService	adminService;


	/**
	 * Tests the methods related to the dashboard information that the admin must have access to.
	 * <p>
	 * This method tests all the different methods that are used in the dashboard.
	 * <p>
	 * 19.14. An actor who is authenticated as an admin must be able to: Display a dashboard with the following information: ...
	 * 
	 * Case 1: An actor logged in as an admin tries to display the dashboard information. No exceptions is expected.
	 * 
	 * Case 2: An actor who is not an admin tries to display the dashboard information. An IllegalArgumentException is expected.
	 * 
	 * Case 3: A person who is not logged in tries to display the dashboard. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverDashboard() {
		final Object testingData[][] = {
			{
				"admin", null
			}, {
				"user1", IllegalAccessException.class
			}, {
				null, IllegalAccessException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDashboard((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the methods related to the dashboard.
	 * <p>
	 * This method defines the template used for the tests that check the methods used in the dashboard.
	 * 
	 * @param username
	 *            The username of the actor that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateDashboard(final String username, final Class<?> expected) {
		Class<?> caught = null;
		this.authenticate(username);
		try {
			try {
				final Admin a = this.adminService.findByPrincipal();
				Assert.notNull(a);
			} catch (final Throwable oops) {
				throw new IllegalAccessException();
			}
			final Integer minAtt = this.adminService.getMinAttendants();
			Assert.notNull(minAtt);
			final Integer maxAtt = this.adminService.getMaxAttendants();
			Assert.notNull(maxAtt);
			final Double avgAtt = this.adminService.getAvgAttendants();
			Assert.notNull(avgAtt);
			final Double stdAtt = this.adminService.getStandardDeviationAttendants();
			Assert.notNull(stdAtt);
			final Collection<User> topUsers = this.adminService.getTopUsers();
			Assert.notNull(topUsers);
			Assert.notEmpty(topUsers);
			final Collection<Driver> topDrivers = this.adminService.getTopDrivers();
			Assert.notNull(topDrivers);
			Assert.notEmpty(topDrivers);
			final Map<Mechanic, Integer> topMechanics = this.adminService.getTopMechanics();
			Assert.notNull(topMechanics);
			Assert.notEmpty(topMechanics);
			final Collection<User> worstUsers = this.adminService.getWorstUsers();
			Assert.notNull(worstUsers);
			Assert.notEmpty(worstUsers);
			final Collection<Driver> worstDrivers = this.adminService.getWorstDrivers();
			Assert.notNull(worstDrivers);
			Assert.notEmpty(worstDrivers);
			final Map<Mechanic, Integer> worstMechanics = this.adminService.getWorstMechanics();
			Assert.notNull(worstMechanics);
			Assert.notEmpty(worstMechanics);
			final Double ratioCancelled = this.adminService.getRatioCancelledAnnouncements();
			Assert.notNull(ratioCancelled);
			final Map<Actor, Integer> mostWritten = this.adminService.getMostReportsWritten();
			Assert.notNull(mostWritten);
			Assert.notEmpty(mostWritten);
			final Map<Actor, Integer> mostReceived = this.adminService.getMostReportsReceived();
			Assert.notNull(mostReceived);
			Assert.notEmpty(mostReceived);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}
}
