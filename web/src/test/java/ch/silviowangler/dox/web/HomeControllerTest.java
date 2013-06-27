/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.web;

import ch.silviowangler.dox.api.DocumentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 31.01.13 00:37
 *        </div>
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    @Mock
    private DocumentService documentService;
    @InjectMocks
    private HomeController controller = new HomeController();

    @Test
    public void testViewName() {
        final String expectedQueryString = "a silly modelAndView";
        ModelAndView modelAndView = controller.query(expectedQueryString, false, false);

        assertThat(modelAndView.getViewName(), is("result.definition"));
        assertTrue(modelAndView.getModelMap().containsKey("documents"));
        assertTrue(modelAndView.getModelMap().containsKey("query"));
        assertThat(modelAndView.getModelMap().get("query").toString(), is(expectedQueryString));
        assertThat(modelAndView.getModelMap().size(), is(2));

        verify(documentService).findDocumentReferences(expectedQueryString);
        verify(documentService, never()).findDocumentReferencesForCurrentUser(expectedQueryString);
    }

    @Test
    public void testWildcard() {
        final String queryStringSentByUser = "a silly modelAndView";
        final String expectedQueryString = "*" + queryStringSentByUser + "*";

        ModelAndView modelAndView = controller.query(queryStringSentByUser, true, false);

        assertThat(modelAndView.getViewName(), is("result.definition"));
        assertTrue(modelAndView.getModelMap().containsKey("documents"));
        assertTrue(modelAndView.getModelMap().containsKey("query"));

        assertThat(modelAndView.getModelMap().get("query").toString(), is(queryStringSentByUser));
        assertThat(modelAndView.getModelMap().size(), is(2));

        verify(documentService).findDocumentReferences(expectedQueryString);
        verify(documentService, never()).findDocumentReferencesForCurrentUser(queryStringSentByUser);
    }

    @Test
    public void testWildcardOnlyIfNoWildcardAreAlreadyInTheQuery() {
        final String queryStringSentByUser = "a s?lly model*View";

        ModelAndView modelAndView = controller.query(queryStringSentByUser, true, false);

        assertThat(modelAndView.getViewName(), is("result.definition"));
        assertTrue(modelAndView.getModelMap().containsKey("documents"));
        assertTrue(modelAndView.getModelMap().containsKey("query"));

        assertThat(modelAndView.getModelMap().get("query").toString(), is(queryStringSentByUser));
        assertThat(modelAndView.getModelMap().size(), is(2));

        verify(documentService).findDocumentReferences(queryStringSentByUser);
        verify(documentService, never()).findDocumentReferencesForCurrentUser(queryStringSentByUser);
    }

    @Test
    public void queryForCurrentUserOnly() {
        final String queryStringSentByUser = "this is not rocket science";

        ModelAndView modelAndView = controller.query(queryStringSentByUser, false, true);

        assertThat(modelAndView.getViewName(), is("result.definition"));
        assertTrue(modelAndView.getModelMap().containsKey("documents"));
        assertTrue(modelAndView.getModelMap().containsKey("query"));

        assertThat(modelAndView.getModelMap().get("query").toString(), is(queryStringSentByUser));
        assertThat(modelAndView.getModelMap().size(), is(2));

        verify(documentService).findDocumentReferencesForCurrentUser(queryStringSentByUser);
        verify(documentService, never()).findDocumentReferences(queryStringSentByUser);

    }

    @Test
    public void queryForCurrentUserOnlyUsingWildcard() {
        final String queryStringSentByUser = "this is not rocket science";

        ModelAndView modelAndView = controller.query(queryStringSentByUser, true, true);

        assertThat(modelAndView.getViewName(), is("result.definition"));
        assertTrue(modelAndView.getModelMap().containsKey("documents"));
        assertTrue(modelAndView.getModelMap().containsKey("query"));

        assertThat(modelAndView.getModelMap().get("query").toString(), is(queryStringSentByUser));
        assertThat(modelAndView.getModelMap().size(), is(2));

        verify(documentService).findDocumentReferencesForCurrentUser("*" + queryStringSentByUser + "*");
        verify(documentService, never()).findDocumentReferences(queryStringSentByUser);
    }
}
