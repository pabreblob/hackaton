
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Driver extends Actor /* implements Reviewable */{

	private String	photoUrl;
	private int		maxPassengers;
	private String	numberPlate;
	private String	location;
	private String	idNumber;
	private double	meanRating;
	private String	nationality;


	public Driver() {
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

	@Min(value = 1)
	public int getMaxPassengers() {
		return this.maxPassengers;
	}

	public void setMaxPassengers(final int maxPassengers) {
		this.maxPassengers = maxPassengers;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNumberPlate() {
		return this.numberPlate;
	}

	public void setNumberPlate(final String numberPlate) {
		this.numberPlate = numberPlate;
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
	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(final String idNumber) {
		this.idNumber = idNumber;
	}

	@Min(value = 0)
	@Digits(integer = 1, fraction = 1)
	public double getMeanRating() {
		return this.meanRating;
	}

	public void setMeanRating(final double meanRating) {
		this.meanRating = meanRating;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(final String nationality) {
		this.nationality = nationality;
	}

}
