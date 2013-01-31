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

import ch.silviowangler.dox.api.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static javax.servlet.http.HttpServletResponse.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
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
}
