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

import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.TranslatableKey;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.DateFormat;

import static java.lang.String.valueOf;
import static java.text.DateFormat.MEDIUM;
import static java.text.DateFormat.getDateInstance;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class DocumentAttributeListingTag extends TagSupport {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private DocumentReference documentReference;
    private String query;

    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    public void setDocumentReference(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }


    @Override
    public int doStartTag() throws JspException {

        if (!documentReference.getIndices().isEmpty()) {

            String[] fragments = new String[0];

            if (query != null) {
                query = query.replace('?', '*');
                fragments = query.split("\\*");
            }

            JspWriter out = pageContext.getOut();

            StringBuilder sb = new StringBuilder("<div class=\"attributeListing\">");

            boolean afterFirst = false;
            for (TranslatableKey key : documentReference.getIndices().keySet()) {

                Object object = documentReference.getIndices().get(key);

                final String value;
                if (object instanceof DateTime) {
                    DateFormat f = getDateInstance(MEDIUM, pageContext.getRequest().getLocale());
                    value = f.format(((DateTime) object).toDate());
                }
                else {
                    value = valueOf(object);
                }

                boolean doesMatch = false;

                for (String fragment : fragments) {
                    if (fragment.length() > 0) doesMatch = value.contains(fragment);
                    if (doesMatch) break;
                }
                if (afterFirst) sb.append("&nbsp;");
                sb.append(key.getTranslation()).append(":&nbsp;");

                if (doesMatch) sb.append("<span class=\"highlight\">");
                sb.append(value);
                if (doesMatch) sb.append("</span>");

                if (!afterFirst) afterFirst = true;
            }

            sb.append("</div>");

            try {
                out.println(sb.toString());
            } catch (IOException e) {
                logger.error("Cannot write to output stream", e);
            }
        }

        return SKIP_BODY;
    }
}
