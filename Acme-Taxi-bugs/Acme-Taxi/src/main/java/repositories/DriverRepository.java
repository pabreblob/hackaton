
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

	@Query("select distinct r.driver from Request r where r.user.id = ?1 and r.driver != null and r.moment < CURRENT_TIMESTAMP and r.cancelled = false")
	Page<Driver> findDriversReviewable(int userId, Pageable pageable);

	@Query("select distinct r.driver from Request r where r.user.id = ?1 and r.driver != null and r.moment < CURRENT_TIMESTAMP and r.cancelled = false")
	Collection<Driver> findDriversReviewable(int userId);

	@Query("select d from Driver d join d.reviews r where r.creator.id = ?1 ")
	Collection<Driver> findDriverWithReviewByUserId(int userId);

	@Query("select d from Driver d join d.reviews r where r.id = ?1 ")
	Driver findDriverByReviewId(int reviewId);

	@Query("select d from Driver d where d.car.id = ?1")
	Driver findDriverByCarId(int carId);
}
