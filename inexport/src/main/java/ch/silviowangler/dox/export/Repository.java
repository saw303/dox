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

package ch.silviowangler.dox.export;

import ch.silviowangler.dox.DoxVersion;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class Repository implements Serializable {

    private Set<DocumentClass> documentClasses = new HashSet<>();
    private DoxVersion version;
    private Set<Translation> translations = new HashSet<>();

    public Set<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Translation> translations) {
        this.translations = translations;
    }

    public Set<DocumentClass> getDocumentClasses() {
        return documentClasses;
    }


    public void setDocumentClasses(Set<DocumentClass> documentClasses) {
        this.documentClasses = documentClasses;
    }

    public DoxVersion getVersion() {
        return version;
    }

    public void setVersion(DoxVersion version) {
        this.version = version;
    }
}
