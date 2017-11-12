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

import ch.silviowangler.dox.api.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class LoadIntegrationTest extends AbstractIntegrationTest {


    private static final int TOTAL_AMOUNT_OF_FILES = 200;
    private static final int TOTAL_AMOUNT_OF_TIME_IN_MILLIS = 1300;

    @Before
    public void setUp() throws Exception {

        loginAsTestRoot();

        Calendar calendar = new GregorianCalendar(2013, Calendar.JANUARY, 1);

        Map<String, SortedSet<Attribute>> cache = new HashMap<>();

        for (int i = 1; i <= TOTAL_AMOUNT_OF_FILES; i++) {

            Map<TranslatableKey, DescriptiveIndex> indices = new HashMap<>();

            SortedSet<Attribute> attributes;
            if (!cache.containsKey("INVOICE")) {
                attributes = documentService.findAttributes(new DocumentClass("INVOICE"));
                cache.put("INVOICE", attributes);
            } else {
                attributes = cache.get("INVOICE");
            }

            for (Attribute attribute : attributes) {
                if (!attribute.isOptional()) {

                    Object o;

                    switch (attribute.getDataType()) {
                        case SHORT:
                        case LONG:
                        case DOUBLE:
                        case STRING:
                        case INTEGER:

                            o = Integer.toString(i);
                            break;

                        case BOOLEAN:

                            o = (i % 2 == 0);
                            break;

                        case DATE:
                            calendar.add(Calendar.DAY_OF_YEAR, 1);
                            o = calendar.getTime();
                            break;

                        case CURRENCY:
                            o = "CHF " + i;
                            break;

                        default:
                            throw new IllegalArgumentException("Unknown attribute type: " + attribute.getDataType());
                    }
                    indices.put(new TranslatableKey(attribute.getShortName()), new DescriptiveIndex(o));
                }
            }

            importFile(i + ".txt", Integer.toString(i), "INVOICE", indices);
        }

        loginAsTestRoot();
    }

    @Test
    @Ignore("Needs rework since it produces unreliable results")
    public void testQuery() throws Exception {

        StopWatch stopWatch = new StopWatch();

        Map<TranslatableKey, DescriptiveIndex> indices = new HashMap<>();

        TranslatableKey company = new TranslatableKey("company");
        indices.put(company, new DescriptiveIndex("3?"));

        stopWatch.start();
        Set<DocumentReference> invoices = documentService.findDocumentReferences(indices, "INVOICE");
        stopWatch.stop();

        final long totalTimeMillis = stopWatch.getTotalTimeMillis();
        assertTrue("This test may take only " + TOTAL_AMOUNT_OF_TIME_IN_MILLIS + " ms but took this time " + totalTimeMillis + " ms", totalTimeMillis <= TOTAL_AMOUNT_OF_TIME_IN_MILLIS);

        for (DocumentReference documentReference : invoices) {
            String value = documentReference.getIndices().get(company).toString();
            assertTrue("Value is wrong: " + value, value.matches("(3|3\\d)"));
        }
        assertThat(invoices.size(), is(11));
    }
}
