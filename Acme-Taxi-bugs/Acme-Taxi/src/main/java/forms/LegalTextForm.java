
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Access(AccessType.PROPERTY)
public class LegalTextForm {

	private String	text;


	public LegalTextForm() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.RELAXED)
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

}
