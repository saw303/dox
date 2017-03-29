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
package ch.silviowangler.dox.jobs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;

import ch.silviowangler.dox.domain.Document;
import ch.silviowangler.dox.repository.DocumentRepository;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@RunWith(MockitoJUnitRunner.class)
public class CleanUpJobServiceImplTest {

    @Mock
    private DocumentRepository documentRepository;
    @InjectMocks
    private CleanUpJobService service = new CleanUpJobServiceImpl();

    private final String hash = "123";

    private File parent = null;
    private File file;

    @Before
    public void setUp() throws Exception {
        file = new File(parent, hash);
        assertTrue(file.createNewFile());
    }

    @After
    public void tearDown() throws Exception {
        file.delete();
    }

    @Test
    public void testEmptyListReturned() throws Exception {

        when(documentRepository.findByFileSize(-1L)).thenReturn(new ArrayList<Document>());

        service.collectFileSizeOnDocumentsThatHaveNotBeenAssignedYet();

        InOrder order = inOrder(documentRepository);

        order.verify(documentRepository).findByFileSize(-1L);
        order.verifyNoMoreInteractions();
    }

    @Test
    public void testOneDocumentReturned() throws Exception {

        final Document document = new Document();
        document.setHash(this.hash);
        document.setFileSize(-1L);

        when(documentRepository.findByFileSize(-1L)).thenReturn(Lists.newArrayList(document));

        service.collectFileSizeOnDocumentsThatHaveNotBeenAssignedYet();

        InOrder order = inOrder(documentRepository);

        order.verify(documentRepository).findByFileSize(-1L);
        order.verify(documentRepository).save(document);
        order.verifyNoMoreInteractions();

        assertThat(document.getFileSize(), is(greaterThan(-1L)));
    }

    @Test
    public void testOneDocumentThatDoesNotExistReturned() throws Exception {

        final Document document = new Document();
        document.setHash("i/do/not/exist");
        document.setFileSize(-1L);

        when(documentRepository.findByFileSize(-1L)).thenReturn(Lists.newArrayList(document));

        service.collectFileSizeOnDocumentsThatHaveNotBeenAssignedYet();

        InOrder order = inOrder(documentRepository);

        order.verify(documentRepository).findByFileSize(-1L);
        order.verifyNoMoreInteractions();

        assertThat(document.getFileSize(), is(-1L));
    }


}
