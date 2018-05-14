
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select s from Sponsorship s where s.sponsor.id=?1")
	Page<Sponsorship> findSponsorshipBySponsorId(int sponsorId, Pageable pageable);

	@Query("select count(s) from Sponsorship s where s.sponsor.id=?1")
	Integer countSponsorshipBySponsorId(int sponsorId);

	@Query("select s from Sponsorship s where s.accepted = false")
	Page<Sponsorship> findSponsorshipNotAccepted(Pageable pageable);

	@Query("select count(s) from Sponsorship s where s.accepted = false")
	Integer countSponsorshipNotAccepted();
}