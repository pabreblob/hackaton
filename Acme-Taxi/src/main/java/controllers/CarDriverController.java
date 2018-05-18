
package controllers;




import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CarService;
import services.RequestService;

import services.DriverService;


import domain.Car;
import domain.Driver;
import domain.RepairShop;
import domain.Request;
import domain.Service;



@Controller
@RequestMapping("/car/driver")
public class CarDriverController extends AbstractController {

	@Autowired
	private CarService		carService;
	@Autowired
	private DriverService		driverService;
	@Autowired
	private RequestService		requestService;



	
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result;
		final Car car=this.driverService.findByPrincipal().getCar();
		Assert.notNull(car);
		final RepairShop repairShop=car.getRepairShop();
		final Driver driver=this.driverService.findDriverByCarId(car.getId());
		int pendingRequests=this.requestService.findRequestByDriverToDo(this.driverService.findByPrincipal().getId()).size();
			
		
		final String requestURI = "car/driver/display.do";
			result = new ModelAndView("car/display");
			
			result.addObject("car", car);
			
			result.addObject("repairShop", repairShop);
			result.addObject("driver", driver);
			result.addObject("pendingRequests", pendingRequests);
			result.addObject("requestURI", requestURI);
				
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res;
		final Car c = this.carService.create();
		res = this.createEditModelAndView(c);

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int carId) {
		ModelAndView res;
		Car c;
		c = this.carService.findOne(carId);
		Driver driver=this.driverService.findDriverByCarId(c.getId());
		Assert.isTrue(this.driverService.findByPrincipal().getId()==driver.getId());
		Collection<Request> pendingRequests=this.requestService.findRequestByDriverToDo(this.driverService.findByPrincipal().getId());
		Assert.isTrue(pendingRequests.isEmpty());
		res = this.createEditModelAndView(c);
		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Car c, final BindingResult binding) {
		ModelAndView res;
		final Car car = this.carService.reconstruct(c, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(car);
		else
			try {
				Car saved=this.carService.save(car);
				res = new ModelAndView("redirect:display.do?carId="+saved.getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(car, "car.commit.error");
			}
		return res;
	}
		protected ModelAndView createEditModelAndView(final Car c) {
			return this.createEditModelAndView(c, null);
		}
		protected ModelAndView createEditModelAndView(final Car c, final String messageCode) {
			ModelAndView res;
			res = new ModelAndView("car/edit");
			res.addObject("car", c);
			Driver driver=this.driverService.findByPrincipal();
			res.addObject("message", messageCode);
			res.addObject("driver", driver);
			
			return res;
		}
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(final Car c, final BindingResult binding) {
			ModelAndView result;
			final Car car = this.carService.reconstruct(c, binding);
			if (binding.hasErrors())
				result= this.createEditModelAndView(car);
			else
			try {
				Collection<Request> pendingRequests=this.requestService.findRequestByDriverToDo(this.driverService.findByPrincipal().getId());
				Assert.isTrue(pendingRequests.isEmpty());
				this.carService.delete(car);
				result = new ModelAndView("redirect:/driver/driver/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(c, "car.delete.error");
			}

			return result;
		}
		@RequestMapping(value = "/select-shop", method = RequestMethod.GET)
		public ModelAndView select(@RequestParam final int repairShopId) {
			ModelAndView res;
			try {
			this.carService.changeRepairShop(repairShopId);
			res = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/misc/panic");
			}
			
			return res;
		}
		@RequestMapping(value = "/remove-shop", method = RequestMethod.GET)
		public ModelAndView removeShop() {
			ModelAndView res;
			try {
			this.carService.RemoveRepairShop();
			res = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/misc/panic");
			}
			
			return res;
		}
}
