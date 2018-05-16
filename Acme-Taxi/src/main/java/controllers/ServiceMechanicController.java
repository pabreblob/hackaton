
package controllers;





import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MechanicService;
import services.RepairShopService;
import services.ReservationService;
import services.ServiceService;
import domain.Mechanic;
import domain.RepairShop;
import domain.Service;



@Controller
@RequestMapping("/service/mechanic")
public class ServiceMechanicController extends AbstractController {

	@Autowired
	private RepairShopService		repairShopService;
	@Autowired
	private MechanicService		mechanicService;
	@Autowired
	private ServiceService		serviceService;
	@Autowired
	private ReservationService		reservationService;



	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int repairShopId) {
		final ModelAndView res;
		final RepairShop repairShop=this.repairShopService.findOne(repairShopId);
		final Service s = this.serviceService.create(repairShop);
		res = this.createEditModelAndView(s);

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int serviceId) {
		ModelAndView res;
		Service s;
		s= this.serviceService.findOne(serviceId);
		if(s.getId()!=0){
			int pendingReserves=this.reservationService.countByService(s.getId());
			Assert.isTrue(pendingReserves==0);
		}
		Assert.isTrue(s.getRepairShop().getMechanic().getId()==this.mechanicService.findByPrincipal().getId());
		res = this.createEditModelAndView(s);
		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Service s, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndView(s);
		else
			try {
				this.serviceService.save(s);
				res = new ModelAndView("redirect:/repairShop/mechanic/display.do?repairShopId="+s.getRepairShop().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(s, "service.commit.error");
			}
		return res;
	}
		protected ModelAndView createEditModelAndView(final Service s) {
			return this.createEditModelAndView(s, null);
		}
		protected ModelAndView createEditModelAndView(final Service s, final String messageCode) {
			ModelAndView res;
			res = new ModelAndView("service/edit");
			res.addObject("service", s);
			res.addObject("message",messageCode);
			
			
			return res;
		}
		@RequestMapping(value = "/suspend-service", method = RequestMethod.GET)
		public ModelAndView suspend(@RequestParam final int serviceId) {
			final ModelAndView res;
			int repairShopId=this.serviceService.findOne(serviceId).getRepairShop().getId();
			this.serviceService.suspend(serviceId);
			res = new ModelAndView("redirect:/repairShop/mechanic/display.do?repairShopId="+repairShopId);

			return res;
		}
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int serviceId) {
			final ModelAndView result;
			Service service=this.serviceService.findOne(serviceId);
			Mechanic mechanic=this.mechanicService.findByPrincipal();
			Assert.isTrue(service.getRepairShop().getMechanic().getId()==mechanic.getId());
			int pendingReservations=this.reservationService.countByService(serviceId);
			result = new ModelAndView("service/display");
			result.addObject("service", service);
			result.addObject("pendingReservations", pendingReservations);
			result.addObject("requestURI", "service/mechanic/display.do");

			return result;
		}
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(final @Valid Service service, final BindingResult binding) {
			ModelAndView result;
			if (binding.hasErrors())
				result= this.createEditModelAndView(service);
			else
			try {
				int pendingReservations=this.reservationService.countByService(service.getId());
				Assert.isTrue(pendingReservations==0);
				final RepairShop repairShop=service.getRepairShop();
				this.serviceService.delete(service);
				result = new ModelAndView("redirect:/repairShop/mechanic/display.do?repairShopId="+repairShop.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(service, "service.delete.error");
			}

			return result;
		}
		
}
