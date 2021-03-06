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

import static ch.silviowangler.dox.domain.AttributeDataType.CURRENCY;
import static ch.silviowangler.dox.domain.AttributeDataType.DATE;
import static ch.silviowangler.dox.domain.AttributeDataType.DOUBLE;
import static ch.silviowangler.dox.domain.AttributeDataType.INTEGER;
import static ch.silviowangler.dox.domain.AttributeDataType.LONG;
import static ch.silviowangler.dox.domain.AttributeDataType.SHORT;
import static ch.silviowangler.dox.domain.AttributeDataType.STRING;
import static ch.silviowangler.dox.domain.DomainUtils.containsWildcardCharacters;
import static ch.silviowangler.dox.domain.DomainUtils.replaceWildcardCharacters;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;

import ch.silviowangler.dox.api.DescriptiveIndex;
import ch.silviowangler.dox.api.DocumentClassNotFoundException;
import ch.silviowangler.dox.api.DocumentDuplicationException;
import ch.silviowangler.dox.api.DocumentNotFoundException;
import ch.silviowangler.dox.api.DocumentNotInStoreException;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.DocumentService;
import ch.silviowangler.dox.api.Money;
import ch.silviowangler.dox.api.PhysicalDocument;
import ch.silviowangler.dox.api.TranslatableKey;
import ch.silviowangler.dox.api.ValidationException;
import ch.silviowangler.dox.api.ValueNotInDomainException;
import ch.silviowangler.dox.api.rest.DocumentClass;
import ch.silviowangler.dox.document.DocumentInspector;
import ch.silviowangler.dox.document.DocumentInspectorFactory;
import ch.silviowangler.dox.domain.AmountOfMoney;
import ch.silviowangler.dox.domain.Attribute;
import ch.silviowangler.dox.domain.AttributeDataType;
import ch.silviowangler.dox.domain.Client;
import ch.silviowangler.dox.domain.Document;
import ch.silviowangler.dox.domain.IndexMapEntry;
import ch.silviowangler.dox.domain.IndexStore;
import ch.silviowangler.dox.domain.Range;
import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.domain.stats.Tag;
import ch.silviowangler.dox.mappers.DocumentClassMapper;
import ch.silviowangler.dox.repository.AttributeRepository;
import ch.silviowangler.dox.repository.ClientRepository;
import ch.silviowangler.dox.repository.DocumentClassRepository;
import ch.silviowangler.dox.repository.DocumentRepository;
import ch.silviowangler.dox.repository.DomainRepository;
import ch.silviowangler.dox.repository.IndexMapEntryRepository;
import ch.silviowangler.dox.repository.IndexStoreRepository;
import ch.silviowangler.dox.repository.TagRepository;
import ch.silviowangler.dox.repository.security.DoxUserRepository;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService, InitializingBean {

    private static final String DD_MM_YYYY = "dd.MM.yyyy";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String CACHE_DOCUMENT_COUNT = "documentCount";
    public static final int PAGE_NUMBER_NOT_RETRIEVABLE = -1;
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
    @Autowired
    private DocumentInspectorFactory documentInspectorFactory;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DoxUserRepository doxUserRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private DocumentClassMapper documentClassMapper;
   /* @Autowired
    private ElasticDocumentStoreService elasticDocumentStoreService;*/

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(archiveDirectory, "Archive directory must not be null. Please make sure you have properly set environment variable DOX_STORE");
        isTrue(archiveDirectory.isDirectory(), "Archive store must be a directory ['" + this.archiveDirectory + "']");
        isTrue(archiveDirectory.canRead(), "Archive store must be readable ['" + this.archiveDirectory + "']");
        isTrue(archiveDirectory.canWrite(), "Archive store must be writable ['" + this.archiveDirectory + "']");
        notEmpty(mimeTypes, "No mime types have been set");
    }

    @Override
    @Transactional
    public void assignTag(DocumentReference documentReference, String tag) throws NoSuchElementException {
        assignTags(documentReference, tag);
    }

    @Override
    @Transactional
    public void assignTags(DocumentReference documentReference, String... tags) throws NoSuchElementException {

        Document document = documentRepository.findOne(documentReference.getId());

        for (String tagName : tags) {

            Tag tag = tagRepository.findByName(tagName);

            if (tag == null) {
                tag = new Tag(tagName);
                tagRepository.save(tag);
            }
            if (!document.getTags().contains(tag)) {
                document.getTags().add(tag);
            }
        }
        documentRepository.save(document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentClass> findAllDocumentClasses() {

        List<DocumentClass> documentClasses = newArrayList();
        List<String> clients = getClients();

        Iterable<ch.silviowangler.dox.domain.DocumentClass> documentClassEntities = documentClassRepository.findAllByClients(clients);

        for (ch.silviowangler.dox.domain.DocumentClass documentClassEntity : documentClassEntities) {

            DocumentClass documentClass = documentClassMapper.toDocumentClassRestApi(documentClassEntity);
            documentClass.setAttributes(newArrayList(documentClassMapper.toAttributeApi(attributeRepository.findAttributesForDocumentClass(documentClassEntity))));
            documentClasses.add(documentClass);
        }
        return documentClasses;
    }

    @Override
    @Transactional
    public void deleteDocument(Long id) {

        logger.info("About delete document reference with id {}", id);

        notNull(id, "id must not be null");

        Document document = documentRepository.findOne(id);

        if (document != null) {

            User user = getPrincipal();

            if (!user.getUsername().equals(document.getUserReference())) {
                logger.warn("User '{}' tries to delete document with id {} but it belongs to user '{}' and can therefore no be deleted by that user", new Object[]{user.getUsername(), document.getId(), document.getUserReference()});
                throw new AccessDeniedException("Document " + document.getId() + " is not owned by you. You can only delete your own documents");
            }

            indexStoreRepository.delete(document.getIndexStore());
            document.setIndexStore(null);
            documentRepository.delete(id);

            logger.info("Document reference {} successfully deleted", id);
        }
    }

    private User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    @Cacheable(CACHE_DOCUMENT_COUNT)
    @Transactional(propagation = SUPPORTS, readOnly = true)
    public long retrieveDocumentReferenceCount() {
        return documentRepository.count();
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
    public SortedSet<ch.silviowangler.dox.api.Attribute> findAttributes(ch.silviowangler.dox.api.DocumentClass documentClass) throws DocumentClassNotFoundException {

        ch.silviowangler.dox.domain.DocumentClass docClass = findDocumentClass(documentClass.getShortName());
        List<Attribute> attributes = attributeRepository.findAttributesForDocumentClass(docClass);
        return this.documentClassMapper.toAttributeApi(attributes);
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Set<ch.silviowangler.dox.api.DocumentClass> findDocumentClasses() {

        Set<ch.silviowangler.dox.api.DocumentClass> result = new HashSet<>();
        Iterable<ch.silviowangler.dox.domain.DocumentClass> documentClasses = documentClassRepository.findAll();

        for (ch.silviowangler.dox.domain.DocumentClass documentClass : documentClasses) {
            logger.debug("Processing document class '{}' with id {}", documentClass.getShortName(), documentClass.getId());
            result.add(this.documentClassMapper.toDocumentClassApi(documentClass));
        }
        logger.info("Found {} document classes in DOX", result.size());
        return result;
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<DocumentReference> findDocumentReferences(String queryString) {
        return findDocumentReferencesInternal(queryString, null, null);
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<DocumentReference> findDocumentReferences(String queryString, Locale locale) {
        return findDocumentReferencesInternal(queryString, null, locale);
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<DocumentReference> findDocumentReferencesForCurrentUser(String queryString) {
        User user = getPrincipal();
        return findDocumentReferencesInternal(queryString, user.getUsername(), null);
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<DocumentReference> findDocumentReferencesForCurrentUser(String queryString, Locale locale) {
        User user = getPrincipal();
        return findDocumentReferencesInternal(queryString, user.getUsername(), locale);
    }

    private List<DocumentReference> findDocumentReferencesInternal(String queryString, String username, Locale locale) {
        logger.debug("About to find document references for query string '{}'", queryString);

        List<Document> documents;
        List<String> clients = getClients();

        if (username == null) {
            if (containsWildcardCharacters(queryString)) {
                String value = replaceWildcardCharacters(queryString);
                documents = indexMapEntryRepository.findByValueLike(value.toUpperCase(), value, clients);
            } else {
                documents = indexMapEntryRepository.findByValue(queryString.toUpperCase(), queryString, clients);
            }
        } else {
            if (containsWildcardCharacters(queryString)) {
                String value = replaceWildcardCharacters(queryString);
                documents = indexMapEntryRepository.findByValueLikeAndUserReference(value.toUpperCase(), value, username, clients);
            } else {
                documents = indexMapEntryRepository.findByValueAndUserReference(queryString.toUpperCase(), queryString, username, clients);
            }
        }

        List<DocumentReference> documentReferences = newArrayListWithCapacity(documents.size());

        logger.info("Found {} documents for query string '{}'", documents.size(), queryString);

        for (Document document : documents) {
            logger.trace("Found document with id {}", document.getId());
            documentReferences.add(toDocumentReference(document, locale));
        }
        return documentReferences;
    }

    private List<String> getClients() {
        User user = getPrincipal();
        DoxUser doxUser = doxUserRepository.findByUsername(user.getUsername());

        List<String> clients = newArrayListWithCapacity(doxUser.getClients().size());

        for (Client client : doxUser.getClients()) {
            clients.add(client.getShortName());
        }
        return clients;
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Set<DocumentReference> findDocumentReferences(Map<TranslatableKey, DescriptiveIndex> queryParams, String documentClassShortName) throws DocumentClassNotFoundException {

        logger.debug("Trying to find document references in document class '{}' using params '{}'", documentClassShortName, queryParams);
        ch.silviowangler.dox.domain.DocumentClass documentClass = findDocumentClass(documentClassShortName);
        List<Attribute> attributes = attributeRepository.findAttributesForDocumentClass(documentClass);

        Map<String, Object> indices = this.documentClassMapper.toEntityMap(fixDataTypesOfIndices(queryParams, attributes));
        List<Document> documents = documentRepository.findDocuments(indices, toAttributeMap(attributes), documentClass);

        HashSet<DocumentReference> documentReferences = new HashSet<>(documents.size());
        for (Document document : documents) {
            logger.trace("Found document with id {}", document.getId());
            documentReferences.add(toDocumentReference(document, null));
        }
        return documentReferences;
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public DocumentReference findDocumentReference(Long id) throws DocumentNotFoundException {
        logger.info("About to find document by using id {}", id);

        if (documentReferenceExists(id)) {
            logger.info("Found document for id {}", id);
            Document document = documentRepository.findOne(id);

            DocumentReference documentReference = toDocumentReference(document, null);

            return documentReference;
        } else {
            logger.warn("No document found for id {}", id);
            throw new DocumentNotFoundException(id);
        }
    }

    @Override
    @CacheEvict(value = CACHE_DOCUMENT_COUNT, allEntries = true)
    @Transactional(propagation = REQUIRED, readOnly = false)
    @PreAuthorize("hasRole('ROLE_USER')")
    public DocumentReference importDocument(PhysicalDocument physicalDocumentApi) throws ValidationException, DocumentDuplicationException, DocumentClassNotFoundException {

        final String documentClassShortName = physicalDocumentApi.getDocumentClass().getShortName();
        ch.silviowangler.dox.domain.DocumentClass documentClassEntity = findDocumentClass(documentClassShortName);

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

        IndexStore indexStore = new IndexStore();
        updateIndices(physicalDocumentApi, indexStore);

        User user = getPrincipal();

        Document document = new Document(hash, documentClassEntity, PAGE_NUMBER_NOT_RETRIEVABLE, mimeType, physicalDocumentApi.getFileName(), indexStore, user.getUsername());
        document.setClient(clientRepository.findByShortName(physicalDocumentApi.getClient()));
        indexStore.setDocument(document);

        document = documentRepository.save(document);
        indexStoreRepository.save(indexStore);

        updateIndexMapEntries(this.documentClassMapper.toEntityMap(physicalDocumentApi.getIndices()), document);

        File target = new File(this.archiveDirectory, hash);
        try {
            FileUtils.writeByteArrayToFile(target, physicalDocumentApi.getContent());
        } catch (IOException e) {
            logger.error("Unable to write file to store at {}", this.archiveDirectory.getAbsolutePath(), e);
        }

        try {
            final long size = Files.size(target.toPath());
            document.setFileSize(size);

            final DocumentInspector documentInspector = documentInspectorFactory.findDocumentInspector(mimeType);
            final int numberOfPages = documentInspector.retrievePageCount(target);

            logger.debug("File '{}' contains {} pages (detected by document inspector '{}')", target.getName(), numberOfPages, documentInspector.getClass().getName());
            document.setPageCount(numberOfPages);

            document = documentRepository.save(document);
        } catch (IOException e) {
            logger.error("Unable to calculate file size of file {}", target.getAbsolutePath(), e);
        }
        DocumentReference docRef = toDocumentReference(document, null);
        return docRef;
    }

    @Override
    @Transactional(propagation = REQUIRED, readOnly = false)
    @PreAuthorize("hasRole('ROLE_USER')")
    public DocumentReference updateIndices(DocumentReference reference) throws DocumentNotFoundException {

        DocumentReference documentReferenceApi = findDocumentReference(reference.getId());
        documentReferenceApi.getIndices().putAll(reference.getIndices());

        Document document = documentRepository.findOne(reference.getId());
        ch.silviowangler.dox.domain.DocumentClass documentClassEntity = document.getDocumentClass();

        List<Attribute> attributes = attributeRepository.findAttributesForDocumentClass(documentClassEntity);

        documentReferenceApi.setIndices(fixDataTypesOfIndices(documentReferenceApi.getIndices(), attributes));

        updateIndices(documentReferenceApi, document.getIndexStore());

        indexStoreRepository.save(document.getIndexStore());

        updateIndexMapEntries(this.documentClassMapper.toEntityMap(documentReferenceApi.getIndices()), document);

        return findDocumentReference(reference.getId());
    }

    @Override
    @Transactional(propagation = SUPPORTS, readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Set<DocumentReference> retrieveAllDocumentReferences() {
        logger.info("Retrieving all document references from repository");
        Iterable<Document> documents = documentRepository.findAll();
        Set<DocumentReference> documentReferences = Sets.newHashSet();

        for (Document document : documents) {
            logger.debug("Processing document {}", document);
            documentReferences.add(toDocumentReference(document, null));
        }
        logger.info("Done retrieving all document references from repository. Fetched {} document references", documentReferences.size());
        return documentReferences;
    }

    private void verifyDomainValues(PhysicalDocument physicalDocument, List<Attribute> attributes) throws ValueNotInDomainException {
        for (Attribute attribute : attributes) {
            final String attributeShortName = attribute.getShortName();
            final TranslatableKey key = new TranslatableKey(attributeShortName);
            if (attribute.getDomain() != null && physicalDocument.getIndices().containsKey(key)) {
                final String attributeValue = String.valueOf(physicalDocument.getIndices().get(key).getValue());
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

    private Map<TranslatableKey, DescriptiveIndex> fixDataTypesOfIndices(final Map<TranslatableKey, DescriptiveIndex> indexes, List<Attribute> attributes) {

        Map<TranslatableKey, DescriptiveIndex> resultMap = Maps.newHashMap(indexes); // copy elements

        for (Attribute attribute : attributes) {

            final TranslatableKey key = new TranslatableKey(attribute.getShortName());
            if (resultMap.containsKey(key) && resultMap.get(key).getValue() != null) {
                if (!isAssignableType(attribute.getDataType(), resultMap.get(key).getValue().getClass())) {
                    logger.debug("Attribute '{}' is not assignable to '{}'", key, attribute.getDataType());
                    resultMap.put(key, new DescriptiveIndex(makeAssignable(attribute.getDataType(), resultMap.get(key).getValue())));
                }
            } else {
                logger.debug("Ignoring attribute '{}' since it was not mentioned in the index map", key);
            }
        }
        return resultMap;
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
        } else if (CURRENCY.equals(desiredDataType) && valueToConvert instanceof String) {
            String value = (String) valueToConvert;
            return new AmountOfMoney(value);
        } else if (CURRENCY.equals(desiredDataType) && valueToConvert instanceof Money) {
            Money money = (Money) valueToConvert;
            return new AmountOfMoney(money.getCurrency(), money.getAmount());
        } else if (CURRENCY.equals(desiredDataType) && valueToConvert instanceof Map) {
            Map money = (Map) valueToConvert;
            return new AmountOfMoney(Currency.getInstance((String) money.get("currency")), new BigDecimal((String) money.get("amount")));
        }


        logger.error("Unable to convert data type '{}' and value '{}' (class: '{}')", new Object[]{desiredDataType, valueToConvert, valueToConvert.getClass().getCanonicalName()});
        throw new IllegalArgumentException("Unable to convert data type '" + desiredDataType + "' and value '" + valueToConvert + "' (Target class: '" + valueToConvert.getClass().getCanonicalName() + "')");
    }

    private boolean isRangeCompatible(AttributeDataType desiredDataType) {
        return newArrayList(DATE, DOUBLE, INTEGER, LONG, SHORT).contains(desiredDataType);
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

            final Object value = documentReference.getIndices().get(key).getValue();
            try {
                final String propertyName = attribute.getMappingColumn().toLowerCase();
                logger.debug("About to set column '{}' using value '{}' on index store", propertyName, value);
                setFieldValue(indexStore, propertyName, value);
            } catch (IllegalAccessException | NoSuchFieldException e) {
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

    private boolean documentReferenceExists(Long id) {
        return documentRepository.exists(id);
    }

    private DocumentReference toDocumentReference(Document document, Locale locale) {
        final DocumentReference documentReference = new DocumentReference(document.getHash(),
                document.getId(),
                document.getPageCount(),
                document.getMimeType(),
                this.documentClassMapper.toDocumentClassApi(document.getDocumentClass()),
                toIndexMap(document.getIndexStore(), attributeRepository.findAttributesForDocumentClass(document.getDocumentClass()), locale),
                document.getOriginalFilename(), document.getUserReference(), document.getFileSize());

        documentReference.setCreationDate(document.getCreationDate());
        documentReference.setClient(document.getClient().getShortName());

        for (Tag tag : document.getTags()) {
            documentReference.getTags().add(tag.getName());
        }

        return documentReference;
    }

    private Map<TranslatableKey, DescriptiveIndex> toIndexMap(IndexStore indexStore, List<Attribute> attributes, Locale locale) {

        Map<TranslatableKey, DescriptiveIndex> indices = newHashMapWithExpectedSize(attributes.size());

        for (Attribute attribute : attributes) {

            DescriptiveIndex index = new DescriptiveIndex();
            index.setAttribute(documentClassMapper.toAttributeApi(attribute));

            try {
                final String fieldName = attribute.getMappingColumn();
                final Object propertyValue = getFieldValue(indexStore, fieldName);

                final TranslatableKey key = new TranslatableKey(attribute.getShortName());
                if (attribute.getDataType() == CURRENCY) {
                    AmountOfMoney amountOfMoney = (AmountOfMoney) propertyValue;
                    if (locale == null) {
                        index.setValue((amountOfMoney == null) ? null : new Money(amountOfMoney.getCurrency(), amountOfMoney.getAmount()));
                    } else {
                        index.setValue((amountOfMoney == null) ? null : amountOfMoney.getCurrency() + " " + amountOfMoney.getAmount());
                    }
                } else {
                    index.setValue(propertyValue);
                }
                indices.put(key, index);

            } catch (IllegalAccessException | NoSuchFieldException e) {
                logger.error("Error setting property '{}'", attribute.getShortName(), e);
            }
        }
        return indices;
    }

    private Object getFieldValue(IndexStore indexStore, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = indexStore.getClass().getDeclaredField(fieldName.toUpperCase());
        field.setAccessible(true);
        return field.get(indexStore);
    }

    private void setFieldValue(IndexStore indexStore, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        final Field field = indexStore.getClass().getDeclaredField(fieldName.toUpperCase());
        field.setAccessible(true);

        if (field.getType() == LocalDate.class && value.getClass() == DateTime.class) {
            field.set(indexStore, ((DateTime) value).toLocalDate());
        } else {
            field.set(indexStore, value);
        }

    }

    private Map<String, Attribute> toAttributeMap(List<Attribute> attributes) {
        Map<String, Attribute> map = newHashMapWithExpectedSize(attributes.size());

        for (Attribute attribute : attributes) {
            map.put(attribute.getShortName(), attribute);
        }
        return map;
    }

    private ch.silviowangler.dox.domain.DocumentClass findDocumentClass(String documentClassShortName) throws DocumentClassNotFoundException {
        ch.silviowangler.dox.domain.DocumentClass documentClassEntity = documentClassRepository.findByShortName(documentClassShortName);

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
