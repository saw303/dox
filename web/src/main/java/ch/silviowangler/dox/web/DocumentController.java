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
import ch.silviowangler.dox.api.stats.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.SortedSet;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
    @Autowired
    private StatisticsService statisticsService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = GET, value = "/document/{id}")
    public void getDocument(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            PhysicalDocument document = documentService.findPhysicalDocument(id);

            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (authentication != null) ? ((User) authentication.getPrincipal()).getUsername() : "anonymous";
            statisticsService.registerDocumentReferenceClick(String.valueOf(id), username);

            response.setStatus(SC_OK);
            response.addHeader("Content-Type", document.getMimeType());
            response.addHeader("Content-Disposition", "inline; filename=\"" + document.getFileName() + "\"");
            response.getOutputStream().write(document.getContent());
            response.getOutputStream().flush();
        } catch (DocumentNotFoundException | DocumentNotInStoreException e) {
            logger.warn("Document with id '{}' not found", id, e);
            response.setStatus(SC_NOT_FOUND);
        } catch (IOException e) {
            logger.error("Could not write document to output stream", e);
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = GET, value = "/document/edit/{id}")
    public ModelAndView editDocument(@PathVariable("id") Long id) {

        ModelAndView modelAndView = new ModelAndView("edit.doc", new HashMap<String, Object>());

        try {
            final DocumentReference documentReference = documentService.findDocumentReference(id);
            final SortedSet<Attribute> attributes = documentService.findAttributes(documentReference.getDocumentClass());

            modelAndView.getModel().put("doc", documentReference);
            modelAndView.getModel().put("attributes", attributes);

        } catch (DocumentNotFoundException | DocumentClassNotFoundException e) {
            logger.error("No such document", e);
        }
        return modelAndView;
    }

    @RequestMapping(method = POST, value = "/document/edit/{id}")
    public ModelAndView editDocument(@PathVariable("id") Long id, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("import.successful");

        try {
            DocumentReference documentReference = documentService.findDocumentReference(id);

            boolean didChangeValue = false;
            for (TranslatableKey key : documentReference.getIndices().keySet()) {
                final String paramValue = request.getParameter(key.getKey());

                if (paramValue != null) {
                    didChangeValue = true;
                    documentReference.getIndices().put(key, new String(paramValue.getBytes("iso-8859-1"), "utf-8"));
                }
            }

            if (didChangeValue) {
                documentReference = documentService.updateIndices(documentReference);
            }
            modelAndView.getModel().put("doc", documentReference);

        } catch (DocumentNotFoundException | UnsupportedEncodingException e) {
            modelAndView.setViewName("modification.doc.failed");
            logger.error("Cannot update document", e);
        }
        return modelAndView;
    }
}
