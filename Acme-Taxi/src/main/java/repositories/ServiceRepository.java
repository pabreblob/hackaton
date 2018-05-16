
package repositories;


import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

	@Query("select s from Service s where s.repairShop.id= ?1")
	Page<Service> findServicesByRepairShop(int repairShopId,Pageable pageable);
	@Query("select count (s) from Service s where s.repairShop.id= ?1")
	Integer countServicesByRepairShop(int repairShopId);
	@Query("select r.service from Reservation r where r.user.id=?1 and r.cancelled=0 and r.moment > CURRENT_TIMESTAMP")
	Collection<Service> findServicesByUser(int userId);
	@Query("select s from Service s where s.repairShop.id= ?1")
	Collection<Service> findAllServicesByRepairShop(int repairShopId);
}
