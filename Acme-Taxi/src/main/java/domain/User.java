
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	private String						photoUrl;
	private String						location;
	private double						meanRating;

	private Collection<Announcement>	announcements;
	private Collection<Review>			reviews;


	public User() {
		super();
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(final String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	@Min(value = 0)
	@Digits(integer = 1, fraction = 1)
	public double getMeanRating() {
		return this.meanRating;
	}

	public void setMeanRating(final double meanRating) {
		this.meanRating = meanRating;
	}

	@NotNull
	@ManyToMany(mappedBy = "attendants")
	public Collection<Announcement> getAnnouncements() {
		return this.announcements;
	}

	public void setAnnouncements(final Collection<Announcement> announcements) {
		this.announcements = announcements;
	}

	@NotNull
	@OneToMany
	public Collection<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(final Collection<Review> reviews) {
		this.reviews = reviews;
	}

}
