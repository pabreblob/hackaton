
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Car;

@Component
@Transactional
public class CarToStringConverter implements Converter<Car, String> {

	@Override
	public String convert(final Car car) {
		String result;
		if (car == null)
			result = null;
		else
			result = String.valueOf(car.getId());
		return result;
	}
}
