
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select f.messages from Folder f where f.id = ?1")
	Collection<Message> findMessagesByFolderId(int folderId);

	@Query("select count(m) from Folder f join f.messages m where f.id = ?1 and m.checked is false")
	Integer countUnreadMessages(int folderId);
}
