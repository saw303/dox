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

/**
 * @author Silvio Wangler
 * @see TranslationService
 * @since 0.1
 *        <p/>
 *        This interface indicates that a e.g. a value object is translatable using the TranslationService
 */
public interface Translatable {

    /**
     * Retrieves the postfix of the key
     *
     * @return key postfix
     */
    public String retrieveKeyPostfix();

    /**
     * Retrieve the translation
     *
     * @return the translated text
     */
    public String getTranslation();

    /**
     * Set the translation
     *
     * @param translation translated text
     */
    public void setTranslation(String translation);
}
