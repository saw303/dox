/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
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

package ch.silviowangler.dox.domain.stats;

import com.google.common.collect.Lists;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class ClickStatsRepositoryCustomImpl implements ClickStatsRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DocumentReferenceClickStats> fetchDocumentReferenceClickStatistics() {

        final Query query = em.createQuery("select count(cs.reference), cs.reference from ClickStats cs where cs.referenceType = :referenceType group by cs.reference");
        query.setParameter("referenceType", ReferenceType.DOCUMENT_REFERENCE);

        final List<Object[]> resultList = query.getResultList();
        List<DocumentReferenceClickStats> results = Lists.newArrayListWithCapacity(resultList.size());

        for (Object[] result : resultList) {
            results.add(new DocumentReferenceClickStats(String.valueOf(result[1]), ((Number) result[0]).longValue()));
        }
        return results;
    }
}
