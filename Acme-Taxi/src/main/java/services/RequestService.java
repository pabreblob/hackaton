
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import utilities.GoogleMaps;
import domain.Configuration;
import domain.Request;
import domain.SpamWord;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository		requestRepository;
	@Autowired
	private UserService				userService;
	@Autowired
	private Validator				validator;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private SpamWordService			spamWordService;
	@Autowired
	private DriverService			driverService;


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

	public Request save(final Request r) {
		final LocalDate now = new LocalDate();
		final LocalDate moment = new LocalDate(r.getMoment());
		Assert.isTrue(moment.isAfter(now));

		final List<Integer> distanceAndTime = GoogleMaps.getDistanceAndDuration(r.getOrigin(), r.getDestination());
		Assert.notNull(distanceAndTime);

		r.setDistance(distanceAndTime.get(0));
		r.setEstimatedTime(distanceAndTime.get(1));

		final Configuration conf = this.configurationService.find();
		double price = (1 + conf.getVat()) * (conf.getMinimumFee() + (conf.getPricePerKm() * (distanceAndTime.get(0) * 1.0 / 1000)));
		final int temp = (int) price * 100;
		price = 1.0 * temp / 100;
		r.setPrice(price);
		boolean spamw = false;
		for (final SpamWord word : this.spamWordService.findAll()) {
			spamw = r.getComment().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (spamw)
				break;
		}
		r.setMarked(spamw);
		if (spamw)
			this.userService.findByPrincipal().setSuspicious(spamw);
		final Request saved = this.requestRepository.save(r);
		return saved;
	}

	public Collection<Request> findRequestByDriverToDo(final int driverId) {
		return this.requestRepository.findRequestByDriverToDo(driverId);
	}
	public Collection<Request> getRequestByUser(final int userId, final Pageable pageable) {
		return this.requestRepository.getRequestByUser(userId, pageable).getContent();
	}
	public Integer countRequestByUser(final int userId) {
		return this.requestRepository.countRequestByUser(userId);
	}
	public Collection<Request> getRequestToAccept(final Pageable pageable) {
		if (this.driverService.findByPrincipal().getCar() == null)
			return new ArrayList<>();
		else
			return this.requestRepository.getRequestToAccept(this.driverService.findByPrincipal().getCar().getMaxPassengers(), pageable).getContent();
	}
	public Integer countRequestToAccept() {
		if (this.driverService.findByPrincipal().getCar() == null)
			return 0;
		else
			return this.requestRepository.countRequestToAccept(this.driverService.findByPrincipal().getCar().getMaxPassengers());
	}
	public Collection<Request> getRequestToDo(final Pageable pageable) {
		return this.requestRepository.getRequestToDo(this.driverService.findByPrincipal().getId(), pageable).getContent();
	}
	public Integer countRequestToDo() {
		return this.requestRepository.countRequestToDo(this.driverService.findByPrincipal().getId());
	}
	public Collection<Request> getFinishedRequest(final Pageable pageable) {
		return this.requestRepository.getFinishedRequest(this.driverService.findByPrincipal().getId(), pageable).getContent();
	}
	public Integer countFinishedRequest() {
		return this.requestRepository.countFinishedRequest(this.driverService.findByPrincipal().getId());
	}
	public Collection<Request> getMarked(final Pageable pageable) {
		return this.requestRepository.getMarked(pageable).getContent();
	}
	public Integer countMarked() {
		return this.requestRepository.countMarked();
	}
	public Collection<Request> getAll(final Pageable pageable) {
		return this.requestRepository.getAll(pageable).getContent();
	}
	public Integer countAll() {
		return this.requestRepository.countAll();
	}
}