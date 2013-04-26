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

import ch.silviowangler.dox.api.*;
import ch.silviowangler.dox.web.util.DeviceMock;
import ch.silviowangler.dox.web.util.TemplateEngine;
import ch.silviowangler.dox.web.util.TemplateEngineImpl;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.TreeSet;

import static ch.silviowangler.dox.api.AttributeDataType.*;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newTreeSet;
import static java.util.Locale.GERMAN;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
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
    @Spy
    private TemplateEngine templateEngine = new TemplateEngineImpl();
    @Mock
    private WebRequest request;

    @Before
    public void setupMocks() {
        when(messageSource.getMessage("button.import.document", null, GERMAN)).thenReturn("Senden");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testQuerySortAlphabeticallyByTranslationInGerman() throws Exception {

        when(documentService.findDocumentClasses()).thenReturn(
                newHashSet(new DocumentClass("a", "Z-Klasse"), new DocumentClass("b", "A-Klasse"), new DocumentClass("c", "C-Klasse"))
        );

        ModelAndView modelAndView = controller.query(GERMAN, false);

        assertThat(modelAndView.getModel().size(), is(3));
        assertThat(modelAndView.getModel().containsKey("documentClasses"), is(true));
        assertThat(modelAndView.getModel().containsKey("defaultMessage"), is(true));
        assertThat(modelAndView.getModel().containsKey("advancedQuery"), is(true));
        assertThat((Boolean) modelAndView.getModel().get("advancedQuery"), is(false));

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

        ModelAndView modelAndView = controller.query(GERMAN, false);

        assertThat(modelAndView.getViewName(), is("import.definition"));
        assertThat(modelAndView.getModel().size(), is(3));
        assertThat(modelAndView.getModel().containsKey("documentClasses"), is(true));
        assertThat(modelAndView.getModel().containsKey("defaultMessage"), is(true));
        assertThat(modelAndView.getModel().containsKey("advancedQuery"), is(true));
        assertThat((Boolean) modelAndView.getModel().get("advancedQuery"), is(false));


        assertTrue(modelAndView.getModel().get("documentClasses") instanceof List);
        List<DocumentClass> documentClasses = (List<DocumentClass>) modelAndView.getModel().get("documentClasses");

        assertThat(documentClasses.size(), is(3));
        assertThat(documentClasses.get(0).getShortName(), is("z"));
        assertThat(documentClasses.get(1).getShortName(), is("d"));
        assertThat(documentClasses.get(2).getShortName(), is("c"));
    }

    @Test
    public void verifyHtmlInErrorCase() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(new TreeSet<Attribute>());
        when(messageSource.getMessage("document.import.no.attributes", new Object[]{docclass}, GERMAN)).thenReturn("an error message");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), false);

        assertThat(html, is("<ul id=\"errors\"><li id=\"info\">an error message</li></ul>"));
    }

    @Test
    public void verifyHtmlInDocumentClassNotFound() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        when(documentService.findAttributes(new DocumentClass(docclass))).thenThrow(new DocumentClassNotFoundException(docclass));
        when(messageSource.getMessage("document.import.no.attributes", new Object[]{docclass}, GERMAN)).thenReturn("an error message bla bla bla");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), false);

        assertThat(html, is("<ul id=\"errors\"><li id=\"info\">an error message bla bla bla</li></ul>"));
    }

    @Test
    public void verifyHtmlForMandatoryStringAttribute() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(newTreeSet(newArrayList(new Attribute("attr1", false, STRING))));
        when(messageSource.getMessage("document.import.button.submit", null, GERMAN)).thenReturn("Senden");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), false);

        assertThat(html, is("<form id=\"fileUpload\" method=\"POST\" action=\"performImport.html\" enctype=\"multipart/form-data\">\n<input name=\"documentClassShortName\" type=\"hidden\" value=\"docclass\"/>\n<label for=\"attr1\">null: <span class=\"required\">*</span></label>\n<input name=\"attr1\" type=\"text\" required />\n<input name=\"file\" type=\"file\" required/>\n\n<button type=\"submit\" id=\"importDocBtn\">Senden</button>\n</form>"));

    }

    @Test
    public void verifyHtmlForOptionalStringAttribute() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(newTreeSet(newArrayList(new Attribute("attr1", true, STRING))));
        when(messageSource.getMessage("document.import.button.submit", null, GERMAN)).thenReturn("Senden");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), false);

        assertThat(html, is("<form id=\"fileUpload\" method=\"POST\" action=\"performImport.html\" enctype=\"multipart/form-data\">\n<input name=\"documentClassShortName\" type=\"hidden\" value=\"docclass\"/>\n<label for=\"attr1\">null:</label>\n<input name=\"attr1\" type=\"text\" />\n<input name=\"file\" type=\"file\" required/>\n\n<button type=\"submit\" id=\"importDocBtn\">Senden</button>\n</form>"));
    }

    @Test
    public void verifyHtmlForMandatoryStringAttributeWithDomain() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        Domain domain = new Domain("domain");
        domain.getValues().add("A");
        domain.getValues().add("B");
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(newTreeSet(newArrayList(new Attribute("attr1", false, domain, STRING, true))));
        when(messageSource.getMessage("document.import.button.submit", null, GERMAN)).thenReturn("Senden");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), false);

        assertThat(html, is("<form id=\"fileUpload\" method=\"POST\" action=\"performImport.html\" enctype=\"multipart/form-data\">\n<input name=\"documentClassShortName\" type=\"hidden\" value=\"docclass\"/>\n<label for=\"attr1\">null: <span class=\"required\">*</span></label>\n<datalist id=\"list-attr1\"><option value=\"A\"/><option value=\"B\"/></datalist>\n<input name=\"attr1\" list=\"list-attr1\" required />\n<input name=\"file\" type=\"file\" required/>\n\n<button type=\"submit\" id=\"importDocBtn\">Senden</button>\n</form>"));
    }

    @Test
    public void verifyHtmlForOptionalStringAttributeWithDomain() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        Domain domain = new Domain("domain");
        domain.getValues().add("A");
        domain.getValues().add("B");
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(newTreeSet(newArrayList(new Attribute("attr1", true, domain, STRING, true))));
        when(messageSource.getMessage("document.import.button.submit", null, GERMAN)).thenReturn("Senden");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), false);

        assertThat(html, is("<form id=\"fileUpload\" method=\"POST\" action=\"performImport.html\" enctype=\"multipart/form-data\">\n<input name=\"documentClassShortName\" type=\"hidden\" value=\"docclass\"/>\n<label for=\"attr1\">null:</label>\n<datalist id=\"list-attr1\"><option value=\"A\"/><option value=\"B\"/></datalist>\n<input name=\"attr1\" list=\"list-attr1\" />\n<input name=\"file\" type=\"file\" required/>\n\n<button type=\"submit\" id=\"importDocBtn\">Senden</button>\n</form>"));
    }

    @Test
    public void verifyHtmlForMandatoryIntegerAttribute() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(newTreeSet(newArrayList(new Attribute("attr2", false, INTEGER))));
        when(messageSource.getMessage("document.import.button.submit", null, GERMAN)).thenReturn("Senden");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), false);

        assertThat(html, is("<form id=\"fileUpload\" method=\"POST\" action=\"performImport.html\" enctype=\"multipart/form-data\">\n<input name=\"documentClassShortName\" type=\"hidden\" value=\"docclass\"/>\n<label for=\"attr2\">null: <span class=\"required\">*</span></label>\n<input name=\"attr2\" type=\"number\" min=\"0\" step=\"any\" required />\n<input name=\"file\" type=\"file\" required/>\n\n<button type=\"submit\" id=\"importDocBtn\">Senden</button>\n</form>"));
    }

    @Test
    public void verifyHtmlForOptionalIntegerAttribute() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(newTreeSet(newArrayList(new Attribute("attr2", true, INTEGER))));
        when(messageSource.getMessage("document.import.button.submit", null, GERMAN)).thenReturn("Senden");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), false);

        assertThat(html, is("<form id=\"fileUpload\" method=\"POST\" action=\"performImport.html\" enctype=\"multipart/form-data\">\n<input name=\"documentClassShortName\" type=\"hidden\" value=\"docclass\"/>\n<label for=\"attr2\">null:</label>\n<input name=\"attr2\" type=\"number\" min=\"0\" step=\"any\" />\n<input name=\"file\" type=\"file\" required/>\n\n<button type=\"submit\" id=\"importDocBtn\">Senden</button>\n</form>"));
    }

    @Test
    public void verifyHtmlForMandatoryDateAttribute() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(newTreeSet(newArrayList(new Attribute("attr3", false, DATE))));
        when(messageSource.getMessage("document.import.button.submit", null, GERMAN)).thenReturn("Senden");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), false);

        assertThat(html, is("<form id=\"fileUpload\" method=\"POST\" action=\"performImport.html\" enctype=\"multipart/form-data\">\n<input name=\"documentClassShortName\" type=\"hidden\" value=\"docclass\"/>\n<label for=\"attr3\">null: <span class=\"required\">*</span></label>\n<input name=\"attr3\" type=\"date\" required />\n<input name=\"file\" type=\"file\" required/>\n\n<button type=\"submit\" id=\"importDocBtn\">Senden</button>\n</form>"));
    }

    @Test
    public void verifyHtmlForOptionalDateAttribute() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(newTreeSet(newArrayList(new Attribute("attr3", true, DATE))));
        when(messageSource.getMessage("document.import.button.submit", null, GERMAN)).thenReturn("Senden");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), false);

        assertThat(html, is("<form id=\"fileUpload\" method=\"POST\" action=\"performImport.html\" enctype=\"multipart/form-data\">\n<input name=\"documentClassShortName\" type=\"hidden\" value=\"docclass\"/>\n<label for=\"attr3\">null:</label>\n<input name=\"attr3\" type=\"date\" />\n<input name=\"file\" type=\"file\" required/>\n\n<button type=\"submit\" id=\"importDocBtn\">Senden</button>\n</form>"));
    }

    @Test
    public void verifyHtmlForOptionalDateAttribute2() throws DocumentClassNotFoundException {

        final String docclass = "docclass";
        when(documentService.findAttributes(new DocumentClass(docclass))).thenReturn(newTreeSet(newArrayList(new Attribute("attr3", true, DATE))));
        when(messageSource.getMessage("query.start.button", null, GERMAN)).thenReturn("Go for it");

        String html = controller.getAttributeForm(docclass, GERMAN, new DeviceMock(), true);

        assertThat(html, is("<form id=\"fileUpload\" method=\"POST\" action=\"extendedQuery.html\" enctype=\"multipart/form-data\">\n<input name=\"documentClassShortName\" type=\"hidden\" value=\"docclass\"/>\n<label for=\"attr3\">null:</label>\n<input name=\"attr3\" type=\"date\" />\n\n<button type=\"submit\" id=\"importDocBtn\">Go for it</button>\n</form>"));
    }

    @Test
    public void importDocument() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException {

        when(request.getParameterNames()).thenReturn(Lists.asList("a", new String[]{"b", "c"}).iterator());
        when(request.getParameter("a")).thenReturn("A");
        when(request.getParameter("b")).thenReturn("B");
        when(request.getParameter("c")).thenReturn("C");

        final ModelAndView modelAndView = controller.importDocument(new MockMultipartFile("test.pdf", "this is just a test".getBytes()), request);

        assertThat(modelAndView.getViewName(), is("import.successful"));
        assertThat(modelAndView.getModel().size(), is(1));

        verify(documentService).importDocument(any(PhysicalDocument.class));
    }

    @Test
    public void importDocumentDuplicateDocument() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException {

        when(request.getParameterNames()).thenReturn(Lists.asList("a", new String[]{"b", "c"}).iterator());
        when(request.getParameter("a")).thenReturn("A");
        when(request.getParameter("b")).thenReturn("B");
        when(request.getParameter("c")).thenReturn("C");
        when(documentService.importDocument(any(PhysicalDocument.class))).thenThrow(new DocumentDuplicationException(1L, "hash"));

        final ModelAndView modelAndView = controller.importDocument(new MockMultipartFile("test.pdf", "this is just a test".getBytes()), request);

        assertThat(modelAndView.getViewName(), is("import.duplicate.document"));
        assertThat(modelAndView.getModel().size(), is(2));
        assertThat(modelAndView.getModel().get("docId").toString(), is("1"));
        assertThat(modelAndView.getModel().get("docHash").toString(), is("hash"));

        verify(documentService).importDocument(any(PhysicalDocument.class));
    }

    @Test
    public void importDocumentValidationException() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException {

        when(request.getParameterNames()).thenReturn(Lists.asList("a", new String[]{"b", "c"}).iterator());
        when(request.getParameter("a")).thenReturn("A");
        when(request.getParameter("b")).thenReturn("B");
        when(request.getParameter("c")).thenReturn("C");
        when(documentService.importDocument(any(PhysicalDocument.class))).thenThrow(new ValidationException("bla"));

        final ModelAndView modelAndView = controller.importDocument(new MockMultipartFile("test.pdf", "this is just a test".getBytes()), request);

        assertThat(modelAndView.getViewName(), is("import.failure"));
        assertThat(modelAndView.getModel().size(), is(1));
        assertThat(((Exception) modelAndView.getModel().get("exception")).getMessage(), is("bla"));

        verify(documentService).importDocument(any(PhysicalDocument.class));
    }
}
