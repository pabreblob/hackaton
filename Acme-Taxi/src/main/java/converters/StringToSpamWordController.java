
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SpamWordRepository;
import domain.SpamWord;

@Component
@Transactional
public class StringToSpamWordController implements Converter<String, SpamWord> {

	@Autowired
	private SpamWordRepository	spamWordRepository;


	@Override
	public SpamWord convert(final String source) {
		final SpamWord spam;
		int id;
		try {
			id = Integer.valueOf(source);
			spam = this.spamWordRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return spam;
	}
}
