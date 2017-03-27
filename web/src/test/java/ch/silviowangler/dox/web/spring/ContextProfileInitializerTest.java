package ch.silviowangler.dox.web.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
