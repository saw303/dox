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
 * @author Silvio Wangler
 * @version 0.1
 */
public interface DocumentService {

    DocumentReference importDocument(PhysicalDocument physicalDocument) throws ValidationException, DocumentDuplicationException;

    DocumentReference findDocumentReference(Long id) throws DocumentNotFoundException;

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
     * @return
     */
    SortedSet<Attribute> findAttributes(DocumentClass documentClass);
}
