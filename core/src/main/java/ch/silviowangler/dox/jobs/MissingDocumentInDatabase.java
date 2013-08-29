package ch.silviowangler.dox.jobs;

import com.google.common.base.Objects;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class MissingDocumentInDatabase extends MissingDocument {

    private Long documentId;

    public MissingDocumentInDatabase(String hash, Long documentId) {
        super(hash);
        this.documentId = documentId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("documentId", documentId)
                .add("hash", getHash())
                .toString();
    }
}
