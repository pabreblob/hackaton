
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Mechanic extends Actor {

	private String	idNumber;
	private String	photoUrl;


	public Mechanic() {
		super();
	}

	@NotBlank
	@SafeHtml
	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(final String idNumber) {
		this.idNumber = idNumber;
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

}