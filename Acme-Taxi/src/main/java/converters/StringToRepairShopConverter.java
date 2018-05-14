
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MechanicRepository;
import repositories.RepairShopRepository;
import domain.Mechanic;
import domain.RepairShop;

@Component
@Transactional
public class StringToRepairShopConverter implements Converter<String, RepairShop> {

	@Autowired
	RepairShopRepository	repairShopRepository;


	@Override
	public RepairShop convert(final String arg0) {
		RepairShop res;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.repairShopRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
