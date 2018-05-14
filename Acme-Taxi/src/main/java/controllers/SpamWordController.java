
package controllers;

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
import org.springframework.web.servlet.ModelAndView;

import services.SpamWordService;
import domain.SpamWord;

@Controller
@RequestMapping("/spamWord")
public class SpamWordController extends AbstractController {

	@Autowired
	private SpamWordService	spamWordService;


	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView list() {
	//		final ModelAndView res = new ModelAndView("spamWord/list");
	//		res.addObject("spamWords", this.spamWordService.findAll());
	//		return res;
	//	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
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
			pageable = new PageRequest(pageNum, 20, dir, sortAtt);
		else
			pageable = new PageRequest(pageNum, 20);
		final ModelAndView res = new ModelAndView("spamWord/list");
		res.addObject("spamWords", this.spamWordService.getSpamWords(pageable));
		res.addObject("total", this.spamWordService.countSpamWords());
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("spamWord/edit");
		res.addObject("spamWord", this.spamWordService.create());
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int spamWordId) {
		final ModelAndView res = new ModelAndView("spamWord/edit");
		final SpamWord sp = this.spamWordService.findOne(spamWordId);
		res.addObject("spamWord", sp);
		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final SpamWord sp, final BindingResult binding) {
		if (binding.hasErrors()) {
			final ModelAndView res = new ModelAndView("spamWord/edit");
			res.addObject("spamWord", sp);
			return res;
		} else
			try {
				if (sp.getId() == 0)
					this.spamWordService.addWord(sp.getWord());
				else {
					if (sp.getWord().contains(","))
						throw new IllegalArgumentException();
					this.spamWordService.save(sp);
				}
				return new ModelAndView("redirect: list.do");
			} catch (final Throwable oops) {
				final ModelAndView res = new ModelAndView("spamWord/edit");
				res.addObject("spamWord", sp);
				if (sp.getId() != 0 && sp.getWord().contains(","))
					res.addObject("message", "spamWord.cantContainAComma");
				else
					res.addObject("message", "spamWord.cannotCommit");
				return res;
			}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int spamWordId) {
		final SpamWord sp = this.spamWordService.findOne(spamWordId);
		this.spamWordService.delete(sp);
		return new ModelAndView("redirect: list.do");
	}
}
