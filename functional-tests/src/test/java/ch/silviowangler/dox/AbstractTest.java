package ch.silviowangler.dox;

import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URL;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
@ContextConfiguration("classpath:applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected File loadFile(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        final File file = FileUtils.toFile(resource);
        assert file != null && file.exists() : "File '" + fileName + "' does not exist. Resource " + resource.getFile();
        return file;
    }
}
