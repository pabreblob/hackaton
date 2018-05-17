
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.DriverRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Driver;
import domain.Folder;
import domain.Review;
import forms.DriverForm;

@Service
@Transactional
public class DriverService {

	@Autowired
	private DriverRepository		driverRepository;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private Validator				validator;
	@Autowired
	private FolderService			folderService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private UserService				userService;


	public DriverService() {
		super();
	}

	public Driver create() {
		final Driver d = new Driver();

		final UserAccount ua = this.userAccountService.create();
		d.setUserAccount(ua);

		d.setFolders(new ArrayList<Folder>());
		d.getFolders().add(this.folderService.create());
		d.getFolders().add(this.folderService.create());
		d.getFolders().add(this.folderService.create());
		d.getFolders().add(this.folderService.create());
		d.getFolders().add(this.folderService.create());

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.DRIVER);
		authorities.add(auth);
		d.getUserAccount().setAuthorities(authorities);

		return d;
	}

	@SuppressWarnings("deprecation")
	public Driver save(final Driver d) {
		Assert.notNull(d);

		int age;
		final LocalDate birth = new LocalDate(d.getBirthdate().getYear() + 1900, d.getBirthdate().getMonth() + 1, d.getBirthdate().getDate());
		final LocalDate now = new LocalDate();
		age = Years.yearsBetween(birth, now).getYears();
		Assert.isTrue(age >= 18);

		if (d.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(d.getUserAccount().getPassword(), null);
			d.getUserAccount().setPassword(hash);
			d.setFolders(new ArrayList<Folder>());
			final Collection<Folder> folders = this.folderService.defaultFolders();
			d.getFolders().addAll(folders);

			final List<Authority> authorities = new ArrayList<Authority>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.DRIVER);
			authorities.add(auth);
			d.getUserAccount().setAuthorities(authorities);
			final UserAccount ua = this.userAccountService.save(d.getUserAccount());
			d.setUserAccount(ua);
		}

		if (d.getPhone() != null && d.getPhone() != "")
			if (!d.getPhone().trim().startsWith("+"))
				d.setPhone("+" + this.configurationService.find().getCountryCode() + " " + d.getPhone().trim());

		final Driver res = this.driverRepository.save(d);
		return res;
	}

	public Driver findOne(final int driverId) {
		final Driver res = this.driverRepository.findOne(driverId);
		Assert.notNull(res);
		return res;
	}

	//	public Collection<User> findAll() {
	//		final Collection<User> res;
	//		res = this.userRepository.findAll();
	//		return res;
	//	}

	public Driver findByDriverByAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Driver res = this.driverRepository.findDriverByUserAccountId(userAccountId);
		return res;
	}

	public Driver findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Driver res = this.driverRepository.findDriverByUserAccountId(u.getId());
		return res;
	}

	public Driver reconstruct(final DriverForm driverForm, final BindingResult binding) {
		final Driver res = this.create();
		res.setName(driverForm.getName());
		res.setSurname(driverForm.getSurname());
		res.setBirthdate(driverForm.getBirthdate());
		res.setPhone(driverForm.getPhone());
		res.setEmail(driverForm.getEmail());
		res.setSuspicious(false);
		res.setBlockedUsers(new ArrayList<Actor>());
		res.setReviews(new ArrayList<Review>());
		res.setPhotoUrl(driverForm.getPhoto());
		res.setLocation(driverForm.getLocation());

		res.setIdNumber(driverForm.getIdNumber());
		res.setNationality(driverForm.getNationality());
		res.getUserAccount().setUsername(driverForm.getUsername());
		res.getUserAccount().setPassword(driverForm.getPassword());

		this.validator.validate(driverForm, binding);
		return res;
	}

	public Driver reconstructEdition(final Driver d, final BindingResult binding) {
		final Driver res = this.driverRepository.findOne(d.getId());

		Assert.isTrue(d.getId() == this.findByPrincipal().getId());
		d.setUserAccount(res.getUserAccount());
		d.setSuspicious(res.isSuspicious());
		d.setFolders(res.getFolders());
		d.setBlockedUsers(res.getBlockedUsers());
		d.setMeanRating(d.getMeanRating());
		d.setReviews(res.getReviews());

		this.validator.validate(d, binding);

		return d;
	}

	public Collection<Driver> findDriversReviewable(final Pageable pageable) {
		final Collection<Driver> res = this.driverRepository.findDriversReviewable(this.userService.findByPrincipal().getId(), pageable).getContent();
		final Collection<Driver> revisados = this.findDriversWithReviewByPrincipal();
		final Collection<Driver> result = new ArrayList<>(res);
		result.removeAll(revisados);

		return result;
	}

	public Collection<Driver> findDriversReviewable() {
		final Collection<Driver> res = this.driverRepository.findDriversReviewable(this.userService.findByPrincipal().getId());
		final Collection<Driver> revisados = this.findDriversWithReviewByPrincipal();
		final Collection<Driver> result = new ArrayList<>(res);
		result.removeAll(revisados);

		return result;
	}

	public Collection<Driver> findDriversWithReviewByPrincipal() {
		return this.driverRepository.findDriverWithReviewByUserId(this.userService.findByPrincipal().getId());
	}

	public Driver findDriverByReviewId(final int reviewId) {
		return this.driverRepository.findDriverByReviewId(reviewId);
	}

	public Integer countDriversReviewable() {
		return this.findDriversReviewable().size();
	}

	Driver findDriverByCarId(final int carId) {
		final Driver d = this.driverRepository.findDriverByCarId(carId);
		Assert.notNull(d);
		return d;
	}
}
