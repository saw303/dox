package ch.silviowangler.dox.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public interface DomainRepository extends CrudRepository<Domain, Long> {
}
