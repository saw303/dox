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
package ch.silviowangler.dox.domain;

import com.google.common.base.MoreObjects;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@Entity
@Table(name = "DOX_TRANSLATIONS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"TRS_LOC", "TRS_KEY"})
})
public class Translation extends AbstractPersistable<Long> {

    @Column(nullable = false, name = "TRS_KEY", length = 150)
    private String key;
    @Column(nullable = false, name = "TRS_LOC", length = 5)
    private Locale locale;
    @Column(nullable = false, name = "TRS_TXT", length = 2500)
    private String languageSpecificTranslation;

    public Translation() {
    }

    public Translation(String key, String languageSpecificTranslation, Locale locale) {
        this.key = key;
        this.languageSpecificTranslation = languageSpecificTranslation;
        this.locale = locale;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLanguageSpecificTranslation() {
        return languageSpecificTranslation;
    }

    public void setLanguageSpecificTranslation(String languageSpecificTranslation) {
        this.languageSpecificTranslation = languageSpecificTranslation;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("key", key)
                .add("locale", locale)
                .add("languageSpecificTranslation", languageSpecificTranslation)
                .toString();
    }
}
