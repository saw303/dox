package ch.silviowangler.dox.api;

import java.util.Map;
import java.util.Set;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public interface DocumentService {

    DocumentReference importDocument(PhysicalDocument physicalDocument) throws ValdiationException, DocumentDuplicationException;

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
}
