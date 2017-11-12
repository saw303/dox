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
package ch.silviowangler.dox.api;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class MissingDocument implements Serializable {
    private String hash;
    private Source source;

    public MissingDocument(String hash, Source source) {
        this.hash = hash;
        this.source = source;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MissingDocument{");
        sb.append("hash='").append(hash).append('\'');
        sb.append(", source=").append(source);
        sb.append('}');
        return sb.toString();
    }
}
