
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.ReportService;

@Controller
@RequestMapping("/actor/admin")
public class ReportAdminController extends AbstractController {

	@Autowired
	private ReportService	reportService;
}
