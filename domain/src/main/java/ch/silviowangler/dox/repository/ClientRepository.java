package ch.silviowangler.dox.repository;

import ch.silviowangler.dox.domain.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Silvio Wangler on 24.12.14.
 *
 * @author Silvio Wangler
 * @since 0.4
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

    /**
     * Finds a client by its unique short name
     *
     * @param shortName the unique short name
     * @return client instance
     */
    Client findByShortName(String shortName);
}
