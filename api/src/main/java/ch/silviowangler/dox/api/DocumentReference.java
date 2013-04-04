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

package ch.silviowangler.dox.api;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@TranslateProperties(value = {"indices"})
public class DocumentReference implements Serializable {

    private String hash;
    private Long id;
    private int pageCount;
    private String mimeType;
    private DocumentClass documentClass;
    private Map<TranslatableKey, Object> indices = Maps.newHashMap();
    private String fileName;

    public DocumentReference(String fileName) {
        this.fileName = fileName;
    }

    public DocumentReference(String hash, int pageCount, String mimeType, DocumentClass documentClass, Map<TranslatableKey, Object> indices, String fileName) {
        this(hash, null, pageCount, mimeType, documentClass, indices, fileName);
    }

    public DocumentReference(String hash, Long id, int pageCount, String mimeType, DocumentClass documentClass, Map<TranslatableKey, Object> indices, String fileName) {
        this.hash = hash;
        this.id = id;
        this.pageCount = pageCount;
        this.mimeType = mimeType;
        this.documentClass = documentClass;
        this.indices = indices;
        this.fileName = fileName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public DocumentClass getDocumentClass() {
        return documentClass;
    }

    public void setDocumentClass(DocumentClass documentClass) {
        this.documentClass = documentClass;
    }

    public Map<TranslatableKey, Object> getIndices() {
        return indices;
    }

    public void setIndices(Map<TranslatableKey, Object> indices) {
        this.indices = indices;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "DocumentReference{" +
                "hash='" + hash + '\'' +
                ", id=" + id +
                ", pageCount=" + pageCount +
                ", mimeType='" + mimeType + '\'' +
                ", documentClass=" + documentClass +
                ", indices=" + indices +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentReference that = (DocumentReference) o;

        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hash != null ? hash.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
