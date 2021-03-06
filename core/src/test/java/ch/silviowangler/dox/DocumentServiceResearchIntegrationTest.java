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

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Maps;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import ch.silviowangler.dox.api.DescriptiveIndex;
import ch.silviowangler.dox.api.DocumentClassNotFoundException;
import ch.silviowangler.dox.api.DocumentDuplicationException;
import ch.silviowangler.dox.api.DocumentNotFoundException;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.Range;
import ch.silviowangler.dox.api.TranslatableKey;
import ch.silviowangler.dox.api.ValidationException;
import ch.silviowangler.dox.api.rest.DocumentClass;

/**
 * @author Silvio Wangler
 * @since 0.1
 * <div>
 * Date: 11.07.12 12:28
 * </div>
 */
public class DocumentServiceResearchIntegrationTest extends AbstractIntegrationTest {

  public static final TranslatableKey COMPANY = new TranslatableKey("company");
  public static final TranslatableKey TITLE = new TranslatableKey("title");
  private static final String SWISSCOM = "Swisscom";
  private static final String SUNRISE = "Sunrise";
  public static final TranslatableKey INVOICE_AMOUNT = new TranslatableKey("invoiceAmount");
  public static final TranslatableKey INVOICE_DATE = new TranslatableKey("invoiceDate");
  public static final TranslatableKey MONEY = new TranslatableKey("money");

  @Before
  public void init() throws ValidationException, DocumentDuplicationException, IOException, DocumentNotFoundException,
      DocumentClassNotFoundException {

    loginAsTestRoot();

    Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);
    indexes.put(COMPANY, new DescriptiveIndex(SUNRISE));
    indexes.put(INVOICE_DATE, new DescriptiveIndex("01.12.2009"));
    indexes.put(INVOICE_AMOUNT, new DescriptiveIndex("100.5"));
    importFile("file-1.txt", "This is a test content", "INVOICE", indexes);

    indexes = newHashMapWithExpectedSize(3);
    indexes.put(COMPANY, new DescriptiveIndex(SWISSCOM));
    indexes.put(INVOICE_DATE, new DescriptiveIndex("02.12.2009"));
    indexes.put(INVOICE_AMOUNT, new DescriptiveIndex("1200.99"));
    importFile("file-2.txt", "This is a test content that contains more text", "INVOICE", indexes);

    indexes = newHashMapWithExpectedSize(2);
    indexes.put(COMPANY, new DescriptiveIndex(SUNRISE));
    indexes.put(TITLE, new DescriptiveIndex("This is a title"));
    importFile("file-3.txt", "tiny content", "CONTRACTS", indexes);

