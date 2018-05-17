
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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RepairShopService;

import services.ConfigurationService;
import services.ServiceService;
import services.UserService;

import domain.RepairShop;

import domain.Service;
import domain.User;



@Controller
@RequestMapping("/repairShop/user")
public class RepairShopUserController extends AbstractController {

	@Autowired
	private RepairShopService		repairShopService;
	@Autowired
	private ServiceService		serviceService;
	@Autowired
	private UserService		userService;
	@Autowired
	private ConfigurationService		configurationsService;



	
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final Integer repairShopId,final HttpServletRequest request) {
		final ModelAndView result;
		Collection<domain.Service> services;
		Pageable pageable;
		Direction dir = null;
		Integer pageNum = 0;
		final User user=this.userService.findByPrincipal();
		Collection<Service> servicesReserved=this.serviceService.findByUser(user.getId());
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
		final Integer total = this.serviceService.countByRepairShop(repairShopId);
		services=this.serviceService.findByRepairShop(repairShopId, pageable);
		final RepairShop repairShop=this.repairShopService.findOne(repairShopId);
		boolean hasReviews=!repairShop.getReviews().isEmpty();
		final String currency=this.configurationsService.find().getCurrency();
		final String requestURI = "repairShop/user/display.do";
			result = new ModelAndView("repairShop/display");
			result.addObject("repairShop", repairShop);
			result.addObject("services", services);
			result.addObject("hasReviews", hasReviews);
			result.addObject("currency", currency);
			result.addObject("servicesReserved", servicesReserved);
			result.addObject("requestURI", requestURI);
			result.addObject("total", total);
				
		return result;
	}
	@RequestMapping(value = "/list-reviewable", method = RequestMethod.GET)
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
		
		repairShops = this.repairShopService.listRepairShopsReviewable(pageable);
		final Integer total = this.repairShopService.countReviewable();
		res = new ModelAndView("repairShop/list");
		res.addObject("repairShops", repairShops);
		res.addObject("total", total);
		res.addObject("requestURI", "repairShop/user/list-reviewable.do");
		return res;
	}
}
