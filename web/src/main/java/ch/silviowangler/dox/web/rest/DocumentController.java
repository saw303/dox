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
package ch.silviowangler.dox.web.rest;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import ch.silviowangler.dox.api.DocumentNotFoundException;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.DocumentService;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
@Controller("restDocumentController")
@RequestMapping("/api/v1/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = GET)
    public @ResponseBody List<DocumentReference> query(@RequestParam("q") String queryString, @RequestParam(value = "wc", defaultValue = "false", required = false) boolean useWildcard, @RequestParam(value = "uo", defaultValue = "false", required = false) boolean forCurrentUserOnly, Locale locale) {
        final boolean hasWildcard = containsWildcard(queryString);
        String queryStringCopy = queryString;

        logger.trace("Received query request '{}' for locale. Contains wildcards? {}", queryString, locale, hasWildcard);

        if (useWildcard && !hasWildcard) {
            logger.debug("Going to overwrite query string '{}' because wildcard searching is activated", queryString);
            queryStringCopy = "*" + queryString + "*";
            logger.debug("Using wildcard search '{}'", queryStringCopy);
        }

        List<DocumentReference> documentReferences;
        if (forCurrentUserOnly) {
            documentReferences = documentService.findDocumentReferencesForCurrentUser(queryStringCopy, locale);
        } else {
            documentReferences = documentService.findDocumentReferences(queryStringCopy, locale);
        }
        return documentReferences;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public @ResponseBody DocumentReference getDocumentReference(@PathVariable("id") Long docId, HttpServletResponse response) {
        DocumentReference documentReference;
        try {
            documentReference = documentService.findDocumentReference(docId);
            response.setStatus(SC_OK);
            return documentReference;
        } catch (DocumentNotFoundException e) {
            response.setStatus(SC_NOT_FOUND);
            return null;
        }
    }

    @RequestMapping(value = "/{id}", method = POST)
    public void updateDocument(@RequestBody DocumentReference documentReference, HttpServletResponse response) {
        logger.info("About to update document {}", documentReference.getId());

        try {
            documentService.updateIndices(documentReference);
            response.setStatus(SC_OK);
        } catch (DocumentNotFoundException e) {
            logger.error("Document {} not found ", documentReference.getId(), e);
            response.setStatus(SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void deleteDocumentReference(@PathVariable("id") Long docId, HttpServletResponse response) {
        logger.debug("About to delete document reference {}", docId);
        documentService.deleteDocument(docId);
        logger.info("Document reference {} successfully deleted", docId);
        response.setStatus(SC_OK);
    }

    private boolean containsWildcard(final String queryString) { return queryString.contains("*") || queryString.contains("?");
    }
}
