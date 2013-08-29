package ch.silviowangler.dox.jobs;

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
import org.mockito.runners.MockitoJUnitRunner;

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

    @InjectMocks
    private VerificationServiceImpl service = new VerificationServiceImpl();
    @Mock
    private DocumentRepository documentRepository;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        service.setArchiveDirectory(folder.getRoot());
    }

    @Test
    public void testVerifyDocumentStore() throws Exception {
        when(documentRepository.findAllKeys()).thenReturn(Lists.<DocumentKeyHash>newArrayList());
        final List<MissingDocument> missingDocuments = service.verifyDocumentStore();
        assertThat(missingDocuments.size(), is(0));
    }
}
