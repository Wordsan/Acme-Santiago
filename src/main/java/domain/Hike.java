
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Hike extends DomainEntity {

	private String				name;
	private Double				length;
	private String				originCity;
	private String				destinationCity;
	private String				description;
	private Collection<String>	pictures;
	private String				difficultyLevel;


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
	public String getOriginCity() {
		return this.originCity;
	}

	public void setOriginCity(final String originCity) {
		this.originCity = originCity;
	}

	@NotBlank
	public String getDestinationCity() {
		return this.destinationCity;
	}

	public void setDestinationCity(final String destinationCity) {
		this.destinationCity = destinationCity;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	public String getDifficultyLevel() {
		return this.difficultyLevel;
	}

	public void setDifficultyLevel(final String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
}
