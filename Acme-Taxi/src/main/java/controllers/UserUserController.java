
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
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import domain.User;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController {

	@Autowired
	private UserService	userService;


	@RequestMapping(value = "/list-reviewable", method = RequestMethod.GET)
	public ModelAndView list(final HttpServletRequest request) {
		ModelAndView res;
		final Collection<User> users;
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

		final Integer total = this.userService.countUsersReviewable();
		users = this.userService.findUsersReviewable(pageable);
		res = new ModelAndView("user/list");
		res.addObject("users", users);
		res.addObject("total", total);
		res.addObject("requestURI", "user/user/list-reviewable.do");
		return res;
	}

}
