
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class ChirpForm {

	private String	title;
	private String	description;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
