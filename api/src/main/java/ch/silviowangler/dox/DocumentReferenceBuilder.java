/**
 * Copyright 2012 - 2018 Silvio Wangler (silvio.wangler@gmail.com)
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

import ch.silviowangler.dox.api.DescriptiveIndex;
import ch.silviowangler.dox.api.DocumentClass;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.TranslatableKey;

/**
 * @author Silvio Wangler
 * @since 1.9.1
 */
public class DocumentReferenceBuilder {

    private DocumentReference documentReference;

    private DocumentReferenceBuilder(String filename) {
        this.documentReference = new DocumentReference(filename);
    }

    public static DocumentReferenceBuilder newDocumentReference(String fileName) {
        return new DocumentReferenceBuilder(fileName);
    }

    public DocumentReferenceBuilder withDocumentClass(String documentClassShortName) {
        return withDocumentClass(new DocumentClass(documentClassShortName));
    }

    public DocumentReferenceBuilder withDocumentClass(DocumentClass documentClass) {
        this.documentReference.setDocumentClass(documentClass);
        return this;
    }

    public DocumentReference build() {
        return this.documentReference;
    }

    public DocumentReferenceBuilder withIndex(String indexName, Object indexValue) {
        this.documentReference.getIndices().put(new TranslatableKey(indexName), new DescriptiveIndex(indexValue));
        return this;
    }
}
