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
package ch.silviowangler.dox.web.admin;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ch.silviowangler.dox.api.stats.DocumentReferenceClickStats;
import ch.silviowangler.dox.api.stats.StatisticsService;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
@Controller
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/admin/stats/documentReferences", method = GET)
    @ResponseStatus(OK)
    public @ResponseBody List<DocumentReferenceClickStats> showDocumentReferenceStats() {

        List<DocumentReferenceClickStats> stats = statisticsService.fetchDocumentReferenceClickStats();

        Collections.sort(stats, new Comparator<DocumentReferenceClickStats>() {
            @Override
            public int compare(DocumentReferenceClickStats o1, DocumentReferenceClickStats o2) {
                return Long.valueOf(o2.getCount()).compareTo(o1.getCount());
            }
        });

        return stats;
    }
}
