package ch.silviowangler.dox;

import ch.silviowangler.dox.api.*;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.*;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public class DocumentServiceTest extends AbstractTest {

    private DocumentClass documentClass;

    @Before
    public void init() {
        this.documentClass = new DocumentClass("INVOICE");
    }

    @Test(expected = DocumentNotFoundException.class)
    public void findUnknownDocument() throws DocumentNotFoundException {
        documentService.findDocumentReference(-9999L);
    }

    @Test
    public void importSinglePagePdf() throws IOException, ValdiationException, DocumentNotFoundException, DocumentDuplicationException {

        File singlePagePdf = loadFile("document-1p.pdf");

        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", new Date());
        indexes.put("invoiceAmount", new BigDecimal("50.00"));

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        DocumentReference documentReference = documentService.importDocument(doc);

        assertEquals(1, documentReference.getPageCount());
        assertEquals("f86eb38a1dd386823ac2d7ff83945b4f79ba11c4138ff6657e7827aa9306bd9c", HashGenerator.sha256Hex(singlePagePdf));
        assertEquals("application/pdf", documentReference.getMimeType());
        assertNotNull(documentReference.getId());
        assertEquals("INVOICE", documentReference.getDocumentClass().getShortName());
        assertEquals("document-1p.pdf", documentReference.getFileName());
        assertNotNull(documentReference.getIndexes());
        assertEquals(3, documentReference.getIndexes().size());
        assertTrue(documentReference.getIndexes().containsKey("company"));
        assertEquals("Sunrise", documentReference.getIndexes().get("company"));
        assertTrue(documentReference.getIndexes().containsKey("invoiceDate"));
        assertTrue("Is not org.joda.time.DateTime. It's " + documentReference.getIndexes().get("invoiceDate").getClass().getCanonicalName(), documentReference.getIndexes().get("invoiceDate") instanceof DateTime);
        assertTrue(documentReference.getIndexes().containsKey("invoiceAmount"));
        assertEquals(new BigDecimal("50.00"), documentReference.getIndexes().get("invoiceAmount"));

        DocumentReference documentReferenceFromDatabase = documentService.findDocumentReference(documentReference.getId());

        assertDocumentReference(documentReference, documentReferenceFromDatabase);
    }

    @Test
    public void importFivePagesPdf() throws IOException, ValdiationException, DocumentNotFoundException, DocumentNotInStoreException, DocumentDuplicationException {

        File fivePagesPdfFile = loadFile("document-5p.pdf");

        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Swisscom");
        indexes.put("invoiceDate", new Date());
        indexes.put("invoiceAmount", "50.25");

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(fivePagesPdfFile), indexes, fivePagesPdfFile.getName());
        DocumentReference documentReference = documentService.importDocument(doc);

        assertEquals(5, documentReference.getPageCount());
        assertEquals("6e7c9a9c8708567230c3e97f27420fe48d296ff6b98e4cb34ac9e36d2474feaa", HashGenerator.sha256Hex(fivePagesPdfFile));
        assertEquals("application/pdf", documentReference.getMimeType());
        assertNotNull(documentReference.getId());
        assertEquals("INVOICE", documentReference.getDocumentClass().getShortName());
        assertEquals("document-5p.pdf", documentReference.getFileName());
        assertNotNull(documentReference.getIndexes());
        assertEquals(3, documentReference.getIndexes().size());
        assertTrue(documentReference.getIndexes().containsKey("company"));
        assertEquals("Swisscom", documentReference.getIndexes().get("company"));
        assertTrue(documentReference.getIndexes().containsKey("invoiceDate"));
        assertTrue("Is not org.joda.time.DateTime. It's " + documentReference.getIndexes().get("invoiceDate").getClass().getCanonicalName(), documentReference.getIndexes().get("invoiceDate") instanceof DateTime);
        assertTrue(documentReference.getIndexes().containsKey("invoiceAmount"));
        assertEquals(new BigDecimal("50.25"), documentReference.getIndexes().get("invoiceAmount"));

        PhysicalDocument docFromDox = documentService.findPhysicalDocument(documentReference.getId());

        assertDocumentReference(documentReference, docFromDox);
    }

    @Test(expected = ValdiationException.class)
    public void importSinglePagePdfUsingAnUnknownDocumentClass() throws IOException, ValdiationException, DocumentDuplicationException {

        File singlePagePdf = loadFile("document-1p.pdf");

        documentClass = new DocumentClass("WHATEVAMAN");
        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", new Date());

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        documentService.importDocument(doc);
    }

    @Test(expected = ValdiationException.class)
    public void importSinglePagePdfMissingAnIndexKeyThatIsMandatory() throws IOException, ValdiationException, DocumentDuplicationException {

        File singlePagePdf = loadFile("document-1p.pdf");

        Map<String, Object> indexes = new HashMap<String, Object>(1);

        indexes.put("company", "Sunrise");

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        documentService.importDocument(doc);
    }

    @Test(expected = ValdiationException.class)
    public void importSinglePagePdfUsingAnIndexKeyThatDoesNotExist() throws IOException, ValdiationException, DocumentDuplicationException {

        File singlePagePdf = loadFile("document-1p.pdf");

        Map<String, Object> indexes = new HashMap<String, Object>(3);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", new Date());
        indexes.put("whatever", 12L);

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        documentService.importDocument(doc);
    }

    @Test
    public void importDocumentUsingProperFormattedStringOnDateIndex() throws IOException, ValdiationException, DocumentNotFoundException, DocumentDuplicationException {
        File singlePagePdf = loadFile("document-16p.pdf");

        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", "01.11.1978");
        indexes.put("invoiceAmount", 777.0);

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        DocumentReference documentReference = documentService.importDocument(doc);

        assertNotNull(documentReference.getId());

        DocumentReference documentReferenceFromStore = documentService.findDocumentReference(documentReference.getId());

        assertTrue(documentReferenceFromStore.getIndexes().containsKey("invoiceDate"));
        assertTrue(documentReferenceFromStore.getIndexes().get("invoiceDate") instanceof DateTime);
        assertEquals(new DateTime(1978, 11, 1, 0, 0), documentReferenceFromStore.getIndexes().get("invoiceDate"));
    }

    @Test
    public void addingTheSameDocumentTwiceToDoxShouldThrowAnException() throws IOException, ValdiationException, DocumentDuplicationException {
        File temp = createTestFile("hello.world.txt", "Lorem ipsum");

        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", "01.11.1978");
        indexes.put("invoiceAmount", 51.0);

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(temp), indexes, temp.getName());
        DocumentReference documentReference = documentService.importDocument(doc);

        assertEquals("text/plain", documentReference.getMimeType());
        assertEquals(-1, documentReference.getPageCount());

        try {
            documentService.importDocument(doc);
        } catch (DocumentDuplicationException e) {
            assertEquals(documentReference.getId(), e.getDocumentId());
            assertEquals(documentReference.getHash(), e.getHash());
        }
    }

    @Test
    public void importSinglePageTiff() throws IOException, ValdiationException, DocumentNotFoundException, DocumentDuplicationException {
        importTiff("document-1p.tif", 1);
    }

    @Test
    public void importTwentyPageTiff() throws IOException, ValdiationException, DocumentNotFoundException, DocumentDuplicationException {
        importTiff("document-20p.tif", 20);
    }

    private void importTiff(String fileName, int expectedPageCount) throws IOException, ValdiationException, DocumentDuplicationException, DocumentNotFoundException {
        File singlePagePdf = loadFile(fileName);

        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", "01.11.2012");
        indexes.put("invoiceAmount", "2000");

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        DocumentReference documentReference = documentService.importDocument(doc);

        assertNotNull(documentReference.getId());

        DocumentReference documentReferenceFromStore = documentService.findDocumentReference(documentReference.getId());

        assertTrue(documentReferenceFromStore.getIndexes().containsKey("invoiceDate"));
        assertTrue(documentReferenceFromStore.getIndexes().get("invoiceDate") instanceof DateTime);
        assertEquals(new DateTime(2012, 11, 1, 0, 0), documentReferenceFromStore.getIndexes().get("invoiceDate"));
        assertEquals("image/tiff", documentReferenceFromStore.getMimeType());
        assertEquals(expectedPageCount, documentReferenceFromStore.getPageCount());
    }

    private void assertDocumentReference(DocumentReference expectedInstance, DocumentReference actualInstance) {
        assertNotNull(actualInstance);
        assertEquals(expectedInstance.getPageCount(), actualInstance.getPageCount());
        assertEquals(expectedInstance.getHash(), actualInstance.getHash());
        assertEquals(expectedInstance.getMimeType(), actualInstance.getMimeType());
        assertEquals(expectedInstance.getId(), actualInstance.getId());
        assertEquals(expectedInstance.getDocumentClass(), actualInstance.getDocumentClass());
        assertEquals(expectedInstance.getFileName(), actualInstance.getFileName());
        assertEquals(expectedInstance.getIndexes().size(), actualInstance.getIndexes().size());
        assertEquals(expectedInstance.getIndexes(), actualInstance.getIndexes());
    }
}
