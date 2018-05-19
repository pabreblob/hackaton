
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AnnouncementRepository;
import domain.Announcement;
import domain.SpamWord;
import domain.User;

@Service
@Transactional
public class AnnouncementService {

	@Autowired
	private AnnouncementRepository	announcementRepository;

	@Autowired
	private UserService				userService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SpamWordService			spamWordService;

	@Autowired
	private Validator				validator;


	public Announcement create() {
		Announcement res;
		res = new Announcement();
		final User creator = this.userService.findByPrincipal();
		res.setCreator(creator);
		final Collection<User> attendants = new ArrayList<User>();
		res.setAttendants(attendants);
		return res;
	}

	public Announcement findOne(final int announcementId) {
		Announcement res;
		res = this.announcementRepository.findOne(announcementId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Announcement> findAll() {
		return this.announcementRepository.findAll();
	}

	public Announcement save(final Announcement announcement) {
		Assert.notNull(announcement);
		Assert.notNull(announcement.getCreator());
		Assert.isTrue(announcement.getCreator().equals(this.userService.findByPrincipal()));
		Assert.isTrue(!announcement.getAttendants().contains(announcement.getCreator()));
		final LocalDateTime now = new LocalDateTime();
		final LocalDateTime moment = new LocalDateTime(announcement.getMoment());
		Assert.isTrue(moment.isAfter(now));
		if (announcement.getId() != 0)
			Assert.isTrue(announcement.getAttendants().isEmpty());
		final String title = announcement.getTitle().toLowerCase();
		final String origin = announcement.getOrigin().toLowerCase();
		final String destination = announcement.getDestination().toLowerCase();
		final String description = announcement.getDescription() != null ? announcement.getDescription().toLowerCase() : "";
		final Collection<SpamWord> tw = this.spamWordService.findAll();
		boolean taboow = false;
		for (final SpamWord word : tw) {
			taboow = title.matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= description.matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= origin.matches(".*\\b" + word.getWord() + "\\b.*");
			taboow |= destination.matches(".*\\b" + word.getWord() + "\\b.*");
			if (taboow) {
				announcement.setMarked(true);
				break;
			}
		}
		Announcement res;
		res = this.announcementRepository.save(announcement);
		return res;
	}

	public void join(final int announcementId) {
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		final Announcement a = this.announcementRepository.findOne(announcementId);
		Assert.notNull(a);
		Assert.isTrue(!a.getCreator().equals(this.userService.findByPrincipal()));
		Assert.isTrue(!a.getAttendants().contains(this.userService.findByPrincipal()));
		Assert.isTrue(!this.userService.findByPrincipal().getAnnouncements().contains(a));
		Assert.isTrue(a.getSeats() > a.getAttendants().size());
		final LocalDateTime now = new LocalDateTime();
		final LocalDateTime moment = new LocalDateTime(a.getMoment());
		Assert.isTrue(Hours.hoursBetween(now, moment).getHours() >= 2);
		a.getAttendants().add(this.userService.findByPrincipal());
		this.userService.findByPrincipal().getAnnouncements().add(a);
	}

	public void dropOut(final int announcementId) {
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		final Announcement a = this.announcementRepository.findOne(announcementId);
		Assert.notNull(a);
		Assert.isTrue(!a.getCreator().equals(this.userService.findByPrincipal()));
		Assert.isTrue(a.getAttendants().contains(this.userService.findByPrincipal()));
		Assert.isTrue(this.userService.findByPrincipal().getAnnouncements().contains(a));
		final LocalDateTime now = new LocalDateTime();
		final LocalDateTime moment = new LocalDateTime(a.getMoment());
		Assert.isTrue(now.isBefore(moment));
		a.getAttendants().remove(this.userService.findByPrincipal());
		this.userService.findByPrincipal().getAnnouncements().remove(a);
		this.messageService.notify(a.getCreator(), "A user has dropped out", "The user " + this.userService.findByPrincipal().getUserAccount().getUsername() + " has dropped out from your announcement \"" + a.getTitle() + "\".");
	}

	public void cancel(final int announcementId) {
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		final Announcement a = this.announcementRepository.findOne(announcementId);
		Assert.notNull(a);
		Assert.isTrue(a.getCreator().equals(this.userService.findByPrincipal()));
		final LocalDateTime now = new LocalDateTime();
		final LocalDateTime moment = new LocalDateTime(a.getMoment());
		Assert.isTrue(now.isBefore(moment));
		a.setCancelled(true);
		for (final User u : a.getAttendants())
			this.messageService.notify(u, "An announcement has been cancelled", "The announcement \"" + a.getTitle() + "\" has been cancelled by its creator. Sorry for the inconvenience.");
	}

	public void delete(final int announcementId) {
		Assert.notNull(announcementId);
		Assert.isTrue(announcementId != 0);
		final Announcement a = this.announcementRepository.findOne(announcementId);
		Assert.notNull(a);
		Assert.isTrue(a.getCreator().equals(this.userService.findByPrincipal()));
		final LocalDateTime now = new LocalDateTime();
		final LocalDateTime moment = new LocalDateTime(a.getMoment());
		Assert.isTrue(now.isBefore(moment));
		Assert.isTrue(a.getAttendants().isEmpty());
		this.announcementRepository.delete(a);
	}

	public Collection<Announcement> getCreatedAnnouncementsByUserId(final int userId, final Pageable pageable) {
		Assert.notNull(userId);
		Assert.isTrue(userId != 0);
		return this.announcementRepository.findCreatedAnnouncementsByUserId(userId, pageable).getContent();
	}

	public Integer countCreatedAnnouncementsByUserId(final int userId) {
		Assert.notNull(userId);
		Assert.isTrue(userId != 0);
		return this.announcementRepository.countCreatedAnnouncementsByUserId(userId);
	}

	public Collection<Announcement> getJoinedAnnouncementsByUserId(final int userId, final Pageable pageable) {
		Assert.notNull(userId);
		Assert.isTrue(userId != 0);
		return this.announcementRepository.findJoinedAnnouncementsByUserId(userId, pageable).getContent();
	}

	public Integer countJoinedAnnouncementsByUserId(final int userId) {
		Assert.notNull(userId);
		Assert.isTrue(userId != 0);
		return this.announcementRepository.countJoinedAnnouncementsByUserId(userId);
	}

	public Announcement reconstruct(final Announcement announcement, final BindingResult binding) {
		Announcement res;
		res = announcement;
		res.setAttendants(new ArrayList<User>());
		res.setCancelled(false);
		res.setMarked(false);
		res.setCreator(this.userService.findByPrincipal());

		this.validator.validate(res, binding);
		return res;
	}
}
