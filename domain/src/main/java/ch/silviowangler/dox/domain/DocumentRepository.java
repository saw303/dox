package ch.silviowangler.dox.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public interface DocumentRepository extends CrudRepository<Document, Long>, DocumentRepositoryCustom {
    Document findByHash(String hash);
}
