
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.id = ?1")
	User findUserByUserAccountId(int UserAccountId);

	@Query("select distinct a.attendants from User u join u.announcements a where u.id = ?1")
	Page<User> findUsersReviewable(int userId, Pageable pageable);

	@Query("select distinct a.attendants from User u join u.announcements a where u.id = ?1")
	Collection<User> findUsersReviewable(int userId);

	@Query("select u from User u join u.reviews r where r.creator.id = ?1")
	Collection<User> findUserWithReviewByUserId(int userId);

	@Query("select u from User u join u.reviews r where r.id = ?1")
	User findUserByReviewId(int reviewId);
}
