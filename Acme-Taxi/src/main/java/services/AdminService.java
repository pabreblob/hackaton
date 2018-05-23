
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	private AdminRepository	adminRepository;


	public AdminService() {
		super();
	}

	//	public Admin create() {
	//		Admin res;
	//		res = new Admin();
	//		final UserAccount ua = this.userAccountService.create();
	//		res.setUserAccount(ua);
	//
	//		final List<Authority> authorities = new ArrayList<Authority>();
	//		final Authority auth = new Authority();
	//		auth.setAuthority(Authority.ADMIN);
	//		authorities.add(auth);
	//		res.getUserAccount().setAuthorities(authorities);
	//
	//		return res;
	//	}

	//	public Admin save(final Admin admin) {
	//		Assert.notNull(admin);
	//
	//		if (admin.getId() == 0) {
	//			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	//			final String hash = encoder.encodePassword(admin.getUserAccount().getPassword(), null);
	//			admin.getUserAccount().setPassword(hash);
	//		}
	//
	//		final List<Authority> authorities = new ArrayList<Authority>();
	//		final Authority auth = new Authority();
	//		auth.setAuthority(Authority.ADMIN);
	//		authorities.add(auth);
	//		admin.getUserAccount().setAuthorities(authorities);
	//		final UserAccount ua = this.userAccountService.save(admin.getUserAccount());
	//		admin.setUserAccount(ua);
	//		final Admin res = this.adminRepository.save(admin);
	//		return res;
	//	}

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

	public Collection<Mechanic> getTopMechanics() {
		final List<Mechanic> res = new ArrayList<Mechanic>(this.adminRepository.getTopMechanics());
		return res.subList(0, res.size() < 10 ? res.size() : 10);
	}

	public Collection<User> getWorstUsers() {
		final List<User> res = new ArrayList<User>(this.adminRepository.getWorstUsers());
		return res.subList(0, res.size() < 10 ? res.size() : 10);
	}

	public Collection<Driver> getWorstDrivers() {
		final List<Driver> res = new ArrayList<Driver>(this.adminRepository.getWorstDrivers());
		return res.subList(0, res.size() < 10 ? res.size() : 10);
	}

	public Collection<Mechanic> getWorstMechanics() {
		final List<Mechanic> res = new ArrayList<Mechanic>(this.adminRepository.getWorstMechanics());
		return res.subList(0, res.size() < 10 ? res.size() : 10);
	}

	public Double getRatioCancelledAnnouncements() {
		final Double res = this.adminRepository.getRatioCancelledAnnouncements();
		if (res == null)
			return 0.;
		else
			return res;
	}

	public Collection<Actor> getMostReportsWritten() {
		final List<Actor> res = new ArrayList<Actor>(this.adminRepository.getMostReportsWritten());
		return res.subList(0, res.size() < 10 ? res.size() : 10);
	}

	public Collection<Actor> getMostReportsReceived() {
		final List<Actor> res = new ArrayList<Actor>(this.adminRepository.getMostReportsReceived());
		return res.subList(0, res.size() < 10 ? res.size() : 10);
	}
}
