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

package ch.silviowangler.dox.api;

import java.util.*;

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
     * @throws DocumentClassNotFoundException if the document class cannot be found
     */
    DocumentReference importDocument(PhysicalDocument physicalDocument) throws ValidationException, DocumentDuplicationException, DocumentClassNotFoundException;

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
     * @param id id of the physical document to retrieve
     * @return returns the physical document
     * @throws DocumentNotFoundException   if there is no document for that ID
     * @throws DocumentNotInStoreException if there is a document in the database registered but the binary content cannot be found in the underlying data store that DOX uses.
     * @see DocumentService#findDocumentReference(Long)
     */
    PhysicalDocument findPhysicalDocument(Long id) throws DocumentNotFoundException, DocumentNotInStoreException;

    Set<DocumentReference> findDocumentReferences(Map<TranslatableKey, Object> queryParams, String documentClassShortName) throws DocumentClassNotFoundException;

    /**
     * Implement a search à la Google. Uses a single String to find document references.
     *
     * @param queryString a simple query string (e.g. a keyword 'taxes' or a date '01.12.2012')
     * @return all documents that are holding this keyword as a part of an index
     */
    List<DocumentReference> findDocumentReferences(String queryString);

    /**
     * Finds document references and returns the indices map keys translated to the given locale and the map values formatted using the given locale
     * @param queryString query params
     * @param locale language / region setting to format/translate map key and value pairs
     * @return see description above
     * @since 0.3
     */
    List<DocumentReference> findDocumentReferences(String queryString, Locale locale);

    /**
     * Retrieves for the current user only
     *
     * @param queryString query params
     * @return all document references that match with the query
     * @since 0.3
     */
    List<DocumentReference> findDocumentReferencesForCurrentUser(String queryString);

    /**
     * Retrieves for the current user only
     *
     * @param queryString query params
     * @param locale language / region setting to format/translate map key and value pairs
     * @return all document references that match with the query
     * @since 0.3
     */
    List<DocumentReference> findDocumentReferencesForCurrentUser(String queryString, Locale locale);

    Set<DocumentClass> findDocumentClasses();

    /**
     * Finds all attributes assigned to the given document class. It returns the global and the document class specific attributes.
     *
     * @param documentClass document class to retrieve the attributes from
     * @return all attributes to the given document class
     * @throws DocumentClassNotFoundException if the document class does not exist
     */
    SortedSet<Attribute> findAttributes(DocumentClass documentClass) throws DocumentClassNotFoundException;

    /**
     * Updates the index values of an existing document reference
     *
     * @param reference the document reference containing the new index values
     * @return returns the up to date document reference
     * @throws DocumentNotFoundException
     */
    DocumentReference updateIndices(DocumentReference reference) throws DocumentNotFoundException;

    /**
     * Retrieves all document references
     *
     * @return all document references within the DOX repository
     */
    Set<DocumentReference> retrieveAllDocumentReferences();

    /**
     * Retrieves the current amount of document references managed by DOX
     *
     * @return the amount of document references
     */
    long retrieveDocumentReferenceCount();

    /**
     * Deletes a document
     *
     * @param id the ID of the document you wish to delete
     * @throws IllegalArgumentException if id is null
     */
    void deleteDocument(Long id);

    /**
     * Retrieves all document classes
     * @return all document classes
     */
    List<DocumentClass> findAllDocumentClasses();
}
