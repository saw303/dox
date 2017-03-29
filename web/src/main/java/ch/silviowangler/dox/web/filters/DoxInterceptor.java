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
package ch.silviowangler.dox.web.filters;

import static org.springframework.util.Assert.notNull;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.silviowangler.dox.api.DocumentService;
import ch.silviowangler.dox.api.VersionService;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class DoxInterceptor implements HandlerInterceptor, InitializingBean, EnvironmentAware {


    private VersionService versionService;
    private DocumentService documentService;
    private Environment environment;

    public void setVersionService(VersionService versionService) {
        this.versionService = versionService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(versionService, "Version service must not be null");
        notNull(documentService, "Document service must not be null");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelExists(modelAndView)) {
            modelAndView.getModel().put("version", versionService.fetchVersion());
            modelAndView.getModel().put("documentCount", documentService.retrieveDocumentReferenceCount());
            modelAndView.getModel().put("environment", environment);
        }
    }

    private boolean modelExists(ModelAndView modelAndView) {
        return modelAndView != null && modelAndView.getModel() != null;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // do nothing
    }
}
