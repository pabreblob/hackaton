
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


	//TODO: Quitar comentarios cuando se incorpore los servicios de Admin

	//@Autowired
	//private AdminService adminService;

	public SpamWord create() {
		//Assert.notNull(this.adminService.findByPrincipal());
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
		//Assert.notNull(this.adminService.findByPrincipal());
		final SpamWord res = this.spamWordRepository.save(sw);
		return res;
	}

	public void delete(final SpamWord sw) {
		Assert.notNull(sw);
		Assert.notNull(sw.getWord());
		Assert.isTrue(sw.getId() > 0);
		//Assert.notNull(this.adminService.findByPrincipal());
		this.spamWordRepository.delete(sw);
	}
}
