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

    @Override
    public void initialize(ConfigurableWebApplicationContext applicationContext) {
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();

        final String profile = System.getProperty("dox.env", "prod");

        logger.debug("Setting Spring profile to '{}'", profile);

        environment.setActiveProfiles(profile);
    }
}
