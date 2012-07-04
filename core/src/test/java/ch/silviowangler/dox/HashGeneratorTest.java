package ch.silviowangler.dox;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static junit.framework.Assert.assertEquals;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public class HashGeneratorTest {

    @Test
    public void generateSha256HexString() throws IOException {

        URL resource = getClass().getClassLoader().getResource("hello.txt");

        String hexHash = HashGenerator.sha256Hex(FileUtils.toFile(resource));

        System.out.println(hexHash.length());

        assertEquals("1f726f5ebbd97a8879f6f4fa8e2f63bd199609d8af4ae022ec42037c2e87d528", hexHash);

    }
}
