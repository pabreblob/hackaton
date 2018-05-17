
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
import domain.RepairShop;
import domain.Review;
import domain.SpamWord;
import domain.User;
import forms.ReviewForm;

@Service
@Transactional
public class ReviewService {

	@Autowired
	private ReviewRepository	reviewRepository;

	// Supporting services ----------------------------------------------------	

	@Autowired
	private UserService			userService;

	@Autowired
	private SpamWordService		spamWordService;

	@Autowired
	private Validator			validator;

	@Autowired
	private DriverService		driverService;

	@Autowired
	private RepairShopService	repairShopService;


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
		d.setMeanRating(ReviewService.calculoRating(d.getReviews()));

		final Collection<SpamWord> sw = this.spamWordService.findAll();
		boolean spamw = false;
		for (final SpamWord word : sw) {
			spamw = review.getTitle().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			spamw |= review.getBody().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (spamw)
				break;
		}
		review.setMarked(spamw);
		if (spamw == true)
			review.getCreator().setSuspicious(spamw);
		return res;
	}

	public Review saveRepairShop(final Review review, final int repairShopId) {
		final User u = this.userService.findByPrincipal();
		final RepairShop rs = this.repairShopService.findOne(repairShopId);
		Assert.isTrue(review.getCreator().equals(u));
		//		Assert.isTrue(this.repairShopService.findRepairShopsReviewable().contains(rs));
		final Review res = this.reviewRepository.save(review);
		rs.getReviews().add(res);
		rs.setMeanRating(ReviewService.calculoRating(rs.getReviews()));

		final Collection<SpamWord> sw = this.spamWordService.findAll();
		boolean spamw = false;
		for (final SpamWord word : sw) {
			spamw = review.getTitle().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			spamw |= review.getBody().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (spamw)
				break;
		}
		review.setMarked(spamw);
		if (spamw == true)
			review.getCreator().setSuspicious(spamw);
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
		final RepairShop rs = this.repairShopService.findRepairShopByReview(review.getId());
		if (d != null) {
			d.getReviews().remove(review);
			d.getReviews().add(res);
			d.setMeanRating(ReviewService.calculoRating(d.getReviews()));
		} else if (rs != null) {
			rs.getReviews().remove(review);
			rs.getReviews().add(res);
			rs.setMeanRating(ReviewService.calculoRating(rs.getReviews()));
		}

		final Collection<SpamWord> sw = this.spamWordService.findAll();
		boolean spamw = false;
		for (final SpamWord word : sw) {
			spamw = review.getTitle().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			spamw |= review.getBody().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (spamw)
				break;
		}
		review.setMarked(spamw);
		if (spamw == true)
			review.getCreator().setSuspicious(spamw);

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
		final RepairShop rs = this.repairShopService.findRepairShopByReview(r.getId());
		if (d != null) {
			d.getReviews().remove(r);
			d.setMeanRating(ReviewService.calculoRating(d.getReviews()));
		} else if (rs != null) {
			rs.getReviews().remove(r);
			rs.setMeanRating(ReviewService.calculoRating(rs.getReviews()));
		}

		this.reviewRepository.delete(r.getId());
	}

	public Review reconstruct(final ReviewForm r, final BindingResult binding) {
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

	public Collection<Review> findReviewsByUserId(final int userId, final Pageable pageable) {
		final Collection<Review> res = this.reviewRepository.findReviewsByUserId(userId, pageable).getContent();
		Assert.notNull(res);
		return res;
	}

	public Integer countReviewsByUserId(final int userId) {
		return this.reviewRepository.countReviewsByUserId(userId);
	}

	public Collection<Review> findReviewsByDriverId(final int driverId, final Pageable pageable) {
		final Collection<Review> res = this.reviewRepository.findReviewsByDriverId(driverId, pageable).getContent();
		Assert.notNull(res);
		return res;
	}

	public Integer countReviewsByDriverId(final int driverId) {
		return this.reviewRepository.countReviewsByDriverId(driverId);
	}

	public Collection<Review> findReviewsByRepairShopId(final int repairShopId, final Pageable pageable) {
		final Collection<Review> res = this.reviewRepository.findReviewsByRepairShopId(repairShopId, pageable).getContent();
		Assert.notNull(res);
		return res;
	}

	public Integer countReviewsByRepairShopId(final int repairShopId) {
		return this.reviewRepository.countReviewsByRepairShopId(repairShopId);
	}

	private static double calculoRating(final Collection<Review> reviews) {
		final int total = reviews.size();
		double rating = 0.0;
		for (final Review review : reviews)
			rating += review.getRating();
		if (total > 0)
			rating = rating / total;
		return rating;
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