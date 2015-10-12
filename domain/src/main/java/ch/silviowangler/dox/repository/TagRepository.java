package ch.silviowangler.dox.repository;

import ch.silviowangler.dox.domain.stats.Tag;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Silvio Wangler on 12/10/15.
 */
public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findByName(String name);
}
