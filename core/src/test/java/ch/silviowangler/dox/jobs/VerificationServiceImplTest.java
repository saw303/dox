package ch.silviowangler.dox.jobs;

import ch.silviowangler.dox.api.MissingDocument;
import ch.silviowangler.dox.api.Source;
import ch.silviowangler.dox.domain.Document;
import ch.silviowangler.dox.repository.DocumentKeyHash;
import ch.silviowangler.dox.repository.DocumentRepository;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@RunWith(MockitoJUnitRunner.class)
public class VerificationServiceImplTest {

    public static final String CHARS_64 = "0123456789012345678901234567890123456789012345678901234567890123";
    @InjectMocks
    private VerificationServiceImpl service = new VerificationServiceImpl();
    @Mock
    private DocumentRepository documentRepository;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        folder.newFile(System.currentTimeMillis() + ".txt");
        service.setArchiveDirectory(folder.getRoot());
    }

    @Test
    public void testVerifyDocumentStore() throws Exception {
        when(documentRepository.findAllKeys()).thenReturn(Lists.<DocumentKeyHash>newArrayList());
        final List<MissingDocument> missingDocuments = service.verifyDocumentStore();
        assertThat(missingDocuments.size(), is(0));
    }

    @Test
    public void testVerifyDocumentStore2() throws Exception {
        when(documentRepository.findAllKeys()).thenReturn(Lists.<DocumentKeyHash>newArrayList(new DocumentKeyHash(1L, CHARS_64)));
        final List<MissingDocument> missingDocuments = service.verifyDocumentStore();
        assertThat(missingDocuments.size(), is(1));

        assertThat(missingDocuments.get(0).getHash(), is(CHARS_64));
        assertThat(missingDocuments.get(0).getSource(), is(Source.STORE));
    }

    @Test
    public void testVerifyDocumentStore3() throws Exception {

        this.folder.newFile(CHARS_64);

        when(documentRepository.findAllKeys()).thenReturn(Lists.<DocumentKeyHash>newArrayList(new DocumentKeyHash(1L, CHARS_64)));
        when(documentRepository.findByHash(CHARS_64)).thenReturn(new Document());
        final List<MissingDocument> missingDocuments = service.verifyDocumentStore();
        assertThat(missingDocuments.size(), is(0));
    }

    @Test
    public void testVerifyDocumentStore4() throws Exception {

        this.folder.newFile(CHARS_64);

        when(documentRepository.findAllKeys()).thenReturn(Lists.<DocumentKeyHash>newArrayList());
        when(documentRepository.findByHash(CHARS_64)).thenReturn(null);
        final List<MissingDocument> missingDocuments = service.verifyDocumentStore();
        assertThat(missingDocuments.size(), is(1));

        assertThat(missingDocuments.get(0).getHash(), is(CHARS_64));
        assertThat(missingDocuments.get(0).getSource(), is(Source.DATABASE));
    }
}
