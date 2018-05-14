
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SpamWord;

@Repository
public interface SpamWordRepository extends JpaRepository<SpamWord, Integer> {

	@Query("select w.word from SpamWord w")
	Collection<String> getWords();

	//PAGINACI�N
	@Query("select w from SpamWord w")
	Page<SpamWord> getSpamWords(Pageable pageable);

	@Query("select count(w) from SpamWord w")
	Integer countSpamWords();
}
