package ch.silviowangler.dox.document;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Silvio Wangler
 * @since 0.3.2
 */
@Component
public class DummyDocumentInspector implements DocumentInspector {

    @Override
    public int retrievePageCount(File file) {
        return -1;
    }
}
