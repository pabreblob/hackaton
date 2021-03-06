
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
import domain.Admin;

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

	public Collection<Actor> getBannedActor(final Pageable pageable) {
		return this.actorRepository.getBannedActor(pageable).getContent();
	}

	public Integer countBannedActor() {
		return this.actorRepository.countBannedActor();
	}

	public void ban(final int actorId) {
		final Actor a = this.findOne(actorId);
		Assert.isTrue(!(a instanceof Admin));
		Assert.notNull(a);
		a.getUserAccount().setBanned(true);
		this.save(a);
	}

	public void unban(final int actorId) {
		final Actor a = this.findOne(actorId);
		Assert.notNull(a);
		a.getUserAccount().setBanned(false);
		this.save(a);
	}

	public void block(final int actorId) {
		final Actor principal = this.findByPrincipal();
		final Actor a = this.findOne(actorId);
		Assert.notNull(a);
		Assert.isTrue(!(a instanceof Admin));
		Assert.notNull(principal);
		Assert.isTrue(a.getId() != principal.getId());
		if (!principal.getBlockedUsers().contains(a))
			principal.getBlockedUsers().add(a);
	}

	public void unblock(final int actorId) {
		final Actor principal = this.findByPrincipal();
		final Actor a = this.findOne(actorId);
		Assert.notNull(a);
		Assert.notNull(principal);
		if (principal.getBlockedUsers().contains(a))
			principal.getBlockedUsers().remove(a);
	}

}
