package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Advertisement extends DomainEntity {

	private String title;
	private String banner;
	private String targetUrl;
	private CreditCard creditCard;
	private int daysDisplayed;
	private Date startMoment;
	private Date endMoment;

	/* RELATIONSHIPS */
	private Agent agent;
	private Hike hike;

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@NotBlank
	@URL
	public String getTargetUrl() {
		return this.targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@Min(value = 1)
	public int getDaysDisplayed() {
		return this.daysDisplayed;
	}

	public void setDaysDisplayed(int daysDisplayed) {
		this.daysDisplayed = daysDisplayed;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return this.endMoment;
	}

	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Agent getAgent() {
		return this.agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Hike getHike() {
		return this.hike;
	}

	public void setHike(Hike hike) {
		this.hike = hike;
	}

}
