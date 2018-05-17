
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.RequestService;
import utilities.GoogleMaps;
import domain.Configuration;
import domain.Request;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {

	@Autowired
	private RequestService			requestService;
	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("request/edit");
		res.addObject("request", this.requestService.create());
		return res;
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ModelAndView confirm(final Request request, final BindingResult bindingResult) {
		final Request r = this.requestService.reconstruct(request, bindingResult);
		if (bindingResult.hasErrors()) {
			final ModelAndView res = new ModelAndView("request/edit");
			res.addObject("request", r);
			return res;
		}
		final List<Integer> distanciaYTiempo = GoogleMaps.getDistanceAndDuration(request.getOrigin(), request.getDestination());
		try {
			Assert.notNull(distanciaYTiempo);
			final Configuration conf = this.configurationService.find();
			double price = (1 + conf.getVat()) * (conf.getMinimumFee() + (conf.getPricePerKm() * (distanciaYTiempo.get(0) * 1.0 / 1000)));
			int temp = (int) (price * 100);
			price = (temp * 1.0) / 100;
			r.setPrice(price);
			r.setDistance(distanciaYTiempo.get(0));
			double time = 1.0 * distanciaYTiempo.get(1) / 3600;
			temp = (int) (time * 100);
			time = (temp * 1.0) / 100;
			r.setEstimatedTime(time);
			final ModelAndView res = new ModelAndView("request/confirm");
			res.addObject("currency", conf.getCurrency());
			res.addObject("request", r);
			String estimated = "";
			final int hours = distanciaYTiempo.get(1) / 3600;
			final int minutes = (distanciaYTiempo.get(1) - hours * 3600) / 60;
			estimated = hours + "h " + minutes + "min";
			res.addObject("estimated", estimated);
			return res;
		} catch (final Throwable oops) {
			if (distanciaYTiempo == null) {
				final ModelAndView res = new ModelAndView("request/edit");
				res.addObject("request", r);
				res.addObject("message", "request.originOrDestinationFail");
				return res;
			}
			final ModelAndView res = new ModelAndView("request/edit");
			res.addObject("request", r);
			res.addObject("message", "request.cannotCommit");
			return res;
		}
	}

}
