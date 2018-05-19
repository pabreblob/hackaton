
package services;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RepairShopRepository;

import domain.Car;
import domain.RepairShop;
import domain.Review;
import domain.SpamWord;


@Service
@Transactional
public class RepairShopService {

	@Autowired
	private MechanicService		mechanicService;
	@Autowired
	private RepairShopRepository	repairShopRepository;
	@Autowired
	private Validator	validator;
	@Autowired
	private SpamWordService		spamWordService;
	@Autowired
	private ServiceService		serviceService;
	@Autowired
	private UserService		userService;
	@Autowired
	private CarService		carService;


	public RepairShopService() {
		super();
	}

	public RepairShop create() {
		final RepairShop repairShop = new RepairShop();
		repairShop.setMechanic(this.mechanicService.findByPrincipal());
		repairShop.setReviews(new ArrayList<Review>());

		return repairShop;
	}
	public RepairShop save(final RepairShop repairShop) {
		Assert.notNull(repairShop);
		Assert.isTrue(repairShop.getMechanic().getId()==this.mechanicService.findByPrincipal().getId());
		final Collection<SpamWord> sw = this.spamWordService.findAll();
		boolean spamw = false;
		for (final SpamWord word : sw) {
			spamw = repairShop.getName().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			spamw |= repairShop.getDescription().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			spamw |= repairShop.getLocation().toLowerCase().matches(".*\\b" + word.getWord() + "\\b.*");
			if (spamw)
				break;
		}
		repairShop.setMarked(spamw);
		if(!repairShop.getMechanic().isSuspicious()){
			repairShop.getMechanic().setSuspicious(spamw);
		}
		
		final RepairShop res = this.repairShopRepository.save(repairShop);
		return res;
	}

	public RepairShop findOne(final int idRepairShop) {
		Assert.isTrue(idRepairShop!= 0);
		
		final RepairShop res = this.repairShopRepository.findOne(idRepairShop);
		Assert.isTrue(res!=null);
		return res;
	}

	public Collection<RepairShop> findAll() {
		final Collection<RepairShop> res;
		res = this.repairShopRepository.findAll();
		return res;
	}
	public Collection<RepairShop> listAll(final Pageable pageable) {
		final Collection<RepairShop> res;
		res = this.repairShopRepository.findAllRepairShops(pageable).getContent();
		return res;
	}
	public Integer countAll(){
		Integer res=this.repairShopRepository.countAllRepairShops();
		return res;
	}
	public Collection<RepairShop> listByMechanic(final int mechanicId,final Pageable pageable) {
		final Collection<RepairShop> res;
		res = this.repairShopRepository.findRepairShopsByMechanic(mechanicId, pageable).getContent();
		return res;
	}
	public Integer countByMechanic(int mechanicId){
		Integer res=this.repairShopRepository.countRepairShopsByMechanic(mechanicId);
		return res;
	}
	public Collection<RepairShop> findByMechanic(final int mechanicId) {
		final Collection<RepairShop> res;
		res = this.repairShopRepository.findRepairShopsByMechanic(mechanicId);
		return res;
	}
	public Collection<RepairShop> listByKeyword(final String keyword,final Pageable pageable) {
		final Collection<RepairShop> res;
		res = this.repairShopRepository.findRepairShopsByKeyword(keyword, pageable).getContent();
		return res;
	}
	public Integer countByKeyword(final String keyword){
		Integer res=this.repairShopRepository.countRepairShopsByKeyword(keyword);
		return res;
	}
	public Collection<RepairShop> listMarked(final Pageable pageable) {
		final Collection<RepairShop> res;
		res = this.repairShopRepository.findMarkedRepairShop(pageable).getContent();
		return res;
	}
	public Integer countMarked(){
		Integer res=this.repairShopRepository.countMarkedRepairShop();
		return res;
	}
	public RepairShop reconstruct(final RepairShop repairShop, final BindingResult binding) {
		RepairShop res;
		if(repairShop.getId()==0){
			res = repairShop;
			res.setMarked(false);
			res.setMeanRating(0.0);
			res.setMechanic(this.mechanicService.findByPrincipal());
			res.setReviews(new ArrayList<Review>());
		}else{
			Assert.isTrue(this.findOne(repairShop.getId()).getMechanic().getId()==this.mechanicService.findByPrincipal().getId());
			res=repairShop;
			res.setMarked(this.findOne(repairShop.getId()).isMarked());
			res.setMeanRating(this.findOne(repairShop.getId()).getMeanRating());
			res.setMechanic(this.findOne(repairShop.getId()).getMechanic());
			res.setReviews(this.findOne(repairShop.getId()).getReviews());
			
			
			
		}
		
		
		this.validator.validate(res, binding);
		return res;
	}
	public void delete(final RepairShop repairShop) {
		assert repairShop != null;
		Collection <domain.Service> services=this.serviceService.findAllByRepairShop(repairShop.getId());
		Collection <Car> cars=this.carService.findByRepairShop(repairShop.getId());
		for(domain.Service s: services){
			this.serviceService.delete(s);
		}
		for(Car c: cars){
			c.setRepairShop(null);
		}
		
		this.repairShopRepository.delete(repairShop.getId());
	}
	public Collection<RepairShop> listRepairShopsReviewable(final Pageable pageable) {
		final Collection<RepairShop> res;
		res = this.repairShopRepository.findAllRepairShops(pageable).getContent();
		Collection<RepairShop> reviewed=this.findRepairShopsReviewed();
		final Collection<RepairShop> result = new ArrayList<>(res);
		result.removeAll(reviewed);
		return result;
	}
	
	public Collection<RepairShop> findRepairShopsReviewed() {
		final Collection<RepairShop> res;
		res = this.repairShopRepository.findRepairShopsReviewed(this.userService.findByPrincipal().getId());
		return res;
	}
	public RepairShop findRepairShopByReview(int reviewId){
		RepairShop res=this.repairShopRepository.findRepairShopByReviewId(reviewId);
		return res;
	}
	public Integer countReviewable(){
		Integer res=this.repairShopRepository.countAllRepairShops();
		Integer reviewed=this.repairShopRepository.countRepairShopsReviewed(this.userService.findByPrincipal().getId());
		res-=reviewed;
		return res;
	}
}
