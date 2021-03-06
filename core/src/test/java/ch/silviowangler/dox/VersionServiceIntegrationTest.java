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

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.silviowangler.dox.api.VersionService;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class VersionServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private VersionService versionService;

    @Test
    public void verifyVersion() {
        final String version = versionService.fetchVersion().getVersion();

        // Test only works in Gradle
        assumeTrue(!version.startsWith("@") && !version.endsWith("@"));

        // make sure release builds don't break this test
        if (version.contains("SNAPSHOT") || version.contains("RELEASE")) {
            assertTrue("Version does not match " + version, version.matches("(\\d\\.?)+-(SNAPSHOT|RELEASE)-\\d{14}"));
        } else if (version.contains("-M")) {
            assertTrue("Version does not match " + version, version.matches("(\\d\\.?)+-M\\d-\\d{14}"));
        } else {
            assertTrue("Version does not match " + version, version.matches("(\\d\\.?)+-\\d{14}"));
        }
    }
}
