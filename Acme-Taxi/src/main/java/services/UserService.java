
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;

import domain.User;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository	userRepository;


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
		final User u  = this.userRepository.findOne(userId);
		return u;
	}
}
