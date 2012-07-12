package ch.silviowangler.dox;

import ch.silviowangler.dox.api.*;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.*;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 11.07.12 12:28
 *        </div>
 */
public class DocumentServiceResearchTest extends AbstractTest {

    @Before
    public void init() throws ValdiationException, DocumentDuplicationException, IOException, DocumentNotFoundException {

        Map<String, Object> indexes = new HashMap<String, Object>(2);
        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", "01.12.2009");

        importFile("file-1.txt", "This is a test content", "INVOICE", indexes);

        indexes = new HashMap<String, Object>(2);
        indexes.put("company", "Swisscom");
        indexes.put("invoiceDate", "02.12.2009");

        importFile("file-2.txt", "This is a test content that contains more text", "INVOICE", indexes);
    }

    private DocumentReference importFile(final String fileName, final String content, final String docClassShortName, final Map<String, Object> indices) throws ValdiationException, DocumentDuplicationException, IOException, DocumentNotFoundException {
        File textFile01 = createTestFile(fileName, content);
        PhysicalDocument doc = new PhysicalDocument(new DocumentClass(docClassShortName), FileUtils.readFileToByteArray(textFile01), indices, fileName);
        try {
            return documentService.importDocument(doc);

        } catch (DocumentDuplicationException e) {
            return documentService.findDocumentReference(e.getDocumentId());
        }
    }

    @Test
    public void findSwisscomInvoice() throws DocumentClassNotFoundException {

        Map<String, Object> queryParams = new HashMap<String, Object>(1);
        final String companyName = "Swisscom";
        queryParams.put("company", companyName);

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals(companyName, documentReferences.iterator().next().getIndexes().get("company"));
    }

    @Test
    public void findSunriseInvoice() throws DocumentClassNotFoundException {

        Map<String, Object> queryParams = new HashMap<String, Object>(1);
        final String companyName = "Sunrise";
        queryParams.put("company", companyName);

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals(companyName, documentReferences.iterator().next().getIndexes().get("company"));
    }

    @Test
    public void findInvoicesByCompaniesStartingWithS() throws DocumentClassNotFoundException {

        Map<String, Object> queryParams = new HashMap<String, Object>(1);
        final String companyName = "S*";
        queryParams.put("company", companyName);

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(2, documentReferences.size());
        assertTrue(((String) documentReferences.iterator().next().getIndexes().get("company")).matches("(Swisscom|Sunrise)"));
    }

    @Test
    public void findInvoicesByCompaniesSunXise() throws DocumentClassNotFoundException {

        Map<String, Object> queryParams = new HashMap<String, Object>(1);
        final String companyName = "Sun?ise";
        queryParams.put("company", companyName);

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals("Sunrise", documentReferences.iterator().next().getIndexes().get("company"));
    }
}
