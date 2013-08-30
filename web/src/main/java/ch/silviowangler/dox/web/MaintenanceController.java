package ch.silviowangler.dox.web;

import ch.silviowangler.dox.api.MissingDocument;
import ch.silviowangler.dox.api.Source;
import ch.silviowangler.dox.api.VerificationService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

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
