/**
 * Copyright 2012 - 2017 Silvio Wangler (silvio.wangler@gmail.com)
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
package ch.silviowangler.dox.settings;

import ch.silviowangler.dox.api.settings.SettingsService;
import ch.silviowangler.dox.domain.UserSetting;
import ch.silviowangler.dox.domain.security.DoxUser;
import ch.silviowangler.dox.repository.UserSettingRepository;
import ch.silviowangler.dox.repository.security.DoxUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
    @PreAuthorize("hasRole('ROLE_USER')")
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, String> findUserSettings() {

        final List<UserSetting> userSettings = userSettingRepository.findByUser(getDoxUser());
        Map<String, String> map = new HashMap<>(userSettings.size());

        for (UserSetting userSetting : userSettings) {
            map.put(userSetting.getKey(), userSetting.getValue());
        }

        return map;
    }
}
