package ch.silviowangler.dox.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class DoxDocumentFileFilter implements FileFilter {

    private Logger logger = LoggerFactory.getLogger(DoxDocumentFileFilter.class);

    public boolean accept(File file) {
        final boolean result = file.getName().length() == 64;
        logger.trace("Accepting file {}? {}", file.getAbsolutePath(), result);
        return result;
    }
}
