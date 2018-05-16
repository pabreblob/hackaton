
package services;



import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import repositories.CarRepository;


import domain.Car;



@Service
@Transactional
public class CarService {

	
	@Autowired
	private CarRepository	carRepository;


	public CarService() {
		super();
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
	
	
}
