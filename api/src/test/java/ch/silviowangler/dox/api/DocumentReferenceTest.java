package ch.silviowangler.dox.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@RunWith(Parameterized.class)
public class DocumentReferenceTest {

    private long fileSize;
    private String expectedHumanReadableFilesize;

    public DocumentReferenceTest(long fileSize, String expectedHumanReadableFilesize) {
        this.fileSize = fileSize;
        this.expectedHumanReadableFilesize = expectedHumanReadableFilesize;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return asList(new Object[][]{
                {1000L, "1.0 kB"},
                {1024L, "1.0 kB"},
                {9223372036854775807L, "9.2 EB"},
                {1000000L, "1.0 MB"},
                {1200000L, "1.2 MB"},
                {3900000000L, "3.9 GB"},
                {0L, "0 B"}
        });
    }

    @Test
    public void testHumanReadableFilesize() throws Exception {

        DocumentReference documentReference = new DocumentReference("");
        documentReference.setFileSize(this.fileSize);

        assertThat(documentReference.humanReadableFileSize(), is(this.expectedHumanReadableFilesize));
    }
}
