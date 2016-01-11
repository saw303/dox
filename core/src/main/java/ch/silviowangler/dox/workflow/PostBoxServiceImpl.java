package ch.silviowangler.dox.workflow;

import ch.silviowangler.dox.api.rest.PostBox;
import ch.silviowangler.dox.api.workflow.PostBoxNotFoundException;
import ch.silviowangler.dox.api.workflow.PostBoxService;
import ch.silviowangler.dox.domain.workflow.UserPostBox;
import ch.silviowangler.dox.repository.workflow.PostBoxRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Silvio Wangler on 04/01/16.
 */
@Service
@Transactional(readOnly = true)
public class PostBoxServiceImpl implements PostBoxService {

    private static final Logger log = LoggerFactory.getLogger(PostBoxServiceImpl.class);

    @Autowired
    private PostBoxRepository postBoxRepository;

    @Override
    public List<PostBox> findAllPostBoxes() {

        Iterable<ch.silviowangler.dox.domain.workflow.PostBox> postBoxes = postBoxRepository.findAll();
        List<PostBox> result = Lists.newArrayList();

        for (ch.silviowangler.dox.domain.workflow.PostBox postBox : postBoxes) {
            PostBox pbox = toRestPostBox(postBox);
            result.add(pbox);
        }
        log.debug("Fetched {} post boxes", result.size());

        return result;
    }

    @Override
    public PostBox findPostBox(String shortName) throws PostBoxNotFoundException {

        ch.silviowangler.dox.domain.workflow.PostBox postBox = postBoxRepository.findByShortName(shortName);

        notNullCheck(postBox);

        return toRestPostBox(postBox);
    }

    @Override
    public PostBox findPostBox(Long id) throws PostBoxNotFoundException {
        ch.silviowangler.dox.domain.workflow.PostBox postBox = postBoxRepository.findOne(id);

        notNullCheck(postBox);

        return toRestPostBox(postBox);
    }

    private void notNullCheck(ch.silviowangler.dox.domain.workflow.PostBox postBox) throws PostBoxNotFoundException {
        if (postBox == null) throw new PostBoxNotFoundException();
    }

    private PostBox toRestPostBox(ch.silviowangler.dox.domain.workflow.PostBox postBox) {

        String parentPostBoxShortName = postBox.getParent() != null ? postBox.getParent().getShortName() : null;
        String username = postBox instanceof UserPostBox ? ((UserPostBox) postBox).getUser().getUsername() : null;
        return new PostBox(postBox.getId(), postBox.getShortName(), parentPostBoxShortName, username);
    }
}
