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

package ch.silviowangler.dox.web.taglib;

import ch.silviowangler.dox.api.Attribute;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.web.util.TemplateEngine;
import ch.silviowangler.dox.web.util.TemplateEngineImpl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Map;
import java.util.SortedSet;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class DocumentEditAttributeFormTag extends TagSupport {

    private DocumentReference documentReference;
    private SortedSet<Attribute> attributes;
    private TemplateEngine templateEngine = new TemplateEngineImpl();
    private String buttonLabel;
    private String formLabel;

    public void setDocumentReference(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    public void setAttributes(SortedSet<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public void setFormLabel(String formLabel) {
        this.formLabel = formLabel;
    }

    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();

            Map<String, Object> binding = newHashMap();
            binding.put("docclass", documentReference.getDocumentClass().getShortName());
            binding.put("attributes", copyOf(attributes));
            binding.put("doc", documentReference);
            binding.put("hasDoc", true);
            binding.put("buttonLabel", buttonLabel);
            binding.put("formUrl", formLabel);

            String htmlForm = templateEngine.render("templates/import-form.doxview", binding);
            out.println(htmlForm);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
