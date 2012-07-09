package ch.silviowangler.dox;

import ch.silviowangler.dox.api.*;
import ch.silviowangler.dox.domain.*;
import ch.silviowangler.dox.domain.DocumentClass;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DocumentClassRepository documentClassRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private IndexStoreRepository indexStoreRepository;

    @Override
    public DocumentReference findDocumentReference(Long id) throws DocumentNotFoundException {
        logger.info("About to find document by using id {}", id);

        if (documentRepository.exists(id)) {
            logger.info("Found document for id {}", id);
            Document document = documentRepository.findOne(id);

            DocumentReference documentReference = toDocumentReference(document);

            return documentReference;
        } else {
            logger.warn("No document found for id {}", id);
            throw new DocumentNotFoundException(id);
        }
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

    private Map<String, Object> toIndexMap(IndexStore indexStore, List<Attribute> attributes) {

        Map<String, Object> indices = new HashMap<String, Object>(attributes.size());

        for (Attribute attribute : attributes) {
            try {
                indices.put(attribute.getShortName(), PropertyUtils.getProperty(indexStore, attribute.getMappingColumn().toLowerCase()));
            } catch (IllegalAccessException e) {
                logger.error("Error setting property '{}'", attribute.getShortName(), e);
            } catch (InvocationTargetException e) {
                logger.error("Error setting property '{}'", attribute.getShortName(), e);
            } catch (NoSuchMethodException e) {
                logger.error("Error setting property '{}'", attribute.getShortName(), e);
            }
        }
        return indices;
    }

    private ch.silviowangler.dox.api.DocumentClass toDocumentClassApi(DocumentClass documentClass) {
        return new ch.silviowangler.dox.api.DocumentClass(documentClass.getShortName());
    }

    @Override
    public DocumentReference importDocument(PhysicalDocument physicalDocument) throws ValdiationException {

        final String documentClassShortName = physicalDocument.getDocumentClass().getShortName();
        DocumentClass documentClassEntity = documentClassRepository.findByShortName(documentClassShortName);

        if (documentClassEntity == null) {
            logger.error("No such document class with name '{}' found", documentClassShortName);
            throw new ValdiationException("No such document class with name '" + documentClassShortName + "' available");
        }

        List<Attribute> attributes = attributeRepository.findAttributesForDocumentClass(documentClassEntity);

        logger.debug("Found {} attributes for document class '{}'", attributes.size(), documentClassShortName);

        verifyMandatoryAttributes(physicalDocument, attributes);
        verifyUnknownKeys(physicalDocument, documentClassShortName, attributes);

        final String hash = DigestUtils.sha256Hex(physicalDocument.getContent());
        final int numberOfPages = getNumberOfPages(physicalDocument);

        IndexStore indexStore = new IndexStore();

        for (String key : physicalDocument.getIndexes().keySet()) {

            Attribute attribute = attributeRepository.findByShortName(key);
            assert attribute != null : "Attribute " + key + " must be there";

            final Object value = physicalDocument.getIndexes().get(key);
            try {
                final String propertyName = attribute.getMappingColumn().toLowerCase();
                logger.debug("About to set column '{}' using value '{}' on index store", propertyName, value);
                PropertyUtils.setProperty(indexStore, propertyName, value);
            } catch (IllegalAccessException e) {
                logger.error("Error setting property '{}' with value '{}'", new Object[]{key, value, e});
            } catch (InvocationTargetException e) {
                logger.error("Error setting property '{}' with value '{}'", new Object[]{key, value, e});
            } catch (NoSuchMethodException e) {
                logger.error("Error setting property '{}' with value '{}'", new Object[]{key, value, e});
            }
        }
        indexStore = indexStoreRepository.save(indexStore);

        Document document = new Document(hash, documentClassEntity, numberOfPages, "application/pdf", physicalDocument.getFileName(), indexStore);
        document = documentRepository.save(document);

        DocumentReference docRef = toDocumentReference(document);
        return docRef;
    }

    private void verifyUnknownKeys(PhysicalDocument physicalDocument, String documentClassShortName, List<Attribute> attributes) throws ValdiationException {
        for (String key : physicalDocument.getIndexes().keySet()) {
            boolean exists = false;
            for (Attribute attribute : attributes) {
                if (attribute.getShortName().equals(key)) {
                    exists = true;
                    continue;
                }
            }
            if (!exists) {
                logger.warn("Key '{}' does not belong to document class '{}'", documentClassShortName);
                throw new ValdiationException("Key " + key + " does not belong to document class " + documentClassShortName);
            }
        }
    }

    private void verifyMandatoryAttributes(PhysicalDocument physicalDocument, List<Attribute> attributes) throws ValdiationException {
        for (Attribute attribute : attributes) {
            if (!attribute.isOptional()) {
                if (!physicalDocument.getIndexes().containsKey(attribute.getShortName())) {
                    logger.warn("Attribute '{}' is required for document class(es) '{}' and not was not provided.", attribute.getShortName(), attribute.getDocumentClasses());
                    throw new ValdiationException("Attribute " + attribute.getShortName() + " is mandatory");
                }
            }
        }
    }

    private int getNumberOfPages(final PhysicalDocument physicalDocument) {
        PdfReader pdfReader;
        try {
            pdfReader = new PdfReader(physicalDocument.getContent());
        } catch (IOException e) {
            logger.error("Unable to determine the number of pages", e);
            return -1;
        }
        final int numberOfPages = pdfReader.getNumberOfPages();
        logger.debug("Found {} page(s) for physical document {}", numberOfPages, pdfReader);
        return numberOfPages;
    }
}
