
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import repositories.IdNumberPatternRepository;
import domain.IdNumberPattern;

@Service
@Transactional
public class IdNumberPatternService {

	@Autowired
	private IdNumberPatternRepository	idNumberPatternRepository;

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private AdminService				adminService;


	public IdNumberPattern create() {
		final IdNumberPattern idN = new IdNumberPattern();
		return idN;
	}

	public IdNumberPattern save(final IdNumberPattern idN) {
		Assert.notNull(idN);
		Assert.isTrue(!StringUtils.isEmpty(idN.getPattern()));
		Assert.isTrue(this.configurationService.find().getNationalities().contains(idN.getNationality()));
		Assert.isTrue(!this.idNumberPatternRepository.findPatternsByNationality(idN.getNationality()).contains(idN.getPattern()));
		idN.setPattern(idN.getPattern().trim());
		final IdNumberPattern saved = this.idNumberPatternRepository.save(idN);
		return saved;
	}
	public void delete(final IdNumberPattern idN) {
		Assert.notNull(idN);
		Assert.isTrue(idN.getId() > 0);
		Assert.notNull(this.adminService.findByPrincipal());
		this.idNumberPatternRepository.delete(idN);
	}
	public void delete(final int id) {
		this.delete(this.idNumberPatternRepository.findOne(id));
	}

	public Collection<IdNumberPattern> findAll() {
		return this.idNumberPatternRepository.findAll();
	}

	public Collection<IdNumberPattern> findByNationality(final String nationality) {
		return this.idNumberPatternRepository.findByNationality(nationality);
	}
	public IdNumberPattern findOne(final int id) {
		final IdNumberPattern res = this.idNumberPatternRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
	public Integer countIdNumberPattern() {
		return this.idNumberPatternRepository.countIdNumberPattern();
	}
	public Integer countIdNumberPatternByNationality(final String nationality) {
		return this.idNumberPatternRepository.countIdNumberPatternByNationality(nationality);
	}
	public Collection<IdNumberPattern> getIdNumberPattern(final Pageable pageable) {
		return this.idNumberPatternRepository.getIdNumberPattern(pageable).getContent();
	}
	public Collection<IdNumberPattern> getIdNumberPatternByNationality(final String nationality, final Pageable pageable) {
		return this.idNumberPatternRepository.getIdNumberPatternByNationality(nationality, pageable).getContent();
	}
}
