
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting services ----------------------------------------------------	

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;


	// Constructors -----------------------------------------------------------
	public SponsorshipService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------
	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> res;
		res = this.sponsorshipRepository.findAll();
		return res;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);
		Sponsorship res;
		res = this.sponsorshipRepository.findOne(sponsorshipId);
		Assert.notNull(res);
		return res;
	}

	public Sponsorship create() {
		Sponsorship res;
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		res = new Sponsorship();
		res.setSponsor(sponsor);
		res.setMoment(new Date(System.currentTimeMillis() - 10000));
		res.setAccepted(false);
		res.setCancelled(false);
		res.setPrice(this.configurationService.find().getAdvertisementPrice());
		return res;
	}
	public Sponsorship save(final Sponsorship sponsorship) {
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		Assert.isTrue(sponsorship.getSponsor().equals(sponsor));
		final Sponsorship res = this.sponsorshipRepository.save(sponsorship);
		return res;
	}
	public void delete(final Sponsorship sponsorship) {
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		assert sponsorship != null;
		assert sponsorship.getId() != 0;
		Assert.isTrue(this.sponsorshipRepository.findOne(sponsorship.getId()) != null);
		Assert.isTrue(sponsor.equals(sponsorship.getSponsor()));
		this.sponsorshipRepository.delete(sponsorship.getId());
	}

	public void cancel(final Sponsorship sponsorship) {
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		assert sponsorship != null;
		Assert.isTrue(sponsor.equals(sponsorship.getSponsor()));
		Assert.isTrue(sponsorship.isAccepted());
		sponsorship.setCancelled(true);
		this.sponsorshipRepository.save(sponsorship);

	}

	public void accept(final Sponsorship sponsorship) {
		assert sponsorship != null;
		Assert.isTrue(!sponsorship.isAccepted());
		sponsorship.setAccepted(true);
		this.sponsorshipRepository.save(sponsorship);

	}

	public Sponsorship reconstruct(final Sponsorship s, final BindingResult binding) {
		final Sponsorship res = s;

		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		res.setSponsor(sponsor);
		res.setMoment(new Date(System.currentTimeMillis() - 10000));
		res.setAccepted(false);
		res.setCancelled(false);
		res.setPrice(this.configurationService.find().getAdvertisementPrice());

		this.validator.validate(res, binding);
		return res;
	}

	// Other business methods -------------------------------------------------
	public Collection<Sponsorship> findSponsorshipByPrincipal(final Pageable pageable) {
		final Sponsor s = this.sponsorService.findByPrincipal();
		final Collection<Sponsorship> res = this.sponsorshipRepository.findSponsorshipBySponsorId(s.getId(), pageable).getContent();
		Assert.notNull(res);
		return res;
	}

	public Integer countSponsorshipBySponsorId() {
		final Sponsor s = this.sponsorService.findByPrincipal();
		return this.sponsorshipRepository.countSponsorshipBySponsorId(s.getId());
	}

	public Collection<Sponsorship> findSponsorshipNotAccepted(final Pageable pageable) {
		final Collection<Sponsorship> res = this.sponsorshipRepository.findSponsorshipNotAccepted(pageable).getContent();
		Assert.notNull(res);
		return res;
	}

	public Integer countSponsorshipNotAccepted() {
		return this.sponsorshipRepository.countSponsorshipNotAccepted();
	}

	public Sponsorship getRandomSponsorship() {
		return new ArrayList<Sponsorship>(this.sponsorshipRepository.getRandomSponsorship()).get(0);
	}
}
