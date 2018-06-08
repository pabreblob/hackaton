
package services;




import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Service;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ServiceServiceTest extends AbstractTest {

	@Autowired
	private RepairShopService		repairShopService;

	@Autowired
	private ServiceService		serviceService;


	

	/**
	 * Tests the creation of services and their edition.
	 * <p>
	 * This method tests the creation of services and their edition. Functional requirement:<br>
	 * An actor who is authenticated as a mechanic must be able to:<br>
	 *Add new services to a repair shop. A service may be edited or deleted as long as no user has currently reserved it. Reserves that have already expired are not taken into account.<br>
	 * A service may be suspended at any moment. That means users will no longer be able to reserve it. A suspended service may be reopened any time<br/>
	 *List all of his repair shops and edit their data. A mechanic shop may be deleted as long as it does not offer any services.
	 * Case 1: A mechanic creates a new service for one of his repair shops. Afterwards, he edits the service. The process is done successfully. <br>
	 * Case 2: A mechanic creates a new service for one of his repair shops. Afterwards, another mechanic tries to edit the service. The process is expected to fail. <br>
	 * Case 3: A mechanic tries to create a service for a repair shop that does not exist. The process is expected to fail. <br>
	 */
	@Test
	public void driverEditService() {
		final Object testingData[][] = {
			{
				"mechanic1", "RepairShop3","mechanic1",null
			}, {
				"mechanic1", "RepairShop3","mechanic2", IllegalArgumentException.class
			}, {
				"mechanic1", "non-valid","mechanic1",NullPointerException.class
				
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],(String) testingData[i][1],(String) testingData[i][2],(Class<?>) testingData[i][3]);
	}
	

	/**
	 * Template for testing the creation of services and their edition.
	 * <p>
	 * This method defines the template used for the tests that check the creation of services and their edition.
	 * 
	 * @param mechanic1
	 *            The mechanic that creates the service
	 * @param repairShop
	 *            The repair shop for which the mechanic wants to create a new service. It must not be null and it must belong to the mechanic that tries to create the service.
	 * @param mechanic2
	 *            The mechanic that edits the service
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateEdit(final String mechanic1, final String repairShop,final String mechanic2, final Class<?> expected) {
		Class<?> caught= null;
		
		try {
			Integer id;
			if (repairShop.equals("non-valid"))
				id = null;
			else
				id = this.getEntityId(repairShop);
			super.authenticate(mechanic1);
			Service s=this.serviceService.create(this.repairShopService.findOne(id));
			s.setPrice(23);
			s.setTitle("test");
			this.serviceService.save(s);
			super.authenticate(null);
			super.authenticate(mechanic2);
			s.setTitle("edited");
			this.serviceService.save(s);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	
	/**
	 * Tests the deletion of services.
	 * <p>
	 * This method tests the deletion of services. Functional requirement:<br>
	 * An actor who is authenticated as a mechanic must be able to:<br>
	 *Add new services to a repair shop. A service may be edited or deleted as long as no user has currently reserved it. Reserves that have already expired are not taken into account.<br>
	 * A service may be suspended at any moment. That means users will no longer be able to reserve it. A suspended service may be reopened any time<br/>
	 *List all of his repair shops and edit their data. A mechanic shop may be deleted as long as it does not offer any services.
	 * Case 1: A mechanic deletes a service which  currently has no reservations and belongs to one of his repair shops. The process is done successfully. <br>
	 * Case 2: A mechanic tries to delete a service which currently has no reservations but does not belong to one of his repair shops. The process is expected to fail. <br>
	 * Case 3: A mechanic tries to delete a service that does not exist. The process is expected to fail. <br>
	 */
	@Test
	public void driverDeleteService() {
		final Object testingData[][] = {
			{
				"mechanic1", "Service6",null
			}, {
				"mechanic2", "Service6",IllegalArgumentException.class
			}, {
				"mechanic1", "non-valid",NullPointerException.class
				
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0],(String) testingData[i][1],(Class<?>) testingData[i][2]);
	}
	

	/**
	 * Template for testing the creation of services and their edition.
	 * <p>
	 * This method defines the template used for the tests that check the creation of services and their edition.
	 * 
	 * @param mechanic
	 *            The mechanic that deletes the service
	 * @param service
	 *            The service that is being deleted. It must not be null, its repair shop must belong to the mechanic that is trying to delete it and it must not have pending reservations.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateDelete(final String mechanic,String service, final Class<?> expected) {
		Class<?> caught= null;
		
		try {
			Integer id;
			if (service.equals("non-valid"))
				id = null;
			else
				id = this.getEntityId(service);
			super.authenticate(mechanic);
			Service s=this.serviceService.findOne(id);
			
			this.serviceService.delete(s);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
}

