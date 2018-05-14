
package repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.RepairShop;

@Repository
public interface RepairShopRepository extends JpaRepository<RepairShop, Integer> {

	@Query("select r from RepairShop r where r.mechanic.id= ?1")
	Page<RepairShop> findRepairShopsByMechanic(int MechanicId,Pageable pageable);
	@Query("select count (r) from RepairShop r where r.mechanic.id= ?1")
	Integer countRepairShopsByMechanic(int MechanicId);
	@Query("select r from RepairShop r ")
	Page<RepairShop> findAllRepairShops(Pageable pageable);
	@Query("select count (r) from RepairShop r ")
	Integer countAllRepairShops();
	@Query("select r from RepairShop r where (r.name like concat('%',?1,'%')or r.description like concat('%',?1,'%') or r.location like concat('%',?1,'%'))")
	Page<RepairShop> findRepairShopsByKeyword(String keyword,Pageable pageable);
	@Query("select count(r) from RepairShop r where (r.name like concat('%',?1,'%')or r.description like concat('%',?1,'%') or r.location like concat('%',?1,'%'))")
	Integer countRepairShopsByKeyword(String keyword);
	@Query("select r from RepairShop r where r.marked=true")
	Page<RepairShop> findMarkedRepairShop(Pageable pageable);
	@Query("select count (r) from RepairShop r where r.marked=true")
	Integer countMarkedRepairShop();
}
