
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

	@Query("select a from Announcement a where a.creator.id = ?1")
	Page<Announcement> findCreatedAnnouncementsByUserId(int userId, Pageable pageable);

	@Query("select count(a) from Announcement a where a.creator.id = ?1")
	Integer countCreatedAnnouncementsByUserId(int userId);

	@Query("select a from Announcement a join a.attendants u where u.id = ?1")
	Page<Announcement> findJoinedAnnouncementsByUserId(int userId, Pageable pageable);

	@Query("select a from Announcement a join a.attendants u where u.id = ?1")
	Integer countJoinedAnnouncementsByUserId(int userId);
}
