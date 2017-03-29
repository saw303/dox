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
package ch.silviowangler.dox.api;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@RunWith(Parameterized.class)
public class DocumentReferenceTest {

    private long fileSize;
    private String expectedHumanReadableFilesize;

    public DocumentReferenceTest(long fileSize, String expectedHumanReadableFilesize) {
        this.fileSize = fileSize;
        this.expectedHumanReadableFilesize = expectedHumanReadableFilesize;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return asList(new Object[][]{
                {1000L, "1.0 kB"},
                {1024L, "1.0 kB"},
                {9223372036854775807L, "9.2 EB"},
                {1000000L, "1.0 MB"},
                {1200000L, "1.2 MB"},
                {3900000000L, "3.9 GB"},
                {0L, "0 B"}
        });
    }

    @Test
    public void testHumanReadableFilesize() throws Exception {

        DocumentReference documentReference = new DocumentReference("");
        documentReference.setFileSize(this.fileSize);

        assertThat(documentReference.humanReadableFileSize(), is(this.expectedHumanReadableFilesize));
    }
}
