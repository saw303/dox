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

package ch.silviowangler.dox.api;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * This is the general service when it comes to working with documents in DOX.
 * <p/>
 * DOX knows the following types of documents:
 * <ul>
 * <li><code>Document reference</code> - a metadata representation of a document (everything but the binary content)</li>
 * <li><code>Physical document</code> - is the document reference plus its binary content</li>
 * </ul>
 *
 * @author Silvio Wangler
 * @since 0.1
 */
public interface DocumentService {

    /**
     * Imports a document inclusive its binary content into DOX.
     *
     * @param physicalDocument the document to create in DOX.
     * @return The document reference that was created in DOX
     * @throws ValidationException
     * @throws DocumentDuplicationException
     */
    DocumentReference importDocument(PhysicalDocument physicalDocument) throws ValidationException, DocumentDuplicationException;

    /**
     * Find a document reference using the unique identifier (ID)
     *
     * @param id unique identifier
     * @return the document reference with all its metadata
     * @throws DocumentNotFoundException if the document does not exist
     */
    DocumentReference findDocumentReference(Long id) throws DocumentNotFoundException;

    /**
     * Works like finding a document reference but returns also the binary content of a document.
     *
     * @param id
     * @return returns the physical document
     * @throws DocumentNotFoundException   if there is no document for that ID
     * @throws DocumentNotInStoreException if there is a document in the database registered but the binary content cannot be found in the underlying data store that DOX uses.
     * @see DocumentService#findDocumentReference(Long)
     */
    PhysicalDocument findPhysicalDocument(Long id) throws DocumentNotFoundException, DocumentNotInStoreException;

    Set<DocumentReference> findDocumentReferences(Map<String, Object> queryParams, String documentClassShortName) throws DocumentClassNotFoundException;

    /**
     * Implement a search à la Google. Uses a single String to find document references.
     *
     * @param queryString a simple query string (e.g. a keyword 'taxes' or a date '01.12.2012')
     * @return all documents that are holding this keyword as a part of an index
     */
    Set<DocumentReference> findDocumentReferences(String queryString);

    Set<DocumentClass> findDocumentClasses();

    /**
     * Finds all attributes assigned to the given document class. It returns the global and the document class specific attributes.
     *
     * @param documentClass
     * @return all attributes to the given document class
     */
    SortedSet<Attribute> findAttributes(DocumentClass documentClass);
}
