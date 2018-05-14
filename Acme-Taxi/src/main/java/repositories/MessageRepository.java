
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.folder.id = ?1")
	Page<Message> findMessagesByFolderId(int folderId, Pageable pageable);

	@Query("select count(m) from Message m where m.folder.id = ?1")
	Integer countMessagesByFolderId(int folderId);

	@Query("select count(m) from Folder f join f.messages m where f.id = ?1 and m.checked is false")
	Integer countUnreadMessages(int folderId);
}
