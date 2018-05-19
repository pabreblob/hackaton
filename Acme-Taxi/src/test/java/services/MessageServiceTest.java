
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Folder;
import domain.Mechanic;
import domain.Message;
import domain.Message.Priority;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private MechanicService	mechanicService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	/**
	 * Tests the creation of messages.
	 * <p>
	 * This method is used to test the creation of empty messages before passing them to the corresponding views.
	 * <p>
	 * 14.2. An actor who is authenticated must be able to: Send a message to another user.
	 * 
	 */
	@Test
	public void testCreateMessage() {
		super.authenticate("mechanic1");
		final Message res = this.messageService.create();
		Assert.notNull(res);
		super.authenticate(null);
	}

	/**
	 * Tests the saving of messages.
	 * <p>
	 * This method tests the creation and later saving of messages as it would be done by a user in the corresponding views.
	 * <p>
	 * 14.2. An actor who is authenticated must be able to: Send a message to another user.
	 * 
	 * Case 1: User1 creates a message. No exception is expected.
	 * 
	 * Case 2: Customer1 creates a message. No exception is expected.
	 * 
	 * Case 3: An unauthenticated user tries to create a message. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverSaveMessage() {
		final Object testingData[][] = {
			{
				"mechanic1", null
			}, {
				"user1", null
			}, {
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSaveMessage((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the saving of messages.
	 * <p>
	 * This method defines the template used for the tests that check the saving of messages.
	 * 
	 * @param username
	 *            The username of the user that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateSaveMessage(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Message res = this.messageService.create();
			res.setSubject("Subject");
			res.setBody("Hello");
			res.setPriority(Priority.NEUTRAL);
			final Collection<Actor> recipients = new ArrayList<Actor>();
			final List<Mechanic> rangers = new ArrayList<Mechanic>(this.mechanicService.findAll());
			recipients.add(rangers.get(0));
			res.setRecipients(recipients);
			final Message saved = this.messageService.save(res);
			final Message found = this.messageService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			final Collection<Message> sender = this.folderService.findFolderByNameAndActor("Out box").getMessages();
			Assert.isTrue(sender != null && sender.contains(saved));
			Assert.isTrue(found.getFolder().getName().equals("Out box"));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the broadcasting of messages.
	 * <p>
	 * This method tests the creation and later broadcasting of messages as it would be done by the admin in the corresponding views.
	 * <p>
	 * 13.2. An actor who is authenticated as an administrator must be able to: Broadcast a message to the actors of the system.
	 * 
	 * Case 1: The admin broadcasts a message. No exception is expected.
	 * 
	 * Case 2: An unauthenticated user tries to broadcast a message. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverBroadcastMessage() {
		final Object testingData[][] = {
			{
				"admin", null
			}, {
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateBroadcastMessage((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the broadcasting of messages.
	 * <p>
	 * This method defines the template used for the tests that check the broadcasting of messages.
	 * 
	 * @param username
	 *            The username of the actor that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateBroadcastMessage(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Message res = this.messageService.create();
			res.setSubject("Subject");
			res.setBody("Hello");
			res.setPriority(Priority.NEUTRAL);
			final Collection<Actor> recipients = new ArrayList<Actor>();
			final List<Mechanic> rangers = new ArrayList<Mechanic>(this.mechanicService.findAll());
			recipients.add(rangers.get(0));
			recipients.add(rangers.get(1));
			res.setRecipients(recipients);
			final Message saved = this.messageService.broadcast(res);
			final Message found = this.messageService.findOne(saved.getId());
			Assert.isTrue(found.equals(saved));
			final Collection<Message> sender = this.folderService.findFolderByNameAndActor("Out box").getMessages();
			Assert.isTrue(sender != null && sender.contains(saved));
			Assert.isTrue(found.getFolder().getName().equals("Out box"));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests notifications.
	 * <p>
	 * This method tests the creation and later notification of messages.
	 * <p>
	 * 15.7 & 15.8.
	 * 
	 * Case 1: A notification is sent to the user1. No exception is expected.
	 * 
	 * Case 2: A notification is sent to the driver1. No exception is expected.
	 * 
	 * Case 3: A notification is sent to an unauthenticated actor. An IndexOutOfBoundsException is expected.
	 */
	@Test
	public void driverNotifyMessage() {
		final Object testingData[][] = {
			{
				"user1", null
			}, {
				"driver1", null
			}, {
				null, IndexOutOfBoundsException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateNotifyMessage((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing notifications.
	 * <p>
	 * This method defines the template used for the tests that check notifications.
	 * 
	 * @param username
	 *            The username of the actor that will receive the notification.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateNotifyMessage(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final Actor a = new ArrayList<Actor>(this.actorService.findByUsername(username)).get(0);
			final Message res = this.messageService.notify(a, "Test", "Notification test");
			final Message found = this.messageService.findOne(res.getId());
			Assert.isTrue(found.equals(res));
			final Collection<Message> notified = this.folderService.findFolderByNameAndActor(a, "Notification box").getMessages();
			Assert.isTrue(notified != null && notified.contains(res));
			Assert.isTrue(found.getFolder().getName().equals("Notification box"));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of messages that are not in the Trash box.
	 * <p>
	 * This method tests the creation and later deletion of messages as it would be done by a user in the corresponding views.
	 * <p>
	 * 14.3. An actor who is authenticated must be able to: Manage the messages in their folders, which includes deleting them and marking them as spam.
	 * 
	 * Case 1: Mechanic1 deletes a message. No exception is expected.
	 * 
	 * Case 2: User1 deletes a message. No exception is expected.
	 * 
	 * Case 3: An unauthenticated user tries to delete a message. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverDeleteMessageNoTrash() {
		final Object testingData[][] = {
			{
				"mechanic1", null
			}, {
				"user1", null
			}, {
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteMessageNoTrash((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the deletion of messages that are not in the Trash box.
	 * <p>
	 * This method defines the template used for the tests that check the deletion of messages that are not in the Trash box.
	 * 
	 * @param username
	 *            The username of the actor that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateDeleteMessageNoTrash(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Message res = this.messageService.create();
			res.setSubject("Subject");
			res.setBody("Hello");
			res.setPriority(Priority.NEUTRAL);
			final Collection<Actor> recipients = new ArrayList<Actor>();
			final List<Mechanic> rangers = new ArrayList<Mechanic>(this.mechanicService.findAll());
			recipients.add(rangers.get(0));
			res.setRecipients(recipients);
			final Message saved = this.messageService.save(res);
			final Folder outf = this.folderService.findFolderByNameAndActor("Out box");
			this.messageService.delete(saved);
			final Collection<Message> trashf = this.folderService.findFolderByNameAndActor("Trash box").getMessages();
			Assert.isTrue(!outf.getMessages().contains(saved));
			Assert.isTrue(trashf != null && trashf.contains(saved));
			final Message found = this.messageService.findOne(saved.getId());
			Assert.isTrue(found.getFolder().getName().equals("Trash box"));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the deletion of messages that are in the Trash box.
	 * <p>
	 * This method tests the creation and later deletion of messages from the Trash box as it would be done by a user in the corresponding views.
	 * <p>
	 * 14.3. An actor who is authenticated must be able to: Manage the messages in their folders, which includes deleting them and marking them as spam.
	 * 
	 * Case 1: Mechanic1 permanently deletes a message. No exception is expected.
	 * 
	 * Case 2: User1 permanently deletes a message. No exception is expected.
	 * 
	 * Case 3: An unauthenticated user tries to permanently delete a message. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverDeleteMessageTrash() {
		final Object testingData[][] = {
			{
				"mechanic1", null
			}, {
				"user1", null
			}, {
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteMessageTrash((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the deletion of messages that are in the Trash box.
	 * <p>
	 * This method defines the template used for the tests that check the deletion of messages from the Trash box.
	 * 
	 * @param username
	 *            The username of the actor that logs in.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateDeleteMessageTrash(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Message res = this.messageService.create();
			res.setSubject("Subject");
			res.setBody("Hello");
			res.setPriority(Priority.NEUTRAL);
			final Collection<Actor> recipients = new ArrayList<Actor>();
			final List<Mechanic> rangers = new ArrayList<Mechanic>(this.mechanicService.findAll());
			recipients.add(rangers.get(0));
			res.setRecipients(recipients);
			final Message saved = this.messageService.save(res);
			final Folder trashf = this.folderService.findFolderByNameAndActor("Trash box");
			this.messageService.delete(saved);
			final Message found = this.messageService.findOne(saved.getId());
			Assert.isTrue(found != null);
			Assert.isTrue(found.getFolder().getName().equals("Trash box"));
			this.messageService.delete(found);
			Assert.isTrue(!this.messageService.findAll().contains(found));
			Assert.isTrue(trashf != null && !trashf.getMessages().contains(found));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	/**
	 * Tests the moving of messages.
	 * <p>
	 * This method tests the creation and later moving of messages as it would be done by a user in the corresponding views.
	 * <p>
	 * 14.3. An actor who is authenticated must be able to: Manage the messages in their folders, which includes deleting them and marking them as spam.
	 * 
	 * Case 1: Mechanic1 moves a message to their Notification box. No exception is expected.
	 * 
	 * Case 2: Mechanic1 moves a message to their Spam box. No exception is expected.
	 * 
	 * Case 2: User1 moves a message to their Notification box. No exception is expected.
	 * 
	 * Case 3: An unauthenticated user tries to move a message. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverMoveMessage() {
		final Object testingData[][] = {
			{
				"mechanic1", "Notification box", null
			}, {
				"mechanic1", "Spam box", null
			}, {
				"user1", "Notification box", null
			}, {
				null, "Some box", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateMoveMessage((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the moving of messages.
	 * <p>
	 * This method defines the template used for the tests that check the moving of messages.
	 * 
	 * @param username
	 *            The username of the actor that logs in.
	 * @param targetFolder
	 *            The target folder.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	public void templateMoveMessage(final String username, final String targetFolder, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(username);
			final Message res = this.messageService.create();
			res.setSubject("Subject");
			res.setBody("Hello");
			res.setPriority(Priority.NEUTRAL);
			final Collection<Actor> recipients = new ArrayList<Actor>();
			final List<Mechanic> rangers = new ArrayList<Mechanic>(this.mechanicService.findAll());
			recipients.add(rangers.get(0));
			res.setRecipients(recipients);
			final Message saved = this.messageService.save(res);
			final Folder outf = this.folderService.findFolderByNameAndActor("Out box");
			final Folder targetf = this.folderService.findFolderByNameAndActor(targetFolder);
			this.messageService.moveToFolder(saved, targetf);
			Assert.isTrue(!outf.getMessages().contains(saved));
			Assert.isTrue(targetf != null && targetf.getMessages().contains(saved));
			final Message found = this.messageService.findOne(saved.getId());
			Assert.isTrue(found.getFolder().getName().equals(targetFolder));
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
