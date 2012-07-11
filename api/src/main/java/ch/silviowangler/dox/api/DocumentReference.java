package ch.silviowangler.dox.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public class DocumentReference implements Serializable {

    private String hash;
    private Long id;
    private int pageCount;
    private String mimeType;
    private DocumentClass documentClass;
    private Map<String, Object> indexes = new HashMap<String, Object>();
    private String fileName;

    public DocumentReference(String fileName) {
        this.fileName = fileName;
    }

    public DocumentReference(String hash, int pageCount, String mimeType, DocumentClass documentClass, Map<String, Object> indexes, String fileName) {
        this(hash, null, pageCount, mimeType, documentClass, indexes, fileName);
    }

    public DocumentReference(String hash, Long id, int pageCount, String mimeType, DocumentClass documentClass, Map<String, Object> indexes, String fileName) {
        this.hash = hash;
        this.id = id;
        this.pageCount = pageCount;
        this.mimeType = mimeType;
        this.documentClass = documentClass;
        this.indexes = indexes;
        this.fileName = fileName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public DocumentClass getDocumentClass() {
        return documentClass;
    }

    public void setDocumentClass(DocumentClass documentClass) {
        this.documentClass = documentClass;
    }

    public Map<String, Object> getIndexes() {
        return indexes;
    }

    public void setIndexes(Map<String, Object> indexes) {
        this.indexes = indexes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "DocumentReference{" +
                "hash='" + hash + '\'' +
                ", id=" + id +
                ", pageCount=" + pageCount +
                ", mimeType='" + mimeType + '\'' +
                ", documentClass=" + documentClass +
                ", indexes=" + indexes +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
