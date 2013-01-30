/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
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
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 11.07.12 12:28
 *        </div>
 */
public class DocumentServiceResearchTest extends AbstractTest {

    public static final TranslatableKey COMPANY = new TranslatableKey("company");

    @Before
    public void init() throws ValidationException, DocumentDuplicationException, IOException, DocumentNotFoundException, DocumentClassNotFoundException {

        Map<TranslatableKey, Object> indexes = newHashMapWithExpectedSize(3);
        indexes.put(COMPANY, "Sunrise");
        indexes.put(new TranslatableKey("invoiceDate"), "01.12.2009");
        indexes.put(new TranslatableKey("invoiceAmount"), "100.5");

        importFile("file-1.txt", "This is a test content", "INVOICE", indexes);

        indexes = newHashMapWithExpectedSize(3);
        indexes.put(COMPANY, "Swisscom");
        indexes.put(new TranslatableKey("invoiceDate"), "02.12.2009");
        indexes.put(new TranslatableKey("invoiceAmount"), "1200.99");

        importFile("file-2.txt", "This is a test content that contains more text", "INVOICE", indexes);

        loginAsRoot();
    }


    private DocumentReference importFile(final String fileName, final String content, final String docClassShortName, final Map<TranslatableKey, Object> indices) throws ValidationException, DocumentDuplicationException, IOException, DocumentNotFoundException, DocumentClassNotFoundException {
        File textFile01 = createTestFile(fileName, content);
        PhysicalDocument doc = new PhysicalDocument(new DocumentClass(docClassShortName), FileUtils.readFileToByteArray(textFile01), indices, fileName);
        try {
            DocumentReference documentReference = documentService.importDocument(doc);
            logger.debug("File '{}' received id {}", fileName, documentReference.getId());
            return documentReference;

        } catch (DocumentDuplicationException e) {
            return documentService.findDocumentReference(e.getDocumentId());
        }
    }

    @Test
    public void findSwisscomInvoice() throws DocumentClassNotFoundException {

        Map<TranslatableKey, Object> queryParams = newHashMapWithExpectedSize(1);
        final String companyName = "Swisscom";
        queryParams.put(COMPANY, companyName);

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);

        logger.debug("Found {}", documentReferences);

        assertThat(documentReferences.size(), is(1));
        assertThat((String) documentReferences.iterator().next().getIndices().get(COMPANY), is(companyName));
    }

    @Test
    public void findSunriseInvoice() throws DocumentClassNotFoundException {

        Map<TranslatableKey, Object> queryParams = newHashMapWithExpectedSize(1);
        final String companyName = "Sunrise";
        queryParams.put(COMPANY, companyName);

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals(companyName, documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findInvoicesByCompaniesStartingWithS() throws DocumentClassNotFoundException {

        Map<TranslatableKey, Object> queryParams = newHashMapWithExpectedSize(1);
        final String companyName = "S*";
        queryParams.put(COMPANY, companyName);

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(2, documentReferences.size());
        for (DocumentReference documentReference : documentReferences) {
            assertTrue(((String) documentReference.getIndices().get(COMPANY)).matches("(Swisscom|Sunrise)"));
        }
    }

    @Test
    public void findInvoicesByCompaniesStartingWithSAndContainingAnotherS() throws DocumentClassNotFoundException {

        Map<TranslatableKey, Object> queryParams = newHashMapWithExpectedSize(1);
        final String companyName = "S*s*";
        queryParams.put(COMPANY, companyName);

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(2, documentReferences.size());
        for (DocumentReference documentReference : documentReferences) {
            assertTrue(((String) documentReference.getIndices().get(COMPANY)).matches("(Swisscom|Sunrise)"));
        }
    }

    @Test
    public void findInvoicesByCompaniesSunXise() throws DocumentClassNotFoundException {

        Map<TranslatableKey, Object> queryParams = newHashMapWithExpectedSize(1);
        final String companyName = "Sun?ise";
        queryParams.put(COMPANY, companyName);

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals("Sunrise", documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findByExactInvoiceAmount() throws DocumentClassNotFoundException {

        Map<TranslatableKey, Object> queryParams = newHashMapWithExpectedSize(1);
        queryParams.put(new TranslatableKey("invoiceAmount"), "100.50");

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals("Sunrise", documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findByRangeInvoiceAmount() throws DocumentClassNotFoundException {

        Map<TranslatableKey, Object> queryParams = newHashMapWithExpectedSize(1);
        queryParams.put(new TranslatableKey("invoiceAmount"), new Range<>(new BigDecimal("100"), new BigDecimal("101")));

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals("Sunrise", documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToIndexCompany() {

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences("Sunrise");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals("Sunrise", documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToIndexCompanyUsingLowercaseString() {

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences("sunrise");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals("Sunrise", documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToIndexCompanyUsingFunnyFormattedString() {

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences("sUnRiSE");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals("Sunrise", documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToInvoiceAmount() {

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences("100.5");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals("Sunrise", documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToCompany() {

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences("02.12.2009");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals("Swisscom", documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToIndexCompanyUsingStringThatContainsWildcard() {

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences("S*");

        assertNotNull(documentReferences);
        assertEquals(2, documentReferences.size());
        for (DocumentReference documentReference : documentReferences) {
            assertTrue(((String) documentReference.getIndices().get(COMPANY)).matches("(Swisscom|Sunrise)"));
        }
    }

    @Test
    public void findBySingleStringReferringToAllProperties() {

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences("*");

        assertNotNull(documentReferences);
        assertEquals(2, documentReferences.size());
        for (DocumentReference documentReference : documentReferences) {
            assertTrue(((String) documentReference.getIndices().get(COMPANY)).matches("(Swisscom|Sunrise)"));
        }
    }

    @Test
    public void findBySingleStringReferringToIndexCompanyUsingStringThatContainsWildcards() {

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences("?unrise");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals("Sunrise", documentReferences.iterator().next().getIndices().get(COMPANY));
    }
}
