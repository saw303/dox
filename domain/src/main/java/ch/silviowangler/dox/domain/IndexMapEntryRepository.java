package ch.silviowangler.dox.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 15.07.12 19:16
 *        </div>
 */
public interface IndexMapEntryRepository extends CrudRepository<IndexMapEntry, Long> {

    @Query("select i.document from IndexMapEntry i where stringRepresentation = ?")
    List<Document> findByValue(String upperCaseValue);
}
