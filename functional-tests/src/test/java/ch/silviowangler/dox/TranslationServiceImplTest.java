/*
 * Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
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
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class TranslationServiceImplTest extends AbstractTest {

    @Autowired
    private TranslationService translationService;

    @Test
    public void readTranslation() throws NoTranslationFoundException {
        assertThat(translationService.findTranslation("Domain:company", GERMAN), is("Firma"));
        assertThat(translationService.findTranslation("Domain:company", new Locale("de", "CH")), is("Firma"));
        assertThat(translationService.findTranslation("Domain:company", ENGLISH), is("Company"));
    }
}