
package services;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReservationRepository;


import domain.Actor;
import domain.Admin;
import domain.Message;
import domain.Message.Priority;
import domain.RepairShop;
import domain.Reservation;


import domain.SpamWord;


@Service
@Transactional
public class ReservationService {
	@Autowired
	private ReservationRepository		reservationRepository;
	@Autowired
	private MechanicService		mechanicService;
	@Autowired
	private RepairShopService	repairShopService;
	@Autowired
	private SpamWordService		spamWordService;
	@Autowired
	private UserService		userService;
	@Autowired
	private ServiceService		serviceService;
	@Autowired
	private MessageService		messageService;
	@Autowired
	private AdminService		adminService;


	public ReservationService() {
		super();
	}

	public Reservation create(domain.Service s) {
		final Reservation reservation = new Reservation();
		Collection<domain.Service> reservedServices=this.serviceService.findByUser(this.userService.findByPrincipal().getId());
		Assert.isTrue(!reservedServices.contains(s));
		Assert.isTrue(!s.isSuspended());
		reservation.setService(s);
		reservation.setUser(this.userService.findByPrincipal());

		return reservation;
	}
	public Reservation save(final Reservation reservation) {
		Assert.notNull(reservation);
		reservation.setUser(this.userService.findByPrincipal());
		Collection<domain.Service> reservedServices=this.serviceService.findByUser(this.userService.findByPrincipal().getId());
		Assert.isTrue(!reservation.getService().isSuspended());
		Assert.isTrue(!reservedServices.contains(reservation.getService()));
		
		final Collection<SpamWord> sw = this.spamWordService.findAll();
		boolean spamw = false;
		for (final SpamWord word : sw) {
			spamw = reservation.getComment().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (spamw)
				break;
		}
		if(!reservation.getUser().isSuspicious()){
			reservation.getUser().setSuspicious(spamw);
		}
		
		
		final Reservation res = this.reservationRepository.save(reservation);
		final Message mes = this.messageService.create();
		if(reservation.getService().getRepairShop().getMechanic().getNationality().equals("Spanish")){
			mes.setSubject("Nueva reserva");
		}else{
			mes.setSubject("New Reservation");
		}
		if(reservation.getService().getRepairShop().getMechanic().getNationality().equals("Spanish")){
			mes.setBody("Una reserva para el servicio " + reservation.getService().getTitle()+" de la tienda de reparaciones "+reservation.getService().getRepairShop().getName()+"ha sido solicitada por "+reservation.getUser().getUserAccount().getUsername());	
		}else{
			mes.setBody("A reservation for the service " + reservation.getService()+" from the repair shop "+reservation.getService().getRepairShop().getName()+"has been made by "+reservation.getUser().getUserAccount().getUsername());
		}
		
		mes.setPriority(Priority.NEUTRAL);
		List<Admin> admin=new ArrayList<Admin>(this.adminService.findAll());
		mes.setSender(admin.get(0));
		final Collection<Actor> recipients = new ArrayList<Actor>();
		recipients.add(reservation.getService().getRepairShop().getMechanic());
		mes.setRecipients(recipients);
		this.messageService.broadcast(mes);
		return res;
	}

	public Reservation findOne(final int idReservation) {
		Assert.isTrue(idReservation!= 0);
		
		final Reservation res = this.reservationRepository.findOne(idReservation);
		Assert.isTrue(res!=null);
		return res;
	}

	public Collection<Reservation> findAll() {
		final Collection<Reservation> res;
		res = this.reservationRepository.findAll();
		return res;
	}
	public Collection<Reservation> listByMechanic(final int mechanicId,final Pageable pageable) {
		final Collection<Reservation> res;
		res = this.reservationRepository.findCurrentReservationsByMechanic(mechanicId, pageable).getContent();
		return res;
	}
	public Integer countByMechanic(int mechanicId){
		Integer res=this.reservationRepository.countCurrentReservationsByMechanic(mechanicId);
		return res;
	}
	
	public Collection<Reservation> listByUser(final int userId,final Pageable pageable) {
		final Collection<Reservation> res;
		res = this.reservationRepository.findCurrentReservationsByUser(userId, pageable).getContent();
		return res;
	}
	public Integer countByUser(int userId){
		Integer res=this.reservationRepository.countCurrentReservationsByUser(userId);
		return res;
	}
	public Collection<Reservation> listByRepairShop(final int repairShopId,final Pageable pageable) {
		final Collection<Reservation> res;
		final RepairShop repairShop=this.repairShopService.findOne(repairShopId);
		Assert.isTrue(repairShop.getMechanic().getId()==this.mechanicService.findByPrincipal().getId());
		
		res = this.reservationRepository.findReservationsByRepairShop(repairShopId, pageable).getContent();
		return res;
	}
	public Integer countByRepairShop(int repairShopId){
		final RepairShop repairShop=this.repairShopService.findOne(repairShopId);
		Assert.isTrue(repairShop.getMechanic().getId()==this.mechanicService.findByPrincipal().getId());
		Integer res=this.reservationRepository.countCurrentReservationsByUser(repairShopId);
		return res;
	}
	public void cancel(int reservationId){
		Reservation reservation=this.findOne(reservationId);
		Assert.isTrue(this.userService.findByPrincipal().getId()==reservation.getUser().getId());
		reservation.setCancelled(true);
	}
	public Integer countByService(int serviceId){
		final domain.Service service=this.serviceService.findOne(serviceId);
		Integer res=this.reservationRepository.countCurrentReservationsByService(service.getId());
		return res;
	}
	public void delete(final Reservation reservation) {
		assert reservation != null;
		this.reservationRepository.delete(reservation.getId());
	}
	public Collection<Reservation> findAllByService (final int serviceId) {
		final Collection<Reservation> res;
		
		res = this.reservationRepository.findAllByService(serviceId);
		return res;
	}
	
}
