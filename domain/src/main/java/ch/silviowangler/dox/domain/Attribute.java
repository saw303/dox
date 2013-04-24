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

package ch.silviowangler.dox.domain;

import com.google.common.base.Objects;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@Entity
@Table(name = "DOX_ATTR", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dataType", "mappingColumn"})
})
public class Attribute extends AbstractPersistable<Long> {

    @Column(unique = true, length = 15, nullable = false)
    private String shortName;
    @Column(nullable = false)
    private boolean optional = false;
    @Enumerated(EnumType.STRING)
    private AttributeDataType dataType;
    @OneToOne
    private Domain domain;
    @ManyToMany(mappedBy = "attributes")
    private Set<DocumentClass> documentClasses;
    @Column(nullable = false, length = 4)
    private String mappingColumn;
    @Column(nullable = false)
    private boolean updateable;

    public boolean isUpdateable() {
        return updateable;
    }

    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public AttributeDataType getDataType() {
        return dataType;
    }

    public void setDataType(AttributeDataType dataType) {
        this.dataType = dataType;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Set<DocumentClass> getDocumentClasses() {
        return documentClasses;
    }

    public void setDocumentClasses(Set<DocumentClass> documentClasses) {
        this.documentClasses = documentClasses;
    }

    public String getMappingColumn() {
        return mappingColumn;
    }

    public void setMappingColumn(String mappingColumn) {
        this.mappingColumn = mappingColumn;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("shortName", shortName)
                .add("optional", optional)
                .add("dataType", dataType)
                .add("domain", domain)
                .add("documentClasses", documentClasses)
                .add("mappingColumn", mappingColumn)
                .add("updateable", updateable)
                .toString();
    }
}
