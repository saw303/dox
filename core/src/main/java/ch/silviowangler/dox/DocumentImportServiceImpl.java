package ch.silviowangler.dox;

import ch.silviowangler.dox.api.DocumentImportService;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.PhysicalDocument;
import ch.silviowangler.dox.api.ValdiationException;
import ch.silviowangler.dox.domain.*;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
@Service
public class DocumentImportServiceImpl implements DocumentImportService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DocumentClassRepository documentClassRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public DocumentReference importDocument(PhysicalDocument physicalDocument) throws ValdiationException {

        final String documentClassShortName = physicalDocument.getDocumentClass().getShortName();
        DocumentClass documentClassEntity = documentClassRepository.findByShortName(documentClassShortName);

        if (documentClassEntity == null) {
            logger.error("No such document class with name '{}' found", documentClassShortName);
            throw new ValdiationException("No such document class with name '"+ documentClassShortName + "' available");
        }

        List<Attribute> attributes = attributeRepository.findAttributesForDocumentClass(documentClassEntity);

        verifyMandatoryAttributes(physicalDocument, attributes);

        for(String key : physicalDocument.getIndexes().keySet()) {
            boolean exists = false;
            for (Attribute attribute : attributes ) {
                if (attribute.getShortName().equals(key)) {
                    exists = true;
                    continue;
                }
            }
            if (!exists) {
                throw new ValdiationException("Key " + key + " does not belong to the document class " + documentClassShortName);
            }
        }

        DocumentReference docRef = new DocumentReference(physicalDocument.getFileName());
        docRef.setMimeType("application/pdf");
        final String hash = DigestUtils.sha256Hex(physicalDocument.getContent());
        docRef.setHash(hash);
        docRef.setPageCount(getNumberOfPages(physicalDocument));
        docRef.setId(1L);
        docRef.setDocumentClass(new ch.silviowangler.dox.api.DocumentClass(documentClassEntity.getShortName()));
        docRef.setIndexes(physicalDocument.getIndexes());
        return docRef;
    }

    private void verifyMandatoryAttributes(PhysicalDocument physicalDocument, List<Attribute> attributes) throws ValdiationException {
        for (Attribute attribute : attributes) {
            if (!attribute.isOptional()) {
                if (!physicalDocument.getIndexes().containsKey(attribute.getShortName())) {
                     throw new ValdiationException("Attribute " + attribute.getShortName() + " is mandatory");
                }
            }
        }
    }

    private int getNumberOfPages(PhysicalDocument physicalDocument) {
        PdfReader pdfReader;
        try {
            pdfReader = new PdfReader(physicalDocument.getContent());
        } catch (IOException e) {
            logger.error("Unable to determine the number of pages", e);
            return -1;
        }
        return pdfReader.getNumberOfPages();
    }
}
