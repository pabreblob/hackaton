
package services;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.ServiceRepository;
import domain.RepairShop;
import domain.Reservation;
import domain.SpamWord;


@Service
@Transactional
public class ServiceService {

	@Autowired
	private MechanicService		mechanicService;
	@Autowired
	private SpamWordService	spamWordService;
	@Autowired
	private ServiceRepository	serviceRepository;
	@Autowired
	private ReservationService reservationService;

	public ServiceService() {
		super();
	}

	public domain.Service create(RepairShop repairShop) {
		final domain.Service service=new domain.Service();
		Assert.isTrue(repairShop.getMechanic().getId()==this.mechanicService.findByPrincipal().getId());
		service.setRepairShop(repairShop);

		return service;
	}
	public domain.Service save(final domain.Service service) {
		Assert.notNull(service);
		Assert.isTrue(service.getRepairShop().getMechanic().getId()==this.mechanicService.findByPrincipal().getId());
		if(service.getId()!=0){
			int pendingReserves=this.reservationService.countByService(service.getId());
			Assert.isTrue(pendingReserves==0);
		}
		final Collection<SpamWord> sw = this.spamWordService.findAll();
		boolean spamw = false;
		for (final SpamWord word : sw) {
			spamw = service.getTitle().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (spamw)
				break;
		}
		service.getRepairShop().setMarked(spamw);
		if(!service.getRepairShop().getMechanic().isSuspicious()){
			service.getRepairShop().getMechanic().setSuspicious(spamw);
		}
		
		final domain.Service res = this.serviceRepository.save(service);
		return res;
	}

	public domain.Service findOne(final int serviceId) {
		Assert.isTrue(serviceId!= 0);
		
		final domain.Service res = this.serviceRepository.findOne(serviceId);
		Assert.isTrue(res!=null);
		return res;
	}

	public Collection<domain.Service> findAll() {
		final Collection<domain.Service> res;
		res = this.serviceRepository.findAll();
		return res;
	}
	public Collection<domain.Service> findByRepairShop(final int repairShopId,final Pageable pageable) {
		final Collection<domain.Service> res;
		res = this.serviceRepository.findServicesByRepairShop(repairShopId, pageable).getContent();
		return res;
	}
	public Integer countByRepairShop(final int repairShopId){
		Integer res=this.serviceRepository.countServicesByRepairShop(repairShopId);
		return res;
	}
	public void suspend(int serviceId){
		domain.Service service=this.findOne(serviceId);
		Assert.isTrue(service.getRepairShop().getMechanic().getId()==this.mechanicService.findByPrincipal().getId());
		if(service.isSuspended()){
			service.setSuspended(false);
		}else{
			service.setSuspended(true);
		}
	}
	public Collection<domain.Service> findByUser(final int userId) {
		final Collection<domain.Service> res;
		res = this.serviceRepository.findServicesByUser(userId);
		return res;
	}
	public void delete(final domain.Service service) {
		assert service != null;
		Collection <Reservation> reservations=this.reservationService.findAllByService(service.getId());
		for(Reservation r: reservations){
			this.reservationService.delete(r);
		}
		this.serviceRepository.delete(service.getId());
	}
	public Collection<domain.Service> findAllByRepairShop(final int repairShopId) {
		final Collection<domain.Service> res;
		res = this.serviceRepository.findAllServicesByRepairShop(repairShopId);
		return res;
	}
}
