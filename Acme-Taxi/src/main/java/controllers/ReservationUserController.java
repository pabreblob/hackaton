
package controllers;


import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ReservationService;
import services.ServiceService;
import services.UserService;

import domain.Reservation;
import domain.Service;
import domain.User;



@Controller
@RequestMapping("/reservation/user")
public class ReservationUserController extends AbstractController {


	@Autowired
	private ReservationService		reservationService;

	@Autowired
	private ServiceService		serviceService;
	@Autowired
	private UserService		userService;



	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
		ModelAndView res;
		Collection<Reservation> reservations;
		Pageable pageable;
		Direction dir = null;
		Integer pageNum = 0;
		final User user=this.userService.findByPrincipal();
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
		final Integer total = this.reservationService.countByUser(user.getId());
		reservations = this.reservationService.listByUser(user.getId(), pageable);
		res = new ModelAndView("reservation/list");
		res.addObject("reservations", reservations);
		res.addObject("total", total);
		res.addObject("requestURI", "reservation/user/list.do");
		return res;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int serviceId) {
		final ModelAndView res;
		Service s=this.serviceService.findOne(serviceId);
		final Reservation r = this.reservationService.create(s);
		res = this.createEditModelAndView(r);

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Reservation r, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndView(r);
		else{
			final Date now = new Date();
			if(r.getMoment().before(now)){
				res = this.createEditModelAndView(r, "reservation.moment.error");
			}else{
				
		
			try {
				this.reservationService.save(r);
				int repairShopId=r.getService().getRepairShop().getId();
				res = new ModelAndView("redirect:/repairShop/user/display.do?repairShopId="+repairShopId);
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(r, "reservation.commit.error");
			}
		}	}return res;
		
	}
		protected ModelAndView createEditModelAndView(final Reservation r) {
			return this.createEditModelAndView(r, null);
		}
		protected ModelAndView createEditModelAndView(final Reservation r, final String messageCode) {
			ModelAndView res;
			res = new ModelAndView("reservation/edit");
			res.addObject("reservation", r);
			res.addObject("message", messageCode);
			
			return res;
		}
		@RequestMapping(value = "/cancel", method = RequestMethod.GET)
		public ModelAndView suspend(@RequestParam final int reservationId) {
			final ModelAndView res;
			this.reservationService.cancel(reservationId);
			res = new ModelAndView("redirect:list.do");

			return res;
		}
}
