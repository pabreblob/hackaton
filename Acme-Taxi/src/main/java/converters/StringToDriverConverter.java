
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.DriverRepository;
import domain.Driver;

@Component
@Transactional
public class StringToDriverConverter implements Converter<String, Driver> {

	@Autowired
	DriverRepository	driverRepository;


	@Override
	public Driver convert(final String arg0) {
		Driver res;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.driverRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
