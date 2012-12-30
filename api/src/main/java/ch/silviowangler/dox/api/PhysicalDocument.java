/*
 * Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
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

import java.util.Map;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class PhysicalDocument extends DocumentReference {

    private byte[] content;

    public PhysicalDocument() {
        this(null, null, null, null);
    }

    /**
     * Use this constructor in order to import a document
     *
     * @param documentClass
     * @param content
     * @param indexes
     */
    public PhysicalDocument(DocumentClass documentClass, byte[] content, Map<String, Object> indexes, String fileName) {
        this(null, -1, null, documentClass, content, indexes, fileName);
    }

    public PhysicalDocument(String hash, int pageCount, String mimeType, DocumentClass documentClass, byte[] content, Map<String, Object> indexes, String fileName) {
        this(hash, null, pageCount, mimeType, documentClass, content, indexes, fileName);
    }

    public PhysicalDocument(String hash, Long id, int pageCount, String mimeType, DocumentClass documentClass, byte[] content, Map<String, Object> indexes, String fileName) {
        super(hash, id, pageCount, mimeType, documentClass, indexes, fileName);
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
