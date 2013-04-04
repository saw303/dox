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

package ch.silviowangler.dox.web.util;

import com.google.common.io.Resources;
import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static com.google.common.base.Charsets.UTF_8;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
@Component("templateEngine")
public class TemplateEngineImpl implements TemplateEngine {

    @Override
    public String render(String templateName, Map<String, Object> binding) {

        URL resource = Resources.getResource(templateName);
        try {
            String text = Resources.toString(resource, UTF_8);
            SimpleTemplateEngine engine = new SimpleTemplateEngine();
            Writable writable = engine.createTemplate(text).make(binding);

            return writable.toString();
        } catch (IOException | ClassNotFoundException e) {
            return "";
        }
    }
}
