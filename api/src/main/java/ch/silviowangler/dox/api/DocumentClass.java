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
package ch.silviowangler.dox.api;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class DocumentClass extends AbstractTranslatable implements Serializable {

    private String shortName;
    private String translatedText;
    private String client;

    public DocumentClass() {
        super();
    }

    public DocumentClass(String shortName, String translatedText) {
        this.shortName = shortName;
        this.translatedText = translatedText;
    }

    public DocumentClass(String shortName) {
        this(shortName, null);
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String getTranslation() {
        return this.translatedText;
    }

    @Override
    public String retrieveKeyPostfix() {
        return this.shortName;
    }

    @Override
    public void setTranslation(String translation) {
        this.translatedText = translation;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentClass that = (DocumentClass) o;

        return shortName.equals(that.shortName);
    }

    @Override
    public int hashCode() {
        return shortName.hashCode();
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("shortName", shortName)
                .add("translatedText", translatedText)
                .add("client", client)
                .toString();
    }
}
