
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "mechanic_id"), @Index(columnList = "marked")
})
public class RepairShop extends DomainEntity {

	private String				name;
	private String				description;
	private String				location;
	private String				phone;
	private String				photoUrl;
	private boolean				marked;
	private double				meanRating;

	private Mechanic			mechanic;
	private Collection<Review>	reviews;


	public RepairShop() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = Integer.MAX_VALUE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(final String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public boolean isMarked() {
		return this.marked;
	}

	public void setMarked(final boolean marked) {
		this.marked = marked;
	}

	@Min(value = 0)
	@Digits(integer = 1, fraction = 1)
	public double getMeanRating() {
		return this.meanRating;
	}

	public void setMeanRating(final double meanRating) {
		this.meanRating = meanRating;
	}

	@Valid
	@ManyToOne(optional = false)
	public Mechanic getMechanic() {
		return this.mechanic;
	}

	public void setMechanic(final Mechanic mechanic) {
		this.mechanic = mechanic;
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
