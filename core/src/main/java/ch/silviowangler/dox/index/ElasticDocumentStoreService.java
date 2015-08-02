package ch.silviowangler.dox.index;

import ch.silviowangler.dox.api.DocumentReference;

/**
 * Created on 02.08.15.
 *
 * @author Silvio Wangler
 * @since 0.5
 */
public interface ElasticDocumentStoreService {

    void store(DocumentReference documentReference);
}
