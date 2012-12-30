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

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 11.07.12 10:57
 *        </div>
 */
public class DocumentDuplicationException extends Exception {

    private Long documentId;
    private String hash;

    public DocumentDuplicationException(Long documentId, String hash) {
        super("Document hash '" + hash + "' does already exist for document with id " + documentId);
        this.documentId = documentId;
        this.hash = hash;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public String getHash() {
        return hash;
    }
}
