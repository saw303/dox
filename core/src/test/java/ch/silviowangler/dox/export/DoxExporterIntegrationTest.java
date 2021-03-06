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
package ch.silviowangler.dox.export;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import ch.silviowangler.dox.AbstractIntegrationTest;
import ch.silviowangler.dox.api.DocumentClassNotFoundException;
import ch.silviowangler.dox.api.DocumentDuplicationException;
import ch.silviowangler.dox.api.ValidationException;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class DoxExporterIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private DoxExporter doxExporter;

    @Before
    public void setUp() throws Exception {
        loginAsTestRoot();
    }

    @Test
    public void hello() throws IOException, DocumentClassNotFoundException, DocumentDuplicationException, ValidationException {

        importDocument("document-1p.pdf", "INVOICE");

        doxExporter.export();
    }
}