    indexes = newHashMapWithExpectedSize(3);
    indexes.put(COMPANY, new DescriptiveIndex(SWISSCOM));
    indexes.put(INVOICE_DATE, new DescriptiveIndex("02.12.2009"));
    indexes.put(INVOICE_AMOUNT, new DescriptiveIndex("12.60"));
    indexes.put(MONEY, new DescriptiveIndex("CHF 12.50"));
    importFile("file-4.txt", "tiny content 2", "INVOICE", indexes);
  }

  @Test
  public void findSwisscomInvoice() throws DocumentClassNotFoundException {

    Map<TranslatableKey, DescriptiveIndex> queryParams = newHashMapWithExpectedSize(1);
    final String companyName = SWISSCOM;
    queryParams.put(COMPANY, new DescriptiveIndex(companyName));

    Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

    assertNotNull(documentReferences);

    logger.debug("Found {}", documentReferences);

    assertThat(documentReferences.size(), is(2));

    DocumentReference docOne = documentReferences.iterator().next();
    assertThat(docOne.getIndices().get(COMPANY) instanceof DescriptiveIndex, is(true));
    assertThat(docOne.getClient(), is("wangler"));

    final DescriptiveIndex index = documentReferences.iterator().next().getIndices().get(COMPANY);
    assertThat((String) index.getValue(), is(companyName));
    assertThat(index.getAttribute(), is(not(nullValue())));

  }

  @Test
  public void findSunriseInvoice() throws DocumentClassNotFoundException {

    Map<TranslatableKey, DescriptiveIndex> queryParams = newHashMapWithExpectedSize(1);
    final String companyName = SUNRISE;
    queryParams.put(COMPANY, new DescriptiveIndex(companyName));

    Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

    assertNotNull(documentReferences);
    assertEquals(1, documentReferences.size());
    assertEquals(companyName, documentReferences.iterator().next().getIndices().get(COMPANY).getValue());
  }

  @Test
  public void findInvoicesByCompaniesStartingWithS() throws DocumentClassNotFoundException {

    Map<TranslatableKey, DescriptiveIndex> queryParams = newHashMapWithExpectedSize(1);
    final String companyName = "S*";
    queryParams.put(COMPANY, new DescriptiveIndex(companyName));

    Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

    assertNotNull(documentReferences);
    assertEquals(3, documentReferences.size());
    for (DocumentReference documentReference : documentReferences) {
      assertTrue(((String) documentReference.getIndices().get(COMPANY).getValue()).matches("(Swisscom|Sunrise)"));
    }
  }

  @Test
  public void findInvoicesByCompaniesStartingWithSAndContainingAnotherS() throws DocumentClassNotFoundException {

    Map<TranslatableKey, DescriptiveIndex> queryParams = newHashMapWithExpectedSize(1);
    final String companyName = "S*s*";
    queryParams.put(COMPANY, new DescriptiveIndex(companyName));

    Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

    assertNotNull(documentReferences);
    assertEquals(3, documentReferences.size());
    for (DocumentReference documentReference : documentReferences) {
      assertTrue(((String) documentReference.getIndices().get(COMPANY).getValue()).matches("(Swisscom|Sunrise)"));
    }
  }

  @Test
  public void findInvoicesByCompaniesSunXise() throws DocumentClassNotFoundException {

    Map<TranslatableKey, DescriptiveIndex> queryParams = newHashMapWithExpectedSize(1);
    final String companyName = "Sun?ise";
    queryParams.put(COMPANY, new DescriptiveIndex(companyName));

    Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

    assertNotNull(documentReferences);
    assertEquals(1, documentReferences.size());
    assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY).getValue());
  }

  @Test
  public void findByExactInvoiceAmount() throws DocumentClassNotFoundException {

    Map<TranslatableKey, DescriptiveIndex> queryParams = newHashMapWithExpectedSize(1);
    queryParams.put(INVOICE_AMOUNT, new DescriptiveIndex("100.50"));

    Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

    assertNotNull(documentReferences);
    assertEquals(1, documentReferences.size());
    assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY).getValue());
  }

  @Test
  public void findByRangeInvoiceAmount() throws DocumentClassNotFoundException {

    Map<TranslatableKey, DescriptiveIndex> queryParams = newHashMapWithExpectedSize(1);
    queryParams.put(INVOICE_AMOUNT, new DescriptiveIndex(new Range<>(new BigDecimal("100"), new BigDecimal("101"))));

    Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryParams, "INVOICE");

    assertNotNull(documentReferences);
    assertEquals(1, documentReferences.size());
    assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY).getValue());
  }

  @Test
  public void findBySingleStringReferringToIndexCompany() {

    List<DocumentReference> documentReferences = documentService.findDocumentReferences(SUNRISE);

    assertNotNull(documentReferences);
    assertEquals(2, documentReferences.size());
    assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY).getValue());
  }

  @Test
  public void findBySingleStringReferringToIndexCompanyUsingLowercaseString() {

    List<DocumentReference> documentReferences = documentService.findDocumentReferences("sunrise");

    assertNotNull(documentReferences);
    assertEquals(2, documentReferences.size());
    assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY).getValue());
  }

  @Test
  public void findBySingleStringReferringToIndexCompanyUsingFunnyFormattedString() {

    List<DocumentReference> documentReferences = documentService.findDocumentReferences("sUnRiSE");

    assertNotNull(documentReferences);
    assertEquals(2, documentReferences.size());
    assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY).getValue());
  }

  @Test
  public void findBySingleStringReferringToInvoiceAmount() {

    List<DocumentReference> documentReferences = documentService.findDocumentReferences("100.5");

    assertNotNull(documentReferences);
    assertEquals(1, documentReferences.size());
    assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY).getValue());
  }

  @Test
  public void findBySingleStringReferringToCompany() {

    List<DocumentReference> documentReferences = documentService.findDocumentReferences("02.12.2009");

    assertNotNull(documentReferences);
    assertEquals(2, documentReferences.size());
    assertEquals(SWISSCOM, documentReferences.iterator().next().getIndices().get(COMPANY).getValue());
  }

  @Test
  public void findBySingleStringReferringToIndexCompanyUsingStringThatContainsWildcard() {

    List<DocumentReference> documentReferences = documentService.findDocumentReferences("S*");

    assertNotNull(documentReferences);
    assertEquals(4, documentReferences.size());
    for (DocumentReference documentReference : documentReferences) {
      assertTrue(((String) documentReference.getIndices().get(COMPANY).getValue()).matches("(Swisscom|Sunrise)"));
    }
  }

  @Test
  public void findBySingleStringReferringToAllProperties() {

    List<DocumentReference> documentReferences = documentService.findDocumentReferences("*");

    assertNotNull(documentReferences);
    assertEquals(4, documentReferences.size());
    for (DocumentReference documentReference : documentReferences) {
      assertTrue(((String) documentReference.getIndices().get(COMPANY).getValue()).matches("(Swisscom|Sunrise)"));
    }
  }

  @Test
  public void findBySingleStringReferringToIndexCompanyUsingStringThatContainsWildcards() {

    List<DocumentReference> documentReferences = documentService.findDocumentReferences("?unrise");

    assertNotNull(documentReferences);
    assertEquals(2, documentReferences.size());
    assertEquals(SUNRISE, documentReferences.iterator().next().getIndices().get(COMPANY).getValue());
  }

  @Test
  public void findByFileName() {

    List<DocumentReference> documentReferences = documentService.findDocumentReferences("file-1.txt");

    assertThat(documentReferences, is(not(nullValue())));
    assertThat(documentReferences.size(), is(1));
    final DocumentReference doc = documentReferences.iterator().next();

    assertEquals(SUNRISE, doc.getIndices().get(COMPANY).getValue());
    assertEquals(BigDecimal.valueOf(100.5), doc.getIndices().get(INVOICE_AMOUNT).getValue());
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
      assertThat(documentReferences.size(), is(1));
      assertEquals(SWISSCOM, documentReferences.get(0).getIndices().get(COMPANY).getValue());
      assertEquals(BigDecimal.valueOf(1200.99), documentReferences.get(0).getIndices().get(INVOICE_AMOUNT).getValue());
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void findByAnotherFileNameAndMakeSureTheIndicesAreTranslatedForUseInUserInterface() {

    List<List<DocumentReference>> results = newArrayList(
        documentService.findDocumentReferences("file-4.txt", Locale.GERMAN),
        documentService.findDocumentReferences("file-4.*t", Locale.GERMAN)
    );

    for (List<DocumentReference> documentReferences : results) {
      assertThat(documentReferences, is(not(nullValue())));
      assertThat(documentReferences.size(), is(1));
      final Map<TranslatableKey, DescriptiveIndex> indices = documentReferences.get(0).getIndices();
      assertEquals(SWISSCOM, indices.get(COMPANY).getValue());
      assertEquals("12.60", indices.get(INVOICE_AMOUNT).getValue().toString());
      assertEquals("CHF 12.50", indices.get(MONEY).getValue());
    }
  }

  @Test
  public void testMakeSureItRespectsTheDocumentClass() throws Exception {

    Map<TranslatableKey, DescriptiveIndex> index = Maps.newHashMap();
    index.put(COMPANY, new DescriptiveIndex(SUNRISE));

    final Set<DocumentReference> documentReferences = documentService.findDocumentReferences(index, "CONTRACTS");

    assertThat(documentReferences.size(), is(1));
    final DocumentReference documentReference = documentReferences.iterator().next();

    assertThat(documentReference.getDocumentClass().getShortName(), is("CONTRACTS"));

    for (DescriptiveIndex descriptiveIndex : documentReference.getIndices().values()) {
      assertNotNull("Translation of index '" + descriptiveIndex.getAttribute().getShortName() + "' must not be null", descriptiveIndex
          .getAttribute().getTranslation());
    }
  }

  @Test
  public void testMakeSureItRespectsTheDocumentClass2() throws Exception {

    Map<TranslatableKey, DescriptiveIndex> index = Maps.newHashMap();
    index.put(COMPANY, new DescriptiveIndex(SUNRISE));
    index.put(TITLE, new DescriptiveIndex("This is a title"));

    final Set<DocumentReference> documentReferences = documentService.findDocumentReferences(index, "CONTRACTS");

    assertThat(documentReferences.size(), is(1));
    assertThat(documentReferences.iterator().next().getDocumentClass().getShortName(), is("CONTRACTS"));
  }

  @Test
  public void testMakeSureItRespectsTheDocumentClass3() throws Exception {

    Map<TranslatableKey, DescriptiveIndex> index = Maps.newHashMap();
    index.put(COMPANY, new DescriptiveIndex(SUNRISE));
    index.put(TITLE, new DescriptiveIndex("*title"));

    final Set<DocumentReference> documentReferences = documentService.findDocumentReferences(index, "CONTRACTS");

    assertThat(documentReferences.size(), is(1));
    assertThat(documentReferences.iterator().next().getDocumentClass().getShortName(), is("CONTRACTS"));
  }

  @Test
  public void testClientCapabilityOnResearch() throws DocumentClassNotFoundException, IOException, DocumentNotFoundException,
      DocumentDuplicationException, ValidationException {

    loginAsTestRoot();

    Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(1);
    indexes.put(new TranslatableKey("name"), new DescriptiveIndex("John F. Kennedy"));

    importFile("yolo.txt", "Ich bin ein Berliner", "DUMMY_DOC", indexes, "wangler_test");

    loginAs("root");
    List<DocumentReference> documents = documentService.findDocumentReferences("*Kennedy*");

    assertThat(documents.size(), is(0));

    loginAsTestRoot();

    documents = documentService.findDocumentReferences("*Kennedy*");
    assertThat(documents.size(), is(1));

    DocumentReference documentReference = documents.get(0);
    assertThat(documentReference.getClient(), is("wangler_test"));
    assertThat(documentReference.getDocumentClass().getClient(), is("wangler_test"));
    assertThat(documentReference.getDocumentClass().getClient(), is("wangler_test"));

  }

  @Test
  public void testClientCapabilityOnRetrievingDocumentClasses() {
    loginAs("root");
    List<DocumentClass> documentClasses = documentService.findAllDocumentClasses();

    for (DocumentClass documentClass : documentClasses) {
      assertThat("Document class " + documentClass.getShortName(), documentClass.getClient(), is("wangler"));
    }
  }
}
