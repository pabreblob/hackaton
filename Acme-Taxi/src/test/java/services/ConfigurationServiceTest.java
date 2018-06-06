
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	@Autowired
	private ConfigurationService	configurationService;


	/**
	 * Test the finding of the configurations of the website
	 * <p>
	 * This tests check the method of the service Configuration which finds the configuration of the website
	 * 
	 */
	@Test
	public void testFind() {
		final Configuration config = this.configurationService.find();
		Assert.notNull(config);
	}

	/**
	 * Tests the edition of the configurations of the system
	 * <p>
	 * This method is used to test the edition of the configuration parameters. If the data introduced is incorrect or an actor who is not an admin tries to edit this data, an exception must be thrown.
	 * <p>
	 * Functional requirement:
	 * <p>
	 * 19.15 An actor who is authenticated as an admin must be able to: Configure different parameters of the system [...].
	 * <p>
	 * Case 1: An admin tries to edit the configurations. No exception is expected <br>
	 * Case 2: A driver tries to edit the configurations. <code>IllegalArgumentException</code> is expected <br>
	 * Case 3: A user tries to edit the configurations. <code>IllegalArgumentException</code> is expected.
	 */
	@Test
	public void driverEditConfiguration() {
		final Object testingData[][] = {
			{
				"admin", null
			}, {
				"driver1", IllegalArgumentException.class
			}, {
				"user1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the edition of the user's profile information
	 * <p>
	 * 
	 * @param userLogged
	 *            The user logged in the system
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected
	 */
	private void templateEdit(final String userLogged, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(userLogged);
			final Configuration c = this.configurationService.find();
			c.setCookiesPolicyEng("Test Text");
			this.configurationService.save(c);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}

}
