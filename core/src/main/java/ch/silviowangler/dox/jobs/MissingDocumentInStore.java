package ch.silviowangler.dox.jobs;

import com.google.common.base.Objects;

import java.nio.file.Path;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class MissingDocumentInStore extends MissingDocument {

    private Path path;

    public MissingDocumentInStore(String hash, Path path) {
        super(hash);
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("path", path)
                .add("hash", getHash())
                .toString();
    }
}
