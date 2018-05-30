
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.driver.id = ?1 and r.cancelled = false and r.moment > CURRENT_TIMESTAMP")
	Collection<Request> findRequestByDriverToDo(int driverId);

	//Listas
	@Query("select r from Request r where r.user.id = ?1")
	Page<Request> getRequestByUser(int userId, Pageable pageable);
	@Query("select count(r) from Request r where r.user.id = ?1")
	Integer countRequestByUser(int userId);

	@Query("select r from Request r where r.passengersNumber < ?1 and r.driver = null and r.moment > CURRENT_TIMESTAMP")
	Page<Request> getRequestToAccept(int numberInCar, Pageable pageable);
	@Query("select count(r) from Request r where r.passengersNumber < ?1 and r.driver = null and r.moment > CURRENT_TIMESTAMP")
	Integer countRequestToAccept(int numberInCar);

	@Query("select r from Request r where r.driver.id = ?1 and r.cancelled = false and r.moment > CURRENT_TIMESTAMP")
	Page<Request> getRequestToDo(int driverId, Pageable pageable);
	@Query("select count(r) from Request r where r.driver.id = ?1 and r.cancelled = false and r.moment > CURRENT_TIMESTAMP")
	Integer countRequestToDo(int driverId);

	@Query("select r from Request r where r.driver.id = ?1 and (r.cancelled = true or r.moment <= CURRENT_TIMESTAMP)")
	Page<Request> getFinishedRequest(int driverId, Pageable pageable);
	@Query("select count(r) from Request r where r.driver.id = ?1 and (r.cancelled = true or r.moment <= CURRENT_TIMESTAMP)")
	Integer countFinishedRequest(int driverId);

	@Query("select r from Request r where r.marked = true")
	Page<Request> getMarked(Pageable pageable);
	@Query("select count(r) from Request r where r.marked = true")
	Integer countMarked();

	@Query("select r from Request r")
	Page<Request> getAll(Pageable pageable);
	@Query("select count(r) from Request r")
	Integer countAll();
}
