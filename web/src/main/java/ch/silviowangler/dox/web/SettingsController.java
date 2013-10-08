package ch.silviowangler.dox.web;

import ch.silviowangler.dox.api.settings.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static ch.silviowangler.dox.api.settings.SettingsConstants.SETTING_FIND_ONLY_MY_DOCUMENTS;
import static ch.silviowangler.dox.api.settings.SettingsConstants.SETTING_WILDCARD_QUERY;
import static com.google.common.collect.Maps.newHashMap;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@Controller
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @RequestMapping(method = GET, value = "settings.html")
    ModelAndView displaySettings() {
        Map<String, String> model = getModel();

        return new ModelAndView("settings", model);
    }

    private Map<String, String> getModel() {
        Map<String, String> model = newHashMap();

        final Map<String, String> userSettings = settingsService.findUserSettings();
        model.put("fomd", userSettings.containsKey(SETTING_FIND_ONLY_MY_DOCUMENTS) ? userSettings.get(SETTING_FIND_ONLY_MY_DOCUMENTS) : "0");
        model.put("wq", userSettings.containsKey(SETTING_WILDCARD_QUERY) ? userSettings.get(SETTING_WILDCARD_QUERY) : "0");
        return model;
    }
}
