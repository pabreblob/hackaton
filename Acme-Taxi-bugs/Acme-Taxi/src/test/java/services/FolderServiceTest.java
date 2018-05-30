
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;


	/**
	 * Tests the creation of a folder.
	 * <p>
	 * This method is used to test the creation of a new folder.
	 * <p>
	 * 14.3. An actor who is authenticated must be able to: Manage the messages in their folders, which includes deleting them and marking them as spam.
	 */
	@Test
	public void testCreate() {
		super.authenticate("user1");
		final Folder f = this.folderService.create();
		Assert.notNull(f);
		super.unauthenticate();
	}

	/**
	 * Tests the creation of the default folders.
	 * <p>
	 * This method tests the creation of of the default folders which all actors must have.
	 * <p>
	 * 14.3. An actor who is authenticated must be able to: Manage the messages in their folders, which includes deleting them and marking them as spam.
	 */
	@Test
	public void testCreationOfDefaultFolders() {
		super.authenticate("user1");
		final Collection<Folder> f = this.folderService.defaultFolders();
		Assert.isTrue(f.size() == 5);
		super.unauthenticate();
	}

	/**
	 * Tests the saving of a new folder.
	 * <p>
	 * This method tests the creation and later saving of a folder.
	 * <p>
	 * 14.3. An actor who is authenticated must be able to: Manage the messages in their folders, which includes deleting them and marking them as spam.
	 * 
	 * Case 1: User1 creates a folder. No exception is expected.
	 * 
	 * Case 2: User2 creates a folder with the same name. No exception is expected.
	 * 
	 * Case 3: User1 creates a folder with the same name. There cannot be two folders with the same name. An IllegalArgumentException is expected.
	 * 
	 * Case 4: User1 creates a folder with the name in blank. The name of the folder cannot be blank. An IllegalArgumentException is expected.
	 * 
	 * Case 5: User1 creates a folder with the same name as a default folder. Cannot be two folders with the same name. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverSaveFolder() {
		final Object testingData[][] = {
			{
				"user1", "Folder1", null
			}, {
				"user2", "Folder1", null
			}, {
				"user1", "Folder1", IllegalArgumentException.class
			}, {
				"user1", "", IllegalArgumentException.class
			}, {
				"user1", "In box", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSaveFolder((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the saving of folders.
	 * <p>
	 * This method defines the template used for the tests that check the saving of folders.
	 * 
	 * @param username
	 *            The username of the actor that logs in.
	 * @param folderName
	 *            The name of the folder the user is creating.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	private void templateSaveFolder(final String username, final String folderName, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(username);
			final Folder f = this.folderService.createNewFolder(folderName);
			Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(f));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();
		this.checkExceptions(expected, caught);
	}

	/**
	 * This method tests the method which takes the folders in the root of the hierarchy.
	 * <p>
	 * This method is very useful when you want to list the folders.
	 * <p>
	 * 14.3. An actor who is authenticated must be able to: Manage the messages in their folders, which includes deleting them and marking them as spam.
	 */
	@Test
	public void testMainFolders() {
		super.authenticate("user1");
		final Collection<Folder> f = this.folderService.mainFolders();
		Assert.isTrue(f.size() >= 5);
		super.unauthenticate();
	}

	/**
	 * Tests the deletion of a folder
	 * <p>
	 * This method tests the deletion of a given folder
	 * <p>
	 * 14.3. An actor who is authenticated must be able to: Manage the messages in their folders, which includes deleting them and marking them as spam.
	 * 
	 * Case 1: Deleting a custom folder. No exception is expected.
	 * 
	 * Case 2: Deleting a folder which does not exist. An IllegalArgumentException is expected.
	 * 
	 * Case 3: Deleting a default folder. Actors cannot edit or delete them. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverDeleteFolder() {
		final Object testingData[][] = {
			{
				"custom", null
			}, {
				"testing", IllegalArgumentException.class
			}, {
				"In box", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteFolder((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/**
	 * Template for testing the deletion of a folder.
	 * <p>
	 * This method defines the template used for the tests that check the deletion of a folder.
	 * 
	 * @param folder
	 *            The folder the actor wants to delete.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	private void templateDeleteFolder(final String folder, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate("user1");
			final Folder f = this.folderService.findFolderByNameAndActor(folder);
			this.folderService.delete(f);
			Assert.isTrue(!this.actorService.findByPrincipal().getFolders().contains(f));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}

	/**
	 * Tests the moving of a folder.
	 * <p>
	 * This method is used to test the moving of a folder.
	 * <p>
	 * 14.3. An actor who is authenticated must be able to: Manage the messages in their folders, which includes deleting them and marking them as spam.
	 * 
	 * Case 1: Moving the folder 'custom'. It will be a child of the folder 'In box'. No exception is expected.
	 * 
	 * Case 2: Moving the folder 'In box'. It will be a child of the folder 'Out box'. A user cannot edit a default folder. An IllegalArgumentException is expected.
	 */
	@Test
	public void driverMoveFolder() {
		final Object testingData[][] = {
			{
				"custom", "In box", null
			}, {
				"In box", "Out box", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateMoveFolder((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the moving of folders.
	 * <p>
	 * This method defines the template used for the tests that check the moving of folders.
	 * 
	 * @param toMoveName
	 *            The name of the folder we want to move.
	 * @param newParentName
	 *            The name of the folder which will be the new parent of the folder we want to move.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	private void templateMoveFolder(final String toMoveName, final String newParentName, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate("user1");
			final Folder toMove = this.folderService.findFolderByNameAndActor(toMoveName);
			final Folder newParent = this.folderService.findFolderByNameAndActor(newParentName);
			this.folderService.changeParent(toMove, newParent);
			Assert.isTrue(newParent.getChildren().contains(toMove));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		super.unauthenticate();
	}

	/**
	 * Tests the renaming of a folder.
	 * <p>
	 * This method is used to test the renaming of a folder.
	 * <p>
	 * 14.3. An actor who is authenticated must be able to: Manage the messages in their folders, which includes deleting them and marking them as spam.
	 * 
	 * Case 1: User1 tries to rename "custom" to "customRenamed". No exception is expected.
	 * 
	 * Case 2: User1 tries to rename "In box" to "InboxRenamed". An IllegalArgumentException is expected.
	 */
	@Test
	public void testRename() {
		final Object testingData[][] = {
			{
				"custom", "customRenamed", null
			}, {
				"In box", "InboxRenamed", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRenameFolder((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * Template for testing the renaming of folders.
	 * <p>
	 * This method defines the template used for the tests that check the renaming of folders.
	 * 
	 * @param folderName
	 *            The name of the folder we want to rename.
	 * @param newName
	 *            The new name of the folder.
	 * @param expected
	 *            The expected exception to be thrown. Use <code>null</code> if no exception is expected.
	 */
	private void templateRenameFolder(final String folderName, final String newName, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate("user1");
			final Folder f = this.folderService.findFolderByNameAndActor(folderName);
			final Folder n = this.folderService.create();
			n.setName(newName);
			n.setChildren(f.getChildren());
			n.setVersion(f.getVersion());
			n.setId(f.getId());
			n.setMessages(f.getMessages());
			n.setParent(f.getParent());
			this.folderService.saveRename(n);
			Assert.isTrue(this.folderService.findFolderByNameAndActor(newName) != null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		super.unauthenticate();

	}

}
