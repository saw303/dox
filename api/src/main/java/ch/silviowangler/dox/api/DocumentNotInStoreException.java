package ch.silviowangler.dox.api;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class DocumentNotInStoreException extends Exception {

    public DocumentNotInStoreException(String hash, Long id) {
        super("Document with hash '" + hash + "' and document reference id '" + id + "' could not be found");
    }
}
