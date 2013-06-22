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
import ch.silviowangler.dox.api.stats.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.SortedSet;
import java.util.TreeSet;

import static ch.silviowangler.dox.web.DocumentReferenceBuilder.newDocumentReference;
import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 14.01.13 22:33
 *        </div>
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {

    @Mock
    private DocumentService documentService;
    @Mock
    private StatisticsService statisticsService;
    @InjectMocks
    private DocumentController controller = new DocumentController();

    @Test
    public void getTheDocumentContent() throws DocumentNotFoundException, DocumentNotInStoreException, UnsupportedEncodingException {

        MockHttpServletResponse response = new MockHttpServletResponse();

        PhysicalDocument physicalDocument = new PhysicalDocument(new DocumentClass("hhh"), "hello".getBytes(), null, "hello.txt");
        physicalDocument.setMimeType("aaa/bbb");
        when(documentService.findPhysicalDocument(1L)).thenReturn(physicalDocument);

        controller.getDocument(1L, response);

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getContentAsString(), is("hello"));
        assertThat(response.getContentType(), is("aaa/bbb"));
        assertThat(response.getHeader("Content-Disposition"), is("inline; filename=\"hello.txt\""));
    }

    @Test
    public void getTheDocumentContentExpectNotFound() throws DocumentNotFoundException, DocumentNotInStoreException, UnsupportedEncodingException {

        MockHttpServletResponse response = new MockHttpServletResponse();
        long expectedDocumentId = 2L;
        when(documentService.findPhysicalDocument(expectedDocumentId)).thenThrow(new DocumentNotFoundException(expectedDocumentId));
        controller.getDocument(expectedDocumentId, response);
        assertThat(response.getStatus(), is(SC_NOT_FOUND));
    }

    @Test
    public void getTheDocumentContentExpectNotFound2() throws DocumentNotFoundException, DocumentNotInStoreException, UnsupportedEncodingException {

        MockHttpServletResponse response = new MockHttpServletResponse();
        long expectedDocumentId = 2L;
        when(documentService.findPhysicalDocument(expectedDocumentId)).thenThrow(new DocumentNotInStoreException("adsfdfadsf", expectedDocumentId));
        controller.getDocument(expectedDocumentId, response);
        assertThat(response.getStatus(), is(SC_NOT_FOUND));
    }

    @Test
    public void getTheDocumentContentExpectNotFound3() throws DocumentNotFoundException, DocumentNotInStoreException, IOException {

        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getOutputStream()).thenThrow(new IOException());
        long expectedDocumentId = 2L;
        when(documentService.findPhysicalDocument(expectedDocumentId)).thenReturn(new PhysicalDocument());
        controller.getDocument(expectedDocumentId, response);

        verify(response).setStatus(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void edit() throws Exception {

        final DocumentClass documentClass = new DocumentClass("hello");
        final DocumentReference documentReference = newDocumentReference("hello.txt").withDocumentClass(documentClass).build();

        when(documentService.findDocumentReference(1L)).thenReturn(documentReference);
        SortedSet<Attribute> attributes = new TreeSet<>();
        attributes.add(new Attribute("a", false, AttributeDataType.CURRENCY));
        attributes.add(new Attribute("b", false, AttributeDataType.STRING));
        when(documentService.findAttributes(documentClass)).thenReturn(attributes);

        final ModelAndView modelAndView = controller.editDocument(1L);

        assertThat(modelAndView.getViewName(), is("edit.doc"));
        assertThat(modelAndView.getModel().size(), is(2));
        assertThat(modelAndView.getModel().containsKey("doc"), is(true));
        assertThat((DocumentReference) modelAndView.getModel().get("doc"), is(documentReference));
        assertThat(modelAndView.getModel().containsKey("attributes"), is(true));
        assertThat(((SortedSet<Attribute>) modelAndView.getModel().get("attributes")).size(), is(2));

        InOrder order = inOrder(documentService);
        order.verify(documentService).findDocumentReference(1L);
        order.verify(documentService).findAttributes(documentReference.getDocumentClass());
        order.verifyNoMoreInteractions();
    }

    @Test
    public void editWhenThrowsException() throws Exception {

        when(documentService.findDocumentReference(1L)).thenThrow(new DocumentNotFoundException(1L));

        final ModelAndView modelAndView = controller.editDocument(1L);

        assertThat(modelAndView.getViewName(), is("edit.doc"));
        assertThat(modelAndView.getModel().isEmpty(), is(true));
    }

    @Test
    public void editDocumentWhenThrowsException() throws Exception {

        when(documentService.findDocumentReference(1L)).thenThrow(new DocumentNotFoundException(1L));

        final ModelAndView modelAndView = controller.editDocument(1L, new MockHttpServletRequest());

        assertThat(modelAndView.getViewName(), is("modification.doc.failed"));
        assertThat(modelAndView.getModel().isEmpty(), is(true));
    }

    @Test
    public void editDocument() throws DocumentNotFoundException {

        final DocumentReference documentReference = newDocumentReference("hello.txt").withDocumentClass("test").build();

        when(documentService.findDocumentReference(1L)).thenReturn(documentReference);

        final ModelAndView modelAndView = controller.editDocument(1L, new MockHttpServletRequest());

        assertThat(modelAndView.getViewName(), is("import.successful"));
        assertThat(modelAndView.getModel().size(), is(1));
        assertThat(modelAndView.getModel().containsKey("doc"), is(true));
        assertThat((DocumentReference) modelAndView.getModel().get("doc"), is(documentReference));

        InOrder order = inOrder(documentService);

        order.verify(documentService).findDocumentReference(1L);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void editDocumentWithParams() throws DocumentNotFoundException {

        final DocumentReference documentReference = newDocumentReference("hello.txt").withDocumentClass("test").withIndex("name", "Silvio").build();

        when(documentService.findDocumentReference(1L)).thenReturn(documentReference);
        when(documentService.updateIndices(documentReference)).thenReturn(documentReference);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "Wangler");
        final ModelAndView modelAndView = controller.editDocument(1L, request);

        assertThat(modelAndView.getViewName(), is("import.successful"));
        assertThat(modelAndView.getModel().size(), is(1));
        assertThat(modelAndView.getModel().containsKey("doc"), is(true));
        final DocumentReference doc = (DocumentReference) modelAndView.getModel().get("doc");
        assertThat(doc, is(documentReference));
        assertThat(doc.getIndices().get(new TranslatableKey("name")).toString(), is("Wangler"));

        InOrder order = inOrder(documentService);

        order.verify(documentService).findDocumentReference(1L);
        order.verify(documentService).updateIndices(documentReference);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void editDocumentWithParams2() throws DocumentNotFoundException {

        final DocumentReference documentReference = newDocumentReference("hello.txt").withDocumentClass("test").withIndex("name", "Wangler").build();

        when(documentService.findDocumentReference(1L)).thenReturn(documentReference);
        when(documentService.updateIndices(documentReference)).thenReturn(documentReference);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstname", "Silvio");
        final ModelAndView modelAndView = controller.editDocument(1L, request);

        assertThat(modelAndView.getViewName(), is("import.successful"));
        assertThat(modelAndView.getModel().size(), is(1));
        assertThat(modelAndView.getModel().containsKey("doc"), is(true));
        final DocumentReference doc = (DocumentReference) modelAndView.getModel().get("doc");
        assertThat(doc, is(documentReference));
        assertThat(doc.getIndices().get(new TranslatableKey("name")).toString(), is("Wangler"));

        InOrder order = inOrder(documentService);

        order.verify(documentService).findDocumentReference(1L);
        order.verify(documentService, never()).updateIndices(documentReference);
        order.verifyNoMoreInteractions();
    }
}
