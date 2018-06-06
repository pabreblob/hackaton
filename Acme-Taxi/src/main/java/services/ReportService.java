
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportRepository;
import domain.Admin;
import domain.Report;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository		reportRepository;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private Validator				validator;


	public Collection<Report> getAll(final Pageable pageable) {
		return this.reportRepository.getAll(pageable).getContent();
	}
	public Integer countAll() {
		return this.reportRepository.countAll();
	}
	public Collection<Report> getNotChecked(final Pageable pageable) {
		return this.reportRepository.getNotChecked(pageable).getContent();
	}
	public Integer countNotChecked() {
		return this.reportRepository.countNotChecked();
	}
	public Collection<Report> getReportByActor(final int actorId, final Pageable pageable) {
		Assert.notNull(this.actorService.findOne(actorId));
		return this.reportRepository.getReportByActor(actorId, pageable).getContent();
	}
	public Integer countReportByActor(final int actorId) {
		return this.reportRepository.countReportByActor(actorId);
	}
	public Integer countReportThisWeek(final int actorId) {
		final long dayInMS = 7 * 24 * 60 * 60 * 1000;
		final Date d = new Date(System.currentTimeMillis() - dayInMS);
		final Integer res = this.reportRepository.countReportThisWeek(actorId, d);
		return res;
	}
	public Report create() {
		final Report r = new Report();
		return r;
	}
	public Report save(final Report r) {
		Assert.isTrue(r.getCreator().getId() != r.getReported().getId());
		Assert.isTrue(!(r.getReported() instanceof Admin));
		final Report saved = this.reportRepository.save(r);
		return saved;
	}
	public void check(final Report r) {
		if (!r.isChecked()) {
			r.setChecked(true);
			this.save(r);
		}
	}
	public Report recontruct(final Report report, final BindingResult bindingResult) {
		report.setId(0);
		report.setVersion(0);
		report.setCreator(this.actorService.findByPrincipal());
		report.setMoment(new Date(System.currentTimeMillis() - 1000));
		report.setChecked(false);
		this.validator.validate(report, bindingResult);
		return report;
	}
	public Report findOne(final int reportId) {
		final Report res = this.reportRepository.findOne(reportId);
		Assert.notNull(res);
		return res;
	}
}
