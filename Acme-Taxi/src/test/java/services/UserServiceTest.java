
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Announcement;
import domain.Review;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {

	@Autowired
	private UserService	userService;


	/**
	 * Tests registering to the system as a user
	 * <p>
	 * This method is used to test the register to the system as a user. If the data introduced is incorrect an exception must be thrown.
	 * <p>
	 * Functional requirement:
	 * <p>
	 * 13.1: An actor who is not authenticated must be able to: Register to the system as a user. Only people above 18 can register to the system.<br>
	 * <p>
	 * Case 1: The data introduced is correct <br>
	 * Case 2: The age of the user is under 18. <code>IllegalArgumentException</code> is expected <br>
	 * Case 3: The username is missing. <code>IllegalArgumentException</code> is expected
	 */
	@Test
	public void driverRegisterUser() {
		final Object testingData[][] = {
			{
				"usertest12", "password", "23/03/1992", null
			}, {
				"usertest2", "password", "23/03/2003", IllegalArgumentException.class
			}, {
				null, "password", "23/03/2003", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegister((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * Template for testing registering to the system as a user
	 * <p>
	 * 
	 * @param username
	 *            The username of the user we are registering
	 * @param password
	 *            The password of the user
	 * @param birthdate
	 *            The date when the user was born
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected
	 * 
	 */
	private void templateRegister(final String username, final String password, final String birthdate, final Class<?> expected) {
		super.unauthenticate();
		Class<?> caught = null;
		try {
			final Date datetest = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
			final User u = this.userService.create();
			u.getUserAccount().setUsername(username);
			u.getUserAccount().setPassword(password);
			u.setName("Name");
			u.setSurname("Surname");
			u.setEmail("email@acme.com");
			u.setPhone("+654321987");
			u.setBirthdate(datetest);
			u.setPhotoUrl("http://www.photo.es");
			u.setLocation("Location");
			u.setAnnouncements(new ArrayList<Announcement>());
			u.setReviews(new ArrayList<Review>());
			this.userService.save(u);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the edition of a user's data
	 * <p>
	 * This method is used to test the edition of a user's data. If the data introduced is incorrect or a user tries to edit data belonging to another user, an exception must be thrown.
	 * <p>
	 * Functional requirement:
	 * <p>
	 * 14.5 An actor who is authenticated must be able to: Edit his profile.
	 * <p>
	 * Case 1: The data introduced is correct <br>
	 * Case 2: A driver tries to edit a user's data. <code>IllegalArgumentException</code> is expected <br>
	 * Case 3: A user tries to edit data which belongs to another user. <code>IllegalArgumentException</code> is expected.
	 */
	@Test
	public void driverEditUser() {
		final Object testingData[][] = {
			{
				"user1", "User1", null
			}, {
				"driver1", "User1", IllegalArgumentException.class
			}, {
				"user2", "User1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the edition of the user's profile information
	 * <p>
	 * 
	 * @param userLogged
	 *            The user logged in the system
	 * @param userEdited
	 *            The user that will be edited
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected
	 */
	private void templateEdit(final String userLogged, final String userEdited, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(userEdited);
			final int id = this.userService.findByPrincipal().getId();
			super.unauthenticate();
			super.authenticate(userLogged);
			final User u = this.userService.findOne(id);
			u.setName("Paco");
			this.userService.save(u);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
