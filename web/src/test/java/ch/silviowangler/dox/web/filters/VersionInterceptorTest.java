/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.web.filters;

import ch.silviowangler.dox.DoxVersion;
import ch.silviowangler.dox.api.VersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
@RunWith(MockitoJUnitRunner.class)
public class VersionInterceptorTest {

    @InjectMocks
    private VersionInterceptor versionInterceptor = new VersionInterceptor();
    @Mock
    private VersionService versionService;

    @Test
    public void testPreHandle() throws Exception {
        assertThat(versionInterceptor.preHandle(null, null, null), is(true));
    }

    @Test
    public void testPostHandle() throws Exception {

        final ModelAndView modelAndView = new ModelAndView();

        final String expectedVersion = "1.1";
        when(versionService.fetchVersion()).thenReturn(new DoxVersion(expectedVersion));

        versionInterceptor.postHandle(null, null, null, modelAndView);

        assertThat(modelAndView.getModel().size(), is(1));
        assertThat(modelAndView.getModel().containsKey("version"), is(true));
        assertThat(modelAndView.getModel().get("version").toString(), is(expectedVersion));

    }

    @Test
    public void testAfterCompletion() throws Exception {
        versionInterceptor.afterCompletion(null, null, null, null);
    }
}
