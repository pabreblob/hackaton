
package controllers;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
}
