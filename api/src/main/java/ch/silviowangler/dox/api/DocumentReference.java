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

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static ch.silviowangler.dox.util.FileUtils.humanReadableByteCount;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@TranslateProperties(value = {"indices", "documentClass"})
public class DocumentReference implements Serializable {

    private String hash;
    private Long id;
    private int pageCount;
    private String mimeType;
    private DocumentClass documentClass;
    private Map<TranslatableKey, DescriptiveIndex> indices = Maps.newHashMap();
    private String fileName;
    private String userReference;
    private long fileSize;
    private DateTime creationDate;
    private String client;
    private Set<String> tags = Sets.newHashSet();

    public DocumentReference() {
        super();
    }

    public DocumentReference(String fileName) {
        this.fileName = fileName;
    }

    public DocumentReference(String hash, int pageCount, String mimeType, DocumentClass documentClass, Map<TranslatableKey, DescriptiveIndex> indices, String fileName) {
        this(hash, null, pageCount, mimeType, documentClass, indices, fileName);
    }

    public DocumentReference(String hash, Long id, int pageCount, String mimeType, DocumentClass documentClass, Map<TranslatableKey, DescriptiveIndex> indices, String fileName) {
        this(hash, id, pageCount, mimeType, documentClass, indices, fileName, null, -1L);
    }

    public DocumentReference(String hash, Long id, int pageCount, String mimeType, DocumentClass documentClass, Map<TranslatableKey, DescriptiveIndex> indices, String fileName, String userName, long fileSize) {
        this.hash = hash;
        this.id = id;
        this.pageCount = pageCount;
        this.mimeType = mimeType;
        this.documentClass = documentClass;
        this.indices = indices;
        this.fileName = fileName;
        this.userReference = userName;
        this.fileSize = fileSize;
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

    public Map<TranslatableKey, DescriptiveIndex> getIndices() {
        return indices;
    }

    public void setIndices(Map<TranslatableKey, DescriptiveIndex> indices) {
        this.indices = indices;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserReference() {
        return userReference;
    }

    public void setUserReference(String userReference) {
        this.userReference = userReference;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String humanReadableFileSize() {
        return humanReadableByteCount(this.fileSize, true);
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hash", hash)
                .add("id", id)
                .add("pageCount", pageCount)
                .add("mimeType", mimeType)
                .add("documentClass", documentClass)
                .add("indices", indices)
                .add("fileName", fileName)
                .add("userReference", userReference)
                .add("fileSize", fileSize)
                .add("creationDate", creationDate)
                .add("client", client)
                .add("tags", tags)
                .toString();
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
