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

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
public class DoxVersionTest {

    private DoxVersion doxVersion;

    @Test
    public void testDoxVersion() throws Exception {

        this.doxVersion = new DoxVersion("0.2-SNAPSHOT-20130425160912");

        assertThat(this.doxVersion.getVersion(), is("0.2-SNAPSHOT-20130425160912"));
    }

    @Test
    public void testFormatDate() throws Exception {

        this.doxVersion = new DoxVersion("0.2-SNAPSHOT-20130425160912");
        assertThat(this.doxVersion.formatVersion(), is("0.2-SNAPSHOT (25.04.2013 16:09:12)"));
    }

    @Test
    public void testFormatDate2() throws Exception {

        this.doxVersion = new DoxVersion("0.2-SNAPSHOT-20140831010959");
        assertThat(this.doxVersion.formatVersion(), is("0.2-SNAPSHOT (31.08.2014 01:09:59)"));
    }

    @Test
    public void testReleaseFormatDate() throws Exception {

        this.doxVersion = new DoxVersion("0.2-20180831010959");
        assertThat(this.doxVersion.formatVersion(), is("0.2 (31.08.2018 01:09:59)"));
    }

    @Test
    public void testFormatDate3() throws Exception {

        this.doxVersion = new DoxVersion("0.2-SNAPSHOT");
        assertThat(this.doxVersion.formatVersion(), is("invalid"));
    }

    @Test
    public void testFormatDateIfPlaceholderIsPresent() throws Exception {

        this.doxVersion = new DoxVersion("@dox.app.version@");
        assertThat(this.doxVersion.formatVersion(), is("<development mode>"));
    }

}
