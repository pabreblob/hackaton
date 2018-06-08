
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
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	@Autowired
	private SponsorService	sponsorService;


	/**
	 * Tests registering to the system as a sponsor
	 * <p>
	 * This method is used to test the register to the system as a sponsor. If the data introduced is incorrect an exception must be thrown.
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
	public void driverRegisterSponsor() {
		final Object testingData[][] = {
			{
				"sponsortest1", "password", "23/03/1992", null
			}, {
				"sponsortest2", "password", "23/03/2003", IllegalArgumentException.class
			}, {
				null, "password", "23/03/2003", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegister((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * Template for testing registering to the system as a sponsor
	 * <p>
	 * 
	 * @param username
	 *            The username of the sponsor we are registering
	 * @param password
	 *            The password of the sponsor
	 * @param birthdate
	 *            The date when the sponsor was born
	 * @param expected
	 *            The expected exception to be thrown. User <code>null</code> if no exception is expected
	 * 
	 */
	private void templateRegister(final String username, final String password, final String birthdate, final Class<?> expected) {
		Class<?> caught = null;
		try {
			final Date datetest = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
			final Sponsor m = this.sponsorService.create();
			m.getUserAccount().setUsername(username);
			m.getUserAccount().setPassword(password);
			m.setName("Name");
			m.setSurname("Surname");
			m.setEmail("email@acme.com");
			m.setPhone("+654321987");
			m.setBirthdate(datetest);
			m.setNationality("Spanish");
			m.setIdNumber("30266271L");
			this.sponsorService.save(m);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests registering to the edition of a sponsor's data
	 * <p>
	 * This method is used to test the edition of a sponsor's data. If the data introduced is incorrect or a sponsor tries to edit data belonging to another user, an exception must be thrown.
	 * <p>
	 * Functional requirement:
	 * <p>
	 * An actor who is authenticated must be able to:
	 * <p>
	 * Edit his profile.
	 * <p>
	 * Case 1: The data introduced is correct <br>
	 * Case 2: The age of the sponsor after editing his profile is set to lower than 18 <br>
	 * Case 3: A sponsor tries to edit data which belongs to another sponsor
	 */
	@Test
	public void driverEditSponsor() {
		final Object testingData[][] = {
			{
				"sponsor1", "Sponsor1", "23/03/1989", null
			}, {
				"sponsor1", "Sponsor1", "23/03/2003", IllegalArgumentException.class
			}, {
				"sponsor2", "Sponsor1", "23/03/1989", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * Template for testing registering to the system as a sponsor
	 * <p>
	 * 
	 * @param sponsorLogged
	 *            The sponsor logged in the system
	 * @param sponsorEdited
	 *            The sponsor who will be edited
	 * @param birthdate
	 *            The date when the sponsor was born
	 * @param expected
	 *            The expected exception to be thrown. User <code>null</code> if no exception is expected
	 */
	private void templateEdit(final String sponsorLogged, final String sponsorEdited, final String birthdate, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(sponsorLogged);
			final int id = super.getEntityId(sponsorEdited);
			final Sponsor s = this.sponsorService.findOne(id);
			final Date datetest = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
			s.setBirthdate(datetest);
			this.sponsorService.save(s);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
