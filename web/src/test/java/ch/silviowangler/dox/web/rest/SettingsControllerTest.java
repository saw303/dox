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
package ch.silviowangler.dox.web.rest;

import static ch.silviowangler.dox.api.settings.SettingsConstants.SETTING_FIND_ONLY_MY_DOCUMENTS;
import static ch.silviowangler.dox.api.settings.SettingsConstants.SETTING_WILDCARD_QUERY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.Maps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ch.silviowangler.dox.api.settings.Setting;
import ch.silviowangler.dox.api.settings.SettingsService;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@RunWith(MockitoJUnitRunner.class)
public class SettingsControllerTest {

    @Mock
    private SettingsService settingsService;
    @Mock
    private WebRequest request;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private SettingsController controller = new SettingsController();


    @Test
    public void testGet() throws Exception {

        when(request.getLocale()).thenReturn(Locale.GERMAN);
        when(messageSource.getMessage(anyString(), (Object[]) eq(null), eq(Locale.GERMAN))).thenReturn("test");

        final List<Setting> settings = controller.listSettings(request);

        assertThat(settings.size(), is(2));

        for (Setting setting : settings) {
            assertThat(setting.getValue(), is("0"));
            assertTrue(setting.getKey().matches("(wq|fomd)"));
            assertThat(setting.getDescription(), is("test"));
        }
    }

    @Test
    public void testGetWithSetting() throws Exception {

        Map<String, String> settingsMap = Maps.newHashMapWithExpectedSize(2);

        settingsMap.put(SETTING_WILDCARD_QUERY, "1");
        settingsMap.put(SETTING_FIND_ONLY_MY_DOCUMENTS, "1");

        when(settingsService.findUserSettings()).thenReturn(settingsMap);

        when(request.getLocale()).thenReturn(Locale.GERMAN);
        when(messageSource.getMessage(anyString(), (Object[]) eq(null), eq(Locale.GERMAN))).thenReturn("test");

        final List<Setting> settings = controller.listSettings(request);

        assertThat(settings.size(), is(2));

        for (Setting setting : settings) {
            assertThat(setting.getValue(), is("1"));
            assertTrue(setting.getKey().matches("(wq|fomd)"));
            assertThat(setting.getDescription(), is("test"));
        }
    }

    @Test
    public void testSave() throws Exception {

        final MockHttpServletResponse response = new MockHttpServletResponse();
        controller.saveSetting(new Setting("invalid key", "0", "whatever"), response);

        assertThat(response.getStatus(), is(200));

        verify(settingsService, never()).createOrUpdateSetting(anyString(), anyString());
    }

    @Test
    public void testSave2() throws Exception {

        final MockHttpServletResponse response = new MockHttpServletResponse();
        controller.saveSetting(new Setting("wq", "1", "whatever"), response);

        assertThat(response.getStatus(), is(200));

        verify(settingsService).createOrUpdateSetting(SETTING_WILDCARD_QUERY, "1");
    }

    @Test
    public void testSave3() throws Exception {

        final MockHttpServletResponse response = new MockHttpServletResponse();
        controller.saveSetting(new Setting("fomd", "0", "whatever"), response);

        assertThat(response.getStatus(), is(200));

        verify(settingsService).createOrUpdateSetting(SETTING_FIND_ONLY_MY_DOCUMENTS, "0");
    }
}
