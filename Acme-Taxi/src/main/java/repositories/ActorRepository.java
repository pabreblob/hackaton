
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select c from Actor c where c.userAccount.id = ?1")
	Actor findActorByUserAccountId(int UserAccountId);

	@Query("select a from Actor a where a.userAccount.username like concat('%', ?1, '%')")
	Collection<Actor> findByUsername(String keyword);
}
