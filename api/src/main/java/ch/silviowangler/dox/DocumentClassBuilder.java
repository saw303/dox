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
package ch.silviowangler.dox;

import ch.silviowangler.dox.api.DocumentClass;

/**
 * Created by Silvio Wangler on 24.12.14.
 *
 * @author Silvio Wangler
 * @since 0.4
 */
public class DocumentClassBuilder {

    private DocumentClass documentClass;

    private DocumentClassBuilder() {
        this.documentClass = new DocumentClass();
    }

    public static DocumentClassBuilder newDocumentClass() {
        return new DocumentClassBuilder();
    }

    public DocumentClassBuilder withShortName(String shortName) {
        this.documentClass.setShortName(shortName);
        return this;
    }

    public DocumentClassBuilder withClient(String clientName) {
        this.documentClass.setClient(clientName);
        return this;
    }

    public DocumentClass build() {
        return this.documentClass;
    }
}
