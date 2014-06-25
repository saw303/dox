/*
 * Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
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
import ch.silviowangler.dox.api.Index;
import ch.silviowangler.dox.api.TranslatableKey;
import com.google.common.collect.Maps;
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
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import static org.junit.Assert.assertEquals;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentAttributeListTagTest {

    public static final TranslatableKey A_PROP = new TranslatableKey("aProp", "Name");
    private DocumentReference documentReference;
    @InjectMocks
    private DocumentAttributeListTag tag = new DocumentAttributeListTag();
    private PageContext pageContext;
    @Mock
    private MessageSource messageSource;
    private Map<TranslatableKey, Index> indices;
    private ServletContext servletContext;
    private MockHttpServletRequest request;
    @Mock
    private WebApplicationContext webApplicationContext;

    private final String newLine = System.lineSeparator();


    @Before
    public void init() {

        indices = Maps.newHashMap();

        servletContext = new MockServletContext();

        request = new MockHttpServletRequest(servletContext);
        request.addPreferredLocale(Locale.GERMAN);

        request.setAttribute(RequestContext.WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);

        pageContext = new MockPageContext(servletContext, request);

        documentReference = new DocumentReference("dummy.txt");
        documentReference.setIndices(indices);
        tag.setDocumentReference(documentReference);
        tag.setPageContext(pageContext);
    }

    @Test
    public void emptyListShouldResultInEmptyOutput() throws JspException, IOException {
        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("", ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void oneIndexShouldResultInEmptyOutput() throws JspException, IOException {

        indices.put(new TranslatableKey("wicked", "Name"), new Index("Hello"));

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<ul><li>Name = Hello</li></ul>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void twoIndexShouldResultInEmptyOutput() throws JspException, IOException {

        indices.put(A_PROP, new Index("Computer"));
        indices.put(new TranslatableKey("test", "Lisa"), new Index("Fellow"));
        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<ul><li>Name = Computer</li><li>Lisa = Fellow</li></ul>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void jodaTimeDateTimeShouldBeFormattedAccordingTheCurrentLocale() throws JspException, IOException {

        indices.put(A_PROP, new Index( new DateTime(2010, 11, 01, 0, 0)));
        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<ul><li>Name = 01.11.2010</li></ul>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void jodaTimeDateTimeShouldBeFormattedAccordingTheCanadianLocale() throws JspException, IOException {

        request.addPreferredLocale(Locale.CANADA);

        indices.put(A_PROP, new Index( new DateTime(2010, 11, 01, 0, 0)));

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<ul><li>Name = 1-Nov-2010</li></ul>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void jodaTimeDateTimeShouldBeFormattedAccordingTheUKLocale() throws JspException, IOException {

        request.addPreferredLocale(Locale.UK);

        indices.put(A_PROP, new Index( new DateTime(2010, 11, 01, 0, 0)));

        assertEquals(SKIP_BODY, tag.doStartTag());
        assertEquals("<ul><li>Name = 01-Nov-2010</li></ul>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }
}
