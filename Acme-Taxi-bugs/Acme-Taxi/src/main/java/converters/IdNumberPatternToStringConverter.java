
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.IdNumberPattern;

@Component
@Transactional
public class IdNumberPatternToStringConverter implements Converter<IdNumberPattern, String> {

	@Override
	public String convert(final IdNumberPattern idNumberPattern) {
		String res;
		if (idNumberPattern == null)
			res = null;
		else
			res = String.valueOf(idNumberPattern.getId());
		return res;
	}
}
