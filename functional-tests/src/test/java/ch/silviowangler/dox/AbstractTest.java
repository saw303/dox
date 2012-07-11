package ch.silviowangler.dox;

import ch.silviowangler.dox.api.DocumentService;
import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static junit.framework.Assert.assertTrue;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
@ContextConfiguration("classpath:applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected DocumentService documentService;

    protected File loadFile(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        final File file = FileUtils.toFile(resource);
        assert file != null && file.exists() : "File '" + fileName + "' does not exist. Resource " + resource.getFile();
        return file;
    }

    protected File createTestFile(final String fileName, final String content) throws IOException {
        File temp = new File(fileName);

        if (temp.exists()) FileUtils.forceDelete(temp);
        FileUtils.write(temp, content);
        assertTrue("Should exist", temp.exists());
        temp.deleteOnExit();

        return temp;
    }
}
