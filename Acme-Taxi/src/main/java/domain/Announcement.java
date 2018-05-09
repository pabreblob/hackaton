
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Announcement extends DomainEntity {

	private String				title;
	private String				from;
	private String				to;
	private double				pricePerPerson;
	private Date				moment;
	private String				description;
	private boolean				petsAllowed;
	private boolean				smokingAllowed;
	private boolean				cancelled;
	private boolean				marked;
	private int					seats;

	private User				creator;
	private Collection<User>	attendants;


	public Announcement() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getFrom() {
		return this.from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTo() {
		return this.to;
	}

	public void setTo(final String to) {
		this.to = to;
	}

	@Min(value = 0)
	@Digits(integer = 15, fraction = 2)
	public double getPricePerPerson() {
		return this.pricePerPerson;
	}

	public void setPricePerPerson(final double pricePerPerson) {
		this.pricePerPerson = pricePerPerson;
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

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = Integer.MAX_VALUE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public boolean isPetsAllowed() {
		return this.petsAllowed;
	}

	public void setPetsAllowed(final boolean petsAllowed) {
		this.petsAllowed = petsAllowed;
	}

	public boolean isSmokingAllowed() {
		return this.smokingAllowed;
	}

	public void setSmokingAllowed(final boolean smokingAllowed) {
		this.smokingAllowed = smokingAllowed;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isMarked() {
		return this.marked;
	}

	public void setMarked(final boolean marked) {
		this.marked = marked;
	}

	@Min(value = 1)
	public int getSeats() {
		return this.seats;
	}

	public void setSeats(final int seats) {
		this.seats = seats;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getCreator() {
		return this.creator;
	}

	public void setCreator(final User creator) {
		this.creator = creator;
	}

	@NotNull
	@ManyToMany
	public Collection<User> getAttendants() {
		return this.attendants;
	}

	public void setAttendants(final Collection<User> attendants) {
		this.attendants = attendants;
	}

}
