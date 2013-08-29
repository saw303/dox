package ch.silviowangler.dox.jobs;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class MissingDocument implements Serializable {
    private String hash;
    private Source source;

    public MissingDocument(String hash, Source source) {
        this.hash = hash;
        this.source = source;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("hash", hash)
                .add("source", source)
                .toString();
    }
}

enum Source {
    DATABASE, STORE;
}
