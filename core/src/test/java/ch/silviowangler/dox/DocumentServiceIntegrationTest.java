/**
 * Copyright 2012 - 2017 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.silviowangler.dox;

import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static com.google.common.collect.Sets.filter;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.common.base.Predicate;

import org.joda.time.LocalDate;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import ch.silviowangler.dox.api.Attribute;
import ch.silviowangler.dox.api.DescriptiveIndex;
import ch.silviowangler.dox.api.DocumentClass;
import ch.silviowangler.dox.api.DocumentClassNotFoundException;
import ch.silviowangler.dox.api.DocumentDuplicationException;
import ch.silviowangler.dox.api.DocumentNotFoundException;
import ch.silviowangler.dox.api.DocumentNotInStoreException;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.Domain;
import ch.silviowangler.dox.api.Money;
import ch.silviowangler.dox.api.PhysicalDocument;
import ch.silviowangler.dox.api.Translatable;
import ch.silviowangler.dox.api.TranslatableKey;
import ch.silviowangler.dox.api.ValidationException;
import ch.silviowangler.dox.api.ValueNotInDomainException;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class DocumentServiceIntegrationTest extends AbstractIntegrationTest {

    public static final TranslatableKey INVOICE_DATE = new TranslatableKey("invoiceDate");
    public static final TranslatableKey INVOICE_AMOUNT = new TranslatableKey("invoiceAmount");
    public static final TranslatableKey COMPANY = new TranslatableKey("company");
    public static final TranslatableKey MONEY = new TranslatableKey("money");
    public static final TranslatableKey STRICT_COMPANY = new TranslatableKey("strictcompany");
    public static final String CLIENT_WANGLER = "wangler";
    private DocumentClass documentClass;
    private final int INVOICE_AMOUNT_INDICES = 5;

    @Before
    public void init() {
        this.documentClass = new DocumentClass("INVOICE");
        this.documentClass.setClient(CLIENT_WANGLER);
        loginAsTestRoot();
    }

    @Test
    public void findAttributesForDocumentClassInvoice() throws DocumentClassNotFoundException {
        SortedSet<Attribute> attributes = documentService.findAttributes(this.documentClass);

        assertNotNull(attributes);
        assertEquals(INVOICE_AMOUNT_INDICES, attributes.size());

        final Iterator<Attribute> iterator = (attributes).iterator();
        assertEquals("Wrong order", "company", iterator.next().getShortName());
        assertEquals("Wrong order", "invoiceAmount", iterator.next().getShortName());
        assertEquals("Wrong order", "invoiceDate", iterator.next().getShortName());

        for (Attribute attribute : attributes) {
            if (attribute.containsDomain()) {
                Domain domain = attribute.getDomain();
                assertNotNull(domain.getShortName());
                assertThat(domain.getValues().size(), is(not(0)));
                assertNotNull("Translation is missing", domain.getTranslation());
            }
        }
    }

    @Test
    public void findDocumentClasses() {
        Set<DocumentClass> documentClasses = documentService.findDocumentClasses();

        assertNotNull(documentClasses);
        assertEquals(8, documentClasses.size());

        for (DocumentClass currentDocumentClass : documentClasses) {
            final String currentShortName = currentDocumentClass.getShortName();
            assertTrue("Unexpected document class " + currentShortName, currentShortName.matches("(INVOICE|TAXES|SALARY_REPORTS|CONTRACTS|BANK_DOCUMENTS|VARIA|DIPLOMA|DUMMY_DOC)"));
        }
    }

    @Test(expected = DocumentNotFoundException.class)
    public void findUnknownDocument() throws DocumentNotFoundException {
        documentService.findDocumentReference(-9999L);
    }

    @Test
    public void importSinglePagePdf() throws IOException, ValidationException, DocumentNotFoundException, DocumentDuplicationException, DocumentClassNotFoundException {

        File singlePagePdf = loadFile("document-1p.pdf");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(new BigDecimal("50.00")));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        doc.setClient(CLIENT_WANGLER);
        DocumentReference documentReference = documentService.importDocument(doc);

        assertEquals(1, documentReference.getPageCount());
        assertEquals("f86eb38a1dd386823ac2d7ff83945b4f79ba11c4138ff6657e7827aa9306bd9c", HashGenerator.sha256Hex(singlePagePdf));
        assertEquals("application/pdf", documentReference.getMimeType());
        assertNotNull(documentReference.getId());
        assertEquals("INVOICE", documentReference.getDocumentClass().getShortName());
        assertEquals("document-1p.pdf", documentReference.getFileName());
        assertNotNull(documentReference.getIndices());
        assertEquals(INVOICE_AMOUNT_INDICES, documentReference.getIndices().size());
        assertTrue(documentReference.getIndices().containsKey(COMPANY));
        assertEquals("Sunrise", documentReference.getIndices().get(COMPANY).getValue());
        assertTrue(documentReference.getIndices().containsKey(INVOICE_DATE));
        assertTrue("Is not org.joda.time.DateTime. It's " + documentReference.getIndices().get(INVOICE_DATE).getValue().getClass().getCanonicalName(), documentReference.getIndices().get(INVOICE_DATE).getValue() instanceof LocalDate);
        assertTrue(documentReference.getIndices().containsKey(INVOICE_AMOUNT));
        assertEquals(new BigDecimal("50.00"), documentReference.getIndices().get(INVOICE_AMOUNT).getValue());
        assertThat(documentReference.getUserReference(), is("root_test"));
        assertThat(documentReference.getFileSize(), is(100524L));

        DocumentReference documentReferenceFromDatabase = documentService.findDocumentReference(documentReference.getId());

        assertDocumentReference(documentReference, documentReferenceFromDatabase);
    }

    @Test
    public void importFivePagesPdf() throws IOException, ValidationException, DocumentNotFoundException, DocumentNotInStoreException, DocumentDuplicationException, DocumentClassNotFoundException {

        File fivePagesPdfFile = loadFile("document-5p.pdf");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        indexes.put(COMPANY, new DescriptiveIndex("Swisscom"));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex("50.25"));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(fivePagesPdfFile), indexes, fivePagesPdfFile.getName());
        doc.setClient(CLIENT_WANGLER);
        DocumentReference documentReference = documentService.importDocument(doc);

        assertEquals(5, documentReference.getPageCount());
        assertEquals("6e7c9a9c8708567230c3e97f27420fe48d296ff6b98e4cb34ac9e36d2474feaa", HashGenerator.sha256Hex(fivePagesPdfFile));
        assertEquals("application/pdf", documentReference.getMimeType());
        assertNotNull(documentReference.getId());
        assertEquals("INVOICE", documentReference.getDocumentClass().getShortName());
        assertEquals("document-5p.pdf", documentReference.getFileName());
        assertNotNull(documentReference.getIndices());
        assertEquals(INVOICE_AMOUNT_INDICES, documentReference.getIndices().size());
        assertTrue(documentReference.getIndices().containsKey(COMPANY));
        assertEquals("Swisscom", documentReference.getIndices().get(COMPANY).getValue());
        assertTrue(documentReference.getIndices().containsKey(INVOICE_DATE));
        assertTrue("Is not org.joda.time.DateTime. It's " + documentReference.getIndices().get(INVOICE_DATE).getValue().getClass().getCanonicalName(), documentReference.getIndices().get(INVOICE_DATE).getValue() instanceof LocalDate);
        assertTrue(documentReference.getIndices().containsKey(INVOICE_AMOUNT));
        assertEquals(new BigDecimal("50.25"), documentReference.getIndices().get(INVOICE_AMOUNT).getValue());

        PhysicalDocument docFromDox = documentService.findPhysicalDocument(documentReference.getId());

        assertNotNull("Must have a content", docFromDox.getContent());
        assertByteArrayEquals("Wrong content", readFileToByteArray(fivePagesPdfFile), doc.getContent());
        assertDocumentReference(documentReference, docFromDox);
    }

    @Test
    @Ignore
    public void importSinglePageWordDocument() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException, IOException {

        Assume.assumeFalse(isMacOsX());

        File singlePageWordDocument = loadFile("word-1-page.doc");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex("20.05"));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(singlePageWordDocument), indexes, singlePageWordDocument.getName());
        doc.setClient(CLIENT_WANGLER);
        DocumentReference documentReference = documentService.importDocument(doc);

        assertThat(documentReference.getPageCount(), is(1));
        assertThat(documentReference.getMimeType(), is("application/msword"));
    }

    @Test
    public void importSinglePageOpenWordDocument() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException, IOException {

        Assume.assumeFalse(isMacOsX());

        File singlePageWordDocument = loadFile("word-1-page.docx");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex("20.05"));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(singlePageWordDocument), indexes, singlePageWordDocument.getName());
        doc.setClient(CLIENT_WANGLER);
        DocumentReference documentReference = documentService.importDocument(doc);

        assertThat(documentReference.getPageCount(), is(1));
        assertThat(documentReference.getMimeType(), is("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
    }

    @Test(expected = DocumentClassNotFoundException.class)
    public void importSinglePagePdfUsingAnUnknownDocumentClass() throws IOException, ValidationException, DocumentDuplicationException, DocumentClassNotFoundException {

        File singlePagePdf = loadFile("document-1p.pdf");

        documentClass = new DocumentClass("WHATEVAMAN");
        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(2);

        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        doc.setClient(CLIENT_WANGLER);
        documentService.importDocument(doc);
    }

    @Test
    public void importCurrencyAmount() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException, IOException {

        File singlePagePdf = loadFile("document-1p.pdf");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(1);
        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(MONEY, new DescriptiveIndex(new Money(Currency.getInstance("CHF"), new BigDecimal("1235.50"))));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(12350L));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        doc.setClient(CLIENT_WANGLER);
        final DocumentReference documentReference = documentService.importDocument(doc);

        final Money money = (Money) documentReference.getIndices().get(MONEY).getValue();
        assertThat(money, is(not(nullValue())));
        assertThat(money.getCurrency().getCurrencyCode(), is("CHF"));
        assertThat(money.getAmount().toPlainString(), is("1235.50"));
    }

    @Test
    public void importCurrencyAmountAsText() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException, IOException {

        File singlePagePdf = loadFile("document-1p.pdf");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(1);
        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(MONEY, new DescriptiveIndex("CHF 1235.50"));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(12350L));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        doc.setClient(CLIENT_WANGLER);
        final DocumentReference documentReference = documentService.importDocument(doc);

        final Money money = (Money) documentReference.getIndices().get(MONEY).getValue();
        assertThat(money, is(not(nullValue())));
        assertThat(money.getCurrency().getCurrencyCode(), is("CHF"));
        assertThat(money.getAmount().toPlainString(), is("1235.50"));
    }

    @Test(expected = ValidationException.class)
    public void importSinglePagePdfMissingAnIndexKeyThatIsMandatory() throws IOException, ValidationException, DocumentDuplicationException, DocumentClassNotFoundException {

        File singlePagePdf = loadFile("document-1p.pdf");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(1);
        indexes.put(INVOICE_DATE, new DescriptiveIndex("Sunrise"));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        doc.setClient(CLIENT_WANGLER);
        documentService.importDocument(doc);
    }

    @Test(expected = ValidationException.class)
    public void importSinglePagePdfUsingAnIndexKeyThatDoesNotExist() throws IOException, ValidationException, DocumentDuplicationException, DocumentClassNotFoundException {

        File singlePagePdf = loadFile("document-1p.pdf");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));
        indexes.put(new TranslatableKey("whatever"), new DescriptiveIndex(12L));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        doc.setClient(CLIENT_WANGLER);
        documentService.importDocument(doc);
    }

    @Test
    public void importDocumentUsingProperFormattedStringOnDateIndex() throws IOException, ValidationException, DocumentNotFoundException, DocumentDuplicationException, DocumentClassNotFoundException {
        File singlePagePdf = loadFile("document-16p.pdf");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(2);

        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(INVOICE_DATE, new DescriptiveIndex("01.11.1978"));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(777.0));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        doc.setClient(CLIENT_WANGLER);
        DocumentReference documentReference = documentService.importDocument(doc);

        assertNotNull(documentReference.getId());

        DocumentReference documentReferenceFromStore = documentService.findDocumentReference(documentReference.getId());

        assertTrue(documentReferenceFromStore.getIndices().containsKey(INVOICE_DATE));
        assertTrue(documentReferenceFromStore.getIndices().get(INVOICE_DATE).getValue() instanceof LocalDate);
        assertEquals(new LocalDate(1978, 11, 1), documentReferenceFromStore.getIndices().get(INVOICE_DATE).getValue());
    }

    @Test
    public void addingTheSameDocumentTwiceToDoxShouldThrowAnException() throws IOException, ValidationException, DocumentDuplicationException, DocumentClassNotFoundException {
        File temp = createTestFile("hello.world.txt", "Lorem ipsum");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(2);

        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(INVOICE_DATE, new DescriptiveIndex("01.11.1978"));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(51.0));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(temp), indexes, temp.getName());
        doc.setClient(CLIENT_WANGLER);
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
    public void importSinglePageTiff() throws IOException, ValidationException, DocumentNotFoundException, DocumentDuplicationException, DocumentClassNotFoundException {
        importTiff("document-1p.tif", 1);
    }

    @Test
    public void importTwentyPageTiff() throws IOException, ValidationException, DocumentNotFoundException, DocumentDuplicationException, DocumentClassNotFoundException {
        importTiff("document-20p.tif", 20);
    }

    @Test
    public void importMustRespectAttributesAssignedToADomain() throws IOException, DocumentDuplicationException, ValidationException, DocumentClassNotFoundException {
        File temp = createTestFile("hello2.world.txt", "Must not import");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        final String valueNotInDomain = "This value does not belong to the company domain";
        indexes.put(COMPANY, new DescriptiveIndex(valueNotInDomain));
        indexes.put(STRICT_COMPANY, new DescriptiveIndex(valueNotInDomain));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(100.0));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(temp), indexes, temp.getName());
        doc.setClient(CLIENT_WANGLER);
        try {
            documentService.importDocument(doc);
            fail("Should throw a validation exception");
        } catch (ValueNotInDomainException e) {
            assertThat(e.getDomainName(), is("strictdomain"));
            assertEquals(valueNotInDomain, e.getValue());
            assertEquals(2, e.getValidValues().size());
            assertTrue(e.getValidValues().contains("Sunrise"));
            assertTrue(e.getValidValues().contains("Swisscom"));
        }
    }

    @Test
    public void importCanAddNewValuesToNonStrictDomains() throws IOException, DocumentDuplicationException, ValidationException, DocumentClassNotFoundException {
        File temp = createTestFile("hello2.world.txt", "Must not import");

        final SortedSet<Attribute> attributesBefore = documentService.findAttributes(documentClass);

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        indexes.put(COMPANY, new DescriptiveIndex("Hello"));
        indexes.put(STRICT_COMPANY, new DescriptiveIndex("Swisscom"));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(100.0));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(temp), indexes, temp.getName());
        doc.setClient(CLIENT_WANGLER);
        documentService.importDocument(doc);

        SortedSet<Attribute> attributesAfter = documentService.findAttributes(documentClass);

        Predicate<Attribute> company = new Predicate<Attribute>() {
            @Override
            public boolean apply(Attribute input) {
                return "company".equals(input.getShortName());
            }
        };

        Predicate<Attribute> strictCompany = new Predicate<Attribute>() {
            @Override
            public boolean apply(Attribute input) {
                return "strictcompany".equals(input.getShortName());
            }
        };

        assertThat("Adding a new value must not be allowed", filter(attributesAfter, strictCompany).iterator().next().getDomain().getValues().size(), is(filter(attributesBefore, strictCompany).iterator().next().getDomain().getValues().size()));
        assertThat("Adding a new value is allowed", filter(attributesAfter, company).iterator().next().getDomain().getValues().size() - 1, is(filter(attributesBefore, company).iterator().next().getDomain().getValues().size()));
    }

    @Test
    public void importMustAcceptIntegerOnDoubleField() throws IOException, DocumentDuplicationException, ValidationException, DocumentClassNotFoundException {
        File temp = createTestFile("hello2.world.txt", "Content");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(100));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(temp), indexes, temp.getName());
        doc.setClient(CLIENT_WANGLER);
        DocumentReference reference = documentService.importDocument(doc);

        assertEquals(BigDecimal.class, reference.getIndices().get(INVOICE_AMOUNT).getValue().getClass());
        assertEquals("100", String.valueOf(reference.getIndices().get(INVOICE_AMOUNT).getValue()));
    }

    @Test
    public void importMustAcceptLongOnDoubleField() throws IOException, DocumentDuplicationException, ValidationException, DocumentClassNotFoundException {
        File temp = createTestFile("hello2.world.txt", "This is a content");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(100L));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(temp), indexes, temp.getName());
        doc.setClient(CLIENT_WANGLER);
        DocumentReference reference = documentService.importDocument(doc);

        assertEquals(BigDecimal.class, reference.getIndices().get(INVOICE_AMOUNT).getValue().getClass());
        assertEquals("100", String.valueOf(reference.getIndices().get(INVOICE_AMOUNT).getValue()));
    }

    @Test
    public void updateIndices() throws IOException, ValidationException, DocumentDuplicationException, DocumentNotFoundException, DocumentClassNotFoundException {

        File temp = createTestFile("file.txt", "content of this file");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(101L));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(temp), indexes, temp.getName());
        doc.setClient(CLIENT_WANGLER);
        DocumentReference reference = documentService.importDocument(doc);

        reference.getIndices().put(COMPANY, new DescriptiveIndex("Swisscom"));
        reference.getIndices().put(INVOICE_AMOUNT, new DescriptiveIndex(2));
        reference.getIndices().put(INVOICE_DATE, new DescriptiveIndex("15.12.1982"));

        DocumentReference referenceAfterUpdate = documentService.updateIndices(reference);

        assertEquals(INVOICE_AMOUNT_INDICES, referenceAfterUpdate.getIndices().size());
        assertEquals("Swisscom", referenceAfterUpdate.getIndices().get(COMPANY).getValue());
        assertEquals("2", String.valueOf(referenceAfterUpdate.getIndices().get(INVOICE_AMOUNT).getValue()));
        assertEquals(new LocalDate(1982, 12, 15), referenceAfterUpdate.getIndices().get(INVOICE_DATE).getValue());
    }

    @Test
    public void fetchAllDocuments() throws ValidationException, DocumentClassNotFoundException, DocumentDuplicationException, IOException {

        DocumentReference tiff1 = importDocument("document-1p.tif", this.documentClass.getShortName());
        DocumentReference pdf16 = importDocument("document-16p.pdf", this.documentClass.getShortName());
        DocumentReference pdf5 = importDocument("document-5p.pdf", this.documentClass.getShortName());

        Set<DocumentReference> documentReferences = documentService.retrieveAllDocumentReferences();

        assertThat(documentReferences.size(), is(3));
        assertTrue(documentReferences.contains(tiff1));
        assertTrue(documentReferences.contains(pdf16));
        assertTrue(documentReferences.contains(pdf5));
    }

    private void importTiff(String fileName, int expectedPageCount) throws IOException, ValidationException, DocumentDuplicationException, DocumentNotFoundException, DocumentClassNotFoundException {
        DocumentReference documentReference = importDocument(fileName, this.documentClass.getShortName());

        assertNotNull(documentReference.getId());

        DocumentReference documentReferenceFromStore = documentService.findDocumentReference(documentReference.getId());

        assertTrue(documentReferenceFromStore.getIndices().containsKey(INVOICE_DATE));
        assertTrue(documentReferenceFromStore.getIndices().get(INVOICE_DATE).getValue() instanceof LocalDate);
        assertEquals(new LocalDate(2012, 11, 1), documentReferenceFromStore.getIndices().get(INVOICE_DATE).getValue());
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

    @Test
    public void verifyThatAttributeIsTranslatableAndGetsTranslated() throws DocumentClassNotFoundException {

        DocumentClass invoice = DocumentClassBuilder.newDocumentClass().withShortName("INVOICE").withClient(CLIENT_WANGLER).build();
        final SortedSet<Attribute> attributes = documentService.findAttributes(invoice);

        for (Attribute attribute : attributes) {
            assertTrue(attribute instanceof Translatable);
            assertNotNull(attribute.getMappingColumn());
            assertNotNull("There should be a translation available", attribute.getTranslation());
        }
    }

    @Test
    public void verifyThatDocumentClassIsTranslatableAndGetsTranslated() throws DocumentClassNotFoundException {

        Set<DocumentClass> documentClasses = documentService.findDocumentClasses();

        for (DocumentClass documentClass : documentClasses) {
            assertTrue(documentClass instanceof Translatable);
            assertThat("There should be a translation available", documentClass.getTranslation(), is(not(nullValue())));
        }
    }

    @Test
    public void importPdfWithEncryption() throws DocumentClassNotFoundException, DocumentDuplicationException, ValidationException, IOException {

        File singlePagePdf = loadFile("Zinsausweis2013-CS-680419-20.pdf");

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(1);
        indexes.put(COMPANY, new DescriptiveIndex("Sunrise"));
        indexes.put(MONEY, new DescriptiveIndex(new Money(Currency.getInstance("CHF"), new BigDecimal("1235.50"))));
        indexes.put(INVOICE_AMOUNT, new DescriptiveIndex(12350L));
        indexes.put(INVOICE_DATE, new DescriptiveIndex(new Date()));

        PhysicalDocument doc = new PhysicalDocument(documentClass, readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        doc.setClient(CLIENT_WANGLER);
        final DocumentReference documentReference = documentService.importDocument(doc);

        assertThat(documentReference.getPageCount(), is(1));

        final Money money = (Money) documentReference.getIndices().get(MONEY).getValue();
        assertThat(money, is(not(nullValue())));
        assertThat(money.getCurrency().getCurrencyCode(), is("CHF"));
        assertThat(money.getAmount().toPlainString(), is("1235.50"));
    }

    @Test
    public void assignDocumentToTag() throws IOException, DocumentClassNotFoundException, DocumentDuplicationException, ValidationException, DocumentNotFoundException {

        final DocumentReference doc = importFile("someFilename.txt", "Ich bin auch eine Datei", "DUMMY_DOC", new HashMap<TranslatableKey, DescriptiveIndex>());

        documentService.assignTag(doc, "Business");

        final DocumentReference documentReference = documentService.findDocumentReference(doc.getId());

        assertThat(documentReference.getTags().size(), is(1));
        assertThat(documentReference.getTags().contains("Business"), is(true));
    }
}
