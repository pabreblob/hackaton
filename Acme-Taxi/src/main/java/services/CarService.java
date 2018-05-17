
package services;




import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;


import repositories.CarRepository;


import domain.Car;
import domain.Driver;
import domain.RepairShop;

import domain.SpamWord;



@Service
@Transactional
public class CarService {

	
	@Autowired
	private CarRepository	carRepository;
	@Autowired
	private RepairShopService	repairShopService;
	@Autowired
	private SpamWordService	spamWordService;
	@Autowired
	private DriverService	driverService;
	@Autowired
	private Validator	validator;


	public CarService() {
		super();
	}
	public Car create() {
		final Car car = new Car();
		Assert.isTrue(this.driverService.findByPrincipal().getCar()==null);
		return car;
	}
	public Car save(final Car car) {
		Assert.notNull(car);
		if(car.getId()!=0){
			Driver driver=this.driverService.findDriverByCarId(car.getId());
			Assert.isTrue(this.driverService.findByPrincipal().getId()==driver.getId());
		}
		
		final Collection<SpamWord> sw = this.spamWordService.findAll();
		boolean spamw = false;
		for (final SpamWord word : sw) {
			spamw = car.getCarModel().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			
			if (spamw)
				break;
		}
		if(!this.driverService.findByPrincipal().isSuspicious()){
			this.driverService.findByPrincipal().setSuspicious(spamw);
		}
		
		final Car res = this.carRepository.save(car);
		this.driverService.findByPrincipal().setCar(res);
		return res;
	}

	

	public Car findOne(final int carId) {
		Assert.isTrue(carId!= 0);
		
		final Car res=this.carRepository.findOne(carId);
		Assert.isTrue(res!=null);
		return res;
	}

	public Collection<Car> findAll() {
		final Collection<Car> res;
		res = this.carRepository.findAll();
		return res;
	}
	public Collection<Car> findByRepairShop(final int repairShopId) {
		final Collection<Car> res;
		res = this.carRepository.findCarsByRepairShop(repairShopId);
		return res;
	}
	public void delete(final Car car) {
		assert car != null;
		Driver driver=this.driverService.findDriverByCarId(car.getId());
		driver.setCar(null);
		this.carRepository.delete(car.getId());
	}
	public void changeRepairShop(final int repairShopId) {
		Car car=this.driverService.findByPrincipal().getCar();
		Assert.isTrue(car!=null);
		RepairShop repairShop=this.repairShopService.findOne(repairShopId);
		car.setRepairShop(repairShop);
	}
	public void RemoveRepairShop(final int carId) {
		Car car=this.findOne(carId);
		Assert.isTrue(this.driverService.findByPrincipal().getId()==this.driverService.findDriverByCarId(car.getId()).getId());
		car.setRepairShop(null);
	}
	public Car reconstruct(final Car car, final BindingResult binding) {
		Car res;
		if(car.getId()==0){
			res = car;
			car.setRepairShop(null);
		}else{
			Assert.isTrue(this.driverService.findByPrincipal().getId()==this.driverService.findDriverByCarId(car.getId()).getId());
			res=car;
			car.setRepairShop(this.findOne(car.getId()).getRepairShop());
			
			
			
		}
		
		
		this.validator.validate(res, binding);
		return res;
	}
	
	
}
