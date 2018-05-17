
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query("select r from Review r where r.creator.id=?1")
	Page<Review> findReviewsByCreatorId(int userId, Pageable pageable);

	@Query("select count(r) from Review r where r.creator.id=?1")
	Integer countReviewsByCreatorId(int creatorId);

	@Query("select r from Review r where r.id in (select rev.id from User u join u.reviews rev where u.id=?1)")
	Page<Review> findReviewsByUserId(int userId, Pageable pageable);

	@Query("select count(r) from Review r where r.id in (select rev.id from User u join u.reviews rev where u.id=?1)")
	Integer countReviewsByUserId(int userId);

	@Query("select r from Review r where r.id in (select rev.id from Driver d join d.reviews rev where d.id=?1)")
	Page<Review> findReviewsByDriverId(int driverId, Pageable pageable);

	@Query("select count(r) from Review r where r.id in (select rev.id from Driver d join d.reviews rev where d.id=?1)")
	Integer countReviewsByDriverId(int driverId);

	@Query("select r from Review r where r.id in (select rev.id from RepairShop rs join rs.reviews rev where rs.id=?1)")
	Page<Review> findReviewsByRepairShopId(int repairShopId, Pageable pageable);

	@Query("select count(r) from Review r where r.id in (select rev.id from RepairShop rs join rs.reviews rev where rs.id=?1)")
	Integer countReviewsByRepairShopId(int repairShopId);

	//	@Query("select s from Sponsorship s where s.accepted = false")
	//	Page<Sponsorship> findSponsorshipNotAccepted(Pageable pageable);
	//
	//	@Query("select count(s) from Sponsorship s where s.accepted = false")
	//	Integer countSponsorshipNotAccepted();

}
