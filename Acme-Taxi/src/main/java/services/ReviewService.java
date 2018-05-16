
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReviewRepository;
import domain.Driver;
import domain.Review;
import domain.User;
import forms.ReviewForm;

@Service
@Transactional
public class ReviewService {

	@Autowired
	private ReviewRepository		reviewRepository;

	// Supporting services ----------------------------------------------------	

	@Autowired
	private UserService				userService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;

	@Autowired
	private DriverService			driverService;


	// Constructors -----------------------------------------------------------
	public ReviewService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------
	public Collection<Review> findAll() {
		Collection<Review> res;
		res = this.reviewRepository.findAll();
		return res;
	}

	public Review findOne(final int reviewId) {
		Assert.isTrue(reviewId != 0);
		Review res;
		res = this.reviewRepository.findOne(reviewId);
		Assert.notNull(res);
		return res;
	}

	public Review create() {
		Review res;
		final User u = this.userService.findByPrincipal();
		res = new Review();
		res.setCreator(u);
		res.setMoment(new Date(System.currentTimeMillis() - 10000));
		res.setMarked(false);
		return res;
	}
	public Review saveDriver(final Review review, final int driverId) {
		final User u = this.userService.findByPrincipal();
		final Driver d = this.driverService.findOne(driverId);
		Assert.isTrue(review.getCreator().equals(u));
		Assert.isTrue(this.driverService.findDriversReviewable().contains(d));
		final Review res = this.reviewRepository.save(review);
		d.getReviews().add(res);
		return res;
	}

	public Review update(final Review review) {
		final User u = this.userService.findByPrincipal();
		Assert.isTrue(review.getCreator().equals(u));

		final Review res = this.reviewRepository.save(review);

		//Buscar los drivers con esa review
		//Si nadie la tiene buscar un repairshop con esa review
		//Si nadie la tiene buscar un user con esa review
		final Driver d = this.driverService.findDriverByReviewId(review.getId());
		if (d != null) {
			d.getReviews().remove(review);
			d.getReviews().add(res);
		}
		return res;
	}

	public void delete(final int reviewId) {
		final Review r = this.findOne(reviewId);
		Assert.notNull(r);
		final User creator = this.userService.findByPrincipal();
		Assert.isTrue(creator.equals(r.getCreator()));
		//Buscar los drivers con esa review
		//Si nadie la tiene buscar un repairshop con esa review
		//Si nadie la tiene buscar un user con esa review
		final Driver d = this.driverService.findDriverByReviewId(r.getId());
		if (d != null)
			d.getReviews().remove(r);

		this.reviewRepository.delete(r.getId());
	}

	public Review reconstructDriver(final ReviewForm r, final BindingResult binding) {
		final Review res = this.create();
		res.setTitle(r.getTitle());
		res.setBody(r.getBody());
		res.setMoment(r.getMoment());
		res.setRating(r.getRating());
		this.validator.validate(res, binding);
		return res;
	}

	// Other business methods -------------------------------------------------
	public Collection<Review> findReviewsByPrincipal(final Pageable pageable) {
		final User u = this.userService.findByPrincipal();
		final Collection<Review> res = this.reviewRepository.findReviewsByCreatorId(u.getId(), pageable).getContent();
		Assert.notNull(res);
		return res;
	}

	public Integer countReviewsByPrincipal() {
		final User u = this.userService.findByPrincipal();
		return this.reviewRepository.countReviewsByCreatorId(u.getId());
	}
	//
	//	public Collection<Sponsorship> findSponsorshipNotAccepted(final Pageable pageable) {
	//		final Collection<Sponsorship> res = this.sponsorshipRepository.findSponsorshipNotAccepted(pageable).getContent();
	//		Assert.notNull(res);
	//		return res;
	//	}
	//
	//	public Integer countSponsorshipNotAccepted() {
	//		return this.sponsorshipRepository.countSponsorshipNotAccepted();
	//	}
}
