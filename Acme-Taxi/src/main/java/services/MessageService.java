
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Admin;
import domain.Folder;
import domain.Message;
import domain.Message.Priority;
import domain.SpamWord;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private SpamWordService		spamWordService;

	@Autowired
	private AdminService		adminService;

	@Autowired
	private Validator			validator;


	public Message create() {
		Message res;
		res = new Message();
		final Actor sender = this.actorService.findByPrincipal();
		res.setSender(sender);
		final Collection<Actor> recipients = new ArrayList<Actor>();
		res.setRecipients(recipients);
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		res.setFolder(this.folderService.findFolderByNameAndActor("Out box"));
		return res;
	}

	public Message findOne(final int messageId) {
		Message res;
		res = this.messageRepository.findOne(messageId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message save(final Message message) {
		Assert.notNull(message);
		final String body = message.getBody().toLowerCase();
		final String subject = message.getSubject().toLowerCase();
		final Collection<SpamWord> tw = this.spamWordService.findAll();
		boolean taboow = false;
		for (final SpamWord word : tw) {
			taboow = body.matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= subject.matches(".*\\b" + word.getWord() + "\\b.*");
			if (taboow)
				break;
		}
		final Actor sender = this.actorService.findByPrincipal();
		Assert.isTrue(sender.equals(message.getSender()));
		Assert.isTrue(sender.getFolders().contains(message.getFolder()));
		Assert.isTrue(message.getFolder().getName().equals("Out box"));
		message.setMoment(new Date(System.currentTimeMillis() - 1000));
		message.setChecked(true);
		Message res;
		res = this.messageRepository.save(message);
		if (taboow)
			sender.setSuspicious(true);
		final Collection<Actor> recipients = message.getRecipients();
		res.getFolder().getMessages().add(res);
		message.setChecked(false);
		for (final Actor a : recipients)
			for (final Folder f : a.getFolders())
				if (!taboow && !a.getBlockedUsers().contains(sender) && f.getName().equals("In box")) {
					message.setFolder(f);
					f.getMessages().add(this.messageRepository.save(message));
					break;
				} else if ((taboow || a.getBlockedUsers().contains(sender)) && f.getName().equals("Spam box")) {
					message.setFolder(f);
					message.setChecked(true);
					f.getMessages().add(this.messageRepository.save(message));
					break;
				}
		return res;
	}

	public Message notify(final Actor a, final String subject, final String body) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		final Message message = new Message();
		Assert.notNull(message);
		final Actor sender = new ArrayList<Admin>(this.adminService.findAll()).get(0);
		Assert.notNull(sender);
		message.setMoment(new Date(System.currentTimeMillis() - 1000));
		message.setSender(sender);
		final Collection<Actor> recipients = new ArrayList<Actor>();
		recipients.add(a);
		message.setRecipients(recipients);
		message.setPriority(Priority.NEUTRAL);
		message.setSubject(subject);
		message.setBody(body);
		final Folder f = this.folderService.findFolderByNameAndActor(a, "Notification box");
		Assert.notNull(f);
		message.setFolder(f);
		Message res;
		res = this.messageRepository.save(message);
		res.getFolder().getMessages().add(res);
		return res;
	}

	public Message broadcast(final Message message) {
		Assert.notNull(message);
		final Actor sender = this.actorService.findByPrincipal();
		message.setSender(sender);
		message.setMoment(new Date(System.currentTimeMillis() - 1000));
		message.setChecked(true);
		Message res;
		res = this.messageRepository.save(message);
		final Collection<Actor> recipients = res.getRecipients();
		Assert.isTrue(sender.equals(message.getSender()));
		Assert.isTrue(sender.getFolders().contains(message.getFolder()));
		Assert.isTrue(message.getFolder().getName().equals("Out box"));
		res.getFolder().getMessages().add(res);
		message.setChecked(false);
		for (final Actor a : recipients)
			for (final Folder f : a.getFolders())
				if (f.getName().equals("Notification box")) {
					message.setFolder(f);
					f.getMessages().add(this.messageRepository.save(message));
					break;
				}
		return res;
	}

	public void delete(final Message message) {
		Assert.notNull(message);
		final Folder folder = message.getFolder();
		Assert.notNull(folder);
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(folder.getId() != 0);
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(folder));
		Assert.isTrue(this.messageRepository.exists(message.getId()));
		Assert.isTrue(folder.getMessages().contains(message));
		if (folder.getName().equals("Trash box")) {
			folder.getMessages().remove(message);
			this.messageRepository.delete(message.getId());
		} else {
			final Folder trashf = this.folderService.findFolderByNameAndActor("Trash box");
			Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(trashf));
			message.setChecked(true);
			folder.getMessages().remove(message);
			message.setFolder(trashf);
			trashf.getMessages().add(message);
		}
	}

	public void markAsSpam(final Message message) {
		Assert.notNull(message);
		final Folder folder = message.getFolder();
		Assert.notNull(folder);
		Assert.isTrue(!folder.getName().equals("Spam box") && !folder.getName().equals("Out box"));
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(folder.getId() != 0);
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(folder));
		Assert.isTrue(this.messageRepository.exists(message.getId()));
		Assert.isTrue(folder.getMessages().contains(message));
		final Folder spamf = this.folderService.findFolderByNameAndActor("Spam box");
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(spamf));
		message.setChecked(true);
		folder.getMessages().remove(message);
		message.setFolder(spamf);
		spamf.getMessages().add(message);
	}

	public void moveToFolder(final Message message, final Folder target) {
		Assert.notNull(message);
		final Folder source = message.getFolder();
		Assert.notNull(source);
		Assert.notNull(target);
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(source.getId() != 0);
		Assert.isTrue(target.getId() != 0);
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(source));
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(target));
		Assert.isTrue(!target.getName().equals("Spam box") && !target.getName().equals("Trash box"));
		Assert.isTrue(this.messageRepository.exists(message.getId()));
		Assert.isTrue(source.getMessages().contains(message));
		source.getMessages().remove(message);
		message.setFolder(target);
		target.getMessages().add(message);
	}

	public Collection<Message> findMessagesByFolderId(final int folderId, final Pageable pageable) {
		Assert.isTrue(folderId != 0);
		final Collection<Message> res = this.messageRepository.findMessagesByFolderId(folderId, pageable).getContent();
		return res;
	}

	public Integer countMessagesByFolderId(final int folderId) {
		Assert.isTrue(folderId != 0);
		final Integer res = this.messageRepository.countMessagesByFolderId(folderId);
		return res;
	}

	public Message reconstruct(final Message message) {
		Message result;

		result = message;
		result.setSender(this.actorService.findByPrincipal());
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setFolder(this.folderService.findFolderByNameAndActor("Out box"));

		return result;
	}

	public Message reconstructAdmin(final Message message, final BindingResult binding) {
		Message result;

		result = message;
		result.setSender(this.actorService.findByPrincipal());
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setFolder(this.folderService.findFolderByNameAndActor("Out box"));
		result.setRecipients(this.actorService.findAll());

		this.validator.validate(result, binding);
		return result;
	}

	public Integer countUnreadMessages(final int folderId) {
		Assert.notNull(folderId);
		Assert.isTrue(folderId != 0);
		return this.messageRepository.countUnreadMessages(folderId);
	}

	public Integer countTotalUnreadMessages() {
		final Actor a = this.actorService.findByPrincipal();
		Assert.notNull(a);
		final Integer res = this.messageRepository.countTotalUnreadMessages(a.getId());
		return res;
	}

	public void checkMessage(final int messageId) {
		Assert.notNull(messageId);
		Assert.isTrue(messageId != 0);
		final Message message = this.findOne(messageId);
		Assert.isTrue(this.actorService.findByPrincipal().getFolders().contains(message.getFolder()));
		message.setChecked(true);
	}
}
