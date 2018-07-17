
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Route extends DomainEntity {

	private String				name;
	private Double				length;
	private String				description;
	private Collection<String>	pictures;

	/* RELATIONSHIPS */

	private Collection<Hike>	composedHikes;
	private Collection<Comment>	comments;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public Double getLength() {
		return this.length;
	}

	public void setLength(final Double length) {
		this.length = length;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	@NotNull
	@NotEmpty
	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Hike> getComposedHikes() {
		return this.composedHikes;
	}

	public void setComposedHikes(final Collection<Hike> composedHikes) {
		this.composedHikes = composedHikes;
	}

	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}
}
