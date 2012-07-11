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

    Set<DocumentReference> findDocumentReferences(Map<String, Object> queryParams);
}
