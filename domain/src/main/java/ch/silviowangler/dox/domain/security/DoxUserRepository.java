package ch.silviowangler.dox.domain.security;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public interface DoxUserRepository extends CrudRepository<DoxUser, Long> {

    DoxUser findByUsername(String username);
}
