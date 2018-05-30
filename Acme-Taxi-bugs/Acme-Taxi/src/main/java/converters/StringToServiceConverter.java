
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ServiceRepository;

import domain.Service;

@Component
@Transactional
public class StringToServiceConverter implements Converter<String, Service> {

	@Autowired
	ServiceRepository	serviceRepository;


	@Override
	public Service convert(final String arg0) {
		Service res;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.serviceRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
