
package repositories;

import java.util.Collection;

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

	@Query("select count(a) from Announcement a join a.attendants u where u.id = ?1")
	Integer countJoinedAnnouncementsByUserId(int userId);

	@Query("select a from Announcement a where a.moment > CURRENT_TIMESTAMP and a.cancelled is false and a.creator.id != ?1")
	Page<Announcement> findAvailableAnnouncements(int userId, Pageable pageable);

	@Query("select count(a) from Announcement a where a.moment > CURRENT_TIMESTAMP and a.cancelled is false and a.creator.id != ?1")
	Integer countAvailableAnnouncements(int userId);

	@Query("select a from Announcement a")
	Page<Announcement> findAllAnnouncements(Pageable pageable);

	@Query("select count(a) from Announcement a")
	Integer countAllAnnouncements();

	@Query("select a from Announcement a where a.creator.id = ?1 or a.id in (select ann.id from Announcement ann join ann.attendants att where att.id = ?1) order by a.moment desc")
	Collection<Announcement> findLastCreatedOrJoinedAnnouncements(int userId);

	@Query("select a from Announcement a where a.moment > CURRENT_TIMESTAMP")
	Page<Announcement> findCurrentAnnouncements(Pageable pageable);

	@Query("select count(a) from Announcement a where a.moment > CURRENT_TIMESTAMP")
	Integer countCurrentAnnouncements();

	@Query("select a from Announcement a where a.title like concat('%', ?1, '%') or a.description like concat('%', ?1, '%') or a.origin like concat('%', ?1, '%') or a.destination like concat('%', ?1, '%')")
	Collection<Announcement> findAnnouncementsByKeyword(String keyword);

	@Query("select a from Announcement a where a.moment > CURRENT_TIMESTAMP and a.cancelled is false and a.creator.id != ?1")
	Collection<Announcement> finderAnnouncements(int userId);

	@Query("select a from Announcement a where a.moment > CURRENT_TIMESTAMP and a.cancelled is false and a.creator.id != ?2 and (a.title like concat('%', ?1, '%') or a.description like concat('%', ?1, '%') or a.origin like concat('%', ?1, '%') or a.destination like concat('%', ?1, '%'))")
	Collection<Announcement> finderAnnouncementsKeyword(String keyword, int userId);
}
