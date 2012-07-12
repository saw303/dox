package ch.silviowangler.dox.domain;

import java.util.List;
import java.util.Map;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 11.07.12 13:07
 *        </div>
 */
public interface DocumentRepositoryCustom {

    List<Document> findDocuments(Map<String, Object> indices, Map<String, Attribute> attributes);
}
