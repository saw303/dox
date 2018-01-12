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
package ch.silviowangler.dox.repository;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class DocumentKeyHash {

    private Long id;
    private String hash;

    public DocumentKeyHash(Long id, String hash) {
        this.id = id;
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentKeyHash)) return false;

        DocumentKeyHash that = (DocumentKeyHash) o;

        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocumentKeyHash{");
        sb.append("id=").append(id);
        sb.append(", hash='").append(hash).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
