
package repositories;


import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	@Query("select r from Reservation r join r.service s join s.repairShop rep where rep.mechanic.id=?1 and r.moment>CURRENT_TIMESTAMP")
	Page<Reservation> findCurrentReservationsByMechanic(int mechanicId,Pageable pageable);
	@Query("select count (r)from Reservation r join r.service s join s.repairShop rep where rep.mechanic.id=?1 and r.moment>CURRENT_TIMESTAMP")
	Integer countCurrentReservationsByMechanic(int mechanicId);
	@Query("select r from Reservation r where r.user.id=?1 and r.moment>CURRENT_TIMESTAMP ")
	Page<Reservation> findCurrentReservationsByUser(int userId,Pageable pageable);
	@Query("select count (r) from Reservation r where r.user.id=?1 and r.moment>CURRENT_TIMESTAMP")
	Integer countCurrentReservationsByUser(int userId);
	@Query("select r from Reservation r join r.service s where s.repairShop.id=?1 and r.moment>CURRENT_TIMESTAMP")
	Page<Reservation> findReservationsByRepairShop(int repairShopId,Pageable pageable);
	@Query("select count (r) from Reservation r join r.service s where s.repairShop.id=?1 and r.moment>CURRENT_TIMESTAMP")
	Integer countReservationsByRepairShop(int repairShopId);
	@Query("select count (r) from Reservation r where r.service.id=?1 and r.moment>CURRENT_TIMESTAMP and r.cancelled=0")
	Integer countCurrentReservationsByService(int serviceId);
	@Query("select r from Reservation r where r.service.id=?1")
	Collection<Reservation> findAllByService(int serviceId);
}
