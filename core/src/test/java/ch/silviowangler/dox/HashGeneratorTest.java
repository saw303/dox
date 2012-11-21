/*
 * Copyright 2012 Silvio Wangler (silvio.wangler@gmail.com)
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

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class HashGeneratorTest {

    @Test
    public void generateSha256HexString() throws IOException {
        final File file = loadFile("hello.txt");
        String hexHash = HashGenerator.sha256Hex(file);
        assertEquals(64, hexHash.length());
        assertEquals("c2c9274416c853a6a5b2f77abc4377f4db0c830936c723b56e88aaccc2589707", hexHash);
    }

    private File loadFile(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        assertNotNull("Resource '" + fileName + "' could not be found", resource);
        return FileUtils.toFile(resource);
    }

    @Test
    public void generateAnotherSha256HexString() throws IOException {
        final File file = loadFile("hello2.txt");
        String hexHash = HashGenerator.sha256Hex(file);
        assertEquals(64, hexHash.length());
        assertEquals("3646d48fbd1dfb5aa76f0d26ec709319a9892407a7bf0aa7c31e75d779b7e6cc", hexHash);
    }
}
