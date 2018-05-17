
package services;



import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import repositories.CarRepository;


import domain.Car;
import domain.RepairShop;
import domain.Review;
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


	public CarService() {
		super();
	}
	public Car create(int repairShopId) {
		final Car car = new Car();
		car.setRepairShop(this.repairShopService.findOne(repairShopId));
		return car;
	}
//	public RepairShop save(final Car car) {
//		Assert.notNull(car);
//		final Collection<SpamWord> sw = this.spamWordService.findAll();
//		boolean spamw = false;
//		for (final SpamWord word : sw) {
//			spamw = car.getCarModel().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
//			
//			if (spamw)
//				break;
//		}
//		car.getDriver().setSuspicious(spamw);
//		final RepairShop res = this.repairShopRepository.save(repairShop);
//		return res;
//	}

	

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
	
	
}
