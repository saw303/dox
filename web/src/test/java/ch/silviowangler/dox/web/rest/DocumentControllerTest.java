package ch.silviowangler.dox.web.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        controller.query(expectedQueryString, false, false);

        verify(documentService).findDocumentReferences(expectedQueryString);
        verify(documentService, never()).findDocumentReferencesForCurrentUser(expectedQueryString);
    }

    @Test
    public void testWildcard() {
        final String queryStringSentByUser = "a silly modelAndView";
        final String expectedQueryString = "*" + queryStringSentByUser + "*";

        controller.query(queryStringSentByUser, true, false);

        verify(documentService).findDocumentReferences(expectedQueryString);
        verify(documentService, never()).findDocumentReferencesForCurrentUser(queryStringSentByUser);
    }

    @Test
    public void testWildcardOnlyIfNoWildcardAreAlreadyInTheQuery() {
        final String queryStringSentByUser = "a s?lly model*View";

        controller.query(queryStringSentByUser, true, false);

        verify(documentService).findDocumentReferences(queryStringSentByUser);
        verify(documentService, never()).findDocumentReferencesForCurrentUser(queryStringSentByUser);
    }

    @Test
    public void queryForCurrentUserOnly() {
        final String queryStringSentByUser = "this is not rocket science";

        controller.query(queryStringSentByUser, false, true);

        verify(documentService).findDocumentReferencesForCurrentUser(queryStringSentByUser);
        verify(documentService, never()).findDocumentReferences(queryStringSentByUser);
    }

    @Test
    public void queryForCurrentUserOnlyUsingWildcard() {
        final String queryStringSentByUser = "this is not rocket science";

        controller.query(queryStringSentByUser, true, true);


        verify(documentService).findDocumentReferencesForCurrentUser("*" + queryStringSentByUser + "*");
        verify(documentService, never()).findDocumentReferences(queryStringSentByUser);
    }
}
