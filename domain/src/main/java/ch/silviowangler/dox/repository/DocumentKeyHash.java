package ch.silviowangler.dox.repository;

import com.google.common.base.MoreObjects;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class DocumentKeyHash {

    private Long id;
    private String hash;

    public DocumentKeyHash(Long id, String hash) {
        this.id = id;
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentKeyHash)) return false;

        DocumentKeyHash that = (DocumentKeyHash) o;

        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("hash", hash)
                .toString();
    }
}
