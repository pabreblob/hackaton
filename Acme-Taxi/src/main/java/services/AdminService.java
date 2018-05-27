
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdminRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Admin;
import domain.Driver;
import domain.Mechanic;
import domain.User;

@Service
@Transactional
public class AdminService {

	@Autowired
	private AdminRepository			adminRepository;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private Validator				validator;


	public AdminService() {
		super();
	}

	@SuppressWarnings("deprecation")
	public Admin save(final Admin admin) {
		int age;
		final LocalDate birth = new LocalDate(admin.getBirthdate().getYear() + 1900, admin.getBirthdate().getMonth() + 1, admin.getBirthdate().getDate());
		final LocalDate now = new LocalDate();
		age = Years.yearsBetween(birth, now).getYears();
		Assert.isTrue(age >= 18);
		Assert.isTrue(admin.getId() == this.findByPrincipal().getId());
		if (admin.getPhone() != null && admin.getPhone() != "")
			if (!admin.getPhone().trim().startsWith("+"))
				admin.setPhone("+" + this.configurationService.find().getCountryCode() + " " + admin.getPhone().trim());

		final Admin res = this.adminRepository.save(admin);
		return res;
	}

	public Admin findOne(final int idAdmin) {
		Assert.isTrue(idAdmin != 0);
		final Admin res = this.adminRepository.findOne(idAdmin);
		return res;
	}

	public Collection<Admin> findAll() {
		final Collection<Admin> res;
		res = this.adminRepository.findAll();
		return res;
	}

	public Admin findByUserAccountId(final int adminAccountId) {
		Assert.isTrue(adminAccountId != 0);
		Admin res;
		res = this.adminRepository.findAdminByUserAccountId(adminAccountId);
		return res;
	}

	public Admin findByPrincipal() {
		final UserAccount u = LoginService.getPrincipal();
		final Admin res = this.adminRepository.findAdminByUserAccountId(u.getId());
		return res;
	}

	public Integer getMaxAttendants() {
		final Integer res = this.adminRepository.getMaxAttendants();
		if (res == null)
			return 0;
		else
			return res;
	}

	public Integer getMinAttendants() {
		final Integer res = this.adminRepository.getMinAttendants();
		if (res == null)
			return 0;
		else
			return res;
	}

	public Double getAvgAttendants() {
		final Double res = this.adminRepository.getAvgAttendants();
		if (res == null)
			return 0.;
		else
			return res;
	}

	public Double getStandardDeviationAttendants() {
		final Double res = this.adminRepository.getStandardDeviationAttendants();
		if (res == null)
			return 0.;
		else
			return res;
	}

	public Collection<User> getTopUsers() {
		final List<User> res = new ArrayList<User>(this.adminRepository.getTopUsers());
		return res.subList(0, res.size() < 10 ? res.size() : 10);
	}

	public Collection<Driver> getTopDrivers() {
		final List<Driver> res = new ArrayList<Driver>(this.adminRepository.getTopDrivers());
		return res.subList(0, res.size() < 10 ? res.size() : 10);
	}

	public Map<Mechanic, Integer> getTopMechanics() {
		List<Mechanic> res = new ArrayList<Mechanic>(this.adminRepository.getTopMechanics());
		res = res.subList(0, res.size() < 10 ? res.size() : 10);
		final Map<Mechanic, Integer> toReturn = new LinkedHashMap<Mechanic, Integer>();
		int i = 0;
		while (i < res.size()) {
			toReturn.put(res.get(i), this.adminRepository.getTopRating(res.get(i).getId()));
			i++;
		}
		return toReturn;
	}

	public Collection<User> getWorstUsers() {
		final List<User> res = new ArrayList<User>(this.adminRepository.getWorstUsers());
		return res.subList(0, res.size() < 10 ? res.size() : 10);
	}

	public Collection<Driver> getWorstDrivers() {
		final List<Driver> res = new ArrayList<Driver>(this.adminRepository.getWorstDrivers());
		return res.subList(0, res.size() < 10 ? res.size() : 10);
	}

	public Map<Mechanic, Integer> getWorstMechanics() {
		List<Mechanic> res = new ArrayList<Mechanic>(this.adminRepository.getWorstMechanics());
		res = res.subList(0, res.size() < 10 ? res.size() : 10);
		final Map<Mechanic, Integer> toReturn = new LinkedHashMap<Mechanic, Integer>();
		int i = 0;
		while (i < res.size()) {
			toReturn.put(res.get(i), this.adminRepository.getWorstRating(res.get(i).getId()));
			i++;
		}
		return toReturn;
	}

	public Double getRatioCancelledAnnouncements() {
		final Double res = this.adminRepository.getRatioCancelledAnnouncements();
		if (res == null)
			return 0.;
		else
			return res;
	}

	public Map<Actor, Integer> getMostReportsWritten() {
		List<Actor> res = new ArrayList<Actor>(this.adminRepository.getMostReportsWritten());
		res = res.subList(0, res.size() < 10 ? res.size() : 10);
		final Map<Actor, Integer> toReturn = new LinkedHashMap<Actor, Integer>();
		int i = 0;
		while (i < res.size()) {
			toReturn.put(res.get(i), this.adminRepository.getCountWritten(res.get(i).getId()));
			i++;
		}
		return toReturn;
	}

	public Map<Actor, Integer> getMostReportsReceived() {
		List<Actor> res = new ArrayList<Actor>(this.adminRepository.getMostReportsReceived());
		res = res.subList(0, res.size() < 10 ? res.size() : 10);
		final Map<Actor, Integer> toReturn = new LinkedHashMap<Actor, Integer>();
		int i = 0;
		while (i < res.size()) {
			toReturn.put(res.get(i), this.adminRepository.getCountReceived(res.get(i).getId()));
			i++;
		}
		return toReturn;
	}
	public Admin reconstruct(final Admin admin, final BindingResult binding) {
		final Admin res = admin;
		Assert.isTrue(admin.getId() == this.findByPrincipal().getId());
		res.setBlockedUsers(this.findOne(admin.getId()).getBlockedUsers());
		res.setFolders(this.findOne(admin.getId()).getFolders());
		res.setUserAccount(this.findOne(admin.getId()).getUserAccount());
		this.validator.validate(res, binding);
		return res;
	}
}
