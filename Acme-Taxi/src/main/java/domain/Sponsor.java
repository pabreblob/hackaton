
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {

	private String	idNumber;


	public Sponsor() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(final String idNumber) {
		this.idNumber = idNumber;
	}

}
