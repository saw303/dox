package ch.silviowangler.dox.jobs;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public abstract class MissingDocument implements Serializable {
    private String hash;

    protected MissingDocument(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("hash", hash)
                .toString();
    }
}
