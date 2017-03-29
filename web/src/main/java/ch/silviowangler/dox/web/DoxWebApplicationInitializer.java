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
package ch.silviowangler.dox.web;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import ch.silviowangler.dox.CoreConfiguration;
import ch.silviowangler.dox.CoreSchedulingConfiguration;
import ch.silviowangler.dox.CoreSecurityConfiguration;
import ch.silviowangler.dox.DomainConfiguration;
import ch.silviowangler.dox.web.spring.ContextProfileInitializer;

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

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic dispatcherServlet) {
        super.customizeRegistration(dispatcherServlet);

        final int maxFileSize = 25 * 1024 * 1024;
        final int maxRequestSize = 125 * 1024 * 1024;
        final int fileSizeThreshold = 1 * 1024 * 1024;
        dispatcherServlet.setMultipartConfig(new MultipartConfigElement(System.getProperty("java.io.tmpdir"), maxFileSize, maxRequestSize, fileSizeThreshold));
    }
}
