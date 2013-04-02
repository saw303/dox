package ch.silviowangler.dox.web.util;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class TemplateEngineImplTest {

    private TemplateEngine templateEngine;

    @Before
    public void init() {
        templateEngine = new TemplateEngineImpl();
    }

    @Test
    public void testRender() throws Exception {

        Map<String, Object> binding = Maps.newHashMap();
        binding.put("name", "World");

        String response = templateEngine.render("template.txt", binding);

        assertThat(response, is("Hello World"));
    }

    @Test
    public void testRender2() throws Exception {

        Map<String, Object> binding = Maps.newHashMap();
        binding.put("name", "stranger");

        String response = templateEngine.render("template.txt", binding);

        assertThat(response, is("Hello stranger"));
    }
}
