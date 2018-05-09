
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SpamWord;

@Component
@Transactional
public class SpamWordToStringConverter implements Converter<SpamWord, String> {

	@Override
	public String convert(final SpamWord spamword) {
		String res;
		if (spamword == null)
			res = null;
		else
			res = String.valueOf(spamword.getId());
		return res;
	}
}
