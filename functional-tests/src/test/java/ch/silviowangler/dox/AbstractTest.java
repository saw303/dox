package ch.silviowangler.dox;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
@ContextConfiguration("classpath:applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
