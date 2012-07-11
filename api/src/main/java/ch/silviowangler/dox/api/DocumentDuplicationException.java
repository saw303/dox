package ch.silviowangler.dox.api;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 11.07.12 10:57
 *        </div>
 */
public class DocumentDuplicationException extends Exception {

    private Long documentId;
    private String hash;

    public DocumentDuplicationException(Long documentId, String hash) {
        super("Document hash '" + hash + "' does already exist for document with id " + documentId);
        this.documentId = documentId;
        this.hash = hash;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public String getHash() {
        return hash;
    }
}
