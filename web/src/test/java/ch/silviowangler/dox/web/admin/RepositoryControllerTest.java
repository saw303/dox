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

package ch.silviowangler.dox.web.admin;

import ch.silviowangler.dox.export.DoxExporter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Properties;

import static com.google.common.net.HttpHeaders.CONTENT_DISPOSITION;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @version 0.2
 * @since Silvio Wangler
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryControllerTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @InjectMocks
    private RepositoryController controller = new RepositoryController();
    @Mock
    private DoxExporter doxExporter;
    @Mock
    private Properties mimeTypes;

    @Test
    public void testGetDocument() throws Exception {

        when(doxExporter.export()).thenReturn(folder.newFile("archive.zip"));
        when(mimeTypes.getProperty("zip")).thenReturn("my content type");

        MockHttpServletResponse response = new MockHttpServletResponse();

        controller.getDocument(response);

        assertThat(response.getContentType(), is("my content type"));
        assertThat(response.containsHeader(CONTENT_DISPOSITION), is(true));

    }
}
