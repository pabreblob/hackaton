
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.creator.id = ?1")
	Collection<Comment> findCommentsByUserId(int userId);

	@Query("select c from Comment c where c.announcement.id=?1 order by c.moment DESC")
	Collection<Comment> findCommentsOrdered(int announcementId);

	@Query("select c from Comment c where c.comment.id=?1 order by c.moment DESC")
	Collection<Comment> findCommentsByParentId(int commentId);

}