/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
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

import ch.silviowangler.dox.api.*;
import com.google.common.collect.Maps;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 11.07.12 12:28
 *        </div>
 */
public class DocumentServiceResearchIntegrationTest extends AbstractIntegrationTest {

    public static final TranslatableKey COMPANY = new TranslatableKey("company");
    public static final TranslatableKey TITLE = new TranslatableKey("title");
    private static final String SWISSCOM = "Swisscom";
    private static final String SUNRISE = "Sunrise";

    @Before
    public void init() throws ValidationException, DocumentDuplicationException, IOException, DocumentNotFoundException, DocumentClassNotFoundException {

        loginAsRoot();

        Map<TranslatableKey, Object> indexes = newHashMapWithExpectedSize(3);
        indexes.put(COMPANY, SUNRISE);
        indexes.put(new TranslatableKey("invoiceDate"), "01.12.2009");
        indexes.put(new TranslatableKey("invoiceAmount"), "100.5");

        importFile("file-1.txt", "This is a test content", "INVOICE", indexes);

        indexes = newHashMapWithExpectedSize(3);
        indexes.put(COMPANY, SWISSCOM);
        indexes.put(new TranslatableKey("invoiceDate"), "02.12.2009");
        indexes.put(new TranslatableKey("invoiceAmount"), "1200.99");

        importFile("file-2.txt", "This is a test content that contains more text", "INVOICE", indexes);

        indexes = newHashMapWithExpectedSize(2);
        indexes.put(COMPANY, SUNRISE);
        indexes.put(TITLE, "This is a title");
        importFile("file-3.txt", "tiny content", "CONTRACTS", indexes);

        loginAsRoot();
    }

    @Test
    public void findSwisscomInvoice() throws DocumentClassNotFoundException {

        Map<TranslatableKey, Object> queryParams = newHashMapWithExpectedSize(1);
        final String companyName = SWISSCOM;
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
        final String companyName = SUNRISE;
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
        assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findByExactInvoiceAmount() throws DocumentClassNotFoundException {

        Map<TranslatableKey, Object> queryParams = newHashMapWithExpectedSize(1);
        queryParams.put(new TranslatableKey("invoiceAmount"), "100.50");

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findByRangeInvoiceAmount() throws DocumentClassNotFoundException {

        Map<TranslatableKey, Object> queryParams = newHashMapWithExpectedSize(1);
        queryParams.put(new TranslatableKey("invoiceAmount"), new Range<>(new BigDecimal("100"), new BigDecimal("101")));

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToIndexCompany() {

        List<DocumentReference> documentReferences = documentService.findDocumentReferences(SUNRISE);

        assertNotNull(documentReferences);
        assertEquals(2, documentReferences.size());
        assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToIndexCompanyUsingLowercaseString() {

        List<DocumentReference> documentReferences = documentService.findDocumentReferences("sunrise");

        assertNotNull(documentReferences);
        assertEquals(2, documentReferences.size());
        assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToIndexCompanyUsingFunnyFormattedString() {

        List<DocumentReference> documentReferences = documentService.findDocumentReferences("sUnRiSE");

        assertNotNull(documentReferences);
        assertEquals(2, documentReferences.size());
        assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToInvoiceAmount() {

        List<DocumentReference> documentReferences = documentService.findDocumentReferences("100.5");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToCompany() {

        List<DocumentReference> documentReferences = documentService.findDocumentReferences("02.12.2009");

        assertNotNull(documentReferences);
        assertEquals(1, documentReferences.size());
        assertEquals(SWISSCOM, documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findBySingleStringReferringToIndexCompanyUsingStringThatContainsWildcard() {

        List<DocumentReference> documentReferences = documentService.findDocumentReferences("S*");

        assertNotNull(documentReferences);
        assertEquals(3, documentReferences.size());
        for (DocumentReference documentReference : documentReferences) {
            assertTrue(((String) documentReference.getIndices().get(COMPANY)).matches("(Swisscom|Sunrise)"));
        }
    }

    @Test
    public void findBySingleStringReferringToAllProperties() {

        List<DocumentReference> documentReferences = documentService.findDocumentReferences("*");

        assertNotNull(documentReferences);
        assertEquals(3, documentReferences.size());
        for (DocumentReference documentReference : documentReferences) {
            assertTrue(((String) documentReference.getIndices().get(COMPANY)).matches("(Swisscom|Sunrise)"));
        }
    }

    @Test
    public void findBySingleStringReferringToIndexCompanyUsingStringThatContainsWildcards() {

        List<DocumentReference> documentReferences = documentService.findDocumentReferences("?unrise");

        assertNotNull(documentReferences);
        assertEquals(2, documentReferences.size());
        assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY));
    }

    @Test
    public void findByFileName() {

        List<DocumentReference> documentReferences = documentService.findDocumentReferences("file-1.txt");

        assertThat(documentReferences, is(not(nullValue())));
        assertThat(documentReferences.size(), CoreMatchers.is(1));
        final DocumentReference doc = documentReferences.iterator().next();

        assertEquals(SUNRISE, doc.getIndices().get(COMPANY));
        assertEquals(BigDecimal.valueOf(100.5), doc.getIndices().get(new TranslatableKey("invoiceAmount")));
        assertThat(doc.getDocumentClass().getShortName(), is("INVOICE"));
        assertThat(doc.getDocumentClass().getTranslation(), is(notNullValue()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findByAnotherFileName() {

        List<List<DocumentReference>> results = newArrayList(
                documentService.findDocumentReferences("file-2.txt"),
                documentService.findDocumentReferences("file-2.*t")
        );

        for (List<DocumentReference> documentReferences : results) {
            assertThat(documentReferences, is(not(nullValue())));
            assertThat(documentReferences.size(), CoreMatchers.is(1));
            assertEquals(SWISSCOM, documentReferences.get(0).getIndices().get(COMPANY));
            assertEquals(BigDecimal.valueOf(1200.99), documentReferences.get(0).getIndices().get(new TranslatableKey("invoiceAmount")));
        }
    }

    @Test
    public void testMakeSureItRespectsTheDocumentClass() throws Exception {

        Map<TranslatableKey, Object> index = Maps.newHashMap();
        index.put(COMPANY, SUNRISE);

        final Set<DocumentReference> documentReferences = documentService.findDocumentReferences(index, "CONTRACTS");

        assertThat(documentReferences.size(), is(1));
        assertThat(documentReferences.iterator().next().getDocumentClass().getShortName(), is("CONTRACTS"));
    }

    @Test
    public void testMakeSureItRespectsTheDocumentClass2() throws Exception {

        Map<TranslatableKey, Object> index = Maps.newHashMap();
        index.put(COMPANY, SUNRISE);
        index.put(TITLE, "This is a title");

        final Set<DocumentReference> documentReferences = documentService.findDocumentReferences(index, "CONTRACTS");

        assertThat(documentReferences.size(), is(1));
        assertThat(documentReferences.iterator().next().getDocumentClass().getShortName(), is("CONTRACTS"));
    }

    @Test
    public void testMakeSureItRespectsTheDocumentClass3() throws Exception {

        Map<TranslatableKey, Object> index = Maps.newHashMap();
        index.put(COMPANY, SUNRISE);
        index.put(TITLE, "*title");

        final Set<DocumentReference> documentReferences = documentService.findDocumentReferences(index, "CONTRACTS");

        assertThat(documentReferences.size(), is(1));
        assertThat(documentReferences.iterator().next().getDocumentClass().getShortName(), is("CONTRACTS"));
    }
}
