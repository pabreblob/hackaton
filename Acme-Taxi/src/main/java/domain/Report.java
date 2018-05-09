
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	private String	reason;
	private Date	moment;
	private boolean	read;
	private String	imageUrl;

	private Actor	creator;
	private Actor	reported;


	public Report() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = Integer.MAX_VALUE)
	public String getReason() {
		return this.reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	@NotNull
	@Valid
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public boolean isRead() {
		return this.read;
	}

	public void setRead(final boolean read) {
		this.read = read;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getCreator() {
		return this.creator;
	}

	public void setCreator(final Actor creator) {
		this.creator = creator;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getReported() {
		return this.reported;
	}

	public void setReported(final Actor reported) {
		this.reported = reported;
	}

}
