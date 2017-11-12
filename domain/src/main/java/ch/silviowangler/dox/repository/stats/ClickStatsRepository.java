/**
 * Copyright 2012 - 2017 Silvio Wangler (silvio.wangler@gmail.com)
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
package ch.silviowangler.dox.repository.stats;

import ch.silviowangler.dox.domain.stats.ClickStats;
import ch.silviowangler.dox.domain.stats.ReferenceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public interface ClickStatsRepository extends CrudRepository<ClickStats, Long> {

    @Query("select count(s.id) from ClickStats s where s.referenceType = ch.silviowangler.dox.domain.stats.ReferenceType.DOCUMENT_REFERENCE")
    Long countDocumentReferenceClicks();

    @Query("select count(s.id) from ClickStats s where s.referenceType = ch.silviowangler.dox.domain.stats.ReferenceType.LINK")
    Long countLinkClicks();

    @Query("select count(cs.reference), cs.reference from ClickStats cs where cs.referenceType = :referenceType group by cs.reference")
    List<Object[]> fetchDocumentReferenceClickStatistics(@Param("referenceType") ReferenceType referenceType);
}
