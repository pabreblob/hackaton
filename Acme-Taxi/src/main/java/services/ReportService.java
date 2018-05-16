
package services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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


	public Page<Report> getAll(final Pageable pageable) {
		return this.reportRepository.getAll(pageable);
	}
	public Integer countAll() {
		return this.reportRepository.countAll();
	}
	public Page<Report> getNotChecked(final Pageable pageable) {
		return this.reportRepository.getNotChecked(pageable);
	}
	public Integer countNotChecked() {
		return this.reportRepository.countNotChecked();
	}
	public Page<Report> getReportByActor(final int actorId, final Pageable pageable) {
		return this.reportRepository.getReportByActor(actorId, pageable);
	}
	public Integer countReportByActor(final int actorId) {
		return this.countReportByActor(actorId);
	}
	public Integer countReportThisWeek() {
		final long dayInMS = 7 * 24 * 60 * 60 * 1000;
		final Integer res = this.reportRepository.countReportThisWeek(new Date(System.currentTimeMillis() - dayInMS));
		return res;
	}
	public Report create() {
		final Report r = new Report();
		return r;
	}
	public Report save(final Report r) {
		Assert.isTrue(r.getCreator().getId() != r.getReported().getId());
		Assert.isTrue(!(r.getReported() instanceof Admin));
		if (r.getId() == 0)
			Assert.isTrue(this.countReportThisWeek() + 1 <= this.configurationService.find().getLimitReportsWeek());
		final Report saved = this.reportRepository.save(r);
		return saved;
	}
	public void check(final Report r) {
		r.setChecked(true);
		this.save(r);
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
}
