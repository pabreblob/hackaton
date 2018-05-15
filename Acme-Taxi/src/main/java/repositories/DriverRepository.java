
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

	@Query("select d from Driver d where d.userAccount.id = ?1")
	Driver findDriverByUserAccountId(int UserAccountId);

	@Query("select r.driver from Request r where r.user.id = ?1 and r.driver != null")
	Page<Driver> findDriversReviewable(int userAccountId, Pageable pageable);

	@Query("select r.driver from Request r where r.user.id = ?1 and r.driver != null")
	Collection<Driver> findDriversReviewable(int userAccountId);

	@Query("select count(r.driver) from Request r where r.user.userAccount.id = ?1 and r.driver != null")
	Integer countDriversReviewable(int userAccountId);
}
