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

import java.util.Locale;
import java.util.Set;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public interface TranslationService {

    /**
     * Attempts to retrieve a translation for a specific translation key and a locale. If there is no translation available
     * for the locale it falls back to the language and executes the query again.
     * <p/>
     * Example
     * <p/>
     * 1. Locale de_CH
     * 2. Locale de (2nd attempt if 1st attempt results in nothing)
     * 3. Throw exception (if 2nd attempt results in nothing)
     *
     * @param key    of the translation
     * @param locale of the wanted translation
     * @return the translation closest to the locale
     * @throws NoTranslationFoundException if there is no translation available for the key and the locale.
     */
    String findTranslation(String key, Locale locale) throws NoTranslationFoundException;

    /**
     * Retrieves all translations within the repository
     *
     * @return all translations within the repository
     */
    Set<Translation> findAll();
}
