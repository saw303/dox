/**
 * Copyright 2012 - 2018 Silvio Wangler (silvio.wangler@gmail.com)
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

/**
 * Created by saw303 on 25.06.14.
 */
@TranslateProperties(value = {"attribute"})
public class DescriptiveIndex extends Index {

    private Attribute attribute;

    public DescriptiveIndex(Object value) {
        super.setValue(value);
    }

    public DescriptiveIndex(Object value, Attribute attribute) {
        super.setValue(value);
        this.attribute = attribute;
    }

    public DescriptiveIndex() {
        super();
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DescriptiveIndex)) return false;
        if (!super.equals(o)) return false;

        DescriptiveIndex that = (DescriptiveIndex) o;

        if (attribute != null ? !attribute.equals(that.attribute) : that.attribute != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        return result;
    }
}
