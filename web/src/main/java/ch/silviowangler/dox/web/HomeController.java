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

import ch.silviowangler.dox.api.DocumentClassNotFoundException;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.DocumentService;
import ch.silviowangler.dox.api.TranslatableKey;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static ch.silviowangler.dox.web.WebConstants.DOCUMENT_CLASS_SHORT_NAME;
import static com.google.common.collect.ImmutableMap.of;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
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
    @Value("#{systemEnvironment['DOX_STORE']}")
    private File archiveDirectory;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = GET, value = "/")
    public ModelAndView homeScreen(@RequestParam(value = "q", defaultValue = "", required = false) String query) {

        Map<String, Object> model = newHashMapWithExpectedSize(1);
        model.put("query", HtmlUtils.htmlUnescape(query));

        return new ModelAndView("base.definition", model);
    }

    @RequestMapping(method = POST, value = "extendedQuery.html")
    public ModelAndView advancedQuery(@RequestParam(DOCUMENT_CLASS_SHORT_NAME) String documentClassShortName, WebRequest request) {

        try {

            Map<TranslatableKey, Object> indices = newHashMap();

            final Iterator<String> iterator = request.getParameterNames();

            while (iterator.hasNext()) {
                final String parameterName = iterator.next();
                if (!DOCUMENT_CLASS_SHORT_NAME.equals(parameterName)) {
                    String value = request.getParameter(parameterName);

                    if (value.length() > 0) {
                        indices.put(new TranslatableKey(parameterName), value);
                    }
                }
            }

            final Set<DocumentReference> documentReferences = documentService.findDocumentReferences(indices, documentClassShortName);
            ImmutableMap<String, Object> model = of("documents", documentReferences, "query", "custom");
            return new ModelAndView("result.definition", model);
        } catch (DocumentClassNotFoundException e) {
            logger.error("Illegal document class short name {}", documentClassShortName, e);
            return new ModelAndView("result.definition");
        }
    }
}
