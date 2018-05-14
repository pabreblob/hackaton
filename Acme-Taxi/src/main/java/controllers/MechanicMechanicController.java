
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import services.ConfigurationService;
import services.IdNumberPatternService;
import services.MechanicService;
import services.RepairShopService;

import domain.IdNumberPattern;
import domain.Mechanic;
import domain.RepairShop;


@Controller
@RequestMapping("/mechanic/mechanic")
public class MechanicMechanicController extends AbstractController {

	@Autowired
	private MechanicService		mechanicService;
	@Autowired
	private ConfigurationService		configurationService;
	@Autowired
	private IdNumberPatternService		idNumberPatternService;
	@Autowired
	private RepairShopService		repairShopService;



	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Mechanic mechanic = this.mechanicService.findByPrincipal();
		Collection <String> nationalities=this.configurationService.find().getNationalities();
	

		result = new ModelAndView("mechanic/edit");
		result.addObject("mechanic", mechanic);
		result.addObject("nationalities", nationalities);

		return result;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Mechanic mechanic, final BindingResult binding) {
ModelAndView result;
		
		final Mechanic res = this.mechanicService.reconstructEdition(mechanic, binding);
		
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(res);
		}else{
			int age;
			final LocalDate birth = new LocalDate(res.getBirthdate().getYear() + 1900, res.getBirthdate().getMonth() + 1, res.getBirthdate().getDate());
			final LocalDate now = new LocalDate();
			age=Years.yearsBetween(birth, now).getYears();
			boolean Idmatches=false;
			List<IdNumberPattern> patterns=new ArrayList<IdNumberPattern>(this.idNumberPatternService.findByNationality(res.getNationality()));
			for(IdNumberPattern p:patterns){
				Pattern pattern=Pattern.compile(p.getPattern());
				Matcher m=pattern.matcher(res.getIdNumber());
				if(m.find()){
					Idmatches=true;
					break;
				}
			}
			if (age<18) {
				result = this.createEditModelAndView(res, "mechanic.underage.error");
			}
			else if (!Idmatches) {
				result = this.createEditModelAndView(res, "mechanic.idNumber.error");
			}else{
				try {
					this.mechanicService.save(res);
					result = new ModelAndView("redirect:/welcome/index.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(res, "mechanic.commit.error");
				}
			}
			
		}
		return result;

	}

	protected ModelAndView createEditModelAndView(final Mechanic mechanic) {
		final ModelAndView result;
		result = this.createEditModelAndView(mechanic, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Mechanic mechanic, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("mechanic/edit");
		Collection <String> nationalities=this.configurationService.find().getNationalities();
		result.addObject("mechanic", mechanic);
		result.addObject("message", messageCode);
		result.addObject("nationalities", nationalities);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final HttpServletRequest request) {
		final ModelAndView result;
		final Mechanic mechanic=this.mechanicService.findByPrincipal();
		int mechanicId=mechanic.getId();
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
		final Integer total = this.repairShopService.countByMechanic(mechanicId);
		repairShops=this.repairShopService.listByMechanic(mechanicId, pageable);
		final String requestURI = "mechanic/mechanic/display.do";
			result = new ModelAndView("mechanic/display");
			result.addObject("mechanic", mechanic);
			result.addObject("repairShops", repairShops);
			result.addObject("requestURI", requestURI);
			result.addObject("total", total);
				
		return result;
	}
}
