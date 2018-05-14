
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

	@Query(value = "create event pepito on schedule at current_timestamp + interval 1 minute do delete * from message", nativeQuery = true)
	void getMessagesPorqueSi();
}
