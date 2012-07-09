package ch.silviowangler.dox.api;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public interface DocumentService {

    DocumentReference importDocument(PhysicalDocument physicalDocument) throws ValdiationException;

    DocumentReference findDocumentReference(Long id) throws DocumentNotFoundException;

    PhysicalDocument findPhysicalDocument(Long id) throws DocumentNotFoundException, DocumentNotInStoreException;
}
