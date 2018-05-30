
package forms;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Message;

@Access(AccessType.PROPERTY)
public class MessageForm {

	private String				subject;
	private String				body;

	private Message.Priority	priority;

	private Collection<String>	recipients;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
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
	public Message.Priority getPriority() {
		return this.priority;
	}

	public void setPriority(final Message.Priority priority) {
		this.priority = priority;
	}

	@NotNull
	@NotEmpty
	public Collection<String> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(final Collection<String> recipients) {
		this.recipients = recipients;
	}

}
