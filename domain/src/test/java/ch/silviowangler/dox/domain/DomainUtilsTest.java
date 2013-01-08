/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
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

package ch.silviowangler.dox.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 19.08.12 20:17
 *        </div>
 */
public class DomainUtilsTest {

    @Test
    public void containsWildcards1() {
        assertTrue(DomainUtils.containsWildcardCharacters("hsjd*hfj"));
    }

    @Test
    public void containsWildcards2() {
        assertTrue(DomainUtils.containsWildcardCharacters("hsjd?hfj"));
    }

    @Test
    public void containsNoWildcards1() {
        assertFalse(DomainUtils.containsWildcardCharacters("Silvio Wangler"));
    }
}

