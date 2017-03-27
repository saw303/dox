package ch.silviowangler.dox.web.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {

    @Mock
    private ch.silviowangler.dox.api.DocumentService documentService;

    @InjectMocks
    private DocumentController controller = new DocumentController();

    @Test
    public void testViewName() {
        final String expectedQueryString = "a silly documentReferences";
        controller.query(expectedQueryString, false, false, GERMAN);

        verify(documentService).findDocumentReferences(expectedQueryString, GERMAN);
        verify(documentService, never()).findDocumentReferencesForCurrentUser(expectedQueryString);
    }

    @Test
    public void testWildcard() {
        final String queryStringSentByUser = "a silly modelAndView";
        final String expectedQueryString = "*" + queryStringSentByUser + "*";

        controller.query(queryStringSentByUser, true, false, GERMAN);

        verify(documentService).findDocumentReferences(expectedQueryString, GERMAN);
        verify(documentService, never()).findDocumentReferencesForCurrentUser(queryStringSentByUser);
    }

    @Test
    public void testWildcardOnlyIfNoWildcardAreAlreadyInTheQuery() {
        final String queryStringSentByUser = "a s?lly model*View";

        controller.query(queryStringSentByUser, true, false, GERMAN);

        verify(documentService).findDocumentReferences(queryStringSentByUser, GERMAN);
        verify(documentService, never()).findDocumentReferencesForCurrentUser(queryStringSentByUser);
    }

    @Test
    public void queryForCurrentUserOnly() {
        final String queryStringSentByUser = "this is not rocket science";

        controller.query(queryStringSentByUser, false, true, ENGLISH);

        verify(documentService).findDocumentReferencesForCurrentUser(queryStringSentByUser, ENGLISH);
        verify(documentService, never()).findDocumentReferences(queryStringSentByUser);
    }

    @Test
    public void queryForCurrentUserOnlyUsingWildcard() {
        final String queryStringSentByUser = "this is not rocket science";

        controller.query(queryStringSentByUser, true, true, ENGLISH);


        verify(documentService).findDocumentReferencesForCurrentUser("*" + queryStringSentByUser + "*", ENGLISH);
        verify(documentService, never()).findDocumentReferences(queryStringSentByUser);
    }
}
