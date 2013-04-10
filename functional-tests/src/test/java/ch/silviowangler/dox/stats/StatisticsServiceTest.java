/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.stats;

import ch.silviowangler.dox.AbstractTest;
import ch.silviowangler.dox.api.stats.StatisticsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class StatisticsServiceTest extends AbstractTest {

    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void storeClick() {

        Long countBefore = statisticsService.fetchDocumentReferenceClicksCount();
        statisticsService.registerDocumentReferenceClick("45478787dsafdf8", "saw303");
        assertThat(statisticsService.fetchDocumentReferenceClicksCount(), is(countBefore + 1));
    }

    @Test
    public void storeClickWithDifferentTypes() {

        Long docRefCountBefore = statisticsService.fetchDocumentReferenceClicksCount();
        Long linkCountBefore = statisticsService.fetchLinkClicksCount();
        statisticsService.registerDocumentReferenceClick("45478787dsafdf8", "saw303");
        statisticsService.registerLinkClick("aaaa", "saw303");
        assertThat(statisticsService.fetchDocumentReferenceClicksCount(), is(docRefCountBefore + 1));
        assertThat(statisticsService.fetchLinkClicksCount(), is(linkCountBefore + 1));
    }
}
