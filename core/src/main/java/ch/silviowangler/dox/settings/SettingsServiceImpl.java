package ch.silviowangler.dox.settings;

import ch.silviowangler.dox.api.settings.SettingsService;
import ch.silviowangler.dox.domain.UserSetting;
import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.repository.UserSettingRepository;
import ch.silviowangler.dox.repository.security.DoxUserRepository;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@Service("settingsService")
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private UserSettingRepository userSettingRepository;
    @Autowired
    private DoxUserRepository doxUserRepository;


    @Override
    @Transactional
    public void createOrUpdateSetting(String key, String value) {

        DoxUser doxUser = getDoxUser();

        UserSetting userSetting = userSettingRepository.findByKeyAndUser(key, doxUser);

        if (userSetting == null) {
            userSetting = new UserSetting(key, value, doxUser);
        } else {
            userSetting.setValue(value);
        }

        userSettingRepository.save(userSetting);
    }

    private DoxUser getDoxUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return doxUserRepository.findByUsername(user.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> findUserSettings() {

        final List<UserSetting> userSettings = userSettingRepository.findByUser(getDoxUser());
        Map<String, String> map = Maps.newHashMapWithExpectedSize(userSettings.size());

        for (UserSetting userSetting : userSettings) {
            map.put(userSetting.getKey(), userSetting.getValue());
        }

        return map;
    }
}
