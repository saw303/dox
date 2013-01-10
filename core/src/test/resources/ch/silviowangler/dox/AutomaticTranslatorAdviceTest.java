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

import ch.silviowangler.dox.api.Attribute;
import ch.silviowangler.dox.api.NoTranslationFoundException;
import ch.silviowangler.dox.api.TranslationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ch.silviowangler.dox.api.AttributeDataType.STRING;
import static java.util.Locale.ENGLISH;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class AutomaticTranslatorAdviceTest {

    @InjectMocks
    private AutomaticTranslatorAdvice advice = new AutomaticTranslatorAdvice();
    @Mock
    private TranslationService translationService;
    @Mock
    private MessageSource messageSource;

    @Test
    public void testAddTranslationIfNeeded() throws Throwable {

        LocaleContextHolder.setLocale(ENGLISH);

        final String expectedTranslation = "This is an english text";
        when(translationService.findTranslation("Attribute:hello", ENGLISH)).thenReturn(expectedTranslation);

        Attribute attribute = new Attribute("hello", true, new ArrayList<String>(), STRING, "S_1");

        assertNull(attribute.getTranslation());

        advice.addTranslationIfNeeded(attribute);

        assertThat(attribute.getTranslation(), is(expectedTranslation));
    }

    @Test
    public void testAddTranslationIfNeededInCollections() throws Throwable {

        LocaleContextHolder.setLocale(ENGLISH);

        final String expectedTranslation = "This is an english text";
        when(translationService.findTranslation("Attribute:hello", ENGLISH)).thenReturn(expectedTranslation);
        when(translationService.findTranslation("Attribute:and good bye", ENGLISH)).thenReturn(expectedTranslation);

        List<Attribute> list = Arrays.asList(new Attribute("hello", true, new ArrayList<String>(), STRING, "S_1"), new Attribute("and good bye", true, new ArrayList<String>(), STRING, "S_2"));
        advice.addTranslationIfNeeded(list);

        for (Attribute attribute : list) {
            assertThat(attribute.getTranslation(), is(expectedTranslation));
        }
    }

    @Test
    public void testAddTranslationIfNeededInArrays() throws Throwable {

        LocaleContextHolder.setLocale(ENGLISH);

        final String expectedTranslation = "This is an english text";
        when(translationService.findTranslation("Attribute:hello", ENGLISH)).thenReturn(expectedTranslation);
        when(translationService.findTranslation("Attribute:and good bye", ENGLISH)).thenReturn(expectedTranslation);

        Attribute[] array = {new Attribute("hello", true, new ArrayList<String>(), STRING, "S_1"), new Attribute("and good bye", true, new ArrayList<String>(), STRING, "S_2")};
        advice.addTranslationIfNeeded(array);

        for (Attribute attribute : array) {
            assertThat(attribute.getTranslation(), is(expectedTranslation));
        }
    }

    @Test
    public void testAddTranslationIfNeededInException() throws Throwable {

        LocaleContextHolder.setLocale(ENGLISH);

        when(translationService.findTranslation("Attribute:hello", ENGLISH)).thenThrow(new NoTranslationFoundException("hello", ENGLISH));
        final String expectedTranslation = "No translation available for key 'hello' and locale ''";
        when(messageSource.getMessage("translationadvice.no.translation.available", new Object[]{"Attribute:hello", ENGLISH}, ENGLISH)).thenReturn(expectedTranslation);

        Attribute attribute = new Attribute("hello", true, new ArrayList<String>(), STRING, "S_1");

        assertNull(attribute.getTranslation());

        advice.addTranslationIfNeeded(attribute);

        assertThat(attribute.getTranslation(), is(expectedTranslation));
    }
}
