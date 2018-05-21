
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Announcement;
import domain.Folder;
import domain.Review;
import domain.User;
import forms.UserForm;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository			userRepository;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private FolderService			folderService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private Validator				validator;


	public User create() {
		final User u = new User();

		final UserAccount ua = this.userAccountService.create();
		u.setUserAccount(ua);

		u.setFolders(new ArrayList<Folder>());
		u.getFolders().add(this.folderService.create());
		u.getFolders().add(this.folderService.create());
		u.getFolders().add(this.folderService.create());
		u.getFolders().add(this.folderService.create());
		u.getFolders().add(this.folderService.create());

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.USER);
		authorities.add(auth);
		u.getUserAccount().setAuthorities(authorities);

		return u;
	}

	@SuppressWarnings("deprecation")
	public User save(final User u) {
		Assert.notNull(u);

		int age;
		final LocalDate birth = new LocalDate(u.getBirthdate().getYear() + 1900, u.getBirthdate().getMonth() + 1, u.getBirthdate().getDate());
		final LocalDate now = new LocalDate();
		age = Years.yearsBetween(birth, now).getYears();
		Assert.isTrue(age >= 18);

		if (u.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(u.getUserAccount().getPassword(), null);
			u.getUserAccount().setPassword(hash);
			u.setFolders(new ArrayList<Folder>());
			final Collection<Folder> folders = this.folderService.defaultFolders();
			u.getFolders().addAll(folders);

			final List<Authority> authorities = new ArrayList<Authority>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.DRIVER);
			authorities.add(auth);
			u.getUserAccount().setAuthorities(authorities);
			final UserAccount ua = this.userAccountService.save(u.getUserAccount());
			u.setUserAccount(ua);
		}

		if (u.getPhone() != null && u.getPhone() != "")
			if (!u.getPhone().trim().startsWith("+"))
				u.setPhone("+" + this.configurationService.find().getCountryCode() + " " + u.getPhone().trim());

		final User res = this.userRepository.save(u);

		return res;
	}

	public User findByPrincipal() {
		User res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findUserByUserAccountId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

	public User findUserByUserAccountId(final int UserAccountId) {
		return this.userRepository.findUserByUserAccountId(UserAccountId);
	}

	public Collection<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findOne(final int userId) {
		final User u = this.userRepository.findOne(userId);
		return u;
	}

	public User reconstruct(final UserForm userForm, final BindingResult binding) {

		final User res = this.create();
		res.setName(userForm.getName());
		res.setSurname(userForm.getSurname());
		res.setBirthdate(userForm.getBirthdate());
		res.setPhone(userForm.getPhone());
		res.setEmail(userForm.getEmail());
		res.setSuspicious(false);
		res.setBlockedUsers(new ArrayList<Actor>());
		res.setReviews(new ArrayList<Review>());
		res.setAnnouncements(new ArrayList<Announcement>());
		res.setPhotoUrl(userForm.getPhoto());
		res.setLocation(userForm.getLocation());

		res.getUserAccount().setUsername(userForm.getUsername());
		res.getUserAccount().setPassword(userForm.getPassword());

		this.validator.validate(userForm, binding);
		return res;
	}

	public User reconstructEdition(final User u, final BindingResult binding) {
		final User res = this.findOne(u.getId());

		Assert.isTrue(u.getId() == this.findByPrincipal().getId());
		u.setUserAccount(res.getUserAccount());
		u.setSuspicious(res.isSuspicious());
		u.setFolders(res.getFolders());
		u.setBlockedUsers(res.getBlockedUsers());
		u.setMeanRating(u.getMeanRating());
		u.setReviews(res.getReviews());
		u.setAnnouncements(res.getAnnouncements());

		this.validator.validate(u, binding);

		return u;
	}

	public Collection<User> findUsersReviewable(final Pageable pageable) {
		final Collection<User> res = this.userRepository.findUsersReviewable(this.findByPrincipal().getId(), pageable).getContent();
		final Collection<User> revisados = this.findUsersWithReviewByPrincipal();
		final Collection<User> result = new ArrayList<>(res);
		result.removeAll(revisados);
		result.remove(this.findByPrincipal());

		return result;
	}

	public Collection<User> findUsersReviewable() {
		final Collection<User> res = this.userRepository.findUsersReviewable(this.findByPrincipal().getId());
		final Collection<User> revisados = this.findUsersWithReviewByPrincipal();
		final Collection<User> result = new ArrayList<>(res);
		result.removeAll(revisados);
		result.remove(this.findByPrincipal());

		return result;
	}

	public Collection<User> findUsersWithReviewByPrincipal() {
		return this.userRepository.findUserWithReviewByUserId(this.findByPrincipal().getId());
	}

	public User findUserByReviewId(final int reviewId) {
		return this.userRepository.findUserByReviewId(reviewId);
	}

	public Integer countUsersReviewable() {
		return this.findUsersReviewable().size();
	}

}
