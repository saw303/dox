package ch.silviowangler.dox.web.util;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

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
            String text = Resources.toString(resource, Charsets.UTF_8);
            SimpleTemplateEngine engine = new SimpleTemplateEngine();
            Writable writable = engine.createTemplate(text).make(binding);

            return writable.toString();
        } catch (IOException | ClassNotFoundException e) {
            return "";
        }
    }
}
