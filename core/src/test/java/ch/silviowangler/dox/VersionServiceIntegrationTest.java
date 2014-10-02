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

import ch.silviowangler.dox.api.VersionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

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

        assumeTrue("Test only works in Gradle", !version.startsWith("@") && !version.endsWith("@"));

        // make sure release builds don't break this test
        if (version.contains("SNAPSHOT") || version.contains("RELEASE")) {
            assertTrue("Version does not match " + version, version.matches("0.3-(SNAPSHOT|RELEASE)-\\d{14}"));
        } else if (version.contains("-M")) {
            assertTrue("Version does not match " + version, version.matches("0.3-M\\d-\\d{14}"));
        } else {
            assertTrue("Version does not match " + version, version.matches("0.3-\\d{14}"));
        }
    }
}
