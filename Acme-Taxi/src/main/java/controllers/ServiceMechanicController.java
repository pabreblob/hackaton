
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
import services.ServiceService;

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
				int repairShopId=s.getRepairShop().getId();
				res = new ModelAndView("redirect:/repairShop/mechanic/display.do?repairShopId="+repairShopId);
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

		
}
