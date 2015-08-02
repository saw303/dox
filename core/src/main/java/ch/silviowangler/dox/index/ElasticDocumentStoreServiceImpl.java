package ch.silviowangler.dox.index;

import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.TranslatableKey;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created on 02.08.15.
 *
 * @author Silvio Wangler
 * @since 0.5
 */
@Component("elasticDocumentStoreService")
public class ElasticDocumentStoreServiceImpl implements ElasticDocumentStoreService {

    @Autowired
    private Client elasticSearchClient;

    private static final Logger logger = LoggerFactory.getLogger(ElasticDocumentStoreServiceImpl.class);

    @Override
    public void store(DocumentReference documentReference) {

        try {

            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("creationDate", documentReference.getCreationDate())
                    .field("client", documentReference.getClient())
                    .field("documentClass", documentReference.getDocumentClass().getShortName())
                    .field("fileName", documentReference.getFileName())
                    .field("fileSize", documentReference.getFileSize())
                    .field("hash", documentReference.getHash())
                    .field("mimeType", documentReference.getMimeType())
                    .field("pageCount", documentReference.getPageCount())
                    .field("userReference", documentReference.getUserReference())
                    .startArray("indices");

            for (TranslatableKey key : documentReference.getIndices().keySet()) {
                builder.startObject()
                        .field(key.getKey(), String.valueOf(documentReference.getIndices().get(key).getValue()))
                        .endObject();
            }
            builder.endArray().endObject();

            IndexResponse response = elasticSearchClient.prepareIndex("dox", "documents", String.valueOf(documentReference.getId()))
                    .setSource(builder).execute().actionGet();

            logger.debug("Indexed document {}. Created? {}", documentReference.getId(), response.isCreated());

        } catch (IOException e) {
            logger.error("Unable to index document {} in elastic search", documentReference.getId(), e);
        }
    }
}
