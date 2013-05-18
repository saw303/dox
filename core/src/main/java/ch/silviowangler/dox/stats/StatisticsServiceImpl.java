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

package ch.silviowangler.dox.stats;

import ch.silviowangler.dox.api.stats.DocumentReferenceClickStats;
import ch.silviowangler.dox.api.stats.StatisticsService;
import ch.silviowangler.dox.domain.stats.ClickStats;
import ch.silviowangler.dox.domain.stats.ClickStatsRepository;
import ch.silviowangler.dox.domain.stats.ClickStatsRepositoryCustom;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ch.silviowangler.dox.domain.stats.ReferenceType.DOCUMENT_REFERENCE;
import static ch.silviowangler.dox.domain.stats.ReferenceType.LINK;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private ClickStatsRepository clickStatsRepository;
    @Autowired
    private ClickStatsRepositoryCustom clickStatsRepositoryCustom;

    @Override
    @Transactional(readOnly = true, propagation = SUPPORTS)
    public Long fetchDocumentReferenceClicksCount() {
        return clickStatsRepository.countDocumentReferenceClicks();
    }

    @Override
    @Transactional(readOnly = true, propagation = SUPPORTS)
    public Long fetchLinkClicksCount() {
        return clickStatsRepository.countLinkClicks();
    }

    @Override
    @Transactional
    public void registerDocumentReferenceClick(String documentReferenceId, String username) {
        ClickStats stats = new ClickStats(documentReferenceId, DOCUMENT_REFERENCE, username);
        clickStatsRepository.save(stats);
    }

    @Override
    @Transactional
    public void registerLinkClick(String link, String username) {
        ClickStats stats = new ClickStats(link, LINK, username);
        clickStatsRepository.save(stats);
    }

    @Override
    @Transactional(readOnly = true, propagation = SUPPORTS)
    public List<DocumentReferenceClickStats> fetchDocumentReferenceClickStats() {

        final List<ch.silviowangler.dox.domain.stats.DocumentReferenceClickStats> documentReferenceClickStatses = clickStatsRepositoryCustom.fetchDocumentReferenceClickStatistics();
        List<DocumentReferenceClickStats> result = Lists.newArrayListWithCapacity(documentReferenceClickStatses.size());

        for (ch.silviowangler.dox.domain.stats.DocumentReferenceClickStats stats : documentReferenceClickStatses) {
            result.add(new DocumentReferenceClickStats(stats.getDocumentReference(), stats.getCount()));
        }
        return result;
    }
}
