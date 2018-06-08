
package services;


import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.RepairShop;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RepairShopServiceTest extends AbstractTest {

	@Autowired
	private RepairShopService		repairShopService;
	@Autowired
	private MechanicService		mechanicService;
	@Autowired
	private ServiceService		serviceService;


	

	/**
	 * Tests the edition of repair shops  and their deletion.
	 * <p>
	 * This method tests the edition of repair shops  and their deletion. Functional requirement:<br>
	 *17. An actor who is authenticated as a mechanic must be able to:<br/>
	 *List all of his repair shops and edit their data. A mechanic shop may be deleted as long as it does not offer any services.
	 * Case 1: A mechanic lists his repair shops and then edit the data of a repair shop with no services. Afterwards, he deletes the repair shop. The process is done successfully. <br>
	 * Case 2: A mechanic lists his repair shops and then tries to edit the data of a repair shop that does not belongs to him. Afterwards, he deletes the repair shop. The process is expected to fail.<br>
	 * Case 3: A mechanic lists his repair shops and then edit the data of a repair shop with services. Afterwards, he tries to delete the repair shop. The process is expected to fail. <br>
	 */
	@Test
	public void driverEditShop() {
		final Object testingData[][] = {
			{
				"mechanic1", "RepairShop3",null
			}, {
				"mechanic2", "RepairShop3", IllegalArgumentException.class
			}, {
				"mechanic1", "RepairShop1",IllegalArgumentException.class
				
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],super.getEntityId((String) testingData[i][1]),(Class<?>) testingData[i][2]);
	}
	

	/**
	 * Template for testing the edition of repair shops  and their deletion.
	 * <p>
	 * This method defines the template used for the tests that check the edition of repair shops  and their deletion.
	 * 
	 * @param mechanic
	 *            The mechanic that lists, edits and deletes the repairShop
	 * @param repairShopId
	 *            The id of the repair shop that is being edited It must not be null and it must belong to the mechanic that tries to edit it.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateEdit(final String mechanic, final int repairShopId, final Class<?> expected) {
		Class<?> caught= null;
		
		try {
			super.authenticate(mechanic);
			Collection<RepairShop> shops=this.repairShopService.findByMechanic(this.mechanicService.findByPrincipal().getId());
			Assert.isTrue(!shops.isEmpty());
			RepairShop shop=this.repairShopService.findOne(repairShopId);
			shop.setName("edited");
			this.repairShopService.save(shop);
			Assert.isTrue(this.serviceService.findAllByRepairShop(shop.getId()).isEmpty());
			this.repairShopService.delete(shop);
			Assert.isTrue(!repairShopService.findAll().contains(shop));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
}

