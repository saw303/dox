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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 15.07.12 18:49
 *        </div>
 */
@Entity
@Table(name = "DOX_IDX_MAP", indexes = {@Index(name = "IDX_STR_VAL", columnList = "stringRepresentation")})
public class IndexMapEntry extends AbstractPersistable<Long> {

    @Column(nullable = false, length = 15)
    private String attributeName;
    @Column(nullable = false, length = 255)
    private String stringRepresentation;
    @OneToOne
    @JoinColumn
    private Document document;

    public IndexMapEntry() {
    }

    public IndexMapEntry(String attributeName, String stringRepresentation, Document document) {
        this.attributeName = attributeName;
        this.stringRepresentation = stringRepresentation;
        this.document = document;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public void setStringRepresentation(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("attributeName", attributeName)
                .add("stringRepresentation", stringRepresentation)
                .add("document", document)
                .toString();
    }
}
