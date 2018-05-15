
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ReportRepository;
import domain.Report;

@Component
@Transactional
public class StringToReportConverter implements Converter<String, Report> {

	@Autowired
	private ReportRepository	reportRepository;


	@Override
	public Report convert(final String arg0) {
		Report res;
		int id;

		try {
			if (StringUtils.isEmpty(arg0))
				res = null;
			else {
				id = Integer.valueOf(arg0);
				res = this.reportRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
