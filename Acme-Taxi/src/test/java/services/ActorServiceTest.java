
package services;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private UserService		userService;


	/**
	 * Tests the banning of an actor.
	 * <p>
	 * Functional Requirement 19: An actor who is authenticated as an admin must be able to:<br>
	 * 2. Ban actors whether they are marked as suspicious or not, deactivating their user account. Banned actors can be unbanned at any moment.
	 * <p>
	 * Case 1: Banning user1. No exception is expected.<br>
	 * Case 2: Banning an admin. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: Banning a non existing actor. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverBan() {
		final Object testingData[][] = {
			{
				"user1", null
			}, {
				"admin", IllegalArgumentException.class
			}, {
				"non-existing", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateBan((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the banning of actor.
	 * <p>
	 * This method defines the template used for the tests that check the banning the actors in the system.
	 * 
	 * @param actor
	 *            The username of the actor who is going to be banned
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	private void templateBan(final String actor, final Class<?> expected) {
		Class<?> caught = null;
		try {
			if (actor.equals("non-existing")) {
				super.authenticate("admin");
				this.actorService.ban(0);
			} else {
				super.authenticate("admin");
				final Actor a = new ArrayList<Actor>(this.actorService.findByUsername(actor)).get(0);
				this.actorService.ban(a.getId());
				final Actor after = new ArrayList<Actor>(this.actorService.findByUsername(actor)).get(0);
				Assert.isTrue(after.getId() == a.getId());
				Assert.isTrue(a.getUserAccount().isBanned());
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the banning and unbanning of an actor.
	 * <p>
	 * Functional Requirement 19: An actor who is authenticated as an admin must be able to:<br>
	 * 2. Ban actors whether they are marked as suspicious or not, deactivating their user account. Banned actors can be unbanned at any moment.
	 * <p>
	 * Case 1: Banning and unbanning user1. No exception is expected.<br>
	 * Case 2: Banning and unbanning admin. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: Unbanning a non existing actor. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverBanAndUnban() {
		final Object testingData[][] = {
			{
				"user1", null
			}, {
				"admin", IllegalArgumentException.class
			}, {
				"non-existing", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateBanAndUnban((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the banning and unbanning of actor.
	 * <p>
	 * This method defines the template used for the tests that check the banning and unbanning the actors in the system.
	 * 
	 * @param actor
	 *            The username of the actor who is going to be banned and unbanned
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	private void templateBanAndUnban(final String actor, final Class<?> expected) {
		Class<?> caught = null;
		try {
			if (actor.equals("non-existing")) {
				super.authenticate("admin");
				this.actorService.unban(0);
			} else {
				super.authenticate("admin");
				final Actor banning = new ArrayList<Actor>(this.actorService.findByUsername(actor)).get(0);
				this.actorService.ban(banning.getId());
				final Actor banned = new ArrayList<Actor>(this.actorService.findByUsername(actor)).get(0);
				Assert.isTrue(banning.getId() == banned.getId());
				Assert.isTrue(banning.getUserAccount().isBanned());
				this.actorService.unban(banned.getId());
				final Actor unbanned = new ArrayList<Actor>(this.actorService.findByUsername(actor)).get(0);
				Assert.isTrue(banned.getId() == unbanned.getId());
				Assert.isTrue(!unbanned.getUserAccount().isBanned());
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();
		this.checkExceptions(expected, caught);
	}
	/**
	 * Tests the blocking and unblocking of an actor.
	 * <p>
	 * Functional Requirement 14. An actor who is authenticated must be able to:<br>
	 * 6. Block another user. This will make said user unable to send him any messages.<br>
	 * <p>
	 * Case 1: Blocking and unblocking user1. No exception is expected.<br>
	 * Case 2: Blocking and unblocking admin. An <code>IllegalArgumentException</code> is expected.<br>
	 * Case 3: Blocking a non existing actor. An <code>IllegalArgumentException</code> is expected.<br>
	 */
	@Test
	public void driverBlockAndUnblock() {
		final Object testingData[][] = {
			{
				"user1", null
			}, {
				"admin", IllegalArgumentException.class
			}, {
				"non-existing", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateBlockAndUnblock((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the blocking and unblocking of actor.
	 * <p>
	 * This method defines the template used for the tests that check the blocking and unblocking the actors in the system.
	 * 
	 * @param actor
	 *            The username of the actor who is going to be blocking and unblocking
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	private void templateBlockAndUnblock(final String actor, final Class<?> expected) {
		Class<?> caught = null;
		try {
			if (actor.equals("non-existing")) {
				super.authenticate("user1");
				this.actorService.block(0);
			} else {
				super.authenticate("user2");
				final Actor blocking = new ArrayList<Actor>(this.actorService.findByUsername(actor)).get(0);
				this.actorService.block(blocking.getId());
				final Actor blocked = new ArrayList<Actor>(this.actorService.findByUsername(actor)).get(0);
				Assert.isTrue(blocked.getId() == blocking.getId());
				Assert.isTrue(this.userService.findByPrincipal().getBlockedUsers().contains(blocked));
				this.actorService.unblock(blocked.getId());
				final Actor unblocked = new ArrayList<Actor>(this.actorService.findByUsername(actor)).get(0);
				Assert.isTrue(unblocked.getId() == blocked.getId());
				Assert.isTrue(!this.userService.findByPrincipal().getBlockedUsers().contains(blocked));
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();
		this.checkExceptions(expected, caught);
	}
}
