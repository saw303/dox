/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
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

package ch.silviowangler.dox.export;

import ch.silviowangler.dox.api.AttributeDataType;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 09.01.13 08:34
 *        </div>
 */
public class Attribute implements Serializable {

    private String shortName;
    private boolean optional;
    private AttributeDataType dataType;
    private Set<String> assignedDocumentClasses;
    private boolean modifiable;
    private Domain domain;
    private String mappingColumn;

    public String getMappingColumn() {
        return mappingColumn;
    }

    public void setMappingColumn(String mappingColumn) {
        this.mappingColumn = mappingColumn;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Set<String> getAssignedDocumentClasses() {
        return assignedDocumentClasses;
    }

    public void setAssignedDocumentClasses(Set<String> assignedDocumentClasses) {
        this.assignedDocumentClasses = assignedDocumentClasses;
    }

    public AttributeDataType getDataType() {
        return dataType;
    }

    public void setDataType(AttributeDataType dataType) {
        this.dataType = dataType;
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

    public boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }
}
