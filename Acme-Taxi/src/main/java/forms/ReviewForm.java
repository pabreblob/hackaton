
package forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Driver;
import domain.RepairShop;
import domain.User;

public class ReviewForm {

	private String		title;
	private String		body;
	private Date		moment;
	private int			rating;
	private boolean		marked;

	private User		creator;
	private Driver		driver;
	private RepairShop	repairShop;
	private User		user;


	public ReviewForm() {
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
	@Column(length = Integer.MAX_VALUE)
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotNull
	@Valid
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Range(max = 5, min = 0)
	public int getRating() {
		return this.rating;
	}

	public void setRating(final int rating) {
		this.rating = rating;
	}

	public boolean isMarked() {
		return this.marked;
	}

	public void setMarked(final boolean marked) {
		this.marked = marked;
	}

	@Valid
	@NotNull
	public User getCreator() {
		return this.creator;
	}

	public void setCreator(final User creator) {
		this.creator = creator;
	}

	@Valid
	public Driver getDriver() {
		return this.driver;
	}

	public void setDriver(final Driver driver) {
		this.driver = driver;
	}

	@Valid
	public RepairShop getRepairShop() {
		return this.repairShop;
	}

	public void setRepairShop(final RepairShop repairShor) {
		this.repairShop = repairShor;
	}

	@Valid
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}
