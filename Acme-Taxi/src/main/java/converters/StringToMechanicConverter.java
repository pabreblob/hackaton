
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MechanicRepository;
import domain.Mechanic;

@Component
@Transactional
public class StringToMechanicConverter implements Converter<String, Mechanic> {

	@Autowired
	MechanicRepository	mechanicRepository;


	@Override
	public Mechanic convert(final String arg0) {
		Mechanic res;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.mechanicRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
