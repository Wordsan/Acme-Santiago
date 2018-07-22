
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	private Date	writeMoment;
	private String	title;
	private String	text;
	private String	pictures;
	private Integer	rate;

	/* RELATIONSHIPS */

	private User	owner;
	private Route   route;
	private Hike    hike;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
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
	@NotEmpty
	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	@Range(min = 0, max = 3)
	@NotNull
	public Integer getRate() {
		return this.rate;
	}

	public void setRate(final Integer rate) {
		this.rate = rate;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getOwner() {
		return this.owner;
	}

	public void setOwner(final User owner) {
		this.owner = owner;
	}

	@AssertTrue
	@Transient
	public boolean isValidHasEitherRouteOrHike()
	{
		// El @AssertTrue encima hace que el validador compruebe que esto devuelve true.
		// Comprueba que el comentario esta asociado solo a un route o a un hike, si no no es valido.
		if (getRoute() != null && getHike() == null) return true;
		if (getRoute() == null && getHike() != null) return true;
		return false;
	}

	@Valid
	@ManyToOne(optional = true)
	public Route getRoute()
	{
		return route;
	}

	public void setRoute(Route route)
	{
		this.route = route;
	}

	@Valid
	@ManyToOne(optional = true)
	public Hike getHike()
	{
		return hike;
	}

	public void setHike(Hike hike)
	{
		this.hike = hike;
	}
}
