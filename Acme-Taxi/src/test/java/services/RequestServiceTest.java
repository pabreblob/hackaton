
package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Request;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RequestServiceTest extends AbstractTest {

	@Autowired
	private RequestService	requestService;
	@Autowired
	private UserService		userService;


	/**
	 * Tests the creation and saving of a Request in the system.
	 * <p>
	 * Functional Requirement 7: Users can request for a private driver to take them somewhere. The request must include the point of departure, the destination, the total number of people who are going to use that service (user included) and the moment
	 * when they want the car to depart. They may also add an additional comment. Requests will have a price that depends on the distance between the point of departure and the destination. The way this price is calculated is the following: The system
	 * stores a minimum fee and a fee per kilometer. These two fees will be the same for any request made. The total price of the request will be the minimum fee plus the product between the fee per kilometer and the distance in kilometers between the
	 * point of departure and the destination. The distance will be calculated using the Google Maps API. The total price of the request must be shown to a user when making the request. As well as the total price taking into account the distance between
	 * the starting point and the destination. The VAT must be also considered when calculating the price.
	 * <p>
	 * Case 1: The user1 creates a request in 21/05/2020. No exception is expected.<br>
	 * Case 2: The user1 creates a request in 01/06/1997. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: The driver1 creates a request in 01/06/2019. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				"user1", "21", "05", "2020", null
			}, {
				"user1", "01", "06", "1997", IllegalArgumentException.class
			}, {
				"driver1", "01", "06", "2019", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	/**
	 * Template for testing the creation and saving of a request.
	 * <p>
	 * This method defines the template used for the tests that check the creation and saving of new request in the system.
	 * 
	 * @param creator
	 *            The username of the creator
	 * @param day
	 *            The day of the moment of departure
	 * @param month
	 *            The month of the moment of departure
	 * @param year
	 *            The year of the moment of departure
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	@SuppressWarnings("deprecation")
	private void templateCreateAndSave(final String creator, final String day, final String month, final String year, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(creator);
			final Request r = this.requestService.create();
			r.setComment("comment");
			r.setOrigin("Los Palacios, Sevilla, España");
			r.setDestination("Sevilla, Sevilla, España");
			r.setMoment(new Date(new Integer(year) - 1900, new Integer(month) - 1, new Integer(day)));
			r.setMarked(false);
			r.setUser(this.userService.findByPrincipal());
			r.setPassengersNumber(1);
			final Request saved = this.requestService.save(r);
			Assert.notNull(saved);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.unauthenticate();
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the creation, the saving and the deleting of a Request in the system.
	 * <p>
	 * Functional Requirement 7: Users can request for a private driver to take them somewhere. The request must include the point of departure, the destination, the total number of people who are going to use that service (user included) and the moment
	 * when they want the car to depart. They may also add an additional comment. Requests will have a price that depends on the distance between the point of departure and the destination. The way this price is calculated is the following: The system
	 * stores a minimum fee and a fee per kilometer. These two fees will be the same for any request made. The total price of the request will be the minimum fee plus the product between the fee per kilometer and the distance in kilometers between the
	 * point of departure and the destination. The distance will be calculated using the Google Maps API. The total price of the request must be shown to a user when making the request. As well as the total price taking into account the distance between
	 * the starting point and the destination. The VAT must be also considered when calculating the price.
	 * <p>
	 * Case 1: The user1 creates a request in 21/05/2020. Then, it's deleted by user1. No exception is expected.<br>
	 * Case 2: The user1 creates a request in 01/06/2025. Then, it's deleted by user2. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: The user1 creates a request in 21/06/2025. Then, it's deleted by driver1. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverCreateSaveAndDelete() {
		final Object testingData[][] = {
			{
				"user1", "21", "05", "2020", "user1", null
			}, {
				"user1", "01", "06", "2025", "user2", IllegalArgumentException.class
			}, {
				"user1", "21", "06", "2025", "driver1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveAndDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	/**
	 * Template for testing the creation, the saving and the deleting of a request.
	 * <p>
	 * This method defines the template used for the tests that check the creation, the saving and the deleting of new request in the system.
	 * 
	 * @param creator
	 *            The username of the creator
	 * @param day
	 *            The day of the moment of departure
	 * @param month
	 *            The month of the moment of departure
	 * @param year
	 *            The year of the moment of departure
	 * @param actor
	 *            The actor who is going to delete the request
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	@SuppressWarnings("deprecation")
	private void templateCreateSaveAndDelete(final String creator, final String day, final String month, final String year, final String actor, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(creator);
			final Request r = this.requestService.create();
			r.setComment("comment");
			r.setOrigin("Los Palacios, Sevilla, España");
			r.setDestination("Sevilla, Sevilla, España");
			r.setMoment(new Date(new Integer(year) - 1900, new Integer(month) - 1, new Integer(day)));
			r.setMarked(false);
			r.setUser(this.userService.findByPrincipal());
			r.setPassengersNumber(1);
			final Request saved = this.requestService.save(r);
			Assert.notNull(saved);
			super.unauthenticate();
			super.authenticate(actor);
			this.requestService.delete(saved.getId());
			Assert.isTrue(!this.requestService.findAll().contains(saved));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.unauthenticate();
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the creation, the saving and the accepting of a Request in the system.
	 * <p>
	 * Functional Requirement 7: Users can request for a private driver to take them somewhere. The request must include the point of departure, the destination, the total number of people who are going to use that service (user included) and the moment
	 * when they want the car to depart. They may also add an additional comment. Requests will have a price that depends on the distance between the point of departure and the destination. The way this price is calculated is the following: The system
	 * stores a minimum fee and a fee per kilometer. These two fees will be the same for any request made. The total price of the request will be the minimum fee plus the product between the fee per kilometer and the distance in kilometers between the
	 * point of departure and the destination. The distance will be calculated using the Google Maps API. The total price of the request must be shown to a user when making the request. As well as the total price taking into account the distance between
	 * the starting point and the destination. The VAT must be also considered when calculating the price.
	 * <p>
	 * Case 1: The user1 creates a request in 21/05/2020. Then, it's accepted by driver1. No exception is expected.<br>
	 * Case 2: The user1 creates a request in 01/06/2050. Then, it's accepted by user2. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: The user1 creates a request in 01/06/2050. Then, it's accepted by driver1. Then, it's accepted by driver2. An <code>IllegalArgumentException</code> is expected.<br>
	 * 
	 */
	@Test
	public void driverCreateSaveAndAccept() {
		final Object testingData[][] = {
			{
				"user1", "21", "05", "2020", "driver1", null
			}, {
				"user1", "01", "06", "2050", "user2", IllegalArgumentException.class
			}, {
				"user1", "01", "06", "2050", "twoDrivers", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveAndAccept((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	/**
	 * Template for testing the creation, the saving and the accepting of a request.
	 * <p>
	 * This method defines the template used for the tests that check the creation, the saving and the accepting of new request in the system.
	 * 
	 * @param creator
	 *            The username of the creator
	 * @param day
	 *            The day of the moment of departure
	 * @param month
	 *            The month of the moment of departure
	 * @param year
	 *            The year of the moment of departure
	 * @param driver
	 *            The driver who is going to accept the request
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	@SuppressWarnings("deprecation")
	private void templateCreateSaveAndAccept(final String creator, final String day, final String month, final String year, final String driver, final Class<?> expected) {
		Class<?> caught = null;
		try {
			if (driver.equals("twoDrivers")) {
				super.authenticate(creator);
				final Request r = this.requestService.create();
				r.setComment("comment");
				r.setOrigin("Los Palacios, Sevilla, España");
				r.setDestination("Sevilla, Sevilla, España");
				r.setMoment(new Date(new Integer(year) - 1900, new Integer(month) - 1, new Integer(day)));
				r.setMarked(false);
				r.setUser(this.userService.findByPrincipal());
				r.setPassengersNumber(1);
				final Request saved = this.requestService.save(r);
				Assert.notNull(saved);
				super.unauthenticate();
				super.authenticate("driver1");
				this.requestService.accept(saved.getId());
				final Request accepted = this.requestService.findOne(saved.getId());
				Assert.notNull(accepted.getDriver());
				super.unauthenticate();
				super.authenticate("driver2");
				this.requestService.accept(accepted.getId());
			} else {
				super.authenticate(creator);
				final Request r = this.requestService.create();
				r.setComment("comment");
				r.setOrigin("Los Palacios, Sevilla, España");
				r.setDestination("Sevilla, Sevilla, España");
				r.setMoment(new Date(new Integer(year) - 1900, new Integer(month) - 1, new Integer(day)));
				r.setMarked(false);
				r.setUser(this.userService.findByPrincipal());
				r.setPassengersNumber(1);
				final Request saved = this.requestService.save(r);
				Assert.notNull(saved);
				super.unauthenticate();
				super.authenticate(driver);
				this.requestService.accept(saved.getId());
				final Request accepted = this.requestService.findOne(saved.getId());
				Assert.notNull(accepted.getDriver());
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.unauthenticate();
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the creation, the saving, the accepting and the cancelling of a Request in the system.
	 * <p>
	 * Functional Requirement 7: Users can request for a private driver to take them somewhere. The request must include the point of departure, the destination, the total number of people who are going to use that service (user included) and the moment
	 * when they want the car to depart. They may also add an additional comment. Requests will have a price that depends on the distance between the point of departure and the destination. The way this price is calculated is the following: The system
	 * stores a minimum fee and a fee per kilometer. These two fees will be the same for any request made. The total price of the request will be the minimum fee plus the product between the fee per kilometer and the distance in kilometers between the
	 * point of departure and the destination. The distance will be calculated using the Google Maps API. The total price of the request must be shown to a user when making the request. As well as the total price taking into account the distance between
	 * the starting point and the destination. The VAT must be also considered when calculating the price.
	 * <p>
	 * Case 1: The user1 creates a request in 21/05/2020. Then, it's accepted by driver1. Later, it's cancelled by user1. No exception is expected.<br>
	 * Case 2: The user1 creates a request in 01/06/2025. Then, it's accepted by driver1. Later, it's cancelled by user2. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: The user1 creates a request in 01/06/2025. Then, it's accepted by driver1. Later, it's cancelled by admin. An <code>IllegalArgumentException</code> is expected.<br>
	 * 
	 */
	@Test
	public void driverCreateSaveAcceptAndCancel() {
		final Object testingData[][] = {
			{
				"user1", "21", "05", "2020", "driver1", "user1", null
			}, {
				"user1", "01", "06", "2025", "driver1", "user2", IllegalArgumentException.class
			}, {
				"user1", "01", "06", "2025", "driver1", "admin", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveAcceptAndCancel((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	/**
	 * Template for testing the creation, the saving, the accepting and the cancelling of a request.
	 * <p>
	 * This method defines the template used for the tests that check the creation, the saving, the accepting and the cancelling of new request in the system.
	 * 
	 * @param creator
	 *            The username of the creator
	 * @param day
	 *            The day of the moment of departure
	 * @param month
	 *            The month of the moment of departure
	 * @param year
	 *            The year of the moment of departure
	 * @param driver
	 *            The driver who is going to accept the request
	 * @param actor
	 *            The actor who is going to cancel the request
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	@SuppressWarnings("deprecation")
	private void templateCreateSaveAcceptAndCancel(final String creator, final String day, final String month, final String year, final String driver, final String actor, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(creator);
			final Request r = this.requestService.create();
			r.setComment("comment");
			r.setOrigin("Los Palacios, Sevilla, España");
			r.setDestination("Sevilla, Sevilla, España");
			r.setMoment(new Date(new Integer(year) - 1900, new Integer(month) - 1, new Integer(day)));
			r.setMarked(false);
			r.setUser(this.userService.findByPrincipal());
			r.setPassengersNumber(1);
			final Request saved = this.requestService.save(r);
			Assert.notNull(saved);
			super.unauthenticate();
			super.authenticate(driver);
			this.requestService.accept(saved.getId());
			final Request accepted = this.requestService.findOne(saved.getId());
			Assert.notNull(accepted.getDriver());
			super.unauthenticate();
			super.authenticate(actor);
			this.requestService.cancel(accepted.getId());
			final Request cancelled = this.requestService.findOne(accepted.getId());
			Assert.isTrue(cancelled.isCancelled());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.unauthenticate();
		this.checkExceptions(expected, caught);
	}
}
