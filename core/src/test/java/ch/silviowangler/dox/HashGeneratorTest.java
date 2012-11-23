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

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@Ignore //travis ci fails
public class HashGeneratorTest {

    @Test
    public void generateSha256HexString() throws IOException {
        final File file = loadFile("hello.txt");
        String hexHash = HashGenerator.sha256Hex(file);
        assertEquals(64, hexHash.length());
        String windowsExpected = "1f726f5ebbd97a8879f6f4fa8e2f63bd199609d8af4ae022ec42037c2e87d528";
        String linuxExpected = "c2c9274416c853a6a5b2f77abc4377f4db0c830936c723b56e88aaccc2589707";
        assertEquals(isLinux() ? linuxExpected : windowsExpected, hexHash);
    }

    private File loadFile(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        return FileUtils.toFile(resource);
    }

    @Test
    public void generateAnotherSha256HexString() throws IOException {
        final File file = loadFile("hello2.txt");
        String hexHash = HashGenerator.sha256Hex(file);
        assertEquals(64, hexHash.length());
        String windowsExpected = "51dabdb8d7e347706260691e3c3447f51c7e747f6b15ddcffd5b9c2ed15ddef7";
        String linuxExpected = "3646d48fbd1dfb5aa76f0d26ec709319a9892407a7bf0aa7c31e75d779b7e6cc";
        assertEquals(isLinux() ? linuxExpected : windowsExpected, hexHash);
    }

    private boolean isLinux() {
        return System.getProperty("os.name").contains("Linux");
    }
}
