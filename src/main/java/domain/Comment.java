
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	private Date				writeMoment;
	private String				title;
	private String				text;
	private Collection<String>	pictures;
	private Integer				rate;


	public Date getWriteMoment() {
		return this.writeMoment;
	}

	public void setWriteMoment(final Date writeMoment) {
		this.writeMoment = writeMoment;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@URL
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	@Range(min = 0, max = 3)
	public Integer getRate() {
		return this.rate;
	}

	public void setRate(final Integer rate) {
		this.rate = rate;
	}
}
