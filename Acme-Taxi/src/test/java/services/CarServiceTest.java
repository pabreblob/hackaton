
package services;






import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Car;


import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CarServiceTest extends AbstractTest {

	
	@Autowired
	private CarService		carService;
	@Autowired
	private RepairShopService		repairShopService;


	

	/**
	 * Tests the creation of a car and its association with a repair shop.
	 * <p>
	 * This method tests the creation of a car and its association with a repair shop. Functional requirement:<br>
	 * An actor who is authenticated as a driver must be able to:<br>
	 *Register a car and associate it to a repair shop. A driver can only have one car registered. The car may be edited or deleted as long as the driver has not accepted any requests which have not taken place yet.<br>
	 * A driver can change the repair shop his car is associated with at any moment, or not associating the car with any repair shop at all.
	 * Case 1: A driver creates a car and associates it to a repair shop. Afterwards, he deletes the car The process is done successfully. <br> 
	 * Case 2: A driver tries to create a car when already  has one created. The process is expected to fail. <br>
	 * Case 3:A driver creates a car and tries to associate it to a repair shop that does not exist. The process is expected to fail <br>
	 */
	@Test
	public void driverCreateReservation() {
		final Object testingData[][] = {
			{
				"driver4", "RepairShop1",null
			}, {
				"driver1", "RepairShop1", IllegalArgumentException.class
			}, {
				"driver3", "non-valid",NullPointerException.class
				
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0],(String) testingData[i][1],(Class<?>) testingData[i][2]);
	}
	

	/**
	 * Template for testing the creation of a car and its association with a repair shop.
	 * <p>
	 * This method defines the template used for the tests that checks the creation of a car and its association with a repair shop.
	 * 
	 * @param driver
	 *            The driver that creates the car.
	 * @param repairShop
	 *            The repair shop the car is associated to.

	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreate(final String driver, String repairShop, final Class<?> expected) {
		Class<?> caught= null;
			try{
				Integer id;
				if (repairShop.equals("non-valid"))
					id = null;
				else
					id = this.getEntityId(repairShop);
			super.authenticate(driver);
			Car c=this.carService.create();
			c.setCarModel("test");
			c.setMaxPassengers(3);
			c.setNumberPlate("45678ABC");
			Car res=this.carService.save(c);
			res.setRepairShop(this.repairShopService.findOne(id));
			this.carService.delete(res);
			super.authenticate(null);
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
	
	
	


