
package repositories;


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

}
