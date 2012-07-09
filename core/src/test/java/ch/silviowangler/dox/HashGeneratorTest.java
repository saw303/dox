package ch.silviowangler.dox;

import ch.silviowangler.dox.domain.AttributeDataType;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
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
        final File file = loadFile("hello.txt");
        String hexHash = HashGenerator.sha256Hex(file);
        assertEquals(64, hexHash.length());
        String windowsExpected = "1f726f5ebbd97a8879f6f4fa8e2f63bd199609d8af4ae022ec42037c2e87d528";
        String linuxExpected = "c2c9274416c853a6a5b2f77abc4377f4db0c830936c723b56e88aaccc2589707";
        assertEquals(isLinux() ? linuxExpected : windowsExpected, hexHash);
    }

    private File loadFile(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        return FileUtils.toFile(resource);
    }

    @Test
    public void generateAnotherSha256HexString() throws IOException {
        final File file = loadFile("hello2.txt");
        String hexHash = HashGenerator.sha256Hex(file);
        assertEquals(64, hexHash.length());
        String windowsExpected = "51dabdb8d7e347706260691e3c3447f51c7e747f6b15ddcffd5b9c2ed15ddef7";
        String linuxExpected = "3646d48fbd1dfb5aa76f0d26ec709319a9892407a7bf0aa7c31e75d779b7e6cc";
        assertEquals(isLinux() ? linuxExpected : windowsExpected, hexHash);
    }

    private boolean isLinux() {
        return System.getProperty("os.name").contains("Linux");
    }
}
