/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.web.taglib;

import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.TranslatableKey;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.System.lineSeparator;
import static java.util.Locale.GERMAN;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import static org.junit.Assert.assertEquals;
import static org.springframework.web.servlet.support.RequestContext.WEB_APPLICATION_CONTEXT_ATTRIBUTE;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentAttributeListingTagTest {


    private DocumentReference documentReference;
    @InjectMocks
    private DocumentAttributeListingTag tag = new DocumentAttributeListingTag();
    private PageContext pageContext;
    @Mock
    private MessageSource messageSource;
    private Map<TranslatableKey, Object> indices;
    private ServletContext servletContext;
    private MockHttpServletRequest request;
    @Mock
    private WebApplicationContext webApplicationContext;

    private final String newLine = lineSeparator();

    @Before
    public void init() {

        indices = newHashMap();

        servletContext = new MockServletContext();

        request = new MockHttpServletRequest(servletContext);
        request.addPreferredLocale(GERMAN);

        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);

        pageContext = new MockPageContext(servletContext, request);

        documentReference = new DocumentReference("dummy.txt");
        documentReference.setIndices(indices);
        tag.setDocumentReference(documentReference);
        tag.setPageContext(pageContext);
    }


    @Test
    public void empty() throws JspException, UnsupportedEncodingException {
        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("", ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void singleThatDoesNotMatchQuery() throws UnsupportedEncodingException, JspException {
        indices.put(new TranslatableKey("wicked", "Name"), "Hello");

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<div class=\"attributeListing\">Name:&nbsp;Hello</div>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void singleThatDoesMatchQuery() throws UnsupportedEncodingException, JspException {
        indices.put(new TranslatableKey("wicked", "Name"), "Hello");
        tag.setQuery("Hello");

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<div class=\"attributeListing\">Name:&nbsp;<span class=\"highlight\">Hello</span></div>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void singleThatDoesMatchQueryWithWildcard() throws UnsupportedEncodingException, JspException {
        indices.put(new TranslatableKey("wicked", "Name"), "Hello");
        tag.setQuery("*ello");

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<div class=\"attributeListing\">Name:&nbsp;<span class=\"highlight\">Hello</span></div>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void singleThatDoesMatchQueryWithWildcard2() throws UnsupportedEncodingException, JspException {
        indices.put(new TranslatableKey("wicked", "Name"), "Hello");
        tag.setQuery("*el?o");

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<div class=\"attributeListing\">Name:&nbsp;<span class=\"highlight\">Hello</span></div>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void singleThatDoesMatchQueryWithWildcard3() throws UnsupportedEncodingException, JspException {
        indices.put(new TranslatableKey("wicked", "Name"), "Hello");
        tag.setQuery("*e*o");

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<div class=\"attributeListing\">Name:&nbsp;<span class=\"highlight\">Hello</span></div>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void singleThatDoesMatchQueryWithWildcard4() throws UnsupportedEncodingException, JspException {
        indices.put(new TranslatableKey("dob", "Date Of Birth"), new DateTime(2013, 8, 11, 0, 0));
        tag.setQuery("*13");

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<div class=\"attributeListing\">Date Of Birth:&nbsp;<span class=\"highlight\">11.08.2013</span></div>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void singleThatDoesMatchQueryWithWildcard5() throws UnsupportedEncodingException, JspException {
        indices.put(new TranslatableKey("dob", "Date Of Birth"), new DateTime(2013, 8, 11, 0, 0));
        indices.put(new TranslatableKey("amount", "Amount"), 1234L);
        tag.setQuery("*13");

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<div class=\"attributeListing\">Amount:&nbsp;1234&nbsp;Date Of Birth:&nbsp;<span class=\"highlight\">11.08.2013</span></div>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void formatDate() throws UnsupportedEncodingException, JspException {

        indices.put(new TranslatableKey("wicked", "Name"), "Hello");
        indices.put(new TranslatableKey("dob", "Date Of Birth"), new DateTime(2013, 2, 23, 0, 0));
        tag.setQuery("Hello");

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<div class=\"attributeListing\">Date Of Birth:&nbsp;23.02.2013&nbsp;Name:&nbsp;<span class=\"highlight\">Hello</span></div>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }
}
