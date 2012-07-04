package ch.silviowangler.dox;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public class HashGenerator {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static String sha256Hex(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        return  DigestUtils.sha256Hex(inputStream);
    }
}
