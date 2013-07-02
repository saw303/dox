package ch.silviowangler.dox.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@Controller
public class SettingsController {

    @RequestMapping(method = GET, value = "settings.html")
    String displaySettings() {
        return "settings";
    }
}
