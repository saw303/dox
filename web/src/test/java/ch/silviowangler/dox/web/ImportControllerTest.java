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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

import com.google.common.collect.Lists;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.WebRequest;

import ch.silviowangler.dox.api.DocumentClassNotFoundException;
import ch.silviowangler.dox.api.DocumentDuplicationException;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.DocumentService;
import ch.silviowangler.dox.api.PhysicalDocument;
import ch.silviowangler.dox.api.ValidationException;

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
    private WebRequest request;


    @Test
    public void importDocument() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException {

        when(request.getParameterNames()).thenReturn(Lists.asList("a", new String[]{"b", "c"}).iterator());
        when(request.getParameter("a")).thenReturn("A");
        when(request.getParameter("b")).thenReturn("B");
        when(request.getParameter("c")).thenReturn("C");

        when(documentService.importDocument(any(PhysicalDocument.class))).thenReturn(new DocumentReference("test.pdf"));

        ResponseEntity responseEntity = controller.importDocument(new MockMultipartFile("test.pdf", "this is just a test".getBytes()), request, "Client");
        assertThat(responseEntity.getStatusCode(), is(CREATED));

        verify(documentService).importDocument(any(PhysicalDocument.class));
    }

    @Test
    public void importDocumentDuplicateDocument() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException {

        when(request.getParameterNames()).thenReturn(Lists.asList("a", new String[]{"b", "c"}).iterator());
        when(request.getParameter("a")).thenReturn("A");
        when(request.getParameter("b")).thenReturn("B");
        when(request.getParameter("c")).thenReturn("C");
        when(documentService.importDocument(any(PhysicalDocument.class))).thenThrow(new DocumentDuplicationException(1L, "hash"));

        ResponseEntity responseEntity = controller.importDocument(new MockMultipartFile("test.pdf", "this is just a test".getBytes()), request, "Client");
        assertThat(responseEntity.getStatusCode(), is(CONFLICT));

        verify(documentService).importDocument(any(PhysicalDocument.class));
    }

    @Test
    public void importDocumentValidationException() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException {

        when(request.getParameterNames()).thenReturn(Lists.asList("a", new String[]{"b", "c"}).iterator());
        when(request.getParameter("a")).thenReturn("A");
        when(request.getParameter("b")).thenReturn("B");
        when(request.getParameter("c")).thenReturn("C");
        when(documentService.importDocument(any(PhysicalDocument.class))).thenThrow(new ValidationException("bla"));

        ResponseEntity responseEntity = controller.importDocument(new MockMultipartFile("test.pdf", "this is just a test".getBytes()), request, "Client");

        assertThat(responseEntity.getStatusCode(), is(CONFLICT));

        verify(documentService).importDocument(any(PhysicalDocument.class));
    }
}
