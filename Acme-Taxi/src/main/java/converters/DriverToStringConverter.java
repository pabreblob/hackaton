
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Driver;

@Component
@Transactional
public class DriverToStringConverter implements Converter<Driver, String> {

	@Override
	public String convert(final Driver d) {
		String result;
		if (d == null)
			result = null;
		else
			result = String.valueOf(d.getId());
		return result;
	}
}
