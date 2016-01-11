package ch.silviowangler.dox.workflow;

import ch.silviowangler.dox.AbstractIntegrationTest;
import ch.silviowangler.dox.api.rest.PostBox;
import ch.silviowangler.dox.api.workflow.PostBoxNotFoundException;
import ch.silviowangler.dox.api.workflow.PostBoxService;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;

import static java.util.Locale.GERMAN;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Silvio Wangler on 04/01/16.
 */
public class PostBoxIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PostBoxService postBoxService;

    @After
    public void tearDown() throws Exception {
        LocaleContextHolder.resetLocaleContext();
    }

    @Test
    public void readAllPostBoxes() {
        List<PostBox> postBoxes = postBoxService.findAllPostBoxes();

        assertThat(postBoxes.size(), is(4));
    }

    @Test(expected = PostBoxNotFoundException.class)
    public void readPostBoxThatDoesNotExist() throws PostBoxNotFoundException {
        postBoxService.findPostBox(-834758L);
    }

    @Test
    public void readSpecificPostBox() throws PostBoxNotFoundException {

        LocaleContextHolder.setLocale(GERMAN);

        PostBox postBox = postBoxService.findPostBox("saw303");

        assertThat(postBox, notNullValue());
        assertThat(postBox.getId(), notNullValue());
        assertThat(postBox.getShortName(), is("saw303"));
        assertThat(postBox.getParent(), is("WANGLER"));
        assertThat(postBox.getUsername(), is("saw303"));

        PostBox postBox2 = postBoxService.findPostBox(postBox.getId());

        assertThat(postBox, equalTo(postBox2));

        PostBox invoices = postBoxService.findPostBox("invoices");
        assertThat(invoices.getTranslation(), is("Rechnungseingang"));
    }
}
