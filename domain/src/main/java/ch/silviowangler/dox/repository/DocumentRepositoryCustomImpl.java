/**
 * Copyright 2012 - 2018 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.silviowangler.dox.repository;

import ch.silviowangler.dox.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ch.silviowangler.dox.domain.DomainUtils.containsWildcardCharacters;
import static ch.silviowangler.dox.domain.DomainUtils.replaceWildcardCharacters;

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
    @SuppressWarnings("unchecked")
    public List<Document> findDocuments(Map<String, Object> indices, Map<String, Attribute> attributes, DocumentClass documentClass) {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Document> query = cb.createQuery(Document.class);
        Root<Document> document = query.from(Document.class);

        CriteriaQuery<Document> select = query.select(document);

        List<Predicate> criterias = new ArrayList<>();
        criterias.add(cb.equal(document.get("documentClass"), documentClass));

        for (String key : indices.keySet()) {
            Object value = indices.get(key);

            if (attributes.containsKey(key)) {
                Attribute attribute = attributes.get(key);

                if (AttributeDataType.STRING.equals(attribute.getDataType())) {
                    String castedStringValue = (String) value;

                    if (containsWildcardCharacters(castedStringValue)) {
                        final String wildcardValue = replaceWildcardCharacters(castedStringValue);
                        logger.debug("Like on '{}' using value '{}'", attribute.getMappingColumn(), wildcardValue);
                        criterias.add(cb.and(cb.like(document.join("indexStore").<String>get(attribute.getMappingColumn()), wildcardValue)));
                    } else {
                        criterias.add(cb.and(cb.equal(document.join("indexStore").<String>get(attribute.getMappingColumn()), castedStringValue)));
                    }
                } else if (AttributeDataType.DOUBLE.equals(attribute.getDataType())) {
                    logger.debug("Setting attribute '{}' with value '{}' (data type: {})", new Object[]{attribute.getShortName(), value, value.getClass().getCanonicalName()});

                    if (value instanceof Range) {
                        Range<BigDecimal> range = (Range<BigDecimal>) value;
                        criterias.add(cb.and(cb.between(
                                document.join("indexStore").<BigDecimal>get(attribute.getMappingColumn()),
                                range.getFrom(),
                                range.getTo())));
                    } else {
                        criterias.add(cb.and(cb.equal(document.join("indexStore").<String>get(attribute.getMappingColumn()), value)));
                    }
                }
            } else {
                logger.warn("Ignoring key '{}' since it is no global attribute", key);
            }
        }
        select.where(criterias.toArray(new Predicate[]{}));

        final List<Document> resultList = em.createQuery(query).getResultList();
        logger.info("Found {} documents in index store", resultList.size());
        return resultList;
    }
}
