/*
 * Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.web.taglib;

import ch.silviowangler.dox.api.DocumentNotFoundException;
import ch.silviowangler.dox.api.DocumentNotInStoreException;
import ch.silviowangler.dox.api.DocumentService;
import ch.silviowangler.dox.api.PhysicalDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 22.07.12 11:45
 *        </div>
 */
@Controller
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    private HttpHeaders httpHeaders = new HttpHeaders();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/document/{id}")
    public void getDocument(@PathVariable("id") Long id, HttpServletResponse response) {

        try {
            PhysicalDocument document = documentService.findPhysicalDocument(id);
            response.setStatus(SC_OK);
            response.addHeader("Content-Type", document.getMimeType());
            response.getOutputStream().write(document.getContent());
        } catch (DocumentNotFoundException | DocumentNotInStoreException e) {
            logger.warn("Document with id '{}' not found", id, e);
            response.setStatus(SC_NOT_FOUND);
        } catch (IOException e) {
            logger.error("Could not write document to output stream", e);
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }
}
