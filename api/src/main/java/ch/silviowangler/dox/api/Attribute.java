/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.api;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 17.07.12 11:12
 *        </div>
 */
public class Attribute implements Serializable, Comparable<Attribute>, Translatable {

    private String shortName;
    private boolean optional = false;
    private Domain domain;
    private AttributeDataType dataType;
    private boolean modifiable;
    private String translation;
    private String mappingColumn;

    public Attribute(String shortName, boolean optional, AttributeDataType dataType) {
        this(shortName, optional, null, dataType, true);
    }

    public Attribute(String shortName, boolean optional, Domain domain, AttributeDataType dataType, boolean modifiable) {
        this.shortName = shortName;
        this.optional = optional;
        this.domain = domain;
        this.dataType = dataType;
        this.modifiable = modifiable;
    }

    public Attribute(String shortName, boolean optional, Domain domain, AttributeDataType dataType, boolean modifiable, String mappingColumn) {
        this.shortName = shortName;
        this.optional = optional;
        this.domain = domain;
        this.dataType = dataType;
        this.modifiable = modifiable;
        this.mappingColumn = mappingColumn;
    }

    public String getMappingColumn() {
        return mappingColumn;
    }

    public void setMappingColumn(String mappingColumn) {
        this.mappingColumn = mappingColumn;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }

    public boolean containsDomain() {
        return domain != null && !domain.getValues().isEmpty();
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public AttributeDataType getDataType() {
        return dataType;
    }

    public void setDataType(AttributeDataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String getTranslation() {
        return this.translation;
    }

    @Override
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String retrieveKeyPostfix() {
        return this.shortName;
    }

    @Override
    public int compareTo(Attribute o) {
        return this.shortName.compareTo(o.getShortName());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Attribute");
        sb.append("{shortName='").append(shortName).append('\'');
        sb.append(", optional=").append(optional);
        sb.append(", domain=").append(domain);
        sb.append(", dataType=").append(dataType);
        sb.append(", modifiable=").append(modifiable);
        sb.append(", translation='").append(translation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
