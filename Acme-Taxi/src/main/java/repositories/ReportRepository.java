
package repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	//Paginaci�n de todos los reports
	@Query("select r from Report r")
	Page<Report> getAll(Pageable pageable);
	@Query("select count(r) from Report r")
	Integer countAll();

	//Paginaci�n de los no le�dos
	@Query("select r from Report r where r.checked = false")
	Page<Report> getNotChecked(Pageable pageable);
	@Query("select count(r) from Report r where r.checked = false")
	Integer countNotChecked();

	//Paginaci�n de los reports de un actor
	@Query("select r from Report r where r.creator.id = ?1")
	Page<Report> getReportByActor(int actorId, Pageable pageable);
	@Query("select count(r) from Report r where r.creator.id = ?1")
	Integer countReportByActor(int actorId);

	@Query("select count(r) from Report r where r.moment > ?1")
	Integer countReportThisWeek(Date date);
}
