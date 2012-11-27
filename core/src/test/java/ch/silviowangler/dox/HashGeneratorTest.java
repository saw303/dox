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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@RunWith(value = Parameterized.class)
public class HashGeneratorTest {

    private File file;
    private String expectedHash;

    public HashGeneratorTest(String expectedHash, File file) {
        this.expectedHash = expectedHash;
        this.file = file;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(
                new Object[]{(isLinux() ? "c2c9274416c853a6a5b2f77abc4377f4db0c830936c723b56e88aaccc2589707" : "1f726f5ebbd97a8879f6f4fa8e2f63bd199609d8af4ae022ec42037c2e87d528"), loadFile("hello.txt")},
                new Object[]{(isLinux() ? "3646d48fbd1dfb5aa76f0d26ec709319a9892407a7bf0aa7c31e75d779b7e6cc" : "51dabdb8d7e347706260691e3c3447f51c7e747f6b15ddcffd5b9c2ed15ddef7"), loadFile("hello2.txt")}
        );

    }

    private static boolean isLinux() {
        return System.getProperty("os.name").contains("Linux");
    }

    private static File loadFile(String fileName) {
        URL resource = HashGenerator.class.getClassLoader().getResource(fileName);
        assertNotNull("Resource '" + fileName + "' could not be found", resource);
        return FileUtils.toFile(resource);
    }

    @Test
    public void generateSha256HexString() throws IOException {
        String hexHash = HashGenerator.sha256Hex(this.file);
        assertThat(hexHash.length(), is(64));
        assertThat(hexHash, is(this.expectedHash));
    }
}
