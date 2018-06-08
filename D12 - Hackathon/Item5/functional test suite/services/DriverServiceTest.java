
package services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Driver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DriverServiceTest extends AbstractTest {

	@Autowired
	private DriverService	driverService;


	/**
	 * Tests registering to the system as a driver
	 * <p>
	 * This method is used to test the register to the system as a driver. If the data introduced is incorrect an exception must be thrown.
	 * <p>
	 * Functional requirement:
	 * <p>
	 * An actor who is not authenticated must be able to:
	 * <p>
	 * Register to the system as a driver, sponsor, user or mechanic. Only people above 18 can register to the system.<br>
	 * <p>
	 * Case 1: The data introduced is correct <br>
	 * Case 2: The age of the user is under 18 <br>
	 * Case 3: The username is missing
	 */
	@Test
	public void driverRegisterDriver() {
		final Object testingData[][] = {
			{
				"drivertest1", "password", "23/03/1992", null
			}, {
				"drivertest2", "password", "23/03/2003", IllegalArgumentException.class
			}, {
				null, "password", "23/03/2003", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegister((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * Template for testing registering to the system as a driver
	 * <p>
	 * 
	 * @param username
	 *            The username of the driver we are registering
	 * @param password
	 *            The password of the driver
	 * @param birthdate
	 *            The date when the driver was born
	 * @param expected
	 *            The expected exception to be thrown. User <code>null</code> if no exception is expected
	 * 
	 */
	private void templateRegister(final String username, final String password, final String birthdate, final Class<?> expected) {
		Class<?> caught = null;
		try {
			final Date datetest = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
			final Driver d = this.driverService.create();
			d.getUserAccount().setUsername(username);
			d.getUserAccount().setPassword(password);
			d.setName("Name");
			d.setSurname("Surname");
			d.setEmail("email@acme.com");
			d.setPhone("+654321987");
			d.setBirthdate(datetest);
			d.setNationality("Spanish");
			d.setIdNumber("30266271L");
			d.setPhotoUrl("http://www.photo.es");
			d.setLocation("Location");
			this.driverService.save(d);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests registering to the edition of a driver's data
	 * <p>
	 * This method is used to test the edition of a driver's data. If the data introduced is incorrect or a driver tries to edit data belonging to another user, an exception must be thrown.
	 * <p>
	 * Functional requirement:
	 * <p>
	 * An actor who is authenticated must be able to:
	 * <p>
	 * Edit his profile.
	 * <p>
	 * Case 1: The data introduced is correct <br>
	 * Case 2: The age of the driver after editing his profile is set to lower than 18 <br>
	 * Case 3: A driver tries to edit data which belongs to another driver
	 */
	@Test
	public void driverEditDriver() {
		final Object testingData[][] = {
			{
				"driver1", "Driver1", "23/03/1989", null
			}, {
				"driver1", "Driver1", "23/03/2003", IllegalArgumentException.class
			}, {
				"driver2", "Driver1", "23/03/1989", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * Template for testing registering to the system as a driver
	 * <p>
	 * 
	 * @param driverLogged
	 *            The driver logged in the system
	 * @param driverEdited
	 *            The driver who will be edited
	 * @param birthdate
	 *            The date when the driver was born
	 * @param expected
	 *            The expected exception to be thrown. User <code>null</code> if no exception is expected
	 */
	private void templateEdit(final String driverLogged, final String driverEdited, final String birthdate, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(driverLogged);
			final int id = super.getEntityId(driverEdited);
			final Driver d = this.driverService.findOne(id);
			final Date datetest = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
			d.setBirthdate(datetest);
			this.driverService.save(d);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
