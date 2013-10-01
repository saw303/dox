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

package ch.silviowangler.dox;

import ch.silviowangler.dox.api.DocumentService;
import ch.silviowangler.dox.domain.Document;
import ch.silviowangler.dox.domain.IndexStore;
import ch.silviowangler.dox.repository.DocumentRepository;
import ch.silviowangler.dox.repository.IndexStoreRepository;
import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceImplTest {

    @InjectMocks
    private DocumentService documentService = new DocumentServiceImpl();
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private IndexStoreRepository indexStoreRepository;
    @Mock
    Authentication authentication;

    @After
    public void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testRetrieveDocumentReferenceCount() throws Exception {

        when(documentRepository.count()).thenReturn(99L);
        assertThat(documentService.retrieveDocumentReferenceCount(), is(99L));
        verify(documentRepository).count();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteDocumentUsingNullId() throws Exception {
        documentService.deleteDocument(null);
    }

    @Test
    public void testDeleteDocument() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<SimpleGrantedAuthority> authorities = Sets.newHashSet();
        when(authentication.getPrincipal()).thenReturn(new User("saw303", "", authorities));

        Document document = new Document();
        document.setIndexStore(new IndexStore());

        document.setUserReference("saw303");

        final long documentId = 1L;

        when(documentRepository.findOne(documentId)).thenReturn(document);
        InOrder order = inOrder(documentRepository, indexStoreRepository);

        documentService.deleteDocument(documentId);

        order.verify(documentRepository).findOne(documentId);
        order.verify(indexStoreRepository).delete(Matchers.<IndexStore>any());
        order.verify(documentRepository).delete(documentId);

        order.verifyNoMoreInteractions();
    }

    @Test(expected = AccessDeniedException.class)
    public void testDeleteDocumentThatDoesNotBelongToTheCurrentUser() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<SimpleGrantedAuthority> authorities = Sets.newHashSet();
        when(authentication.getPrincipal()).thenReturn(new User("guschti", "", authorities));

        Document document = new Document();
        document.setIndexStore(new IndexStore());

        document.setUserReference("saw303");

        final long documentId = 1L;

        when(documentRepository.findOne(documentId)).thenReturn(document);
        documentService.deleteDocument(documentId);
    }

    @Test
    public void testDeleteDocumentThatDoesNotExist() throws Exception {

        final long documentId = 1L;

        when(documentRepository.findOne(documentId)).thenReturn(null);

        documentService.deleteDocument(documentId);

        InOrder order = inOrder(documentRepository, indexStoreRepository);
        order.verify(documentRepository).findOne(documentId);
        order.verifyNoMoreInteractions();
    }
}
