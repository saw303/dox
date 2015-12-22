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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static ch.silviowangler.dox.web.WebConstants.DOCUMENT_CLASS_SHORT_NAME;
import static com.google.common.collect.Maps.newHashMap;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.util.Assert.notNull;

/**
 * @author Silvio Wangler
 * @since 0.1
 * <div>
 * Date: 17.07.12 08:22
 * </div>
 */
@Controller
public class ImportController implements MessageSourceAware, InitializingBean {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DocumentService documentService;
    private MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.messageSource, "Message source must not be null");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST, value = "performImport.html")
    public ResponseEntity<String> importDocument(MultipartFile file, WebRequest request, @RequestParam("x_client") String client) {

        try {
            DocumentClass documentClass = new DocumentClass(request.getParameter(DOCUMENT_CLASS_SHORT_NAME));

            Iterator<String> params = request.getParameterNames();
            Map<TranslatableKey, DescriptiveIndex> indices = newHashMap();

            while (params.hasNext()) {
                String param = params.next();
                if (!DOCUMENT_CLASS_SHORT_NAME.endsWith(param) && !param.startsWith("x_")) {
                    indices.put(new TranslatableKey(param), new DescriptiveIndex(request.getParameter(param)));
                }
            }

            PhysicalDocument physicalDocument = new PhysicalDocument(
                    documentClass,
                    file.getBytes(),
                    indices,
                    file.getOriginalFilename());
            physicalDocument.setClient(client);

            DocumentReference documentReference = documentService.importDocument(physicalDocument);

            logger.info("Successfully imported file {}. Id = {}", file.getOriginalFilename(), documentReference.getHash());
            return new ResponseEntity(CREATED);

        } catch (ValidationException | IOException | DocumentClassNotFoundException e) {
            logger.error("Unable to import document", e);
            return new ResponseEntity(e.getMessage(), CONFLICT);
        } catch (DocumentDuplicationException e) {
            logger.error("Unable to import document. Duplicate document detected", e);
            return new ResponseEntity(e.getMessage(), CONFLICT);
        }
    }
}
