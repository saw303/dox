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

package ch.silviowangler.dox.web;

import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.DocumentService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.ImmutableMap.of;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 16.07.12 09:47
 *        </div>
 */
@Controller
public class HomeController {

    @Autowired
    private DocumentService documentService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = POST, value = "query.html")
    public ModelAndView query(@RequestParam("q") String queryString, @RequestParam(value = "wildcard", defaultValue = "0", required = false) boolean useWildcard) {

        final boolean hasWildcard = containsWildcard(queryString);
        String queryStringCopy = queryString;

        logger.trace("Received query request '{}'. Contains wildcards? {}", queryString, hasWildcard);

        if (useWildcard && !hasWildcard) {
            logger.debug("Going to overwrite query string '{}' because wildcard searching is activated", queryString);
            queryStringCopy = "*" + queryString + "*";
            logger.debug("Using wildcard search '{}'", queryStringCopy);
        }

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryStringCopy);
        Map<String, Object> model = of("documents", documentReferences, "query", queryString);
        return new ModelAndView("result.definition", model);
    }

    @RequestMapping(method = POST, value = "advanced.html")
    public ModelAndView advancedQuery() {

        ImmutableMap<String, Serializable> model = of("documents", Lists.newArrayList(), "query", "custom");
        return new ModelAndView("result.definition", model);
    }

    private boolean containsWildcard(final String queryString) {
        return queryString.contains("*") || queryString.contains("?");
    }
}
