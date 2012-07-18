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

import ch.silviowangler.dox.domain.AttributeRepository;
import ch.silviowangler.dox.domain.DocumentClassRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public class DataSetTest extends AbstractTest {

    @Autowired
    private DocumentClassRepository documentClassRepository;
    @Autowired
    private AttributeRepository attributeRepository;

    @Test
    public void verifyDocumentClassCount() {
        assertEquals("Please verify the numbers of document classes", 1, documentClassRepository.count());
    }

    @Test
    public void verifyGlobalAttributes() {
        assertEquals("Unexpected amount of global attributes", 0, attributeRepository.findGlobalAttributes().size());
    }
}
