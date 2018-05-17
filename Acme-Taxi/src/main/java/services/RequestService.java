
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;
	@Autowired
	private UserService			userService;
	@Autowired
	private Validator			validator;


	public Request create() {
		final Request r = new Request();
		r.setPassengersNumber(1);
		return r;
	}

	public Request reconstruct(final Request request, final BindingResult bindingResult) {
		request.setId(0);
		request.setVersion(0);
		request.setCancelled(false);
		request.setDistance(0);
		request.setDriver(null);
		request.setEstimatedTime(0);
		request.setPrice(0);
		request.setMarked(false);
		request.setUser(this.userService.findByPrincipal());
		this.validator.validate(request, bindingResult);
		return request;
	}
}
