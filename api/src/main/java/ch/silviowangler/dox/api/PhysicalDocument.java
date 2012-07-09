package ch.silviowangler.dox.api;

import java.util.Map;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public class PhysicalDocument extends DocumentReference {

    private byte[] content;

    public PhysicalDocument() {
        this(null, null, null, null);
    }

    /**
     * Use this constructor in order to import a document
     *
     * @param documentClass
     * @param content
     * @param indexes
     */
    public PhysicalDocument(DocumentClass documentClass, byte[] content, Map<String, Object> indexes, String fileName) {
        this(null, -1, null, documentClass, content, indexes, fileName);
    }

    public PhysicalDocument(String hash, int pageCount, String mimeType, DocumentClass documentClass, byte[] content, Map<String, Object> indexes, String fileName) {
        this(hash, null, pageCount, mimeType, documentClass, content, indexes, fileName);
    }

    public PhysicalDocument(String hash, Long id, int pageCount, String mimeType, DocumentClass documentClass, byte[] content, Map<String, Object> indexes, String fileName) {
        super(hash, id, pageCount, mimeType, documentClass, indexes, fileName);
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
