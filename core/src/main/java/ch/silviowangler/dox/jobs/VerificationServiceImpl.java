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

        List<MissingDocument> missingDocuments = newArrayList();

        final List<DocumentKeyHash> documentKeyHashes = documentRepository.findAllKeys();

        final File[] files = archiveDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().length() == 64;
            }
        });

        for (DocumentKeyHash documentKeyHash : documentKeyHashes) {

            final File file = new File(this.archiveDirectory, documentKeyHash.getHash());
            if (!file.exists()) {
                missingDocuments.add(new MissingDocumentInStore(documentKeyHash.getHash(), file.toPath()));
            }
        }
        return missingDocuments;
    }
}
