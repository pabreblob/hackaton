
package services;




import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Reservation;


import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ReservationServiceTest extends AbstractTest {

	
	@Autowired
	private ReservationService		reservationService;
	@Autowired
	private ServiceService		serviceService;


	

	/**
	 * Tests the creation of a reservation related to a service.
	 * <p>
	 * This method tests the creation of a reservation related to a service. Functional requirement:<br>
	 * An actor who is authenticated as a user must be able to:<br>
	 *Reserve a service from a repair shop. The reserve can be cancelled as long as the moment when it was supposed to take place has not passed. The owner of the repair shop will receive a message from the system informing him about it.<br>
	 *The message must be stored in folder “notification folder”.
	 * Case 1: A user reserves a service that is available. The process is done successfully. <br>
	 * Case 2: A user tries to reserve a service that is not available. The process is expected to fail. <br>
	 * Case 3: A user tries to reserve a service that he has already reserved. The process is expected to fail. <br>
	 */
	@Test
	public void driverCreateReservation() {
		final Object testingData[][] = {
			{
				"user1", "Service6",null
			}, {
				"user2", "Service3", IllegalArgumentException.class
			}, {
				"user1", "Service1",IllegalArgumentException.class
				
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0],super.getEntityId((String) testingData[i][1]),(Class<?>) testingData[i][2]);
	}
	

	/**
	 * Template for testing the creation of a reservation related to a service.
	 * <p>
	 * This method defines the template used for the tests that checks the creation of a reservation related to a service.
	 * 
	 * @param user
	 *            The user that makes the reservation
	 * @param serviceId
	 *            The id of the service that is being reserved.

	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreate(final String user, final int serviceId, final Class<?> expected) {
		Class<?> caught= null;
			try{
			super.authenticate(user);
			Reservation r=this.reservationService.create(this.serviceService.findOne(serviceId));
			r.setMoment(new Date(System.currentTimeMillis()+50000000));
			this.reservationService.save(r);
			super.authenticate(null);
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	/**
	 * Tests the cancelation of a reservation related to a service.
	 * <p>
	 * This method tests the cancelation of a reservation related to a service. Functional requirement:<br>
	 * An actor who is authenticated as a user must be able to:<br>
	 *Reserve a service from a repair shop. The reserve can be cancelled as long as the moment when it was supposed to take place has not passed. The owner of the repair shop will receive a message from the system informing him about it.<br>
	 *The message must be stored in folder “notification folder”.
	 * Case 1: A user cancels a reservation that he has made. The process is done successfully. <br>
	 * Case 2: A user tries to cancel a reservation he has not made. The process is expected to fail. <br>
	 * Case 3: A user tries to cancel a reservation whose moment is in the past. The process is expected to fail. <br>
	 */
	@Test
	public void driverCancelReservation() {
		final Object testingData[][] = {
			{
				"user1", "Reservation1",null
			}, {
				"user2", "Reservation4", IllegalArgumentException.class
			}, {
				"user1", "Reservation2",IllegalArgumentException.class
				
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCancel((String) testingData[i][0],super.getEntityId((String) testingData[i][1]),(Class<?>) testingData[i][2]);
	}
	

	/**
	 * Template for testing the creation of a reservation related to a service.
	 * <p>
	 * This method defines the template used for the tests that checks the creation of a reservation related to a service.
	 * 
	 * @param user
	 *            The user that cancels the reservation
	 * @param reservationId
	 *            The id of the reservation that is being cancelled.

	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCancel(final String user, final int reservationId, final Class<?> expected) {
		Class<?> caught= null;
			try{
			super.authenticate(user);
			this.reservationService.cancel(reservationId);
			super.authenticate(null);
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
	
	
	


