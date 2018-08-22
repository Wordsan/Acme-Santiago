
package domain;

import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	// Constructors -----------------------------------------------------------

	public CreditCard() {
		super();

	}

	// Attributes -------------------------------------------------------------
	private String holderName;
	private String brandName;
	private String cardNumber;
	private Integer expirationMonth;
	private Integer expirationYear;
	private Integer cvvCode;

	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@NotBlank
	@CreditCardNumber
	@Pattern(regexp = "\\d{16}")
	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@NotNull
	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@NotNull
	@Min(2018)
	public Integer getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCvvCode() {
		return this.cvvCode;
	}

	public void setCvvCode(Integer cvvCode) {
		this.cvvCode = cvvCode;
	}

	// Support methods ----------------------------------------------------------

	public String cardNumberMasked() {
		String cardNumberMasked;

		if (this.getCardNumber().length() == 16) {
			cardNumberMasked = this.getCardNumber().substring(0, 4) + " **** **** "
					+ this.getCardNumber().substring(12, 16);
		} else {
			cardNumberMasked = "";
		}

		return cardNumberMasked;
	}

	public boolean expired() {
		boolean isExpired;
		Integer currentMonth;
		Integer currentYear;

		isExpired = true;
		currentMonth = Integer.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
		currentYear = Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR));

		if (this.getExpirationYear() != null) {
			if (this.getExpirationYear() > currentYear) {
				isExpired = false;
			} else if ((this.getExpirationYear().equals(currentYear)) && (this.getExpirationMonth() > currentMonth)) {
				isExpired = false;
			}
		} else {
			isExpired = false;
		}

		return isExpired;
	}
}
