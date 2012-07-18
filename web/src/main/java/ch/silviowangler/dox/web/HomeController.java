package ch.silviowangler.dox.web;

import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    @RequestMapping(method = RequestMethod.POST, value = "query.html")
    public ModelAndView query(@RequestParam("q") String queryString) {

        Set<DocumentReference> documentReferences = documentService.findDocumentReferences(queryString);
        Map<String, Object> model = new HashMap<String, Object>(1);
        model.put("documents", documentReferences);

        return new ModelAndView("result.definition", model);
    }
}
