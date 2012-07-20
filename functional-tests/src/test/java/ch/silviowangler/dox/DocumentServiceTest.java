/*
 * Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox;

import ch.silviowangler.dox.api.*;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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

    @Test
    public void findAttributesForDocumentClassInvoice() {
        SortedSet<Attribute> attributes = documentService.findAttributes(this.documentClass);

        assertNotNull(attributes);
        assertEquals(3, attributes.size());

        final Iterator<Attribute> iterator = (attributes).iterator();
        assertEquals("Wrong order", "company", iterator.next().getShortName());
        assertEquals("Wrong order", "invoiceAmount", iterator.next().getShortName());
        assertEquals("Wrong order", "invoiceDate", iterator.next().getShortName());
    }

    @Test
    public void findDocumentClasses() {
        Set<DocumentClass> documentClasses = documentService.findDocumentClasses();

        assertNotNull(documentClasses);
        assertEquals(1, documentClasses.size());
        assertEquals("INVOICE", documentClasses.iterator().next().getShortName());
    }

    @Test(expected = DocumentNotFoundException.class)
    public void findUnknownDocument() throws DocumentNotFoundException {
        documentService.findDocumentReference(-9999L);
    }

    @Test
    public void importSinglePagePdf() throws IOException, ValidationException, DocumentNotFoundException, DocumentDuplicationException {

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
        assertNotNull(documentReference.getIndices());
        assertEquals(3, documentReference.getIndices().size());
        assertTrue(documentReference.getIndices().containsKey("company"));
        assertEquals("Sunrise", documentReference.getIndices().get("company"));
        assertTrue(documentReference.getIndices().containsKey("invoiceDate"));
        assertTrue("Is not org.joda.time.DateTime. It's " + documentReference.getIndices().get("invoiceDate").getClass().getCanonicalName(), documentReference.getIndices().get("invoiceDate") instanceof DateTime);
        assertTrue(documentReference.getIndices().containsKey("invoiceAmount"));
        assertEquals(new BigDecimal("50.00"), documentReference.getIndices().get("invoiceAmount"));

        DocumentReference documentReferenceFromDatabase = documentService.findDocumentReference(documentReference.getId());

        assertDocumentReference(documentReference, documentReferenceFromDatabase);
    }

    @Test
    public void importFivePagesPdf() throws IOException, ValidationException, DocumentNotFoundException, DocumentNotInStoreException, DocumentDuplicationException {

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
        assertNotNull(documentReference.getIndices());
        assertEquals(3, documentReference.getIndices().size());
        assertTrue(documentReference.getIndices().containsKey("company"));
        assertEquals("Swisscom", documentReference.getIndices().get("company"));
        assertTrue(documentReference.getIndices().containsKey("invoiceDate"));
        assertTrue("Is not org.joda.time.DateTime. It's " + documentReference.getIndices().get("invoiceDate").getClass().getCanonicalName(), documentReference.getIndices().get("invoiceDate") instanceof DateTime);
        assertTrue(documentReference.getIndices().containsKey("invoiceAmount"));
        assertEquals(new BigDecimal("50.25"), documentReference.getIndices().get("invoiceAmount"));

        PhysicalDocument docFromDox = documentService.findPhysicalDocument(documentReference.getId());

        assertDocumentReference(documentReference, docFromDox);
    }

    @Test(expected = ValidationException.class)
    public void importSinglePagePdfUsingAnUnknownDocumentClass() throws IOException, ValidationException, DocumentDuplicationException {

        File singlePagePdf = loadFile("document-1p.pdf");

        documentClass = new DocumentClass("WHATEVAMAN");
        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", new Date());

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        documentService.importDocument(doc);
    }

    @Test(expected = ValidationException.class)
    public void importSinglePagePdfMissingAnIndexKeyThatIsMandatory() throws IOException, ValidationException, DocumentDuplicationException {

        File singlePagePdf = loadFile("document-1p.pdf");

        Map<String, Object> indexes = new HashMap<String, Object>(1);

        indexes.put("company", "Sunrise");

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        documentService.importDocument(doc);
    }

    @Test(expected = ValidationException.class)
    public void importSinglePagePdfUsingAnIndexKeyThatDoesNotExist() throws IOException, ValidationException, DocumentDuplicationException {

        File singlePagePdf = loadFile("document-1p.pdf");

        Map<String, Object> indexes = new HashMap<String, Object>(3);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", new Date());
        indexes.put("whatever", 12L);

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        documentService.importDocument(doc);
    }

    @Test
    public void importDocumentUsingProperFormattedStringOnDateIndex() throws IOException, ValidationException, DocumentNotFoundException, DocumentDuplicationException {
        File singlePagePdf = loadFile("document-16p.pdf");

        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", "01.11.1978");
        indexes.put("invoiceAmount", 777.0);

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        DocumentReference documentReference = documentService.importDocument(doc);

        assertNotNull(documentReference.getId());

        DocumentReference documentReferenceFromStore = documentService.findDocumentReference(documentReference.getId());

        assertTrue(documentReferenceFromStore.getIndices().containsKey("invoiceDate"));
        assertTrue(documentReferenceFromStore.getIndices().get("invoiceDate") instanceof DateTime);
        assertEquals(new DateTime(1978, 11, 1, 0, 0), documentReferenceFromStore.getIndices().get("invoiceDate"));
    }

    @Test
    public void addingTheSameDocumentTwiceToDoxShouldThrowAnException() throws IOException, ValidationException, DocumentDuplicationException {
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
    public void importSinglePageTiff() throws IOException, ValidationException, DocumentNotFoundException, DocumentDuplicationException {
        importTiff("document-1p.tif", 1);
    }

    @Test
    public void importTwentyPageTiff() throws IOException, ValidationException, DocumentNotFoundException, DocumentDuplicationException {
        importTiff("document-20p.tif", 20);
    }

    private void importTiff(String fileName, int expectedPageCount) throws IOException, ValidationException, DocumentDuplicationException, DocumentNotFoundException {
        File singlePagePdf = loadFile(fileName);

        Map<String, Object> indexes = new HashMap<String, Object>(2);

        indexes.put("company", "Sunrise");
        indexes.put("invoiceDate", "01.11.2012");
        indexes.put("invoiceAmount", "2000");

        PhysicalDocument doc = new PhysicalDocument(documentClass, FileUtils.readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        DocumentReference documentReference = documentService.importDocument(doc);

        assertNotNull(documentReference.getId());

        DocumentReference documentReferenceFromStore = documentService.findDocumentReference(documentReference.getId());

        assertTrue(documentReferenceFromStore.getIndices().containsKey("invoiceDate"));
        assertTrue(documentReferenceFromStore.getIndices().get("invoiceDate") instanceof DateTime);
        assertEquals(new DateTime(2012, 11, 1, 0, 0), documentReferenceFromStore.getIndices().get("invoiceDate"));
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
        assertEquals(expectedInstance.getIndices().size(), actualInstance.getIndices().size());
        assertEquals(expectedInstance.getIndices(), actualInstance.getIndices());
    }
}
