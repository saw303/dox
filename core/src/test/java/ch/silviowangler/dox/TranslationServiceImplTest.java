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

import static java.util.Locale.GERMAN;
import static java.util.Locale.GERMANY;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ch.silviowangler.dox.api.NoTranslationFoundException;
import ch.silviowangler.dox.api.TranslationService;
import ch.silviowangler.dox.domain.Translation;
import ch.silviowangler.dox.repository.TranslationRepository;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class TranslationServiceImplTest {

    @Mock
    private TranslationRepository translationRepository;
    @InjectMocks
    private TranslationService translationService = new TranslationServiceImpl();

    @Test
    public void testFindTranslation() throws Exception {
        final String key = "hello.yellow";
        when(translationRepository.findByKeyAndLocale(key, GERMANY)).thenReturn(new Translation(key, "Ich bin ein Text", GERMANY));

        String translatedText = translationService.findTranslation(key, GERMANY);

        assertThat(translatedText, is("Ich bin ein Text"));

        verify(translationRepository).findByKeyAndLocale(key, GERMANY);
        verifyNoMoreInteractions(translationRepository);
    }

    @Test
    public void testFindTranslationUsingFallBack() throws Exception {
        final String key = "hello.yellow";
        when(translationRepository.findByKeyAndLocale(key, GERMANY)).thenReturn(null);
        when(translationRepository.findByKeyAndLocale(key, GERMAN)).thenReturn(new Translation(key, "Ich bin ein deutscher Text", GERMAN));

        String translatedText = translationService.findTranslation(key, GERMANY);

        assertThat(translatedText, is("Ich bin ein deutscher Text"));

        InOrder order = inOrder(translationRepository);
        order.verify(translationRepository).findByKeyAndLocale(key, GERMANY);
        order.verify(translationRepository).findByKeyAndLocale(key, GERMAN);
        verifyNoMoreInteractions(translationRepository);
    }

    @Test
    public void testFindTranslationUsingFallBackWithException() throws Exception {
        final String key = "hello.yellow";
        when(translationRepository.findByKeyAndLocale(key, GERMANY)).thenReturn(null);
        when(translationRepository.findByKeyAndLocale(key, GERMAN)).thenReturn(null);

        try {
            translationService.findTranslation(key, GERMANY);
            fail("No exception was thrown");
        } catch (NoTranslationFoundException e) {
            // ok
        } finally {
            InOrder order = inOrder(translationRepository);
            order.verify(translationRepository).findByKeyAndLocale(key, GERMANY);
            order.verify(translationRepository).findByKeyAndLocale(key, GERMAN);
            verifyNoMoreInteractions(translationRepository);
        }
    }
}
