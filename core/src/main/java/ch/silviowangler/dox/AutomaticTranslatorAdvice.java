/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
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
import ch.silviowangler.dox.api.Translatable;
import ch.silviowangler.dox.api.TranslateProperties;
import ch.silviowangler.dox.api.TranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@Component
public class AutomaticTranslatorAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TranslationService translationService;
    @Autowired
    private MessageSource messageSource;

    public void addTranslationIfNeeded(Object retVal) throws Throwable {

        translateWhenPossible(retVal);

        TranslateProperties translateProperties = retVal.getClass().getAnnotation(TranslateProperties.class);
        if (translateProperties != null) {
            final String className = retVal.getClass().getName();
            logger.debug("detected class that declares translatable properties {}", className);

            for (String propertyName : translateProperties.value()) {
                logger.trace("About to inspect property {} of class {}", propertyName, className);
                Object propertyValue = new PropertyDescriptor(propertyName, retVal.getClass()).getReadMethod().invoke(retVal);

                if (propertyValue != null) {
                    logger.trace("Property {} is class {}", propertyName, propertyValue.getClass().getName());
                    translateWhenPossible(propertyValue);
                }
                else {
                    logger.trace("Property {} has null value", propertyName);
                }
            }
        }
        logger.debug("Return value is {}", retVal);
    }

    private void translateWhenPossible(Object translationCandidate) {

        if (isTranslatable(translationCandidate)) {
            Translatable translatable = (Translatable) translationCandidate;
            translate(translatable);
        }

        if (translationCandidate instanceof Collection) {
            for (Object o : (Collection) translationCandidate) {
                try {
                    addTranslationIfNeeded(o);
                } catch (Throwable throwable) {
                    logger.error("Unable to translate", throwable);
                }
            }
        }

        if (translationCandidate instanceof Object[]) {
            for (Object o : (Object[]) translationCandidate) {
                if (isTranslatable(o)) translate((Translatable) o);
            }
        }

        if (translationCandidate instanceof Map) {

            Map map = (Map) translationCandidate;
            for (Object key : map.keySet()) {
                if (isTranslatable(key)) {
                    translate((Translatable) key);
                }
            }
        }
    }

    private boolean isTranslatable(Object retVal) {
        boolean translatable;

        if (retVal instanceof Class) {
            translatable = Translatable.class.isAssignableFrom((Class) retVal);
        } else {
            translatable = retVal instanceof Translatable;
        }
        logger.trace("Is object '{}' translatable? {}", retVal.getClass().getName(), translatable);
        return translatable;
    }

    private void translate(Translatable translatable) {

        logger.debug("Detected translatable in class {}", translatable.getClass().getName());

        final String messageKey = translatable.retrieveMessageKey();
        final Locale locale = LocaleContextHolder.getLocale();
        try {
            String translation = translationService.findTranslation(messageKey, locale);
            translatable.setTranslation(translation);
        } catch (NoTranslationFoundException e) {
            String message = getTranslation(messageKey, locale);
            translatable.setTranslation(message);
        }

        final Field[] declaredFields = translatable.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            if (isTranslatable(field.getType())) {
                try {
                    Translatable objectToTranslate = (Translatable) new PropertyDescriptor(field.getName(), translatable.getClass()).getReadMethod().invoke(translatable);
                    if (objectToTranslate != null) {
                        translate(objectToTranslate);
                    }
                } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
                    logger.error("Unable to translate", e);
                }
            }
        }
    }

    private String getTranslation(String messageKey, Locale locale) {
        try {
            return messageSource.getMessage("translationadvice.no.translation.available", new Object[]{messageKey, locale}, locale);
        } catch (NoSuchMessageException e1) {
            if (Locale.GERMAN.equals(locale)) {
                throw new NoSuchMessageException(messageKey, locale);
            }
            return getTranslation(messageKey, Locale.GERMAN);
        }
    }
}
