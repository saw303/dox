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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import static com.google.common.net.HttpHeaders.CONTENT_DISPOSITION;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    @Mock
    private ServletOutputStream outputStream;
    @Mock
    private HttpServletResponse response;

    @Test
    public void testGetDocument() throws Exception {

        when(doxExporter.export()).thenReturn(folder.newFile("archive.zip"));
        when(mimeTypes.getProperty("zip")).thenReturn("my content type");

        MockHttpServletResponse response = new MockHttpServletResponse();

        controller.getDocument(response);

        assertThat(response.getContentType(), is("my content type"));
        assertThat(response.containsHeader(CONTENT_DISPOSITION), is(true));
    }

    @Test
    public void testGetDocument2() throws Exception {

        final File file = folder.newFile("hello.txt");

        InputStream in = new ByteArrayInputStream("hello".getBytes());
        Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

        when(response.getOutputStream()).thenReturn(outputStream);
        doThrow(new IOException()).when(outputStream).write(any(byte[].class), any(int.class), any(int.class));

        when(doxExporter.export()).thenReturn(file);
        when(mimeTypes.getProperty("zip")).thenReturn("my content type");

        controller.getDocument(this.response);
        verify(response).setStatus(SC_INTERNAL_SERVER_ERROR);
    }
}
