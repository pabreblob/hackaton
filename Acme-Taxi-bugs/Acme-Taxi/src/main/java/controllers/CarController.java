
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
import domain.RepairShop;



@Controller
@RequestMapping("/car")
public class CarController extends AbstractController {


	@Autowired
	private CarService		carService;
	@Autowired
	private DriverService		driverService;



	
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final Integer carId) {
		final ModelAndView result;
		final Car car=this.carService.findOne(carId);
		final RepairShop repairShop=car.getRepairShop();
		final Driver driver=this.driverService.findDriverByCarId(carId);
		final String requestURI = "car/display.do";
			result = new ModelAndView("car/display");
			result.addObject("driver", driver);
			result.addObject("car", car);
			result.addObject("repairShop", repairShop);
			result.addObject("requestURI", requestURI);
				
		return result;
	}
}
