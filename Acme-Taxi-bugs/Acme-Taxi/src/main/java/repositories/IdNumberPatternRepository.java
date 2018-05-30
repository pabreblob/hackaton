
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.IdNumberPattern;

@Repository
public interface IdNumberPatternRepository extends JpaRepository<IdNumberPattern, Integer> {

	@Query("select i from IdNumberPattern i where i.nationality = ?1")
	Collection<IdNumberPattern> findByNationality(String nationality);
	@Query("select i.pattern from IdNumberPattern i where i.nationality = ?1")
	Collection<String> findPatternsByNationality(String nationality);
	//Paginación
	@Query("select i from IdNumberPattern i")
	Page<IdNumberPattern> getIdNumberPattern(Pageable pageable);
	@Query("select count(i) from IdNumberPattern i")
	Integer countIdNumberPattern();
	@Query("select i from IdNumberPattern i where i.nationality = ?1")
	Page<IdNumberPattern> getIdNumberPatternByNationality(String nationality, Pageable pageable);
	@Query("select count(i) from IdNumberPattern i where i.nationality = ?1")
	Integer countIdNumberPatternByNationality(String nationality);

}
