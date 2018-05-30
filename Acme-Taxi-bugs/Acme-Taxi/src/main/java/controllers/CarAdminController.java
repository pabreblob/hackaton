
package controllers;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CarService;

import services.DriverService;


import domain.Car;
import domain.Driver;




@Controller
@RequestMapping("/car/admin")
public class CarAdminController extends AbstractController {


	@Autowired
	private CarService		carService;
	@Autowired
	private DriverService		driverService;


	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int carId) {
		ModelAndView res;
		Car car=this.carService.findOne(carId);
		Driver driver=this.driverService.findDriverByCarId(carId);
		try {
			this.carService.delete(car);
			
				res = new ModelAndView("redirect:/driver/display.do?driverId="+driver.getId());
			
		} catch (final Exception e) {
				res = new ModelAndView("redirect:/car/display.do?carId="+carId);
			res.addObject("message", "car.commit.error");
		}
		return res;
	}
	
	
	
}
