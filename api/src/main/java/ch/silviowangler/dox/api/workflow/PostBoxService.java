package ch.silviowangler.dox.api.workflow;

import ch.silviowangler.dox.api.rest.PostBox;

import java.util.List;

/**
 * Created by Silvio Wangler on 04/01/16.
 */
public interface PostBoxService {

    List<PostBox> findAllPostBoxes();

    PostBox findPostBox(String shortName) throws PostBoxNotFoundException;

    PostBox findPostBox(Long id) throws PostBoxNotFoundException;
}
