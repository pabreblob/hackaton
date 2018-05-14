
package services;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RepairShopRepository;
import domain.Mechanic;
import domain.RepairShop;
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


	public RepairShopService() {
		super();
	}

	public RepairShop create() {
		final RepairShop repairShop = new RepairShop();
		repairShop.setMechanic(this.mechanicService.findByPrincipal());

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
		repairShop.getMechanic().setSuspicious(spamw);
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
		}else{
			Assert.isTrue(this.findOne(repairShop.getId()).getMechanic().getId()==this.mechanicService.findByPrincipal().getId());
			res=repairShop;
			res.setMarked(this.findOne(repairShop.getId()).isMarked());
			res.setMeanRating(this.findOne(repairShop.getId()).getMeanRating());
			res.setMechanic(this.findOne(repairShop.getId()).getMechanic());
			
			
			
		}
		
		
		this.validator.validate(res, binding);
		return res;
	}

}
