
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
import domain.Mechanic;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MechanicServiceTest extends AbstractTest {

	@Autowired
	private MechanicService	mechanicService;


	/**
	 * Tests registering to the system as a mechanic
	 * <p>
	 * This method is used to test the register to the system as a user. If the data introduced is incorrect an exception must be thrown.
	 * <p>
	 * Functional requirement:
	 * <p>
	 *  An actor who is not authenticated must be able to:
	 * <p>
	 * Register to the system as a driver, sponsor, user or mechanic. Only people above 18 can register to the system.<br>
	 * <p>
	 * Case 1: The data introduced is correct <br>
	 * Case 2: The age of the user is under 18 <br>
	 * Case 3: The username is missing
	 */
	@Test
	public void driverRegisterAsAMechanic() {
		final Object testingData[][] = {
			{
				"username", "password","23/03/1992", null
			}, {
				"username2", "password","23/03/2003",IllegalArgumentException.class
			},{
				null, "password","23/03/2003",NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegister((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * Template for testing registering to the system as a mechanic
	 * <p>
	 * 
	 * @param username
	 *            The username of the mechanic we are registering
	 * @param password
	 *            The password of the mechanic
	 * @param expected
	 *            The expected exception to be thrown. User <code>null</code> if no exception is expected
	 */
	private void templateRegister(final String username, final String password,final String birthdate, final Class<?> expected) {
		Class<?> caught = null;
		try {
			Date datetest=new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
			final Mechanic m = this.mechanicService.create();
			m.getUserAccount().setUsername(username);
			m.getUserAccount().setPassword(password);
			m.setName("Name");
			m.setSurname("Surname");
			m.setEmail("email@acme.com");
			m.setPhone("+654321987");
			m.setBirthdate(datetest);
			m.setNationality("Spanish");
			m.setIdNumber("30266271L");
			this.mechanicService.save(m);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	/**
	 * Tests registering to the edition of a mechanic's data
	 * <p>
	 * This method is used to test the edition of a mechanic's data. If the data introduced is incorrect or a mechanic tries to edit data belonging to another user, an exception must be thrown.
	 * <p>
	 * Functional requirement:
	 * <p>
	 *  An actor who is authenticated must be able to:
	 * <p>
	 * Edit his profile.
	 * <p>
	 * Case 1: The data introduced is correct <br>
	 * Case 2: The age of the mechanic after editing his profile is set to lower than 18 <br>
	 * Case 3: A mechanic tries to edit data which belongs to another mechanic
	 */
	@Test
	public void driverEditMechanic() {
		final Object testingData[][] = {
			{
				"mechanic1", "Mechanic1","23/03/1989", null
			}, {
				"mechanic1", "Mechanic1","23/03/2003",IllegalArgumentException.class
			},{
				"mechanic2", "Mechanic1","23/03/1989",IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],(String) testingData[i][1],(String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/**
	 * Template for testing registering to the system as a mechanic
	 * <p>
	 * 
	 * @param username
	 *            The username of the mechanic we are registering
	 * @param password
	 *            The password of the mechanic
	 * @param expected
	 *            The expected exception to be thrown. User <code>null</code> if no exception is expected
	 */
	private void templateEdit(final String mechanicLogged,String mechanicEdited,final String birthdate, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(mechanicLogged);
			int id=super.getEntityId(mechanicEdited);
			Mechanic m=this.mechanicService.findOne(id);
			Date datetest=new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
			m.setBirthdate(datetest);
			this.mechanicService.save(m);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
