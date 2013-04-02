package ch.silviowangler.dox.web.util;

import java.util.Map;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public interface TemplateEngine {

    String render(String templateName, Map<String, Object> binding);
}
