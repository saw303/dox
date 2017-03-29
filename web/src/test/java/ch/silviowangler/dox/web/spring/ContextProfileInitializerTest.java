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
package ch.silviowangler.dox.web.spring;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@RunWith(MockitoJUnitRunner.class)
public class ContextProfileInitializerTest {

    public static final String DOX_ENV = "dox.env";
    private ContextProfileInitializer contextProfileInitializer = new ContextProfileInitializer();

    @Mock
    private ConfigurableWebApplicationContext applicationContext;
    @Mock
    private ConfigurableEnvironment environment;

    @Before
    public void setUp() throws Exception {
        System.clearProperty(DOX_ENV);
    }

    @Test
    public void verifyThatProfileProdGetsActivatedIfSystemPropertyIsMissing() throws Exception {

        when(applicationContext.getEnvironment()).thenReturn(environment);
        contextProfileInitializer.initialize(applicationContext);
        verify(environment).setActiveProfiles("prod");
    }

    @Test
    public void verifyThatProfileFromSystemPropertyGetsActivated() throws Exception {

        System.setProperty("dox.env", "gugus");

        when(applicationContext.getEnvironment()).thenReturn(environment);
        contextProfileInitializer.initialize(applicationContext);
        verify(environment).setActiveProfiles("gugus");
    }
}
