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

import ch.silviowangler.dox.api.*;
import ch.silviowangler.dox.web.util.TemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static org.springframework.util.Assert.notNull;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 17.07.12 08:22
 *        </div>
 */
@Controller
public class ImportController implements MessageSourceAware, InitializingBean {

    private static final String DOCUMENT_CLASS_SHORT_NAME = "documentClassShortName";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DocumentService documentService;
    @Autowired
    private TemplateEngine templateEngine;
    private MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.messageSource, "Message source must not be null");
    }

    @RequestMapping(method = RequestMethod.GET, value = "showForm.html")
    public ModelAndView query(Locale locale, @RequestParam(value = "advancedQuery", defaultValue = "0", required = false) boolean isAdvancedQuery) {
        Map<String, Object> model = newHashMap();
        ArrayList<DocumentClass> documentClasses = newArrayList(documentService.findDocumentClasses());
        Collections.sort(documentClasses, new Comparator<DocumentClass>() {
            @Override
            public int compare(DocumentClass o1, DocumentClass o2) {
                return o1.getTranslation().compareTo(o2.getTranslation());
            }
        });
        model.put("documentClasses", documentClasses);
        model.put("defaultMessage", messageSource.getMessage("document.import.choose.document.class", null, locale));
        model.put("advancedQuery", isAdvancedQuery);
        return new ModelAndView("import.definition", model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ajax/attributes")
    @ResponseBody
    public String getAttributeForm(@RequestParam(DOCUMENT_CLASS_SHORT_NAME) String documentClassShortName, Locale locale, Device device, @RequestParam("advancedQuery") boolean isAdvancedQuery) {

        logger.debug("Normal device: {}, mobile device: {}, tablet device: {}", new Object[]{device.isNormal(), device.isMobile(), device.isTablet()});
        logger.debug("About to generate form for document class '{}'", documentClassShortName);

        Set<Attribute> attributes;
        try {
            attributes = documentService.findAttributes(new DocumentClass(documentClassShortName));
        } catch (DocumentClassNotFoundException e) {
            logger.error("Unable to find attributes for document class short name '{}'", documentClassShortName, e);
            attributes = newHashSet();
        }

        String html;
        Map<String, Object> binding = new HashMap<>();

        binding.put("hasDoc", false);
        binding.put("isAdvancedQuery", isAdvancedQuery);

        if (isAdvancedQuery) {
            binding.put("buttonLabel", messageSource.getMessage("query.start.button", null, locale));
            binding.put("formUrl", "extendedQuery.html");
        } else {
            binding.put("buttonLabel", messageSource.getMessage("button.import.document", null, locale));
            binding.put("formUrl", "performImport.html");
        }

        if (!attributes.isEmpty()) {
            binding.put("docclass", documentClassShortName);
            binding.put("attributes", copyOf(attributes));
            html = templateEngine.render("templates/import-form.doxview", binding);
        } else {
            final String message = htmlEscape(messageSource.getMessage("document.import.no.attributes", new Object[]{documentClassShortName}, locale));
            binding.put("message", message);
            html = templateEngine.render("templates/import-failure.doxview", binding);
        }
        logger.debug("Returning HTML code {}", html);
        return html;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performImport.html")
    public ModelAndView importDocument(MultipartFile file, WebRequest request) {

        Map<String, Object> model = newHashMap();

        try {
            DocumentClass documentClass = new DocumentClass(request.getParameter(DOCUMENT_CLASS_SHORT_NAME));

            Iterator<String> params = request.getParameterNames();
            Map<TranslatableKey, Object> indices = newHashMap();

            while (params.hasNext()) {
                String param = params.next();
                if (!DOCUMENT_CLASS_SHORT_NAME.endsWith(param)) {
                    indices.put(new TranslatableKey(param), new String(request.getParameter(param).getBytes("iso-8859-1"), "utf-8"));
                }
            }

            PhysicalDocument physicalDocument = new PhysicalDocument(
                    documentClass,
                    file.getBytes(),
                    indices,
                    file.getOriginalFilename());

            DocumentReference documentReference = documentService.importDocument(physicalDocument);

            model.put("doc", documentReference);
            return new ModelAndView("import.successful", model);

        } catch (ValidationException | IOException | DocumentClassNotFoundException e) {
            logger.error("Unable to import document", e);
            model.put("exception", e);
            return new ModelAndView("import.failure", model);
        } catch (DocumentDuplicationException e) {
            logger.error("Unable to import document. Duplicate document detected", e);
            model.put("docId", e.getDocumentId());
            model.put("docHash", e.getHash());
            return new ModelAndView("import.duplicate.document", model);
        }
    }
}
