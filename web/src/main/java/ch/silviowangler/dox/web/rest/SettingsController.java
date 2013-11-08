package ch.silviowangler.dox.web.rest;

import ch.silviowangler.dox.api.settings.Setting;
import ch.silviowangler.dox.api.settings.SettingsService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
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
@Controller("restSettingsController")
@RequestMapping("/api/v1/settings")
public class SettingsController implements MessageSourceAware {

    @Autowired
    private SettingsService settingsService;

    private MessageSource messageSource;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private Map<String, String> getModel() {
        Map<String, String> model = newHashMap();

        final Map<String, String> userSettings = settingsService.findUserSettings();
        model.put("fomd", userSettings.containsKey(SETTING_FIND_ONLY_MY_DOCUMENTS) ? userSettings.get(SETTING_FIND_ONLY_MY_DOCUMENTS) : "0");
        model.put("wq", userSettings.containsKey(SETTING_WILDCARD_QUERY) ? userSettings.get(SETTING_WILDCARD_QUERY) : "0");
        return model;
    }

    @RequestMapping(method = GET)
    public
    @ResponseBody
    List<Setting> listSettings(WebRequest request) {
        final Map<String, String> model = getModel();

        List<Setting> settings = Lists.newArrayListWithCapacity(model.size());

        for (String key : model.keySet()) {
            settings.add(new Setting(key, model.get(key), messageSource.getMessage("settings." + key, null, request.getLocale())));
        }

        return settings;
    }

    @RequestMapping(method = POST)
    public void saveSetting(@RequestBody Setting setting, HttpServletResponse response) {

        logger.debug("Received {} settings", setting);

        if ("fomd".equals(setting.getKey())) {
            settingsService.createOrUpdateSetting(SETTING_FIND_ONLY_MY_DOCUMENTS, setting.getValue());
        } else if ("wq".equals(setting.getKey())) {
            settingsService.createOrUpdateSetting(SETTING_WILDCARD_QUERY, setting.getValue());
        } else {
            logger.warn("Unknown key {}. Did not update any setting", setting.getKey());
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
