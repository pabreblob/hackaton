
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Car extends DomainEntity {

	private String		carModel;
	private int			maxPassengers;
	private String		numberPlate;

	private RepairShop	repairShop;


	public Car() {
		super();
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCarModel() {
		return this.carModel;
	}

	public void setCarModel(final String carModel) {
		this.carModel = carModel;
	}

	@Min(value = 1)
	public int getMaxPassengers() {
		return this.maxPassengers;
	}

	public void setMaxPassengers(final int maxPassengers) {
		this.maxPassengers = maxPassengers;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNumberPlate() {
		return this.numberPlate;
	}

	public void setNumberPlate(final String numberPlate) {
		this.numberPlate = numberPlate;
	}

	@Valid
	@ManyToOne(optional = true)
	public RepairShop getRepairShop() {
		return this.repairShop;
	}

	public void setRepairShop(final RepairShop repairShop) {
		this.repairShop = repairShop;
	}

}
