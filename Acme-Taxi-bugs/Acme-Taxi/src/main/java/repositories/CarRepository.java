
package repositories;



import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import domain.Car;


@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
	@Query("select c from Car c where c.repairShop.id=?1")
	Collection<Car> findCarsByRepairShop(int repairShopId);

}
