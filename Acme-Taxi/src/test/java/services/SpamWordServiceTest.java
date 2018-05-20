
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
import domain.SpamWord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SpamWordServiceTest extends AbstractTest {

	@Autowired
	private SpamWordService	spamWordService;


	/**
	 * Tests the creation and saving of Spam Words in the system.
	 * <p>
	 * Functional Requirement 15: Configure different parameters of the system such as currency, spam words, limit of reports per week, prices of sponsorships, the fees associated with private drivers, welcome message and any other parameter that may be
	 * considered necessary.
	 * <p>
	 * Case 1: An spam word is created. No exception is expected.<br>
	 * Case 2: An empty spam word is created. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				"spamWord", null
			}, {
				"", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the creation and saving of Spam Words.
	 * <p>
	 * This method defines the template used for the tests that check the creation and saving of new Spam Words in the system.
	 * 
	 * @param word
	 *            The word which will be the new spam word.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateCreateAndSave(final String word, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate("admin");
			final SpamWord tabooword = this.spamWordService.create();
			tabooword.setWord(word);
			final SpamWord res = this.spamWordService.save(tabooword);
			Assert.notNull(res);
			Assert.isTrue(res.equals(this.spamWordService.findOne(res.getId())));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of Spam Words by an Administrator of the System
	 * <p>
	 * Functional Requirement 15: Configure different parameters of the system such as currency, spam words, limit of reports per week, prices of sponsorships, the fees associated with private drivers, welcome message and any other parameter that may be
	 * considered necessary.
	 * <p>
	 * Case 1: An Administrator deletes a Spam Word. No exception is expected.<br>
	 * Case 2: A User tries to delete a Spam Word. An <code>IllegalArgumentException</code> is expected.<br>
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
	 * Template for testing the deletion of Spam Word.
	 * <p>
	 * This method defines the template used for the tests that check the deletion of Spam Word in the system.
	 * 
	 * @param actor
	 *            The actor that will try to delete the Spam Word.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	protected void templateDelete(final String actor, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(actor);
			final List<SpamWord> spamWords = new ArrayList<SpamWord>(this.spamWordService.findAll());
			final SpamWord res = spamWords.get(0);
			this.spamWordService.delete(res);
			Assert.isTrue(!this.spamWordService.findAll().contains(res));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
