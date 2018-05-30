
package controllers;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Admin;


import services.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

	@Autowired
	private AdminService	adminService;
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final Integer adminId,final HttpServletRequest request) {
		final ModelAndView result;
		final Admin admin=this.adminService.findOne(adminId);
		final String requestURI = "admin/display.do";
			result = new ModelAndView("admin/display");
			result.addObject("admin", admin);
			result.addObject("requestURI", requestURI);
			
				
		return result;
	}
	
}
