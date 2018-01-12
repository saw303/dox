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
package ch.silviowangler.dox;

import ch.silviowangler.dox.api.NoTranslationFoundException;
import ch.silviowangler.dox.api.TranslationService;
import ch.silviowangler.dox.domain.Translation;
import ch.silviowangler.dox.repository.TranslationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Locale.GERMAN;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@Service("translationService")
public class TranslationServiceImpl implements TranslationService {

    @Autowired
    private TranslationRepository translationRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional(readOnly = true, propagation = SUPPORTS)
    public Set<ch.silviowangler.dox.api.Translation> findAll() {

        Iterable<Translation> translations = translationRepository.findAll();
        Set<ch.silviowangler.dox.api.Translation> translationsApi = StreamSupport.stream(translations.spliterator(), false)
                .map(t -> new ch.silviowangler.dox.api.Translation(t.getKey(), t.getLocale(), t.getLanguageSpecificTranslation()))
                .collect(Collectors.toSet());

        return translationsApi;
    }

    @Override
    @Transactional(readOnly = true, propagation = SUPPORTS)
    public String findTranslation(String key, Locale locale) throws NoTranslationFoundException {

        logger.debug("Trying to find message for key '{}' and locale '{}'", key, locale.getDisplayName());

        Translation translation = translationRepository.findByKeyAndLocale(key, locale);

        if (translation == null) {
            final Locale fallbackLocale = new Locale(locale.getLanguage());
            logger.debug("No translation found for key '{}' and locale '{}'. Falling back to locale '{}'", new Object[]{key, locale.getDisplayName(), fallbackLocale.getDisplayName()});
            translation = translationRepository.findByKeyAndLocale(key, fallbackLocale);

            if (translation == null && !fallbackLocale.equals(GERMAN)) {

                translation = translationRepository.findByKeyAndLocale(key, GERMAN);

                if (translation == null) {
                    logger.warn("No translation found for key '{}' and locale '{}'", key, locale);
                    throw new NoTranslationFoundException(key, locale);
                }
                return translation.getLanguageSpecificTranslation();

            } else {
                logger.debug("Found translation of key '{}' using fallback locale '{}'", key, fallbackLocale.getDisplayName());
                if (translation == null) throw new NoTranslationFoundException(key, locale);
                return translation.getLanguageSpecificTranslation();
            }
        } else {
            logger.debug("Found translation of key '{}' on first attempt using locale '{}'", key, locale.getDisplayName());
            return translation.getLanguageSpecificTranslation();
        }
    }
}
