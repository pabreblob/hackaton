
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "nationality")
})
public class IdNumberPattern extends DomainEntity {

	private String	pattern;
	private String	nationality;


	public IdNumberPattern() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
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
