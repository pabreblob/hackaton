
package controllers;


import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;

import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MechanicService;
import services.RepairShopService;

import domain.RepairShop;



@Controller
@RequestMapping("/repairShop/mechanic")
public class RepairShopMechanicController extends AbstractController {

	@Autowired
	private RepairShopService		repairShopService;
	@Autowired
	private MechanicService		mechanicService;



	
	@RequestMapping(value = "/list-created", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
		ModelAndView res;
		Collection<RepairShop> repairShops;
		Pageable pageable;
		Direction dir = null;
		Integer pageNum = 0;
		final String pageNumStr = request.getParameter(new ParamEncoder("row").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
		final String sortAtt = request.getParameter(new ParamEncoder("row").encodeParameterName(TableTagParameters.PARAMETER_SORT));
		final String sortOrder = request.getParameter(new ParamEncoder("row").encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		if (sortOrder != null)
			if (sortOrder.equals("1"))
				dir = Direction.ASC;
			else
				dir = Direction.DESC;
		if (pageNumStr != null)
			pageNum = Integer.parseInt(pageNumStr) - 1;
		if (sortAtt != null && dir != null)
			pageable = new PageRequest(pageNum, 5, dir, sortAtt);
		else
			pageable = new PageRequest(pageNum, 5);
		final Integer total = this.repairShopService.countByMechanic(this.mechanicService.findByPrincipal().getId());
		repairShops = this.repairShopService.listByMechanic(this.mechanicService.findByPrincipal().getId(), pageable);
		res = new ModelAndView("repairShop/list");
		res.addObject("repairShops", repairShops);
		res.addObject("total", total);
		res.addObject("requestURI", "repairShop/mechanic/list-created.do");
		return res;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res;
		final RepairShop r = this.repairShopService.create();
		res = this.createEditModelAndView(r);

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int repairShopId) {
		ModelAndView res;
		RepairShop r;
		r = this.repairShopService.findOne(repairShopId);
		Assert.isTrue(r.getMechanic().getId()==this.mechanicService.findByPrincipal().getId());
		res = this.createEditModelAndView(r);
		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RepairShop r, final BindingResult binding) {
		ModelAndView res;
		final RepairShop repairShop = this.repairShopService.reconstruct(r, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(repairShop);
		else
			try {
				this.repairShopService.save(repairShop);
				res = new ModelAndView("redirect:list-created.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(repairShop, "repairShop.commit.error");
			}
		return res;
	}
		protected ModelAndView createEditModelAndView(final RepairShop r) {
			return this.createEditModelAndView(r, null);
		}
		protected ModelAndView createEditModelAndView(final RepairShop r, final String messageCode) {
			ModelAndView res;
			res = new ModelAndView("repairShop/edit");
			res.addObject("repairShop", r);
			
			return res;
		}
}
