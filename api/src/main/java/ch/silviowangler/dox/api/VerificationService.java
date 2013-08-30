package ch.silviowangler.dox.api;

import java.util.List;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public interface VerificationService {

    /**
     * Verifies the DOX document store and reports missing documents (either in the database or in the document store)
     *
     * @return a list of missing documents
     */
    List<MissingDocument> verifyDocumentStore();
}
