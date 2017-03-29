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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class ContextProfileInitializer  implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(ContextProfileInitializer.class);
    public static final String ENVIRONMENT_PRODUCTION = "prod";
    public static final String ENVIRONMENT_DEVELOPMENT = "dev";

    @Override
    public void initialize(ConfigurableWebApplicationContext applicationContext) {
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();

        final String profile = System.getProperty("dox.env", ENVIRONMENT_PRODUCTION);

        logger.debug("Setting Spring profile to '{}'", profile);

        environment.setActiveProfiles(profile);
    }
}
