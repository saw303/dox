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
