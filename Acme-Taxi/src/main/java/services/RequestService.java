
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RequestRepository;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;


	public Request create() {
		final Request r = new Request();
		return r;
	}
}
