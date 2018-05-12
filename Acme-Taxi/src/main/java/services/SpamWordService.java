
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SpamWordRepository;
import domain.SpamWord;
import forms.SpamWordForm;

@Service
@Transactional
public class SpamWordService {

	@Autowired
	private SpamWordRepository	spamWordRepository;
	@Autowired
	private Validator			validator;


	public SpamWord create() {
		final SpamWord res = new SpamWord();

		return res;
	}

	public Collection<SpamWord> findAll() {
		return this.spamWordRepository.findAll();
	}

	public SpamWord save(final SpamWord sw) {
		Assert.notNull(sw);
		Assert.notNull(sw.getWord());
		Assert.isTrue(sw.getWord().replace(" ", "").length() != 0);
		sw.setWord(sw.getWord().trim());
		final SpamWord res = this.spamWordRepository.save(sw);
		return res;
	}

	public void delete(final SpamWord sw) {
		Assert.notNull(sw);
		Assert.notNull(sw.getWord());
		Assert.isTrue(sw.getId() > 0);
		this.spamWordRepository.delete(sw);
	}

	public SpamWord findOne(final int id) {
		final SpamWord sp = this.spamWordRepository.findOne(id);
		Assert.notNull(sp);
		return sp;
	}

	public SpamWord reconstruct(final SpamWordForm form, final BindingResult binding) {
		final SpamWord sp = this.create();
		sp.setWord(form.getWord());
		if (sp.getId() != 0)
			sp.setId(form.getId());
		this.validator.validate(form, binding);
		return sp;
	}

	public void addWord(final String text) {
		final Collection<String> all = this.getWords();
		for (final String w : text.split(","))
			if (!all.contains(w.trim())) {
				all.add(w);//Linea para evitar los fallos por repetición
				final SpamWord sp = this.create();
				sp.setWord(w);
				this.save(sp);
			}
	}

	public Collection<String> getWords() {
		return this.spamWordRepository.getWords();
	}
}
