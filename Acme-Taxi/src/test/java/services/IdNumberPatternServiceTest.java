
package services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.IdNumberPattern;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class IdNumberPatternServiceTest extends AbstractTest {

	@Autowired
	private IdNumberPatternService	idNumberPatternService;


	/**
	 * Tests the creation and saving of Id Number Pattern in the system.
	 * <p>
	 * Functional Requirement 15: Configure different parameters of the system such as currency, spam words, limit of reports per week, prices of sponsorships, the fees associated with private drivers, welcome message and any other parameter that may be
	 * considered necessary.
	 * <p>
	 * Case 1: An id number pattern is created. No exception is expected.<br>
	 * Case 2: An empty id number pattern is created. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				"^$", null
			}, {
				"", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the creation and saving of Id Number Pattern.
	 * <p>
	 * This method defines the template used for the tests that check the creation and saving of new Id Number Pattern in the system.
	 * 
	 * @param pattern
	 *            The pattern which will be the new id number pattern.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreateAndSave(final String pattern, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate("admin");
			final IdNumberPattern idPattern = this.idNumberPatternService.create();
			idPattern.setPattern(pattern);
			idPattern.setNationality("Spanish");
			final IdNumberPattern res = this.idNumberPatternService.save(idPattern);
			Assert.notNull(res);
			Assert.isTrue(res.equals(this.idNumberPatternService.findOne(res.getId())));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of Id Number Pattern by an Administrator of the System
	 * <p>
	 * Functional Requirement 15: Configure different parameters of the system such as currency, spam words, limit of reports per week, prices of sponsorships, the fees associated with private drivers, welcome message and any other parameter that may be
	 * considered necessary.
	 * <p>
	 * Case 1: An Administrator deletes a Id Number Pattern. No exception is expected.<br>
	 * Case 2: A User tries to delete a Id Number Pattern. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				"admin", null
			}, {
				"user1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the deletion of Id Number Pattern.
	 * <p>
	 * This method defines the template used for the tests that check the deletion of Id Number Pattern in the system.
	 * 
	 * @param actor
	 *            The actor that will try to delete the Id Number Pattern.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateDelete(final String actor, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(actor);
			final List<IdNumberPattern> idPattern = new ArrayList<IdNumberPattern>(this.idNumberPatternService.findAll());
			final IdNumberPattern res = idPattern.get(0);
			this.idNumberPatternService.delete(res);
			Assert.isTrue(!this.idNumberPatternService.findAll().contains(res));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
