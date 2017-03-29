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
package ch.silviowangler.dox.web;

import static com.google.common.collect.Maps.newHashMap;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import ch.silviowangler.dox.api.MissingDocument;
import ch.silviowangler.dox.api.Source;
import ch.silviowangler.dox.api.VerificationService;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@Controller
public class MaintenanceController {

    @Autowired
    private VerificationService verificationService;

    @RequestMapping(method = RequestMethod.GET, value = "/maintenance")
    public ModelAndView index() {
        Map<String, List<MissingDocument>> model = newHashMap();

        final String database = "database";
        final String store = "store";

        model.put(database, Lists.<MissingDocument>newArrayList());
        model.put(store, Lists.<MissingDocument>newArrayList());

        final List<MissingDocument> missingDocuments = verificationService.verifyDocumentStore();

        for (MissingDocument missingDocument : missingDocuments) {
            if (missingDocument.getSource() == Source.DATABASE) {
                model.get(database).add(missingDocument);
            } else if (missingDocument.getSource() == Source.STORE) {
                model.get(store).add(missingDocument);
            } else {
                throw new IllegalStateException("Unknown enum type " + missingDocument.getSource());
            }
        }
        return new ModelAndView("maintenance", model);
    }
}
