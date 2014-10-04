package ch.silviowangler.dox.document;

import java.io.File;

/**
 * @author Silvio Wangler
 * @since 0.3.2
 */
public interface DocumentInspector {

    /**
     * Retrieves the number of pages from an input stream
     *
     * @param file document to analyze
     * @return number of pages
     */
    public int retrievePageCount(File file);

}
