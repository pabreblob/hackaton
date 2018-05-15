
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;


	public Actor findByPrincipal() {
		Actor res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findActorByUserAccountId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

	public Actor findActorByUserAccountId(final int UserAccountId) {
		return this.actorRepository.findActorByUserAccountId(UserAccountId);
	}

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int actorId) {
		final Actor a = this.actorRepository.findOne(actorId);
		return a;
	}

	public Actor save(final Actor a) {
		Actor res;
		res = this.actorRepository.save(a);
		return res;
	}

	public Collection<Actor> findByUsername(final String keyword) {
		if (StringUtils.isEmpty(keyword))
			return new ArrayList<Actor>();
		else
			return this.actorRepository.findByUsername(keyword);
	}

	public Collection<Actor> getSuspiciousActor(final Pageable pageable) {
		return this.actorRepository.getSuspiciousActor(pageable).getContent();
	}
	public Integer countSuspiciousActor() {
		return this.actorRepository.countSuspiciousActor();
	}
	public void ban(final int actorId) {
		final Actor a = this.findOne(actorId);
		Assert.notNull(a);
		a.setBanned(true);
		this.save(a);
	}

	public void unban(final int actorId) {
		final Actor a = this.findOne(actorId);
		Assert.notNull(a);
		a.setBanned(false);
		this.save(a);
	}

}
