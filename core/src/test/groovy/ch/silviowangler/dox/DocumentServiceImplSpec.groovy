/*
 * Copyright 2012 - 2017 Silvio Wangler (silvio.wangler@gmail.com)
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
package ch.silviowangler.dox

import ch.silviowangler.dox.api.DocumentService
import ch.silviowangler.dox.domain.Document
import ch.silviowangler.dox.domain.IndexStore
import ch.silviowangler.dox.repository.DocumentRepository
import ch.silviowangler.dox.repository.IndexStoreRepository
import com.google.common.collect.Sets
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import spock.lang.Specification

/**
 * @author Silvio Wangler
 * @since 0.2
 */
class DocumentServiceImplSpec extends Specification {


    DocumentService documentService = new DocumentServiceImpl()

    def cleanup() throws Exception {
        SecurityContextHolder.clearContext()
    }

    void "Count document references"() {

        given:
        def documentRepository = Mock(DocumentRepository)
        documentService.documentRepository = documentRepository

        when:
        Long count = documentService.retrieveDocumentReferenceCount()

        then:
        count == 99L

        and:
        1 * documentRepository.count() >> 99L
    }


    void "Null values are inacceptable"() {
        when:
        documentService.deleteDocument(null)

        then:
        thrown(IllegalArgumentException)
    }

    void "Delete document"() {

        given:
        def authentication = Mock(Authentication)
        SecurityContextHolder.getContext().setAuthentication(authentication)

        and:
        def documentRepository = Mock(DocumentRepository)
        def indexStoreRepository = Mock(IndexStoreRepository)
        documentService.documentRepository = documentRepository
        documentService.indexStoreRepository = indexStoreRepository

        Document document = new Document()
        document.setIndexStore(new IndexStore())
        document.setUserReference("saw303")

        when:
        documentService.deleteDocument(77L)

        then:
        1 * authentication.getPrincipal() >> new User("saw303", "", Sets.newHashSet())
        1 * documentRepository.findOne(77L) >> document
        1 * indexStoreRepository.delete(_)
        1 * documentRepository.delete(77L)
    }

    void "Deleting a document that does not belong to the current user get denied"() {

        given: 'a user '
        def authentication = Mock(Authentication)
        SecurityContextHolder.getContext().setAuthentication(authentication)

        and:
        def documentRepository = Mock(DocumentRepository)
        documentService.documentRepository = documentRepository

        and:
        Document document = new Document()
        document.setIndexStore(new IndexStore())
        document.setUserReference("saw303")

        final long documentId = 1L

        when:
        documentService.deleteDocument(documentId)

        then:
        thrown(AccessDeniedException)

        and:
        1 * authentication.getPrincipal() >> new User("guschti", "", Sets.newHashSet())
        1 * documentRepository.findOne(documentId) >> document
    }

    void "delete document that does not exist"() {

        given:
        final long documentId = 1L

        and:
        def documentRepository = Mock(DocumentRepository)
        documentService.documentRepository = documentRepository

        when:
        documentService.deleteDocument(documentId)

        then:
        1 * documentRepository.findOne(documentId) >> null
    }
}
