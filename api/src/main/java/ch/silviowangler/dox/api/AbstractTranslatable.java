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

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public abstract class AbstractTranslatable implements Translatable {

    private String keyPrefix;

    protected AbstractTranslatable() {
        this.keyPrefix = getClass().getSimpleName();
    }

    protected void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @Override
    public String retrieveMessageKey() {
        return this.keyPrefix + ":" + retrieveKeyPostfix();
    }
}
