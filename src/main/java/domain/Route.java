
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Indexed
@Table(indexes = {
	@Index(columnList = "creator_id"), @Index(columnList = "length")
})
public class Route extends DomainEntity {

	private String				name;
	private Double				length;
	private String				description;
	private String				pictures;

	/* RELATIONSHIPS */

	private Collection<Hike>	composedHikes;
	private Collection<Comment>	comments;
	private User				creator;


	@Field
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@Min(value = 0)
	public Double getLength() {
		return this.length;
	}

	public void setLength(final Double length) {
		this.length = length;
	}

	@Field
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	@NotBlank
	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	// RELATIONSHIPS

	@IndexedEmbedded
	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
	public Collection<Hike> getComposedHikes() {
		return this.composedHikes;
	}

	public void setComposedHikes(final Collection<Hike> composedHikes) {
		this.composedHikes = composedHikes;
	}

	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
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
}
