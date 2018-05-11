
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.IdNumberPatternRepository;
import domain.IdNumberPattern;
import forms.IdNumberPatternForm;

@Service
@Transactional
public class IdNumberPatternService {

	@Autowired
	private IdNumberPatternRepository	idNumberPatternRepository;

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private Validator					validator;


	public IdNumberPattern create() {
		final IdNumberPattern idN = new IdNumberPattern();
		return idN;
	}

	public IdNumberPattern save(final IdNumberPattern idN) {
		Assert.notNull(idN);
		Assert.isTrue(!StringUtils.isEmpty(idN.getPattern()));
		Assert.isTrue(this.configurationService.find().getNationalities().contains(idN.getNationality()));
		idN.setPattern(idN.getPattern().trim());
		final IdNumberPattern saved = this.idNumberPatternRepository.save(idN);
		return saved;
	}

	public void delete(final IdNumberPattern idN) {
		Assert.notNull(idN);
		Assert.isTrue(idN.getId() > 0);
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
	public IdNumberPattern recontruct(final IdNumberPatternForm form, final BindingResult br) {
		IdNumberPattern res = null;
		res = this.create();
		res.setPattern(form.getText());
		res.setNationality(form.getNationality());
		if (form.getId() != 0) {
			res.setId(form.getId());
			res.setVersion(this.idNumberPatternRepository.findOne(form.getId()).getVersion());
		}
		this.validator.validate(form, br);
		return res;
	}
}
