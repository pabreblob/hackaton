
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

	//	@Query("select s from Sponsorship s where s.accepted = false")
	//	Page<Sponsorship> findSponsorshipNotAccepted(Pageable pageable);
	//
	//	@Query("select count(s) from Sponsorship s where s.accepted = false")
	//	Integer countSponsorshipNotAccepted();

}
