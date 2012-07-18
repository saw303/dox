/*
 * Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
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
import java.util.List;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 17.07.12 11:12
 *        </div>
 */
public class Attribute implements Serializable {

    private String shortName;
    private boolean optional = false;
    private List<String> domainValues;
    private AttributeDataType dataType;
    private boolean updateable;

    public Attribute(String shortName, boolean optional, List<String> domainValues, AttributeDataType dataType) {
        this(shortName, optional, domainValues, dataType, true);
    }

    public Attribute(String shortName, boolean optional, List<String> domainValues, AttributeDataType dataType, boolean updateable) {
        this.shortName = shortName;
        this.optional = optional;
        this.domainValues = domainValues;
        this.dataType = dataType;
        this.updateable = updateable;
    }

    public boolean isUpdateable() {
        return updateable;
    }

    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

    public boolean hasDomainValues() {
        return domainValues != null && !domainValues.isEmpty();
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

    public List<String> getDomainValues() {
        return domainValues;
    }

    public void setDomainValues(List<String> domainValues) {
        this.domainValues = domainValues;
    }

    public AttributeDataType getDataType() {
        return dataType;
    }

    public void setDataType(AttributeDataType dataType) {
        this.dataType = dataType;
    }
}
