package ch.silviowangler.dox.document;

import com.itextpdf.text.pdf.PdfReader;
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
public class PdfDocumentInspector implements DocumentInspector {

    private static final Logger logger  = LoggerFactory.getLogger(PdfDocumentInspector.class);

    public int retrievePageCount(File file) {
        PdfReader pdfReader;
        try {
            pdfReader = new PdfReader(new FileInputStream(file));
            int numberOfPages = pdfReader.getNumberOfPages();
            logger.trace("Found {} pages in PDF in {}", file.getAbsolutePath(), numberOfPages);
            return numberOfPages;
        } catch (IOException e) {
            logger.error("Unable to determine the number of pages of file ", file.getAbsolutePath(), e);
            return -1;
        }
    }
}
