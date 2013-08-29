package ch.silviowangler.dox.jobs;

import ch.silviowangler.dox.repository.DocumentKeyHash;
import ch.silviowangler.dox.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import static ch.silviowangler.dox.jobs.Source.DATABASE;
import static ch.silviowangler.dox.jobs.Source.STORE;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@Service("verificationService")
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    private DocumentRepository documentRepository;
    @Value("#{systemEnvironment['DOX_STORE']}")
    private File archiveDirectory;

    private Logger logger = LoggerFactory.getLogger(VerificationServiceImpl.class);

    public void setArchiveDirectory(File archiveDirectory) {
        this.archiveDirectory = archiveDirectory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MissingDocument> verifyDocumentStore() {

        logger.info("Starting to verify document store and database");

        List<MissingDocument> missingDocuments = newArrayList();

        final List<DocumentKeyHash> documentKeyHashes = documentRepository.findAllKeys();
        logger.debug("Found {} document within the database", documentKeyHashes.size());

        final File[] files = archiveDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                final boolean result = pathname.getName().length() == 64;
                logger.trace("Accepting file {}? {}", pathname.getAbsolutePath(), result);
                return result;
            }
        });

        logger.debug("Found {} files in the document store", files.length);

        for (DocumentKeyHash documentKeyHash : documentKeyHashes) {

            logger.trace("Processing database document {} with id {}", documentKeyHash.getHash(), documentKeyHash.getId());

            final File file = new File(this.archiveDirectory, documentKeyHash.getHash());
            if (!file.exists()) {
                logger.warn("Database reference to document '{}' does not exist. Verify document store please", file.getAbsolutePath());
                missingDocuments.add(new MissingDocument(documentKeyHash.getHash(), STORE));
            }
        }

        for (File file : files) {
            if (documentRepository.findByHash(file.getName()) == null) {
                logger.warn("Document in document store '{}' does not exist in the database. Verify document store please", file.getAbsolutePath());
                missingDocuments.add(new MissingDocument(file.getName(), DATABASE));
            }
        }

        logger.info("Done verifying document store and database. Found {} missing documents", missingDocuments.size());

        return missingDocuments;
    }
}
