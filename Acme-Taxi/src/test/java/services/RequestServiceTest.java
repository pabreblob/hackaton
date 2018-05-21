
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


	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				"user1", "21", "05", "2020", null
			}, {
				"user1", "01", "06", "1997", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

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

	@Test
	public void driverCreateSaveAndDelete() {
		final Object testingData[][] = {
			{
				"user1", "21", "05", "2020", "user1", null
			}, {
				"user1", "01", "06", "2025", "user2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveAndDelete((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

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

	@Test
	public void driverCreateSaveAndAccept() {
		final Object testingData[][] = {
			{
				"user1", "21", "05", "2020", "driver1", null
			}, {
				"user1", "01", "06", "1997", "driver1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveAndAccept((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	@SuppressWarnings("deprecation")
	private void templateCreateSaveAndAccept(final String creator, final String day, final String month, final String year, final String driver, final Class<?> expected) {
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
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.unauthenticate();
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateSaveAcceptAndCancel() {
		final Object testingData[][] = {
			{
				"user1", "21", "05", "2020", "driver1", null
			}, {
				"user1", "01", "06", "1997", "driver1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveAcceptAndCancel((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	@SuppressWarnings("deprecation")
	private void templateCreateSaveAcceptAndCancel(final String creator, final String day, final String month, final String year, final String driver, final Class<?> expected) {
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
			super.authenticate(creator);
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
