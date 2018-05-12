
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SpamWordRepository;
import domain.SpamWord;

@Service
@Transactional
public class SpamWordService {

	@Autowired
	private SpamWordRepository	spamWordRepository;


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

	public void addWords(final String words) {
		final String[] w = words.split(",");
		SpamWord temp;
		for (final String word : w) {
			temp = this.create();
			temp.setWord(word);
			this.save(temp);
		}
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
}
