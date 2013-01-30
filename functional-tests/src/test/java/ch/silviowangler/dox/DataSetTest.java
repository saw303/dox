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

package ch.silviowangler.dox;

import ch.silviowangler.dox.domain.AttributeRepository;
import ch.silviowangler.dox.domain.DocumentClassRepository;
import ch.silviowangler.dox.domain.Translation;
import ch.silviowangler.dox.domain.TranslationRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class DataSetTest extends AbstractTest {

    @Autowired
    private DocumentClassRepository documentClassRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private TranslationRepository translationRepository;

    @Test
    public void verifyDocumentClassCount() {
        assertEquals("Please verify the numbers of document classes", 3, documentClassRepository.count());
    }

    @Test
    public void verifyGlobalAttributes() {
        assertEquals("Unexpected amount of global attributes", 0, attributeRepository.findGlobalAttributes().size());
    }

    @Test
    public void verifyLocalesOfTranslations() {

        List<Translation> translations = translationRepository.findByKey("DocumentClass:INVOICE");

        assertThat(translations.size(), is(2));

        for (Translation currentTranslation : translations) {
            assertThat(currentTranslation.getLocale(), anyOf(equalTo(GERMAN), equalTo(ENGLISH)));
        }
    }

    @Test
    public void verifyLocaleOfTranslation() {
        Translation translation = translationRepository.findByKeyAndLocale("DocumentClass:TAXES", GERMAN);

        assertThat(translation.getKey(), is("DocumentClass:TAXES"));
        assertThat(translation.getLocale(), is(GERMAN));
        assertThat(translation.getLanguageSpecificTranslation(), is("Steuern"));
    }
}
