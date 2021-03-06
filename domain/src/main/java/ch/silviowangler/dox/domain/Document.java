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

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ch.silviowangler.dox.domain.stats.Tag;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@Entity
@Table(name = "DOX_DOC")
public class Document extends AbstractPersistable<Long> {

    @Column(unique = true, nullable = false, length = 64)
    private String hash;
    @ManyToOne(optional = false)
    private DocumentClass documentClass;
    @Column(nullable = false)
    private Integer pageCount = -1;
    @Column(nullable = false, length = 100)
    private String mimeType;
    @Column(nullable = false, length = 255)
    private String originalFilename;
    @OneToOne(optional = true, orphanRemoval = true)
    private IndexStore indexStore;
    @Column(nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationDate = DateTime.now();
    @OneToMany(mappedBy = "document", orphanRemoval = true)
    private Set<IndexMapEntry> indexMapEntries;
    @Column(nullable = false, length = 25)
    private String userReference;
    @Column(nullable = false)
    private long fileSize = -1L;
    @ManyToOne(optional = false)
    private Client client;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

    public Document() {
        super();
    }

    public Document(String hash, DocumentClass documentClass, Integer pageCount, String mimeType, String originalFilename, IndexStore indexStore, String userReference) {
        super();
        this.hash = hash;
        this.documentClass = documentClass;
        this.pageCount = pageCount;
        this.mimeType = mimeType;
        this.originalFilename = originalFilename;
        this.indexStore = indexStore;
        this.userReference = userReference;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public DocumentClass getDocumentClass() {
        return documentClass;
    }

    public void setDocumentClass(DocumentClass documentClass) {
        this.documentClass = documentClass;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public IndexStore getIndexStore() {
        return indexStore;
    }

    public void setIndexStore(IndexStore indexStore) {
        this.indexStore = indexStore;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hash", hash)
                .add("documentClass", documentClass)
                .add("pageCount", pageCount)
                .add("mimeType", mimeType)
                .add("originalFilename", originalFilename)
                .add("indexStore", indexStore)
                .add("creationDate", creationDate)
                .add("indexMapEntries", indexMapEntries)
                .add("userReference", userReference)
                .add("fileSize", fileSize)
                .add("client", client)
                .toString();
    }
}
