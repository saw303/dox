package ch.silviowangler.dox.api.settings;

import java.util.Map;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public interface SettingsService {

    void createOrUpdateSetting(String key, String value);

    Map<String, String> findUserSettings();
}
