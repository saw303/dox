package ch.silviowangler.dox.domain;

import ch.silviowangler.dox.api.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
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
                } else if (AttributeDataType.DOUBLE.equals(attribute.getDataType())) {
                    logger.debug("Setting attribute '{}' with value '{}' (data type: {})", new Object[]{attribute.getShortName(), value, value.getClass().getCanonicalName()});

                    if (value instanceof Range) {
                        Range<BigDecimal> range = (Range<BigDecimal>) value;
                        select.where(criteriaBuilder.between(
                                document.join("indexStore").<BigDecimal>get(attribute.getMappingColumn()),
                                range.getFrom(),
                                range.getTo()));
                    } else {
                        select.where(criteriaBuilder.equal(document.join("indexStore").<String>get(attribute.getMappingColumn()), value));
                    }
                }
            } else {
                logger.warn("Ignoring key '{}' since it is no global attribute", key);
            }
        }

        final List<Document> resultList = em.createQuery(query).getResultList();
        logger.info("Found {} index stores", resultList.size());
        return resultList;
    }

    private String replaceWildcardCharacters(String value) {
        return value.replaceAll("(\\*|\\?)", "%");
    }

    private boolean containsWildcardCharacters(final String value) {
        return value.contains("*") || value.contains("?");
    }
}
