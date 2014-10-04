package ch.silviowangler.dox.document;

import com.itextpdf.text.io.RandomAccessSource;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Silvio Wangler
 * @since 0.3.2
 */
@Component
public class TiffDocumentInspector implements DocumentInspector {

    private static final Logger logger = LoggerFactory.getLogger(TiffDocumentInspector.class);

    public int retrievePageCount(File file) {
        try {
            RandomAccessSource source = new RandomAccessSourceFactory().createSource(new FileInputStream(file));
            int numberOfPages = TiffImage.getNumberOfPages(new RandomAccessFileOrArray(source));
            logger.trace("Found {} pages in TIFF in {}", numberOfPages, file.getAbsolutePath());
            return numberOfPages;
        } catch (IOException e) {
            logger.error("Unable to retrieve number of pages of {}", file.getAbsolutePath(), e);
            return -1;
        }
    }
}
