
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Mechanic;
import domain.RepairShop;

@Component
@Transactional
public class RepairShopToStringConverter implements Converter<RepairShop, String> {

	@Override
	public String convert(final RepairShop repairShop) {
		String result;
		if (repairShop == null)
			result = null;
		else
			result = String.valueOf(repairShop.getId());
		return result;
	}
}
