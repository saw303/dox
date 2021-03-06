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
package ch.silviowangler.dox;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import ch.silviowangler.dox.domain.Translation;
import ch.silviowangler.dox.repository.AttributeRepository;
import ch.silviowangler.dox.repository.DocumentClassRepository;
import ch.silviowangler.dox.repository.TranslationRepository;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class DataSetIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private DocumentClassRepository documentClassRepository;
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private TranslationRepository translationRepository;

    @Test
    public void verifyDocumentClassCount() {
        assertEquals("Please verify the numbers of document classes", 8, documentClassRepository.count());
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
