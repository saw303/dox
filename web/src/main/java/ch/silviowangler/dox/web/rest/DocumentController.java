package ch.silviowangler.dox.web.rest;

import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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
    public
    @ResponseBody
    List<DocumentReference> query(@RequestParam("q") String queryString, @RequestParam(value = "wc", defaultValue = "false", required = false) boolean useWildcard, @RequestParam(value = "uo", defaultValue = "false", required = false) boolean forCurrentUserOnly) {
        final boolean hasWildcard = containsWildcard(queryString);
        String queryStringCopy = queryString;

        logger.trace("Received query request '{}'. Contains wildcards? {}", queryString, hasWildcard);

        if (useWildcard && !hasWildcard) {
            logger.debug("Going to overwrite query string '{}' because wildcard searching is activated", queryString);
            queryStringCopy = "*" + queryString + "*";
            logger.debug("Using wildcard search '{}'", queryStringCopy);
        }

        List<DocumentReference> documentReferences;
        if (forCurrentUserOnly) {
            documentReferences = documentService.findDocumentReferencesForCurrentUser(queryStringCopy);
        } else {
            documentReferences = documentService.findDocumentReferences(queryStringCopy);
        }
        return documentReferences;
    }

    private boolean containsWildcard(final String queryString) {
        return queryString.contains("*") || queryString.contains("?");
    }
}
