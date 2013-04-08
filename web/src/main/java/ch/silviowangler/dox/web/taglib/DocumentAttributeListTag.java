/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
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

package ch.silviowangler.dox.web.taglib;

import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.TranslatableKey;
import org.joda.time.DateTime;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.DateFormat;

import static java.text.DateFormat.getDateInstance;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class DocumentAttributeListTag extends TagSupport {


    private DocumentReference documentReference;

    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    public void setDocumentReference(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    @Override
    public int doStartTag() throws JspException {


        try {

            if (!documentReference.getIndices().isEmpty()) {
                JspWriter out = pageContext.getOut();

                StringBuilder sb = new StringBuilder("<ul>");

                for (TranslatableKey key : documentReference.getIndices().keySet()) {

                    sb.append("<li>").append(key.getTranslation());
                    Object value = documentReference.getIndices().get(key);

                    if (value instanceof DateTime) {
                        DateFormat f = getDateInstance(DateFormat.MEDIUM, pageContext.getRequest().getLocale());
                        value = f.format(((DateTime) value).toDate());
                    }

                    sb.append(" = ").append(value);
                    sb.append("</li>");
                }
                sb.append("</ul>");
                out.println(sb.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
