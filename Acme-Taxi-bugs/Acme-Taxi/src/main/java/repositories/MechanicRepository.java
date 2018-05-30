
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Mechanic;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Integer> {

	@Query("select m from Mechanic m where m.userAccount.id = ?1")
	Mechanic findMechanicByUserAccountId(int UserAccountId);
}
