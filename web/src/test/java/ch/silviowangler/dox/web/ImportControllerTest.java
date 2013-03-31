/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.web;

import ch.silviowangler.dox.api.Attribute;
import ch.silviowangler.dox.api.DocumentClass;
import ch.silviowangler.dox.api.DocumentClassNotFoundException;
import ch.silviowangler.dox.api.DocumentService;
import ch.silviowangler.dox.web.util.DeviceMock;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mobile.device.Device;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Locale.GERMAN;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class ImportControllerTest {

    @InjectMocks
    private ImportController controller = new ImportController();
    @Mock
    private DocumentService documentService;
    @Mock
    private MessageSource messageSource;


    @Test
    @SuppressWarnings("unchecked")
    public void testQuerySortAlphabeticallyByTranslationInGerman() throws Exception {

        when(documentService.findDocumentClasses()).thenReturn(
                newHashSet(new DocumentClass("a", "Z-Klasse"), new DocumentClass("b", "A-Klasse"), new DocumentClass("c", "C-Klasse"))
        );

        ModelAndView modelAndView = controller.query(GERMAN);

        assertThat(modelAndView.getModel().size(), is(2));
        assertThat(modelAndView.getModel().containsKey("documentClasses"), is(true));
        assertThat(modelAndView.getModel().containsKey("defaultMessage"), is(true));

        assertTrue(modelAndView.getModel().get("documentClasses") instanceof List);
        List<DocumentClass> documentClasses = (List<DocumentClass>) modelAndView.getModel().get("documentClasses");

        assertThat(documentClasses.size(), is(3));
        assertThat(documentClasses.get(0).getShortName(), is("b"));
        assertThat(documentClasses.get(1).getShortName(), is("c"));
        assertThat(documentClasses.get(2).getShortName(), is("a"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testQuerySortAlphabeticallyByTranslationInEnglish() throws Exception {

        when(documentService.findDocumentClasses()).thenReturn(
                newHashSet(new DocumentClass("z", "Aeroport"), new DocumentClass("d", "Houston"), new DocumentClass("c", "Zero"))
        );

        ModelAndView modelAndView = controller.query(GERMAN);

        assertThat(modelAndView.getViewName(), is("import.definition"));
        assertThat(modelAndView.getModel().size(), is(2));
        assertThat(modelAndView.getModel().containsKey("documentClasses"), is(true));
        assertThat(modelAndView.getModel().containsKey("defaultMessage"), is(true));


        assertTrue(modelAndView.getModel().get("documentClasses") instanceof List);
        List<DocumentClass> documentClasses = (List<DocumentClass>) modelAndView.getModel().get("documentClasses");

        assertThat(documentClasses.size(), is(3));
        assertThat(documentClasses.get(0).getShortName(), is("z"));
        assertThat(documentClasses.get(1).getShortName(), is("d"));
        assertThat(documentClasses.get(2).getShortName(), is("c"));
    }

    @Test
    public void attributeForm() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(new TreeSet<Attribute>());
        when(messageSource.getMessage("document.import.no.attributes", new Object[]{docclass}, GERMAN)).thenReturn("an error message");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock());

        assertThat(html, is("<ul id=\"errors\"><li id=\"info\">an error message</li></ul>"));
    }
}
