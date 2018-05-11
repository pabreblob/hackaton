
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Mechanic;

@Component
@Transactional
public class MechanicToStringConverter implements Converter<Mechanic, String> {

	@Override
	public String convert(final Mechanic mechanic) {
		String result;
		if (mechanic == null)
			result = null;
		else
			result = String.valueOf(mechanic.getId());
		return result;
	}
}
