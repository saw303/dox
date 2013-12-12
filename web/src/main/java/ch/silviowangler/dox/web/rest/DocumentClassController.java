package ch.silviowangler.dox.web.rest;

import ch.silviowangler.dox.api.DocumentService;
import ch.silviowangler.dox.api.rest.DocumentClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/v1/documentClass")
public class DocumentClassController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping(method = GET)
    public List<DocumentClass> list() {
        return documentService.findAllDocumentClasses();
    }
}
