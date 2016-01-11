package ch.silviowangler.dox.repository.workflow;

import ch.silviowangler.dox.domain.workflow.PostBox;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Silvio Wangler on 03/01/16.
 */
public interface PostBoxRepository extends CrudRepository<PostBox, Long> {

    PostBox findByShortName(String shortName);
}
