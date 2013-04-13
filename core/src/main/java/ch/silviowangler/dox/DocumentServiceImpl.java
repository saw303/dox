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
import ch.silviowangler.dox.api.Domain;
import ch.silviowangler.dox.domain.*;
import ch.silviowangler.dox.domain.Attribute;
import ch.silviowangler.dox.domain.AttributeDataType;
import ch.silviowangler.dox.domain.DocumentClass;
import ch.silviowangler.dox.domain.Range;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.itextpdf.text.io.RandomAccessSource;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

import static ch.silviowangler.dox.domain.AttributeDataType.*;
import static ch.silviowangler.dox.domain.DomainUtils.containsWildcardCharacters;
import static ch.silviowangler.dox.domain.DomainUtils.replaceWildcardCharacters;
import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;
import static org.springframework.util.Assert.*;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService, InitializingBean {

    private static final String DD_MM_YYYY = "dd.MM.yyyy";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String CACHE_DOCUMENT_COUNT = "documentCount";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DocumentClassRepository documentClassRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DomainRepository domainRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private IndexStoreRepository indexStoreRepository;
    @Value("#{systemEnvironment['DOX_STORE']}")
    private File archiveDirectory;
    @Autowired
    private Properties mimeTypes;
    @Autowired
    private IndexMapEntryRepository indexMapEntryRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(archiveDirectory, "Archive directory must not be null. Please make sure you have properly set environment variable DOX_STORE");
        isTrue(archiveDirectory.isDirectory(), "Archive store must be a directory ['" + this.archiveDirectory + "']");
        isTrue(archiveDirectory.canRead(), "Archive store must be readable ['" + this.archiveDirectory + "']");
        isTrue(archiveDirectory.canWrite(), "Archive store must be writable ['" + this.archiveDirectory + "']");
        notEmpty(mimeTypes, "No mime types have been set");
    }

    @Override
    @Cacheable(CACHE_DOCUMENT_COUNT)
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public long retrieveDocumentReferenceCount() {
        return documentRepository.count();
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public SortedSet<ch.silviowangler.dox.api.Attribute> findAttributes(ch.silviowangler.dox.api.DocumentClass documentClass) throws DocumentClassNotFoundException {

        DocumentClass docClass = findDocumentClass(documentClass.getShortName());
        List<Attribute> attributes = attributeRepository.findAttributesForDocumentClass(docClass);
        return toAttributeApi(attributes);
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public Set<ch.silviowangler.dox.api.DocumentClass> findDocumentClasses() {

        Set<ch.silviowangler.dox.api.DocumentClass> result = new HashSet<>();
        Iterable<DocumentClass> documentClasses = documentClassRepository.findAll();

        for (DocumentClass documentClass : documentClasses) {
            logger.debug("Processing document class '{}' with id {}", documentClass.getShortName(), documentClass.getId());
            result.add(toDocumentClassApi(documentClass));
        }
        logger.info("Found {} document classes in DOX", result.size());
        return result;
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public Set<DocumentReference> findDocumentReferences(String queryString) {

        logger.debug("About to find document references for query string '{}'", queryString);

        List<Document> documents;

        if (containsWildcardCharacters(queryString)) {
            String value = replaceWildcardCharacters(queryString);
            documents = indexMapEntryRepository.findByValueLike(value.toUpperCase(), value);
        } else {
            documents = indexMapEntryRepository.findByValue(queryString.toUpperCase(), queryString);
        }

        Set<DocumentReference> documentReferences = new HashSet<>(documents.size());

        logger.info("Found {} documents for query string '{}'", documents.size(), queryString);

        for (Document document : documents) {
            logger.trace("Found document with id {}", document.getId());
            documentReferences.add(toDocumentReference(document));
        }
        return documentReferences;
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Set<DocumentReference> findDocumentReferences(Map<TranslatableKey, Object> queryParams, String documentClassShortName) throws DocumentClassNotFoundException {

        logger.debug("Trying to find document references in document class '{}' using params '{}'", documentClassShortName, queryParams);
        DocumentClass documentClass = findDocumentClass(documentClassShortName);
        List<Attribute> attributes = attributeRepository.findAttributesForDocumentClass(documentClass);

        List<Document> documents = documentRepository.findDocuments(toEntityMap(fixDataTypesOfIndices(queryParams, attributes)), toAttributeMap(attributes));

        HashSet<DocumentReference> documentReferences = new HashSet<>(documents.size());
        for (Document document : documents) {
            logger.trace("Found document with id {}", document.getId());
            documentReferences.add(toDocumentReference(document));
        }
        return documentReferences;
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public PhysicalDocument findPhysicalDocument(Long id) throws DocumentNotFoundException, DocumentNotInStoreException {

        DocumentReference doc = findDocumentReference(id);
        PhysicalDocument document = new PhysicalDocument();

        try {
            BeanUtils.copyProperties(document, doc);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("Unable to copy properties", e);
        }

        File file = new File(this.archiveDirectory, doc.getHash());

        if (!file.exists()) {
            logger.error("Unable to find file for document hash '{}' on path '{}'", doc.getHash(), file.getAbsolutePath());
            throw new DocumentNotInStoreException(doc.getHash(), doc.getId());
        }

        try {
            document.setContent(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            logger.error("Unable to read content of file '{}'", file.getAbsolutePath(), e);
        }
        return document;
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public DocumentReference findDocumentReference(Long id) throws DocumentNotFoundException {
        logger.info("About to find document by using id {}", id);

        if (documentReferenceExists(id)) {
            logger.info("Found document for id {}", id);
            Document document = documentRepository.findOne(id);

            DocumentReference documentReference = toDocumentReference(document);

            return documentReference;
        } else {
            logger.warn("No document found for id {}", id);
            throw new DocumentNotFoundException(id);
        }
    }

    @Override
    @CacheEvict(value = CACHE_DOCUMENT_COUNT, allEntries = true)
    @Transactional(propagation = REQUIRED, readOnly = false)
    public DocumentReference importDocument(PhysicalDocument physicalDocumentApi) throws ValidationException, DocumentDuplicationException, DocumentClassNotFoundException {

        final String documentClassShortName = physicalDocumentApi.getDocumentClass().getShortName();
        DocumentClass documentClassEntity = findDocumentClass(documentClassShortName);

        List<Attribute> attributes = attributeRepository.findAttributesForDocumentClass(documentClassEntity);

        logger.debug("Found {} attributes for document class '{}'", attributes.size(), documentClassShortName);

        verifyMandatoryAttributes(physicalDocumentApi, attributes);
        verifyUnknownKeys(physicalDocumentApi, documentClassShortName, attributes);
        try {
            verifyDomainValues(physicalDocumentApi, attributes);
        } catch (ValueNotInDomainException e) {
            ch.silviowangler.dox.domain.Domain domain = domainRepository.findByShortName(e.getDomainName());

            if (domain.isStrict()) {
                logger.warn("Domain '{}' is defined as strict domain and can therefore only accept predefined value", e.getDomainName());
                throw e;
            }
            logger.debug("Adding value '{}' to domain '{}'", e.getValue(), e.getDomainName());
            domain.getValues().add(e.getValue());
            domainRepository.save(domain);
            verifyDomainValues(physicalDocumentApi, attributes);
        }

        physicalDocumentApi.setIndices(fixDataTypesOfIndices(physicalDocumentApi.getIndices(), attributes));

        final String mimeType = investigateMimeType(physicalDocumentApi.getFileName());
        final String hash = DigestUtils.sha256Hex(physicalDocumentApi.getContent());

        final Document documentByHash = documentRepository.findByHash(hash);
        if (documentByHash != null) {
            throw new DocumentDuplicationException(documentByHash.getId(), hash);
        }

        final int numberOfPages = getNumberOfPages(physicalDocumentApi, mimeType);

        IndexStore indexStore = new IndexStore();
        updateIndices(physicalDocumentApi, indexStore);
        Document document = new Document(hash, documentClassEntity, numberOfPages, mimeType, physicalDocumentApi.getFileName(), indexStore);
        indexStore.setDocument(document);
        document = documentRepository.save(document);
        indexStoreRepository.save(indexStore);

        updateIndexMapEntries(toEntityMap(physicalDocumentApi.getIndices()), document);

        File target = new File(this.archiveDirectory, hash);
        try {
            FileUtils.writeByteArrayToFile(target, physicalDocumentApi.getContent());
        } catch (IOException e) {
            logger.error("Unable to write content to store", e);
        }
        DocumentReference docRef = toDocumentReference(document);
        return docRef;
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = false)
    public DocumentReference updateIndices(DocumentReference reference) throws DocumentNotFoundException {

        DocumentReference documentReferenceApi = findDocumentReference(reference.getId());
        documentReferenceApi.getIndices().putAll(reference.getIndices());

        Document document = documentRepository.findOne(reference.getId());
        DocumentClass documentClassEntity = document.getDocumentClass();

        List<Attribute> attributes = attributeRepository.findAttributesForDocumentClass(documentClassEntity);

        documentReferenceApi.setIndices(fixDataTypesOfIndices(documentReferenceApi.getIndices(), attributes));

        updateIndices(documentReferenceApi, document.getIndexStore());

        indexStoreRepository.save(document.getIndexStore());

        updateIndexMapEntries(toEntityMap(documentReferenceApi.getIndices()), document);

        return findDocumentReference(reference.getId());
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public Set<DocumentReference> retrieveAllDocumentReferences() {
        logger.info("Retrieving all document references from repository");
        Iterable<Document> documents = documentRepository.findAll();
        Set<DocumentReference> documentReferences = Sets.newHashSet();

        for (Document document : documents) {
            logger.debug("Processing document {}", document);
            documentReferences.add(toDocumentReference(document));
        }
        logger.info("Done retrieving all document references from repository. Fetched {} document references", documentReferences.size());
        return documentReferences;
    }

    private void verifyDomainValues(PhysicalDocument physicalDocument, List<Attribute> attributes) throws ValueNotInDomainException {
        for (Attribute attribute : attributes) {
            final String attributeShortName = attribute.getShortName();
            final TranslatableKey key = new TranslatableKey(attributeShortName);
            if (attribute.getDomain() != null && physicalDocument.getIndices().containsKey(key)) {
                final String attributeValue = String.valueOf(physicalDocument.getIndices().get(key));
                logger.debug("Analyzing domain value on attribute '{}' for value '{}'", attributeShortName, attributeValue);
                if (!attribute.getDomain().getValues().contains(attributeValue)) {
                    logger.error("Attribute '{}' belongs to a domain. This domain does not contain the value '{}'", attributeShortName, attributeValue);
                    throw new ValueNotInDomainException("Value is not part of this domain", attributeValue, attribute.getDomain().getValues(), attribute.getDomain().getShortName());
                }
            }
        }
        logger.info("All domains and their values have been respected");
    }

    private String getStringRepresentation(Object indexValue) {
        String indexValueToStore;

        if (indexValue instanceof DateTime) {
            indexValueToStore = ((DateTime) indexValue).toString(DD_MM_YYYY);
        } else {
            indexValueToStore = String.valueOf(indexValue);
        }
        logger.debug("String representation of index value '{}' is '{}'", indexValue, indexValueToStore);
        return indexValueToStore;
    }

    private String investigateMimeType(final String fileName) {

        logger.debug("Trying to find media type (mime type) for file name '{}'", fileName);

        int i = fileName.lastIndexOf('.');
        String extension = null;

        if (i > 0 && i < fileName.length() - 1) {
            extension = fileName.substring(i + 1).toLowerCase();
        }

        if (this.mimeTypes.containsKey(extension)) {
            return (String) this.mimeTypes.get(extension);
        }
        logger.error("No media type (mime type) registered for file extension '{}' (original file name '{}')", extension, fileName);
        throw new UnsupportedOperationException("No mime type registered for file extension " + fileName);
    }

    private Map<TranslatableKey, Object> fixDataTypesOfIndices(final Map<TranslatableKey, Object> indexes, List<Attribute> attributes) {

        Map<TranslatableKey, Object> resultMap = Maps.newHashMap(indexes); // copy elements

        for (Attribute attribute : attributes) {

            final TranslatableKey key = new TranslatableKey(attribute.getShortName());
            if (resultMap.containsKey(key) && resultMap.get(key) != null) {
                if (!isAssignableType(attribute.getDataType(), resultMap.get(key).getClass())) {
                    logger.debug("Attribute '{}' is not assignable to '{}'", key, attribute.getDataType());
                    resultMap.put(key, makeAssignable(attribute.getDataType(), resultMap.get(key)));
                }
            } else {
                logger.debug("Ignoring attribute '{}' since it was not mentioned in the index map", key);
            }
        }
        return resultMap;
    }

    private Map<String, Object> toEntityMap(final Map<TranslatableKey, Object> indices) {
        Map<String, Object> entityMap = Maps.newHashMapWithExpectedSize(indices.size());

        for (TranslatableKey key : indices.keySet()) {
            entityMap.put(key.getKey(), indices.get(key));
        }
        return entityMap;
    }

    @SuppressWarnings("unchecked")
    private Object makeAssignable(AttributeDataType desiredDataType, Object valueToConvert) {

        if (valueToConvert instanceof ch.silviowangler.dox.api.Range && isRangeCompatible(desiredDataType)) {
            logger.debug("Found a range parameter. Skip this one");

            if (DATE.equals(desiredDataType)) {
                ch.silviowangler.dox.api.Range<DateTime> original = (ch.silviowangler.dox.api.Range<DateTime>) valueToConvert;
                return new Range<>(original.getFrom(), original.getTo());
            } else if (DOUBLE.equals(desiredDataType)) {
                ch.silviowangler.dox.api.Range<BigDecimal> original = (ch.silviowangler.dox.api.Range<BigDecimal>) valueToConvert;
                return new Range<>(original.getFrom(), original.getTo());
            } else if (INTEGER.equals(desiredDataType)) {
                ch.silviowangler.dox.api.Range<Integer> original = (ch.silviowangler.dox.api.Range<Integer>) valueToConvert;
                return new Range<>(original.getFrom(), original.getTo());
            } else if (LONG.equals(desiredDataType)) {
                ch.silviowangler.dox.api.Range<Long> original = (ch.silviowangler.dox.api.Range<Long>) valueToConvert;
                return new Range<>(original.getFrom(), original.getTo());
            } else if (SHORT.equals(desiredDataType)) {
                ch.silviowangler.dox.api.Range<Short> original = (ch.silviowangler.dox.api.Range<Short>) valueToConvert;
                return new Range<>(original.getFrom(), original.getTo());
            }
            throw new IllegalArgumentException();
        }

        if (DATE.equals(desiredDataType) && valueToConvert instanceof String) {

            final String stringValueToConvert = (String) valueToConvert;
            String regexPattern;

            if (stringValueToConvert.matches("\\d{4}-\\d{2}-\\d{2}")) {
                regexPattern = YYYY_MM_DD;
            } else if (stringValueToConvert.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                regexPattern = DD_MM_YYYY;
            } else {
                logger.error("Unsupported format of a date string '{}'", stringValueToConvert);
                throw new UnsupportedOperationException("Unknown date format " + stringValueToConvert);
            }
            return DateTimeFormat.forPattern(regexPattern).parseDateTime(stringValueToConvert);
        } else if (DATE.equals(desiredDataType) && valueToConvert instanceof Date) {
            return new DateTime(valueToConvert);
        } else if (DOUBLE.equals(desiredDataType) && valueToConvert instanceof Double) {
            return BigDecimal.valueOf((Double) valueToConvert);
        } else if (DOUBLE.equals(desiredDataType) && valueToConvert instanceof String && ((String) valueToConvert).matches("(\\d.*|\\d.*\\.\\d{1,2})")) {
            return new BigDecimal((String) valueToConvert);
        } else if (DOUBLE.equals(desiredDataType) && valueToConvert instanceof Integer) {
            return BigDecimal.valueOf(Long.parseLong(String.valueOf(valueToConvert)));
        } else if (DOUBLE.equals(desiredDataType) && valueToConvert instanceof Long) {
            return BigDecimal.valueOf((Long) valueToConvert);
        } else if (CURRENCY.equals(desiredDataType) && valueToConvert instanceof Money) {
            Money money = (Money) valueToConvert;
            return new AmountOfMoney(money.getCurrency(), money.getAmount());
        }

        logger.error("Unable to convert data type '{}' and value '{}' (class: '{}')", new Object[]{desiredDataType, valueToConvert, valueToConvert.getClass().getCanonicalName()});
        throw new IllegalArgumentException("Unable to convert data type " + desiredDataType + " and value " + valueToConvert + "(Class: '" + valueToConvert.getClass().getCanonicalName() + "')");
    }

    private boolean isRangeCompatible(AttributeDataType desiredDataType) {
        return (DATE.equals(desiredDataType) ||
                DOUBLE.equals(desiredDataType) ||
                INTEGER.equals(desiredDataType) ||
                LONG.equals(desiredDataType) ||
                SHORT.equals(desiredDataType));
    }

    @SuppressWarnings("unchecked")
    private boolean isAssignableType(AttributeDataType desiredDataType, Class currentType) {

        if (desiredDataType == DATE) {
            return currentType.isAssignableFrom(DateTime.class);
        } else if (desiredDataType == STRING) {
            return currentType.isAssignableFrom(String.class);
        } else if (desiredDataType == DOUBLE) {
            return currentType.isAssignableFrom(BigDecimal.class);
        } else if (desiredDataType == CURRENCY) {
            return currentType.isAssignableFrom(AmountOfMoney.class);
        } else {
            logger.error("Unknown data type '{}'", desiredDataType);
            throw new IllegalArgumentException("Unknown data type " + desiredDataType);
        }
    }

    private void updateIndices(DocumentReference documentReference, IndexStore indexStore) {
        for (TranslatableKey key : documentReference.getIndices().keySet()) {

            Attribute attribute = attributeRepository.findByShortName(key.getKey());
            assert attribute != null : "Attribute " + key + " must be there";

            final Object value = documentReference.getIndices().get(key);
            try {
                final String propertyName = attribute.getMappingColumn().toLowerCase();
                logger.debug("About to set column '{}' using value '{}' on index store", propertyName, value);
                PropertyUtils.setProperty(indexStore, propertyName, value);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.error("Error setting property '{}' with value '{}'", new Object[]{key, value, e});
            }
        }
    }

    private void verifyUnknownKeys(PhysicalDocument physicalDocument, String documentClassShortName, List<Attribute> attributes) throws ValidationException {
        for (TranslatableKey key : physicalDocument.getIndices().keySet()) {
            boolean exists = false;
            for (Attribute attribute : attributes) {
                if (attribute.getShortName().equals(key.getKey())) {
                    exists = true;
                    continue;
                }
            }
            if (!exists) {
                logger.warn("Key '{}' does not belong to document class '{}'", documentClassShortName);
                throw new ValidationException("Key " + key + " does not belong to document class " + documentClassShortName);
            }
        }
    }

    private void verifyMandatoryAttributes(PhysicalDocument physicalDocument, List<Attribute> attributes) throws ValidationException {
        for (Attribute attribute : attributes) {
            if (!attribute.isOptional()) {
                logger.trace("Analyzing mandatory attribute '{}'", attribute.getShortName());
                if (!physicalDocument.getIndices().containsKey(new TranslatableKey(attribute.getShortName()))) {
                    logger.warn("Attribute '{}' is required for document class(es) '{}' and not was not provided.", attribute.getShortName(), attribute.getDocumentClasses());
                    throw new ValidationException("Attribute " + attribute.getShortName() + " is mandatory");
                }
            }
        }
    }

    private int getNumberOfPages(final PhysicalDocument physicalDocument, final String mimeType) {

        int numberOfPages = -1;

        if ("application/pdf".equals(mimeType)) {

            PdfReader pdfReader;
            try {
                pdfReader = new PdfReader(physicalDocument.getContent());
                numberOfPages = pdfReader.getNumberOfPages();
            } catch (IOException e) {
                logger.error("Unable to determine the number of pages", e);
                return numberOfPages;
            }
        } else if ("text/plain".equals(mimeType)) {
            numberOfPages = -1;
        } else if ("image/tiff".equals(mimeType)) {
            RandomAccessSource source = new RandomAccessSourceFactory().createSource(physicalDocument.getContent());
            numberOfPages = TiffImage.getNumberOfPages(new RandomAccessFileOrArray(source));
        } else {
            throw new UnsupportedOperationException("Cannot determine page count from a document with a mime type '" + mimeType + "'");
        }
        logger.debug("Found {} page(s) on physical document {}", numberOfPages, physicalDocument);
        return numberOfPages;
    }

    private boolean documentReferenceExists(Long id) {
        return documentRepository.exists(id);
    }

    private DocumentReference toDocumentReference(Document document) {
        return new DocumentReference(document.getHash(),
                document.getId(),
                document.getPageCount(),
                document.getMimeType(),
                toDocumentClassApi(document.getDocumentClass()),
                toIndexMap(document.getIndexStore(), attributeRepository.findAttributesForDocumentClass(document.getDocumentClass())),
                document.getOriginalFilename());
    }

    private Map<TranslatableKey, Object> toIndexMap(IndexStore indexStore, List<Attribute> attributes) {

        Map<TranslatableKey, Object> indices = newHashMapWithExpectedSize(attributes.size());

        for (Attribute attribute : attributes) {
            try {
                final Object propertyValue = PropertyUtils.getProperty(indexStore, attribute.getMappingColumn().toLowerCase());

                if (attribute.getDataType() == CURRENCY) {
                    AmountOfMoney amountOfMoney = (AmountOfMoney) propertyValue;
                    indices.put(new TranslatableKey(attribute.getShortName()), (amountOfMoney == null) ? null : new Money(amountOfMoney.getCurrency(), amountOfMoney.getAmount()));
                } else {
                    indices.put(new TranslatableKey(attribute.getShortName()), propertyValue);
                }

            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.error("Error setting property '{}'", attribute.getShortName(), e);
            }
        }
        return indices;
    }

    private ch.silviowangler.dox.api.DocumentClass toDocumentClassApi(DocumentClass documentClass) {
        return new ch.silviowangler.dox.api.DocumentClass(documentClass.getShortName());
    }

    private Map<String, Attribute> toAttributeMap(List<Attribute> attributes) {
        Map<String, Attribute> map = newHashMapWithExpectedSize(attributes.size());

        for (Attribute attribute : attributes) {
            map.put(attribute.getShortName(), attribute);
        }
        return map;
    }

    private SortedSet<ch.silviowangler.dox.api.Attribute> toAttributeApi(List<Attribute> attributes) {
        SortedSet<ch.silviowangler.dox.api.Attribute> result = new TreeSet<>();

        for (Attribute attribute : attributes) {
            ch.silviowangler.dox.api.Attribute attr = toAttributeApi(attribute);
            result.add(attr);
        }
        return result;
    }

    private ch.silviowangler.dox.api.Attribute toAttributeApi(Attribute attribute) {
        return new ch.silviowangler.dox.api.Attribute(
                attribute.getShortName(),
                attribute.isOptional(),
                attribute.getDomain() != null ? toDomainApi(attribute.getDomain()) : null,
                ch.silviowangler.dox.api.AttributeDataType.valueOf(attribute.getDataType().toString()),
                attribute.isUpdateable(), attribute.getMappingColumn());
    }

    private Domain toDomainApi(ch.silviowangler.dox.domain.Domain domain) {

        Domain domainApi = new Domain();
        domainApi.setShortName(domain.getShortName());
        for (String domainValue : domain.getValues()) {
            domainApi.getValues().add(domainValue);
        }
        return domainApi;
    }


    private DocumentClass findDocumentClass(String documentClassShortName) throws DocumentClassNotFoundException {
        DocumentClass documentClassEntity = documentClassRepository.findByShortName(documentClassShortName);

        if (documentClassEntity == null) {
            logger.error("No such document class with name '{}' found", documentClassShortName);
            throw new DocumentClassNotFoundException(documentClassShortName);
        }
        return documentClassEntity;
    }

    private void updateIndexMapEntries(final Map<String, Object> indices, Document document) {
        List<IndexMapEntry> indexMapEntries = indexMapEntryRepository.findByDocument(document);
        indexMapEntryRepository.delete(indexMapEntries);

        for (String key : indices.keySet()) {
            final Object value = indices.get(key);
            String valueToStore = getStringRepresentation(value);
            IndexMapEntry indexMapEntry = new IndexMapEntry(key, valueToStore.toUpperCase(), document);
            indexMapEntryRepository.save(indexMapEntry);
        }
    }
}
