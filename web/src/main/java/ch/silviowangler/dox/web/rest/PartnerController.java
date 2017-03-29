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

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import ch.silviowangler.dox.api.rest.PartnerModel;

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
        ArrayList<PartnerModel> partnerModels = new ArrayList<>();

        partnerModels.add(new PartnerModel("Hans Brauer"));
        partnerModels.add(new PartnerModel("Peter Ritzli"));
        partnerModels.add(new PartnerModel("Karl Koss"));

        return partnerModels;
    }
}
