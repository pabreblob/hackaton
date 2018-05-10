
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.IdNumberPatternRepository;
import domain.IdNumberPattern;

@Component
@Transactional
public class StringToIdNumberPatternConverter implements Converter<String, IdNumberPattern> {

	@Autowired
	private IdNumberPatternRepository	idNumberPatternRepository;


	@Override
	public IdNumberPattern convert(final String source) {
		final IdNumberPattern idNumberPattern;
		int id;
		try {
			id = Integer.valueOf(source);
			idNumberPattern = this.idNumberPatternRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return idNumberPattern;
	}
}
