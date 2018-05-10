
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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


	//TODO: Quitar comentarios cuando se implemente Configuration
	//@Autowired
	//private ConfigurationService	configurationService;

	public IdNumberPattern create() {
		final IdNumberPattern idN = new IdNumberPattern();
		return idN;
	}

	public IdNumberPattern save(final IdNumberPattern idN) {
		Assert.notNull(idN);
		Assert.isTrue(!StringUtils.isEmpty(idN.getPattern()));
		//Assert.isTrue(configurationService.find().getNationalities().contains(idN.getNationality()));
		idN.setPattern(idN.getPattern().trim());
		final IdNumberPattern saved = this.idNumberPatternRepository.save(idN);
		return saved;
	}

	public void delete(final IdNumberPattern idN) {
		Assert.notNull(idN);
		Assert.isTrue(idN.getId() > 0);
		this.idNumberPatternRepository.delete(idN);
	}

	public Collection<IdNumberPattern> findAll() {
		return this.idNumberPatternRepository.findAll();
	}
}
