
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ReservationRepository;

import domain.Reservation;

@Component
@Transactional
public class StringToReservationConverter implements Converter<String, Reservation> {

	@Autowired
	ReservationRepository	reservationRepository;


	@Override
	public Reservation convert(final String arg0) {
		Reservation res;
		 int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.reservationRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
