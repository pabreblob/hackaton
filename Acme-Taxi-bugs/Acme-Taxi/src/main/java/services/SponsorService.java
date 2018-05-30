
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Folder;
import domain.Sponsor;
import forms.SponsorForm;

@Service
@Transactional
public class SponsorService {

	@Autowired
	private SponsorRepository		sponsorRepository;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private Validator				validator;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ConfigurationService	configurationService;


	public SponsorService() {
		super();
	}

	public Sponsor create() {
		final Sponsor sponsor = new Sponsor();

		final UserAccount ua = this.userAccountService.create();
		sponsor.setUserAccount(ua);

		sponsor.setFolders(new ArrayList<Folder>());
		sponsor.getFolders().add(this.folderService.create());
		sponsor.getFolders().add(this.folderService.create());
		sponsor.getFolders().add(this.folderService.create());
		sponsor.getFolders().add(this.folderService.create());
		sponsor.getFolders().add(this.folderService.create());

		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.SPONSOR);
		authorities.add(auth);
		sponsor.getUserAccount().setAuthorities(authorities);

		return sponsor;
	}

	@SuppressWarnings("deprecation")
	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		int age;
		final LocalDate birth = new LocalDate(sponsor.getBirthdate().getYear() + 1900, sponsor.getBirthdate().getMonth() + 1, sponsor.getBirthdate().getDate());
		final LocalDate now = new LocalDate();
		age = Years.yearsBetween(birth, now).getYears();
		Assert.isTrue(age >= 18);

		if (sponsor.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(sponsor.getUserAccount().getPassword(), null);
			sponsor.getUserAccount().setPassword(hash);
			sponsor.setFolders(new ArrayList<Folder>());
			final Collection<Folder> folders = this.folderService.defaultFolders();
			sponsor.getFolders().addAll(folders);

			final List<Authority> authorities = new ArrayList<Authority>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.SPONSOR);
			authorities.add(auth);
			sponsor.getUserAccount().setAuthorities(authorities);
			final UserAccount ua = this.userAccountService.save(sponsor.getUserAccount());
			sponsor.setUserAccount(ua);
		} else
			Assert.isTrue(sponsor.equals(this.findByPrincipal()));

		if (sponsor.getPhone() != null && sponsor.getPhone() != "")
			if (!sponsor.getPhone().trim().startsWith("+"))
				sponsor.setPhone("+" + this.configurationService.find().getCountryCode() + " " + sponsor.getPhone().trim());

		final Sponsor res = this.sponsorRepository.save(sponsor);
		return res;
	}

	public Sponsor findOne(final int sponsorId) {
		final Sponsor res = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(res);
		return res;
	}

	//	public Collection<User> findAll() {
	//		final Collection<User> res;
	//		res = this.userRepository.findAll();
	//		return res;
	//	}

	public Sponsor findBySponsorByAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Sponsor res = this.sponsorRepository.findSponsorByUserAccountId(userAccountId);
		return res;
	}

	public Sponsor findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Sponsor res = this.sponsorRepository.findSponsorByUserAccountId(u.getId());
		return res;
	}

	public Sponsor reconstruct(final SponsorForm sponsorForm, final BindingResult binding) {
		final Sponsor res = this.create();
		res.setName(sponsorForm.getName());
		res.setSurname(sponsorForm.getSurname());
		res.setBirthdate(sponsorForm.getBirthdate());
		res.setPhone(sponsorForm.getPhone());
		res.setEmail(sponsorForm.getEmail());
		res.setSuspicious(false);
		res.setBlockedUsers(new ArrayList<Actor>());

		res.setIdNumber(sponsorForm.getIdNumber());
		res.setNationality(sponsorForm.getNationality());
		res.getUserAccount().setUsername(sponsorForm.getUsername());
		res.getUserAccount().setPassword(sponsorForm.getPassword());

		this.validator.validate(sponsorForm, binding);
		return res;
	}

	public Sponsor reconstructEdition(final Sponsor s, final BindingResult binding) {
		final Sponsor res = this.sponsorRepository.findOne(s.getId());

		Assert.isTrue(s.getId() == this.findByPrincipal().getId());
		s.setUserAccount(res.getUserAccount());
		s.setSuspicious(res.isSuspicious());
		s.setFolders(res.getFolders());
		s.setBlockedUsers(res.getBlockedUsers());

		//		res.setName(s.getName());
		//		res.setSurname(s.getSurname());
		//		res.setBirthdate(s.getBirthdate());
		//		res.setPhone(s.getPhone());
		//		res.setEmail(s.getEmail());
		//		res.setIdNumber(s.getIdNumber());
		//		res.setNationality(s.getNationality());

		this.validator.validate(s, binding);

		return s;
	}

}
