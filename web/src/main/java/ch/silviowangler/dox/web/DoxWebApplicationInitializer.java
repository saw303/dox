package ch.silviowangler.dox.web;

import ch.silviowangler.dox.CoreConfiguration;
import ch.silviowangler.dox.CoreSchedulingConfiguration;
import ch.silviowangler.dox.CoreSecurityConfiguration;
import ch.silviowangler.dox.DomainConfiguration;
import ch.silviowangler.dox.web.spring.ContextProfileInitializer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by Silvio Wangler on 06/10/15.
 */
public class DoxWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{DomainConfiguration.class, CoreConfiguration.class, CoreSchedulingConfiguration.class, CoreSecurityConfiguration.class, WebConfiguration.class, WebSecurityConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy("springSecurityFilterChain");

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        return new Filter[]{delegatingFilterProxy, characterEncodingFilter};
    }

    @Override
    protected ApplicationContextInitializer<?>[] getRootApplicationContextInitializers() {
        return new ApplicationContextInitializer<?>[]{new ContextProfileInitializer()};
    }
}
