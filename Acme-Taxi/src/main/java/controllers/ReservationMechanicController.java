
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MechanicService;
import services.RepairShopService;
import services.ReservationService;


import domain.Mechanic;
import domain.RepairShop;
import domain.Reservation;



@Controller
@RequestMapping("/reservation/mechanic")
public class ReservationMechanicController extends AbstractController {

	@Autowired
	private RepairShopService		repairShopService;
	@Autowired
	private ReservationService		reservationService;
	@Autowired
	private MechanicService		mechanicService;



	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
		ModelAndView res;
		Collection<Reservation> reservations;
		Pageable pageable;
		Direction dir = null;
		Integer pageNum = 0;
		final Mechanic mechanic=this.mechanicService.findByPrincipal();
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
		final Integer total = this.reservationService.countByMechanic(mechanic.getId());
		reservations = this.reservationService.listByMechanic(mechanic.getId(), pageable);
		res = new ModelAndView("reservation/list");
		res.addObject("reservations", reservations);
		res.addObject("total", total);
		res.addObject("requestURI", "reservation/mechanic/list.do");
		return res;
	}
	@RequestMapping(value = "/list-shop", method = RequestMethod.GET)
	public ModelAndView listShop(@RequestParam final int repairShopId,final HttpServletRequest request) {
		ModelAndView res;
		Collection<Reservation> reservations;
		Pageable pageable;
		Direction dir = null;
		Integer pageNum = 0;
		final Mechanic mechanic=this.mechanicService.findByPrincipal();
		final RepairShop repairShop=this.repairShopService.findOne(repairShopId);
		Assert.isTrue(repairShop.getMechanic().getId()==mechanic.getId());
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
		final Integer total = this.reservationService.countByRepairShop(repairShopId);
		reservations = this.reservationService.listByRepairShop(repairShopId, pageable);
		res = new ModelAndView("reservation/list");
		res.addObject("reservations", reservations);
		res.addObject("total", total);
		res.addObject("requestURI", "reservation/mechanic/list-shop.do");
		return res;
	}
	
}
