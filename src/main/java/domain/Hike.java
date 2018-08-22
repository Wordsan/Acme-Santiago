
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Indexed
@Entity
@Access(AccessType.PROPERTY)
public class Hike extends DomainEntity {

	private String name;
	private Double length;
	private String originCity;
	private String destinationCity;
	private String description;
	private String pictures;
	private String difficultyLevel;

	/* RELATIONSHIPS */

	private Route route;
	private Collection<Comment> comments;
	private Collection<Advertisement> advertisements;

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
	public String getOriginCity() {
		return this.originCity;
	}

	public void setOriginCity(final String originCity) {
		this.originCity = originCity;
	}

	@Field
	@NotBlank
	public String getDestinationCity() {
		return this.destinationCity;
	}

	public void setDestinationCity(final String destinationCity) {
		this.destinationCity = destinationCity;
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

	@Valid
	@Pattern(regexp = ("^EASY|MEDIUM|DIFFICULT$"))
	public String getDifficultyLevel() {
		return this.difficultyLevel;
	}

	public void setDifficultyLevel(final String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Route getRoute() {
		return this.route;
	}

	public void setRoute(final Route route) {
		this.route = route;
	}

	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hike")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@Valid
	@NotNull
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hike")
	public Collection<Advertisement> getAdvertisements() {
		return this.advertisements;
	}

	public void setAdvertisements(final Collection<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}
}
