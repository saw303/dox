package ch.silviowangler.dox.web;

import ch.silviowangler.dox.api.settings.Setting;
import ch.silviowangler.dox.api.settings.SettingsService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static ch.silviowangler.dox.api.settings.SettingsConstants.SETTING_FIND_ONLY_MY_DOCUMENTS;
import static ch.silviowangler.dox.api.settings.SettingsConstants.SETTING_WILDCARD_QUERY;
import static com.google.common.collect.Maps.newHashMap;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

    @RequestMapping(method = POST, value = "updateSetting.html")
    ModelAndView updateSettings(@RequestParam("setting") String settingKey, @RequestParam(required = false, value = "v", defaultValue = "0") String value) {

        settingsService.createOrUpdateSetting(settingKey, value);

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

    @RequestMapping(method = GET, value = "settings")
    public
    @ResponseBody
    List<Setting> listSettings() {
        final Map<String, String> model = getModel();

        List<Setting> settings = Lists.newArrayListWithCapacity(model.size());

        for (String key : model.keySet()) {
            settings.add(new Setting(key, model.get(key)));
        }

        return settings;
    }
}
