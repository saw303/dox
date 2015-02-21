package ch.silviowangler.dox.web.rest;

import ch.silviowangler.dox.api.rest.PartnerModel;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created on 21.02.15.
 *
 * @author Silvio Wangler
 * @since 0.5
 */
@RestController("partnerController")
@RequestMapping("/api/v1/partner")
public class PartnerController {

    @RequestMapping(value = "/{id}", method = GET)
    public PartnerModel getPartner(@PathVariable("id") String partnerId) {
        return new PartnerModel();
    }

    @RequestMapping(method = GET)
    public List<PartnerModel> getPartners() {
        return Lists.asList(new PartnerModel(), new PartnerModel(), null);
    }
}
