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
package ch.silviowangler.dox.web;

import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;

import ch.silviowangler.dox.api.security.UserService;

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
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(method = GET, value = "/")
    public ModelAndView homeScreen(@RequestParam(value = "q", defaultValue = "", required = false) String query) {

        Map<String, Object> model = newHashMapWithExpectedSize(1);
        model.put("query", HtmlUtils.htmlUnescape(query));

        return new ModelAndView("base.definition.angularjs", model);
    }

    @RequestMapping(method = GET, value = "/ui/*")
    public ModelAndView interceptAnyUICalls(@RequestParam(value = "q", defaultValue = "", required = false) String query) {
        return homeScreen(query);
    }

    @RequestMapping(method = GET, value = "/partials/{view}")
    public ModelAndView retrieveViews(@PathVariable("view") String viewName) {
        logger.debug("Getting request for partial view {}", viewName);

        Map<String, Object> model = Maps.newHashMap();
        model.put("clients", userService.getCurrentUserClients());
        return new ModelAndView(viewName, model);
    }
}
