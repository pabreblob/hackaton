
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.IdNumberPattern;

@Repository
public interface IdNumberPatternRepository extends JpaRepository<IdNumberPattern, Integer> {

	@Query("select i from IdNumberPattern i where i.nationality = ?1")
	Collection<IdNumberPattern> findByNationality(String nationality);
}
