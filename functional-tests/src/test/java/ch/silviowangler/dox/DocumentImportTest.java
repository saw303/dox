package ch.silviowangler.dox;

import ch.silviowangler.dox.api.*;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public class DocumentImportTest extends AbstractTest {

    @Autowired
    private DocumentImportService documentImportService;


    @Test(expected = ValdiationException.class)
    public void importSinglePagePdfUsingAnUnknownDocumentClass() throws IOException, ValdiationException {

        File singlePagePdf = loadFile("document-1p.pdf");

        DocumentClass documentClass = new DocumentClass("WHATEVAMAN");
        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", new Date());

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        DocumentReference documentReference = documentImportService.importDocument(doc);
    }

    @Test
    public void importSinglePagePdf() throws IOException, ValdiationException {

        File singlePagePdf = loadFile("document-1p.pdf");

        DocumentClass documentClass = new DocumentClass("INVOICE");
        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", new Date());

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        DocumentReference documentReference = documentImportService.importDocument(doc);

        assertEquals(1, documentReference.getPageCount());
        assertEquals("f86eb38a1dd386823ac2d7ff83945b4f79ba11c4138ff6657e7827aa9306bd9c", HashGenerator.sha256Hex(singlePagePdf));
        assertEquals("application/pdf", documentReference.getMimeType());
        assertNotNull(documentReference.getId());
        assertEquals("INVOICE", documentReference.getDocumentClass().getShortName());
        assertEquals("document-1p.pdf", documentReference.getFileName());
        assertNotNull(documentReference.getIndexes());
        assertEquals(2, documentReference.getIndexes().size());
        assertTrue(documentReference.getIndexes().containsKey("company"));
        assertEquals("Sunrise", documentReference.getIndexes().get("company"));
        assertTrue(documentReference.getIndexes().containsKey("invoiceDate"));
        assertTrue(documentReference.getIndexes().get("invoiceDate") instanceof Date);
    }

    @Test
    public void importFivePagesPdf() throws IOException, ValdiationException {

        File fivePagesPdfFile = loadFile("document-5p.pdf");

        DocumentClass documentClass = new DocumentClass("INVOICE");
        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Swisscom");
        indexes.put("invoiceDate", new Date());

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(fivePagesPdfFile), indexes, fivePagesPdfFile.getName());
        DocumentReference documentReference = documentImportService.importDocument(doc);

        assertEquals(5, documentReference.getPageCount());
        assertEquals("6e7c9a9c8708567230c3e97f27420fe48d296ff6b98e4cb34ac9e36d2474feaa", HashGenerator.sha256Hex(fivePagesPdfFile));
        assertEquals("application/pdf", documentReference.getMimeType());
        assertNotNull(documentReference.getId());
        assertEquals("INVOICE", documentReference.getDocumentClass().getShortName());
        assertEquals("document-5p.pdf", documentReference.getFileName());
        assertNotNull(documentReference.getIndexes());
        assertEquals(2, documentReference.getIndexes().size());
        assertTrue(documentReference.getIndexes().containsKey("company"));
        assertEquals("Swisscom", documentReference.getIndexes().get("company"));
        assertTrue(documentReference.getIndexes().containsKey("invoiceDate"));
        assertTrue(documentReference.getIndexes().get("invoiceDate") instanceof Date);
    }
}
