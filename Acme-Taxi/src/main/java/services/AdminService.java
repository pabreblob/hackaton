
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.LoginService;
import security.UserAccount;
import domain.Admin;

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

}
