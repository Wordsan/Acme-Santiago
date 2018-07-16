
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Inn extends DomainEntity {

	private String		name;
	private String		badge;
	private String		address;
	private String		phoneNumber;
	private String		email;
	private String		webSite;
	private String		creditCard;

	/* RELATIONSHIPS */

	private Innkeeper	innkeeper;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@URL
	public String getBadge() {
		return this.badge;
	}

	public void setBadge(final String badge) {
		this.badge = badge;
	}

	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotBlank
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(final String webSite) {
		this.webSite = webSite;
	}

	@CreditCardNumber
	public String getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final String creditCard) {
		this.creditCard = creditCard;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Innkeeper getInnkeeper() {
		return this.innkeeper;
	}

	public void setInnkeeper(final Innkeeper innkeeper) {
		this.innkeeper = innkeeper;
	}
}
