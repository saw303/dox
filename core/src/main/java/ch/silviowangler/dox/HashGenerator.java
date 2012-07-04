package ch.silviowangler.dox;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public class HashGenerator {

    private static Logger logger = LoggerFactory.getLogger(HashGenerator.class);

    public static String sha256Hex(File file) throws IOException {

        Assert.notNull(file, "file must not be null");

        logger.debug("About to generate SHA-256 hash for file '{}'. Does it exist? {}", file.getAbsolutePath(), file.exists());

        InputStream inputStream = new FileInputStream(file);
        return  DigestUtils.sha256Hex(inputStream);
    }
}
