
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.IdNumberPattern;

@Repository
public interface IdNumberPatternRepository extends JpaRepository<IdNumberPattern, Integer> {

}
