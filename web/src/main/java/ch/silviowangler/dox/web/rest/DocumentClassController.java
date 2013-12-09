package ch.silviowangler.dox.web.rest;

import ch.silviowangler.dox.api.DocumentClass;
import ch.silviowangler.dox.api.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/api/v1/documentClass")
public class DocumentClassController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping(method = GET)
    public @ResponseBody List<DocumentClass> list() {
        return documentService.findAllDocumentClasses();
    }
}
