
package controllers;

import java.text.DecimalFormat;


import javax.servlet.http.HttpServletRequest;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Admin;

import services.AdminService;

@Controller
@RequestMapping("/admin/admin")
public class AdminAdminController extends AbstractController {

	@Autowired
	private AdminService	adminService;


	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView res = new ModelAndView("admin/dashboard");
		final DecimalFormat df = new DecimalFormat("#,###,##0.00");
		res.addObject("maxAttendants", this.adminService.getMaxAttendants());
		res.addObject("minAttendants", this.adminService.getMinAttendants());
		res.addObject("avgAttendants", df.format(this.adminService.getAvgAttendants()));
		res.addObject("stdAttendants", df.format(this.adminService.getStandardDeviationAttendants()));
		res.addObject("topUsers", this.adminService.getTopUsers());
		res.addObject("topDrivers", this.adminService.getTopDrivers());
		res.addObject("topMechanics", this.adminService.getTopMechanics());
		res.addObject("worstUsers", this.adminService.getWorstUsers());
		res.addObject("worstDrivers", this.adminService.getWorstDrivers());
		res.addObject("worstMechanics", this.adminService.getWorstMechanics());
		res.addObject("cancelledAnnouncements", df.format(this.adminService.getRatioCancelledAnnouncements()));
		res.addObject("mostWritten", this.adminService.getMostReportsWritten());
		res.addObject("mostReceived", this.adminService.getMostReportsReceived());
		return res;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final HttpServletRequest request) {
		final ModelAndView result;
		final Admin admin=this.adminService.findByPrincipal();
		final String requestURI = "admin/admin/display.do";
			result = new ModelAndView("admin/display");
			result.addObject("admin", admin);
			result.addObject("requestURI", requestURI);
			
				
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Admin admin = this.adminService.findByPrincipal();
		
	

		result = new ModelAndView("admin/edit");
		result.addObject("admin", admin);
		

		return result;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Admin admin, final BindingResult binding) {
ModelAndView result;
		
		final Admin res = this.adminService.reconstruct(admin, binding);
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(res);
		}else{
			int age;
			final LocalDate birth = new LocalDate(res.getBirthdate().getYear() + 1900, res.getBirthdate().getMonth() + 1, res.getBirthdate().getDate());
			final LocalDate now = new LocalDate();
			age=Years.yearsBetween(birth, now).getYears();
			if (age<18) {
				result = this.createEditModelAndView(res, "admin.underage.error");
			}
			else{
				try {
					this.adminService.save(res);
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(res, "admin.commit.error");
				}
			}
			
		}
		return result;

	}

	protected ModelAndView createEditModelAndView(final Admin admin) {
		final ModelAndView result;
		result = this.createEditModelAndView(admin, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Admin admin, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("admin/edit");
		
		result.addObject("admin", admin);
		result.addObject("message", messageCode);
		
		return result;
	}
	
}
