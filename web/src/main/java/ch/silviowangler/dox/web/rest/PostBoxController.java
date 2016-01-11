package ch.silviowangler.dox.web.rest;

import ch.silviowangler.dox.api.rest.PostBox;
import ch.silviowangler.dox.api.workflow.PostBoxNotFoundException;
import ch.silviowangler.dox.api.workflow.PostBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Silvio Wangler on 04/01/16.
 */
@RestController
@RequestMapping("/api/v1/postbox")
public class PostBoxController {

    @Autowired
    private PostBoxService postBoxService;

    @RequestMapping(method = GET)
    public List<PostBox> list() {
        return postBoxService.findAllPostBoxes();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public PostBox getPostBox(@PathVariable("id") String id) throws PostBoxNotFoundException {
        return postBoxService.findPostBox(Long.valueOf(id));
    }
}
