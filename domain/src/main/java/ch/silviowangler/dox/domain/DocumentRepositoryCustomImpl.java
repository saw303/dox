package ch.silviowangler.dox.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 11.07.12 13:07
 *        </div>
 */
public class DocumentRepositoryCustomImpl implements DocumentRepositoryCustom {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Document> findDocuments(Map<String, Object> indices, Map<String, Attribute> attributes) {

        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Document> query = criteriaBuilder.createQuery(Document.class);
        Root<Document> document = query.from(Document.class);

        CriteriaQuery<Document> select = query.select(document);

        for (String key : indices.keySet()) {
            Object value = indices.get(key);

            if (attributes.containsKey(key)) {
                Attribute attribute = attributes.get(key);

                if (AttributeDataType.STRING.equals(attribute.getDataType())) {
                    String castedStringValue = (String) value;

                    if (containsWildcardCharacters(castedStringValue)) {
                        final String wildcardValue = replaceWildcardCharacters(castedStringValue);
                        logger.debug("Like on '{}' using value '{}'", attribute.getMappingColumn(), wildcardValue);
                        select.where(criteriaBuilder.like(document.join("indexStore").<String>get(attribute.getMappingColumn()), wildcardValue));
                    } else {
                        select.where(criteriaBuilder.equal(document.join("indexStore").<String>get(attribute.getMappingColumn()), castedStringValue));
                    }
                }
            } else {
                logger.warn("Ignoring key '{}' since it is no global attribute", key);
            }
        }

        final List<Document> resultList = em.createQuery(query).getResultList();

        logger.info("Found {} index stores", resultList.size());

        if (!resultList.isEmpty()) {
            return resultList;
        }
        return new ArrayList();
    }

    private String replaceWildcardCharacters(String value) {
        return value.replace("*", "%").replace("?", "%");
    }

    private boolean containsWildcardCharacters(final String value) {
        return value.contains("*") || value.contains("?");
    }

    private void logIndexStoresIfDebugIsEnabled(List<IndexStore> resultList) {
        if (logger.isDebugEnabled()) {
            for (IndexStore indexStore : resultList) {
                logger.debug("Found index store '{}'", indexStore);
            }
        }
    }
}
