
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private String				bannerUrl;
	private String				currency;
	private double				pricePerKm;
	private double				minimumFee;
	private double				advertisementPrice;
	private String				welcomeEng;
	private String				welcomeEsp;
	private int					limitReportsWeek;
	private String				footer;
	private double				vat;
	private String				legalTextEng;
	private String				legalTextEsp;
	private String				cookiesPolicyEng;
	private String				cookiesPolicyEsp;
	private String				contactEng;
	private String				contactEsp;
	private String				countryCode;
	private String				acceptCookiesEng;
	private String				acceptCookiesEsp;
	private Collection<String>	nationalities;


	public Configuration() {
		super();
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBannerUrl() {
		return this.bannerUrl;
	}

	public void setBannerUrl(final String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	@Min(value = 0)
	@Digits(integer = 15, fraction = 2)
	public double getPricePerKm() {
		return this.pricePerKm;
	}

	public void setPricePerKm(final double pricePerKm) {
		this.pricePerKm = pricePerKm;
	}

	@Min(value = 0)
	@Digits(integer = 15, fraction = 2)
	public double getMinimumFee() {
		return this.minimumFee;
	}

	public void setMinimumFee(final double minimumFee) {
		this.minimumFee = minimumFee;
	}

	@Min(value = 0)
	@Digits(integer = 15, fraction = 2)
	public double getAdvertisementPrice() {
		return this.advertisementPrice;
	}

	public void setAdvertisementPrice(final double advertisementPrice) {
		this.advertisementPrice = advertisementPrice;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = Integer.MAX_VALUE)
	public String getWelcomeEng() {
		return this.welcomeEng;
	}

	public void setWelcomeEng(final String welcomeEng) {
		this.welcomeEng = welcomeEng;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = Integer.MAX_VALUE)
	public String getWelcomeEsp() {
		return this.welcomeEsp;
	}

	public void setWelcomeEsp(final String welcomeEsp) {
		this.welcomeEsp = welcomeEsp;
	}

	@Min(value = 0)
	public int getLimitReportsWeek() {
		return this.limitReportsWeek;
	}

	public void setLimitReportsWeek(final int limitReportsWeek) {
		this.limitReportsWeek = limitReportsWeek;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = Integer.MAX_VALUE)
	public String getFooter() {
		return this.footer;
	}

	public void setFooter(final String footer) {
		this.footer = footer;
	}

	@Min(value = 0)
	@Digits(integer = 15, fraction = 2)
	public double getVat() {
		return this.vat;
	}

	public void setVat(final double vat) {
		this.vat = vat;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.RELAXED)
	@Column(length = Integer.MAX_VALUE)
	public String getLegalTextEng() {
		return this.legalTextEng;
	}

	public void setLegalTextEng(final String legalTextEng) {
		this.legalTextEng = legalTextEng;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.RELAXED)
	@Column(length = Integer.MAX_VALUE)
	public String getLegalTextEsp() {
		return this.legalTextEsp;
	}

	public void setLegalTextEsp(final String legalTextEsp) {
		this.legalTextEsp = legalTextEsp;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.RELAXED)
	@Column(length = Integer.MAX_VALUE)
	public String getCookiesPolicyEng() {
		return this.cookiesPolicyEng;
	}

	public void setCookiesPolicyEng(final String cookiesPolicyEng) {
		this.cookiesPolicyEng = cookiesPolicyEng;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.RELAXED)
	@Column(length = Integer.MAX_VALUE)
	public String getCookiesPolicyEsp() {
		return this.cookiesPolicyEsp;
	}

	public void setCookiesPolicyEsp(final String cookiesPolicyEsp) {
		this.cookiesPolicyEsp = cookiesPolicyEsp;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.RELAXED)
	@Column(length = Integer.MAX_VALUE)
	public String getContactEng() {
		return this.contactEng;
	}

	public void setContactEng(final String contactEng) {
		this.contactEng = contactEng;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.RELAXED)
	@Column(length = Integer.MAX_VALUE)
	public String getContactEsp() {
		return this.contactEsp;
	}

	public void setContactEsp(final String contactEsp) {
		this.contactEsp = contactEsp;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = Integer.MAX_VALUE)
	public String getAcceptCookiesEng() {
		return this.acceptCookiesEng;
	}

	public void setAcceptCookiesEng(final String acceptCookiesEng) {
		this.acceptCookiesEng = acceptCookiesEng;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = Integer.MAX_VALUE)
	public String getAcceptCookiesEsp() {
		return this.acceptCookiesEsp;
	}

	public void setAcceptCookiesEsp(final String acceptCookiesEsp) {
		this.acceptCookiesEsp = acceptCookiesEsp;
	}

	@NotNull
	@NotEmpty
	@ElementCollection
	public Collection<String> getNationalities() {
		return this.nationalities;
	}

	public void setNationalities(final Collection<String> nationalities) {
		this.nationalities = nationalities;
	}

}
