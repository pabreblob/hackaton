
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

import domain.RepairShop;




@Controller
@RequestMapping("/repairShop/admin")
public class RepairShopAdminController extends AbstractController {

	@Autowired
	private RepairShopService		repairShopService;



	
	@RequestMapping(value = "/list-marked", method = RequestMethod.GET)
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
		final Integer total = this.repairShopService.countMarked();
		repairShops = this.repairShopService.listMarked(pageable);
		res = new ModelAndView("repairShop/list");
		res.addObject("repairShops", repairShops);
		res.addObject("total", total);
		res.addObject("requestURI", "repairShop/admin/list-marked.do");
		return res;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int repairShopId, final String requestURI) {
		ModelAndView res;
		final String redirect1 = "redirect:/repairShop/list.do";
		final String redirect2 = "redirect:list-marked.do";
		try {
			RepairShop repairShop=this.repairShopService.findOne(repairShopId);
			this.repairShopService.delete(repairShop);
			if (requestURI.equals("repairShop/admin/list-marked.do"))
				res = new ModelAndView(redirect2);
			else
				res = new ModelAndView(redirect1);
		} catch (final Exception e) {
			if (requestURI == "repairShop/admin/list-marked.do")
				res = new ModelAndView(redirect2);
			else
				res = new ModelAndView(redirect1);
			res.addObject("message", "repairShop.commit.error");
		}
		return res;
	}
}
