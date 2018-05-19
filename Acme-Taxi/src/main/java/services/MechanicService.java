
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

import repositories.MechanicRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Folder;
import domain.Mechanic;
import forms.MechanicForm;

@Service
@Transactional
public class MechanicService {

	@Autowired
	private MechanicRepository		mechanicRepository;
	@Autowired
	private UserAccountService	userAccountService;
	@Autowired
	private Validator			validator;
	@Autowired
	private ConfigurationService		configurationService;
	@Autowired
	private FolderService		folderService;


	public MechanicService() {
		super();
	}

	public Mechanic create() {
		final Mechanic mechanic = new Mechanic();

		final UserAccount ua = this.userAccountService.create();
		mechanic.setUserAccount(ua);

		mechanic.setFolders(new ArrayList<Folder>());
		mechanic.getFolders().add(this.folderService.create());
		mechanic.getFolders().add(this.folderService.create());
		mechanic.getFolders().add(this.folderService.create());
		mechanic.getFolders().add(this.folderService.create());
		mechanic.getFolders().add(this.folderService.create());
		mechanic.setBlockedUsers(new ArrayList<Actor>());
		final List<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.MECHANIC);
		authorities.add(auth);
		mechanic.getUserAccount().setAuthorities(authorities);

		return mechanic;
	}
	@SuppressWarnings("deprecation")
	public Mechanic save(final Mechanic mechanic) {
		Assert.notNull(mechanic);
		Assert.isTrue(!mechanic.getUserAccount().getUsername().isEmpty());
		Assert.isTrue(!mechanic.getUserAccount().getPassword().isEmpty());
		Assert.isTrue(!mechanic.getEmail().isEmpty());
		Assert.isTrue(!mechanic.getName().isEmpty());
		Assert.isTrue(!mechanic.getSurname().isEmpty());
		int age;
		final LocalDate birth = new LocalDate(mechanic.getBirthdate().getYear() + 1900, mechanic.getBirthdate().getMonth() + 1, mechanic.getBirthdate().getDate());
		final LocalDate now = new LocalDate();
		age=Years.yearsBetween(birth, now).getYears();
		Assert.isTrue(age>=18);
		if(mechanic.getId()!=0){
			Assert.isTrue(mechanic.getId()==this.findByPrincipal().getId());
		}
		if (mechanic.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(mechanic.getUserAccount().getPassword(), null);
			mechanic.getUserAccount().setPassword(hash);
		}
		
		if (mechanic.getId() == 0) {
			mechanic.setFolders(new ArrayList<Folder>());
			final Collection<Folder> folders = this.folderService.defaultFolders();
			mechanic.getFolders().addAll(folders);
		}
	
		if (mechanic.getPhone() != null && mechanic.getPhone() != "")
			if (!mechanic.getPhone().trim().startsWith("+"))
				mechanic.setPhone("+" + this.configurationService.find().getCountryCode() + " " + mechanic.getPhone().trim());
		if(mechanic.getId()==0){
			final List<Authority> authorities = new ArrayList<Authority>();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.MECHANIC);
			authorities.add(auth);
			mechanic.getUserAccount().setAuthorities(authorities);
			final UserAccount ua = this.userAccountService.save(mechanic.getUserAccount());
			mechanic.setUserAccount(ua);		
		}
		

		final Mechanic res = this.mechanicRepository.save(mechanic);
		return res;
	}

	public Mechanic findOne(final int idMechanic) {
		Assert.isTrue(idMechanic!= 0);
		
		final Mechanic res = this.mechanicRepository.findOne(idMechanic);
		Assert.isTrue(res!=null);
		return res;
	}

	public Collection<Mechanic> findAll() {
		final Collection<Mechanic> res;
		res = this.mechanicRepository.findAll();
		return res;
	}

	public Mechanic findByUserAccountId(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);
		final Mechanic res = this.mechanicRepository.findMechanicByUserAccountId(userAccountId);
		return res;
	}

	public Mechanic findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Mechanic res = this.mechanicRepository.findMechanicByUserAccountId(u.getId());
		return res;
	}
	public Mechanic reconstructCreation(final MechanicForm mechanicForm, final BindingResult binding) {
		final Mechanic res = this.create();
		res.setName(mechanicForm.getName());
		res.setSurname(mechanicForm.getSurname());
		res.setBirthdate(mechanicForm.getBirthdate());
		res.setPhone(mechanicForm.getPhone());
		res.setEmail(mechanicForm.getEmail());
		res.setSuspicious(false);
		res.getUserAccount().setUsername(mechanicForm.getUserAccount().getUsername());
		res.getUserAccount().setPassword(mechanicForm.getUserAccount().getPassword());
		res.setIdNumber(mechanicForm.getIdNumber());
		res.setPhotoUrl(mechanicForm.getPhotoUrl());
		res.setNationality(mechanicForm.getNationality());
		this.validator.validate(res, binding);
		return res;
	}
	
	public Mechanic reconstructEdition(final Mechanic mechanic, final BindingResult binding) {
		final Mechanic res = mechanic;
		Assert.isTrue(mechanic.getId()==this.findByPrincipal().getId());
		res.setBlockedUsers(this.findOne(mechanic.getId()).getBlockedUsers());
		res.setFolders(this.findOne(mechanic.getId()).getFolders());
		res.setUserAccount(this.findOne(mechanic.getId()).getUserAccount());
		this.validator.validate(res, binding);
		return res;
	}

}
