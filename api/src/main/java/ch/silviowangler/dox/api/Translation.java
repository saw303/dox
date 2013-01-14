/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.api;

import java.io.Serializable;
import java.util.Locale;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 14.01.13 07:36
 *        </div>
 */
public class Translation implements Serializable {

    private String key;
    private Locale locale;
    private String translation;

    public Translation() {
    }

    public Translation(String key, Locale locale, String translation) {
        this.key = key;
        this.locale = locale;
        this.translation = translation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Translation");
        sb.append("{key='").append(key).append('\'');
        sb.append(", locale=").append(locale);
        sb.append(", translation='").append(translation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}