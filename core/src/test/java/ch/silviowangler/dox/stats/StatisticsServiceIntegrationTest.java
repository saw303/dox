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

import ch.silviowangler.dox.AbstractIntegrationTest;
import ch.silviowangler.dox.api.stats.DocumentReferenceClickStats;
import ch.silviowangler.dox.api.stats.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class StatisticsServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private StatisticsService statisticsService;

    @Before
    public void setUp() throws Exception {
        loginAsTestRoot();
    }

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

    @Test
    public void testFetchDocumentReferenceStats() throws Exception {

        final List<DocumentReferenceClickStats> stats = statisticsService.fetchDocumentReferenceClickStats();

        assertThat(stats.size(), is(3));

        for (DocumentReferenceClickStats stat : stats) {
            assertTrue(stat.getDocumentReference().matches("[3-5]"));
            if ("3".equals(stat.getDocumentReference())) assertThat(stat.getCount(), is(3L));
            else if ("4".equals(stat.getDocumentReference())) assertThat(stat.getCount(), is(2L));
            else if ("5".equals(stat.getDocumentReference())) assertThat(stat.getCount(), is(1L));
            else fail("Unknown id");
        }


    }
}
