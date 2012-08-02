/*
 * Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
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

package ch.silviowangler.dox.web.taglib;

import ch.silviowangler.dox.api.DocumentReference;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class DocumentAttributeListTagTest {

    private DocumentReference documentReference;
    private DocumentAttributeListTag tag;
    private PageContext pageContext;
    private MessageSource messageSource;
    private Map<String, Object> indices;
    private ServletContext servletContext;
    private MockHttpServletRequest request;

    private final String newLine = System.lineSeparator();


    @Before
    public void init() {

        indices = new HashMap<String, Object>();

        servletContext = new MockServletContext();

        request = new MockHttpServletRequest(servletContext);
        request.addPreferredLocale(Locale.GERMAN);

        final RequestDataValueProcessor requestDataValueProcessor = mock(RequestDataValueProcessor.class);

        request.setAttribute(RequestContext.WEB_APPLICATION_CONTEXT_ATTRIBUTE, new WebApplicationContext() {
            @Override
            public ServletContext getServletContext() {
                return servletContext;
            }

            @Override
            public String getId() {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public String getDisplayName() {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public long getStartupDate() {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public ApplicationContext getParent() {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public void publishEvent(ApplicationEvent event) {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public BeanFactory getParentBeanFactory() {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public boolean containsLocalBean(String name) {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public boolean containsBeanDefinition(String beanName) {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public int getBeanDefinitionCount() {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public String[] getBeanDefinitionNames() {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public String[] getBeanNamesForType(Class<?> type) {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public Object getBean(String name) throws BeansException {
                return messageSource;
            }

            @Override
            public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
                return (T) requestDataValueProcessor;
            }

            @Override
            public <T> T getBean(Class<T> requiredType) throws BeansException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public Object getBean(String name, Object... args) throws BeansException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public boolean containsBean(String name) {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public String[] getAliases(String name) {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public Environment getEnvironment() {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {

                if ("attr.wicked".equals(code)) {
                    return "Name";
                } else if ("attr.aProp".equals(code)) {
                    return "Name";
                } else if ("attr.test".equals(code)) {
                    return "Lisa";
                }

                throw new UnsupportedOperationException("not yet implemented " + code);
            }

            @Override
            public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public Resource[] getResources(String locationPattern) throws IOException {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public Resource getResource(String location) {
                throw new UnsupportedOperationException("not yet implemented");
            }

            @Override
            public ClassLoader getClassLoader() {
                throw new UnsupportedOperationException("not yet implemented");
            }
        });

        pageContext = new MockPageContext(servletContext, request);
        messageSource = mock(MessageSource.class);

        documentReference = new DocumentReference("dummy.txt");
        documentReference.setIndices(indices);
        tag = new DocumentAttributeListTag();
        tag.setDocumentReference(documentReference);
        tag.setPageContext(pageContext);
    }

    @Test
    public void emptyListShouldResultInEmptyOutput() throws JspException, IOException {
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("", ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void oneIndexShouldResultInEmptyOutput() throws JspException, IOException {

        indices.put("wicked", "Hello");

        when(messageSource.getMessage("attr.wicked", null, Locale.getDefault())).thenReturn("Name");

        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("<ul><li>Name = Hello</li></ul>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void twoIndexShouldResultInEmptyOutput() throws JspException, IOException {

        indices.put("aProp", "Computer");
        indices.put("test", "Fellow");

        when(messageSource.getMessage("attr.aProp", null, Locale.getDefault())).thenReturn("Name");
        when(messageSource.getMessage("attr.test", null, Locale.getDefault())).thenReturn("Lisa");

        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("<ul><li>Name = Computer</li><li>Lisa = Fellow</li></ul>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void jodaTimeDateTimeShouldBeFormattedAccordingTheCurrentLocale() throws JspException, IOException {

        indices.put("aProp", new DateTime(2010, 11, 01, 0, 0));

        when(messageSource.getMessage("attr.aProp", null, Locale.getDefault())).thenReturn("Name");

        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("<ul><li>Name = 01.11.2010</li></ul>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void jodaTimeDateTimeShouldBeFormattedAccordingTheCanadianLocale() throws JspException, IOException {

        request.addPreferredLocale(Locale.CANADA);

        indices.put("aProp", new DateTime(2010, 11, 01, 0, 0));

        when(messageSource.getMessage("attr.aProp", null, Locale.getDefault())).thenReturn("Name");

        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("<ul><li>Name = 1-Nov-2010</li></ul>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }

    @Test
    public void jodaTimeDateTimeShouldBeFormattedAccordingTheUKLocale() throws JspException, IOException {

        request.addPreferredLocale(Locale.UK);

        indices.put("aProp", new DateTime(2010, 11, 01, 0, 0));

        when(messageSource.getMessage("attr.aProp", null, Locale.getDefault())).thenReturn("Name");

        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
        assertEquals("<ul><li>Name = 01-Nov-2010</li></ul>" + newLine, ((MockHttpServletResponse) pageContext.getResponse()).getContentAsString());
    }
}
